package ca.syncron.app2.tools;

import android.util.Log;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////	debug
public final class Debug {
	private Debug(){}
	
	public static void out (Object msg){
		Log.i ("Syncron", msg.toString ());
	}public static void out (Object msg, Object tag){
		Log.i (tag.toString(), msg.toString ());
	}
}