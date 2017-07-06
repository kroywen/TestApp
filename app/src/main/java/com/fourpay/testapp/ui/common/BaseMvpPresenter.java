package com.fourpay.testapp.ui.common;

import io.reactivex.disposables.Disposable;

public class BaseMvpPresenter<V extends MvpView> implements MvpPresenter<V> {

	private V mView;

	@Override
	public void attachView(V view) {
		mView = view;
	}

	@Override
	public void detachView() {
		mView = null;
	}

	public boolean isViewAttached() {
		return mView != null;
	}

	public V getView() {
		return mView;
	}

	public void checkViewAttached() {
		if (!isViewAttached())
			throw new MvpViewNotAttachedException();
	}

	protected void unsubscribe(Disposable disposable) {
		if (disposable != null && !disposable.isDisposed())
			disposable.dispose();
	}

}