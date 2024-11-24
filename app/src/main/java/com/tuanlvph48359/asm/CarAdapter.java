package com.tuanlvph48359.asm;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tuanlvph48359.asm.models.ApiResponse;
import com.tuanlvph48359.asm.models.Car;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {

    private List<Car> carList;
    private Context context;

    public CarAdapter(List<Car> carList, Context context) {
        this.carList = carList;
        this.context = context;
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);
        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carList.get(position);

        holder.tvCarName.setText(car.getName());
        holder.tvManufacturer.setText(car.getManufacturer());
        holder.tvYear.setText(String.valueOf(car.getYear()));
        holder.tvPrice.setText(String.valueOf(car.getPrice()));
        holder.tvDescription.setText(car.getDescription());

        // Xử lý nút sửa
        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateCarActivity.class);

            // Truyền dữ liệu xe qua Intent
            intent.putExtra("carId", car.getId());
            intent.putExtra("name", car.getName());
            intent.putExtra("manufacturer", car.getManufacturer());
            intent.putExtra("year", car.getYear());
            intent.putExtra("price", car.getPrice());
            intent.putExtra("description", car.getDescription());
            Log.d("CarAdapter", "Car to edit: " + car.getId() + ", " + car.getName());

            context.startActivity(intent);
        });

        // Xử lý nút xóa
        holder.btnDelete.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition(); // Lấy vị trí hiện tại
            if (currentPosition == RecyclerView.NO_POSITION) {
                return; // Vị trí không hợp lệ, thoát
            }

            ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
            Call<ApiResponse<Void>> call = apiService.deleteCar(car.getId());

            call.enqueue(new Callback<ApiResponse<Void>>() {
                @Override
                public void onResponse(Call<ApiResponse<Void>> call, Response<ApiResponse<Void>> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                        Toast.makeText(context, "Xóa xe thành công!", Toast.LENGTH_SHORT).show();
                        carList.remove(currentPosition); // Xóa mục khỏi danh sách
                        notifyItemRemoved(currentPosition); // Cập nhật RecyclerView
                        notifyItemRangeChanged(currentPosition, carList.size());
                    } else {
                        Toast.makeText(context, "Không thể xóa xe!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<Void>> call, Throwable t) {
                    Toast.makeText(context, "Lỗi kết nối API!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    // Phương thức mới để cập nhật danh sách xe
    public void updateData(List<Car> newCarList) {
        carList.clear();
        carList.addAll(newCarList);
        notifyDataSetChanged();
    }

    public static class CarViewHolder extends RecyclerView.ViewHolder {

        TextView tvCarName, tvManufacturer, tvYear, tvPrice, tvDescription;
        Button btnEdit, btnDelete;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCarName = itemView.findViewById(R.id.tvCarName);
            tvManufacturer = itemView.findViewById(R.id.tvManufacturer);
            tvYear = itemView.findViewById(R.id.tvYear);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            btnEdit = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
