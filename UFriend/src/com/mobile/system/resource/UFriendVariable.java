package com.mobile.system.resource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import com.mobile.system.utils.Face3Utils;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: 11. 9. 19 Time: 오후 6:22
 * To change this template use File | Settings | File Templates.
 */
public class UFriendVariable {

	public static final String SERVER_SUCCESS_STRING = "SUCCESS";
	 //public static final String WEB_SERVER_URL = "117.16.200.243";
	public static final String WEB_SERVER_URL = "192.168.123.104";
	//public static final String WEB_SERVER_URL = "192.168.0.228";
//	public static final String WEB_SERVER_URL = "10.80.41.229";
	public static final String WEB_SERVER_CONTEXT_PATH = "ufriend_web";
	 //public static final String WEB_SERVER_PORT = "80";
	public static final String WEB_SERVER_PORT = "8080";
	public static final String DEV_MODE = "vm_";
	public static final String ID_PROPERTY = "student_id";
	public static final String IS_LOGIN = "is_login";
	public static final String YEAR_PROPERTY = "cur_year";
	public static final String TERM_PROPERTY = "cur_term";
	public static final String PASSWORD_PROPERTY = "student_password";
	public static final int DISPLAY_HEIGHT = 800;
	public static final int DISPLAY_WIDTH = 480;
	public static final int DISPLAY_HEADER_HEIGHT = 96;
	public static final int SOCK_TIMEOUT = 10000;
	public static final int LONG_SOCK_TIMEOUT = 50000;

	public static final String getServerHttpUrl(String strProgUrl) {
		if (strProgUrl.charAt(0) == '/') {
			return getServerHttpUrl() + strProgUrl;
		} else {
			return getServerHttpUrl() + "/" + strProgUrl;
		}
	}

	public static final String getServerHttpUrl() {
		return "http://" + WEB_SERVER_URL + ":" + WEB_SERVER_PORT + "/"
				+ WEB_SERVER_CONTEXT_PATH;

	}
	

	public static final String getVersionName(Context context) {
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return pi.versionName;
		} catch (NameNotFoundException e) {
			return null;
		}
	}
	


}
