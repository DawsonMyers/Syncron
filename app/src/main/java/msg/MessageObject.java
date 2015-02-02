package msg;

import java.io.Serializable;

/**
 * Created by Dawson on 1/29/2015.
 */
public class MessageObject implements Serializable{
public int mMsgId, mObjectId, mUserId, mActionId, mStatus;
public String[] mStringsArgs;
public int[] mIntArgs;
public String mIntent;
Long mTime;

Object mMsgObject;
}
