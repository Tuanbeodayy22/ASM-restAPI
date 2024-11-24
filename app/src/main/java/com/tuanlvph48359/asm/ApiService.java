package com.tuanlvph48359.asm;

import com.tuanlvph48359.asm.models.ApiResponse;
import com.tuanlvph48359.asm.models.Car;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    // Lấy danh sách xe
    @GET("/")
    Call<ApiResponse<List<Car>>> getCars(); // JSON trả về một danh sách xe
    // Thêm xe mới
    @POST("/add")
    Call<ApiResponse<Car>> addCar(@Body Car car);

    // Xóa xe theo ID
    @DELETE("/delete/{id}")
    Call<ApiResponse<Void>> deleteCar(@Path("id") int id);

    // Cập nhật thông tin xe theo ID
    @PUT("/update/{id}")
    Call<ApiResponse<Car>> updateCar(@Path("id") int id, @Body Car car);
}
