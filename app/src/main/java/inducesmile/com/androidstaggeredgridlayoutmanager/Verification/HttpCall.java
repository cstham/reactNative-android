package inducesmile.com.androidstaggeredgridlayoutmanager.Verification;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpCall {

    //to  post login data as x-www-form-urlencoded
    public static void postLoginData(String username, String password, String reqDateTime, String securityToken){
        OkHttpClient client = new OkHttpClient();
        //MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        //RequestBody body = RequestBody.create(mediaType, "string-that-you-need-to-pass-in-body");

        RequestBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .add("reqDateTime", reqDateTime)
                .add("securityToken", securityToken)
                .build();

        Request request = new Request.Builder()
                .url("http://14.102.149.22:8580/mobile/session/login")
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        //SYNCHRONOUS CALL
        //Response response = client.newCall(request).execute();

        //ASYNC CALL
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    String jsonData = response.body().string();
                    //JSONObject result = new JSONObject(jsonData);
                    System.out.println("response lolxxx:    " + jsonData);
                }
            }
        });

    }




}
