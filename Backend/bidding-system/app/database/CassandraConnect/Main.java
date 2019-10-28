package caconnect

import com.datastax.driver.core.Cluster

import com.datastax.driver.core.ResultSet

import com.datastax.driver.core.Row

import com.datastax.driver.core.Session

//remove if not needed
import scala.collection.JavaConversions._

class cassandraConnect(IPAddress: String, username: String, password: String) {

  private var session: Session = Cluster
    .builder()
    .addContactPoint(IPAddress)
    .withCredentials(username, password)
    .build()
    .connect()

  def execQuery(query: String): ResultSet = session.execute(query)

}

object Main {

  def main(args: Array[String]): Unit = {
    val cc: cassandraConnect =
      new cassandraConnect("127.0.0.1", "cassandra", "cassandra")
    val insertQuery: String =
      "INSERT INTO bidtimeseriesks.BIDTIMESERIES(id, bid, timeseries) values (now(), 'My Bid 1', toTimeStamp(toDate(now())));"
//Create Keyspace
    cc.execQuery(
      "CREATE KEYSPACE IF NOT EXISTS bidtimeseriesks WITH REPLICATION = {'class': 'SimpleStrategy', 'replication_factor': 1};")
//Create table
    cc.execQuery(
      "CREATE TABLE IF NOT EXISTS bidtimeseriesks.BIDTIMESERIES(id UUID PRIMARY KEY, bid text, timeseries timestamp);")
//Insert data into table
    cc.execQuery(insertQuery)
//Query back the table
    val selectQuery: String = "SELECT * FROM bidtimeseriesks.BIDTIMESERIES;"
    val rs: ResultSet = cc.execQuery(selectQuery)
    for (row <- rs) {
      println(row.getUUID(0))
      println(row.getString(1))
      println(row.getTimestamp(2))
    }
  }

}
