package com.jonfhancock.mindbody.models;

import android.annotation.SuppressLint;
import android.content.ContentProviderOperation;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.alexgilleran.icesoap.annotation.XMLField;
import com.alexgilleran.icesoap.annotation.XMLObject;
import com.jonfhancock.mindbody.data.provider.MindBodyContent.Staff.Columns;

@XMLObject("//Staff")
public class Staff implements Parcelable {

	private static final String FIELD_IMAGE_URL = "ImageURL";
	private static final String FIELD_IS_MALE = "isMale";
	private static final String FIELD_HOME_PHONE = "HomePhone";
	private static final String FIELD_CITY = "City";
	private static final String FIELD_BIO = "Bio";
	private static final String FIELD_MOBILE_PHONE = "MobilePhone";
	private static final String FIELD_FIRST_NAME = "FirstName";
	private static final String FIELD_COUNTRY = "Country";
	private static final String FIELD_STATE = "State";
	private static final String FIELD_ID = "ID";
	private static final String FIELD_NAME = "Name";
	private static final String FIELD_POSTAL_CODE = "PostalCode";
	private static final String FIELD_ADDRESS = "Address";
	private static final String FIELD_LAST_NAME = "LastName";
	private static final String FIELD_EMAIL = "Email";

	@XMLField(FIELD_IMAGE_URL)
	private String mImageURL;
	@XMLField(FIELD_IS_MALE)
	private boolean mMale;
	@XMLField(FIELD_HOME_PHONE)
	private String mHomePhone;
	@XMLField(FIELD_CITY)
	private String mCity;
	@XMLField(FIELD_BIO)
	private String mBio;
	@XMLField(FIELD_MOBILE_PHONE)
	private String mMobilePhone;
	@XMLField(FIELD_FIRST_NAME)
	private String mFirstName;
	@XMLField(FIELD_COUNTRY)
	private String mCountry;
	@XMLField(FIELD_STATE)
	private String mState;
	@XMLField(FIELD_ID)
	private long mId;
	@XMLField(FIELD_NAME)
	private String mName;
	@XMLField(FIELD_POSTAL_CODE)
	private String mPostalCode;
	@XMLField(FIELD_ADDRESS)
	private String mAddress;
	@XMLField(FIELD_LAST_NAME)
	private String mLastName;
	@XMLField(FIELD_EMAIL)
	private String mEmail;

	public Staff() {

	}

	public void setImageURL(String imageURL) {
		mImageURL = imageURL;
	}

	public String getImageURL() {
		return mImageURL;
	}

	public void setMale(boolean isMale) {
		mMale = isMale;
	}

	public boolean isMale() {
		return mMale;
	}

	public void setHomePhone(String homePhone) {
		mHomePhone = homePhone;
	}

	public String getHomePhone() {
		return mHomePhone;
	}

	public void setCity(String city) {
		mCity = city;
	}

	public String getCity() {
		return mCity;
	}

	public void setBio(String bio) {
		mBio = bio;
	}

	public String getBio() {
		return mBio;
	}

	public void setMobilePhone(String mobilePhone) {
		mMobilePhone = mobilePhone;
	}

	public String getMobilePhone() {
		return mMobilePhone;
	}

	public void setFirstName(String firstName) {
		mFirstName = firstName;
	}

	public String getFirstName() {
		return mFirstName;
	}

	public void setCountry(String country) {
		mCountry = country;
	}

	public String getCountry() {
		return mCountry;
	}

	public void setState(String state) {
		mState = state;
	}

	public String getState() {
		return mState;
	}

	public void setId(long id) {
		mId = id;
	}

	public long getId() {
		return mId;
	}

	public void setName(String name) {
		mName = name;
	}

	public String getName() {
		return mName;
	}

	public void setPostalCode(String postalCode) {
		mPostalCode = postalCode;
	}

	public String getPostalCode() {
		return mPostalCode;
	}

	public void setAddress(String address) {
		mAddress = address;
	}

	public String getAddress() {
		return mAddress;
	}

	public void setLastName(String lastName) {
		mLastName = lastName;
	}

	public String getLastName() {
		return mLastName;
	}

	public void setEmail(String email) {
		mEmail = email;
	}

	public String getEmail() {
		return mEmail;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Staff) {
			return ((Staff) obj).getId() == mId;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return ((Long) mId).hashCode();
	}

	public Staff(Parcel in) {
		mImageURL = in.readString();
		mMale = in.readInt() == 1;
		mHomePhone = in.readString();
		mCity = in.readString();
		mBio = in.readString();
		mMobilePhone = in.readString();
		mFirstName = in.readString();
		mCountry = in.readString();
		mState = in.readString();
		mId = in.readLong();
		mName = in.readString();
		mPostalCode = in.readString();
		mAddress = in.readString();
		mLastName = in.readString();
		mEmail = in.readString();
	}

	public Staff(Cursor c) {
		mImageURL = c.getString(Columns.IMAGE_URL.getIndex());
		mMale = c.getInt(Columns.MALE.getIndex()) == 1;
		mHomePhone = c.getString(Columns.HOME_PHONE.getIndex());
		mCity = c.getString(Columns.CITY.getIndex());
		mBio = c.getString(Columns.BIO.getIndex());
		mMobilePhone = c.getString(Columns.MOBILE_PHONE.getIndex());
		mFirstName = c.getString(Columns.FIRST_NAME.getIndex());
		mCountry = c.getString(Columns.COUNTRY.getIndex());
		mState = c.getString(Columns.STATE.getIndex());
		mId = c.getLong(Columns.ID.getIndex());
		mName = c.getString(Columns.NAME.getIndex());
		mPostalCode = c.getString(Columns.POSTAL_CODE.getIndex());
		mAddress = c.getString(Columns.ADDRESS.getIndex());
		mLastName = c.getString(Columns.LAST_NAME.getIndex());
		mEmail = c.getString(Columns.EMAIL.getIndex());
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
		builder.withValue(Columns.ID.getName(), mId)
				.withValue(Columns.NAME.getName(), mName)
				.withValue(Columns.EMAIL.getName(), mEmail)
				.withValue(Columns.IMAGE_URL.getName(), mImageURL)
				.withValue(Columns.MALE.getName(), mMale ? 1 : 0)
				.withValue(Columns.HOME_PHONE.getName(), mHomePhone)
				.withValue(Columns.CITY.getName(), mCity)
				.withValue(Columns.BIO.getName(), mBio)
				.withValue(Columns.MOBILE_PHONE.getName(), mMobilePhone)
				.withValue(Columns.FIRST_NAME.getName(), mFirstName)
				.withValue(Columns.COUNTRY.getName(), mCountry)
				.withValue(Columns.STATE.getName(), mState)
				.withValue(Columns.POSTAL_CODE.getName(), mPostalCode)
				.withValue(Columns.ADDRESS.getName(), mAddress)
				.withValue(Columns.LAST_NAME.getName(), mLastName);
		return builder.build();

	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Parcelable.Creator<Staff> CREATOR = new Parcelable.Creator<Staff>() {
		public Staff createFromParcel(Parcel in) {
			return new Staff(in);
		}

		public Staff[] newArray(int size) {
			return new Staff[size];
		}
	};

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(mImageURL);
		dest.writeInt(mMale ? 1 : 0);
		dest.writeString(mHomePhone);
		dest.writeString(mCity);
		dest.writeString(mBio);
		dest.writeString(mMobilePhone);
		dest.writeString(mFirstName);
		dest.writeString(mCountry);
		dest.writeString(mState);
		dest.writeLong(mId);
		dest.writeString(mName);
		dest.writeString(mPostalCode);
		dest.writeString(mAddress);
		dest.writeString(mLastName);
		dest.writeString(mEmail);
	}

	// Convenience methods for getting username and password. Not useful in an
	// actual production app.

	public String getUsername() {
		return mFirstName + "." + mLastName;
	}

	@SuppressLint("DefaultLocale")
	public String getPassword() {
		String pw = String.valueOf(mFirstName.charAt(0))
				+ String.valueOf(mLastName.charAt(0)) + String.valueOf(mId);
		return pw.toLowerCase();
	}

}