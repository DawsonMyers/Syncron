package ca.syncron.syncron.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import sync.utils.obj.sock.MsgObject;
import ca.syncron.syncron.system.Syncron;
//import ca.syncron.network.MsgObject;
//import javax.swing.JOptionPane;

//import projClasses.DbDataObject;

//import sqlTestingDec27.MySqlSocketTester;

public class SocketClientThread extends Thread {
	Socket requestSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	ObjectOutputStream outMsg;
	ObjectInputStream inMsg;
	String message, query;
	// DbDataObject dbData;
    public static String syncronIP = "192.163.250.179";
    public static String IP = syncronIP;
	MsgObject msgObj = new MsgObject();
	public boolean quit = false;

	public SocketClientThread() {
	msgObj = Syncron.getMsgObj();
	}
	public SocketClientThread(int port) {
	}

	public SocketClientThread(Socket socket) {
		requestSocket = socket;
	}

	synchronized public void setExternalObj(MsgObject msgObj) {
		Syncron.setMsgObj(msgObj);
	}
synchronized public void onPause() {
    try {
        if(!requestSocket.equals(null)) requestSocket.close();
        if(!in.equals(null)) in.close();
        if(!out.equals(null)) out.close();
    } catch (IOException e) {
        e.printStackTrace();
    }

}
	public void run() {
		Debug.out("android socket thread is running");
		msgObj =Syncron.getMsgObj();
		String s = "S";
		String q = "QUIT";
		try {

			Socket requestSocket = new Socket(IP, 6005);
//			Socket mSocket = new Socket("localhost", 6004);
			// Debug.out("Connected to localhost in port 6004");

			// 2. get Input and Output streams
			ObjectInputStream in = new ObjectInputStream(requestSocket.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();


			// 3: Communicating with the server
			//BufferedReader inLine = new BufferedReader(new InputStreamReader(System.in));

			Debug.out("Enter command:");
			while (!requestSocket.isClosed()) {
				if(quit) break;
				if (true) {


					message = "Sending request to server from android";
					Debug.out("server>" + message);

					//MsgObject msgObj = new MsgObject();
					// "SELECT * FROM DataLive"
					query = msgObj.sqlQuery;
					//msgObj.sqlQuery = query;
//					if (query.length() > 0) msgObj.setIntent(msgObj.SQL);
//					else msgObj.setIntent(msgObj.TESTSQL);

					try {
						out.writeObject(msgObj);
						out.flush();
					} catch (Exception e) {

						e.printStackTrace();
					}

					try {
						msgObj = (MsgObject) in.readObject();
						setExternalObj(msgObj);
					} catch (ClassNotFoundException e) {

						e.printStackTrace();
					}
					if (msgObj.getSuccess()) {

					} else Debug.out("msg not successfull");

					quit = true;
				}

			}
		} catch (IOException e) {

			e.printStackTrace();
		} finally {

            try {
                if(requestSocket!=null) requestSocket.close();
                if(in!=null) in.close();
                if(out!=null) out.close();
            } catch (Exception e ) {
                e.printStackTrace();
            }
            synchronized (this) {
					this.notify();
				}


		}


	}

	synchronized void sendObject(MsgObject msgObj) {
		try {

			/*
			 * msgObj.alRowList = dbData.alRowList; msgObj.colLabels =
			 * dbData.colLabels; msgObj.columns = dbData.columns; msgObj.rowData
			 * = dbData.getRowData(); msgObj.RowList = dbData.RowList;
			 * msgObj.rsMap = dbData.rsMap;
			 */

			out.writeObject(msgObj);
			out.flush();

			Debug.out("client>" + "Db object sent to server");

		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	/*
	 * synchronized void sendObject(DbDataObject dbData) { try {
	 *
	 * msgObj.alRowList = dbData.alRowList; msgObj.colLabels = dbData.colLabels;
	 * msgObj.columns = dbData.columns; msgObj.rowData = dbData.getRowData();
	 * msgObj.RowList = dbData.RowList; msgObj.rsMap = dbData.rsMap;
	 *
	 * out.writeObject(msgObj); out.flush();
	 *
	 * Debug.out("client>" + "Db object sent to server");
	 *
	 * } catch (IOException ioException) { ioException.printStackTrace(); } }
	 */

	synchronized void sendMsg(String msg) {
		try {
			outMsg.writeObject(msg);
			outMsg.flush();
			Debug.out("server>" + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

	}

	// public static void main(String args[]) throws SQLException,
	// ClassNotFoundException {
	// Requester client = new Requester();
	// client.run();
	// }

}