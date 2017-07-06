package com.fourpay.testapp;

import android.app.Application;

import com.fourpay.testapp.data.network.ApiService;

public class TestApp extends Application {

	private static TestApp mInstance;
	private ApiService mApiService;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}

	public static TestApp getInstance() {
		return mInstance;
	}

	public ApiService getApiService() {
		if (mApiService == null)
			mApiService = ApiService.Creator.newApiService();
		return mApiService;
	}

}