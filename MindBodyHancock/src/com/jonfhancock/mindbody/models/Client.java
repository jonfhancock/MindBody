package com.jonfhancock.mindbody.models;

import android.content.ContentProviderOperation;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;
import com.jonfhancock.mindbody.data.provider.MindBodyContent.Appointment.Columns;

@XMLObject("//Client")
public class Client implements Parcelable {

	private static final String FIELD_IS_PROSPECT = "IsProspect";
	private static final String FIELD_ID = "ID";
	private static final String FIELD_FIRST_NAME = "FirstName";
	private static final String FIELD_FIRST_APPOINTMENT_DATE = "FirstAppointmentDate";
	private static final String FIELD_LAST_NAME = "LastName";

	@XMLField(FIELD_IS_PROSPECT)
	private boolean mIsProspect;
	@XMLField(FIELD_ID)
	private long mID;
	@XMLField(FIELD_FIRST_NAME)
	private String mFirstName;
	@XMLField(FIELD_FIRST_APPOINTMENT_DATE)
	private String mFirstAppointmentDate;
	@XMLField(FIELD_LAST_NAME)
	private String mLastName;

	public Client() {

	}

	public void setIsProspect(boolean isProspect) {
		mIsProspect = isProspect;
	}

	public boolean getIsProspect() {
		return mIsProspect;
	}

	public void setID(long iD) {
		mID = iD;
	}

	public long getID() {
		return mID;
	}

	public void setFirstName(String firstName) {
		mFirstName = firstName;
	}

	public String getFirstName() {
		return mFirstName;
	}

	public void setFirstAppointmentDate(String firstAppointmentDate) {
		mFirstAppointmentDate = firstAppointmentDate;
	}

	public String getFirstAppointmentDate() {
		return mFirstAppointmentDate;
	}

	public void setLastName(String lastName) {
		mLastName = lastName;
	}

	public String getLastName() {
		return mLastName;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Client) {
			return ((Client) obj).getID() == mID;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return ((Long) mID).hashCode();
	}

	public Client(Parcel in) {
		mIsProspect = in.readInt() == 1;
		mID = in.readLong();
		mFirstName = in.readString();
		mFirstAppointmentDate = in.readString();
		mLastName = in.readString();
	}

	public Client(Cursor c) {
		mID = c.getLong(Columns.CLIENT_ID.getIndex());
		mIsProspect = c.getInt(Columns.CLIENT_IS_PROSPECT.getIndex()) == 1;
		mFirstName = c.getString(Columns.CLIENT_FIRST_NAME.getIndex());
		mLastName = c.getString(Columns.CLIENT_LAST_NAME.getIndex());
		mFirstAppointmentDate = c.getString(Columns.CLIENT_FIRST_APPT_DATE
				.getIndex());
	}

	public ContentProviderOperation.Builder addValues(
			ContentProviderOperation.Builder builder) {
		builder.withValue(Columns.CLIENT_ID.getName(), mID)
				.withValue(Columns.CLIENT_IS_PROSPECT.getName(),
						mIsProspect ? 1 : 0)
				.withValue(Columns.CLIENT_FIRST_NAME.getName(), mFirstName)
				.withValue(Columns.CLIENT_LAST_NAME.getName(), mLastName)
				.withValue(Columns.CLIENT_FIRST_APPT_DATE.getName(),
						mFirstAppointmentDate);
		return builder;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Parcelable.Creator<Client> CREATOR = new Parcelable.Creator<Client>() {
		public Client createFromParcel(Parcel in) {
			return new Client(in);
		}

		public Client[] newArray(int size) {
			return new Client[size];
		}
	};

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mIsProspect ? 1 : 0);
		dest.writeLong(mID);
		dest.writeString(mFirstName);
		dest.writeString(mFirstAppointmentDate);
		dest.writeString(mLastName);
	}

}