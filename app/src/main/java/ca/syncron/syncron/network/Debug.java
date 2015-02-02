package ca.syncron.syncron.network;

import android.util.Log;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////	debug
public final class Debug{
	private Debug (){}
	
	public static void out (Object msg){
		Log.i ("info", msg.toString ());
	}
}