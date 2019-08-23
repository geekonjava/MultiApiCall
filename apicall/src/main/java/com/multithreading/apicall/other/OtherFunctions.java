package com.multithreading.apicall.other;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.multithreading.apicall.model.Categories;



public class OtherFunctions {
	
	public String[] getSocialCount(Document doc) {
		Elements socialCount = doc.getElementsByClass("social-count");
		return socialCount.text().split(" ");
	}
	
	public String[] getExtraPostInfo(Document doc) {
		Elements commit = doc.select("ul.numbers-summary > li >a>span.num.text-emphasized");
		String arr[] = commit.text().split(" ");
		ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(arr));
		if(arrayList.size()==3)
			arrayList.add("1");
		return arrayList.toArray(arr);
	}
	
	public Element getReadMe(Document doc) {
		return doc.getElementById("readme");
	}
	
	public String getDescription(Document doc) {
		Elements description = doc.select("span[itemprop=about]");
		return description.text();	
	}
	
	public String getTitle(String url) {
		int lastSlash = url.lastIndexOf("/");
		String title = url.substring(lastSlash + 1, url.length());
		return title.contains("-")? toTitleCase(title.replaceAll("-", " ")): toTitleCase(title);
	}
	
	public String getCategories(Document doc) {
		Elements catArray = doc.getElementsByClass("topic-tag");
		String array[] = catArray.text().split(" ");
		String result = ""; 
		if (array.length > 0) { 
			StringBuilder sb = new StringBuilder(); 
				for (String s : array) { 
					sb.append(s).append(","); 
					} 
				result = sb.deleteCharAt(sb.length() - 1).toString(); 
				} 
		return result;		
	}
	
	public String toTitleCase(String input) {
	    StringBuilder titleCase = new StringBuilder(input.length());
	    boolean nextTitleCase = true;

	    for (char c : input.toCharArray()) {
	        if (Character.isSpaceChar(c)) {
	            nextTitleCase = true;
	        } else if (nextTitleCase) {
	            c = Character.toTitleCase(c);
	            nextTitleCase = false;
	        }

	        titleCase.append(c);
	    }

	    return titleCase.toString();
	}
	public String getExtraDetails(String urlText, String user, String repo) {
		System.setProperty("http.agent", "Chrome");
		String url = getJsonURL(urlText, user, repo);
		String badgeData = "";
		String label = "";
		String message = "";
		try {
			badgeData = IOUtils.toString(new URL(url));
			JSONObject badge = new JSONObject(badgeData);
			label = (String) badge.get("label");
			message = badge.getString("message");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return label+"-"+message;
	}
	
	public static String getJsonURL(String urlText, String user, String repo) {
		String url = "";
		switch(urlText) {
			case "language":
				url = String.format("https://img.shields.io/github/languages/top/%s/%s.json", user, repo);
				break;
			case "vulnerability":
				url = String.format("https://img.shields.io/snyk/vulnerabilities/github/%s/%s.json", user, repo);
				break;
			case "language_count":
				url = String.format("https://img.shields.io/github/languages/count/%s/%s.json", user, repo);
				break;
			case "dependencies":
				url = String.format("https://img.shields.io/librariesio/github/%s/%s.json", user, repo);
				break;
			case "code_size":
				url = String.format("https://img.shields.io/github/languages/code-size/%s/%s.json", user, repo);
				break;
			case "repo_size":
				url = String.format("https://img.shields.io/github/repo-size/%s/%s.json", user, repo);
				break;
			case "open_issues":
				url = String.format("https://img.shields.io/github/issues-raw/%s/%s.json", user, repo);
				break;
			case "close_issue":
				url = String.format("https://img.shields.io/github/issues-closed-raw/%s/%s.json", user, repo);
				break;
			case "license":
				url = String.format("https://img.shields.io/github/license/%s/%s.json", user, repo);
				break;
			case "commit_activity":
				url = String.format("https://img.shields.io/github/commit-activity/y/%s/%s.json", user, repo);
				break;
			case "last_commit":
				url = String.format("https://img.shields.io/github/last-commit/%s/%s.json", user, repo);
				break;
			case "release_commit":
				url = String.format("https://img.shields.io/github/release-date/%s/%s.json", user, repo);
				break;
			case "code_quality":
				url = String.format("https://img.shields.io/coveralls/github/%s/%s.json", user, repo);
				break;
			default:
				url = "";
				break;
				
		}
		return url;
	}
	
}
