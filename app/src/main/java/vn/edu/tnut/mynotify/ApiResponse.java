package vn.edu.tnut.mynotify;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("ok")
    private int ok;

    @SerializedName("id")
    private int id;

    @SerializedName("msg")
    private String msg;

    public int getOk() {
        return ok;
    }

    public int getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }
}
