package com.v_care.Beans;

/**
 * Created by lenovo on 10-Apr-17.
 */
public class Info_bean
{
    String information_id;
    String info_img_name;
    String information_title;
    String information_description;
    String info_file_type;
    String information_status;
    String video_thumbnail;

   //creating Getter Setter for the above string variables
    public String getInformation_id() {
        return information_id;
    }

    public void setInformation_id(String information_id) {
        this.information_id = information_id;
    }

    public String getInfo_img_name() {
        return info_img_name;
    }

    public void setInfo_img_name(String info_img_name) {
        this.info_img_name = info_img_name;
    }

    public String getInformation_title() {
        return information_title;
    }

    public void setInformation_title(String information_title) {
        this.information_title = information_title;
    }

    public String getInformation_description() {
        return information_description;
    }

    public void setInformation_description(String information_description) {
        this.information_description = information_description;
    }

    public String getInfo_file_type() {
        return info_file_type;
    }

    public void setInfo_file_type(String info_file_type) {
        this.info_file_type = info_file_type;
    }

    public String getInformation_status() {
        return information_status;
    }

    public void setInformation_status(String information_status) {
        this.information_status = information_status;
    }

    public String getVideo_thumbnail() {
        return video_thumbnail;
    }

    public void setVideo_thumbnail(String video_thumbnail) {
        this.video_thumbnail = video_thumbnail;
    }
}
