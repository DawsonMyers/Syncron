/**
 *
 */
package msg;

import java.io.Serializable;

/**
 * @author Dawson
 */
public class NodeData implements Serializable {

// Analog input values
public static int[]     analogVals   = null;
// Digital inputs/outputs
public static boolean[] digitalInput = null;

//public static String analogString = null;
public static  boolean[]   digitalOutput = null;
public static  NodeMsgData nodeMsgData   = new NodeMsgData();
private static String      mAnalogString = "";

/**
 * @return object analogVals of type int[]
 */
public static synchronized int[] getAnalogVals() {
	return analogVals;
}

/**
 * @param analogVals the analogVals to set
 */
public static synchronized void setAnalogVals(int[] analogVals) {
	if (analogVals != null)
		NodeData.analogVals = analogVals;
}

/**
 * @return object analogVals of type int[]
 */
public static synchronized String getAnalogString() {

	if (analogVals != null) {
		for (int i = 0; i < analogVals.length; i++) {
			mAnalogString += analogVals[i] + "\t";
		}
	}

	return mAnalogString;
}

/**
 * @return analogString of type String
 */
public static synchronized void setAnalogString(String analogString) {
	mAnalogString = analogString;
}

/**
 * @return object digitalInput of type boolean[]
 */
public static synchronized boolean[] getDigitalInput() {
	return digitalInput;
}

/**
 * @param digitalInput the digitalInput to set
 */
public static synchronized void setDigitalInput(boolean[] digitalInput) {
	if (digitalInput != null)
		NodeData.digitalInput = digitalInput;
}

/**
 * @return object digitalOutput of type boolean[]
 */
public static synchronized boolean[] getDigitalOutput() {
	return digitalOutput;
}

/**
 * @param digitalOutput the digitalOutput to set
 */
public static synchronized void setDigitalOutput(boolean[] digitalOutput) {
	if (digitalOutput != null)
		NodeData.digitalOutput = digitalOutput;
}

/**
 * @return object nodeMsgData of type NodeMsgData
 */
public static synchronized NodeMsgData getNodeMsgData() {
	nodeMsgData.analogVals = analogVals;
	nodeMsgData.digitalInput = digitalInput;
	nodeMsgData.digitalOutput = digitalOutput;
	return nodeMsgData;
}

public void setNodeMsgData(NodeMsgData nodeMsgData) {
	analogVals = nodeMsgData.analogVals;
	digitalInput = nodeMsgData.digitalInput;
	digitalOutput = nodeMsgData.digitalOutput;
}
}
