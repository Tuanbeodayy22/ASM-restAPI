package com.tuanlvph48359.asm.models;

public class ApiResponse<T> {
    private boolean success;
    private T data; // T có thể là một danh sách (List<Car>) hoặc một đối tượng đơn lẻ (Car)

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
