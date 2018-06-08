package org.sc.exception;

public class MyLoginException extends RuntimeException {
	private static final long serialVersionUID = -6803675507874008949L;

	public MyLoginException(String msg) {
		super(msg);
	}

	public MyLoginException(String msg, Throwable t) {
		super(msg, t);
	}
}
