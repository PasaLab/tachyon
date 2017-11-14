package alluxio.worker.block.evictor;

import alluxio.worker.block.AbstractBlockStoreEventListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GDSFEvictor extends AbstractBlockStoreEventListener implements Evictor {

  protected Map<String, Map<Long, Double>> mTierCache = Collections.synchronizedMap(new HashMap<>(3));

}
