package com.multithreading.apicall.other;

import com.multithreading.apicall.model.Article;
import com.multithreading.apicall.model.Author;
import com.multithreading.apicall.model.Badge;

import lombok.Data;

@Data
public class GitData {
	
	private long LoadTime;
	private Badge Language;
	private Badge Vulnerability;
	private Badge LanguageCount;
	private Badge Dependencies;
	private Badge CodeSize;
	private Badge License;
	private Badge OpenIssues;
	private Badge CloseIssues;
	private Badge CommitActivity;
	private Badge LastCommit;
	private Badge ReleaseCommit;
	private Badge CodeQuality;
	private Badge RepoSize;
	private Author Author;
	private Article Article;
	

}
