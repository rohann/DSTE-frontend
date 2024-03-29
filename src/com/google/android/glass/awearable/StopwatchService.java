/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.glass.awearable;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.android.glass.timeline.LiveCard;

/**
 * Service owning the LiveCard living in the timeline.
 */
public class StopwatchService extends Service {

    private static final String LIVE_CARD_TAG = "stopwatch";
    private static final String TAG = "SliderService";
    private ChronometerDrawer mCallback;

    private LiveCard mLiveCard;

    private final IBinder mBinder = new LocalBinder();
    
    public class LocalBinder extends Binder {
        StopwatchService getService() {
            return StopwatchService.this;
        }
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

   /* @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mLiveCard == null) {
            mLiveCard = new LiveCard(this, LIVE_CARD_TAG);

            // Keep track of the callback to remove it before unpublishing.
            mCallback = new ChronometerDrawer(this);
            mLiveCard.setDirectRenderingEnabled(true).getSurfaceHolder().addCallback(mCallback);

            Intent menuIntent = new Intent(this, MenuActivity.class);
            menuIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mLiveCard.setAction(PendingIntent.getActivity(this, 0, menuIntent, 0));
            mLiveCard.attach(this);
            mLiveCard.publish(PublishMode.REVEAL);
        } else {
            mLiveCard.navigate();
        }

        return START_STICKY;
    }*/
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
    	if(mLiveCard==null){
    		mLiveCard = new LiveCard(this, LIVE_CARD_TAG);
       	 	
       	 	RemoteViews remoteviews=new RemoteViews(this.getPackageName(), R.layout.livecard);//left to check
      		mLiveCard.setViews(remoteviews);
      		
      		
      		mLiveCard.setAction(PendingIntent.getActivity(this,0, intent,0));
      		mLiveCard.publish(LiveCard.PublishMode.REVEAL);
      		try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      		try {
				String text = askForData(this);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return START_STICKY;
    }
    
    public String askForData(Context context) throws InterruptedException{
    	RemoteViews remoteviews = new RemoteViews(context.getPackageName(),R.layout.livecard); // Defining a view. We need to give some text to the view. And it has to be the address.
		remoteviews.setTextViewText(R.id.livecard_content, "What would you like to do?");
		mLiveCard.setViews(remoteviews); 
		
		
		Intent intent = new Intent(context, VoiceActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		mLiveCard.setAction(PendingIntent.getActivity(context, 0, intent, 0));

		if(! mLiveCard.isPublished()){
			mLiveCard.publish(LiveCard.PublishMode.REVEAL);
			
		}
		else{
			Log.d(TAG,"liveCard not published");
			Thread.sleep(1000);
		}
		startActivity(intent);
    	return "";
    }

    @Override
    public void onDestroy() {
        if (mLiveCard != null && mLiveCard.isPublished()) {
            mLiveCard.unpublish();
            mLiveCard = null;
        }
        super.onDestroy();
    }
}
