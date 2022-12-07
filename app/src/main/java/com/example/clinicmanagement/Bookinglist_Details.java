package com.example.clinicmanagement;

public class Bookinglist_Details {
        String employee_id,employee_name,schedule_name,booking_id,patient_name,patient_id,user_id,gender_id,city_id,doctor_note,uhid,created_date,booking_date,schedule_id,gender_name,city_name;

        public Bookinglist_Details(String booking_id, String uhid, String patient_id, String patient_name, String gender_id, String gender_name, String city_id,String city_name, String user_id, String schedule_id,String doctor_note, String created_date, String booking_date,String schedule_name,String employee_name,String employee_id) {
            this.booking_id = booking_id;
            this.uhid = uhid;
            this.patient_id = patient_id;
            this.patient_name = patient_name;
            this.gender_id = gender_id;
            this.gender_name = gender_name;
            this.city_id = city_id;
            this.city_name = city_name;
            this.user_id = user_id;
            this.schedule_id = schedule_id;
            this.doctor_note = doctor_note;
            this.created_date = created_date;
            this.booking_date = booking_date;
            this.schedule_name = schedule_name;
            this.employee_name = employee_name;
            this.employee_id = employee_id;
        }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getSchedule_name() {
        return schedule_name;
    }

    public void setSchedule_name(String schedule_name) {
        this.schedule_name = schedule_name;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGender_id() {
        return gender_id;
    }

    public void setGender_id(String gender_id) {
        this.gender_id = gender_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getDoctor_note() {
        return doctor_note;
    }

    public void setDoctor_note(String doctor_note) {
        this.doctor_note = doctor_note;
    }

    public String getUhid() {
        return uhid;
    }

    public void setUhid(String uhid) {
        this.uhid = uhid;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }

    public String getSchedule_id() {
        return schedule_id;
    }

    public void setSchedule_id(String schedule_id) {
        this.schedule_id = schedule_id;
    }

    public String getGender_name() {
        return gender_name;
    }

    public void setGender_name(String gender_name) {
        this.gender_name = gender_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
}
