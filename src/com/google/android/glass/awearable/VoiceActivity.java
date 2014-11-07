package com.google.android.glass.awearable;


import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
	private String footnote = "Product of Lexicon";
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String body = "Welcome to Lexcion.\nYou have three options:\n1. List all items\n2. List all properties\n3.Query a specific item.\n";
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
	        String text="";
			try {
				text = parseText(spokenText);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        setCard(text,footnote);
	    }
	    super.onActivityResult(requestCode, resultCode, data);
	}
	
	private String parseText(String text) throws JsonParseException, JsonMappingException, IOException{
			return ParseText.withAll(text); // returning json response for now
	}
	
	
    private void setCard (String body, String footnote){
        Card card = new Card(this);
        card.setText(body);
        card.setFootnote(footnote);
        setContentView(card.getView());
        
    }
}
