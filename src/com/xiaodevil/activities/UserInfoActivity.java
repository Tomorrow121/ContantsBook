package com.xiaodevil.activities;



import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.xiaodevil.contantsbook.R;
import com.xiaodevil.database.DatabaseHelper;

public class UserInfoActivity extends OrmLiteBaseActivity<DatabaseHelper> {

	private TextView textview;
	protected void onCreate(Bundle savedInstanceState ){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts_info);
		
		textview = (TextView) findViewById(R.id.name);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		textview.setText(bundle.getString("name"));	
	}
}
