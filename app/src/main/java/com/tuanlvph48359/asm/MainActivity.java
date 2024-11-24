package com.tuanlvph48359.asm;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tuanlvph48359.asm.models.ApiResponse;
import com.tuanlvph48359.asm.models.Car;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CarAdapter carAdapter;
    private List<Car> carList = new ArrayList<>(); // Danh sách gốc
    private SearchView searchView;
    private Button btnAddCar;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các view
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        btnAddCar = findViewById(R.id.btnAddCar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Lấy danh sách xe từ API
        fetchCars();

        // Xử lý tìm kiếm
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterCars(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCars(newText);
                return true;
            }
        });

        // Xử lý nút thêm xe
        btnAddCar.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddCarActivity.class);
            startActivity(intent);
        });
    }

    private void fetchCars() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ApiResponse<List<Car>>> call = apiService.getCars();

        call.enqueue(new Callback<ApiResponse<List<Car>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<Car>>> call, Response<ApiResponse<List<Car>>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    carList = response.body().getData(); // Lưu danh sách gốc
                    carAdapter = new CarAdapter(carList, MainActivity.this);
                    recyclerView.setAdapter(carAdapter);
                } else {
                    Toast.makeText(MainActivity.this, "Không thể tải danh sách xe!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<Car>>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Lỗi kết nối API!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterCars(String query) {
        // Lọc danh sách gốc dựa trên từ khóa tìm kiếm
        List<Car> filteredList = new ArrayList<>();
        for (Car car : carList) {
            if (car.getName().toLowerCase().contains(query.toLowerCase()) ||
                    car.getManufacturer().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(car);
            }
        }
        // Cập nhật RecyclerView với danh sách đã lọc
        carAdapter.updateData(filteredList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchCars();
    }
}
