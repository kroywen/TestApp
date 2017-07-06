package com.fourpay.testapp.ui.main.view;

import com.fourpay.testapp.data.model.Transaction;
import com.fourpay.testapp.ui.common.MvpView;

import java.util.List;

public interface MainView extends MvpView {

	void showHistory(List<Transaction> transactions);

	void showEmpty();

	void showError(String error);

}