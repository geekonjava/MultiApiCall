package com.multithreading.apicall.services;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.multithreading.apicall.model.Article;
import com.multithreading.apicall.other.OtherFunctions;

@Service
public class ArticleClient {
	
	@Async
	public CompletableFuture<Article> getArticleDetails(String user, String repo){
		Article article = new Article();
		OtherFunctions ghs = new OtherFunctions();
		String url = "https://github.com/"+user+"/"+repo;
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String socialCount[] = ghs.getSocialCount(doc);
		String extraInfo[] = ghs.getExtraPostInfo(doc);
		Element readMe = ghs.getReadMe(doc);
		String categories = ghs.getCategories(doc);
		String post_title = ghs.getTitle(url);
		String post_description = ghs.getDescription(doc);
		String post_watch = socialCount[0].replaceAll(",", "");
		String post_star =  socialCount[1].replaceAll(",", "");
		String post_fork =  socialCount[2].replaceAll(",", "");
		
		String post_commit = extraInfo[0].replaceAll(",", "");
		String post_branch =  extraInfo[1].replaceAll(",", "");
		String post_release =  extraInfo[2].replaceAll(",", "");
		String post_contributor =  extraInfo[3].replaceAll(",", "");
		
		String readMeText = readMe.text();
		String readMeHtml = readMe.html();
		
		article.setPostTitle(post_title);
        article.setPostUrl(user+"/"+repo);
        article.setPostDescription(post_description);
        article.setPostWatch(Integer.parseInt(post_watch));
        article.setPostStar(Integer.parseInt(post_star));
        article.setPostFork(Integer.parseInt(post_fork));
        article.setPostCommit(Integer.parseInt(post_commit));
        article.setPostBranch(Integer.parseInt(post_branch));
        article.setPostRelease(Integer.parseInt(post_release));
        article.setPostContributer(Integer.parseInt(post_contributor));		
        article.setPostReadme(readMeHtml);
        article.setPostReadMeText(readMeText);
        article.setCategories(categories);

		return CompletableFuture.completedFuture(article);
	}

}
