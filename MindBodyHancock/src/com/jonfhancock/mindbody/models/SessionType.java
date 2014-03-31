package com.jonfhancock.mindbody.models;

import android.content.ContentProviderOperation.Builder;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;
import com.jonfhancock.mindbody.data.provider.MindBodyContent.Appointment.Columns;

@XMLObject("//SessionType")
public class SessionType implements Parcelable {

	private static final String FIELD_ID = "ID";
	private static final String FIELD_DEFAULT_TIME_LENGTH = "DefaultTimeLength";
	private static final String FIELD_PROGRAM_ID = "ProgramID";
	private static final String FIELD_NAME = "Name";

	@XMLField(FIELD_ID)
	private long mID;
	@XMLField(FIELD_DEFAULT_TIME_LENGTH)
	private int mDefaultTimeLength;
	@XMLField(FIELD_PROGRAM_ID)
	private long mProgramID;
	@XMLField(FIELD_NAME)
	private String mName;

	public SessionType() {

	}

	public void setID(long iD) {
		mID = iD;
	}

	public long getID() {
		return mID;
	}

	public void setDefaultTimeLength(int defaultTimeLength) {
		mDefaultTimeLength = defaultTimeLength;
	}

	public int getDefaultTimeLength() {
		return mDefaultTimeLength;
	}

	public void setProgramID(long programID) {
		mProgramID = programID;
	}

	public long getProgramID() {
		return mProgramID;
	}

	public void setName(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SessionType) {
			return ((SessionType) obj).getID() == mID;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return ((Long) mID).hashCode();
	}

	public SessionType(Parcel in) {
		mID = in.readLong();
		mDefaultTimeLength = in.readInt();
		mProgramID = in.readLong();
		mName = in.readString();
	}

	public SessionType(Cursor c) {
		mID = c.getLong(Columns.SESSION_TYPE_ID.getIndex());
		mDefaultTimeLength = c.getInt(Columns.SESSION_TYPE_DEFAULT_TIME
				.getIndex());
		mProgramID = c.getLong(Columns.SESSION_TYPE_PROGRAM_ID.getIndex());
		mName = c.getString(Columns.SESSION_TYPE_NAME.getIndex());
	}

	public Builder addValues(Builder builder) {
		builder.withValue(Columns.SESSION_TYPE_ID.getName(), mID)
				.withValue(Columns.SESSION_TYPE_DEFAULT_TIME.getName(),
						mDefaultTimeLength)
				.withValue(Columns.SESSION_TYPE_PROGRAM_ID.getName(),
						mProgramID)
				.withValue(Columns.SESSION_TYPE_NAME.getName(), mName);
		return builder;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Parcelable.Creator<SessionType> CREATOR = new Parcelable.Creator<SessionType>() {
		public SessionType createFromParcel(Parcel in) {
			return new SessionType(in);
		}

		public SessionType[] newArray(int size) {
			return new SessionType[size];
		}
	};

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(mID);
		dest.writeInt(mDefaultTimeLength);
		dest.writeLong(mProgramID);
		dest.writeString(mName);
	}

}