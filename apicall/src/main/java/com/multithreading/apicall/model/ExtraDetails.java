package com.multithreading.apicall.model;

import lombok.Data;

@Data
public class ExtraDetails {
	
	private String language;
	private String vulnerability;
	private String languageCount;
	private String dependencies;
	private String codeSize;
	private String repoSize;
	private String openIssues;
	private String closeIssues;
	private String license;
	private String commitActivity;
	private String lastCommit;
	private String releaseCommit;
	private String codeQuality;
	private String coverAll;

}