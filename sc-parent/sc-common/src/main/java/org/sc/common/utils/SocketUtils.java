package org.sc.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Socket工具类
 * @author DongWenBin
 *
 */
public class SocketUtils {
	/**
	 * 连接并发送消息
	 * @param host
	 * @param port
	 * @param message
	 * @return
	 * @throws Exception
	 */
	public static String connectSend(String host, int port, String message) throws Exception {
		Socket socket = null;
		try {
			socket = new Socket(host, port);
			socket.setSendBufferSize(4096);
			socket.setSoTimeout(10000);
			PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"GBK"),true);
			output.write(message);
			output.flush();
			socket.shutdownOutput();
			
			InputStream in = socket.getInputStream();
			String res = readStream(in);
			socket.shutdownInput();
			return res;
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	private static String readStream(InputStream inStream) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "GBK"));
		StringBuilder sb = new StringBuilder();
		String txt = null;
		while ((txt = reader.readLine()) != null) {
			sb.append(txt);
		}
		return sb.toString();
	}
	
	private SocketUtils() {}
	
	public static void main(String[] args) throws Exception {
		String res = SocketUtils.connectSend("127.0.0.1", 10001, "法拉利");
		System.out.println(res);
	}
}
