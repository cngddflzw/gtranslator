package org.uestc.gtranslator;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class new_word {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
    
    @Persistent
	private String new_word;
	
	@Persistent
	private String username;
	
	public new_word(String nw, String un) {
		this.new_word = nw;
		this.username = un;
	}
	
	public Key getKey() {
		return key;
	}

	public String getNew_word() {
		return new_word;
	}
	
	public void setNew_word(String new_word) {
		this.new_word = new_word;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}
