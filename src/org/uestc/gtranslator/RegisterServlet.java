package org.uestc.gtranslator;

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

import static org.uestc.gtranslator.Data.*;

@SuppressWarnings("serial")
public class RegisterServlet extends HttpServlet {
	// 用户名密码验证均在客户端完成
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
        Entity user = new Entity(USER_ENTITY_KIND, userKey);
        user.setProperty(USER_COL_USERNAME, userName);
        user.setProperty(USER_COL_PASSWD, passwd);
        user.setProperty(USER_COL_DATE, date);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(user);
        
        // 返回注册结果
        resp.getWriter().write(RESULT_SUCC);
	}
	
}
