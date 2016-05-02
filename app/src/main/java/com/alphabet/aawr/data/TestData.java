package com.alphabet.aawr.data;

import java.util.List;

/**
 * Created by alphabet on 5/2/16.
 */
public class TestData {
    public boolean error;

    List<FuliData> results;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("error:").append(error).append("\n");
        for (FuliData result : results) {
            sb.append("{")
                    .append(result.toString())
                    .append("}\n");
        }
        return sb.toString();
    }
}
