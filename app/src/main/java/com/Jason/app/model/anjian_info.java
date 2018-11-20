package com.Jason.app.model;

/**
 * Created by Jason on 2018/3/28.
 */

public class anjian_info {
    /*
    * @ anjian_type
     * 1代表 已排期
    * 2代表 未排期
    * 3代表 已结束
    * */
    private String name;
    private String anjian_type;
    private String anjian_id;
    private String sessionTime;
    private String ss_fayuan;

    public String getSs_fayuan() {
        return "所属法院："+ss_fayuan;
    }

    public void setSs_fayuan(String ss_fayuan) {
        this.ss_fayuan = ss_fayuan;
    }

    public String getSessionTime() {
        return "开庭时间："+sessionTime;
    }

    public void setSessionTime(String sessionTime) {
        this.sessionTime = sessionTime;
    }

    public String getAnjian_id() {
        return anjian_id;
    }

    public void setAnjian_id(String anjian_id) {
        this.anjian_id = anjian_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnjian_type() {
        return anjian_type;
    }

    public void setAnjian_type(String anjian_type) {
        this.anjian_type = anjian_type;
    }
}
