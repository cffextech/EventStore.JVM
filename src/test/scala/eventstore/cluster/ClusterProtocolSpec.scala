package eventstore
package cluster

import org.joda.time.{ DateTime, DateTimeZone }
import org.specs2.mutable.Specification
import org.specs2.specification.Scope
import NodeState.{ Master, Slave }
import eventstore.cluster.ClusterProtocol.ClusterInfoFormat
import play.api.libs.json.Json

class ClusterProtocolSpec extends Specification {
  "ClusterProtocol" should {
    "parse gossip.json" in new TestScope {
      val is = getClass.getResourceAsStream("gossip.json")
      val json = Json.parse(is)
      val actual = ClusterInfoFormat.reads(json).get
      actual mustEqual clusterInfo
    }

    "read & write cluster info" in new TestScope {
      val json = ClusterInfoFormat.writes(clusterInfo)
      ClusterInfoFormat.reads(json).get mustEqual clusterInfo
    }

    trait TestScope extends Scope {
      val clusterInfo = ClusterInfo("127.0.0.1" :: 2113, List(
        MemberInfo(
          instanceId = "4534f211-10af-45f1-87c0-8398215328be".uuid,
          timestamp = new DateTime(2014, 9, 24, 19, 53, 18, 590, DateTimeZone.UTC),
          state = Slave,
          isAlive = false,
          internalTcp = "127.0.0.1" :: 3111,
          externalTcp = "127.0.0.1" :: 3112,
          internalSecureTcp = "127.0.0.1" :: 0,
          externalSecureTcp = "127.0.0.1" :: 0,
          internalHttp = "127.0.0.1" :: 3113,
          externalHttp = "127.0.0.1" :: 3114,
          lastCommitPosition = 115826,
          writerCheckpoint = 131337,
          chaserCheckpoint = 131337,
          epochPosition = 131149,
          epochNumber = 1,
          epochId = "b5c64b95-9c8f-4e1c-82f4-f619118edb73".uuid,
          nodePriority = 0),
        MemberInfo(
          instanceId = "8f680215-3abe-4aed-9d06-c5725776303d".uuid,
          timestamp = new DateTime(2015, 1, 29, 10, 23, 9, 41, DateTimeZone.UTC),
          state = Master,
          isAlive = true,
          internalTcp = "127.0.0.1" :: 2111,
          externalTcp = "127.0.0.1" :: 2112,
          internalSecureTcp = "127.0.0.1" :: 0,
          externalSecureTcp = "127.0.0.1" :: 0,
          internalHttp = "127.0.0.1" :: 2113,
          externalHttp = "127.0.0.1" :: 2114,
          lastCommitPosition = 115826,
          writerCheckpoint = 131337,
          chaserCheckpoint = 131337,
          epochPosition = 131149,
          epochNumber = 1,
          epochId = "b5c64b95-9c8f-4e1c-82f4-f619118edb73".uuid,
          nodePriority = 0),
        MemberInfo(
          instanceId = "44baf256-55a4-4ccc-b6ef-7bd383c88991".uuid,
          timestamp = new DateTime(2015, 1, 26, 19, 52, 40, DateTimeZone.UTC),
          state = Slave,
          isAlive = true,
          internalTcp = "127.0.0.1" :: 1111,
          externalTcp = "127.0.0.1" :: 1112,
          internalSecureTcp = "127.0.0.1" :: 0,
          externalSecureTcp = "127.0.0.1" :: 0,
          internalHttp = "127.0.0.1" :: 1113,
          externalHttp = "127.0.0.1" :: 1114,
          lastCommitPosition = 115826,
          writerCheckpoint = 131337,
          chaserCheckpoint = 131337,
          epochPosition = 131149,
          epochNumber = 1,
          epochId = "b5c64b95-9c8f-4e1c-82f4-f619118edb73".uuid,
          nodePriority = 0)))
    }
  }
}
