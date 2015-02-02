package ca.syncron.syncron.system;

import android.content.Context;
import android.content.ContextWrapper;

import msg.MessageWrapper;
import sync.utils.obj.sock.MsgObject;

/**
 * Created by Dawson on 1/31/2015.
 */
public class DataHandler extends ContextWrapper{
Syncron syncron;

public DataHandler(Context base) {
	super(base);
	syncron = (Syncron) this.getBaseContext();// Syncron.getSingletonInstance();
}

public double getMsgReceiveRate() {
	return msgReceiveRate;
}

synchronized public void setMsgReceiveRate(double rate) {
	msgReceiveRate = rate;
}

synchronized public static MsgObject getMsgObj() {
	return msgObj;
}

/**
 * @param pMsgObj the msgObj to set
 */
synchronized public static void setMsgObj(MsgObject pMsgObj) {
	msgObj = pMsgObj;
}

synchronized public static void setMsgObj(MessageWrapper msg) {
	msg = msg;
}

synchronized public static int[] getAnalogValues() {
	return msgObj.getAnalogVals();
}

synchronized public static void setAnalogValues(int[] analogValues) {
	msgObj.setAnalogVals(analogValues);
}



}
