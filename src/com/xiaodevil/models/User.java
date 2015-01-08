/**
 * 
 * @author ydli
 *	
 */
package com.xiaodevil.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user")
public class User {
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(canBeNull = false)
	private String userName;
	
	@DatabaseField(canBeNull = false)
	private String phoneNumber;
	
	@DatabaseField(canBeNull = true)
	private String nickname;

	
	public User()
	{
		//ORMLite need
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}




	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Override
	public String toString()
	{
		String text = "";
		text += "\nid = " + id;
		text += "\nusername = " + userName;
		text += "\nphoneNumber = " + phoneNumber;
		text += "\nnickname = " + nickname;
		
		return text;
	}
}
