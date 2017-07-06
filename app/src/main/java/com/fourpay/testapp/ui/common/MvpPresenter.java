package com.fourpay.testapp.ui.common;

public interface MvpPresenter<V extends MvpView> {

	void attachView(V view);

	void detachView();

}