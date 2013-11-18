package com.alekstodorov.savemyspot.models;

public class SpotModel {

	private long id;
	private String title; 
	private double latitude;
	private double longitude;
	private String authCode;
	
	public SpotModel(){
		
	}

	public SpotModel(String title,
			double latitute, double longitude, String authCode) {
		super(); 
		this.title = title; 
		this.latitude = latitute;
		this.longitude = longitude;
		this.authCode = authCode;
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
 
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
}
