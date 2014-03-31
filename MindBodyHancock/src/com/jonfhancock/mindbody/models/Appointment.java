package com.jonfhancock.mindbody.models;

import android.content.ContentProviderOperation;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;
import com.jonfhancock.mindbody.data.provider.MindBodyContent.Appointment.Columns;

@XMLObject("//Appointments/Appointment")
public class Appointment implements Parcelable {

	private static final String FIELD_STATUS = "Status";
	private static final String FIELD_STAFF_REQUESTED = "StaffRequested";
	private static final String FIELD_CLIENT = "Client";
	private static final String FIELD_END_DATE_TIME = "EndDateTime";
	private static final String FIELD_RESOURCES = "Resources";
	private static final String FIELD_LOCATION = "Location";
	private static final String FIELD_SESSION_TYPE = "SessionType";
	private static final String FIELD_PROGRAM = "Program";
	private static final String FIELD_DURATION = "Duration";
	private static final String FIELD_ID = "ID";
	private static final String FIELD_FIRST_APPOINTMENT = "FirstAppointment";
	private static final String FIELD_START_DATE_TIME = "StartDateTime";
	private static final String FIELD_NOTES = "Notes";
	private static final String FIELD_STAFF = "Staff";

	@XMLField(FIELD_STATUS)
	private String mStatus;
	@XMLField(FIELD_STAFF_REQUESTED)
	private String mStaffRequested;
	@XMLField(FIELD_CLIENT)
	private Client mClient;
	@XMLField(FIELD_END_DATE_TIME)
	private String mEndDateTime;
	@XMLField(FIELD_RESOURCES)
	private String mResource;
	@XMLField(FIELD_LOCATION)
	private Location mLocation;
	@XMLField(FIELD_SESSION_TYPE)
	private SessionType mSessionType;
	@XMLField(FIELD_PROGRAM)
	private Program mProgram;
	@XMLField(FIELD_ID)
	private long mID;
	@XMLField(FIELD_FIRST_APPOINTMENT)
	private boolean mFirstAppointment;
	@XMLField(FIELD_START_DATE_TIME)
	private String mStartDateTime;
	@XMLField(FIELD_NOTES)
	private String mNote;
	@XMLField(FIELD_STAFF)
	private Staff mStaff;
	@XMLField(FIELD_DURATION)
	private int mDuration;

	public Appointment() {

	}

	public Appointment(Cursor c) {
		mID = c.getLong(Columns.ID.getIndex());
		mStatus = c.getString(Columns.STATUS.getIndex());
		mStaffRequested = c.getString(Columns.STAFF_REQUESTED.getIndex());
		mNote = c.getString(Columns.NOTES.getIndex());
		mResource = c.getString(Columns.RESOURCE.getIndex());
		mFirstAppointment = c.getInt(Columns.FIRST_APPOINTMENT.getIndex()) == 1;
		mStartDateTime = c.getString(Columns.START_TIME.getIndex());
		mEndDateTime = c.getString(Columns.END_TIME.getIndex());
		mClient = new Client(c);
		mLocation = new Location(c);
		mProgram = new Program(c);
		mSessionType = new SessionType(c);
		mStaff = new Staff();
		mStaff.setId(c.getLong(Columns.STAFF.getIndex()));

	}

	public ContentProviderOperation getUpdateOperation(Uri uri) {
		ContentProviderOperation.Builder builder = ContentProviderOperation
				.newUpdate(uri);
		return getOperation(builder);
	}

	public ContentProviderOperation getInsertOperation(Uri uri) {
		ContentProviderOperation.Builder builder = ContentProviderOperation
				.newInsert(uri);
		return getOperation(builder);
	}

	private ContentProviderOperation getOperation(
			ContentProviderOperation.Builder builder) {
		builder.withValue(Columns.ID.getName(), mID)
				.withValue(Columns.STATUS.getName(), mStatus)
				.withValue(Columns.STAFF_REQUESTED.getName(), mStaffRequested)
				.withValue(Columns.NOTES.getName(), mNote)
				.withValue(Columns.RESOURCE.getName(), mResource)
				.withValue(Columns.FIRST_APPOINTMENT.getName(),
						mFirstAppointment)
				.withValue(Columns.START_TIME.getName(), mStartDateTime)
				.withValue(Columns.END_TIME.getName(), mEndDateTime)
				.withValue(Columns.STAFF.getName(), mStaff.getId());
		builder = mClient.addValues(builder);
		builder = mLocation.addValues(builder);
		builder = mProgram.addValues(builder);
		builder = mSessionType.addValues(builder);
		
		return builder.build();
	}

	public void setStatus(String status) {
		mStatus = status;
	}

	public String getStatus() {
		return mStatus;
	}

	public void setStaffRequested(String staffRequested) {
		mStaffRequested = staffRequested;
	}

	public String getStaffRequested() {
		return mStaffRequested;
	}

	public void setClient(Client client) {
		mClient = client;
	}

	public Client getClient() {
		return mClient;
	}

	public void setEndDateTime(String endDateTime) {
		mEndDateTime = endDateTime;
	}

	public String getEndDateTime() {
		return mEndDateTime;
	}

	public void setResource(String resource) {
		mResource = resource;
	}

	public String getResource() {
		return mResource;
	}

	public void setLocation(Location location) {
		mLocation = location;
	}

	public Location getLocation() {
		return mLocation;
	}

	public void setSessionType(SessionType sessionType) {
		mSessionType = sessionType;
	}

	public SessionType getSessionType() {
		return mSessionType;
	}

	public void setProgram(Program program) {
		mProgram = program;
	}

	public Program getProgram() {
		return mProgram;
	}

	public void setID(long iD) {
		mID = iD;
	}

	public long getID() {
		return mID;
	}

	public void setFirstAppointment(boolean firstAppointment) {
		mFirstAppointment = firstAppointment;
	}

	public boolean isFirstAppointment() {
		return mFirstAppointment;
	}

	public void setStartDateTime(String startDateTime) {
		mStartDateTime = startDateTime;
	}

	public String getStartDateTime() {
		return mStartDateTime;
	}

	public void setNote(String note) {
		mNote = note;
	}

	public String getNote() {
		return mNote;
	}

	public void setStaff(Staff staff) {
		mStaff = staff;
	}

	public Staff getStaff() {
		return mStaff;
	}

	public int getDuration() {
		return mDuration;
	}

	public void setDuration(int duration) {
		mDuration = duration;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Appointment) {
			return ((Appointment) obj).getID() == mID;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return ((Long) mID).hashCode();
	}

	public Appointment(Parcel in) {
		mStatus = in.readString();
		mStaffRequested = in.readString();
		mClient = in.readParcelable(Client.class.getClassLoader());
		mEndDateTime = in.readString();
		mResource = in.readString();
		mLocation = in.readParcelable(Location.class.getClassLoader());
		mSessionType = in.readParcelable(SessionType.class.getClassLoader());
		mProgram = in.readParcelable(Program.class.getClassLoader());
		mID = in.readLong();
		mFirstAppointment = in.readInt() == 1;
		mStartDateTime = in.readString();
		mNote = in.readString();
		mStaff = in.readParcelable(Staff.class.getClassLoader());
		mDuration = in.readInt();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Parcelable.Creator<Appointment> CREATOR = new Parcelable.Creator<Appointment>() {
		public Appointment createFromParcel(Parcel in) {
			return new Appointment(in);
		}

		public Appointment[] newArray(int size) {
			return new Appointment[size];
		}
	};

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mStatus);
		dest.writeString(mStaffRequested);
		dest.writeParcelable(mClient, flags);
		dest.writeString(mEndDateTime);
		dest.writeString(mResource);
		dest.writeParcelable(mLocation, flags);
		dest.writeParcelable(mSessionType, flags);
		dest.writeParcelable(mProgram, flags);
		dest.writeLong(mID);
		dest.writeInt(mFirstAppointment ? 1 : 0);
		dest.writeString(mStartDateTime);
		dest.writeString(mNote);
		dest.writeParcelable(mStaff, flags);
		dest.writeInt(mDuration);
	}
	
	public String getClientName(){
		return mClient.getFirstName() + " " + mClient.getLastName();
	}
	
	public String getSessionName(){
		return mProgram.getName() + " / " + mSessionType.getName();
	}
}