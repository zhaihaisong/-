package com.Jason.app.model;

/**
 * Created by Administrator on 2018/11/19.
 */

public class anjian_details_info {
    private  String type;
    private  String type_data;
    private  boolean is_Lines;
    private  boolean is_title;
    private  boolean isExpanded;

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public anjian_details_info(boolean is_Lines, boolean is_title, boolean isExpanded,String type, String type_data){
        this.type=type;
        this.isExpanded=isExpanded;
        this.type_data=type_data;
        this.is_Lines=is_Lines;
        this.is_title=is_title;
    }

    public boolean isIs_title() {
        return is_title;
    }

    public void setIs_title(boolean is_title) {
        this.is_title = is_title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType_data() {
        return type_data;
    }

    public void setType_data(String type_data) {
        this.type_data = type_data;
    }

    public boolean isIs_Lines() {
        return is_Lines;
    }

    public void setIs_Lines(boolean is_Lines) {
        this.is_Lines = is_Lines;
    }
}
