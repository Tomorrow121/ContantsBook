/**
 * 
 * @author ydli
 *	
 */
package com.xiaodevil.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.xiaodevil.models.User;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	private static final String TAG = "DatabaseHelper";
	
	//database name
	private static final String DATABASE_NAME = "MyContacts.db";
	
	//database version
	private static final int DATABASE_VERSION = 1;
	
	private Dao<User, Integer> userDao = null;
	
	private RuntimeExceptionDao<User, Integer> userRuntimeDao = null;
	
	public DatabaseHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	/**
     * @param context
     * @param databaseName
     * @param factory
     * @param databaseVersion
     */
	public DatabaseHelper(Context context, String databaseName, CursorFactory factory, int databaseVersionb){
		super(context, databaseName, factory, databaseVersionb);
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connecttionSource) {
		try{
			//setup tabel User
			TableUtils.createTable(connecttionSource, User.class);
			//initialise DAO
			userDao = getUserDao();
		}catch(SQLException e){
			Log.e(TAG, e.toString());
			e.printStackTrace();
		}		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	///////////////setters and getters
	/**
	 * 
	 * @return
	 * @throws SQLException 
	 * @throws SQLEXception
	 */
	private Dao<User, Integer> getUserDao() throws SQLException {
		if(userDao == null){
			userDao = getDao(User.class);
			}
		return userDao;
	}

	public RuntimeExceptionDao<User, Integer> getUserRuntimeDao() {
		if(userRuntimeDao == null){
			userRuntimeDao = getRuntimeExceptionDao(User.class);
		}
		return userRuntimeDao;
	}

	public void setUserRuntimeDao(RuntimeExceptionDao<User, Integer> userRuntimeDao) {
		this.userRuntimeDao = userRuntimeDao;
	}
	
	@Override
	public void close(){
		super.close();
		userRuntimeDao = null;
	}

}
