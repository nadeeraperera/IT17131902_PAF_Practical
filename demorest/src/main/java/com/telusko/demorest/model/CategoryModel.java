package com.telusko.demorest.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CategoryModel {
	
	private int id_categories;
	private String description;
	
	public int getid_categories() {
		return id_categories;
	}
	public void setid_categories(int id_categories) {
		this.id_categories = id_categories;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

}
