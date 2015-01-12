/**
 * 
 * @author ydli
 * 
 */
package com.xiaodevil.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xiaodevil.contantsbook.R;
import com.xiaodevil.models.User;

public class MainActivity extends Activity {
	private ListView contactsListView;
	
	private Intent intent = new Intent();
	private User selectedUser = new User();
	
	public final static String SER_KEY = "com.xiaode.user";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
//        Cursor cursor = getContentResolver().query(People.CONTENT_URI, null, null, null, null);
//        startManagingCursor(cursor);
//        
//        ListAdapter listAdapter = new SimpleCursorAdapter(this, 
//        		android.R.layout.simple_expandable_list_item_1, 
//        		cursor, 
//        		new String[]{People.NAME}, 
//        		new int[]{});
        
        ArrayAdapter<String> listAdapter2 = new ArrayAdapter<String>(this, 
        		android.R.layout.simple_expandable_list_item_1,
        		getData());
        
        contactsListView.setAdapter(listAdapter2);
      
        contactsListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bundle mBundle = new Bundle();
				mBundle.putSerializable(SER_KEY, selectedUser);
				intent.putExtras(mBundle);
				intent.putExtra("name", "xiaode");
				
				intent.setClass(MainActivity.this, UserInfoActivity.class);
				startActivity(intent);
			}
		});
    
        
    }
    
    public void setupViews(){
    	contactsListView = (ListView) findViewById(R.id.list1);
    }

    private List<String> getData(){
    	List<String> data =  new ArrayList<String>();
    	data.add("假少林");
    	data.add("王四康");
    	data.add("留梦成");
    	data.add("高格");
    	return data;
    }
}
