package com.ruwan.reacongym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.ruwan.reacongym.api.ApiClient;
import com.ruwan.reacongym.api.LocalData;
import com.ruwan.reacongym.api.service.RouterService;
import com.ruwan.reacongym.models.DietModel;
import com.ruwan.reacongym.models.ScheduleModel;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleActivity extends AppCompatActivity {

    TextView w1, w2, w3, w4, w5, w6, w7, r1, r2, r3, r4, r5, r6, r7;
    SharedPreferences lcData;

    List<TextView> tvs1 ;
    List<TextView> tvs2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        w1 = findViewById(R.id.wok1);
        w2 = findViewById(R.id.wok2);
        w3 = findViewById(R.id.wok3);
        w4 = findViewById(R.id.wok4);
        w5 = findViewById(R.id.wok5);
        w6 = findViewById(R.id.wok6);
        w7 = findViewById(R.id.wok7);
        r1 = findViewById(R.id.rep1);
        r2 = findViewById(R.id.rep2);
        r3 = findViewById(R.id.rep3);
        r4 = findViewById(R.id.rep4);
        r5 = findViewById(R.id.rep5);
        r6 = findViewById(R.id.rep6);
        r7 = findViewById(R.id.rep7);

        lcData = getSharedPreferences("lcData", Context.MODE_PRIVATE);
        int t = LocalData.getBodyType(lcData);
        tvs1 = Arrays.asList(w1, w2,w3,w4,w5,w6,w7);
        tvs2 = Arrays.asList(r1, r2,r3,r4,r5,r6,r7);

        setData(t);
    }

    private void setData(int type) {
        ScheduleModel sm = new ScheduleModel(type);
        ApiClient.getClient(LocalData.getServerIp(lcData)).create(RouterService.class).GetSchedulePlan(sm).enqueue(new Callback<List<ScheduleModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<ScheduleModel>> call, @NonNull Response<List<ScheduleModel>> response) {
                List<ScheduleModel> user = response.body();
                assert user != null;
                String temp;
                for(int i = 0; i<7;i++){
                   tvs1.get(i).setText(String.valueOf(user.get(i).getName()));
                   temp = user.get(i).getReps() + " x Reps";
                   tvs2.get(i).setText(temp);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ScheduleModel>> call, @NonNull Throwable t) {

            }
        });
    }
}