package ca.syncron.syncron.system;

/**
 * Created by Dawson on 1/27/2015.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

@Override
public void onReceive(Context context, Intent intent) {
   // Toast.makeText(context, "Intent Received.", Toast.LENGTH_LONG).show();
}
//public void sendIntent() {
//    Intent intent = new Intent( );
//    //intent.setAction("syncron-getdata");
//    intent.setAction("syncron.getdata");
//    this.sendBroadcast(intent);
//    Toast.makeText(this,"Intent Called",Toast.LENGTH_SHORT).show();
//
//}
}