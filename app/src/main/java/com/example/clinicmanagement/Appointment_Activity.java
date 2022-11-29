package com.example.clinicmanagement;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.util.Pair;


import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Appointment_Activity extends AppCompatActivity {
    String DATE ="";
    android.icu.util.Calendar calendar;
    SimpleDateFormat mdformat = new SimpleDateFormat("dd-MM-yyyy");
    TextView date_tv,from_to_date_tv,schedule_time_txt;
    ImageView  power_img,search_enter,search_clear,close;
    LinearLayout LL_booking_screen,LL_date,LL_search_list;
    EditText search_edittext;
    LinearLayout search_card;
    String[] datestr ;
    String startDateStr,strDate;
    Date monthFirstDay;
    Date monthLastDay;
    Calendar calendar1;
    ArrayList<String> schedule_time = new ArrayList<>();
    ArrayAdapter spin_adapter;
    Dialog schedule_dialog;
    Context context;
    ListView list_view,listview_card;
    ScheduleListAdapter scheduleListAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        context = this;
        date_tv = (TextView) findViewById(R.id.date_tv);
        power_img = (ImageView) findViewById(R.id.power_img);

        from_to_date_tv = (TextView) findViewById(R.id.from_to_date_tv);

        search_enter = (ImageView) findViewById(R.id.search_enter);
        search_clear = (ImageView) findViewById(R.id.search_clear);

        LL_booking_screen  = (LinearLayout) findViewById(R.id.LL_booking_screen);
        LL_date  = (LinearLayout) findViewById(R.id.LL_date);
        LL_search_list = (LinearLayout) findViewById(R.id.LL_search_list);

        search_edittext = (EditText) findViewById(R.id.search_edittext);

        search_card = (LinearLayout) findViewById(R.id.search_card);

        schedule_time_txt  = (TextView) findViewById(R.id.schedule_time_txt);

        listview_card = (ListView) findViewById(R.id.listview_card);

        schedule_dialog = new Dialog(context);
        schedule_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        schedule_dialog.setContentView(R.layout.schedule_popup);
        list_view = (ListView) schedule_dialog.findViewById(R.id.list_view);
        close = (ImageView) schedule_dialog.findViewById(R.id.close);

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
        schedule_time.add("13:00");schedule_time.add("13:30");
        schedule_time.add("14:00");schedule_time.add("14:30");
        schedule_time.add("15:00");schedule_time.add("15:30");
        schedule_time.add("16:00");schedule_time.add("16:30");
        schedule_time.add("17:00");schedule_time.add("17:30");
        schedule_time.add("18:00");schedule_time.add("18:30");
        schedule_time.add("19:00");schedule_time.add("19:30");
        schedule_time.add("20:00");schedule_time.add("20:30");
        schedule_time.add("21:00");schedule_time.add("21:30");
        schedule_time.add("22:00");schedule_time.add("22:30");
        schedule_time.add("23:00");schedule_time.add("23:30");
        schedule_time.add("24:00");

        // handle select date button which opens the
        // material design date picker
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

                                            }
                                        });
                        smoothDateRangePickerFragment.setMaxDate(calendar1);
                        smoothDateRangePickerFragment.show(getFragmentManager(), "Datepickerdialog");

                    }
                });

        scheduleListAdapter = new ScheduleListAdapter(getApplicationContext(),schedule_time);
        list_view.setAdapter(scheduleListAdapter);
        scheduleListAdapter.notifyDataSetChanged();
        //listview_card.setAdapter(scheduleListAdapter);
        schedule_time_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenScheduleDialog();
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
                    if(search_clear.getVisibility() == View.GONE){
                        search_clear.setVisibility(View.VISIBLE);
                    }
                }
                if(editable.length()==0) {
                    if(search_clear.getVisibility() == View.VISIBLE){
                        search_clear.setVisibility(View.GONE);
                    }
                }
            }
        });

        search_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LL_search_list.getVisibility() == View.GONE) {
                    LL_search_list.setVisibility(View.VISIBLE);
                }

                if(LL_date.getVisibility() == View.VISIBLE){
                    LL_date.setVisibility(View.GONE);
                }

                if(LL_booking_screen.getVisibility() ==View.VISIBLE){
                    LL_booking_screen.setVisibility(View.GONE);
                }
            }
        });
        search_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_edittext.setText("");
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
        }
        try {
            date_tv.setText(String.valueOf(DATE));
        } catch (Exception e) {
            e.printStackTrace();
        }

        date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // openDatePickerDialog(view);
            }
        });

    }

    private void OpenScheduleDialog() {
        if(schedule_dialog != null) {
            schedule_dialog.show();
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        String str="";
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rdb_name_id:
                if(checked)
                    str = "Name ID Selected";
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
                break;
            case R.id.rdb_date:
                if(checked)
                    str = "DATE  Selected";
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
                break;

        }
     //   Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
        Log.d("APPOINTMENT ACTIVITY ====(OPTION SELECTION)====>",str);

    }

    public void goback() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setMessage("Are you sure you want to logout ?")
                .setTitle("Clinic")
                .setIcon(R.drawable.ic_launcher_foreground)
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
        cal1.add(Calendar.DAY_OF_YEAR, -10);
        // Date mindate = s.format(new Date(cal1.getTimeInMillis()));


        // Get Current Date
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    //String selectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                    String selectedDate =String.format("%02d-%02d-%d", dayOfMonth,(monthOfYear + 1), year);
                    date_tv.setText(String.valueOf(selectedDate));
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        // datePickerDialog.getDatePicker().setMinDate(cal1.getTimeInMillis());
        // datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        datePickerDialog.show();
    }

    public class ScheduleListAdapter extends BaseAdapter {
        LayoutInflater inflater;
        Context context;
        ArrayList<String> myList;

        public ScheduleListAdapter(Context context, ArrayList<String> myList) {
            this.myList = myList;
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return myList.size();
        }

        @Override
        public String getItem(int position) {
            return (String) myList.get(position);
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
                } catch (Exception e) {
                    Log.i("Route ===============>", e.toString());

                }
                convertView.setTag(mHolder);
            } else {
                mHolder = (ViewHolder) convertView.getTag();
            }
            mHolder.schedule_tv.setText(String.valueOf(myList.get(position)));
            return convertView;

        }

    }
    private class ViewHolder {
        TextView schedule_tv,remarks;
        LinearLayout listLL;
        CardView card_view;

    }

}
