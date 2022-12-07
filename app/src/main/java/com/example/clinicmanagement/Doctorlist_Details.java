package com.example.clinicmanagement;

public class Doctorlist_Details {
    String employee_id,employee_name,active_status;

    public Doctorlist_Details(String employee_id, String employee_name, String active_status) {
        this.employee_id = employee_id;
        this.employee_name = employee_name;
        this.active_status = active_status;
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

    public String getActive_status() {
        return active_status;
    }

    public void setActive_status(String active_status) {
        this.active_status = active_status;
    }
}
