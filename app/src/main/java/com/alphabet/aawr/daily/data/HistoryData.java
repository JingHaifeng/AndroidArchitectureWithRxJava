package com.alphabet.aawr.daily.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author: haifeng jing(haifeng_jing@kingdee.com)
 * @date: 2016-05-10
 * @time: 10:44
 */
public class HistoryData {
    public boolean error;
    @SerializedName("results")
    public List<String> mDateList;
}
