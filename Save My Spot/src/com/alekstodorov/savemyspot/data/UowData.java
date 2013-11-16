package com.alekstodorov.savemyspot.data;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.alekstodorov.savemyspot.models.SpotModel;
import com.alekstodorov.savemyspot.models.UserModel;
  
public class UowData implements IUowData, IReadable {

	private IDatasource<UserModel> usersDatasource;
	private IDatasource<SpotModel> spotsDatasource;
	private List<IReadable> datasources;
	
	public UowData(Context context){
		
		usersDatasource = new UsersDatasource(context);
		spotsDatasource = new SpotsDatasource(context); 
		
		datasources = new ArrayList<IReadable>();
		datasources.add((IReadable)usersDatasource);
		datasources.add((IReadable)spotsDatasource); 
	}
	
	@Override
	public void open() {
		for (IReadable datasource : datasources) {
			datasource.open();
		} 
	} 
	
	@Override
	public void close() {
		for (IReadable datasource : datasources) {
			datasource.close();
		} 
	}

	@Override
	public IDatasource<UserModel> getUsers() {
		return usersDatasource;
	}

	@Override
	public IDatasource<SpotModel> getSpots() {
		return spotsDatasource;
	} 
}
