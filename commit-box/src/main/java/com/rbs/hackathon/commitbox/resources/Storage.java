package com.rbs.hackathon.commitbox.resources;

import java.util.Date;

public class Storage {

	private Date lastModified;
	private String lastBuildNumber;

	private Storage() {
		
	}
	
	private static Storage instance;
	
	public static Storage getInstance() {
		if (instance == null) {
			instance = new Storage();
		}
		
		return instance;
	}

	public Date getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(final Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getLastBuildNumber() {
		return this.lastBuildNumber;
	}
	
	public void setLastBuildNumber(final String lastBuildID) {
		this.lastBuildNumber = lastBuildID;
	}
}
