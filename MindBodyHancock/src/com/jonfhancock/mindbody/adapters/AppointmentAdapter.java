package com.jonfhancock.mindbody.adapters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jonfhancock.mindbody.R;
import com.jonfhancock.mindbody.models.Appointment;

@SuppressLint("SimpleDateFormat")
public class AppointmentAdapter extends CursorAdapter {
	private LayoutInflater mInflater;
	private DateFormat mStartDateFormat = new SimpleDateFormat(
			"EEEE M/d/yyyy, h:mma");
	private DateFormat mEndDateFormat = new SimpleDateFormat(" '-' h:mma");
	private DateFormat mInputFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:SS");

	public AppointmentAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public Appointment getItem(int position) {
		return new Appointment((Cursor) super.getItem(position));
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		Appointment appt = new Appointment(cursor);

		TextView tvDate = (TextView) view.findViewById(R.id.text_appt_date);
		TextView client = (TextView) view.findViewById(R.id.text_client);
		TextView type = (TextView) view.findViewById(R.id.text_appt_type);

		try {
			Date startDate = mInputFormat.parse(appt.getStartDateTime());
			Date endDate = mInputFormat.parse(appt.getEndDateTime());

			tvDate.setText(mStartDateFormat.format(startDate)
					+ mEndDateFormat.format(endDate));

		} catch (ParseException e) {
			// If we can't parse the date, then we just won't show it.
			tvDate.setText(R.string.tbd);

		}

		client.setText(context.getString(R.string.client)
				+ appt.getClientName());
		type.setText(appt.getSessionName());

	}

	@Override
	public View newView(Context context, Cursor cursror, ViewGroup parent) {
		ViewGroup view = (ViewGroup) mInflater.inflate(R.layout.item_appt,
				null, false);

		return view;
	}

}