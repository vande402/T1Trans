package com.org.scube.dashboard.dto;

public class UserLoginDataDto {

	private String name;
	
	private String password;

	public String getName() {
		System.out.println("name=====getName==="+name);
		return name;
	}

	public void setName(String name) {
		System.out.println("name======setName=="+name);
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
