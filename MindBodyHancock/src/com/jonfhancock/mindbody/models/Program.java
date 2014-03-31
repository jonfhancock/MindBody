package com.jonfhancock.mindbody.models;

import android.content.ContentProviderOperation.Builder;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;
import com.jonfhancock.mindbody.data.provider.MindBodyContent.Appointment.Columns;

@XMLObject("//Program")

public class Program implements Parcelable{

    private static final String FIELD_ID = "ID";
    private static final String FIELD_NAME = "Name";
    private static final String FIELD_SCHEDULE_TYPE = "ScheduleType";


    @XMLField(FIELD_ID)
    private long mID;
    @XMLField(FIELD_NAME)
    private String mName;
    @XMLField(FIELD_SCHEDULE_TYPE)
    private String mScheduleType;


    public Program(){

    }

    public void setID(long iD) {
        mID = iD;
    }

    public long getID() {
        return mID;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setScheduleType(String scheduleType) {
        mScheduleType = scheduleType;
    }

    public String getScheduleType() {
        return mScheduleType;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Program){
            return ((Program) obj).getID() == mID;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return ((Long)mID).hashCode();
    }

    public Program(Parcel in) {
        mID = in.readLong();
        mName = in.readString();
        mScheduleType = in.readString();
    }

    public Program(Cursor c) {
		mID = c.getLong(Columns.PROGRAM_ID.getIndex());
		mName = c.getString(Columns.PROGRAM_NAME.getIndex());
		mScheduleType = c.getString(Columns.PROGRAM_SCHED_TYPE.getIndex());
	}
    public Builder addValues(Builder builder) {
		builder.withValue(Columns.PROGRAM_ID.getName(), mID)
		.withValue(Columns.PROGRAM_NAME.getName(), mName)
		.withValue(Columns.PROGRAM_SCHED_TYPE.getName(), mScheduleType);
		return builder;
	}
	@Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Program> CREATOR = new Parcelable.Creator<Program>() {
        public Program createFromParcel(Parcel in) {
            return new Program(in);
        }

        public Program[] newArray(int size) {
        return new Program[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mID);
        dest.writeString(mName);
        dest.writeString(mScheduleType);
    }

	


}