package msg;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import socketmsg.ObjectMessengerBase;

public class ObjectMessengerThread extends ObjectMessengerBase implements MsgConstants{
// MessageWrapper msg = new MessageWrapper();
public boolean mode       = false;
public String  ip         = "192.168.1.109";
public int     serverPort = 6005;
public Socket socket;
public ObjLock objLock = new ObjLock();
public String  query   = "";
public int actionId = STREAM;
public int requestId;

// public ObjLock objLock = new ObjLock();

	public ObjectMessengerThread(Socket socket) {
		// super(socket);
		this();
		this.socket = socket;
		start();
	}

// Constructors
// ///////////////////////////////////////////////////////////////////////////////////
public ObjectMessengerThread() {
	super();

	}

	public ObjectMessengerThread(String ip, int port) {
		// super(ip, port);
		this();
	}

	// Send/receive message
	// ///////////////////////////////////////////////////////////////////////////////////
	public MessageWrapper sendReqest(MessageWrapper msg,
			String ip, int port) {
		this.ip = ip;
		this.serverPort = port;
		this.msg = msg;

		requestId = msg.mRequestId;
		String q = msg.getQuery();
		if (q.length() < 5) query = q;
		else requestId = STREAM;
		this.start();
		synchronized (objLock) {
			try {
				// this.sleep(20);
				objLock.wait();
				os.close();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return this.msg;
	}
//	Send with only msg
public MessageWrapper sendReqest(MessageWrapper msg) {

	this.msg = msg;
	this.start();


	synchronized (objLock) {
		try {

			objLock.wait();
			os.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	return this.msg;
}


// Run
	// ///////////////////////////////////////////////////////////////////////////////////
	// either send-receive (client) or receive-send (server)
	public synchronized void run() {
		if (socket == null) {
			//	client request
			
			os.connectClient(ip, serverPort);
			//MessageWrapper msg = new MessageWrapper();
			//msg.mStatus = 0;
			timer.start();
			//msg.setRequestId(SQL);
			//msg.setRequestId(requestId);
			//msg.setRequestSql(query);
			//msg.messageObj.dbBundle.sqlQuery = QUERY5;
			sendMessage(this.msg);
			System.out.println((new SimpleDateFormat("[MMM-dd HH.mm.ss.SSS]")).format(new Date()) + " Message sent to server");
			msg = readMessage();
			System.out.println((new SimpleDateFormat("[MMM-dd HH.mm.ss.SSS]")).format(new Date()) + " Message Received from server");
			timer.finish(msgTime);
			timer.print();
			System.out.println((new SimpleDateFormat("[MMM-dd HH.mm.ss.SSS]")).format(new Date()) + " Message #" + msg.mStatus);
		} else {
			//	server response
			os.connectServer(socket);
			msg = os.readMessage();
			MsgResponseHandler msgHandler = new MsgResponseHandler(msg);
			msg.mStatus = 1;//MessageServer.getCount();
			sendMessage(msg);

		}
		// notifies waiting threads
		os.close();
		synchronized (this) {
			this.notify();
		}
		synchronized (objLock) {
			objLock.notifyAll();
		}
	}

}
