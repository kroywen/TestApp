package com.fourpay.testapp.ui.main.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fourpay.testapp.R;
import com.fourpay.testapp.data.model.Transaction;
import com.fourpay.testapp.ui.common.DatePickerFragment;
import com.fourpay.testapp.ui.main.presenter.MainPresenter;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {

	@BindView(R.id.dateFromBtn) Button mDateFromBtn;
	@BindView(R.id.dateToBtn) Button mDateToBtn;
	@BindView(R.id.transactionsListView) RecyclerView mTransactionsListView;

	private int mUserId = 1; // should be real user id, returned from service
	private Date mDateFrom;
	private Date mDateTo;

	private MainPresenter mPresenter;
	private TransactionsAdapter mAdapter;
	private DateFormat mDateFormat = new DateFormat();
	private MaterialDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
		initRecyclerView();

		mDateTo = new Date(); // current time
		mDateFrom = new Date(mDateTo.getTime() - 3600 * 1000); // one day back

		mPresenter = new MainPresenter();
		mPresenter.attachView(this);

		mAdapter = new TransactionsAdapter(this);
	}

	private void initRecyclerView() {
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		mTransactionsListView.setLayoutManager(layoutManager);
		mTransactionsListView.setHasFixedSize(true);
		mTransactionsListView.addItemDecoration(
				new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
		mTransactionsListView.setAdapter(mAdapter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mPresenter.detachView();
	}

	@OnClick(R.id.dateFromBtn)
	public void showPickDateFromDialog() {
		DatePickerFragment fragment = DatePickerFragment.newInstance(mDateFrom);
		fragment.setOnDateSetListener((view, year, month, dayOfMonth) -> {
			mDateFrom.setYear(year - 1900);
			mDateFrom.setMonth(month);
			mDateFrom.setDate(dayOfMonth);
			mDateFromBtn.setText(mDateFormat.format("yyyy-MM-dd", mDateFrom));
		});
		fragment.show(getSupportFragmentManager(), "dateFromPicker");
	}

	@OnClick(R.id.dateToBtn)
	public void showPickDateToDialog() {
		DatePickerFragment fragment = DatePickerFragment.newInstance(mDateTo);
		fragment.setOnDateSetListener((view, year, month, dayOfMonth) -> {
			mDateTo.setYear(year - 1900);
			mDateTo.setMonth(month);
			mDateTo.setDate(dayOfMonth);
			mDateToBtn.setText(mDateFormat.format("yyyy-MM-dd", mDateTo));
		});
		fragment.show(getSupportFragmentManager(), "dateToPicker");
	}

	@OnClick(R.id.sendBtn)
	public void loadHistory() {
		mPresenter.loadHistory(
				String.valueOf(mUserId),
				mDateFormat.format("yyyy-MM-dd", mDateFrom).toString(),
				mDateFormat.format("yyyy-MM-dd", mDateTo).toString()
		);
		showProgress();
	}

	private void showProgress() {
		hideProgress();
		mProgressDialog = new MaterialDialog.Builder(this)
				.content(R.string.please_wait)
				.progress(true, 0)
				.show();
	}

	private void hideProgress() {
		if (mProgressDialog != null)
			mProgressDialog.dismiss();
	}

	@Override
	public void showHistory(List<Transaction> transactions) {
		hideProgress();
		mAdapter.setTransactions(transactions);
		mTransactionsListView.setAdapter(mAdapter);
	}

	@Override
	public void showEmpty() {
		hideProgress();
		Toast.makeText(this, R.string.no_history, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void showError(String error) {
		hideProgress();
		Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
	}

}