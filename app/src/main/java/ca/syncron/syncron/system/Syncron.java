package ca.syncron.syncron.system;


//import sync.utils.obj.sock.MsgObject;
//import ca.syncron.network.SyncronService;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import ca.syncron.syncron.R;
import ca.syncron.syncron.network.SyncronService;
import msg.MessageWrapper;
import sync.utils.obj.sock.MsgObject;
//import ca.syncron.network.MsgObject;

public class Syncron extends Application implements Handler.Callback {

public        MyReceiver mMyReceiver = new MyReceiver();
public static MsgObject  msgObj      = new MsgObject();
private String name;
private String email;
public String ip = "http://dawsonmyers.ca";
public SyncronService service;

////////////////////////////////////////////////////////////////////    Analog List
String[] keys;
int[]    values;
int[] id = {R.id.row_name, R.id.row_value};

ArrayList<HashMap<String, String>> list;
int      layout  = R.layout.list_2value_row;
String[] strKeys = {"name", "value"};
ListView lv;

public long    msgLastReceiveTime = 0;
public long    msgSendTime        = 0;
public long    msgReceiveTime     = 0;
public long    msgReceiveCount    = 0;
public double  msgReceiveRate     = 0.0;
public boolean updateUI           = false;
public static MessageWrapper msg;
public Context syncronContext;
public static Syncron singleton;
public static DataHandler dataHandler ;
// ///////////////////////////////////////////////////////////////////////////////////

private static Syncron syncron = new Syncron( );
//	access with: Syncron controller = Syncron.getSingletonInstance();
private Syncron(){ }

public synchronized static Syncron getSingletonInstance( ) {
	return syncron;
}
// ///////////////////////////////////////////////////////////////////////////////////

public synchronized long getMsgReceiveTime() {
	return msgReceiveTime;
}

public static Syncron getInstance() {
	return singleton;
}

@Override
public void onCreate() {
	super.onCreate();
	singleton = this;
	syncron = this;
	dataHandler = new DataHandler( );
}



public Intent intent = new Intent();

////////////////////////////////////////////////////////////////////
public void getData() {
	//Intent intent = new Intent();
	//intent.setAction("syncron.ui.getdata");
	intent.setAction("syncron.ui.getdata");
	sendBroadcast(intent);
	// Toast.makeText(this,"Intent ui.getdata Called",Toast.LENGTH_SHORT).show();

}

private static final String TAG = "EAT";
public Handler mWorkerHandler, handler;
Thread t;

public void sendIntent() {
	Intent intent = new Intent();
	//intent.setAction("syncron-getdata");
	intent.setAction("syncron.ui.getdata");
	sendBroadcast(intent);
	Toast.makeText(this, "Intent Called", Toast.LENGTH_SHORT).show();

}

public synchronized void setUpdateUI(Boolean updateUI) {
	this.updateUI = updateUI;
}

public synchronized Boolean getUpdateUI() {
	return this.updateUI;
}

public void startService() {
	//service = new SyncronService();
	//Intent i = new Intent(Syncron.this, SyncronService.class);
	//service.startService(i);
	startService(new Intent(Syncron.this, SyncronService.class));
}


private void sendToUI() {
	Intent local = new Intent();

	local.setAction("syncron.ui.update");

	this.sendBroadcast(local);
}

ListAdapter adapter;

@Override
public boolean handleMessage(Message msg) {
	return false;
}

public class AdapterBundle {
	String[] keys;
	int[]    values;
	int[] id = {R.id.row_name, R.id.row_value};
	int                                listIndex;
	ArrayList<HashMap<String, String>> list;
	int      layout  = R.layout.list_2value_row;
	String[] strKeys = {"name", "value"};
	ListView lv;
}
}
