package com.multithreading.apicall.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.multithreading.apicall.model.Article;
import com.multithreading.apicall.model.Author;
import com.multithreading.apicall.model.Badge;
import com.multithreading.apicall.model.ExtraDetails;
import com.multithreading.apicall.other.GitData;
import com.multithreading.apicall.other.OtherFunctions;
import com.multithreading.apicall.services.ArticleClient;
import com.multithreading.apicall.services.AuthorClient;
import com.multithreading.apicall.services.BadgeClient;

@RestController
public class ApiCall {
	
	@Autowired
	BadgeClient badgeService;
	
	@Autowired
	AuthorClient authorService;
	
	@Autowired
	ArticleClient articleService;
	
	@GetMapping("async/{user}/{repo}")
	public GitData getAsyncData(@PathVariable("user") String userId, @PathVariable("repo") String articleUrl) throws InterruptedException, ExecutionException {
		long start = System.currentTimeMillis(); 
			GitData gitData = new GitData();
		// Extra Details
			CompletableFuture<Badge> Language = badgeService.getExtraDetails("language", userId, articleUrl);
	     	CompletableFuture<Badge> Vulnerability = badgeService.getExtraDetails("vulnerability", userId, articleUrl);
			CompletableFuture<Badge> LanguageCount = badgeService.getExtraDetails("language_count", userId, articleUrl);
			CompletableFuture<Badge> Dependencies = badgeService.getExtraDetails("dependencies", userId, articleUrl);
			CompletableFuture<Badge> CodeSize = badgeService.getExtraDetails("code_size", userId, articleUrl);
			CompletableFuture<Badge> License = badgeService.getExtraDetails("license", userId, articleUrl);
			CompletableFuture<Badge> OpenIssues = badgeService.getExtraDetails("open_issues", userId, articleUrl);
			CompletableFuture<Badge> CloseIssues = badgeService.getExtraDetails("close_issue", userId, articleUrl);
			CompletableFuture<Badge> CommitActivity = badgeService.getExtraDetails("commit_activity", userId, articleUrl);
			CompletableFuture<Badge> LastCommit = badgeService.getExtraDetails("last_commit", userId, articleUrl);
			CompletableFuture<Badge> ReleaseCommit = badgeService.getExtraDetails("release_commit", userId, articleUrl);
			CompletableFuture<Badge> CodeQuality = badgeService.getExtraDetails("code_quality", userId, articleUrl);
			CompletableFuture<Badge> RepoSize = badgeService.getExtraDetails("repo_size", userId, articleUrl);
		
		// Author Details
			CompletableFuture<Author> AuthorDetails = authorService.getAuthorDetails(userId);
			
		// Article Details
			CompletableFuture<Article> ArticleDetails = articleService.getArticleDetails(userId, articleUrl);
			
			CompletableFuture.allOf(Language, Vulnerability, LanguageCount, Dependencies, CodeSize, License,
					OpenIssues, CloseIssues, CommitActivity, LastCommit, ReleaseCommit, CodeQuality, RepoSize, 
					AuthorDetails, ArticleDetails).join();
			long end = System.currentTimeMillis(); 
			
			gitData.setLoadTime(end-start);
        	gitData.setLanguage(Language.get());
        	gitData.setVulnerability(Vulnerability.get());
        	gitData.setLanguageCount(LanguageCount.get());
        	gitData.setDependencies(Dependencies.get());
        	gitData.setCodeSize(CodeSize.get());
        	gitData.setLicense(License.get());
        	gitData.setOpenIssues(OpenIssues.get());
        	gitData.setCloseIssues(CloseIssues.get());
        	gitData.setCommitActivity(CommitActivity.get());
        	gitData.setLastCommit(LastCommit.get());
        	gitData.setReleaseCommit(ReleaseCommit.get());
        	gitData.setCodeQuality(CodeQuality.get());
        	gitData.setRepoSize(RepoSize.get());
        	gitData.setAuthor(AuthorDetails.get());
        	gitData.setArticle(ArticleDetails.get());
        
        
        return gitData;
	}
	
	@GetMapping("simple/{user}/{repo}")
	public void getSimpleData(@PathVariable("user") String userId, @PathVariable("repo") String articleUrl) throws InterruptedException, ExecutionException {
		OtherFunctions ghs = new OtherFunctions();
		ExtraDetails extraDetails = new ExtraDetails();
		
		long start = System.currentTimeMillis(); 
        extraDetails.setLanguage(ghs.getExtraDetails("language", userId, articleUrl));
        extraDetails.setVulnerability(ghs.getExtraDetails("vulnerability", userId, articleUrl));
        extraDetails.setLanguageCount(ghs.getExtraDetails("language_count", userId, articleUrl));
        extraDetails.setDependencies(ghs.getExtraDetails("dependencies", userId, articleUrl));
        extraDetails.setCodeSize(ghs.getExtraDetails("code_size", userId, articleUrl));
        extraDetails.setLicense(ghs.getExtraDetails("license", userId, articleUrl));
        extraDetails.setOpenIssues(ghs.getExtraDetails("open_issues", userId, articleUrl));
        extraDetails.setCloseIssues(ghs.getExtraDetails("close_issue", userId, articleUrl));
        extraDetails.setCommitActivity(ghs.getExtraDetails("commit_activity", userId, articleUrl));
        extraDetails.setLastCommit(ghs.getExtraDetails("last_commit", userId, articleUrl));
        extraDetails.setReleaseCommit(ghs.getExtraDetails("release_commit", userId, articleUrl));
        extraDetails.setCodeQuality(ghs.getExtraDetails("code_quality", userId, articleUrl));
        extraDetails.setRepoSize(ghs.getExtraDetails("repo_size", userId, articleUrl));
        System.out.println(extraDetails.getLanguage());
        long end = System.currentTimeMillis(); 
        System.out.println("Estimated time::: " + 
                (end - start) + "ms"); 
	}

}
