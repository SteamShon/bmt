import com.thinkaurelius.titan.core.TitanFactory
import com.thinkaurelius.titan.core.Order
import com.thinkaurelius.titan.graphdb.configuration.GraphDatabaseConfiguration
import java.util.Random
rand = new Random()

zkQuorum = args[0]
numOfRows = 1000
numOfCols = 10
dimOfRows = numOfRows
dimOfCols = numOfRows

labelName = "friends"
userIdKey = "userId";
timestampKey = "timestamp";


batchSize = 1000
titanConf = new BaseConfiguration()
titanConf.setProperty("storage.backend","hbase")
titanConf.setProperty("storage.hostname",zkQuorum)
titanConf.setProperty("storage.hbase.region-count",5)
titanConf.setProperty("storage.write-attempts",100)
titanConf.setProperty("storage.tablename", "titan")
titanConf.setProperty("storage.batch-loading",true)
titanConf.setProperty("ids.block-size", 4000000)
titanConf.setProperty("persist-wait-time",30000)
titanConf.setProperty("persist-attempts",100)
titanConf.setProperty("storage.idauthority-block-size", 4000000)
titanConf.setProperty("storage.buffer-size", batchSize * 2)
titanConf.setProperty("storage.lock-wait-time", 10000)
titanConf.setProperty("storage.idauthority-wait-time", 10000)
titanConf.setProperty("autotype", "none")

g = TitanFactory.open(titanConf)
/*
mgmt = g.getManagementSystem()

if (mgmt.getPropertyKey(userIdKey) == null) {
  userIdProp = mgmt.makePropertyKey(userIdKey).dataType(String.class).cardinality(Cardinality.SINGLE).make();
} else {
  userIdProp = mgmt.getPropertyKey(userIdKey);
}

if (mgmt.getPropertyKey(timestampKey) == null) {
  timestampProp = mgmt.makePropertyKey(timestampKey).dataType(Long.class).cardinality(Cardinality.SINGLE).make();
} else {
  timestampProp = getPropertyKey(timestampKey);
}

mgmt.buildIndex('byUserId', Vertex.class).addKey(userIdProp).unique().buildCompositeIndex();

mgmt.makeEdgeLabel(labelName).multiplicity(Multiplicity.MULTI).make();

friend = mgmt.getEdgeLabel(labelName);

mgmt.buildEdgeIndex(friend, 'friendByTimestamp', Direction.BOTH, Order.DESC, timestampProp);

mgmt.commit();
*/
bg = new BatchGraph(g, VertexIDType.STRING, batchSize)
bg.setVertexIdKey(userIdKey)
bg.setLoadingFromScratch(true)

// create vertex first.
cnt = 0
for (i = 0; i < numOfRows; i++) {
  row = i.toString()
  if (i % batchSize == 0) println i + " vertices inserted."
  v = bg.addVertex(row)
  v.setProperty(userIdKey, row)
  cnt += 1
}
//bg.commit()

println (new Date()).toString()
println cnt + " vertices completed."

cnt = 0
for (i = 0; i < numOfRows; i++) {
  row = i.toString()
  srcV = bg.getVertex(row)
  if (srcV == null) continue
  
  for (j = 0; j < numOfCols; j++) {
    cnt += 1
    if (cnt % batchSize == 0) println i + " edges inserted."
    
    col = rand.nextInt(dimOfCols).toString()
    ts = System.currentTimeMillis()
    tgtV = bg.getVertex(col)
    if (tgtV == null) continue
    edge = bg.addEdge(null, srcV, tgtV, labelName)
    if (edge == null) continue
    edge.setProperty(timestampKey, ts)
  }
}

bg.commit()
println cnt + " edges completed."
System.exit(0);
