package com.uestc.gtranslator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
public class RegisterServlet extends HttpServlet {
	// 用户名密码验证均在客户端完成
	private static final String USERNAME_TAG = "user_name";	// 获取post数据的key
	private static final String PASSWD_TAG = "passwd";
	private static final String USER_KEY_KIND = "user_key_kind";
	private static final String USER_KEY = "user_key";
	private static final String USER_ENTITY_KIND = "user";
	private static final String USER_COL_USERNAME = "username";	// 表中列名
	private static final String USER_COL_PASSWD = "passwd";
	private static final String USER_COL_DATE = "date";
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private TimeZone timezone = TimeZone.getTimeZone("Asia/Shanghai");
	
	public RegisterServlet() {
		sdf.setTimeZone(timezone);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String userName = req.getParameter(USERNAME_TAG);
		String passwd = req.getParameter(PASSWD_TAG);
		
		// 保存注册信息
		Key userKey = KeyFactory.createKey(USER_KEY_KIND, USER_KEY);
        String date = sdf.format(new Date());
        Entity greeting = new Entity(USER_ENTITY_KIND, userKey);
        greeting.setProperty(USER_COL_USERNAME, userName);
        greeting.setProperty(USER_COL_PASSWD, passwd);
        greeting.setProperty(USER_COL_DATE, date);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(greeting);
        
        // 返回注册结果
        resp.getWriter().write("注册成功");
	}
	
}
