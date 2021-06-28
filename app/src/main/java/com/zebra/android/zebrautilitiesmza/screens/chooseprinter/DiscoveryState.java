package com.zebra.android.zebrautilitiesmza.screens.chooseprinter;

public enum DiscoveryState
{
	   BT_FINISHED("BT_FINISHED", 3),
	   BT_STARTED("BT_STARTED", 1),
	   NETWORK_FINISHED("NETWORK_FINISHED", 4),
	   NETWORK_STARTED("NETWORK_STARTED", 2),
	   NOT_STARTED("NOT_STARTED", 0),
	   TEAR_DOWN_DISCO("TEAR_DOWN_DISCO", 5);
	   
	   	private static final DiscoveryState[] $VALUES;
	   static {
	      DiscoveryState[] var0 = new DiscoveryState[]{NOT_STARTED, BT_STARTED, NETWORK_STARTED, BT_FINISHED, NETWORK_FINISHED, TEAR_DOWN_DISCO};
	      $VALUES = var0;
	   }

	   DiscoveryState(String var1, int var2) {}
//  static
//  {
	/*NOT_STARTED (0),
	BT_STARTED (1),  //= 	new DiscoveryState("BT_STARTED", 1),
    NETWORK_STARTED (2),// = new DiscoveryState("NETWORK_STARTED", 2);
    BT_FINISHED (3),//= new DiscoveryState("BT_FINISHED", 3);
    NETWORK_FINISHED (4),// = new DiscoveryState("NETWORK_FINISHED", 4);
    TEAR_DOWN_DISCO (5);//= new DiscoveryState("TEAR_DOWN_DISCO", 5);
	*/
    /*DiscoveryState[] arrayOfDiscoveryState = new DiscoveryState[6];
    arrayOfDiscoveryState[0] = NOT_STARTED;
    arrayOfDiscoveryState[1] = BT_STARTED;
    arrayOfDiscoveryState[2] = NETWORK_STARTED;
    arrayOfDiscoveryState[3] = BT_FINISHED;
    arrayOfDiscoveryState[4] = NETWORK_FINISHED;
    arrayOfDiscoveryState[5] = TEAR_DOWN_DISCO;
    $VALUES = arrayOfDiscoveryState;*/
  //}
	/*private final int levelCode;

    private DiscoveryState(int plevelCode) {
        this.levelCode = plevelCode;
    }*/
  //private DiscoveryState() {}	
}