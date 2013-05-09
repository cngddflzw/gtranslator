package org.uestc.gtranslator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
import com.google.appengine.api.datastore.Query;

import static org.uestc.gtranslator.Data.*;

@SuppressWarnings("serial")
public class DlNewWordsServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/xml;UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String userName = req.getParameter(USERNAME_TAG);
		
		// 查询用户生词
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key newKey = KeyFactory.createKey(NEWWORD_KEY_KIND, NEWWORD_KEY);
		Query query = new Query(NEWWORD_ENTITY_KIND, newKey).addFilter(
				USER_COL_USERNAME, Query.FilterOperator.EQUAL, userName);
	    List<Entity> newWords = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(40));

	    StringBuilder newWordsStr = new StringBuilder();
	    for (Entity word : newWords)
	    	newWordsStr.append(word.getProperty(NEWWORD_COL) + "|");
	    
        // 返回查询结果
	    String result = "";
	    if (newWords.size() > 1)
	    	result = newWordsStr.substring(0, newWordsStr.length() - 1);
	    else if (newWords.size() == 1)
	    	result = newWords.get(0).getProperty(HISWORD_COL).toString();
        resp.getWriter().write(result);
	}
	
}
