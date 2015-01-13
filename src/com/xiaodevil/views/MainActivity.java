/**
 * 
 * @author ydli
 * 
 */
package com.xiaodevil.views;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AlphabetIndexer;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaodevil.contantsbook.R;
import com.xiaodevil.models.User;
import com.xiaodevil.utils.ContactAdapter;

@SuppressLint("DefaultLocale")
public class MainActivity extends Activity {
	private ListView contactsListView;
	private LinearLayout titleLayout;
	private TextView title;
	private ContactAdapter adapter;
	private AlphabetIndexer indexer;
	private Button alphabetButton; 
	//private RelativeLayout sectionToastLayout;
	//private TextView sectionToastText;
	private Toast sectionToast;//暂时用Toast代替
	private List<User> users = new ArrayList<User>();
	private String alphabet = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private int lastFirstVisibleItem = -1;
	
	
	private Intent intent = new Intent();
	private User selectedUser = new User();
	private final static String TAG = "com.xiaodevil.views.MainActivity";
	private Cursor cursor;
	public final static String SER_KEY = "com.xiaode.user";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG,"MainActivity start");
        setupViews();
     
        contactsListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bundle mBundle = new Bundle();
				selectedUser = (User)adapter.getItem(arg2);
				mBundle.putSerializable(SER_KEY, selectedUser);
				intent.putExtras(mBundle);
				intent.setClass(MainActivity.this, UserInfoActivity.class);
				cursor.close();//跳转页面前关闭游标
				startActivity(intent);
			}
		});
    
        
    }
    
    /**
     * 绑定组件，访问联系人数据库
     * 
     */
    public void setupViews(){
    	contactsListView = (ListView) findViewById(R.id.contancts_list);
    	titleLayout = (LinearLayout) findViewById(R.id.title_layout);
    	title = (TextView) findViewById(R.id.title);
    	alphabetButton = (Button) findViewById(R.id.alphabetButton);

    	
    	adapter = new ContactAdapter(this, R.layout.contact_item,users);
    	Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    	cursor = getContentResolver().query(uri, 
    			new String[]{"display_name","sort_key","data1"}, 
    			null, 
    			null, 
    			"sort_key");
    	
    	
    	if(cursor.moveToFirst()){
    		do{
    			String name = cursor.getString(0);
    			String sortKey = getSortKey(cursor.getString(cursor.getColumnIndex("sort_key")));
    			String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
    			User user = new User();
    			user.setUserName(name);
    			user.setSortKey(sortKey);
    			user.setPhoneNumber(phoneNumber);
    			users.add(user);
    		}while(cursor.moveToNext());
    		startManagingCursor(cursor);
    		indexer = new AlphabetIndexer(cursor, 1, alphabet);
    		adapter.setIndexer(indexer);
    		if(users.size() > 0){
    			setupContactsListView();
    		}
    		
    	}
    	
    	alphabetButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float alphabetHeight = alphabetButton.getHeight();
				float Y = event.getY();
				int sectionPosition = (int) ((Y / alphabetHeight) / (1f / 27f));
				if(sectionPosition < 0){
					sectionPosition = 0;
				}else if(sectionPosition > 26){
					sectionPosition = 26;
				}
				String sectionLetter = String.valueOf(alphabet.charAt(sectionPosition));
				int position = indexer.getPositionForSection(sectionPosition);
				switch(event.getAction()){
				case MotionEvent.ACTION_DOWN:
					alphabetButton.setBackgroundResource(R.drawable.a_z_click);
					sectionToast = Toast.makeText(getApplicationContext(), sectionLetter, Toast.LENGTH_SHORT);
					sectionToast.setGravity(Gravity.CENTER, 0, 0);
					sectionToast.show();
					contactsListView.setSelection(position);
					break;
					
				case MotionEvent.ACTION_MOVE:
					sectionToast = Toast.makeText(getApplicationContext(), sectionLetter, Toast.LENGTH_SHORT);
					sectionToast.setGravity(Gravity.CENTER, 0, 0);
					sectionToast.show();
					contactsListView.setSelection(position);
					break;
					
				default:
					alphabetButton.setBackgroundResource(R.drawable.a_z);
				}
				
				return true;
			}
		});
    }

    /**
     * 为联系人ListView设置监听事件，根据当前的滑动状态来改变分组的显示位置，实现挤压动画效果
     * 
     */
    private void setupContactsListView(){
    	
    	contactsListView.setAdapter(adapter);
    	//滑动响应
    	contactsListView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				int section = indexer.getSectionForPosition(firstVisibleItem);
				int nextSecPosition = indexer.getPositionForSection(section+1);
				if(firstVisibleItem != lastFirstVisibleItem){
					MarginLayoutParams params = (MarginLayoutParams)titleLayout.getLayoutParams();
					params.topMargin = 0;
					titleLayout.setLayoutParams(params);
					title.setText(String.valueOf(alphabet.charAt(section)));	
				}
				if(nextSecPosition == firstVisibleItem + 1){
					View childView = view.getChildAt(0);
					if(childView != null){
						int titleHeight = titleLayout.getHeight();
						int bottom = childView.getBottom();
						MarginLayoutParams params = (MarginLayoutParams)titleLayout.getLayoutParams();
						if (bottom < titleHeight) {
							float pushedDistance = bottom - titleHeight;
							params.topMargin = (int)pushedDistance;
							title.setLayoutParams(params);
						}else{
							if(params.topMargin != 0 ){
								params.topMargin = 0;
								titleLayout.setLayoutParams(params);
							}
						}
					}
				}
				lastFirstVisibleItem = firstVisibleItem;
			}
			
		});
    	
    	
    }
    /**
     * 获取sort key 的首个字母，如果是英文就直接返回，否则返回#
     *
     *@param sortKeyString
     *@return 英文字母或者#
     *
     */
    private String getSortKey(String sortKeyString){
    	String key = sortKeyString.substring(0,1).toUpperCase();

    	if(key.matches("[A-Z]")){
    		
    		return key;
    	}
    	return "#";
    }
}
