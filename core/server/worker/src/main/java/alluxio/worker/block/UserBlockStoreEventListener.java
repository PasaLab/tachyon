package alluxio.worker.block;

public interface UserBlockStoreEventListener {

   void onAccessBlockByUser(long sessionId, long blockId, String user);

   void onAbortBlockByUser(long sessionId, long blockId, String user);


   void onCommitBlockByUser(long sessionId, long blockId, String user);

   void onMoveBlockByClientByUser(long sessionId, long blockId, BlockStoreLocation oldLocation,
                                  BlockStoreLocation newLocation, String user);


   void onMoveBlockByWorkerByUser(long sessionId, long blockId, String user);

   void onRemoveBlockByClienByUser(long sessionId, long blockId, String user);

   void onRemoveBlockByWorkerByUser(long sessionId, long blockId, String user);
}
