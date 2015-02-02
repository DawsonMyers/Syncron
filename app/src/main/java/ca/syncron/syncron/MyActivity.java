package ca.syncron.syncron;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;

import ca.syncron.syncron.network.ObjectMessenger;
import ca.syncron.syncron.network.SocketClientThread;
import ca.syncron.syncron.network.SyncronService;
import ca.syncron.syncron.system.Syncron;
import sync.utils.obj.sock.MsgObject;


public class MyActivity extends Activity implements View.OnClickListener {

Button  btnHome;
Syncron app;
public static MsgObject msgObj = new MsgObject();

//public static MsgObject msgObj;                // = new MsgObject();
Button   btn1;
Button   btn2;
EditText text;
private View btnLive;

TextView t1;
TextView t2;
TextView t3;
Boolean updateUI = false;
Handler            handler;
SocketClientThread clientThread;
Thread             updateUIThread;
IntentFilter       filter;
BroadcastReceiver  updateUIReciver;
ListView           lv;
ListAdapter        adapter;
ListAdapter        adapterOld;

@Override
protected void onPause() {
	super.onPause();
	// clientThread.onPause();
	//updateUI=false;
	unregisterReceiver(updateUIReciver);
	setUpdateUI(false);


}

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_my);
	lv = (ListView) findViewById(R.id.value_list);
	app = (Syncron) getApplicationContext();
	//startService(new Intent(this, SyncronService.class));
	// app.sendIntent();
	btn1 = (Button) findViewById(R.id.btn1);

	btn1.setOnClickListener(this);

	//  start list again after rotate
	if (updateUI) startUpdatingUI();

	msgObj = new MsgObject();
	if (handler == null) handler = new Handler() {
        public void handleMessage(Message msg) {
            //ListAdapter listadapter = (ListAdapter) msg.obj;
            // lv.setAdapter(listadapter);

            //((SimpleAdapter) adapter).notifyDataSetChanged();
            //lv.invalidate();

            //adapter = new SimpleAdapter(MyActivity.this, list,
            //                            layout,
            //                            strKeys,
            //                            id);
            //lv = (ListView) findViewById(R.id.value_list);
            //lv.setAdapter(adapter);

        }
    };
    // app = (Syncron) getApplicationContext();

    //  text = (EditText) findViewById(R.id.editText1);
    t1 = (TextView) findViewById(R.id.tv1);
    t2 = (TextView) findViewById(R.id.tv2);
    t3 = (TextView) findViewById(R.id.tv3);
    btn1 = (Button) findViewById(R.id.btn1);
    btn2 = (Button) findViewById(R.id.btn2);
    btnLive = (Button) findViewById(R.id.btnLive);
    btn1.setOnClickListener(this);
    btn2.setOnClickListener(this);
    btnLive.setOnClickListener(this);
    //ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    filter = new IntentFilter();
    ListAdapter adapter;
    filter.addAction("syncron.ui.update");

    updateUIReciver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // UI update here

        }
    };
    registerReceiver(updateUIReciver, filter);


}

public void onClick(View v) {
    // TODO Auto-generated method stub
    switch (v.getId()) {

            case R.id.btn1:
	            ObjectMessenger messenger = new ObjectMessenger(/*Syncron.getInstance()*/);
	            messenger.start();
                //Intent i;
                //msgObj.sqlQuery = MsgObject.QUERY4;
                //msgObj.setIntent(msgObj.SQL());
                //getData();
                // i = new Intent(MainActivity.this, DataList.class);
                // startActivity(i);
                startService(new Intent(this, SyncronService.class));
                break;
            case R.id.btn2:
               // setList();
                app.sendIntent();
                app.getData();
                //text.setText(msgObj.rowData);
                //
                //Intent i2;
                //msgObj.sqlQuery = MsgObject.QUERY4;
                //msgObj.setIntent(msgObj.SQL());
                //getData();
                // i2 = new Intent(MainActivity.this, DataList.class);
                // startActivity(i2);
                break;
            case R.id.btnLive:
                // text.setText(msgObj.rowData);
                app.msgObj.setIntent(app.msgObj.STREAM);
                app.getData();
	            app.startService();
                setUpdateUI(!updateUI);
                startUpdatingUI();

                // try {
                // Thread.sleep(100);
                // } catch (InterruptedException e) {
                // e.printStackTrace();
                // }
                // if (app.app.msgObj.analogVals != null) {
                // t1.setText("" + app.app.msgObj.analogVals[1]);
                // t2.setText("" + app.app.msgObj.analogVals[2]);
                // t3.setText("" + app.app.msgObj.analogVals[3]);
                // } else {
                // t1.setText("1");
                // t2.setText("3");
                // t3.setText("4");
                // }
                // Intent i3;
                // //app.app.msgObj.sqlQuery = MsgObject.QUERY4;
                // //getData();
                // i3 = new Intent(MainActivity.this, LiveActivity.class);
                // startActivity(i3);
                break;
        }
    }

private synchronized void startUpdatingUI() {

    if (getUpdateUI()) {
        updateUIThread = new Thread("UIUpdateThread") {

            public void run() {
                while (getUpdateUI()) {
                    app.msgObj.setIntent(MsgObject.STREAM);
                    //getData();

                    //  thread started in service using intent
                    app.getData();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    UpdateUI();
                }
            }
        };
        updateUIThread.start();
    }
}

	/*
	 * private void UpdateUI() { handler.post(new Runnable() {
	 * @Override public void run() { if (app.msgObj.analogVals != null) {
	 * t1.setText("" + app.msgObj.analogVals[1]); t2.setText("" +
	 * app.msgObj.analogVals[2]); t3.setText("" + app.msgObj.analogVals[3]); } else {
	 * t1.setText("1"); t2.setText("3"); t3.setText("4"); } } }); } };
	 * updateUIThread.start(); }
	 */

String[] keys;
int[]    values;
int[] id = {R.id.row_name, R.id.row_value};

ArrayList<HashMap<String, String>> list;
int      layout  = R.layout.list_2value_row;
String[] strKeys = {"name", "value"};
int listIndex;

private void UpdateUI() {
	handler.post(new Runnable() {


		@Override
		public void run() {
			if (getUpdateUI()) try {
				listIndex = lv.getFirstVisiblePosition();
				View v = lv.getChildAt(0);
				int top = (v == null) ? 0 : (v.getTop() - lv.getPaddingTop());


				values = app.msgObj.analogVals;
				list = new ArrayList<HashMap<String, String>>();
				//HashMap<String, String> map = new HashMap<String, String>();
				keys = new String[values.length];
				//int[] id = {R.id.row_name, R.id.row_value};
				//String[] strKeys = {"name", "value"};
				//int layout = R.layout.list_2value_row;
				for (int i = 0; i < values.length; i++) {
					keys[i] = "Analog " + i;
					//map.put(keys[i], values[i] + "");
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(strKeys[0], keys[i]);
					map.put(strKeys[1], values[i] + "");
					list.add(map);
				}


				if (values != null) {
					t1.setText("" + app.msgObj.analogVals[1]);
					t2.setText("" + app.msgObj.analogVals[2]);
					t3.setText("" + app.msgObj.analogVals[3]);
				} else {
					t1.setText("1");
					t2.setText("3");
					t3.setText("4");
				}
				if (values != null) {

					//ListView lv = findViewById(R.id.value_list);

					//ListAdapter
					// if (adapter == null) {
					adapter = new SimpleAdapter(MyActivity.this, list,
					                            layout,
					                            strKeys,
					                            id);
					//ListView listView = (ListView) findViewById(R.id.value_list);
					lv.setAdapter(adapter);
					//} else {
					//    //update only dataset
					//    //list = latestetParedJson;
					//    ((SimpleAdapter) adapter).notifyDataSetChanged();
					//    ((SimpleAdapter) adapter).notifyDataSetInvalidated();
					//    //((SimpleAdapter) adapter).notifyDataSetInvalidated();
					//    //FrameLayout frame = (FrameLayout)findViewById(R.id.frame_layout);
					//    //frame.removeAllViews();
					//    //frame.addView(lv);
					//}
					lv.setSelectionFromTop(listIndex, top);
					//lv.setAdapter(adapter);
					Message msg = new Message();
					msg.obj = adapter;
					handler.sendMessage(msg);
				}
			} catch (RuntimeException e) {
			}
		}

	});

}

public synchronized void setList() {
	int[] values = msgObj.analogVals;
	ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
	//HashMap<String, String> map = new HashMap<String, String>();
	String[] keys = new String[values.length];
	int[] id = {R.id.row_name, R.id.row_value};
	int layout = R.layout.list_2value_row;
	for (int i = 0; i < values.length; i++) {
		keys[i] = "Analog " + i;
		//map.put(keys[i], values[i] + "");
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(keys[i], values[i] + "");
		list.add(map);
	}


	if (values != null) {
		t1.setText("" + msgObj.analogVals[1]);
		t2.setText("" + msgObj.analogVals[2]);
		t3.setText("" + msgObj.analogVals[3]);
	} else {
		t1.setText("1");
		t2.setText("3");
		t3.setText("4");
	}
	if (values != null) {

		//ListView lv = findViewById(R.id.value_list);


		ListAdapter adapter = new SimpleAdapter(MyActivity.this, list,
		                                        layout,
		                                        keys,
		                                        id);
		ListView listView = (ListView) findViewById(R.id.value_list);
		listView.setAdapter(adapter);


		//lv.setAdapter(adapter);
		//Message msg = new Message();
		//msg.obj = adapter;
		//handler.sendMessage(msg);
	}
}

public synchronized void setUpdateUI(Boolean updateUI) {
	this.updateUI = updateUI;
}

public synchronized Boolean getUpdateUI() {
	return this.updateUI;
}

@Override
public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.menu_my , menu);
	return true;
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
	// Handle action bar item clicks here. The action bar will
	// automatically handle clicks on the Home/Up button, so long
	// as you specify a parent activity in AndroidManifest.xml.
	int id = item.getItemId();
	if (id == R.id.action_settings) {
		return true;
	}
	return super.onOptionsItemSelected(item);
}

public void getData() {
	clientThread = new SocketClientThread();
	clientThread.start();
}

synchronized public static MsgObject getMsgObj() {
	return msgObj;
}

/**
 * @param pMsgObj
 *            the msgObj to set
 */
synchronized public static void setMsgObj(MsgObject pMsgObj) {
	msgObj = pMsgObj;
	Syncron.setMsgObj(pMsgObj);
}

}
