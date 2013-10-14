package com.mobile.system;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class C2dmService {

	private Context context;
	private Intent regiService;

	public C2dmService(Context context) {
		this.context = context;

	}

	public void init() {
		regiService = new Intent("com.google.android.c2dm.intent.REGISTER");
		regiService.putExtra("app",
				PendingIntent.getBroadcast(context, 0, new Intent(), 0)); // boilerplate
		regiService.putExtra("sender", "kgitae7@gmail.com");
		context.startService(regiService);
	}

	public void sender(String regId, String msg)
			throws Exception {
		
		String authToken = getAuthToken();
		
		StringBuffer postDataBuilder = new StringBuffer();

		postDataBuilder.append("registration_id=" + regId); // �깅줉ID
		postDataBuilder.append("&collapse_key=1");
		postDataBuilder.append("&delay_while_idle=1");
		postDataBuilder.append("&data.msg=" + URLEncoder.encode(msg, "UTF-8")); // �쒖슱
																				// 硫붿떆吏�

		byte[] postData = postDataBuilder.toString().getBytes("UTF8");

		URL url = new URL("https://android.apis.google.com/c2dm/send");

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length",
				Integer.toString(postData.length));
		conn.setRequestProperty("Authorization", "GoogleLogin auth="
				+ authToken);

		OutputStream out = conn.getOutputStream();
		out.write(postData);
		out.close();

		conn.getInputStream();

	}

	public String getAuthToken() throws Exception {
		String authtoken = "";

		StringBuffer postDataBuilder = new StringBuffer();
		postDataBuilder.append("accountType=HOSTED_OR_GOOGLE"); // �묎컳���⑥＜�붿빞 �⑸땲��
		postDataBuilder.append("&Email=kgitae7@gmail.com"); // 媛쒕컻��援ш� id
		postDataBuilder.append("&Passwd=Kitae1780711"); // 媛쒕컻��援ш� 鍮꾨퉴踰덊샇
		postDataBuilder.append("&service=ac2dm");
		postDataBuilder.append("&source=test-1.0");

		byte[] postData = postDataBuilder.toString().getBytes("UTF8");

		URL url = new URL("https://www.google.com/accounts/ClientLogin");

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length",
				Integer.toString(postData.length));

		OutputStream out = conn.getOutputStream();
		out.write(postData);
		out.close();

		BufferedReader br = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));

		String sidLine = br.readLine();
		String lsidLine = br.readLine();
		String authLine = br.readLine();

		System.out.println("sidLine----------->>>" + sidLine);
		System.out.println("lsidLine----------->>>" + lsidLine);
		System.out.println("authLine----------->>>" + authLine);
		System.out.println("AuthKey----------->>>"
				+ authLine.substring(5, authLine.length()));

		authtoken = authLine.substring(5, authLine.length());

		return authtoken;
	}

}
