package com.mobile.system.lib;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;
/*
		Intent intent = new Intent(context, activity.class);
		ParcelableMap parcelableMap = new ParcelableMap(map);
		intent.putExtra("parcelableMAp", parcelableMap);
		startActivity(intent);

 */
public class ParcelableMap implements Parcelable {
	private Map<String, Object> map;

	public ParcelableMap(Map<String, Object> map) {
		// TODO Auto-generated constructor stub
		this.map = map;
	}

	public ParcelableMap() {

	}
	
	public ParcelableMap(Parcel in){
		readFromParcel(in);
	}

	private void readFromParcel(Parcel in) {
		// TODO Auto-generated method stub
		map = in.readHashMap(ParcelableMap.class.getClassLoader());
	}

	public Map<String, Object> getUserMap() {
		return map;
	}

	public void setUserMap(Map<String, Object> userMap) {
		this.map = userMap;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeMap(map);

	}

	public static final Parcelable.Creator<ParcelableMap> CREATOR = new Parcelable.Creator<ParcelableMap>() {

		@Override
		public ParcelableMap createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new ParcelableMap(source);
		}

		@Override
		public ParcelableMap[] newArray(int size) {
			// TODO Auto-generated method stub
			return new ParcelableMap[size];
		}
	};

}
