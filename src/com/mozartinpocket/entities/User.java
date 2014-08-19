package com.mozartinpocket.entities;

public class User {
	private String username=null;
	private String password=null;
	private String name=null;
	private int age=0;
	private String gender=null;
	private String email=null;
	private String interestsTag=null;
	private String myMusicStyle=null;
	private String photoFilename = null;

	public void setUsername(String username){
		this.username=username;
	}
	public String getUsername(){
		return username;
	}
	public void setPassword(String password){
		this.password=password;
	}
	public String getPassword(){
		return password;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setAge(int age){
		this.age=age;
	}
	public int getAge(){
		return age;
	}
	public void setGender(String gender){
		this.gender=gender;
	}
	public String getGender(){
		return gender;
	}
	public void setEmail(String email){
		this.email=email;
	}
	public String getEmail(){
		return email;
	}
	public void setInterestsTag(String interestsTag){
		this.interestsTag=interestsTag;
	}
	public String getInterestsTag(){
		return interestsTag;
	}
	public void setMyMusicStyle(String myMusicStyle){
		this.myMusicStyle=myMusicStyle;
	}
	public String getMyMusicStyle(){
		return myMusicStyle;
	}
	public void setPhotoFilename(String photoFilename){
		this.photoFilename=photoFilename;
	}
	public String getPhotoFilename(){
		return photoFilename;
	}
}
