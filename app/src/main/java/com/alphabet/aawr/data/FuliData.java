package com.alphabet.aawr.data;

/**
 * Created by alphabet on 5/2/16.
 */
public class FuliData {

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
}
