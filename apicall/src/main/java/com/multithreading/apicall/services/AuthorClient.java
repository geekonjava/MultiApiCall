package com.multithreading.apicall.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.multithreading.apicall.model.Author;

@Service
public class AuthorClient {
	
	@Async
	public CompletableFuture<Author> getAuthorDetails(String user) {
		
		String gitScorURL = "http://www.gitscore.com/user/"+user+"/calculate";
		String genreJson = "";
		try {
			genreJson = IOUtils.toString(new URL(gitScorURL));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject(genreJson);
        String authorPic = obj.getJSONObject("user").getString("avatar");
        String authorLocation = obj.getJSONObject("user").getString("location");
        String author_name = obj.getJSONObject("user").getString("name");
        String author_username = obj.getJSONObject("user").getString("username");
        int authorRank = (Integer) obj.get("position");
        int authorTotalScore = obj.getJSONObject("scores").getInt("total");
        int authorRepository = obj.getJSONObject("scores").getInt("repo");
        int authorGist = obj.getJSONObject("scores").getInt("gist");
        int authorUser = obj.getJSONObject("scores").getInt("user");
        
        Author author = new Author();
        
        author.setAuthorName(author_name);
        author.setAuthorUsername(author_username);
        author.setAuthorPic(authorPic);
        author.setAuthorLocation(authorLocation);
        author.setAuthorRank(authorRank);
        author.setAuthorScore(authorTotalScore);
        author.setAuthorRepo(authorRepository);
        author.setAuthorGist(authorGist);
        author.setAuthorUser(authorUser);
        
		return CompletableFuture.completedFuture(author);
	}

}
