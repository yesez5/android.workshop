package com.cocomsys.http101;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class HttpService {

	private final String Source =
			"https://gdata.youtube.com/feeds/api/users/%s/uploads?v=2&alt=jsonc&start-index=1&max-results=%d";

	private class DefaultData{
		public static final String User = "yesez5";
		public static final int Count = 3;
	}

	private String getSource(){
		return String.format(Source, DefaultData.User, DefaultData.Count);
	}

	private String getSource(String user, int count){
		return String.format(Source, user, count);
	}
//region http client
	public String getByApacheHttpClient(){
		String response = "";

		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(getSource());
			getRequest.addHeader("accept", "application/json");

			HttpResponse httpResponse = httpClient.execute(getRequest);
			response = parseBufferToString(httpResponse.getEntity().getContent());
			httpClient.getConnectionManager().shutdown();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	private String parseBufferToString(InputStream stream){
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(stream));

		StringBuilder result = new StringBuilder();
		String line;
		try {
			while ((line = reader.readLine()) != null)
				result.append(line);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result.toString();
	}

	public ArrayList<VideoItem> parseToModel(String data){
		ArrayList<VideoItem> items = new ArrayList<VideoItem>();
		try {
			JSONObject root = new JSONObject(data);
			JSONObject dataObj = root.getJSONObject(VideoItem.DATA_FIELD);
			JSONArray itemsArray = dataObj.getJSONArray(VideoItem.ITEMS_FIELD);

			for(int i = 0; i < itemsArray.length(); i++){
				JSONObject jsonItem = itemsArray.getJSONObject(i);
				VideoItem modelToAdd = parseToModel(jsonItem);
				items.add(modelToAdd);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return items;
	}

	private VideoItem parseToModel(JSONObject jsonModel){
		VideoItem itemToAdd = null;
		try {
			itemToAdd = new VideoItem(
					jsonModel.getString(VideoItem.ID_FIELD),
					jsonModel.getString(VideoItem.UPLOADED_FIELD),
					jsonModel.getString(VideoItem.TITLE_FIELD),
					jsonModel.getString(VideoItem.DESCRIPTION_FIELD),
					jsonModel.getDouble(VideoItem.DURATION_FIELD),
					jsonModel.getInt(VideoItem.VIEWCOUNT_FIELD)
			);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return itemToAdd;
	}

	public ArrayList<VideoItem> parseToModelWithGson(String data){
		ArrayList<VideoItem> list = new ArrayList<VideoItem>();
		Gson parser = new Gson();
		try {
			String items = new JSONObject(data)
					.getJSONObject(VideoItem.DATA_FIELD)
					.getJSONArray(VideoItem.ITEMS_FIELD)
					.toString();

			Type type = new TypeToken<ArrayList<VideoItem>>(){}.getType();
			list = parser.fromJson(items, type);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return list;
	}
//endregion


}
