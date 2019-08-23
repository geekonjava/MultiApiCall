package com.multithreading.apicall.model;

import lombok.Data;


@Data
public class Article {
	
	private String postUrl;
	private String postTitle;
	private String postDescription;
	private int postWatch;
	private int postStar;
	private int postFork;
	private int postContributer;
	private int postCommit;
	private int postBranch;
	private int postRelease;
	private String postReadme;
	private String postReadMeText;
	private String categories;
	

}
