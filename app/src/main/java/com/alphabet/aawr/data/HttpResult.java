package com.alphabet.aawr.data;

import java.util.List;

/**
 * Created by alphabet on 5/2/16.
 */
public class HttpResult<T> {

    public boolean error;

    public List<T> results;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("error:").append(error).append("\n");
        for (T result : results) {
            sb.append("{\n")
                    .append(result)
                    .append("}\n");
        }
        return sb.toString();
    }
}
