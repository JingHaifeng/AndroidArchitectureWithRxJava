package com.alphabet.aawr.daily.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alphabet on 5/2/16.
 */
public class BaseData implements Parcelable {

    /**
     * _id : 5722b27b67765974fbfcf9b9
     * createdAt : 2016-04-29T09:01:47.962Z
     * desc : 4.29
     * publishedAt : 2016-04-29T11:36:42.906Z
     * source : chrome
     * type : 福利
     * url : http://ww1.sinaimg.cn/large/7a8aed7bgw1f3damign7mj211c0l0dj2.jpg
     * used : true
     * who : 张涵宇
     */

    public String _id;
    public String createdAt;
    public String desc;
    public String publishedAt;
    public String source;
    public String type;
    public String url;
    public boolean used;
    public String who;

    protected BaseData(Parcel in) {
        _id = in.readString();
        createdAt = in.readString();
        desc = in.readString();
        publishedAt = in.readString();
        source = in.readString();
        type = in.readString();
        url = in.readString();
        used = in.readByte() != 0;
        who = in.readString();
    }

    public static final Creator<BaseData> CREATOR = new Creator<BaseData>() {
        @Override
        public BaseData createFromParcel(Parcel in) {
            return new BaseData(in);
        }

        @Override
        public BaseData[] newArray(int size) {
            return new BaseData[size];
        }
    };

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("_id:" + _id).append("\n");
        stringBuilder.append("createdAt:" + createdAt).append("\n");
        stringBuilder.append("desc:" + desc).append("\n");
        stringBuilder.append("publishedAt:" + publishedAt).append("\n");
        stringBuilder.append("source:" + source).append("\n");
        stringBuilder.append("type:" + type).append("\n");
        stringBuilder.append("url:" + url).append("\n");
        stringBuilder.append("used:" + used).append("\n");
        stringBuilder.append("who:" + who).append("\n");
        return stringBuilder.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(createdAt);
        dest.writeString(desc);
        dest.writeString(publishedAt);
        dest.writeString(source);
        dest.writeString(type);
        dest.writeString(url);
        dest.writeByte((byte) (used ? 1 : 0));
        dest.writeString(who);
    }
}
