package com.example.ecommerce.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ecommerce.DataBase.EcommerceDataBase;
import com.example.ecommerce.R;

public class ForgetPasswordActivity extends AppCompatActivity {
    TextView forgetpassword_textview , forgetpassword_retrivedpassword;
    EditText forgettpassword_answereditext;
    Button btn_forgettpassword_searchforpassword;
    EcommerceDataBase DB;
    Cursor c;

    String username , password , question , answer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        forgetpassword_textview = (TextView)(findViewById(R.id.forgetpassword_textview));
        forgetpassword_retrivedpassword = (TextView)(findViewById(R.id.forgetpassword_retrivedpassword));
        forgettpassword_answereditext = findViewById(R.id.forgettpassword_answereditext);
        btn_forgettpassword_searchforpassword = (Button)(findViewById(R.id.btn_forgettpassword_searchforpassword));

        DB = new EcommerceDataBase(getApplicationContext());

        Bundle b = getIntent().getExtras();
        username = b.getString("username");

        c = DB.ForgetPassword(username);
        password = c.getString(1);
        question = c.getString(2);
        answer = c.getString(3);


        forgetpassword_textview.setText(question);

        btn_forgettpassword_searchforpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String outpass = "Your Password is " + password.substring(0,password.length()) ;
                String error_editextisempty = "Please Enter An Answer!!!";
                String error_answer_is_wrong = "Answer Is Wrong Enter It Again";
                if( forgettpassword_answereditext.getText().toString().equals("") ){
                    new CountDownTimer(2000, 1000) {

                        public void onTick(long millisUntilFinished) {
                            forgetpassword_retrivedpassword.setText(error_editextisempty);
                            forgetpassword_retrivedpassword.setTextColor(getResources().getColor(R.color.errorcolor));
                        }

                        public void onFinish() {
                            forgetpassword_retrivedpassword.setText("");
                        }
                    }.start();


                }
                else{
                    if( answer.equals(forgettpassword_answereditext.getText().toString()) ) {
                        forgetpassword_retrivedpassword.setTextColor(getResources().getColor(R.color.white));
                        forgetpassword_retrivedpassword.setText(outpass);
                    }
                    else {
                        new CountDownTimer(2000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                forgetpassword_retrivedpassword.setText(error_answer_is_wrong);
                                forgetpassword_retrivedpassword.setTextColor(getResources().getColor(R.color.errorcolor));
                            }

                            public void onFinish() {
                                forgetpassword_retrivedpassword.setText("");
                            }
                        }.start();
                    }
                }


            }
        });
    }
}