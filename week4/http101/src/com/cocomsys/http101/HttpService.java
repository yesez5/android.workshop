package com.cocomsys.http101;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
//endregion


}
