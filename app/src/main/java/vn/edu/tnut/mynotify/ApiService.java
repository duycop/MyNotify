package vn.edu.tnut.mynotify;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("your_api_endpoint")
    Call<ApiResponse> getNotificationContent();
}