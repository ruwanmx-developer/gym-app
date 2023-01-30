package com.ruwan.reacongym;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.ruwan.reacongym.api.ApiClient;
import com.ruwan.reacongym.api.LocalData;
import com.ruwan.reacongym.api.service.RouterService;
import com.ruwan.reacongym.models.ResponseModel;
import com.ruwan.reacongym.models.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText reg_email, reg_password, reg_first_name, reg_last_name;
    private RadioButton reg_male;
    SharedPreferences lcData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        reg_email = findViewById(R.id.reg_email);
        reg_password = findViewById(R.id.reg_password);
        reg_first_name = findViewById(R.id.reg_first_name);
        reg_last_name = findViewById(R.id.reg_last_name);
        reg_male = findViewById(R.id.reg_male);

        lcData = getSharedPreferences("lcData", Context.MODE_PRIVATE);
        Button reg_reg_btn = findViewById(R.id.reg_reg_btn);
        Button reg_log_btn = findViewById(R.id.reg_log_btn);

        reg_log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            }
        });

        reg_reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reg_validation()) {
                    String email = reg_email.getText().toString();
                    String password = reg_password.getText().toString();
                    String first_name = reg_first_name.getText().toString();
                    String last_name = reg_last_name.getText().toString();
                    String gender = reg_male.isChecked() ? "Male" : "Female";
                    UserModel new_user = new UserModel(email, password, first_name, last_name, gender);

                    ApiClient.getClient(LocalData.getServerIp(lcData)).create(RouterService.class).RegisterNewUser(new_user).enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            ResponseModel user = response.body();
                            Toast.makeText(RegisterActivity.this, user.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    private boolean reg_validation() {
        if (reg_email.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Email Address to continue", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (reg_password.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Password to continue", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (reg_first_name.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter First Name to continue", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (reg_last_name.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Last Name to continue", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}