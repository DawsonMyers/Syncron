package ca.syncron.app2.async;

/**
 * Created by Dawson on 1/27/2015.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;

import ca.syncron.app2.MainActivity;

public class MyReceiver extends BroadcastReceiver implements MsgIntentConstants {
int receiverCount = 7;
final String dbDataRequested   = "syncron.msg.db_data_requested";
final String dbDataReceived    = "syncron.msg.db_data_received";
final String dbDataReqSent     = "syncron.msg.db_data_sent";
final String dbDataReqReceived = "syncron.msg.db_data_data_received";
final String dbDataReady       = "syncron.msg.db_data_done_data_ready";
final String nodeDataRequested = "syncron.msg.node_data_requested";
final String nodeDataReceived  = "syncron.msg.node_data_received";
ArrayList<String> al = new ArrayList<>();
Handler mHandler;
public Context context;
public MainActivity mActivity;
public MyReceiver() {
	al.add(dbDataRequested);
	al.add(dbDataReceived);
	al.add(dbDataReqSent);
	al.add(dbDataReqReceived);
	al.add(dbDataReady);
	al.add(nodeDataReceived);
	al.add(nodeDataRequested);

}

@Override
public void onReceive(Context context, Intent intent) {
	switch (intent.getAction()) {
		case dbDataRequested:

		case dbDataReceived:
			context.sendBroadcast(new Intent("syncron.ui").putExtra("id","Button 1 clicked") );
			break;
		case dbDataReqSent:
			context.sendBroadcast(new Intent("syncron.ui").putExtra("id","Button 2 clicked") );
			break;
		case dbDataReqReceived:
			context.sendBroadcast(new Intent("syncron.ui").putExtra("id","Button 3 clicked") );
			break;
		//case dbDataReady:
		//case nodeDataRequested:
		//case nodeDataReceived:


	}

	Toast.makeText(context.getApplicationContext(), "data received", Toast.LENGTH_SHORT).show();
	//String action = intent.getExtras();
	//context.sendBroadcast(new Intent("syncron.ui").putExtra("id","Button 1 clicked") );

	// Toast.makeText(context, "Intent Received.", Toast.LENGTH_LONG).show();
}

public void register(Context context) {
	this.context = context;
	mActivity = (MainActivity) context ;
	IntentFilter filter = new IntentFilter();
	for (String intent : al) {
		filter.addAction(intent);
	}
	context.registerReceiver(this, filter);
}

public void unRegister(Context context) {
	IntentFilter filter = new IntentFilter();
	for (String intent : al) {
		filter.addAction(intent);
	}
		context.unregisterReceiver(this);

}

public void setHandler(Handler handler){
	this.mHandler = handler;
}
/*public void postInActivity(){


	mHandler.post(new Runnable() {



		@Override
		public void run() {

		}

});
}*/
public void sendBroadcast(Context context) {

}
}
