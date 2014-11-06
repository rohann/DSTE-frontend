package com.google.android.glass.awearable;

import java.util.HashMap;

import org.codehaus.jackson.JsonNode;

import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Path;
import android.os.StrictMode;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class ParseText {

	HashMap<String,String> mapping = new HashMap<String,String>();
	static String baseURL = "http://160.39.171.118:9000";
	
	/*public HashMap<String,String[]> parse(String input){
		if(input.contains("all")){
			return withAll();
		}
		else{
			return null;
		}
	}*/
	
	public static String withAll(String spokenText){
    	
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy);
		Gson gson = new Gson();
		
		RestAdapter restAdapter = new RestAdapter.Builder()
		.setEndpoint("http://160.39.171.118:9000")
		.setConverter(new GsonConverter(gson))
		.build();
		
		GitHubService service = restAdapter.create(GitHubService.class);
		String response ="";
		try {
			 if(spokenText.trim().contains("all"))
				 response= service.getAll().toString();
			 else if(spokenText.toLowerCase().contains("eat") || spokenText.toLowerCase().contains("eating"))
				 response= service.getItem("Eating").toString();
			 else if(spokenText.toLowerCase().contains("toilet hygiene") || spokenText.toLowerCase().contains("toilet"))
				 response= service.getItem("Toilet Hygiene").toString();
			 else if(spokenText.toLowerCase().contains("oral hygiene") || spokenText.toLowerCase().contains("oral"))
				 response= service.getItem("Oral Hygiene").toString();
			 else if(spokenText.toLowerCase().contains("lower body dressing") || spokenText.toLowerCase().contains("lower body") || spokenText.toLowerCase().contains("lower") || spokenText.toLowerCase().contains("lowerbody"))
				 response= service.getItem("Lower Body Dressing").toString();
			 else if(spokenText.toLowerCase().contains("upper body dressing") || spokenText.toLowerCase().contains("upperbodydressing") || spokenText.toLowerCase().contains("upperbody dressing"))
				 response= service.getItem("Upper Body Dressing").toString();
			 else if(spokenText.toLowerCase().contains("wash upper body") || spokenText.toLowerCase().contains("washupperbody") || spokenText.toLowerCase().contains("wash upperbody"))
				 response= service.getItem("Upper Body Dressing").toString();
			 else
				 response= "Sorry, we don't have that in our list.";
			} catch (RetrofitError e) {
			  Log.d("ERROR", "" +e.getCause().toString());
			}
		return response.toString();
	}
	public interface GitHubService {
		 @GET("/careTool/items/")
		 JsonArray getAll();
		@GET("/careTool/items/name/{name}")
		JsonArray getItem(@Path("name") String name);
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
