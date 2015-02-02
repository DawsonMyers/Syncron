package ca.syncron.app2.System;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import ca.syncron.app2.MainActivity;
import ca.syncron.app2.async.MyReceiver;
import ca.syncron.app2.async.RequestReceiver;
import ca.syncron.app2.tools.Debug;
import msg.*;

/**
 * Created by Dawson on 1/31/2015.
 */

//public Debug print;
public class Syncron extends Application implements MsgConstants {
public static Syncron     singleton;
public static DataHandler dataHandler;
public static  NodeData nodeData = new NodeData();
private static Syncron  syncron  = new Syncron();
//public static MessageWrapper msg;
public Context         syncronContext;
public MainActivity    mActivity;
public MyReceiver      mReceiver;
public RequestReceiver mReqReceiver;
// ///////////////////////////////////////////////////////////////////////////////////
public NodeMsgData mNodeMsgData = new NodeMsgData();
public boolean     isRunning    = false;

//	access with: Syncron controller = Syncron.getSingletonInstance();
private Syncron() { }

public synchronized static Syncron getSingletonInstance() {
	return syncron;
}

@Override
public void onCreate() {
	super.onCreate();
	//MainActivity mainActivity;
	//Debug.out("d");

}

// ///////////////////////////////////////////////////////////////////////////////////
public void sendIntent(String action) {
	Intent intent = new Intent();

	intent.setAction(action);
	sendBroadcast(intent);
	Toast.makeText(this, "Intent Called", Toast.LENGTH_SHORT).show();

}

public synchronized void testMsg(final Context context){

	if(!isRunning) {
		isRunning = true;

		Thread t = new Thread("UIUpdate") {

			public void run() {

				MessageWrapper msg = new MessageWrapper(20, 200);
				ObjectMessengerThread messenger = new ObjectMessengerThread(IP, PORT_SERVER);
				//messenger.sendReqest(msg);
				msg.setRequestId(SQL);
				msg.setRequestSql(QUERY4);
				msg = messenger.sendReqest(msg, IP, PORT_SERVER);
				String result = msg.messageObj.dbBundle.rowData;
				Debug.out(result);

				MyReceiver myReceiver = new MyReceiver();

				Intent i = new Intent("syncron.test");
				i.putExtra("id", result);
				context.sendBroadcast(i); //sendBroadcast(i);
isRunning = false;
				//ObjectMessenger oMsg = new ObjectMessenger(msg);
				//handler.post(new Runnable() {
				//	@Override
				//	public void run() {
				//		TextView tv = (TextView) findViewById(R.id.tv1);
				//		tv.setText(string);
				//
				//	}
				//
				//});

			}
		};
		t.start();
	}
}


}
