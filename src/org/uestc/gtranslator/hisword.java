package org.uestc.gtranslator;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class hisword {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
    
    @Persistent
	private String history_word;
	
	@Persistent
	private String username;
	
	public hisword(String hw, String un) {
		this.history_word = hw;
		this.username = un;
	}
	
	public Key getKey() {
		return key;
	}

	public String getHistory_word() {
		return history_word;
	}
	
	public void setHistory_word(String history_word) {
		this.history_word = history_word;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}
