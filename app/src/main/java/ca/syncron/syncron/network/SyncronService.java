package ca.syncron.syncron.network;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import ca.syncron.syncron.R;
import ca.syncron.syncron.system.NotificationReceiver;
import ca.syncron.syncron.system.Syncron;

//import ca.syncron.system.Syncron;
public class SyncronService extends Service {
////////////////////////////////////////////////////////////////////
public static MsgObject msgObj;
//	global app class
Syncron app;// = (Syncron) getApplicationContext();
Boolean updateUI = false;
Handler            handler;
SocketClientThread clientThread;
Thread             updateUIThread;
IntentFilter       filter;
BroadcastReceiver  updateUIReciver;
//public MyReceiver mMyReceiver = new MyReceiver();
public boolean serviceRunning;
////////////////////////////////////////////////////////////////////    Analog List
String[] keys;
int[]    values;
int[] id = {R.id.row_name, R.id.row_value};

ArrayList<HashMap<String, String>> list;
int      layout  = R.layout.list_2value_row;
String[] strKeys = {"name", "value"};
ListView lv;

////////////////////////////////////////////////////////////////////
public SyncronService() {
	boolean serviceRunning = true;
	//app = (Syncron) getApplicationContext();
}

@Override
public void onDestroy() {
	super.onDestroy();
	boolean serviceRunning = false;
}
//@Override
//public void onCreate() {
//    super.onCreate();
//
//}


@Override
public void onCreate() {
	super.onCreate();
	boolean serviceRunning = true;
	Toast.makeText(this, "Service Started.", Toast.LENGTH_LONG).show();
	filter = new IntentFilter();
	serviceNotification();
	filter.addAction("syncron.ui.getdata");

	updateUIReciver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			//UI update here
			//sendToUI();
			// Toast.makeText(SyncronService.this,"Intent ui.getdata RECEIVED:SERVICE",Toast.LENGTH_SHORT).show();
			  getData();
		            }
		        };
       // MyReceiver myReceiver = new MyReceiver();
		 registerReceiver(updateUIReciver,filter);

	}


	private void sendToUI() {
		Intent local = new Intent();

		local.setAction("syncron.ui.update");

		this.sendBroadcast(local);
	}


	public int onStartCommand(Intent intent, int flags, int startId) {
	    //TODO do something useful
	    return Service.START_NOT_STICKY;
	  }
	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	 public void getData() {
		 // serviceNotification();


		 ObjectMessenger messenger = new ObjectMessenger(Syncron.getInstance());
		 messenger.start();

		 //SocketClientThread clientThread;
		 //// Toast.makeText(this,"Starting clientThread :Service",Toast.LENGTH_SHORT).show();
		 //try {
			// clientThread = new SocketClientThread();
			// clientThread.start();
		 //} catch (Exception e) {}

    	    /*synchronized (clientThread) {
    	    	try {
    				Thread.sleep(100);
    				clientThread.wait();
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}*/
		 //}
		 //txtOut.setText(msgObj.rowData);
	 }
	/**
	 * @return the msgObj
	 */
	synchronized public static MsgObject getMsgObj() {
		return msgObj;
	}

	/**
	 * @param pMsgObj the msgObj to set
	 */
	synchronized public static void setMsgObj(MsgObject pMsgObj) {
		msgObj = pMsgObj;
	}

public void serviceNotification(){
    // prepare intent which is triggered if the
// notification is selected

    Intent intent = new Intent(this, NotificationReceiver.class);
    PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

// build notification
// the addAction re-use the same intent to keep the example short
    Notification n  = new Notification.Builder(this)
                              .setContentTitle("Data service running")
                              .setContentText("Subject")
                              .setSmallIcon(R.drawable.ic_launcher)
                              .setContentIntent(pIntent)
                              .setAutoCancel(true).build();
                              /*.addAction(R.drawable.syncron_filled, "Call", pIntent)
                              .addAction(R.drawable.syncron_filled, "More", pIntent)
                              .addAction(R.drawable.syncron_filled, "And more", pIntent).build();*/


    NotificationManager notificationManager =
            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    notificationManager.notify(0, n);
}

/*ListAdapter adapter;
private void UpdateUI() {
    handler.post(new Runnable() {



        @Override
        public void run() {

            int index = lv.getFirstVisiblePosition();
            View v = lv.getChildAt(0);
            int top = (v == null) ? 0 : (v.getTop() - lv.getPaddingTop());


            values = msgObj.analogVals;
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


            *//*if (values != null) {
                t1.setText("" + msgObj.analogVals[1]);
                t2.setText("" + msgObj.analogVals[2]);
                t3.setText("" + msgObj.analogVals[3]);
            } else {
                t1.setText("1");
                t2.setText("3");
                t3.setText("4");
            }*//*
            if (values != null) {

                //ListView lv = findViewById(R.id.value_list);

                //ListAdapter
                // if (adapter == null) {
                adapter = new SimpleAdapter(MyActivity.this, list,
                                            layout,
                                            strKeys,
                                            id);
                ListView listView = (ListView) findViewById(R.id.value_list);
                listView.setAdapter(adapter);
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
                lv.setSelectionFromTop(index, top);
                //lv.setAdapter(adapter);
                Message msg = new Message();
                msg.obj = adapter;
                handler.sendMessage(msg);
            }

        }

    });

}*/
}
