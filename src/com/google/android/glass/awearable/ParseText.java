package com.google.android.glass.awearable;

import java.util.HashMap;

import org.codehaus.jackson.JsonNode;

import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import android.os.StrictMode;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class ParseText {

	HashMap<String,String> mapping = new HashMap<String,String>();
	static String baseURL = "http://209.2.214.255:9000";
	
	/*public HashMap<String,String[]> parse(String input){
		if(input.contains("all")){
			return withAll();
		}
		else{
			return null;
		}
	}*/
	
	public static String withAll(){
    	
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy);
		Gson gson = new Gson();
		
		RestAdapter restAdapter = new RestAdapter.Builder()
		.setEndpoint("http://209.2.214.255:9000")
		.setConverter(new GsonConverter(gson))
		.build();
		
		GitHubService service = restAdapter.create(GitHubService.class);
		String response ="";
		try {
			 response= service.getAll().toString();
			} catch (RetrofitError e) {
			  Log.d("ERROR", "" +e.getCause().toString());
			}
		/*String url = baseURL+"items/";
		URL obj = null;
		HttpURLConnection con=null;
		StringBuffer response = new StringBuffer();
		String inputLine;
		try {
			obj = new URL(url);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			con = (HttpURLConnection) obj.openConnection();
			int responseCode = con.getResponseCode();
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			
			
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    */	
		return response.toString();
	}
	public interface GitHubService {
		 @GET("/careTool/items/")
		 JsonArray getAll();
		 }
}

class MyErrorHandler implements ErrorHandler {
	  @Override 
	  public Throwable handleError(RetrofitError cause) {
	    Response r = cause.getResponse();
	    if (r != null && r.getStatus() == 401) {
	      return new Throwable(cause);
	    }
	    return cause;
	  }


	}
