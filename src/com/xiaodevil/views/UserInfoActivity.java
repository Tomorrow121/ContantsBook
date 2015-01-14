/**
 * 
 * @author ydli
 * 
 */
package com.xiaodevil.views;



import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.xiaodevil.contantsbook.R;
import com.xiaodevil.models.User;

public class UserInfoActivity extends Activity {
	
	private final static String TAG = "com.xiaodevil.views.UserInfoActivity";

	private TextView textview;
	private Button dia;
	private Button msg;
	private Button delete;
	private User user;
	protected void onCreate(Bundle savedInstanceState ){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts_info);
		Log.i(TAG,"UserInfoActivity start");
		textview = (TextView) findViewById(R.id.name);
		dia = (Button)findViewById(R.id.dia);
		msg = (Button)findViewById(R.id.msg);
		delete = (Button)findViewById(R.id.delete);
		Intent intent = this.getIntent();

		user = (User)intent.getSerializableExtra(MainActivity.SER_KEY);
		if(user != null){
			textview.setText(user.getUserName());
		}
		msg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//短信功能，应当使用对应user的号码
				Intent intent = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+user.getPhoneNumber()));
				intent.putExtra("sms_body","绍林好美");
				startActivity(intent);
				
				
			}
		});
		
		dia.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//拨号功能
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:"+user.getPhoneNumber()));
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				
			}
		});
		
		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
								
			}
		});
	}
}
