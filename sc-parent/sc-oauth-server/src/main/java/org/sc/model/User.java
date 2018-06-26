package org.sc.model;



import lombok.Data;

@Data
public class User {
	private String id;
	/**
     * 用户名，登陆名
     */
    private String userName;
    
    /**
     * 密码
     */
    private String password;
}
