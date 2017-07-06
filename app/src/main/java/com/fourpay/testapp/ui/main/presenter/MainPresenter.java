package com.fourpay.testapp.ui.main.presenter;

import com.fourpay.testapp.TestApp;
import com.fourpay.testapp.data.model.Transaction;
import com.fourpay.testapp.data.network.ApiService;
import com.fourpay.testapp.ui.common.BaseMvpPresenter;
import com.fourpay.testapp.ui.main.view.MainView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter extends BaseMvpPresenter<MainView> {

	private Disposable mDisbosable;
	private ApiService mApiService;

	public MainPresenter() {
		mApiService = TestApp.getInstance().getApiService();
	}

	@Override
	public void detachView() {
		super.detachView();
		unsubscribe(mDisbosable);
	}

	public void loadHistory(String userId, String dateFrom, String dateTo) {
		checkViewAttached();
		unsubscribe(mDisbosable);

		mDisbosable = mApiService.getHistory(userId, dateFrom, dateTo)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeWith(createObserver());
	}

	private DisposableObserver<List<Transaction>> createObserver() {
		return new DisposableObserver<List<Transaction>>() {

			@Override
			public void onNext(@NonNull List<Transaction> transactions) {
				if (transactions != null && !transactions.isEmpty()) {
					getView().showHistory(transactions);
				} else {
					getView().showEmpty();
				}
			}

			@Override
			public void onComplete() {}

			@Override
			public void onError(@NonNull Throwable e) {
				getView().showError(e.getMessage());
			}

		};
	}

}