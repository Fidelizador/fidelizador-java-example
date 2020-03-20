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

    private static final String URL_CREATE_LIST = "https://api.fidelizador.com/1.0/list.xml";
    private static final String URL_GET_LIST = "https://api.fidelizador.com/1.0/list.xml";
    private static final String URL_IMPORT_CONTACT = "https://api.fidelizador.com/1.0/list/{list_id}/import.xml";


    /**
     * Create a List in Fidelizador Platform and returns and XML String with response
     *
     * @param  slug  Instance slug
     * @param  access_token  Access token obtained through Oauth2
     * @param  name the list name
     * @return  xml string response
     */
    public String createList(String slug, String access_token, String name) {
        OkHttpClient client = new OkHttpClient().newBuilder()
          .build();
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
          .addFormDataPart("name", name)
          .addFormDataPart("fields[0]", "EMAIL")
          .addFormDataPart("fields[1]", "FIRSTNAME")
          .build();

        Request request = new Request.Builder()
          .url(ListManager.URL_CREATE_LIST)
          .method("POST", body)
          .addHeader("X-Client-Slug", slug)
          .addHeader("Authorization", "Bearer " + access_token)
          .build();

        String str = null;
        try {
            Response response = client.newCall(request).execute();
            str = response.body().string();
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return str;
    }


    /**
     * Get all list in fidelizador by page. Returns an xml string response
     *
     * @param  slug  Instance slug
     * @param  access_token  Access token obtained through Oauth2
     * @return  xml string response
     */
    public String getLists(String slug, String access_token) {
        OkHttpClient client = new OkHttpClient().newBuilder()
          .build();
        Request request = new Request.Builder()
          .url(ListManager.URL_GET_LIST)
          .method("GET", null)
          .addHeader("X-Client-Slug", slug)
          .addHeader("Authorization", "Bearer " + access_token)
          .build();

        String str = null;
        try {
            Response response = client.newCall(request).execute();
            str = response.body().string();
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
        
        
        return str;
    }


    /**
     * Import contacts to a list using a csv file
     *
     * @param  slug  Instance slug
     * @param  access_token  Access token obtained through Oauth2
     * @param  list_id the id of an existen list in Fidelizador
     * @param  filepath is the path to a csv file. Example: ./data.csv
     * @return  xml string response
     */
    public String createImport(String slug, String access_token, 
                                int list_id, String filepath) {


        String url = this.URL_IMPORT_CONTACT.replace("{list_id}", String.valueOf(list_id));

        OkHttpClient client = new OkHttpClient().newBuilder()
          .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
          .addFormDataPart("file","file",
            RequestBody.create(MediaType.parse("application/octet-stream"),
            new File(filepath)))
          .addFormDataPart("autouniquecode", "1")
          .addFormDataPart("fields[EMAIL]", "1")
          .build();

        Request request = new Request.Builder()
          .url(url)
          .method("POST", body)
          .addHeader("X-Client-Slug", slug)
          .addHeader("Authorization", "Bearer " + access_token)
          .build();

        String str = null;
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