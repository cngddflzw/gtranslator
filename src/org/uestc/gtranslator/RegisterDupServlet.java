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
public class RegisterDupServlet extends HttpServlet {

	// 验证用户名是否重复
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String userName = req.getParameter(USERNAME_TAG);
		
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key userKey = KeyFactory.createKey(USER_KEY_KIND, USER_KEY);
		Query query = new Query(USER_ENTITY_KIND, userKey).addFilter(
				USER_COL_USERNAME, Query.FilterOperator.EQUAL, userName);
	    List<Entity> usernames = datastore.prepare(query).asList(FetchOptions.Builder.withLimit(1));
	    
	    if (usernames.size() < 1)
	    	resp.getWriter().write(RESULT_SUCC);
	    else
	    	resp.getWriter().write(RESULT_FAILED);
	}
	
}
