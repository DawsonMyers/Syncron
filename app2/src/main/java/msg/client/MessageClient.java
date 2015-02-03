package msg.client;

import java.text.SimpleDateFormat;
import java.util.Date;

import ca.syncron.app2.System.Syncron;
import msg.MessageWrapper;
import msg.MsgConstants;
import msg.ObjectMessengerThread;

public class MessageClient extends Thread implements MsgConstants {
public static String         IP      = IP_HOME;                            // "192.168.1.109";
public static int            port    = PORT_SERVER;                        // 6005;
public static MessageWrapper msg     = new MessageWrapper();
public        ObjLock        objLock = new ObjLock();
Syncron app = Syncron.getSingletonInstance();

public MessageClient() {
	super("MsgClientThread");

}

public MessageWrapper sendMessage(MessageWrapper msg) {
	this.msg = msg;
	start();
	synchronized (objLock) {
		try {
			objLock.wait(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	return this.msg;

}

@Override
public void run() {
	super.run();

	ObjectMessengerThread messenger = new ObjectMessengerThread(IP, port);


	long msgTime = System.currentTimeMillis();
	System.out.println((new SimpleDateFormat("[MMM-dd HH.mm.ss.SSS]")).format(new Date()) + " Message sent to server");

	// set message request id and query so that the server will be able
	// to get the data that was requested
	msg.setRequestId(msg.mRequestId);
	msg.setRequestSql(msg.getQuery());
	msg = messenger.sendReqest(msg, IP, port);

	msgTime = System.currentTimeMillis() - msgTime;
	System.out.println((new SimpleDateFormat("[MMM-dd HH.mm.ss.SSS]")).format(new Date()) + " Message Received from server");
	System.out.println(msg.messageObj.dbBundle.rowData);
	synchronized (objLock) {
		objLock.notify();
	}

	System.out.println("terminating messageg thread");
}

public class ObjLock {
	int lockId = 0;

	public ObjLock() {}

	;
}

}
