package msg;

/**
 * Created by Dawson on 2/1/2015.
 */
public class ObjectMessenger implements MsgConstants {
public static MessageWrapper msg = new MessageWrapper();
public static String			IP			= IP_HOME;							// "192.168.1.109";
public static int				port		= PORT_SERVER;						// 6005;

	public ObjectMessenger() {

	}
public ObjectMessenger(MessageWrapper msg) {
	sendMessage(msg);
	}

public void sendMessage(final MessageWrapper msg){
	/*Thread thread = new Thread(){
		public void run() {
			ObjectMessengerThread messenger = new ObjectMessengerThread(IP, port);
			messenger.sendReqest(msg);
		}
	};
	thread.start();*/
}

	}
