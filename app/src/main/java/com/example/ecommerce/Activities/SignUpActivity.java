package com.example.ecommerce.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ecommerce.DataBase.EcommerceDataBase;
import com.example.ecommerce.Models.Customer;
import com.example.ecommerce.R;


public class SignUpActivity extends AppCompatActivity {
    EditText ed_name;
    EditText ed_gender;
    EditText ed_birthdate;
    EditText ed_job;
    EditText ed_username;
    EditText ed_password;
    EditText ed_question;
    EditText ed_answer;

    TextView tv;

    EcommerceDataBase dbobj;

    Customer c;
    Button btn_confirm;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ed_name = findViewById(R.id.signup_name);
        ed_gender = findViewById(R.id.signup_gender);
        ed_birthdate = findViewById(R.id.signup_birthdate);
        ed_job = findViewById(R.id.signup_job);
        ed_username = findViewById(R.id.signup_username);
        ed_password = findViewById(R.id.signup_password);
        ed_question = findViewById(R.id.signup_recoveryquesiton);
        ed_answer = findViewById(R.id.signup_recoveryanswer);

        tv = (TextView)(findViewById(R.id.signup_wait_asceond_and_successfully_));




        dbobj = new EcommerceDataBase(getApplicationContext());

        btn_confirm = (Button)(findViewById(R.id.btn_confirm_data_in_sign_up));

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                c = new Customer(ed_name.getText().toString() , ed_username.getText().toString() , ed_password.getText().toString() , ed_birthdate.getText().toString() , ed_gender.getText().toString() , ed_job.getText().toString() , ed_question.getText().toString() , ed_answer.getText().toString() );

                dbobj.SignUp(c);
                new CountDownTimer(4000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        tv.setText("Wait a Second...............");
                    }

                    public void onFinish() {
                        tv.setText("Successfully Sign Up");
                    }
                }.start();
            }
        });

        ed_birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar mycalender = Calendar.getInstance();
                final int myear = mycalender.get(Calendar.YEAR) , mmonth = mycalender.get(Calendar.MONTH) , mday = mycalender.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int x = month + 1;
                        ed_birthdate.setText(dayOfMonth + "-" + x + "-" + year);
                    }
                },myear,mmonth,mday);
                datePickerDialog.setTitle("Select Data");
                datePickerDialog.show();
            }
        });

    }
}