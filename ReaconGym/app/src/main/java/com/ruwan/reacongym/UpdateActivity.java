package com.ruwan.reacongym;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class UpdateActivity extends AppCompatActivity {

    EditText bdy_height, bdy_weight, bdy_age;
    TextView out_height, out_weight, out_age, out_status, out_bmi;
    ImageView image;
    Button bdy_cal_bmi, bdy_save,btn_diet,btn_schedule;
    SharedPreferences lcData;
    boolean x = false;
    float height;
    float weight;
    int age, user_id, body_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        bdy_height = findViewById(R.id.bdy_height);
        bdy_weight = findViewById(R.id.bdy_weight);
        bdy_age = findViewById(R.id.bdy_age);
        out_height = findViewById(R.id.out_height);
        out_weight = findViewById(R.id.out_weight);
        out_age = findViewById(R.id.out_age);
        out_status = findViewById(R.id.out_status);
        out_bmi = findViewById(R.id.out_bmi);
        image = findViewById(R.id.image);
        bdy_cal_bmi = findViewById(R.id.bdy_cal_bmi);
        bdy_save = findViewById(R.id.bdy_save);
        btn_diet = findViewById(R.id.bdy_diet);
        btn_schedule = findViewById(R.id.bdy_schedule);

        lcData = getSharedPreferences("lcData", Context.MODE_PRIVATE);

        user_id = LocalData.getUserId(lcData);

        initializeData();

        btn_diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalData.saveBodyType(lcData, body_type);
                startActivity(new Intent(UpdateActivity.this, DietPlanActivity.class));
            }
        });

        btn_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalData.saveBodyType(lcData, body_type);
                startActivity(new Intent(UpdateActivity.this, ScheduleActivity.class));
            }
        });

        bdy_cal_bmi.setOnClickListener(view -> {
            if (bmi_validate()) {
                height = Float.parseFloat(bdy_height.getText().toString());
                weight = Float.parseFloat(bdy_weight.getText().toString());
                age = Integer.parseInt(bdy_age.getText().toString());
                setViewData(weight, height, age);
                x = true;
            }
        });

        bdy_save.setOnClickListener(view -> {
            if (!out_height.getText().equals("Height :") && age != 0) {
                UserModel user = new UserModel(user_id, age, weight, height);
                ApiClient.getClient(LocalData.getServerIp(lcData)).create(RouterService.class).UpdateExistingUser(user).enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseModel> call, @NonNull Response<ResponseModel> response) {
                        ResponseModel user = response.body();
                        assert user != null;
                        Toast.makeText(UpdateActivity.this, user.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseModel> call, @NonNull Throwable t) {

                    }
                });
            }
        });
    }

    private void initializeData() {
        UserModel data = new UserModel(user_id);
        ApiClient.getClient(LocalData.getServerIp(lcData)).create(RouterService.class).GetExistingUser(data).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                UserModel user = response.body();
                assert user != null;
                if (!String.valueOf(user.getAge()).equals("")) {
                    setViewData(user.getWeight(), user.getHeight(), user.getAge());
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {

            }
        });
    }

    private void setViewData(float weight, float height, int age) {
        if (height == 0 && weight == 0 && age == 0) {
            out_height.setText(getString(R.string.height));
            out_weight.setText(getString(R.string.weight1));
            out_age.setText(getString(R.string.age1));
            out_status.setText(getString(R.string.status1));
            out_bmi.setText(getString(R.string.bmi_value1));
            image.setImageDrawable(AppCompatResources.getDrawable(UpdateActivity.this, R.drawable.empty));
            return;
        }
        float bmi = weight / ((height / 100) * (height / 100));
        String status, temp;
        if (LocalData.getUserGender(lcData).equals("Male")) {
            if (bmi < 18.5) {
                body_type = 1;
                image.setImageDrawable(AppCompatResources.getDrawable(UpdateActivity.this, R.drawable.male_tp1));
                status = "Under Weight";
            } else if (bmi >= 18.5 && bmi <= 25) {
                body_type = 2;
                image.setImageDrawable(AppCompatResources.getDrawable(UpdateActivity.this, R.drawable.male_tp2));
                status = "Normal";
            } else {
                body_type = 3;
                image.setImageDrawable(AppCompatResources.getDrawable(UpdateActivity.this, R.drawable.male_tp3));
                status = "Over Weight";
            }
        } else {
            if (bmi < 18.5) {
                body_type = 1;
                image.setImageDrawable(AppCompatResources.getDrawable(UpdateActivity.this, R.drawable.female_tp1));
                status = "Under Weight";
            } else if (bmi >= 18.5 && bmi <= 25) {
                body_type = 2;
                image.setImageDrawable(AppCompatResources.getDrawable(UpdateActivity.this, R.drawable.female_tp2));
                status = "Normal";
            } else {
                body_type = 3;
                image.setImageDrawable(AppCompatResources.getDrawable(UpdateActivity.this, R.drawable.female_tp3));
                status = "Over Weight";
            }
        }

        temp = "Height : " + height + " cm";
        out_height.setText(temp);
        temp = "Weight : " + weight + " kg";
        out_weight.setText(temp);
        temp = "Age : " + age + " years";
        out_age.setText(temp);
        temp = "Status : " + status;
        out_status.setText(temp);
        temp = "BMI : " + bmi;
        out_bmi.setText(temp);
        x = true;
    }

    private boolean bmi_validate() {
        if (bdy_height.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Body Height to continue", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (bdy_weight.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Body Weight to continue", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (bdy_age.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter Age to continue", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}