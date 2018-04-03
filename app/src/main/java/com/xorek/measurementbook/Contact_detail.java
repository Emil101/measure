package com.xorek.measurementbook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Contact_detail extends AppCompatActivity implements AlertDialogRadio.AlertPositiveListener{

    @BindView(R.id.fab)
    FloatingActionButton fab;
//    @BindView(R.id.date)
    EditText dateen;
//    @BindView(R.id.deliveryDate)
    TextView delivery;
//    @BindView(R.id.deliveryTime)
    TextView deliveryT;
//    @BindView(R.id.fnames)
    EditText name;
//    @BindView(R.id.telphs)
    TextView phone;
    @BindView(R.id.im1)
    ImageView con;

    TextView reminder;

    String nam;
    private DatePicker datePicker;
    TimePickerDialog mTimePicker;
    private Calendar calendar;
    private int year, month, day, hour, minute;

    Context context;
    AlarmManager alarmManager;

    private PendingIntent pendingIntent;

    //AlarmReceiver nalrmR = new AlarmReceiver();
    Contact contact;

    int f = 0;
    int position = 0;
    String sex, time;

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Long alarm, remind;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        setTitle(null);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }

        ButterKnife.bind(this);
        Intent incomingIntent = getIntent();
       // registerReceiver(nalrmR, new IntentFilter("MyReceiver"));

        dateen= findViewById(R.id.date);
        delivery = findViewById(R.id.deliveryDate);
        name = findViewById(R.id.fnames);
        deliveryT = findViewById(R.id.deliveryTime);
        phone = findViewById(R.id.telphs);

        context = getApplicationContext();


        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);


        reminder = (TextView)findViewById(R.id.reminder);

        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager manager = getFragmentManager();
                AlertDialogRadio alert = new AlertDialogRadio();

                /** Creating a bundle object to store the selected item's index */
                Bundle b  = new Bundle();

                /** Storing the selected item's index in the bundle object */
                b.putInt("position", position);

                /** Setting the bundle object to the dialog fragment object */
                alert.setArguments(b);

                /** Creating the dialog fragment object, which will in turn open the alert dialog window */
                alert.show(manager, "alert_dialog_radio");

            }
        });



    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onPositiveClick(int position) {
        this.position = position;



        /** Setting the selected android version in the textview */
        reminder.setText( Time.time[this.position]);
    }

    @OnClick(R.id.fab)
    public void startActive(View view){

        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);

        if (delivery.getText().toString().equals("")){

            alarm = calendar.getTimeInMillis() + 604800000;
        }else {
            alarm = calendar.getTimeInMillis();
        }

        if (reminder.getText().toString().isEmpty()){
            remind = alarm - 86400000;
            Log.d("Remind", String.valueOf(remind));
        }

        if(reminder.getText().toString().equals("No reminder")){

            remind = 0L;
            Log.d("Remind", String.valueOf(remind));
        }else if(reminder.getText().toString().equals("30 minutes")){
            remind = alarm - 1800000;
            Log.d("Remind", String.valueOf(remind));
        }else if(reminder.getText().toString().equals("1 hour")){
            remind = alarm - 3600000;
            Log.d("Remind", String.valueOf(remind));
        }else if(reminder.getText().toString().equals("24 hours")){
            remind = alarm - 86400000;
            Log.d("Remind", String.valueOf(remind));
        }else if(reminder.getText().toString().equals("2 days")){
            remind = alarm - 172800000;
            Log.d("Remind", String.valueOf(remind));
        }else if(reminder.getText().toString().equals("1 week")){
            remind = alarm - 604800000;
            Log.d("Remind", String.valueOf(remind));
        }



        nam = name.getText().toString();
        contact = new Contact();
        contact.setNa(nam);

        if (!(nam.isEmpty())) {
            Intent intent = new Intent(getApplicationContext(), Measure.class);
            intent.putExtra("name", name.getText().toString());
            intent.putExtra("phone", phone.getText().toString());
            intent.putExtra("date", dateen.getText().toString());
            intent.putExtra("delivery", delivery.getText().toString());
            intent.putExtra("alarm", alarm );
            intent.putExtra("sex", sex);
            intent.putExtra("remind", remind);
            startActivity(intent);
        }else{
            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Atleast Enter Client's Name...", Snackbar.LENGTH_LONG);
            View sbView = snack.getView();
            sbView.setBackgroundColor(ContextCompat.getColor(Contact_detail.this, R.color.colorRed));
            TextView tv = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(ContextCompat.getColor(Contact_detail.this, R.color.colorash));
            snack.show();
        }

    }
//   @OnClick({R.id.date,R.id.deliveryDate})
//    public void addDate(){
//        startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
//    }

    @OnClick(R.id.im1)
    public void contPick(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, 7);
    }


    public void setReminder(){



    }



    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
//        Toast.makeText(getApplicationContext(), "ca",
//                Toast.LENGTH_SHORT)
//                .show();

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, R.style.MyDialog,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
//                    showDate(arg1, arg2+1, arg3);
                    delivery.setText(new StringBuilder().append(arg3).append("/")
                            .append(arg2+1).append("/").append(arg1));
                    day = arg3;
                    month= arg2;
                    year = arg1;

                }
            };


    private void showDate(int year, int month, int day) {
        dateen.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    public void setTime(View view){
        mTimePicker = new TimePickerDialog(Contact_detail.this, R.style.MyDialog, new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                deliveryT.setText( selectedHour + ":" + String.format("%02d", selectedMinute));
                hour = selectedHour;
                minute = selectedMinute;
            }
        }, hour, minute, true);//Yes 24 hour time

//        mTimePicker.setTitle("SELECT TIME");
        mTimePicker.show();

    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_f:
                if (checked)
                    sex = "Female";
                    break;
            case R.id.radio_m:
                if (checked)
                    sex = "Male";
                    break;
        }
    }



//    public void startingAlarms() {
//
//        ArrayList<PendingIntent> pi = new ArrayList<PendingIntent>();
//
//
////    sendBroadcast(myIntent);
////    Intent intent = new Intent(context, AlarmReceiver.class);
//        //pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MINUTE, minute);
//        calendar.set(Calendar.HOUR_OF_DAY, hour);
//        calendar.set(Calendar.DAY_OF_MONTH, day);
//        calendar.set(Calendar.MONTH, month);
//        calendar.set(Calendar.YEAR, year);
//        for (f = 0; f < 10000; f++) {
//            Intent myIntent = new Intent(context, AlarmReceiver.class);
//            myIntent.putExtra("name", name.getText().toString());
//            pendingIntent = PendingIntent.getBroadcast(context,f, myIntent,0);
//
//
//
//            Long alertTime = new GregorianCalendar().getTimeInMillis() + 5 * 1000;
//
//           alarm[f]  =  (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//            alarm[f].set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//            Log.d("TIMEALARM", String.valueOf((" " + alertTime + " --- " + calendar.getTimeInMillis())) + " " + day + "/" + month + "/" + year + " " + hour + ":" + minute);
//
//            pi.add(pendingIntent);
//
//
//        }
//    }


    @Override
    public void onActivityResult(int RequestCode, int ResultCode, Intent ResultIntent) {

        super.onActivityResult(RequestCode, ResultCode, ResultIntent);

        switch (RequestCode) {

            case (7):
                if (ResultCode == Activity.RESULT_OK) {

                    Uri uri;
                    Cursor cursor1, cursor2;
                    String TempNameHolder, TempNumberHolder, TempContactID, IDresult = "" ;
                    int IDresultHolder ;

                    uri = ResultIntent.getData();

                    cursor1 = getContentResolver().query(uri, null, null, null, null);


                    if (cursor1.moveToFirst()) {

                        TempNameHolder = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                        TempContactID = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID));

                        IDresult = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        IDresultHolder = Integer.valueOf(IDresult) ;

                        if (IDresultHolder == 1) {

                            cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + TempContactID, null, null);

                            while (cursor2.moveToNext()) {

                                TempNumberHolder = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));


                                //dbHandler dbHandler = new dbHandler(this);


                                phone.setText(TempNumberHolder);

                                //dbHandler.addContact(contact);
                                //Toast.makeText(this, "Contact Added", Toast.LENGTH_LONG).show();






                            }
                        }

                    }
                }
                break;
        }
    }

//    @Override
//    protected void onDestroy() {
//        unregisterReceiver(nalrmR);
//        super.onDestroy();
//    }

}


