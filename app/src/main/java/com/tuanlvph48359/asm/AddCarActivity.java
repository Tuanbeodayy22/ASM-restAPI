package com.tuanlvph48359.asm;

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

public class AddCarActivity extends AppCompatActivity {

    private EditText etName, etManufacturer, etYear, etPrice, etDescription;
    private Button btnAddCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        // Khởi tạo các view
        etName = findViewById(R.id.etName);
        etManufacturer = findViewById(R.id.etManufacturer);
        etYear = findViewById(R.id.etYear);
        etPrice = findViewById(R.id.etPrice);
        etDescription = findViewById(R.id.etDescription);
        btnAddCar = findViewById(R.id.btnAddCar);

        // Xử lý sự kiện bấm nút thêm xe
        btnAddCar.setOnClickListener(view -> addCar());
    }

    private void addCar() {
        String name = etName.getText().toString();
        String manufacturer = etManufacturer.getText().toString();
        int year = Integer.parseInt(etYear.getText().toString());
        double price = Double.parseDouble(etPrice.getText().toString());
        String description = etDescription.getText().toString();

        // Tạo đối tượng Car mới
        Car newCar = new Car(name, manufacturer, year, price, description);

        // Gửi API thêm xe
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse<Car>> call = apiService.addCar(newCar);

        call.enqueue(new Callback<ApiResponse<Car>>() {
            @Override
            public void onResponse(Call<ApiResponse<Car>> call, Response<ApiResponse<Car>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Toast.makeText(AddCarActivity.this, "Thêm xe thành công!", Toast.LENGTH_SHORT).show();
                    finish(); // Đóng activity sau khi thêm thành công
                } else {
                    Log.e("AddCarActivity", "API Error: " + response.message());
                    Toast.makeText(AddCarActivity.this, "Không thể thêm xe!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Car>> call, Throwable t) {
                Log.e("AddCarActivity", "API Failure: " + t.getMessage(), t);
                Toast.makeText(AddCarActivity.this, "Lỗi kết nối API!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
