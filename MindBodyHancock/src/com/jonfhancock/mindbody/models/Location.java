package com.jonfhancock.mindbody.models;

import android.content.ContentProviderOperation.Builder;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;
import com.jonfhancock.mindbody.data.provider.MindBodyContent.Appointment.Columns;

@XMLObject("//Location")
public class Location implements Parcelable{

    private static final String FIELD_TAX3 = "Tax3";
    private static final String FIELD_TAX2 = "Tax2";
    private static final String FIELD_TAX5 = "Tax5";
    private static final String FIELD_TREATMENT_ROOMS = "TreatmentRooms";
    private static final String FIELD_TAX4 = "Tax4";
    private static final String FIELD_TAX1 = "Tax1";
    private static final String FIELD_HAS_CLASSES = "HasClasses";
    private static final String FIELD_LONGITUDE = "Longitude";
    private static final String FIELD_SITE_ID = "SiteID";
    private static final String FIELD_ADDITIONAL_IMAGE_UR_LS = "AdditionalImageURLs";
    private static final String FIELD_ID = "ID";
    private static final String FIELD_FACILITY_SQUARE_FEET = "FacilitySquareFeet";
    private static final String FIELD_NAME = "Name";
    private static final String FIELD_LATITUDE = "Latitude";


    @XMLField(FIELD_TAX3)
    private int mTax3;
    @XMLField(FIELD_TAX2)
    private int mTax2;
    @XMLField(FIELD_TAX5)
    private int mTax5;
    @XMLField(FIELD_TREATMENT_ROOMS)
    private String mTreatmentRoom;
    @XMLField(FIELD_TAX4)
    private int mTax4;
    @XMLField(FIELD_TAX1)
    private int mTax1;
    @XMLField(FIELD_HAS_CLASSES)
    private boolean mHasClass;
    @XMLField(FIELD_LONGITUDE)
    private long mLongitude;
    @XMLField(FIELD_SITE_ID)
    private int mSiteID;
    @XMLField(FIELD_ADDITIONAL_IMAGE_UR_LS)
    private String mAdditionalImageURL;
    @XMLField(FIELD_ID)
    private long mID;
    @XMLField(FIELD_FACILITY_SQUARE_FEET)
    private int mFacilitySquareFeet;
    @XMLField(FIELD_NAME)
    private String mName;
    @XMLField(FIELD_LATITUDE)
    private long mLatitude;


    public Location(){

    }

    public void setTax3(int tax3) {
        mTax3 = tax3;
    }

    public int getTax3() {
        return mTax3;
    }

    public void setTax2(int tax2) {
        mTax2 = tax2;
    }

    public int getTax2() {
        return mTax2;
    }

    public void setTax5(int tax5) {
        mTax5 = tax5;
    }

    public int getTax5() {
        return mTax5;
    }

    public void setTreatmentRoom(String treatmentRoom) {
        mTreatmentRoom = treatmentRoom;
    }

    public String getTreatmentRoom() {
        return mTreatmentRoom;
    }

    public void setTax4(int tax4) {
        mTax4 = tax4;
    }

    public int getTax4() {
        return mTax4;
    }

    public void setTax1(int tax1) {
        mTax1 = tax1;
    }

    public int getTax1() {
        return mTax1;
    }

    public void setHasClass(boolean hasClass) {
        mHasClass = hasClass;
    }

    public boolean hasClass() {
        return mHasClass;
    }

    public void setLongitude(long longitude) {
        mLongitude = longitude;
    }

    public long getLongitude() {
        return mLongitude;
    }

    public void setSiteID(int siteID) {
        mSiteID = siteID;
    }

    public int getSiteID() {
        return mSiteID;
    }

    public void setAdditionalImageURL(String additionalImageURL) {
        mAdditionalImageURL = additionalImageURL;
    }

    public String getAdditionalImageURL() {
        return mAdditionalImageURL;
    }

    public void setID(long iD) {
        mID = iD;
    }

    public long getID() {
        return mID;
    }

    public void setFacilitySquareFeet(int facilitySquareFeet) {
        mFacilitySquareFeet = facilitySquareFeet;
    }

    public int getFacilitySquareFeet() {
        return mFacilitySquareFeet;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setLatitude(long latitude) {
        mLatitude = latitude;
    }

    public long getLatitude() {
        return mLatitude;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Location){
            return ((Location) obj).getID() == mID;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return ((Long)mID).hashCode();
    }

    public Location(Parcel in) {
        mTax3 = in.readInt();
        mTax2 = in.readInt();
        mTax5 = in.readInt();
        mTreatmentRoom = in.readString();
        mTax4 = in.readInt();
        mTax1 = in.readInt();
        mHasClass = in.readInt() == 1;
        mLongitude = in.readLong();
        mSiteID = in.readInt();
        mAdditionalImageURL = in.readString();
        mID = in.readLong();
        mFacilitySquareFeet = in.readInt();
        mName = in.readString();
        mLatitude = in.readLong();
    }

    public Location(Cursor c) {
    	mTax1 = c.getInt(Columns.LOCATION_TAX1.getIndex());
    	mTax2 = c.getInt(Columns.LOCATION_TAX2.getIndex());
    	mTax3 = c.getInt(Columns.LOCATION_TAX3.getIndex());
    	mTax4 = c.getInt(Columns.LOCATION_TAX4.getIndex());
    	mTax5 = c.getInt(Columns.LOCATION_TAX5.getIndex());
    	mTreatmentRoom = c.getString(Columns.LOCATION_TREAT_ROOM.getIndex());
    	mHasClass = c.getInt(Columns.LOCATION_HAS_CLASSES.getIndex()) == 1;
    	mLongitude = c.getLong(Columns.LOCATION_LONGITUDE.getIndex());
    	mLatitude = c.getLong(Columns.LOCATION_LATITUDE.getIndex());
    	mSiteID = c.getInt(Columns.LOCATION_SITE_ID.getIndex());
    	mFacilitySquareFeet = c.getInt(Columns.LOCATION_SQFT.getIndex());
    	mName = c.getString(Columns.LOCATION_NAME.getIndex());
	}
    
    public Builder addValues(Builder builder) {
		builder.withValue(Columns.LOCATION_TAX1.getName(), mTax1)
		.withValue(Columns.LOCATION_TAX2.getName(), mTax2)
		.withValue(Columns.LOCATION_TAX3.getName(), mTax3)
		.withValue(Columns.LOCATION_TAX4.getName(), mTax4)
		.withValue(Columns.LOCATION_TAX5.getName(), mTax5)
		.withValue(Columns.LOCATION_TREAT_ROOM.getName(), mTreatmentRoom)
		.withValue(Columns.LOCATION_HAS_CLASSES.getName(), mHasClass ? 1:0)
		.withValue(Columns.LOCATION_LONGITUDE.getName(), mLongitude)
		.withValue(Columns.LOCATION_LATITUDE.getName(), mLatitude)
		.withValue(Columns.LOCATION_SITE_ID.getName(), mSiteID)
		.withValue(Columns.LOCATION_SQFT.getName(), mFacilitySquareFeet)
		.withValue(Columns.LOCATION_NAME.getName(), mName);
		return builder;
	}
	@Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        public Location[] newArray(int size) {
        return new Location[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mTax3);
        dest.writeInt(mTax2);
        dest.writeInt(mTax5);
        dest.writeString(mTreatmentRoom);
        dest.writeInt(mTax4);
        dest.writeInt(mTax1);
        dest.writeInt(mHasClass ? 1:0);
        dest.writeLong(mLongitude);
        dest.writeInt(mSiteID);
        dest.writeString(mAdditionalImageURL);
        dest.writeLong(mID);
        dest.writeInt(mFacilitySquareFeet);
        dest.writeString(mName);
        dest.writeLong(mLatitude);
    }

	


}