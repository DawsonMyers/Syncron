package ca.syncron.app2.async;

/**
 * Created by Dawson on 1/31/2015.
 */
public  interface MsgIntentConstants {
final String dbDataRequested = "syncron.msg.db_data_requested";
final String dbDataReceived = "syncron.msg.db_data_received";
final String dbDataReqSent = "syncron.msg.db_data_sent";
final String dbDataReqReceived = "syncron.msg.db_data_data_received";
final String dbDataReady = "syncron.msg.db_data_done_data_ready";
final String nodeDataRequested = "syncron.msg.node_data_requested";
final String nodeDataReceived = "syncron.msg.node_data_received";

final String QUERY_DATALIVE  = "syncron.msg.q.datalive";
final String QUERY_TESTDB  = "syncron.msg.q.testdb";
}
