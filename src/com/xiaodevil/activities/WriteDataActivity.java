package com.xiaodevil.activities;

import java.util.List;
import android.os.Bundle;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.xiaodevil.database.DatabaseHelper;
import com.xiaodevil.models.User;

public class WriteDataActivity extends OrmLiteBaseActivity<DatabaseHelper>{
	
	private TextView mTextView;
	private RuntimeExceptionDao<User, Integer> mUserDAO;
	User user;
	
	@Override
	protected void onCreate(Bundle savedInstanceState ){
		super.onCreate(savedInstanceState);
		//setContentView();
		
		mUserDAO = getHelper().getUserDataDao();
		
	}
	/**
     * 插入值测试
     */
    private void insertTest()
    {
        for (int i = 0; i < 5; i++)
        {
            user = new User();
            user.setUserName("name" + i);
            user.setPhoneNumber("phoneNumber" + i);
            mUserDAO.createIfNotExists(user);
        }
    }

    /**
     * 更新
     * 
     * @param user 待更新的user
     */
    private void update(User user)
    {
        mUserDAO.createOrUpdate(user);
        // mUserDAO.update(user);
    }

    /**
     * 按照指定的id 与 username 删除一项
     * 
     * @param id
     * @param username
     * @return 删除成功返回true ，失败返回false
     */
    private int delete(String username)
    {
        try
        {
            // 删除指定的信息，类似delete User where 'id' = id ;
            DeleteBuilder<User, Integer> deleteBuilder = mUserDAO.deleteBuilder();
            deleteBuilder.where().eq("username", username);

            return deleteBuilder.delete();
        }
        catch (java.sql.SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 按照id查询user
     * 
     * @param id
     * @return
     */
    private User search(String username)
    {
        try
        {
            // 查询的query 返回值是一个列表
            // 类似 select * from User where 'username' = username;
            List<User> users = mUserDAO.queryBuilder().where().eq("username", username).query();
            if (users.size() > 0)
                return users.get(0);
        }
        catch (java.sql.SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除全部
     */
    private void deleteAll()
    {
        mUserDAO.delete(queryAll());
    }

    /**
     * 查询所有的
     */
    private List<User> queryAll()
    {
        List<User> users = mUserDAO.queryForAll();
        return users;
    }

    /**
     * 显示所有的
     */
    private void display()
    {
        List<User> users = queryAll();
        for (User user : users)
        {
            mTextView.append(user.toString());
        }
    }
}
