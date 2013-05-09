package org.uestc.gtranslator;

import java.io.IOException;
import java.util.List;

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
public class DlHisWordsServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/xml;UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String userName = req.getParameter(USERNAME_TAG);
		
		// 查询用户历史词
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key HisKey = KeyFactory.createKey(HISWORD_KEY_KIND, HISWORD_KEY);
		Query query = new Query(HISWORD_ENTITY_KIND, HisKey).addFilter(
				USER_COL_USERNAME, Query.FilterOperator.EQUAL, userName);
	    List<Entity> hisWords = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(40));

	    StringBuilder hisWordsStr = new StringBuilder();
	    for (Entity word : hisWords) {
	    	hisWordsStr.append(word.getProperty(HISWORD_COL) + "|");
	    }
	    
	    String result = "";
	    if (hisWords.size() > 1)
	    	result = hisWordsStr.substring(0, hisWordsStr.length() - 1);
	    else if (hisWords.size() == 1)
	    	result = hisWords.get(0).getProperty(HISWORD_COL).toString();
        resp.getWriter().write(result);
	}
	
}
