package com.google.android.glass.awearable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Path;
import android.os.StrictMode;
import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import dto.CTMiniItem;
import dto.CareToolParser;

public class ParseText {

	HashMap<String,String> mapping = new HashMap<String,String>();
	static String baseURL = "http://54.172.222.22:9000";
	
	/*public HashMap<String,String[]> parse(String input){
		if(input.contains("all")){
			return withAll();
		}
		else{
			return null;
		}
	}*/
	
	public static String withAll(String spokenText) throws JsonParseException, JsonMappingException, IOException{
    	
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    	StrictMode.setThreadPolicy(policy);
		Gson gson = new Gson();
		
		RestAdapter restAdapter = new RestAdapter.Builder()
		.setEndpoint("http://54.172.222.22:9000")
		.setConverter(new GsonConverter(gson))
		.build();
		
		GitHubService service = restAdapter.create(GitHubService.class);
		JsonArray jsonArray=null;
		String jsonResponse="";
		String response="";
		String property="";
		String item="";
		try {
			if(spokenText.toLowerCase().contains("all") && spokenText.toLowerCase().contains("items")){
				jsonResponse = service.getAllItems().toString();
				List<CTMiniItem> listOfItems =  CareToolParser.parseItems(jsonResponse);
				Iterator<CTMiniItem> iter = listOfItems.iterator();
				//Eresponse="Name\tSet\tCode\n";
				while(iter.hasNext()){
					CTMiniItem miniItem = iter.next();
					response = response+miniItem.getName()+"\t"+miniItem.getSet()+"\t"+miniItem.getCode()+"\n";	
				}
			}
			else if(spokenText.toLowerCase().contains("all") && (spokenText.toLowerCase().contains("property") || spokenText.toLowerCase().contains("properties")))
				response = service.getAllProperties().toString();
			else if(spokenText.toLowerCase().contains("all"))
				response = "What do you want to be listed?\n Say 'all items' to list all items or say 'all properties' to list all properties"; 
			else {
				if(spokenText.toLowerCase().contains("eating") || spokenText.toLowerCase().contains("eat"))
					item = "Eating";
				else if(spokenText.toLowerCase().contains("toilet hygiene") || spokenText.toLowerCase().contains("toilet"))
					item = "Toilet Hygiene";
				else if(spokenText.toLowerCase().contains("oral hygiene") || spokenText.toLowerCase().contains("oral"))
					item = "Oral Hygiene";
				else if(spokenText.toLowerCase().contains("lower body dressing") || spokenText.toLowerCase().contains("lower body") || spokenText.toLowerCase().contains("lower") || spokenText.toLowerCase().contains("lowerbody"))
					item = "Lower Body Dressing";
				else if(spokenText.toLowerCase().contains("upper body dressing") || spokenText.toLowerCase().contains("upperbody dressing") || spokenText.toLowerCase().contains("upperbodydressing"))
					item = "Upper Body Dressing";
				else if(spokenText.toLowerCase().contains("wash upper body") || spokenText.toLowerCase().contains("wash body") || spokenText.toLowerCase().contains("wash upperbody"))
					item = "Wash Upper Body";

				if(spokenText.toLowerCase().contains("name"))
					property = "name";
				else if(spokenText.toLowerCase().contains("set"))
					property = "set";
				else if(spokenText.toLowerCase().contains("code"))
					property = "code";
				else if(spokenText.toLowerCase().contains("page"))
					property = "page";
				else if(spokenText.toLowerCase().contains("description"))
					property = "description";
				else if(spokenText.toLowerCase().contains("exception") || spokenText.toLowerCase().contains("exceptions"))
					property = "exceptions";
				else if(spokenText.toLowerCase().contains("tip") || spokenText.toLowerCase().contains("tips"))
					property = "tips";
				else if(spokenText.toLowerCase().contains("dependent") || spokenText.toLowerCase().contains("dependents"))
					property = "dependent";
				else if(spokenText.toLowerCase().contains("substantial"))
					property = "substantial";
				else if(spokenText.toLowerCase().contains("partial"))
					property = "partial";
				else if(spokenText.toLowerCase().contains("supervision"))
					property = "supervision";
				else if(spokenText.toLowerCase().contains("setup"))
					property = "setup";
				else if(spokenText.toLowerCase().contains("independent"))
					property = "independent";

				if(item!="" && property!=""){
					jsonArray = service.getProperty(item,property);
					response=parseProperty(jsonArray,property);	
				}
				else if(item!=""){
					jsonArray = service.getProperty(item,"description");
					response=parseProperty(jsonArray,item);
				}
				else if(property!="")
					response= "Which item do you need the "+property+" of?";
			}
		}catch (RetrofitError e) {
			  Log.d("ERROR", "" +e.getCause().toString());
			}
		return response.toString();
	}
	
	public static String parseProperty(JsonArray jarray,String property){
		String response ="";
		if(property=="name"|| property=="set"|| property=="code"|| property=="page" || property=="description"){
			response=jarray.get(0).toString();
		}
		else{
			JsonArray propertyList =(JsonArray) jarray.get(0);
			Iterator<JsonElement> itr = propertyList.iterator();
			while(itr.hasNext()){
				response=response+itr.next().toString();
			}
		}
		return  response;
	}
	
	public interface GitHubService {
		 @GET("/careTool/items/")
		 JsonArray getAllItems();
		 @GET("/careTool/properties/")
		 JsonArray getAllProperties();
		@GET("/careTool/items/name/{name}")
		JsonArray getItem(@Path("name") String name);
		@GET("/careTool/property/{item}/{property}")
		JsonArray getProperty(@Path("item") String item, @Path("property") String property);
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
