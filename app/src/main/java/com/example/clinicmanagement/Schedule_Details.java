package com.example.clinicmanagement;

public class Schedule_Details {
    String schedule_name,schedule_id,active_status,count;

    public Schedule_Details(String schedule_name, String schedule_id, String active_status,String count) {
        this.schedule_name = schedule_name;
        this.schedule_id = schedule_id;
        this.active_status = active_status;
        this.count = count;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSchedule_name() {
        return schedule_name;
    }

    public void setSchedule_name(String schedule_name) {
        this.schedule_name = schedule_name;
    }

    public String getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(String schedule_id) {
        this.schedule_id = schedule_id;
    }

    public String getActive_status() {
        return active_status;
    }

    public void setActive_status(String active_status) {
        this.active_status = active_status;
    }
}
