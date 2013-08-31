/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package app.light;

import static app.light.CommonUtilities.SENDER_ID;

import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;

/**
 * IntentService responsible for handling GCM messages.
 */
public class GCMIntentService extends GCMBaseIntentService {

    @SuppressWarnings("hiding")
    private static final String TAG = "GCMIntentService";

    public GCMIntentService() {
        super(SENDER_ID);
    }

    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        System.out.println(getString(R.string.gcm_registered));
        ServerUtilities.register(context, registrationId);
    }

    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        System.out.println(getString(R.string.gcm_unregistered));
        if (GCMRegistrar.isRegisteredOnServer(context)) {
            ServerUtilities.unregister(context, registrationId);
        } else {
            // This callback results from the call to unregister made on
            // ServerUtilities when the registration to the server failed.
            Log.i(TAG, "Ignoring unregister callback");
        }
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message"); 
        
       // String message = intent.getStringExtra("nickname")+":"+intent.getStringExtra("content");
        String type = intent.getStringExtra("type");
        String nickname = intent.getStringExtra("nickname");
        String pre_content = intent.getStringExtra("pre_content");
        String content = intent.getStringExtra("content");
        String calorie = intent.getStringExtra("calorie");
        String date = intent.getStringExtra("date");
        
        JSONObject json_obj = new JSONObject();
        try{
        	json_obj.put("type", type);
        	json_obj.put("nickname", nickname);
        	json_obj.put("pre_content", pre_content);
        	json_obj.put("content", content);
        	json_obj.put("calorie", calorie);
        	json_obj.put("date", date);
        }
        catch(Exception e){
        	System.out.println("JSON 데이터 만들기 에러");
        }
        
        System.out.println("onMessage => "+nickname+" : "+content);       
        // notifies user
        
        boolean running_status = isRunningProcess(context, "app.light");
        if(running_status) {	//앱이 동작중일 때	
        	String running_activity = getRunningActivity(context);
        	System.out.println("RUNNING => "+running_activity);  
        	TimelineFrag tmp_tf = new TimelineFrag();
        	tmp_tf.addTimelineData(json_obj);
        }
        else {	//앱이 동작중이지 않을 때
        	generateNotification(context, json_obj);
        }     
    }

    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message = getString(R.string.gcm_deleted, total);
        // notifies user
        //generateNotification(context, message, "");
    }

    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    private static void generateNotification(Context context, JSONObject json_param) {
    	
    	try{
    		String tmp_type = json_param.getString("type");
	    	String tmp_nickname = json_param.getString("nickname");
	    	String tmp_content = json_param.getString("content");
	    	
	    	int icon = R.drawable.light_icon;
	        long when = System.currentTimeMillis();
	        NotificationManager notificationManager = (NotificationManager)
	                context.getSystemService(Context.NOTIFICATION_SERVICE);
	        String message = "";
	        if (tmp_type.equals("6")){
	        	message = tmp_nickname + " : " + "사진";
	        }
	        else {
	        	message = tmp_nickname + " : " + tmp_content;
	        }
	        
	        //Notification notification = new Notification(icon, message, when);
	        Notification notification = new Notification.Builder(context)
	        								.setSmallIcon(icon)
	        								.setContentTitle(message)
	        								.setWhen(when)
	        								.build();
	        
	        String title = context.getString(R.string.app_name);
	        Intent notificationIntent = new Intent(context, LoginActivity.class);
	        notificationIntent.putExtra("nickname", tmp_nickname);
	        notificationIntent.putExtra("content", tmp_content);
	        // set intent so it does not start a new activity
	        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
	        PendingIntent intent =
	                PendingIntent.getActivity(context, 0, notificationIntent, 0);
	        notification.setLatestEventInfo(context, title, message, intent);
	        notification.flags |= Notification.FLAG_AUTO_CANCEL;
	        notificationManager.notify(0, notification);
    	}
    	catch(Exception e){
    		System.out.println("JSON 데이터 가져오기 실패");
    	}    
    }

    public static boolean isRunningProcess(Context context, String packageName) {	 
        boolean isRunning = false;
        ActivityManager actMng = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);                      
        List<RunningAppProcessInfo> list = actMng.getRunningAppProcesses();     
 
        for(RunningAppProcessInfo rap : list)                           
        {                                
            if(rap.processName.equals(packageName))                              
            {   
                isRunning = true;     
                break;
            }                         
        }
        return isRunning;
    }
    
    public static String getRunningActivity(Context context) {
		ActivityManager activityManager = (ActivityManager)context.getSystemService(context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> info;
		info = activityManager.getRunningTasks(1);
		for (Iterator iterator = info.iterator(); iterator.hasNext();) {
			RunningTaskInfo runningTaskInfo = (RunningTaskInfo) iterator.next();
			return runningTaskInfo.topActivity.getClassName();
		}
		return "";
    }
}
