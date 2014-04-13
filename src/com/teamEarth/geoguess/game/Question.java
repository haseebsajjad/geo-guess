package com.teamEarth.geoguess.game;

public class Question {
	public String image, url, ans, ch1, ch2, ch3, ch4;
	int correctChoice;
	public void fillQuestion(String image, String url, String ans){
		this.image=image;
		this.url=url;
		this.ans=ans;
	}
	
	public void fillChoices(String ch1, String ch2, String ch3, String ch4){
		this.ch1=ch1;
		this.ch2=ch2;
		this.ch3=ch3;
		this.ch4=ch4;
	}
	
	public void clearQuestion(){
		image="";
		url="";
		ans="";
		ch1="";
		ch2="";
		ch3="";
		ch4="";
	}
}
