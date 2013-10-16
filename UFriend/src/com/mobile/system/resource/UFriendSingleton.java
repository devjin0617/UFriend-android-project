package com.mobile.system.resource;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by IntelliJ IDEA. User: Administrator Date: 11. 9. 19 Time: 오후 6:21
 * To change this template use File | Settings | File Templates.
 */
public class UFriendSingleton {

    private static com.mobile.system.resource.UFriendSingleton instance = new com.mobile.system.resource.UFriendSingleton();

    public static com.mobile.system.resource.UFriendSingleton getInstance() {
        return instance;
    }

    private UFriendSingleton() {
        //mainUiController = new MainUiController();
        uFriendVariable = new UFriendVariable();
        resourceManger = new ResourceManger();
        dbResource = new DbResource();



    }
    //private MainUiController mainUiController;
    private UserInfo userInfo;
    private DbResource dbResource;
    private UFriendVariable uFriendVariable;
    private ResourceManger resourceManger;
    private HashMap<String, Object> requestMap;
    private Context mainContext;
    //private CommonActivity currentActivity;
    //private StackView mainContents;
    private SharedPreferences sharedPreference;

    public SharedPreferences getSharedPreference() {
        return sharedPreference;
    }

    public void setSharedPreference(SharedPreferences sharedPreference) {
        this.sharedPreference = sharedPreference;
    }
    /*
    public StackView getMainContents() {
        return mainContents;
    }

    public void setMainContents(StackView mainContents) {
        this.mainContents = mainContents;
    }

    public CommonActivity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(CommonActivity currentActivity) {
        this.currentActivity = currentActivity;
    }
     */
    public Context getMainContext() {
        return mainContext;
    }

    public void setMainContext(Context mainContext) {
        this.mainContext = mainContext;
    }

    public HashMap<String, Object> getRequestMap() {
        requestMap = new HashMap<String, Object>();
        requestMap.put("student_no", userInfo.getStudent_no());
        requestMap.put("user_id", userInfo.getUser_id());

        return requestMap;

    }
    /*
    public MainUiController getMainUiController() {
        return mainUiController;
    }
	*/
    public ResourceManger getResourceManger() {
        return resourceManger;
    }

    public void setResourceManger(ResourceManger resourceManger) {
        this.resourceManger = resourceManger;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public DbResource getDbResource() {
        return dbResource;
    }

    public void setDbResource(DbResource dbResource) {
        this.dbResource = dbResource;
    }

    public UFriendVariable getSmartCampusVariable() {
        return uFriendVariable;
    }

    public void setUFriendVariable(UFriendVariable uFriendVariable) {
        this.uFriendVariable = uFriendVariable;
    }
}
