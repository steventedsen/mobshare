package edu.ucsb.cs.cs184.npoon.mobshare;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

/**
 * Created by Julio on 12/3/2017.
 */


/*

//Old code as an activity, DO NOT DELETE!!!
public class newRideShare extends AppCompatActivity {

    double sourceLatitude = 41.008238;
    double sourceLongitude = 28.978359;
    double destinationLatitude = 37.983810;
    double destinationLongitude = 23.727539;


    EditText editTextDate;
    EditText editTextPrice;
    Spinner spinnerTripType;
    Button buttonSubmit;
    Button buttonLaunchMaps;
    TextView textViewDest;

    DatabaseReference databaserideShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rideshare);

        databaserideShare = FirebaseDatabase.getInstance().getReference("rideShare");

        editTextDate =      findViewById(R.id.editTextDate);
        editTextPrice =     findViewById(R.id.editTextPrice);

        spinnerTripType = findViewById(R.id.tripType);

        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);

        buttonLaunchMaps = findViewById(R.id.buttonLaunchMaps);
        textViewDest = findViewById(R.id.textViewDest);

        buttonLaunchMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchMaps();

                Log.d("ADebugTag", "Value: " + Double.toString(destinationLatitude));

            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                addRideShare();
            }

        });


    }
*/

public class newRideShare extends Fragment {

/*
    double sourceLatitude = 41.008238;
    double sourceLongitude = 28.978359;
    double destinationLatitude = 37.983810;
    double destinationLongitude = 23.727539;
*/

    private TextView mDisplayDepartD;
    private TextView mDisplayReturnD;

    private DatePickerDialog.OnDateSetListener mDateSetListener1;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;

    private TextView mDisplayDepartT;
    private TextView mDisplayReturnT;

    private TimePickerDialog.OnTimeSetListener mTimeSetListener1;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener2;

//    EditText editTextDate;
    EditText editTextPrice;
    Spinner spinnerDestination;
    Spinner spinnerTripType;
    Button buttonSubmit;
//  Button buttonLaunchMaps;
//    TextView textViewDest;


    private FirebaseAuth mAuth;

    DatabaseReference databaserideShare;
    FirebaseDatabase User;
    DatabaseReference UserRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add_rideshare, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        databaserideShare = FirebaseDatabase.getInstance().getReference("rideShare");
        User = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        View v = getView();
//        editTextDate =      v.findViewById(R.id.editTextDate);
        editTextPrice = v.findViewById(R.id.editTextPrice);

        spinnerDestination = v.findViewById(R.id.tripDestination);

        spinnerTripType = v.findViewById(R.id.tripType);

        buttonSubmit = v.findViewById(R.id.buttonSubmit);

        mDisplayDepartD = (TextView) v.findViewById(R.id.textViewDepartD);
        mDisplayReturnD = (TextView) v.findViewById(R.id.textViewReturnD);

        mDisplayDepartT = (TextView) v.findViewById(R.id.textViewDepartT);
        mDisplayReturnT = (TextView) v.findViewById(R.id.textViewReturnT);


        mDisplayDepartD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener1,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayDepartD.setText(date);
            }
        };

        mDisplayReturnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener2,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                mDisplayReturnD.setText(date);
            }
        };


        mDisplayDepartT.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(
                        getActivity(), mTimeSetListener1, hour, minute,
                        DateFormat.is24HourFormat(getActivity()));

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mTimeSetListener1 =new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet (TimePicker timePicker,int hour, int minute){

                String time = getTime(hour, minute);

                mDisplayDepartT.setText(time);
            }
        };


        mDisplayReturnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(
                        getActivity(), mTimeSetListener2, hour, minute,
                        DateFormat.is24HourFormat(getActivity()));

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mTimeSetListener2 = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {

                String time = getTime(hour, minute);

                mDisplayReturnT.setText(time);

            }
        };

        final String[] spinnerCheck_array = getResources().getStringArray(R.array.items);

//        final int currentSpinVal = spinnerTripType.getSelectedItemPosition();
//        spinnerTripType.setOnItemSelectedListener(this);


        spinnerTripType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

//                position = 1;
                if(spinnerCheck_array[position].equals("One Way")){

                    mDisplayReturnT.setVisibility(View.GONE);
                    mDisplayReturnD.setVisibility(View.GONE);
                }
                else{

                    mDisplayReturnT.setVisibility(View.VISIBLE);
                    mDisplayReturnD.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRideShare();
            }

        });



//        buttonLaunchMaps =  v.findViewById(R.id.buttonLaunchMaps);
//        textViewDest =      v.findViewById(R.id.textViewDest);

/*
        buttonLaunchMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                launchMaps();

                Log.d("ADebugTag", "Value: " + Double.toString(destinationLatitude));

            }
        });
*/



    }

    private String getTime(int hr, int min) {
        Time tme = new Time(hr, min, 0);
        Format formatter;
        formatter = new SimpleDateFormat("h:mm a");
        return formatter.format(tme);
    };



    private void addRideShare() {
        String[] spinnerCheck_array = getResources().getStringArray(R.array.items);
        String[] spinnerCheck_array2 = getResources().getStringArray(R.array.items2);
        final String destination = spinnerDestination.getSelectedItem().toString();
        final String price = editTextPrice.getText().toString().trim();
        final String tripType = spinnerTripType.getSelectedItem().toString();

        final String dateD = mDisplayDepartD.getText().toString().trim();
        final String dateR = mDisplayReturnD.getText().toString().trim();

        final String timeD = mDisplayDepartT.getText().toString().trim();
        final String timeR = mDisplayReturnT.getText().toString().trim();

        UserRef = User.getReference("Users").child(mAuth.getUid().toString());
        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String NameValue = dataSnapshot.child("Name").getValue(String.class);
                String UNameValue = dataSnapshot.child("UserName").getValue(String.class);
                String PhoneValue = dataSnapshot.child("Phone").getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        if ((!TextUtils.isEmpty(price)))
        {

            if (destination.equals(spinnerCheck_array2[0]))
            {
                Toast.makeText(newRideShare.this.getActivity().getApplicationContext(), "Please Select a Destination!", Toast.LENGTH_LONG).show();
            }else if (tripType.equals(spinnerCheck_array[0]))
            {
                Toast.makeText(newRideShare.this.getActivity().getApplicationContext(), "Please Select a type of Trip!", Toast.LENGTH_LONG).show();
            }else if (dateD.equals(""))
            {
                Toast.makeText(newRideShare.this.getActivity().getApplicationContext(), "Please Select a Departure Date!", Toast.LENGTH_LONG).show();
            }else if (timeD.equals(""))
            {
                Toast.makeText(newRideShare.this.getActivity().getApplicationContext(), "Please Select a Departure Time!", Toast.LENGTH_LONG).show();
            }else if ((dateR.equals("")) && (!tripType.equals(spinnerCheck_array[1])))
            {
                Toast.makeText(newRideShare.this.getActivity().getApplicationContext(), "Please Select a Return Date!", Toast.LENGTH_LONG).show();
            }else if ((timeR.equals("")) && (!tripType.equals(spinnerCheck_array[1])))
            {
                Toast.makeText(newRideShare.this.getActivity().getApplicationContext(), "Please Select a Return Time!", Toast.LENGTH_LONG).show();
            }
            else {

                if (tripType.equals(spinnerCheck_array[1])) {

                    final String id = databaserideShare.push().getKey();

                    UserRef = User.getReference("Users").child(mAuth.getUid().toString());
                    UserRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            String NameValue = dataSnapshot.child("Name").getValue(String.class);
                            String UNameValue = dataSnapshot.child("UserName").getValue(String.class);
                            String PhoneValue = dataSnapshot.child("Phone").getValue(String.class);
//                        rideShare rS = new rideShare(UNameValue, destination, tripType, price, date, PhoneValue, NameValue);

                            databaserideShare.child(id).child("Username").setValue(UNameValue);
                            databaserideShare.child(id).child("Name").setValue(NameValue);
                            databaserideShare.child(id).child("Destination").setValue(destination);
                            databaserideShare.child(id).child("Trip Type").setValue(tripType);
                            databaserideShare.child(id).child("Price").setValue(price);
                            databaserideShare.child(id).child("Depart Date").setValue(dateD);
                            databaserideShare.child(id).child("Return Date").setValue("N/A");
                            databaserideShare.child(id).child("Depart Time").setValue(timeD);
                            databaserideShare.child(id).child("Return Time").setValue("N/A");
                            databaserideShare.child(id).child("Phone Number").setValue(PhoneValue);

                            Toast.makeText(newRideShare.this.getActivity().getApplicationContext(),
                                    "Ride Share Posted!",
                                    Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });
                    // Failed to read value
                    //Log.w(TAG, "Failed to read value.", error.toException());}

                } else {
                    final String id = databaserideShare.push().getKey();

                    UserRef = User.getReference("Users").child(mAuth.getUid().toString());
                    UserRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            String NameValue = dataSnapshot.child("Name").getValue(String.class);
                            String UNameValue = dataSnapshot.child("UserName").getValue(String.class);
                            String PhoneValue = dataSnapshot.child("Phone").getValue(String.class);

                            databaserideShare.child(id).child("Username").setValue(UNameValue);
                            databaserideShare.child(id).child("Name").setValue(NameValue);
                            databaserideShare.child(id).child("Destination").setValue(destination);
                            databaserideShare.child(id).child("Trip Type").setValue(tripType);
                            databaserideShare.child(id).child("Price").setValue(price);
                            databaserideShare.child(id).child("Depart Date").setValue(dateD);
                            databaserideShare.child(id).child("Return Date").setValue(dateR);
                            databaserideShare.child(id).child("Depart Time").setValue(timeD);
                            databaserideShare.child(id).child("Return Time").setValue(timeR);
                            databaserideShare.child(id).child("Phone Number").setValue(PhoneValue);

                            Toast.makeText(newRideShare.this.getActivity().getApplicationContext(),
                                    "Ride Share Posted!",
                                    Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onCancelled(DatabaseError error) {
                        }
                    });
                    // Failed to read value
                    //Log.w(TAG, "Failed to read value.", error.toException());
                }
            }
        }
        else{
                Toast.makeText(newRideShare.this.getActivity().getApplicationContext(),
                        "Please Enter a Price!",
                        Toast.LENGTH_LONG).show();
            }

    }
/*    private void launchMaps(){

        String uri = "http://maps.google.com/maps?saddr=" + sourceLatitude + "," + sourceLongitude + "&daddr=" + destinationLatitude + "," + destinationLongitude;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }*/



    public class rideShare {

        private String rSUsername;
        private String rSDestination;
        private String rStripType;
        private String rSprice;
        private String rSdate;
        private String rSRealName;
        private String rSPhone;

        public rideShare() {

        }

        public rideShare(String rSDestination, String rSUsername, String rStripType, String rSprice, String rSdate, String rSPhone, String rSRealName) {

            this.rSDestination = rSDestination;
            this.rSUsername = rSUsername;
            this.rStripType = rStripType;
            this.rSprice = rSprice;
            this.rSdate = rSdate;
            this.rSPhone = rSPhone;
            this.rSRealName = rSRealName;
        }

        public String getrSUsername() { return rSUsername; }

        public String getrSDestination() { return rSDestination; }

        public String getrStripType() {
            return rStripType;
        }

        public String getrSprice() {
            return rSprice;
        }

        public String getrSdate() {
            return rSdate;
        }

        public String getrSRealName() {
            return rSRealName;
        }

        public String getrSPhone() {
            return rSPhone;
        }
    }
}
