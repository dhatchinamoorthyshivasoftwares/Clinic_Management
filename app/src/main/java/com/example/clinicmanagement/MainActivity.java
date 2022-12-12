package com.example.clinicmanagement;

import static com.example.clinicmanagement.Login_Activity.USER_CODE;
import static com.example.clinicmanagement.Splash_Screen.COMPANY_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    ImageView  power_img;
    TextView date_tv;
    String DATE ="";

    android.icu.util.Calendar calendar;

    SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat mdformat_db = new SimpleDateFormat("yyyy-MM-dd");

    FloatingActionButton fab_add;
    ArrayList<Doctorlist_Details> doctorlist_details = new ArrayList<>();
    SwipeMenuListView booking_listview;
    BookingListAdapter bookingListAdapter=null;
    DoctorListAdapter doctorListAdapter=null;
    ProgressDialog progressDialog;
    SwipeMenuCreator creator;
    GifImageView loader_imageview;
    Dialog schedule_dialog,edit_dialog,doctor_dialog,loader_dialog;
    Context context ;
    ListView list_view,list_view_doctor;
    TextView no_record_tv,title_tv,edit_title_tv,doctor_title_tv,schedule_title_tv,booking_date_tv,patient_name_txt,patient_id_txt,schedule_time_txt,doctor_name_txt,doctor_note_txt,save_txt;
    ArrayList<Schedule_Details> schedule_details = new ArrayList<>();
    ArrayList<Bookinglist_Details> bookinglist_details = new ArrayList<>();
    ScheduleListAdapter scheduleListAdapter;
    ImageView close,close_schedule,close_doctor;
    String SCHEDULE_ID="",SELECTED_DATE="",DOCTOR_NOTE="", BOOKING_ID ="",BOOKING_DATE="",EMPLOYEE_ID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        date_tv = (TextView) findViewById(R.id.date_tv);
        fab_add = (FloatingActionButton) findViewById(R.id.fab_add);

        power_img = (ImageView) findViewById(R.id.power_img);

        booking_listview = (SwipeMenuListView) findViewById(R.id.booking_listview);

        title_tv = (TextView) findViewById(R.id.title_tv);

        no_record_tv = (TextView) findViewById(R.id.no_record_tv);

        context = this;

        schedule_dialog = new Dialog(context);
        schedule_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        schedule_dialog.setContentView(R.layout.schedule_popup);

        edit_dialog = new Dialog(context);
        edit_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        edit_dialog.setContentView(R.layout.edit_popup);

        doctor_dialog = new Dialog(context);
        doctor_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        doctor_dialog.setContentView(R.layout.schedule_popup);

        patient_name_txt  = (EditText) edit_dialog.findViewById(R.id.patient_name_txt);
        patient_id_txt  = (EditText) edit_dialog.findViewById(R.id.patient_id_txt);
        doctor_note_txt = (EditText) edit_dialog.findViewById(R.id.doctor_note_txt);
        schedule_time_txt  = (TextView) edit_dialog.findViewById(R.id.schedule_time_txt);
        doctor_name_txt = (TextView) edit_dialog.findViewById(R.id.doctor_name_txt);
        save_txt  = (TextView) edit_dialog.findViewById(R.id.save_txt);
        booking_date_tv = (TextView) edit_dialog.findViewById(R.id.booking_date_tv);

        doctor_title_tv = (TextView) doctor_dialog.findViewById(R.id.title_tv);
        schedule_title_tv = (TextView) schedule_dialog.findViewById(R.id.title_tv);
        edit_title_tv = (TextView) edit_dialog.findViewById(R.id.title_tv);

        close  = (ImageView) edit_dialog.findViewById(R.id.close);
        close_schedule  = (ImageView) schedule_dialog.findViewById(R.id.close);
        close_doctor = (ImageView) doctor_dialog.findViewById(R.id.close);

        list_view  = (ListView) schedule_dialog.findViewById(R.id.list_view);
        list_view_doctor  = (ListView) doctor_dialog.findViewById(R.id.list_view);

        patient_name_txt.setFocusable(false);
        patient_id_txt.setFocusable(false);

        edit_title_tv.setText(String.valueOf("Update Booking"));
        doctor_title_tv.setText(String.valueOf("Select Doctor"));
        schedule_title_tv.setText(String.valueOf("Select Schedule Time"));

        if(!COMPANY_NAME.equals("")) {
            title_tv.setText(String.valueOf(COMPANY_NAME));
        }
        //Swipe menu editior functionality
         creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item

                SwipeMenuItem edit = new SwipeMenuItem(MainActivity.this);
                //  openItem.setBackground(ContextCompat.getDrawable(context,R.drawable.noborder));
                edit.setWidth(130);
                edit.setIcon(R.drawable.ic_edit);
                menu.addMenuItem(edit);

                SwipeMenuItem openItem = new SwipeMenuItem(MainActivity.this);
                //  openItem.setBackground(ContextCompat.getDrawable(context,R.drawable.noborder));
                openItem.setWidth(130);
                openItem.setIcon(R.drawable.ic_delete);
                menu.addMenuItem(openItem);
                
            }
        };
        booking_listview.setMenuCreator(creator);

        booking_listview.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch (index) {
                    case 0:
                      //  Toast.makeText(MainActivity.this,currentListDataedit.get(position).getPatient_name(),Toast.LENGTH_SHORT).show();
                       // EditPopup(currentListDataedit.get(position));
                        break;

                }
                return true;
            }
        });

        power_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goback();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            calendar = android.icu.util.Calendar.getInstance();
            //calendar.add(Calendar.DATE, -13);
            DATE = mdformat.format(calendar.getTime());
            SELECTED_DATE = mdformat_db.format(calendar.getTime());
        }
        try {
            date_tv.setText(String.valueOf(DATE));
            booking_date_tv.setText(String.valueOf(DATE));
            booking_date_tv.setFocusable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        save_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DOCTOR_NOTE = doctor_note_txt.getText().toString();

                if(SCHEDULE_ID.equals("")){
                    schedule_time_txt.setError("Select schedule time");
                }
                if(EMPLOYEE_ID.equals("")){
                    doctor_name_txt.setError("Select doctor");
                }
                  if(!USER_CODE.equals("") && !BOOKING_ID.equals("") && !DOCTOR_NOTE.equals("") && !SCHEDULE_ID.equals("") && !SELECTED_DATE.equals("") && !EMPLOYEE_ID.equals("")){
                      new AsyncUpdateBooking().execute();
                  }
            }
        });
        date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePickerDialog(view);
            }
        });
        booking_date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePickerDialog_db(view);
            }
        });

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Appointment_Activity.class);
                MainActivity.this.finish();
                startActivity(i);
            }
        });

        schedule_time_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SchedulePopup();
                schedule_time_txt.setError(null);
                if(!booking_date_tv.getText().toString().equals("")){
                    new AsyncSchedule().execute();
                }else{
                    Toast.makeText(context, "Please select booking date", Toast.LENGTH_SHORT).show();
                }

            }
        });

        doctor_name_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doctor_name_txt.setError(null);

                new AsyncDoctorlist().execute();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               edit_dialog.dismiss();
            }
        });
        close_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                schedule_dialog.dismiss();
            }
        });
        close_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doctor_dialog.dismiss();
            }
        });
              new AsyncBookinglist().execute();
    }

    private void EditPopup(Bookinglist_Details details) {
        BOOKING_ID ="";
        SCHEDULE_ID="";
        DOCTOR_NOTE="";

        edit_dialog.show();

        patient_name_txt.setText(String.valueOf(details.getPatient_name()));
      //  patient_id_txt.setText(String.valueOf(details.getPatient_id()));
        patient_id_txt.setText(String.valueOf(details.getUhid()));
        schedule_time_txt.setText(String.valueOf(details.getSchedule_name()));
        doctor_note_txt.setText(String.valueOf(details.getDoctor_note()));
        doctor_name_txt.setText(String.valueOf(details.getEmployee_name()));

        BOOKING_ID = details.getBooking_id();
        SCHEDULE_ID = details.getSchedule_id();
        DOCTOR_NOTE = details.getDoctor_note();
        EMPLOYEE_ID = details.getEmployee_id();
    }

    public void SchedulePopup(){
        schedule_dialog.show();
    }

    public  void  DoctorPopup(){
        doctor_dialog.show();
    }

    //GET SEARCH LIST
    public class AsyncBookinglist extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj, jsonObj_body,jsonObj_userinfo;
        JSONArray jsonArray;
        String message, token;
        String  success="";

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            try {
                //Make webservice connection and call APi

                RestAPI objRestAPI = new RestAPI();
                if (isNetworkAvailable()) {
                    jsonObj = objRestAPI.GetBookingList(DATE);
                    message = "";
                    token = "";


                    if(jsonObj!=null) {
                        jsonObj_body = new JSONObject(jsonObj.getString("body"));
                    }

                    if(jsonObj_body != null) {
                        jsonArray = jsonObj_body.getJSONArray("Bookinglist_Array");

                        if(jsonArray != null){
                            bookinglist_details.clear();
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject json = jsonArray.getJSONObject(i);
                                //  schedule_details.add(new Schedule_Details(json.getString("schedule_name"),json.getString("schedule_id"),json.getString("active_status")));
                                bookinglist_details.add(new Bookinglist_Details(json.getString("booking_id"),json.getString("uhid"),
                                        json.getString("patient_id"),json.getString("patient_name"),json.getString("gender_id"),
                                        json.getString("gender_name"),json.getString("city_id"),json.getString("city_name"),
                                        json.getString("user_id"), json.getString("schedule_id"), json.getString("doctor_note"),
                                        json.getString("created_date"),json.getString("booking_date"),json.getString("schedule_name"),
                                        json.getString("employee_name"),json.getString("employee_id")));
                                Log.d("MAIN ACTIVITY ACTIVITY ========(BookingList Array)====("+String.valueOf(i+1)+")====>", json.toString());
                            }
                        }
                    }
                }
            }
            //Catch Block UserAuth true
            catch (Exception e) {
                Log.d("AsyncLoggerService", "Message");
                Log.d("AsyncLoggerService", e.getMessage());
            }
            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //            progressDialog = new ProgressDialog(MainActivity.this);
//            progressDialog.setMessage("Loading..."); // Setting Message
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
//            progressDialog.show();

            loader_dialog = new Dialog(context);
            loader_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            loader_dialog.setContentView(R.layout.loader_layout);
            loader_imageview  = (GifImageView) loader_dialog.findViewById(R.id.loader_imageview);
            loader_dialog.show();
            loader_imageview.setBackgroundResource(R.drawable.heart_beat);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loader_dialog.hide();

                    try{

                        if(bookinglist_details.size()>0) {
                            if(no_record_tv.getVisibility() == View.VISIBLE){
                                no_record_tv.setVisibility(View.GONE);
                            }
                        }else{
                            if(no_record_tv.getVisibility() == View.GONE){
                                no_record_tv.setVisibility(View.VISIBLE);
                            }
                        }
                        bookingListAdapter = new BookingListAdapter(getApplicationContext(), bookinglist_details);
                        booking_listview.setAdapter(bookingListAdapter);
                        bookingListAdapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 1100);
         //            if(progressDialog != null) {
//                progressDialog.hide();
//            }
            //Toast.makeText(context, String.valueOf(final_flag) +""+getdeviceid, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        loader_dialog.dismiss(); // try this
    }

    //SECHEDULE LIST
    public class AsyncSchedule extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj, jsonObj_body,jsonObj_userinfo;
        JSONArray jsonArray;
        String message, token;
        String  success="";

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            try {
                //Make webservice connection and call APi

                RestAPI objRestAPI = new RestAPI();
                if (isNetworkAvailable()) {
                    jsonObj = objRestAPI.GetSchedule(booking_date_tv.getText().toString());
                    message = "";
                    token = "";


                    if(jsonObj!=null) {
                        jsonObj_body = new JSONObject(jsonObj.getString("body"));
                    }

                    if(jsonObj_body != null) {
                        jsonArray = jsonObj_body.getJSONArray("Schedule_Array");

                        if(jsonArray != null){
                            schedule_details.clear();
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject json = jsonArray.getJSONObject(i);
                                schedule_details.add(new Schedule_Details(json.getString("schedule_name"),json.getString("schedule_id"),json.getString("active_status"),json.getString("count")));
                            }
                        }
                    }
                }
            }
            //Catch Block UserAuth true
            catch (Exception e) {
                Log.d("AsyncLoggerService", "Message");
                Log.d("AsyncLoggerService", e.getMessage());
            }
            return true;
        }

        @Override
        protected void onPreExecute() {
            loader_dialog = new Dialog(context);
            loader_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            loader_dialog.setContentView(R.layout.loader_layout);
            loader_imageview  = (GifImageView) loader_dialog.findViewById(R.id.loader_imageview);
            loader_dialog.show();
            loader_imageview.setBackgroundResource(R.drawable.heart_beat);

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loader_dialog.hide();

                    SchedulePopup();
                    //Toast.makeText(context, String.valueOf(final_flag) +""+getdeviceid, Toast.LENGTH_SHORT).show();
                    if(schedule_details != null){

                        scheduleListAdapter = new ScheduleListAdapter(getApplicationContext(),schedule_details);
                        list_view.setAdapter(scheduleListAdapter);
                        scheduleListAdapter.notifyDataSetChanged();
                    }
                }
            },1000);

        }
    }

    //DOCTOR LIST
    public class AsyncDoctorlist extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj, jsonObj_body,jsonObj_userinfo;
        JSONArray jsonArray;
        String message, token;
        String  success="";

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            try {
                //Make webservice connection and call APi

                RestAPI objRestAPI = new RestAPI();
                if (isNetworkAvailable()) {
                    jsonObj = objRestAPI.GetDoctorList(USER_CODE);
                    message = "";
                    token = "";


                    if(jsonObj!=null) {
                        jsonObj_body = new JSONObject(jsonObj.getString("body"));
                    }

                    if(jsonObj_body != null) {
                        jsonArray = jsonObj_body.getJSONArray("Doctorlist_Array");

                        if(jsonArray != null){
                            doctorlist_details.clear();
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject json = jsonArray.getJSONObject(i);
                                //  schedule_details.add(new Schedule_Details(json.getString("schedule_name"),json.getString("schedule_id"),json.getString("active_status")));
                                doctorlist_details.add(new Doctorlist_Details(json.getString("employee_id"),json.getString("employee_name"),
                                        json.getString("active_status")));
                                Log.d("MAIN ACTIVITY ACTIVITY ========(Doctor List Array)====("+String.valueOf(i+1)+")====>", json.toString());
                            }
                        }
                    }
                }
            }
            //Catch Block UserAuth true
            catch (Exception e) {
                Log.d("AsyncLoggerService", "Message");
                Log.d("AsyncLoggerService", e.getMessage());
            }
            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

//            progressDialog = new ProgressDialog(MainActivity.this);
//            progressDialog.setMessage("Loading..."); // Setting Message
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
//            progressDialog.show();
            loader_dialog = new Dialog(context);
            loader_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            loader_dialog.setContentView(R.layout.loader_layout);
            loader_imageview  = (GifImageView) loader_dialog.findViewById(R.id.loader_imageview);
            loader_dialog.show();
            loader_imageview.setBackgroundResource(R.drawable.heart_beat);

        }

        @Override
        protected void onPostExecute(Boolean result) {

//            if(progressDialog != null) {
//                progressDialog.hide();
//            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    loader_dialog.hide();

                    //Toast.makeText(context, String.valueOf(final_flag) +""+getdeviceid, Toast.LENGTH_SHORT).show();
                    doctorListAdapter = new DoctorListAdapter(getApplicationContext(),doctorlist_details);
                    list_view_doctor.setAdapter(doctorListAdapter);
                    doctorListAdapter.notifyDataSetChanged();

                    DoctorPopup();

                }
            }, 1000);

        }
    }

    //GET SAVE BOOKING
    public class AsyncUpdateBooking extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj, jsonObj_body,jsonObj_userinfo;
        JSONArray jsonArray;
        String message, token;
        String  success="";

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            try {
                //Make webservice connection and call APi

                RestAPI objRestAPI = new RestAPI();
                if (isNetworkAvailable()) {
                    //jsonObj = objRestAPI.GetUpdateBooking("2","2","TEST 111", "2","2022-11-22","6");
                    jsonObj = objRestAPI.GetUpdateBooking(USER_CODE,SCHEDULE_ID,DOCTOR_NOTE, BOOKING_ID,SELECTED_DATE,EMPLOYEE_ID);
                    Log.d("MAIN ACTIVITY ======(JSON PARAMS)======>"," USER_CODE : "+USER_CODE+" SCHEDULE_ID : "+SCHEDULE_ID+" DOCTOR_NOTE : "+DOCTOR_NOTE+" BOOKING_ID : "+BOOKING_ID+" SELECTED_DATE : "+SELECTED_DATE+" EMPLOYEE_ID : "+EMPLOYEE_ID);
                    message = "";

                    if(jsonObj!=null) {

                        message = new JSONObject(jsonObj.getString("body")).getString("message");
                    }

                }
            }
            //Catch Block UserAuth true
            catch (Exception e) {
                Log.d("AsyncLoggerService", "Message");
                Log.d("AsyncLoggerService", e.getMessage());
            }
            return true;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(Boolean result) {
            //Toast.makeText(context, String.valueOf(final_flag) +""+getdeviceid, Toast.LENGTH_SHORT).show();

            if(message.equals("success")) {
                patient_name_txt.setText("");
                patient_id_txt.setText("");
                schedule_time_txt.setText("");
                doctor_note_txt.setText("");
                booking_date_tv.setText("");
                doctor_name_txt.setText("");

                Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
                if(edit_dialog != null) {
                    edit_dialog.dismiss();
                }
                Get_List();
            }

        }
    }


    public class BookingListAdapter extends BaseAdapter {
        LayoutInflater inflater;
        Context context;
        ArrayList<Bookinglist_Details> bookinglist;

        public BookingListAdapter(Context context, ArrayList<Bookinglist_Details> myList) {
            this.bookinglist = myList;
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return bookinglist.size();
        }

        @Override
        public Bookinglist_Details getItem(int position) {
            return (Bookinglist_Details) bookinglist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            if (getCount() > 0) {
                return getCount();
            } else {
                return super.getViewTypeCount();
            }
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mHolder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.bookinglist_card, parent, false);
                mHolder = new ViewHolder();
                try {
                    mHolder.patient_name_tv = (TextView) convertView.findViewById(R.id.patient_name_tv);
                    mHolder.doctorname_tv = (TextView) convertView.findViewById(R.id.doctorname_tv);
                    mHolder.cityname_tv = (TextView) convertView.findViewById(R.id.cityname_tv);
                    mHolder.uhid_tv = (TextView) convertView.findViewById(R.id.uhid_tv);
                    mHolder.scheduletime_tv  = (TextView) convertView.findViewById(R.id.schedule_time_tv);

                } catch (Exception e) {
                    Log.i("Route ===============>", e.toString());

                }
                convertView.setTag(mHolder);
            } else {
                mHolder = (ViewHolder) convertView.getTag();
            }
            mHolder.patient_name_tv.setText(String.valueOf(bookinglist.get(position).getPatient_name()));
            mHolder.doctorname_tv.setText(String.valueOf(bookinglist.get(position).getEmployee_name()));
            mHolder.cityname_tv.setText(String.valueOf(bookinglist.get(position).getCity_name()));
            mHolder.uhid_tv.setText(String.valueOf(bookinglist.get(position).getUhid()));
            mHolder.scheduletime_tv.setText(String.valueOf(bookinglist.get(position).getSchedule_name()));
            //convertView.setBackgroundResource(R.drawable.custom_borberless_ripple);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle);
//                        builder.setMessage("Do you want to edit booking ?")
//                                .setCancelable(false)
//                                //.setIcon(R.mipmap.admin)
//                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        EditPopup(bookinglist.get(position));
//                                    }
//                                })
//                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        dialog.cancel();
//                                    }
//                                });
//                        AlertDialog alert = builder.create();
//                        alert.show();
                    EditPopup(bookinglist.get(position));

                }
            });
            return convertView;

        }

    }


    public class DoctorListAdapter extends BaseAdapter {
        LayoutInflater inflater;
        Context context;
        ArrayList<Doctorlist_Details> list;

        public DoctorListAdapter(Context context, ArrayList<Doctorlist_Details> myList) {
            this.list = myList;
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Doctorlist_Details getItem(int position) {
            return (Doctorlist_Details) list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            if (getCount() > 0) {
                return getCount();
            } else {
                return super.getViewTypeCount();
            }
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mHolder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.doctorlist_card, parent, false);
                mHolder = new ViewHolder();
                try {
                    mHolder.doctor_name_tv = (TextView) convertView.findViewById(R.id.doctor_name_tv);

                } catch (Exception e) {
                    Log.i("Route ===============>", e.toString());

                }
                convertView.setTag(mHolder);
            } else {
                mHolder = (ViewHolder) convertView.getTag();
            }
            mHolder.doctor_name_tv.setText(String.valueOf(list.get(position).getEmployee_name()));

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //list.get(position).getEmployee_id()
                    doctor_name_txt.setText(String.valueOf(list.get(position).getEmployee_name()));
                    EMPLOYEE_ID = list.get(position).getEmployee_id();
                    doctor_dialog.dismiss();
                }
            });
            return convertView;

        }

    }


    public class ScheduleListAdapter extends BaseAdapter {
        LayoutInflater inflater;
        Context context;
        ArrayList<Schedule_Details> myList;

        public ScheduleListAdapter(Context context, ArrayList<Schedule_Details> myList) {
            this.myList = myList;
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return myList.size();
        }

        @Override
        public Schedule_Details getItem(int position) {
            return (Schedule_Details) myList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            if (getCount() > 0) {
                return getCount();
            } else {
                return super.getViewTypeCount();
            }
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder mHolder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.schedulelist_card, parent, false);
                mHolder = new ViewHolder();
                try {
                    mHolder.schedule_tv = (TextView) convertView.findViewById(R.id.schedule_tv);
                    mHolder.appointment_count_tv = (TextView) convertView.findViewById(R.id.appointment_count_tv);
                } catch (Exception e) {
                    Log.i("Route ===============>", e.toString());
                }
                convertView.setTag(mHolder);
            } else {
                mHolder = (ViewHolder) convertView.getTag();
            }
          try{
              mHolder.schedule_tv.setText(String.valueOf(myList.get(position).getSchedule_name()));
                if(!myList.get(position).getCount().equals("0")) {
                    mHolder.appointment_count_tv.setText(String.valueOf(myList.get(position).getCount()));
                }
          } catch (Exception e) {
              e.printStackTrace();
          }
            //convertView.setBackgroundResource(R.drawable.custom_borberless_ripple);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    schedule_time_txt.setText("");
                    schedule_time_txt.setText(String.valueOf(myList.get(position).getSchedule_name()));
                    SCHEDULE_ID = myList.get(position).getSchedule_id();
                    if(schedule_dialog != null) {
                        schedule_dialog.dismiss();
                    }
                }
            });
            return convertView;

        }

    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private class ViewHolder {
        TextView doctorname_tv,uhid_tv,scheduletime_tv,patient_name_tv,cityname_tv;
        LinearLayout listLL;
        CardView card_view;
        TextView doctor_name_tv,appointment_count_tv,schedule_tv,remarks,gender_tv,areaname_tv,mobileno_tv;
    }

    public void goback() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setMessage("Are you sure you want to logout ?")
                .setTitle(COMPANY_NAME)
                .setIcon(R.mipmap.appointment)
                .setCancelable(false)
                //.setIcon(R.mipmap.admin)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent logout = new Intent(MainActivity.this, Login_Activity.class);
                        MainActivity.this.finish();
                        startActivity(logout);
                        //.setIcon(R.mipmap.admin)
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void Exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setMessage("Are you sure you want to exit ?")
                .setTitle("Clinic")
                .setIcon(R.drawable.ic_launcher_foreground)
                .setCancelable(false)
                //.setIcon(R.mipmap.admin)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                        //.setIcon(R.mipmap.admin)
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void openDatePickerDialog(final View v) {
        Calendar cal = Calendar.getInstance();

        Calendar cal1 = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        cal1.add(Calendar.DAY_OF_YEAR, -10);
        // Date mindate = s.format(new Date(cal1.getTimeInMillis()));


        // Get Current Date
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    //String selectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                    String selectedDate =String.format("%02d-%02d-%d", dayOfMonth,(monthOfYear + 1), year);
                    date_tv.setText(String.valueOf(selectedDate));
                    DATE = selectedDate;

                    Get_List();
                    Log.d("MAIN ACITIVITY ======(SELECTED DATE )======>",DATE);
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        // datePickerDialog.getDatePicker().setMinDate(cal1.getTimeInMillis());
        // datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePickerDialog.show();
    }

    public void openDatePickerDialog_db(final View v) {
        Calendar cal = Calendar.getInstance();

        Calendar cal1 = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        cal1.add(Calendar.DAY_OF_YEAR, -10);
        // Date mindate = s.format(new Date(cal1.getTimeInMillis()));


        // Get Current Date
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    //String selectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                    String selectedDate =String.format("%02d-%02d-%d", dayOfMonth,(monthOfYear + 1), year);
                    String selectedDate_db =String.format("%d-%02d-%02d", year,(monthOfYear + 1), dayOfMonth);
                    booking_date_tv.setText(String.valueOf(selectedDate));
                    SELECTED_DATE = selectedDate_db;
                    Log.d("MAIN ACITIVITY ======(SELECTED DATE )======>",SELECTED_DATE);
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

         datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        // datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePickerDialog.show();
    }

    private void Get_List() {
        new AsyncBookinglist().execute();
    }

    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
        Exit();
    }
}