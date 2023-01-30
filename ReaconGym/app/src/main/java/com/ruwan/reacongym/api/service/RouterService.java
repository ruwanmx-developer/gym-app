package com.ruwan.reacongym.api.service;

import com.ruwan.reacongym.models.DietModel;
import com.ruwan.reacongym.models.ResponseModel;
import com.ruwan.reacongym.models.ScheduleModel;
import com.ruwan.reacongym.models.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RouterService {
    @POST("/users/reg-new-usr/")
    Call<ResponseModel> RegisterNewUser(@Body UserModel userModel);

    @POST("/users/log-ext-usr/")
    Call<ResponseModel> LogExistingUser(@Body UserModel userModel);

    @POST("/users/upd-ext-usr/")
    Call<ResponseModel> UpdateExistingUser(@Body UserModel userModel);

    @POST("/users/get-ext-usr/")
    Call<UserModel> GetExistingUser(@Body UserModel userModel);

    @POST("/users/get-diet-plan/")
    Call<DietModel> GetDietPlan(@Body DietModel dietModel);

    @POST("/users/get-she-plan/")
    Call<List<ScheduleModel>> GetSchedulePlan(@Body ScheduleModel scheduleModel);
}
