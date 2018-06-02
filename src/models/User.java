package models;

import java.sql.Date;

public class User {

	private int id;
	private String username;
	private String password;
	private String email;
	private int cityid;
	private int sportsid;
	private String age;
	private int universityid;
	private String points;
	private int rankid;
	private String startdate;
	private String usercol;
	private String role;
	private String profile_image;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCityid() {
		return cityid;
	}

	public void setCityid(int cityid) {
		this.cityid = cityid;
	}

	public int getSportsid() {
		return sportsid;
	}

	public void setSportsid(int sportsid) {
		this.sportsid = sportsid;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public int getUniversityid() {
		return universityid;
	}

	public void setUniversityid(int universityid) {
		this.universityid = universityid;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public int getRankid() {
		return rankid;
	}

	public void setRankid(int rankid) {
		this.rankid = rankid;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getUsercol() {
		return usercol;
	}

	public void setUsercol(String usercol) {
		this.usercol = usercol;
	}

	public String getProfile_image() {
		return profile_image;
	}

	public void setProfile_image(String profile_image) {
		this.profile_image = profile_image;
	}

	
}
