package com.zebra.android.zebrautilitiesmza.util;

import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.device.ZebraIllegalArgumentException;

import java.io.IOException;

public interface ZebraPrinterTaskI<T, S, A>
{
  A doWork(T[] paramArrayOfT)
    throws ConnectionException, ZebraIllegalArgumentException, IOException;
  
  void handleError(Exception paramException);
  
  void onPostExecute(A paramA);
  
  void onPreExecute();
  
  void onProgressUpdate(S... result);
}


