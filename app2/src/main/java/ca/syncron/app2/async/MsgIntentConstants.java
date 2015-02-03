package ca.syncron.app2.async;

/**
 * Created by Dawson on 1/31/2015.
 */
public  interface MsgIntentConstants {
final String dbDataRequested   = "syncron.msg.db_data_requested";
final String dbDataReceived    = "syncron.msg.db_data_received";
final String dbDataReqSent     = "syncron.msg.db_data_sent";
final String dbDataReqReceived = "syncron.msg.db_data_data_received";
final String dbDataReady       = "syncron.msg.db_data_done_data_ready";
final String nodeDataRequested = "syncron.msg.node_data_requested";
final String nodeDataReceived  = "syncron.msg.node_data_received";

final String QUERY_DATALIVE      = "syncron.msg.q.datalive";
final String QUERY_DATALIVE_DONE = "syncron.msg.q.datalive.DONE";
final String QUERY_TESTDB        = "syncron.msg.q.testdb";
final String QUERY_TESTDB_DONE   = "syncron.msg.q.testdb.DONE";
final String STREAM_GET          = "syncron.msg.stream.get";
final String STREAM_GET_DONE     = "syncron.msg.stream.get.DONE";
final String STREAM_DONE         = "syncron.msg.stream.DONE";
}
