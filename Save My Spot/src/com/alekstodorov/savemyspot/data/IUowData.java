package com.alekstodorov.savemyspot.data;

import com.alekstodorov.savemyspot.models.SpotModel;
import com.alekstodorov.savemyspot.models.UserModel;

public interface IUowData { 
	IDatasource<UserModel> getUsers(); 
	IDatasource<SpotModel> getSpots(); 
}
