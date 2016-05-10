package com.alphabet.aawr.data;

import com.alphabet.aawr.daily.data.BaseData;

import java.util.List;

/**
 * Created by alphabet on 5/2/16.
 */
public class TestData {
    public boolean error;

    List<BaseData> results;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("error:").append(error).append("\n");
        for (BaseData result : results) {
            sb.append("{")
                    .append(result.toString())
                    .append("}\n");
        }
        return sb.toString();
    }
}
