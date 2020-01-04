package com.ngyb.googleplayserver.bean;

import java.util.List;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2020/1/2 15:45
 */
public class AppDetailBean {

    /**
     * author : 北京千橡网景科技发展有限公司
     * date : 2014-05-20
     * des : 2005-2014 你的校园一直在这儿。 中国最大的实名制SNS网络平台，大学生必备网络社交应用。 -------我们好像在哪儿见过------- 	早春发芽，我在人人通过姓名，学校，找到了从小到大的同学，并加入了校园新圈子 	花开半夏，在新鲜事里和好友分享彼此的生活点滴，我们渺小如星辰，却真实存在着 	花花世界，这里的人貌似不疯不成活，蛇精病短视频、激萌语音照片，芝麻烂谷飚日志 	一叶知秋，喜欢上了每天看人人话题、看世界，公共主页、我知道这个世界有多大我们就得担负多大。 	漫天雪花：不知什么时候，习惯上了回顾过去，三千前，五年前，我们无知无畏，无所不能，每个样子，都好像在哪儿见过。
     * downloadNum : 1000万+
     * downloadUrl : app/com.renren.mobile.android/com.renren.mobile.android.apk
     * iconUrl : app/com.renren.mobile.android/icon.jpg
     * id : 1580615
     * name : 人人
     * packageName : com.renren.mobile.android
     * safe : [{"safeDes":"已通过安智市场官方认证，是正版软件","safeDesColor":0,"safeDesUrl":"app/com.renren.mobile.android/safeDesUrl0.jpg","safeUrl":"app/com.renren.mobile.android/safeIcon0.jpg"}]
     * screen : ["app/com.renren.mobile.android/screen0.jpg","app/com.renren.mobile.android/screen1.jpg","app/com.renren.mobile.android/screen2.jpg","app/com.renren.mobile.android/screen3.jpg","app/com.renren.mobile.android/screen4.jpg"]
     * size : 21803987
     * stars : 2.0
     * version : 7.5.3
     */

    private String author;
    private String date;
    private String des;
    private String downloadNum;
    private String downloadUrl;
    private String iconUrl;
    private int id;
    private String name;
    private String packageName;
    private int size;
    private double stars;
    private String version;
    private List<SafeBean> safe;
    private List<String> screen;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(String downloadNum) {
        this.downloadNum = downloadNum;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<SafeBean> getSafe() {
        return safe;
    }

    public void setSafe(List<SafeBean> safe) {
        this.safe = safe;
    }

    public List<String> getScreen() {
        return screen;
    }

    public void setScreen(List<String> screen) {
        this.screen = screen;
    }

    public static class SafeBean {
        /**
         * safeDes : 已通过安智市场官方认证，是正版软件
         * safeDesColor : 0
         * safeDesUrl : app/com.renren.mobile.android/safeDesUrl0.jpg
         * safeUrl : app/com.renren.mobile.android/safeIcon0.jpg
         */

        private String safeDes;
        private int safeDesColor;
        private String safeDesUrl;
        private String safeUrl;

        public String getSafeDes() {
            return safeDes;
        }

        public void setSafeDes(String safeDes) {
            this.safeDes = safeDes;
        }

        public int getSafeDesColor() {
            return safeDesColor;
        }

        public void setSafeDesColor(int safeDesColor) {
            this.safeDesColor = safeDesColor;
        }

        public String getSafeDesUrl() {
            return safeDesUrl;
        }

        public void setSafeDesUrl(String safeDesUrl) {
            this.safeDesUrl = safeDesUrl;
        }

        public String getSafeUrl() {
            return safeUrl;
        }

        public void setSafeUrl(String safeUrl) {
            this.safeUrl = safeUrl;
        }
    }
}
