package com.xiaodevil.database;

import android.content.ContentValues;
import android.content.Context;

public class DataHelper {
	private Context context;
	private ContentValues values;
	public DataHelper(){
		
	}
	public DataHelper(Context context){
		this.context = context;
		values = new ContentValues();
	}
	
	public boolean insertContacts(ContentValues values){
		return false;
	}
	
	public boolean deleteContacts(){
		//context.getContentResolver().delete(url, where, selectionArgs)
		return false;
	}
	
	
}
