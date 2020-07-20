package com.omidipoor.cla.model;

public class UserSharedPreferences {

    private Boolean first_time_run = true;
    private  String first_name = "";
    private  String last_name = "";
    private String image_url= "";

    public Boolean getFirst_time_run() {
        return first_time_run;
    }

    public void setFirst_time_run(Boolean first_time_run) {
        this.first_time_run = first_time_run;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
}
