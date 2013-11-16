package com.alekstodorov.savemyspot.data;

import java.util.List;

public interface IDatasource<T> {

	T create(T entry); 
	List<T> findAll(); 
	T findById(long id); 
}
