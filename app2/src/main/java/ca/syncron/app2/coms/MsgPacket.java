/**
 * 
 */
package ca.syncron.app2.coms;

import org.json.JSONObject;

import java.net.DatagramPacket;
import java.util.Map;

import ca.syncron.app2.System.SyncUtils;


// import net.sf.json.JSONObject;
// import net.sf.json.JSONSerializer;

/**
 * @author Dawson
 */
public class MsgPacket implements ComConstants {
//public final static Logger         log = LoggerFactory.getLogger(MsgPacket.class.getName());
public              DatagramPacket dp  = null;
;
private Client mClient = null;
;

private String mJsonMsg = "";
Map<String, String> jMap = null;

public String                protocol      = "";
public String                cmd           = "";
public String                targetId      = "";
public String                pin           = "";
public String                value         = "";
public Client				client		= null;

	// Constructors
	// ///////////////////////////////////////////////////////////////////////////////////

	public MsgPacket() {}

	public MsgPacket(Client client, String jsonMsg, DatagramPacket dp) {
		setDp(dp);
		setClient(client);
		setJsonMsg(jsonMsg);
		addNewClient();
	}

	public MsgPacket(Client client, String jsonMsg) {

		setClient(client);
		setJsonMsg(jsonMsg);
		// addNewClient();
	}

	public MsgPacket(String jsonMsg, DatagramPacket dp) {
		setDp(dp);
		setClient(client);
		setJsonMsg(jsonMsg);
		addNewClient();
	}

	// Message Getters/Setters
	// ///////////////////////////////////////////////////////////////////////////////////

	public void addNewClient() {
		client = new Client(dp);

	}


	/**
	 * @return object dp of type DatagramPacket
	 */
	public DatagramPacket getDp() {
		return this.dp;
	}

	/**
	 * @param dp
	 *            the dp to set
	 */
	public void setDp(DatagramPacket dp) {
		this.dp = dp;
	}


	/**
	 * @return object client of type Client
	 */
	public Client getClient() {
		return this.mClient;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Client client) {
		this.mClient = client;
	}

	/**
	 * @return object jasonMsg of type String
	 */
	// public String getJsonMsg() {
	// return this.mJsonMsg;
	// }

	// Set message data
	// ///////////////////////////////////////////////////////////////////////////////////

	/**
	 * @param jsonMsg
	 *            the jasonMsg to set
	 */
	public void setJsonMsg(String jsonMsg) {
		this.mJsonMsg = jsonMsg;
		setPacketData();
	}

	public void setPacketData(String msgString) {
		dp.setData(msgString.getBytes());
	}

	public void setPacketData() {
		dp.setData(mJsonMsg.getBytes());
	}

	public void extractJsonData(Map<String, String> json) {
	//	log.d("Extracting data");
		// protocol = (String) json.get(PROTOCAL);
		cmd = (String) json.get(CMD).toString();
		// targetId = (String) json.get(TARGET_ID).toString();
		pin = (String) json.get(PIN).toString();
		value = (String) json.get(VALUE).toString();
	}

	public String toJsonString(Map<String, String> jMap) {
		try {
			JSONObject json = new JSONObject();
			mJsonMsg = json.toJSONString(jMap);
		} catch (Exception e) {
			e.printStackTrace();
			SyncUtils.getDateBox();
		}
		return mJsonMsg;
	}


	// Member setter/getter
	// ///////////////////////////////////////////////////////////////////////////////////

	/**
	 * @return object jasonMsg of type String
	 */
	public String getJsonMsg() {
		return mJsonMsg;
	}

	public int getPin() {
		return Integer.parseInt(pin);

	}

	public int getIntValue() {
		return Integer.parseInt(value);

	}

	public String getStringValue() {
		return value;

	}

	public void setPin(String pin) {
		this.pin = pin;

	}

	public void setValue(String val) {
		value = val;

	}

	public void setCmd(String cmd) {
		this.cmd = cmd;

	}


}
