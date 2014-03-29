package com.cocomsys.todoapp.models;

import java.util.ArrayList;
import java.util.Date;

public class Task {
	private int id;
	private String name;
	private String description;
	private Date creationDate;
	private static ArrayList<Task> tasks;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public static ArrayList<Task> getTasks() {
		if(tasks == null) tasks = new ArrayList<Task>();
		return tasks;
	}

	public static void setTasks(ArrayList<Task> _tasks) {
		tasks = _tasks;
	}

	@Override
	public String toString() {
		return "task{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}
