package com.cocomsys.http101;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yesez on 07-11-14.
 */
public class VideoItem {

	@SerializedName("id")
	private String id;
	private String uploaded;
	private String title;
	private String description;
	private double duration;
	private int viewCount;

	public static final String DATA_FIELD = "data";
	public static final String ITEMS_FIELD = "items";
	public static final String ID_FIELD = "id";
	public static final String UPLOADED_FIELD = "uploaded";
	public static final String TITLE_FIELD = "title";
	public static final String DESCRIPTION_FIELD = "description";
	public static final String DURATION_FIELD = "duration";
	public static final String VIEWCOUNT_FIELD = "viewCount";

	public VideoItem() {}

	public VideoItem(String id, String uploaded, String title, String description, double duration, int viewCount) {
		this.id = id;
		this.uploaded = uploaded;
		this.title = title;
		this.description = description;
		this.duration = duration;
		this.viewCount = viewCount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUploaded() {
		return uploaded;
	}

	public void setUploaded(String uploaded) {
		this.uploaded = uploaded;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
}
