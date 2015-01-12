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
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.xiaodevil.contantsbook.R;

public class MainActivity extends Activity {
	private ListView contactsListView;
	
	private Intent intent = new Intent();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactsListView = (ListView) findViewById(R.id.list1);
        Cursor cursor = getContentResolver().query(People.CONTENT_URI, null, null, null, null);
        startManagingCursor(cursor);
        
        ListAdapter listAdapter = new SimpleCursorAdapter(this, 
        		android.R.layout.simple_expandable_list_item_1, 
        		cursor, 
        		new String[]{People.NAME}, 
        		new int[]{});
        
        ArrayAdapter<String> listAdapter2 = new ArrayAdapter<String>(this, 
        		android.R.layout.simple_expandable_list_item_1,
        		getData());
        
        contactsListView.setAdapter(listAdapter2);
        
        contactsListView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				intent.putExtra("name", "xiaode");
				intent.setClass(MainActivity.this, UserInfoActivity.class);
				startActivity(intent);
				
			}
		});
        
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
