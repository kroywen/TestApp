package com.fourpay.testapp.ui.common;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment {

	public static final String KEY_SELECTED_DATE = "selected_date";

	private DatePickerDialog.OnDateSetListener mListener;
	private Date mSelectedDate;

	public static DatePickerFragment newInstance(Date selectedDate) {
		DatePickerFragment fragment = new DatePickerFragment();
		Bundle args = new Bundle();
		args.putSerializable(KEY_SELECTED_DATE, selectedDate);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle args = getArguments();
		if (args != null) {
			mSelectedDate = args.containsKey(KEY_SELECTED_DATE) ?
					(Date) args.getSerializable(KEY_SELECTED_DATE) : new Date();
		}
	}

	public void setOnDateSetListener(DatePickerDialog.OnDateSetListener listener) {
		mListener = listener;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		return new DatePickerDialog(getActivity(), mListener, year, month, day);
	}

}