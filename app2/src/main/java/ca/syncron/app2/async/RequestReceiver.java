package ca.syncron.app2.async;

/**
 * Created by Dawson on 1/27/2015.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

import java.util.ArrayList;

import ca.syncron.app2.MainActivity;
import ca.syncron.app2.tools.Debug;
import msg.MessageWrapper;
import msg.MsgConstants;
import msg.client.MessageClient;

public class RequestReceiver extends BroadcastReceiver implements MsgIntentConstants, MsgConstants {
final String dbDataRequested   = "syncron.msg.db_data_requested";
final String dbDataReceived    = "syncron.msg.db_data_received";
final String dbDataReqSent     = "syncron.msg.db_data_sent";
final String dbDataReqReceived = "syncron.msg.db_data_data_received";
final String dbDataReady       = "syncron.msg.db_data_done_data_ready";
final String nodeDataRequested = "syncron.msg.node_data_requested";
final String nodeDataReceived  = "syncron.msg.node_data_received";
final String QUERY_DATALIVE    = "syncron.msg.q.datalive";
final String QUERY_TESTDB      = "syncron.msg.q.testdb";
public Context      context;
public MainActivity mActivity;
public boolean isRunning = false;
int               receiverCount = 7;
ArrayList<String> al            = new ArrayList<>();
Handler mHandler;
Thread t;

public RequestReceiver() {

	al.add(QUERY_DATALIVE);
	al.add(QUERY_TESTDB);
	al.add(STREAM_GET);

	//al.add(dbDataRequested);
	//al.add(dbDataReceived);
	//al.add(dbDataReqSent);
	//al.add(dbDataReqReceived);
	//al.add(dbDataReady);
	//al.add(nodeDataReceived);
	//al.add(nodeDataRequested);

}

@Override
public void onReceive(Context context, Intent intent) {
	switch (intent.getAction()) {
		case dbDataRequested:

		case QUERY_DATALIVE:
			handleRequest(context, SQL, qDATA_LIVE, intent.getAction());
			context.sendBroadcast(new Intent("syncron.ui").putExtra("id", QUERY4));
			break;
		case QUERY_TESTDB:
			handleRequest(context, SQL, qTEST_DB, intent.getAction());
			//context.sendBroadcast(new Intent("syncron.ui").putExtra("id", QUERY5));
			break;
		case STREAM_GET:
			handleRequest(context, STREAM, 30, intent.getAction());
			//context.sendBroadcast(new Intent("syncron.ui").putExtra("id", "Button 3 clicked"));
			break;
		//case dbDataReady:
		//case nodeDataRequested:
		//case nodeDataReceived:


	}

	//Toast.makeText(context.getApplicationContext(), "data received", Toast.LENGTH_SHORT).show();
	//String action = intent.getExtras();
	//context.sendBroadcast(new Intent("syncron.ui").putExtra("id","Button 1 clicked") );

	// Toast.makeText(context, "Intent Received.", Toast.LENGTH_LONG).show();
}

public synchronized void handleRequest(final Context context, final int requestId, int queryId, final String action) {
	ArrayList<String> queryArray = new ArrayList<>();
	queryArray.add(qDATA_LIVE, QUERY4);
	queryArray.add(qTEST_DB, QUERY5);
	final String query;
	if (queryId < 30) query = queryArray.get(queryId);
	else query = "";


	//@TODO develop queries


	t = new Thread("UIUpdate") {

		public void run() {

			MessageClient msgClient = new MessageClient();

			MessageWrapper msg = new MessageWrapper(requestId, 200);
			msg.setRequestSql(query);
			msg = msgClient.sendMessage(msg);
			String result = msg.messageObj.dbBundle.rowData;
			Debug.out(result);

			Intent i = new Intent(action + ".DONE");

			i.putExtra("id", result);
			i.putExtra("message", msg);
			context.sendBroadcast(i); //sendBroadcast(i);


		}

	};
	t.start();

}

public void register(Context context) {
	this.context = context;
	mActivity = (MainActivity) context;
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

public void setHandler(Handler handler) {
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
