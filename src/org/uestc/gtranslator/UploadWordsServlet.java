package org.uestc.gtranslator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import static org.uestc.gtranslator.Data.*;

@SuppressWarnings("serial")
public class UploadWordsServlet extends HttpServlet {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private TimeZone timezone = TimeZone.getTimeZone("Asia/Shanghai");
	
	public UploadWordsServlet() {
		sdf.setTimeZone(timezone);
	}

	/**
	 * 同步用户本地生词和历史单词到服务器
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/xml;UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String userName = req.getParameter(USERNAME_TAG);
		String hiswords = req.getParameter(HISWORD_TAG);
		String newwords = req.getParameter(NEWWORD_TAG);
		
		if ((hiswords == null || hiswords.equals("")) 
				&& (newwords == null || newwords.equals("")))
//				|| (userName == null || userName.equals("")))
			return;
		
		// 切分本地单词和历史单词
		List<String> hiswordsList = new ArrayList<String>(Arrays.asList(hiswords.split("\\|")));
		List<String> newwordsList = new ArrayList<String>(Arrays.asList(newwords.split("\\|")));
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		// 删除原有单词
		PersistenceManager pm = PMF.get().getPersistenceManager();
	    Query query = pm.newQuery(hisword.class);
	    query.setFilter("username == usernameParam");
	    query.declareParameters("String usernameParam");
	    query.deletePersistentAll(userName);
	    query = pm.newQuery(new_word.class);
	    query.setFilter("username == usernameParam");
	    query.declareParameters("String usernameParam");
	    query.deletePersistentAll(userName);
	
		// 保存历史单词
		for (String word : hiswordsList) {
			Key hiswordKey = KeyFactory.createKey(HISWORD_KEY_KIND, HISWORD_KEY);
			Entity hisword = new Entity(HISWORD_ENTITY_KIND, hiswordKey);
	        hisword.setProperty(USER_COL_USERNAME, userName);
	        hisword.setProperty(HISWORD_COL, word);
	        datastore.put(hisword);
		}
		
		// 保存生词
		for (String word : newwordsList) {
			Key newwordKey = KeyFactory.createKey(NEWWORD_KEY_KIND, NEWWORD_KEY);
			Entity newword = new Entity(NEWWORD_ENTITY_KIND, newwordKey);
	        newword.setProperty(USER_COL_USERNAME, userName);
	        newword.setProperty(NEWWORD_COL, word);
	        datastore.put(newword);
		}
		
        // 返回结果
        resp.getWriter().write(RESULT_SUCC);
	}
	
}
