package org.sc.configuration;



import lombok.Data;

@Data
public class Response<T> {
	
	private int code;
	private String message;
	private T data;
	
	public Response(){
		this.code = 0;
		this.message = "success";
	}
	
	public Response(String message, Integer code){
		this.code = code;
		this.message = message;
	}
	
	public Response(T data, String message, Integer code){
		this.code = code;
		this.message = message;
		this.data = data;
	}
}
