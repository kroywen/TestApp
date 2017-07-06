package com.fourpay.testapp.ui.main.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fourpay.testapp.R;
import com.fourpay.testapp.data.model.Transaction;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private Context mContext;
	private List<Transaction> mTransactions;

	public TransactionsAdapter(Context context) {
		mContext = context;
		mTransactions = new ArrayList<>();
	}

	public void setTransactions(List<Transaction> transactions) {
		mTransactions = transactions;
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_list_item, parent, false);
		return new TransactionViewHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if (holder instanceof TransactionViewHolder) {
			TransactionViewHolder viewHolder = (TransactionViewHolder) holder;
			Transaction transaction = mTransactions.get(position);

			viewHolder.amount.setText(mContext.getString(R.string.amount, String.valueOf(transaction.getAmount())));
			viewHolder.debit.setText(mContext.getString(R.string.debit, transaction.getDebit()));
			viewHolder.credit.setText(mContext.getString(R.string.credit, transaction.getCredit()));
			viewHolder.date.setText(mContext.getString(R.string.date, transaction.getDate()));
		}
	}

	@Override
	public int getItemCount() {
		return mTransactions.size();
	}


	static class TransactionViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.amount) TextView amount;
		@BindView(R.id.debit) TextView debit;
		@BindView(R.id.credit) TextView credit;
		@BindView(R.id.date) TextView date;

		public TransactionViewHolder(View view) {
			super(view);
			ButterKnife.bind(this, view);
		}

	}

}