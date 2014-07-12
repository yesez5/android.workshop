package com.cocomsys.db101.models;

/**
 * Created by yesez on 07-12-14.
 */
public class Country {

	private int id;
	private String name;
	private String abrev;

	public Country() {}

	public Country(int id, String name, String abrev) {
		this.id = id;
		this.name = name;
		this.abrev = abrev;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbrev() {
		return abrev;
	}

	public void setAbrev(String abrev) {
		this.abrev = abrev;
	}

}
