package com.tuanlvph48359.asm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tuanlvph48359.asm.models.ApiResponse;
import com.tuanlvph48359.asm.models.Car;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateCarActivity extends AppCompatActivity {

    private EditText etName, etManufacturer, etYear, etPrice, etDescription;
    private Button btnUpdateCar;
    private int carId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_car);

        // Ánh xạ các view
        etName = findViewById(R.id.etName);
        etManufacturer = findViewById(R.id.etManufacturer);
        etYear = findViewById(R.id.etYear);
        etPrice = findViewById(R.id.etPrice);
        etDescription = findViewById(R.id.etDescription);
        btnUpdateCar = findViewById(R.id.btnUpdateCar);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        carId = intent.getIntExtra("carId", -1);
        String name = intent.getStringExtra("name");
        String manufacturer = intent.getStringExtra("manufacturer");
        int year = intent.getIntExtra("year", 0);
        double price = intent.getDoubleExtra("price", 0.0);
        String description = intent.getStringExtra("description");

        // Gán dữ liệu cho các trường nhập
        etName.setText(name);
        etManufacturer.setText(manufacturer);
        etYear.setText(String.valueOf(year));
        etPrice.setText(String.valueOf(price));
        etDescription.setText(description);

        Log.d("UpdateCarActivity", "Car ID: " + carId);
        Log.d("UpdateCarActivity", "Name: " + name);
        Log.d("UpdateCarActivity", "Manufacturer: " + manufacturer);
        Log.d("UpdateCarActivity", "Year: " + year);
        Log.d("UpdateCarActivity", "Price: " + price);
        Log.d("UpdateCarActivity", "Description: " + description);

        // Xử lý sự kiện bấm nút cập nhật
        btnUpdateCar.setOnClickListener(view -> updateCar());
    }


    private void updateCar() {
        // Lấy dữ liệu từ các trường nhập
        String name = etName.getText().toString().trim();
        String manufacturer = etManufacturer.getText().toString().trim();
        int year = Integer.parseInt(etYear.getText().toString().trim());
        double price = Double.parseDouble(etPrice.getText().toString().trim());
        String description = etDescription.getText().toString().trim();

        // Kiểm tra dữ liệu trước khi gửi
        Log.d("UpdateCarActivity", "Updating car with: " + name + ", " + manufacturer);

        // Tạo đối tượng Car mới
        Car updatedCar = new Car();
        updatedCar.setName(name);
        updatedCar.setManufacturer(manufacturer);
        updatedCar.setYear(year);
        updatedCar.setPrice(price);
        updatedCar.setDescription(description);

        // Gọi API để cập nhật xe
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse<Car>> call = apiService.updateCar(carId, updatedCar);

        call.enqueue(new Callback<ApiResponse<Car>>() {
            @Override
            public void onResponse(Call<ApiResponse<Car>> call, Response<ApiResponse<Car>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Car updatedCar = response.body().getData(); // Lấy đối tượng xe đã cập nhật

                    // Log chi tiết đối tượng Car
                    Log.d("UpdateCarActivity", "Updated car: " + updatedCar.toString());

                    Toast.makeText(UpdateCarActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    finish(); // Quay lại màn hình chính sau khi cập nhật
                } else {
                    Toast.makeText(UpdateCarActivity.this, "Không thể cập nhật xe!", Toast.LENGTH_SHORT).show();
                    Log.e("UpdateCarActivity", "API Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Car>> call, Throwable t) {
                Toast.makeText(UpdateCarActivity.this, "Lỗi kết nối API!", Toast.LENGTH_SHORT).show();
                Log.e("UpdateCarActivity", "API Failure: " + t.getMessage(), t);
            }
        });
    }
}
