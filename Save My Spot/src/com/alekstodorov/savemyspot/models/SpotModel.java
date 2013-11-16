package com.alekstodorov.savemyspot.models;

public class SpotModel {

	private long id;
	private String title; 
	private double latitute;
	private double longitude;
	
	public SpotModel(){
		
	}

	public SpotModel(String title,
			double latitute, double longitude) {
		super(); 
		this.title = title; 
		this.latitute = latitute;
		this.longitude = longitude;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
 
	public double getLatitute() {
		return latitute;
	}

	public void setLatitute(double latitute) {
		this.latitute = latitute;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
}
