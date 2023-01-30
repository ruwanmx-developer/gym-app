package com.ruwan.reacongym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ruwan.reacongym.api.ApiClient;
import com.ruwan.reacongym.api.LocalData;
import com.ruwan.reacongym.api.service.RouterService;
import com.ruwan.reacongym.models.ResponseModel;
import com.ruwan.reacongym.models.UserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText log_email, log_password,ip;
    Button log_reg_btn, log_log_btn,save_ip;
    SharedPreferences lcData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        log_email = findViewById(R.id.log_email);
        log_password = findViewById(R.id.log_password);
        ip = findViewById(R.id.ip1);
        save_ip = findViewById(R.id.save_ip);

        log_reg_btn = findViewById(R.id.log_reg_btn);
        log_log_btn = findViewById(R.id.log_log_btn);

        lcData = getSharedPreferences("lcData", Context.MODE_PRIVATE);

        log_reg_btn.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            finish();
        });


        save_ip.setOnClickListener(view -> LocalData.saveServerIp(lcData, ip.getText().toString()));

        log_log_btn.setOnClickListener(view -> {
            if (log_validation()) {
                String email = log_email.getText().toString();
                String password = log_password.getText().toString();

                UserModel user = new UserModel(email, password);

                ApiClient.getClient(LocalData.getServerIp(lcData)).create(RouterService.class).LogExistingUser(user).enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                        ResponseModel user = response.body();
                        assert user != null;
                        if (user.isValid()) {
                            LocalData.saveUserId(lcData, user.getStatus(), user.getMessage());
                            startActivity(new Intent(MainActivity.this, UpdateActivity.class));
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, user.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {

                    }
                });
            }
        });
    }

    private boolean log_validation() {
        if (log_email.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Email Address to continue", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (log_password.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Password to continue", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}