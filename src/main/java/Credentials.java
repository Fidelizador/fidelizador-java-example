package fidelizador;

import java.io.*;
import java.lang.*;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.OkHttpClient;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.MultipartBody;


public class Credentials {

    private String clientId = "";//clientId
    private String clientSecret = "";//client secret
    private static final String tokenUrl = "https://api.fidelizador.com/oauth/v2/token";
    private static final Pattern pat = Pattern.compile(".*\"access_token\"\\s*:\\s*\"([^\"]+)\".*");

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getCredentials() {
        OkHttpClient client = new OkHttpClient().newBuilder()
          .build();
        Request request = new Request.Builder()
          .url(Credentials.tokenUrl + "?client_id="+this.clientId+"&client_secret="+this.clientSecret+"&grant_type=client_credentials&scope=user")
          .method("GET", null)
          .build();

        String str = "";
        try {
            Response response = client.newCall(request).execute();
            str = response.body().string();
        }
        catch(IOException e) {
            System.out.println(e.getMessage());
        }
        String returnValue = null;
        
        Matcher matcher = pat.matcher(str);
        if (matcher.matches() && matcher.groupCount() > 0) {
            returnValue = matcher.group(1);
        }
        
        return returnValue;
    }

}