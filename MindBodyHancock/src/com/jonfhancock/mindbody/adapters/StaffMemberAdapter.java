package com.jonfhancock.mindbody.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jonfhancock.mindbody.R;
import com.jonfhancock.mindbody.models.Staff;
import com.jonfhancock.mindbody.util.CropSquareTransformation;
import com.squareup.picasso.Picasso;

public class StaffMemberAdapter extends CursorAdapter {
	private LayoutInflater mInflater;
	private Picasso mPicasso;

	public StaffMemberAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		mInflater = LayoutInflater.from(context);
		mPicasso = Picasso.with(context);
	}


	@Override
	public Staff getItem(int position) {
		return new Staff((Cursor) super.getItem(position));
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		Staff staff = new Staff(cursor);
		ImageView avatar = (ImageView) view.findViewById(R.id.image_staff_avatar);
		TextView name = (TextView) view.findViewById(R.id.text_staff_name);
		if (staff.getImageURL() != null) {
			mPicasso.load(Uri.parse(staff.getImageURL()))
					.transform(new CropSquareTransformation())
					.placeholder(R.drawable.ic_default_photo).into(avatar);
		}
		name.setText(staff.getName());
	}

	@Override
	public View newView(Context context, Cursor cursror, ViewGroup parent) {
		ViewGroup view = (ViewGroup) mInflater.inflate(R.layout.item_staff,
				null, false);

		return view;
	}

}