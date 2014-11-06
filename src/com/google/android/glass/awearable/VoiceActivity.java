package com.google.android.glass.awearable;


import java.util.List;

import com.google.android.glass.app.Card;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class VoiceActivity  extends Activity {

	private static final int SPEECH_REQUEST = 0;
	private String footnote = "Product of Awearable";
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String body = "Welcome to Awereable.\nYou have two options:\n1. List all items\n2.Query a specific item.\n";
		body=body+"\nTap to continue.\n";
		setCard(body,footnote);
	}
	
/*	@Override
	protected void onStart(){
		super.onStart();
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		startActivityForResult(intent, SPEECH_REQUEST);
	}*/
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            return false;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
        	Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        	startActivityForResult(intent,SPEECH_REQUEST);
        }
		
		return false;
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	        Intent data) {
	    if (requestCode == SPEECH_REQUEST && resultCode == RESULT_OK) {
	        List<String> results = data.getStringArrayListExtra(
	                RecognizerIntent.EXTRA_RESULTS);
	        String spokenText = results.get(0);
	        String text= parseText(spokenText);
	        
	        setCard(text,footnote);
	        
	        	//setCard("You requested for a specific query.",footnote);
	        
	     //   setCard(spokenText,footnote);
	        
	        // Do something with spokenText.
	    }
	    super.onActivityResult(requestCode, resultCode, data);
	}
	
	private String parseText(String text){
		//if(text.contains("all")){
			return ParseText.withAll();
	//}
		
		//return "";
	}
	
	
    private void setCard (String body, String footnote){
        Card card = new Card(this);
        card.setText(body);
        card.setFootnote(footnote);
        setContentView(card.getView());
        
    }
}
