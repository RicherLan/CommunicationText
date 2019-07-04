package thefirstchange.example.com.communicationtext;

import android.os.Parcel;
import android.os.Parcelable;

public class UserNumber implements Parcelable {

    public String followers;
    public String follows;
    public String activities;
    public String feeds;
    public String unpaids;

    protected UserNumber(Parcel in) {
        followers = in.readString();
        follows = in.readString();
        activities = in.readString();
        feeds = in.readString();
        unpaids = in.readString();
    }

    public UserNumber() {
    }

    public static final Creator<UserNumber> CREATOR = new Creator<UserNumber>() {
        @Override
        public UserNumber createFromParcel(Parcel in) {
            return new UserNumber(in);
        }

        @Override
        public UserNumber[] newArray(int size) {
            return new UserNumber[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(followers);
        dest.writeString(follows);
        dest.writeString(activities);
        dest.writeString(feeds);
        dest.writeString(unpaids);
    }
}
