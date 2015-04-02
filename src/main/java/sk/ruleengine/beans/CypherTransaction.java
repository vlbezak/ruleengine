package sk.ruleengine.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CypherTransaction {
	
	private String expires;

	public CypherTransaction() {
		
	}
	
	public String getExpires() {
		return expires;
	}

	public void setExpires(String expires) {
		this.expires = expires;
	}

	@Override
	public String toString() {
		return "CypherTransaction [expires=" + expires + "]";
	}
}
