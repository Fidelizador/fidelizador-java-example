package fidelizador;

import java.io.*;
import java.lang.*;
import okhttp3.OkHttpClient;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.MultipartBody;

public class ListManager {

	private static final String URL_GET_LIST = "https://api.fidelizador.com/1.0/list.xml";
	
	public String getLists(String slug, String access_token) {
		OkHttpClient client = new OkHttpClient().newBuilder()
          .build();
        Request request = new Request.Builder()
          .url(ListManager.URL_GET_LIST)
          .method("GET", null)
          .addHeader("X-Client-Slug", slug)
          .addHeader("Authorization", "Bearer " + access_token)
          .build();

        String str = "";
        try {
            Response response = client.newCall(request).execute();
            str = response.body().string();
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
        
        
        return str;
	}
}