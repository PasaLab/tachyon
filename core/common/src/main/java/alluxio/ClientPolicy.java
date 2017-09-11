package alluxio;


import alluxio.proto.dataserver.Protocol;

public enum ClientPolicy {
  LRU(0),
  LFU(1),
  GREDDY(2);

  private final int mIndex;

  ClientPolicy(int index){
    mIndex = index;
  }

  public final int getNumber() { return mIndex; }

  public static ClientPolicy indexOf(int index) {
    switch (index) {
      case 0: return LRU;
      case 1: return LFU;
      case 2: return GREDDY;
      default: return null;
    }
  }

  public Protocol.PolicyType toProto() {
    return Protocol.PolicyType.valueOf(mIndex);
  }

  public static ClientPolicy fromProto(Protocol.PolicyType policy) {
    return indexOf(policy.getNumber());
  }
}
