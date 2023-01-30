package com.ruwan.reacongym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.ruwan.reacongym.api.ApiClient;
import com.ruwan.reacongym.api.LocalData;
import com.ruwan.reacongym.api.service.RouterService;
import com.ruwan.reacongym.models.DietModel;
import com.ruwan.reacongym.models.ResponseModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DietPlanActivity extends AppCompatActivity {

    TextView td_b,td_l,td_d,tm_b,tm_l,tm_d,da_b,da_l,da_d;
    SharedPreferences lcData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_plan);

        td_b = findViewById(R.id.td_b);
        td_l = findViewById(R.id.td_l);
        td_d = findViewById(R.id.td_d);
        tm_b = findViewById(R.id.tm_b);
        tm_l = findViewById(R.id.tm_l);
        tm_d = findViewById(R.id.tm_d);
        da_b = findViewById(R.id.da_b);
        da_l = findViewById(R.id.da_l);
        da_d = findViewById(R.id.da_d);

        lcData = getSharedPreferences("lcData", Context.MODE_PRIVATE);

        int t = LocalData.getBodyType(lcData);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd", Locale.getDefault());
        int date = Integer.parseInt(df.format(c));
        int d = date % 15;

        setData(t,d,td_b,td_l,td_d);
        d = (date + 1) % 7;
        setData(t,d,tm_b,tm_l,tm_d);
        d = (date + 2) % 7;
        setData(t,d,da_b,da_l,da_d);
    }

    private void setData(int type, int date,TextView b, TextView l , TextView d){
        DietModel dm = new DietModel(date,type);
        ApiClient.getClient(LocalData.getServerIp(lcData)).create(RouterService.class).GetDietPlan(dm).enqueue(new Callback<DietModel>() {
            @Override
            public void onResponse(@NonNull Call<DietModel> call, @NonNull Response<DietModel> response) {
                DietModel user = response.body();
                assert user != null;
                b.setText(user.getBreakfast());
                l.setText(user.getLunch());
                d.setText(user.getDinner());
            }

            @Override
            public void onFailure(@NonNull Call<DietModel> call, @NonNull Throwable t) {

            }
        });
    }

}