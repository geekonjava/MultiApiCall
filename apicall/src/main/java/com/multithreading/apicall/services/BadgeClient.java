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
import com.multithreading.apicall.model.Badge;
import com.multithreading.apicall.other.OtherFunctions;

@Service
public class BadgeClient {
	
	
	@Async
	public CompletableFuture<Badge> getExtraDetails(String urlText, String user, String repo) {
		System.setProperty("http.agent", "Chrome");
		String url = OtherFunctions.getJsonURL(urlText, user, repo);
		String badgeData = "";
		String label = "";
		String message = "";
		Badge badgeObj = new Badge();
		try {
			badgeData = IOUtils.toString(new URL(url));
			JSONObject badge = new JSONObject(badgeData);
			label = (String) badge.get("label");
			message = badge.getString("message");
			badgeObj.setLabel(label);
			badgeObj.setMessage(message);
		} catch (MalformedURLException e) {
			badgeObj.setLabel("Malformed URL Exception");
			badgeObj.setMessage(e.toString());
		} catch (IOException e) {
			badgeObj.setLabel("IO Exception");
			badgeObj.setMessage(e.toString());
		}
		
		return CompletableFuture.completedFuture(badgeObj);
	}
	
	

}
