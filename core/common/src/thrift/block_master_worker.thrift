namespace java alluxio.thrift

include "common.thrift"
include "exception.thrift"

struct BlockIdToCValue {
  1: i64 BlockId
  2: i64 CValue

}

struct MtLRUSampleTOptions {}
struct MtLRUSampleTResponse {
  1: list<BlockIdToCValue> BlockIdList
}

struct MtLRUGetCricTOptions {
  1: i64 BlockId
}
struct MtLRUGetCricTResponse {
  1: i64 critValue
}

struct MtLRUBroadCricTOptions {
  1: i64 cricValue
}
struct MtLRUBroadCricTResponse {}

service BlockWorkerMasterService extends common.AlluxioService {
  MtLRUSampleTResponse sample(
    1: MtLRUSampleTOptions options,
  )
  throws (1: exception.AlluxioTException e)

  MtLRUGetCricTResponse getCricValue(
    1: MtLRUGetCricTOptions options,
  )
  throws (1: exception.AlluxioTException e)

  MtLRUBroadCricTResponse BroadCricValue(
    1: MtLRUBroadCricTOptions options,
  )
  throws (1: exception.AlluxioTException e)
}
