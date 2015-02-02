package ca.syncron.app2;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ca.syncron.app2.System.Syncron;
import ca.syncron.app2.async.MsgIntentConstants;
import ca.syncron.app2.async.MyReceiver;
import ca.syncron.app2.async.RequestReceiver;
import msg.MsgConstants;

import static android.view.View.OnClickListener;


public class MainActivity extends ActionBarActivity implements OnClickListener, MsgIntentConstants, Handler.Callback, MsgConstants {
Button     btn1;
Button     btn2;
Button     btn3;
TextView   tv;
MyReceiver MyReceiver;
public MyReceiver myReceiver;
public MyReceiver myReceiver2;
public MyReceiver myReceiver3;
public Handler    mHandler;
public Handler    mUiHandler;
public long       mUiThreadId;
Handler handler;
public static String string = "";

public  Syncron         app;
private RequestReceiver reqReceiver;

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	//MainActivity mainActivity = this;

	app = Syncron.getSingletonInstance();
	//app.mActivity = this;


	btn1 = (Button) findViewById(R.id.btn1);
	btn2 = (Button) findViewById(R.id.btn2);
	btn3 = (Button) findViewById(R.id.btn3);
	tv = (TextView) findViewById(R.id.tv1);
	btn1.setOnClickListener(this);
	btn2.setOnClickListener(this);
	btn3.setOnClickListener(this);
	myReceiver = new MyReceiver();
	myReceiver.register(this);
	//myReceiver.setHandler(mHandler);
	mUiThreadId = Thread.currentThread().getId();
	mUiHandler = new Handler();
	handler = new Handler();
	TextView tv;
	IntentFilter filter = new IntentFilter("syncron.ui");

	myReceiver2 = new MyReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Button btn1 = (Button) findViewById(R.id.btn1);
			TextView tv = (TextView) findViewById(R.id.tv1);
			//tv.setText("IT WORKED!!!!!!!!!!!!!!!");
			//tv.setText(intent.getStringExtra("id"));
			//tv.append(intent.getStringExtra("id") + "\n");
			/*MessageWrapper msg = new MessageWrapper(20, 200);
			ObjectMessengerThread messenger = new ObjectMessengerThread(IP, PORT_SERVER);
			messenger.sendReqest(msg);
			msg.setRequestId(SQL);
			msg.setRequestSql(QUERY4);*/

		}


	};
	IntentFilter filter2 = new IntentFilter("syncron.test");
	myReceiver3 = new MyReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Button btn1 = (Button) findViewById(R.id.btn1);
			TextView tv = (TextView) findViewById(R.id.tv1);
			//tv.setText("IT WORKED!!!!!!!!!!!!!!!");
			//tv.setText(intent.getStringExtra("id"));
			tv.append(intent.getStringExtra("id"));
			//MessageWrapper msg = new MessageWrapper(20, 200);
			//ObjectMessengerThread messenger = new ObjectMessengerThread(IP, PORT_SERVER);
			//messenger.sendReqest(msg);
			//msg.setRequestId(SQL);
			//msg.setRequestSql(QUERY4);

		}


	};IntentFilter filter3 = new IntentFilter();
	filter3.addAction("syncron.msg.q.datalive,DONE");
	filter3.addAction("syncron.msg.q.testdb.DONE");
	reqReceiver = new RequestReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Button btn1 = (Button) findViewById(R.id.btn1);
			TextView tv = (TextView) findViewById(R.id.tv1);
			//tv.setText("IT WORKED!!!!!!!!!!!!!!!");
			//tv.setText(intent.getStringExtra("id"));
			tv.append(intent.getStringExtra("id"));
			//MessageWrapper msg = new MessageWrapper(20, 200);
			//ObjectMessengerThread messenger = new ObjectMessengerThread(IP, PORT_SERVER);
			//messenger.sendReqest(msg);
			//msg.setRequestId(SQL);
			//msg.setRequestSql(QUERY4);

		}


	};
	registerReceiver(myReceiver2, filter);
	registerReceiver(myReceiver3, filter2);
	registerReceiver(reqReceiver, filter3);
}
//	//handler = new Handler() {
//		public void handleMessage(Message msg) {
//		}
//	};

//
//}

//public void customRunOnUiThread(Runnable action) {
//	if (Thread.currentThread().getId() != mUiThreadId) {
//		mUiHandler.post(action);
//	} else {
//		action.run();
//	}
//}

@Override
protected void onPause() {
	super.onPause();
	myReceiver.unRegister(this);
	unregisterReceiver(myReceiver2);
	unregisterReceiver(myReceiver3);
}


@Override
public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.menu_main, menu);
	return true;
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
	// Handle action bar item clicks here. The action bar will
	// automatically handle clicks on the Home/Up button, so long
	// as you specify a parent activity in AndroidManifest.xml.
	int id = item.getItemId();

	//noinspection SimplifiableIfStatement
	if (id == R.id.action_settings) {
		return true;
	}

	return super.onOptionsItemSelected(item);
}

@Override
public void onClick(View v) {

	Intent intent = new Intent();

	switch (v.getId()) {
		case R.id.btn1:
			//app.testMsg(this);
			intent.setAction(QUERY_DATALIVE);
			Toast.makeText(this, "Sent "+QUERY_DATALIVE, Toast.LENGTH_SHORT).show();
			sendBroadcast(intent);
		break;
		case R.id.btn2:
			intent.setAction(QUERY_TESTDB);
			sendBroadcast(intent);
			Toast.makeText(this, "Sent "+QUERY_TESTDB, Toast.LENGTH_SHORT).show();
			break;
		case R.id.btn3:
			intent.setAction(dbDataReqReceived);
			sendBroadcast(intent);
			Toast.makeText(this, "Sent: data received", Toast.LENGTH_SHORT).show();
			break;
	}
}

@Override
public boolean handleMessage(Message msg) {
	mHandler.post(new Runnable() {
		Button btn1 = (Button) findViewById(R.id.btn1);
		TextView tv = (TextView) findViewById(R.id.tv1);

		@Override
		public void run() {

			tv.setText("IT WORKED!!!!!!!!!!!!!!!");
		}
		});


	return false;
}
}