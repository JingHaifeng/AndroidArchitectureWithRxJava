package com.alphabet.aawr.daily.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author: haifeng jing(haifeng_jing@kingdee.com)
 * @date: 2016-05-10
 * @time: 10:56
 */
public class DailyData implements Parcelable{

    public boolean error;

    public ResultsEntity results;

    public List<String> category;

    protected DailyData(Parcel in) {
        error = in.readByte() != 0;
        results = in.readParcelable(ResultsEntity.class.getClassLoader());
        category = in.createStringArrayList();
    }

    public static final Creator<DailyData> CREATOR = new Creator<DailyData>() {
        @Override
        public DailyData createFromParcel(Parcel in) {
            return new DailyData(in);
        }

        @Override
        public DailyData[] newArray(int size) {
            return new DailyData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (error ? 1 : 0));
        dest.writeParcelable(results, flags);
        dest.writeStringList(category);
    }

    public static class ResultsEntity implements Parcelable{

        @SerializedName("Android")
        public List<BaseData> androidList;

        @SerializedName("IOS")
        public List<BaseData> iosList;

        @SerializedName("休息视频")
        public List<BaseData> videoList;

        @SerializedName("拓展资源")
        public List<BaseData> extendList;

        @SerializedName("瞎推荐")
        public List<BaseData> bindList;

        @SerializedName("福利")
        public List<BaseData> fuliList;

        protected ResultsEntity(Parcel in) {
            androidList = in.createTypedArrayList(BaseData.CREATOR);
            iosList = in.createTypedArrayList(BaseData.CREATOR);
            videoList = in.createTypedArrayList(BaseData.CREATOR);
            extendList = in.createTypedArrayList(BaseData.CREATOR);
            bindList = in.createTypedArrayList(BaseData.CREATOR);
            fuliList = in.createTypedArrayList(BaseData.CREATOR);
        }

        public static final Creator<ResultsEntity> CREATOR = new Creator<ResultsEntity>() {
            @Override
            public ResultsEntity createFromParcel(Parcel in) {
                return new ResultsEntity(in);
            }

            @Override
            public ResultsEntity[] newArray(int size) {
                return new ResultsEntity[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(androidList);
            dest.writeTypedList(iosList);
            dest.writeTypedList(videoList);
            dest.writeTypedList(extendList);
            dest.writeTypedList(bindList);
            dest.writeTypedList(fuliList);
        }
    }
}
