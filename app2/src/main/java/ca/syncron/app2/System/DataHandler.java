package ca.syncron.app2.System;

import msg.NodeMsgData;

/**
 * Created by Dawson on 2/1/2015.
 */
public class DataHandler {
public Syncron app = Syncron.getSingletonInstance();
// controller data interface
// ///////////////////////////////////////////////////////////////////////////////////

// Node data access
// ////////////////////////////////////////////////////////////////

public synchronized NodeMsgData getNodeData() {
	return app.nodeData.getNodeMsgData();
}

public synchronized void setNodeData(NodeMsgData nodeMsgData) {
	app.nodeData.setNodeMsgData(nodeMsgData);
}

public synchronized String getAnalogString() {
	return app.nodeData.getAnalogString();
}

public synchronized void getNodeData(NodeMsgData nodeMsgData) {
	app.nodeData.setNodeMsgData(nodeMsgData);
}

}
