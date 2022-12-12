package com.example.clinicmanagement;

import static com.example.clinicmanagement.Login_Activity.USER_CODE;
import static com.example.clinicmanagement.Login_Activity.USER_ID;
import static com.example.clinicmanagement.Splash_Screen.COMPANY_NAME;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.util.Pair;


import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import pl.droidsonroids.gif.GifImageView;


public class Appointment_Activity extends AppCompatActivity {
    String DATE ="";
   Calendar cal;
    SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat mdformat_db = new SimpleDateFormat("yyyy-MM-dd");
    TextView title_tv,doctor_name_txt,doctor_title_tv,booking_date_tv,date_tv,from_to_date_tv,schedule_time_txt,save_txt;
    ImageView  close_doctor,power_img,search_enter,search_clear,close,back_btn;
    LinearLayout LL_booking_screen,LL_date,LL_search_list;
    EditText search_edittext,doctor_note_txt,patient_name_txt,patient_id_txt;
    LinearLayout search_card,LL_top;
    String[] datestr ;
    String startDateStr,strDate,SELECTED_DATE="",EMPLOYEE_ID="";
    Date monthFirstDay;
    Date monthLastDay;
    Calendar calendar1;
    ArrayList<String> schedule_time = new ArrayList<>();
    ArrayAdapter spin_adapter;
    GifImageView loader_imageview;
    Dialog schedule_dialog,doctor_dialog,loader_dialog;
    Context context;
    ListView list_view,search_listview,list_view_doctor;
    ScheduleListAdapter scheduleListAdapter;
    SearchListAdapter searchListAdapter;
    ArrayList<Schedule_Details> schedule_details = new ArrayList<>();
    ArrayList<Searchlist_Details> searchlist_details = new ArrayList<>();
    ArrayList<Doctorlist_Details> doctorlist_details = new ArrayList<>();
    private boolean doubleBackToExitPressedOnce;
    private Handler mHandler;
    String PATIENT_TOKEN ="",SAVE_BOOKING_TOKEN="";
    String PATIENT_NAME ="",PATIENT_ID="",SCHEDULE_ID = "",DOCTOR_NOTE="";
    ProgressDialog progressDialog;
    DoctorListAdapter doctorListAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        context = this;
        mHandler = new Handler();
        date_tv = (TextView) findViewById(R.id.date_tv);
        booking_date_tv = (TextView) findViewById(R.id.booking_date_tv);
        power_img = (ImageView) findViewById(R.id.power_img);

        from_to_date_tv = (TextView) findViewById(R.id.from_to_date_tv);

        search_enter = (ImageView) findViewById(R.id.search_enter);
        search_clear = (ImageView) findViewById(R.id.search_clear);

        LL_booking_screen  = (LinearLayout) findViewById(R.id.LL_booking_screen);
        LL_date  = (LinearLayout) findViewById(R.id.LL_date);
        LL_search_list = (LinearLayout) findViewById(R.id.LL_search_list);
        LL_top = (LinearLayout) findViewById(R.id.LL_top);



        search_edittext = (EditText) findViewById(R.id.search_edittext);

        search_card = (LinearLayout) findViewById(R.id.search_card);

        patient_name_txt  = (EditText) findViewById(R.id.patient_name_txt);
        patient_id_txt  = (EditText) findViewById(R.id.patient_id_txt);
        doctor_note_txt = (EditText) findViewById(R.id.doctor_note_txt);
        schedule_time_txt  = (TextView) findViewById(R.id.schedule_time_txt);
        save_txt  = (TextView) findViewById(R.id.save_txt);

        search_listview = (ListView) findViewById(R.id.search_listview);

        back_btn = (ImageView) findViewById(R.id.back_btn);

        doctor_name_txt = (TextView) findViewById(R.id.doctor_name_txt);

        schedule_dialog = new Dialog(context);
        schedule_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        schedule_dialog.setContentView(R.layout.schedule_popup);
        list_view = (ListView) schedule_dialog.findViewById(R.id.list_view);
        close = (ImageView) schedule_dialog.findViewById(R.id.close);
        title_tv  = (TextView) schedule_dialog.findViewById(R.id.title_tv);

        doctor_dialog = new Dialog(context);
        doctor_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        doctor_dialog.setContentView(R.layout.schedule_popup);

        doctor_title_tv = (TextView) doctor_dialog.findViewById(R.id.title_tv);
        close_doctor = (ImageView) doctor_dialog.findViewById(R.id.close);
        list_view_doctor  = (ListView) doctor_dialog.findViewById(R.id.list_view);

        doctor_title_tv.setText("Select Doctor");
        title_tv.setText("Select Schedule Time");

        doctor_name_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doctor_name_txt.setError(null);
                new AsyncDoctorlist().execute();
            }
        }) ;
        close_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doctor_dialog.dismiss();
            }
        });

              /* doctor_note_txt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(LL_top.getVisibility() ==View.VISIBLE){
                    LL_top.setVisibility(View.GONE);
                }
                doubleBackToExitPressedOnce  = false;

                return false;
            }
        });*/


        save_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PATIENT_NAME = patient_name_txt.getText().toString();
                //PATIENT_ID = patient_id_txt.getText().toString();
                DOCTOR_NOTE = doctor_note_txt.getText().toString();

              //               if(PATIENT_NAME.equals("")){
//                    patient_name_txt.setError("Please enter patient name");
//                }
//                if(PATIENT_ID.equals("")){
//                    patient_id_txt.setError("Please enter patient id");
//                }
                if(PATIENT_ID.equals("")){
                    patient_id_txt.setError("Select patient id");
                }
                if(PATIENT_NAME.equals("")){

                    patient_name_txt.setError("Select patient name");
                }
                if(SCHEDULE_ID.equals("")){
                    schedule_time_txt.setError("Select schedule time");
                }
                if(EMPLOYEE_ID.equals("")){
                    doctor_name_txt.setError("Select doctor");
                }

                if(!PATIENT_NAME.equals("") &&  !PATIENT_ID.equals("") && !SCHEDULE_ID.equals("") && !EMPLOYEE_ID.equals("")){
                    new AsyncSaveBookingJwt().execute(PATIENT_ID,USER_ID,SCHEDULE_ID,DOCTOR_NOTE,SELECTED_DATE,EMPLOYEE_ID);
                }

            }
        });
        if(close != null) {
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    schedule_dialog.dismiss();
                }
            });
        }
        // store as "Long" type value
        MaterialDatePicker.Builder<Pair<Long, Long>> materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker();

        // now define the properties of the
        // materialDateBuilder
        materialDateBuilder.setTitleText("SELECT A DATE");

        // now create the instance of the material date
        // picker

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        monthFirstDay = calendar.getTime();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        monthLastDay = calendar.getTime();

        mdformat = new SimpleDateFormat("dd-MM-yyyy ");

        startDateStr = mdformat.format(monthFirstDay);
        String endDateStr = mdformat.format(monthLastDay);

        calendar1 = Calendar.getInstance();
        strDate = mdformat.format(calendar1.getTime());

        from_to_date_tv.setText(startDateStr +" to "+strDate);
        String getdate = from_to_date_tv.getText().toString();

        datestr = getdate.split(" to ");

        schedule_time.add("09:00");schedule_time.add("09:30");
        schedule_time.add("10:00");schedule_time.add("10:30");
        schedule_time.add("11:00");schedule_time.add("11:30");
        schedule_time.add("12:00");schedule_time.add("12:30");
        schedule_time.add("01:00");schedule_time.add("01:30");
        schedule_time.add("02:00");schedule_time.add("02:30");
        schedule_time.add("03:00");schedule_time.add("03:30");
        schedule_time.add("04:00");schedule_time.add("04:30");
        schedule_time.add("05:00");schedule_time.add("05:30");
        schedule_time.add("06:00");schedule_time.add("06:30");
        schedule_time.add("07:00");schedule_time.add("07:30");
        schedule_time.add("08:00");schedule_time.add("08:30");
        schedule_time.add("09:00");

        /*schedule_time.add("09:30");
        schedule_time.add("10:00");schedule_time.add("10:30");
        schedule_time.add("11:00");schedule_time.add("11:30");
        schedule_time.add("12:00");*/

        // handle select date button which opens the
        // material design date picker

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Appointment_Activity.this, MainActivity.class);
                Appointment_Activity.this.finish();
                startActivity(i);
            }
        });

        from_to_date_tv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        /*  // getSupportFragmentManager() to
                        // interact with the fragments
                        // associated with the material design
                        // date picker tag is to get any error
                        // in logcat
                        final MaterialDatePicker<Pair<Long, Long>> materialDatePicker = materialDateBuilder.build();
                       // materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                        // now handle the positive button click from the
                        // material design date picker
                        materialDatePicker.addOnPositiveButtonClickListener(
                                new MaterialPickerOnPositiveButtonClickListener() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void onPositiveButtonClick(Object selection) {

                                        // if the user clicks on the positive
                                        // button that is ok button update the
                                        // selected date
                                        from_to_date_tv.setText(materialDatePicker.getHeaderText());
                                        // in the above statement, getHeaderText
                                        // will return selected date preview from the
                                        // dialog
                                    }
                                });*/

                        /*MaterialDatePicker
                                .Builder
                                .dateRangePicker()
                                .setTitleText("Select a date")
                                .build()
                                .show(((AppCompatActivity) Appointment_Activity.this).getSupportFragmentManager(), "DATE_PICKER_RANGE");*/

                        SmoothDateRangePickerFragment smoothDateRangePickerFragment =
                                SmoothDateRangePickerFragment
                                        .newInstance(new SmoothDateRangePickerFragment.OnDateRangeSetListener() {
                                            @Override
                                            public void onDateRangeSet(SmoothDateRangePickerFragment view,
                                                                       int yearStart, int monthStart,
                                                                       int dayStart, int yearEnd,
                                                                       int monthEnd, int dayEnd) {
                                                String day;
                                                if (dayStart < 10) {
                                                    day = "0" + String.valueOf((dayStart));
                                                } else {
                                                    day = String.valueOf((dayStart));
                                                }
                                                String endday;
                                                if (dayEnd < 10) {
                                                    endday = "0" + String.valueOf((dayEnd));
                                                } else {
                                                    endday = String.valueOf((dayEnd));
                                                }
                                                String monthvalue;
                                                int startval = ++monthStart;
                                                if (startval < 10) {
                                                    monthvalue = "0" + String.valueOf((startval));
                                                } else {
                                                    monthvalue = String.valueOf((startval));
                                                }
                                                String monthendvalue;
                                                int endval = ++monthEnd;
                                                if (endval < 10) {
                                                    monthendvalue = "0" + String.valueOf((endval));
                                                } else {
                                                    monthendvalue = String.valueOf((endval));
                                                }
                                                String date = (day) + "-" + (monthvalue)
                                                        + "-" + yearStart + " to " + endday + "-"
                                                        + (monthendvalue) + "-" + yearEnd;
                                                from_to_date_tv.setText(date);
                                                String getdate = from_to_date_tv.getText().toString();
                                                datestr = getdate.split(" to ");
                                                new AsyncGetPatientsJwt().execute("","2",datestr[0],datestr[1]);
                                            }
                                        });

                        smoothDateRangePickerFragment.setMaxDate(calendar1);
                        smoothDateRangePickerFragment.show(getFragmentManager(), "Datepickerdialog");

                    }
                });


        //listview_card.setAdapter(scheduleListAdapter);
        schedule_time_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                schedule_time_txt.setError(null);
             //     if(!booking_date_tv.getText().toString().equals("")) {
                    OpenScheduleDialog();
             //                }else{
//                    Toast.makeText(context, "Please select booking date", Toast.LENGTH_SHORT).show();
//                }
            }
        });
        spin_adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,schedule_time) {
            @SuppressLint("ResourceAsColor")
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textview = (TextView) view;
                         textview.setEnabled(false);
                //    textview.setTextColor(R.color.black);
                return view;
            }

            @Override
            public boolean isEnabled(int position) {
             //   if (position == 0) {
             //      return false;
             //   } else {
                    return true;
              //  }
            }

        };

        search_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()>0) {
               //                        if (LL_search_list.getVisibility() == View.GONE) {
//                            LL_search_list.setVisibility(View.VISIBLE);
//                        }
//
//                        if(LL_date.getVisibility() == View.GONE){
//                            LL_date.setVisibility(View.VISIBLE);
//                        }
//
//                        if(LL_booking_screen.getVisibility() ==View.VISIBLE){
//                            LL_booking_screen.setVisibility(View.GONE);
//                        }
                    if(search_clear.getVisibility() == View.VISIBLE){
                        search_clear.setVisibility(View.GONE);
                    }
                    if(search_enter.getVisibility() ==View.GONE){
                        search_enter.setVisibility(View.VISIBLE);
                    }
                }
                /*if(editable.length()==0) {
                    if(search_clear.getVisibility() == View.VISIBLE){
                        search_clear.setVisibility(View.GONE);
                    }
                    if(search_enter.getVisibility() ==View.GONE){
                        search_enter.setVisibility(View.VISIBLE);
                    }
                }*/
            }
        });

        search_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String SEARCH_VALUE ="";

                if(LL_date.getVisibility() == View.VISIBLE){
                    LL_date.setVisibility(View.GONE);
                }

                if(search_edittext != null) {
                    if (search_edittext.getText().toString().length() >=3) {
                        patient_id_txt.setError(null);
                        patient_name_txt.setError(null);

                        if(search_clear.getVisibility() == View.GONE){
                            search_clear.setVisibility(View.VISIBLE);
                        }
                        if(search_enter.getVisibility() ==View.VISIBLE){
                            search_enter.setVisibility(View.GONE);
                        }

                        new AsyncGetPatientsJwt().execute(search_edittext.getText().toString().toLowerCase(),"1","","");

                    }else{
                        Toast.makeText(context, "Please enter minimum three character", Toast.LENGTH_SHORT).show();

                        if(LL_date.getVisibility() == View.VISIBLE){
                            LL_date.setVisibility(View.GONE);
                        }
                        if (LL_search_list.getVisibility() == View.VISIBLE) {
                            LL_search_list.setVisibility(View.GONE);
                        }
                        if(LL_booking_screen.getVisibility() ==View.GONE){
                            LL_booking_screen.setVisibility(View.VISIBLE);
                        }
                    }

                }

            }
        });
        search_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(search_clear.getVisibility() == View.VISIBLE){
                    search_clear.setVisibility(View.GONE);
                }
                if(search_enter.getVisibility() ==View.GONE){
                    search_enter.setVisibility(View.VISIBLE);
                }
                search_edittext.setText("");
                search_listview.setAdapter(null);
                if(LL_date.getVisibility() == View.VISIBLE){
                    LL_date.setVisibility(View.GONE);
                }

                if (LL_search_list.getVisibility() == View.VISIBLE) {
                    LL_search_list.setVisibility(View.GONE);
                }

                if(LL_booking_screen.getVisibility() ==View.GONE){
                    LL_booking_screen.setVisibility(View.VISIBLE);
                }
            }
        });
        power_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goback();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            calendar = Calendar.getInstance();
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

        booking_date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePickerDialog(view);
            }
        });

        if (LL_search_list.getVisibility() == View.GONE) {
            LL_search_list.setVisibility(View.VISIBLE);
        }

        if(LL_date.getVisibility() == View.VISIBLE){
            LL_date.setVisibility(View.GONE);
        }
        if(search_card.getVisibility() == View.GONE){
            search_card.setVisibility(View.VISIBLE);
        }
        if(LL_booking_screen.getVisibility() ==View.GONE){
            LL_booking_screen.setVisibility(View.VISIBLE);
        }

       // new AsyncSchedule().execute();

        patient_name_txt.setFocusable(false);
        patient_id_txt.setFocusable(false);
    }

    private void OpenScheduleDialog() {
        if(!booking_date_tv.getText().toString().equals("")) {
            new AsyncSchedule().execute();
        }else{
            Toast.makeText(context, "Please select booking date", Toast.LENGTH_SHORT).show();
        }
    }

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String str="";
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rdb_name_id:
                if(checked)
                    str = "Name ID Selected";
               /* if(LL_top.getVisibility() == View.GONE){
                    LL_top.setVisibility(View.VISIBLE);
                }*/
                if (LL_search_list.getVisibility() == View.GONE) {
                    LL_search_list.setVisibility(View.VISIBLE);
                }

                if(LL_date.getVisibility() == View.VISIBLE){
                    LL_date.setVisibility(View.GONE);
                }
                if(search_card.getVisibility() == View.GONE){
                    search_card.setVisibility(View.VISIBLE);
                }
                if(LL_booking_screen.getVisibility() ==View.GONE){
                    LL_booking_screen.setVisibility(View.VISIBLE);
                }
                search_listview.setAdapter(null);
                search_edittext.setText("");

                patient_name_txt.setText("");
                patient_id_txt.setText("");
                schedule_time_txt.setText("");
                doctor_note_txt.setText("");
                DOCTOR_NOTE ="";
                SCHEDULE_ID = "";
                booking_date_tv.setText(String.valueOf(DATE));
                cal = Calendar.getInstance();
                SELECTED_DATE = mdformat_db.format(cal.getTime());
                //  patient_name_txt.setFocusableInTouchMode(true);
              //  patient_id_txt.setFocusableInTouchMode(true);
                break;
            case R.id.rdb_date:
                if(checked)
                    str = "DATE  Selected";
                /*if(LL_top.getVisibility() == View.GONE){
                    LL_top.setVisibility(View.VISIBLE);
                }*/
                if (LL_search_list.getVisibility() == View.GONE) {
                    LL_search_list.setVisibility(View.VISIBLE);
                }

                if(LL_date.getVisibility() == View.GONE){
                    LL_date.setVisibility(View.VISIBLE);
                }
                if(search_card.getVisibility() == View.VISIBLE){
                    search_card.setVisibility(View.GONE);
                }
                if(LL_booking_screen.getVisibility() ==View.GONE){
                    LL_booking_screen.setVisibility(View.VISIBLE);
                }
               // new AsyncGetPatientsJwt().execute("","2","15-11-2022","22-11-2022");
                search_listview.setAdapter(null);
                search_edittext.setText("");

                patient_name_txt.setText("");
                patient_id_txt.setText("");

                schedule_time_txt.setText("");
                doctor_note_txt.setText("");
                DOCTOR_NOTE ="";
                SCHEDULE_ID = "";

                booking_date_tv.setText(String.valueOf(DATE));

                cal = Calendar.getInstance();
                SELECTED_DATE = mdformat_db.format(cal.getTime());

                //  patient_name_txt.setFocusableInTouchMode(true);
                //  patient_id_txt.setFocusableInTouchMode(true);
                break;
            default:
                break;

        }
                 //   Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        Log.d("APPOINTMENT ACTIVITY ====(OPTION SELECTION)====>",str);

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
                        Intent logout = new Intent(Appointment_Activity.this, Login_Activity.class);
                        Appointment_Activity.this.finish();
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


    public void openDatePickerDialog(final View v) {
        Calendar cal = Calendar.getInstance();

        Calendar cal1 = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        //cal1.add(Calendar.DAY_OF_YEAR,0);
        // Date mindate = s.format(new Date(cal1.getTimeInMillis()));


        // Get Current Date
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    //String selectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                    String selectedDate =String.format("%02d-%02d-%d", dayOfMonth,(monthOfYear + 1), year);
                    String selectedDate_db =String.format("%d-%02d-%02d", year,(monthOfYear + 1), dayOfMonth);

                    //date_tv.setText(String.valueOf(selectedDate));
                    booking_date_tv.setText(String.valueOf(selectedDate));

                    SELECTED_DATE = selectedDate_db;
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

         datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        // datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePickerDialog.show();
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
            mHolder.schedule_tv.setText(String.valueOf(myList.get(position).getSchedule_name()));
            try{
                if(!myList.get(position).getCount().equals("0")){
                    mHolder.appointment_count_tv.setText(String.valueOf(myList.get(position).getCount()));
                    //convertView.setBackgroundResource(R.drawable.custom_borberless_ripple);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


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


    public class SearchListAdapter extends BaseAdapter {
        LayoutInflater inflater;
        Context context;
        ArrayList<Searchlist_Details> searchList;

        public SearchListAdapter(Context context, ArrayList<Searchlist_Details> myList) {
            this.searchList = myList;
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return searchList.size();
        }

        @Override
        public Searchlist_Details getItem(int position) {
            return (Searchlist_Details) searchList.get(position);
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
                convertView = inflater.inflate(R.layout.searchlist_card, parent, false);
                mHolder = new ViewHolder();
                try {
                    mHolder.patient_name_tv = (TextView) convertView.findViewById(R.id.patient_name_tv);
                    mHolder.gender_tv = (TextView) convertView.findViewById(R.id.gender_tv);
                    mHolder.areaname_tv = (TextView) convertView.findViewById(R.id.areaname_tv);
                    mHolder.mobileno_tv = (TextView) convertView.findViewById(R.id.mobileno_tv);

                } catch (Exception e) {
                    Log.i("Route ===============>", e.toString());

                }
                convertView.setTag(mHolder);
            } else {
                mHolder = (ViewHolder) convertView.getTag();
            }
            mHolder.patient_name_tv.setText(String.valueOf(searchList.get(position).getPatient_name()));
            mHolder.gender_tv.setText(String.valueOf(searchList.get(position).getGender_name()));
            mHolder.areaname_tv.setText(String.valueOf(searchList.get(position).getCity_name()));
            mHolder.mobileno_tv.setText(String.valueOf(searchList.get(position).getMobile_number()));
            //convertView.setBackgroundResource(R.drawable.custom_borberless_ripple);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    patient_name_txt.setText("");
                    patient_id_txt.setText("");
                    if (LL_search_list.getVisibility() == View.VISIBLE) {
                        LL_search_list.setVisibility(View.GONE);
                    }
                    if(LL_booking_screen.getVisibility() ==View.GONE){
                        LL_booking_screen.setVisibility(View.VISIBLE);
                    }
                    search_listview.setAdapter(null);
                    patient_name_txt.setText(String.valueOf(searchList.get(position).getPatient_name()));
                    patient_id_txt.setText(String.valueOf(searchList.get(position).getUhid()));

                    PATIENT_ID = searchList.get(position).getPatient_id();

                    patient_name_txt.setFocusable(false);
                    patient_id_txt.setFocusable(false);
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

//            progressDialog = new ProgressDialog(Appointment_Activity.this);
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
            try{
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void DoctorPopup() {
        doctor_dialog.show();
    }

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

                    //Toast.makeText(context, String.valueOf(final_flag) +""+getdeviceid, Toast.LENGTH_SHORT).show();
                    if(schedule_dialog != null) {
                        schedule_dialog.show();
                    }
                    if(schedule_details != null){
                        scheduleListAdapter = new ScheduleListAdapter(getApplicationContext(),schedule_details);
                        list_view.setAdapter(scheduleListAdapter);
                        scheduleListAdapter.notifyDataSetChanged();
                    }
                }
            },1000);


        }
    }

    //GET searchPatientsJwt
    public class AsyncGetPatientsJwt extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj, jsonObj_body,jsonObj_userinfo;
        JSONArray jsonArray;
        String message, Patient_token;
        String  success="";

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            try {
                //Make webservice connection and call APi

                RestAPI objRestAPI = new RestAPI();
                if (isNetworkAvailable()) {
                    jsonObj = objRestAPI.GetPatientJWT(params[0],params[1],params[2],params[3]);
                    message = "";
                    PATIENT_TOKEN = "";


                    if(jsonObj!=null) {
                      //  jsonObj_body = new JSONObject(jsonObj.getString("body"));
                        PATIENT_TOKEN = new JSONObject(jsonObj.getString("body")).getString("token");
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

            Log.d("APPONITMENT ACTIVITY ========(PATIENT TOKEN)=======>",  PATIENT_TOKEN);
            if(!PATIENT_TOKEN.equals("")){
                new AsyncSearchlist().execute();
            }
        }
    }

    //GET SEARCH LIST
    public class AsyncSearchlist extends
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
                    jsonObj = objRestAPI.GetPatient(PATIENT_TOKEN);
                    message = "";
                    token = "";


                    if(jsonObj!=null) {
                        jsonObj_body = new JSONObject(jsonObj.getString("body"));
                    }

                    if(jsonObj_body != null) {
                        jsonArray = jsonObj_body.getJSONArray("Searchlist_Array");

                        if(jsonArray != null){
                            searchlist_details.clear();
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject json = jsonArray.getJSONObject(i);
                                //  schedule_details.add(new Schedule_Details(json.getString("schedule_name"),json.getString("schedule_id"),json.getString("active_status")));
                                searchlist_details.add(new Searchlist_Details(json.getString("patient_id"),json.getString("patient_name"),
                                                      json.getString("guardian_name"),json.getString("date_of_birth"),json.getString("gender_id"),
                                                      json.getString("city_id"),json.getString("mobile_number"),json.getString("uhid"),
                                                      json.getString("age_day"), json.getString("age_month"), json.getString("age_year"),
                                                      json.getString("dob_type"), json.getString("gender_name"), json.getString("city_name")));
                                Log.d("APPONITMENT ACTIVITY ========(Searchlist Array)====("+String.valueOf(i+1)+")====>", json.toString());
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
//            progressDialog = new ProgressDialog(Appointment_Activity.this);
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
            if(searchlist_details.size()>0){

                if (LL_search_list.getVisibility() == View.GONE) {
                    LL_search_list.setVisibility(View.VISIBLE);
                }

                if(LL_booking_screen.getVisibility() ==View.VISIBLE){
                    LL_booking_screen.setVisibility(View.GONE);
                }
                searchListAdapter = new SearchListAdapter(getApplicationContext(),searchlist_details);
                search_listview.setAdapter(searchListAdapter);
                searchListAdapter.notifyDataSetChanged();
            }else{
                search_listview.setAdapter(null);

                if (LL_search_list.getVisibility() == View.VISIBLE) {
                    LL_search_list.setVisibility(View.GONE);
                }

                if(LL_booking_screen.getVisibility() ==View.GONE){
                    LL_booking_screen.setVisibility(View.VISIBLE);
                }
                Toast.makeText(context, "No record found", Toast.LENGTH_SHORT).show();

            }
            //InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
           // imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }
            }, 1000);
        }
    }

    //GET saveBookingJwt
    public class AsyncSaveBookingJwt extends
            AsyncTask<String, JSONObject,Boolean> {
        JSONObject jsonObj, jsonObj_body,jsonObj_userinfo;
        JSONArray jsonArray;
        String message, Patient_token;
        String  success="";

        @Override
        protected Boolean doInBackground(String... params) {
            //Try Block
            try {
                //Make webservice connection and call APi

                RestAPI objRestAPI = new RestAPI();
                if (isNetworkAvailable()) {
                    jsonObj = objRestAPI.GetSaveBookingJWT(params[0],params[1],params[2],params[3],params[4],params[5]);
                    message = "";
                    SAVE_BOOKING_TOKEN = "";


                    if(jsonObj!=null) {
                        //  jsonObj_body = new JSONObject(jsonObj.getString("body"));
                        SAVE_BOOKING_TOKEN = new JSONObject(jsonObj.getString("body")).getString("token");
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

            Log.d("APPONITMENT ACTIVITY ========(SaveBookingJwt TOKEN)=======>",  SAVE_BOOKING_TOKEN);
            if(!SAVE_BOOKING_TOKEN.equals("")){
                new AsyncSaveBooking().execute();
            }
        }
    }

    //GET SAVE BOOKING
    public class AsyncSaveBooking extends
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
                    jsonObj = objRestAPI.GetSaveBooking(SAVE_BOOKING_TOKEN);
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
                search_edittext.setText("");

                doctor_name_txt.setText("");
                booking_date_tv.setText("");

                if(search_clear.getVisibility() == View.VISIBLE){
                    search_clear.setVisibility(View.GONE);
                }
                if(search_enter.getVisibility() ==View.GONE){
                    search_enter.setVisibility(View.VISIBLE);
                }

                Toast.makeText(context, "Saved Successfully", Toast.LENGTH_SHORT).show();

            }

        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private class ViewHolder {
        TextView appointment_count_tv,doctor_name_tv,schedule_tv,remarks,patient_name_tv,gender_tv,areaname_tv,mobileno_tv;
        LinearLayout listLL;
        CardView card_view;
    }

    @Override
    public void onBackPressed() {

         //  if(LL_top.getVisibility() ==View.GONE){
     //       LL_top.setVisibility(View.VISIBLE);
      //  }
        Go_Back();
         //  super.onBackPressed();
         /*   if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
       // Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        mHandler.postDelayed(mRunnable, 2000);
        */
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        if (mHandler != null) { mHandler.removeCallbacks(mRunnable); }
    }

    public void Go_Back() {
        /* Intent i=new Intent(Appointment_Activity.this,MainActivity.class);
        Appointment_Activity.this.finish();
        startActivity(i);
       */
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setMessage("Are you sure want to go back ?")
                .setIcon(R.mipmap.appointment)
                .setTitle(COMPANY_NAME)
              //  .setIcon(R.drawable.ic_launcher_foreground)
                .setCancelable(false)
                //.setIcon(R.mipmap.admin)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i=new Intent(Appointment_Activity.this,MainActivity.class);
                        Appointment_Activity.this.finish();
                        startActivity(i);
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

}
