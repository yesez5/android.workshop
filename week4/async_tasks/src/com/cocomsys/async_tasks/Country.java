package com.cocomsys.async_tasks;

import java.util.ArrayList;

/**
 * Created by yesez on 07-06-14.
 */
public class Country {
	private static int counter = 1;
	private String name;

	public Country() {}

	public Country(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static ArrayList<Country> generate(int size, int lag){
		try {
			Thread.sleep(lag * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ArrayList<Country> list = new ArrayList<Country>();
		for(int i = 1; i <= size; i++){
			list.add(new Country("country " + counter));
			counter++;
		}
		return list;
	}

	@Override
	public String toString() {
		return getName();
	}
}
