package ca.syncron.app2.async;

import android.content.Context;
import android.content.Intent;

import ca.syncron.app2.System.Syncron;

/**
 * Created by Dawson on 1/31/2015.
 */
public class Broadcaster/* extends BroadcastReceiver */{
Syncron controller = Syncron.getSingletonInstance();
Context mContext;
Broadcaster(){
	mContext = Syncron.getSingletonInstance();
}
public void sendMsgIntent() {
	Intent intent = new Intent();
	//intent.setAction("syncron-getdata");
	intent.setAction("syncron.ui.getdata");
	//BroadcastReceiver.sendBroadcast(intent);

}
}
