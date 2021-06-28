 package com.zebra.android.zebrautilitiesmza.util;
 
 
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import ar.gov.mendoza.PrometeoMuni.R;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalStateApp;

import com.zebra.android.zebrautilitiesmza.ZebraUtilitiesApplication;
import com.zebra.sdk.comm.UsbConnection;
import com.zebra.sdk.printer.discovery.DiscoveredPrinter;
import com.zebra.sdk.printer.discovery.DiscoveredPrinterBluetooth;
import com.zebra.sdk.printer.discovery.DiscoveredPrinterNetwork;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
 	   public class ZebraUtilitiesOptions
	   {
		   public static final String CURRENTLY_SELECTED_PRINTER = "CURRENTLY_SELECTED_PRINTER";
		   public static final String CUSTOM_DPI = "CUSTOM_DPI";
		   public static final String CUSTOM_PAGE_HEIGHT = "CUSTOM_PAGE_HEIGHT";
		   public static final String CUSTOM_PAGE_SIZE = "Custom...";
		   public static final String CUSTOM_PAGE_WIDTH = "CUSTOM_PAGE_WIDTH";
		   public static final String DEFAULT_PRINTER_LANGUAGE = "DEFAULT_PRINTER_LANGUAGE";
		   public static final String DEFAULT_PRINTER_PORT = "DEFAULT_PRINTER_PORT";
		   public static final String FILE_DIRECTORY = "FILE_DIRECTORY";
		   public static final String GET_FILES_FROM_PRINTER = "GET_FILES_FROM_PRINTER";
		   public static final boolean IS_FIRST_START_UP = true;
		   public static final String KNOWN_PRINTER_FRIENDLY_NAME = "KNOWN_PRINTER_FRIENDLY_NAME";
		   public static final String KNOWN_PRINTER_IP_DNS = "KNOWN_PRINTER_IP_DNS";
		   public static final String KNOWN_PRINTER_IS_BLUETOOTH = "KNOWN_PRINTER_IS_BLUETOOTH";
		   public static final String KNOWN_PRINTER_MAC_ADDRESS = "KNOWN_PRINTER_MAC_ADDRESS";
		   public static final String KNOWN_PRINTER_PORT = "KNOWN_PRINTER_PORT";
		   public static final String MANUAL_ADD_BT_ADDRESS = "MANUAL_ADD_BT_ADDRESS";
		   public static final String MANUAL_ADD_IP_DNS = "MANUAL_ADD_IP_DNS";
		   public static final String MANUAL_ADD_IS_BT_SELECTED = "MANUAL_ADD_IS_BT_SELECTED";
		   public static final String MANUAL_ADD_PORT = "MANUAL_ADD_PORT";
		   public static final String NUMBER_OF_KNOWN_PRINTERS = "NUMBER_OF_KNOWN_PRINTERS";
		   public static final String PAGE_SIZE_OPTION = "PAGE_SIZE_OPTION";
		   public static final String REMOTE_STORAGE_CHECKBOX = "REMOTE_STORAGE_CHECKBOX";
		   public static final String REMOTE_STORAGE_PASSWORD = "REMOTE_STORAGE_PASSWORD";
		   public static final String REMOTE_STORAGE_SETTINGS = "REMOTE_STORAGE_SETTINGS";
		   public static final String REMOTE_STORAGE_URL = "REMOTE_STORAGE_URL";
		   public static final String REMOTE_STORAGE_USERNAME = "REMOTE_STORAGE_USERNAME";
		   public static final String SELECTED_PRINTER_FRIENDLY_NAME = "SELECTED_PRINTER_FRIENDLY_NAME";
		   public static final String SELECTED_PRINTER_IP_DNS = "SELECTED_PRINTER_IP_DNS";
		   public static final String SELECTED_PRINTER_IS_BLUETOOTH = "SELECTED_PRINTER_IS_BLUETOOTH";
		   public static final String SELECTED_PRINTER_MAC_ADDRESS = "SELECTED_PRINTER_MAC_ADDRESS";
		   public static final String SELECTED_PRINTER_PORT = "SELECTED_PRINTER_PORT";
		   public static UsbConnection usbConnection = null;

		   public static void resetPrefDefaults()
		   {
		     SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);
		     Editor editor = p.edit();
		     editor.clear();
		     editor.commit();
		     
		     PreferenceManager.setDefaultValues(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT,R.xml.preferences, true);//2130968577
		     PreferenceManager.setDefaultValues(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT,R.xml.storage_preferences, true);// 2130968578
		     editor.commit();
		   }
		   
		   public static boolean getFilesFromPrinter()
		   {
		     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);
		 	      return sharedPref.getBoolean("GET_FILES_FROM_PRINTER", false);
		 	      //return PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT).getBoolean("GET_FILES_FROM_PRINTER", false);
		   }
		   
		   public static String getDefaultPrinterPort()
		   {
		     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);
		     Map<String, ?> pMap = sharedPref.getAll();
		     return (String)pMap.get("DEFAULT_PRINTER_PORT");
		   }
		   
		   public static int getDefaultPrinterPortAsInteger()
		   {
		     int port = 9100;
		     try
		     {
		       return Integer.parseInt(getDefaultPrinterPort());
		     }
		     catch (Exception localException) {}
		     return port;
		   }
		   
		   
		   public static String getPageSize()
		   {
		     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);
		 		  Map<String, ?> pMap = sharedPref.getAll();
		     return (String)pMap.get("PAGE_SIZE_OPTION");
		   }
		   
		   public static String getRemoteStoragePassword()
		   {
		     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);Map<String, ?> pMap = sharedPref.getAll();
		     return (String)pMap.get("REMOTE_STORAGE_PASSWORD");
		   }
		   
		   public static String getRemoteStorageUserName()
		   {
		     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);Map<String, ?> pMap = sharedPref.getAll();
		     return (String)pMap.get("REMOTE_STORAGE_USERNAME");
		   }
		   
		   public static String getRemoteStorageUrl()
		   {
		     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);Map<String, ?> pMap = sharedPref.getAll();
		     return (String)pMap.get("REMOTE_STORAGE_URL");
		   }
		   
		   
		   public static String getSelectedPrinterDisplayString()
		   {
		     if (usbConnection != null)
		     {
		       String str = usbConnection.getDeviceName();
		       return str;
		     }
		     else
		     {
			     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);
			     Map<String, ?> pMap = sharedPref.getAll();
			     
			     boolean b = pMap.containsKey("CURRENTLY_SELECTED_PRINTER");
			     if (b) 
			     {
			       return (String)pMap.get("CURRENTLY_SELECTED_PRINTER");
			     }
			     else
			     {
			    	 return "No Printer Selected";
			     }
		     }
		     
		   }
		   
		 
		   
		   
		   public static String getManualAddIpDns()
		   {
		    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);Map<String, ?> pMap = sharedPref.getAll();
		    if (pMap.containsKey("MANUAL_ADD_IP_DNS")) 
		    {
		       return (String)pMap.get("MANUAL_ADD_IP_DNS");
		    }
		    else
		    {
		     return "";
		    }
		   }

		   
		   public static String getManualAddPort()
		   {
		     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);Map<String, ?> pMap = sharedPref.getAll();
		     if (pMap.containsKey("MANUAL_ADD_PORT")) 
				  {
		       return (String)pMap.get("MANUAL_ADD_PORT");
		     }
				  else
		     return "6101";
		   }
		   


		   public static String getManualAddBtAddress()
		   {
		     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);Map<String, ?> pMap = sharedPref.getAll();
		     if (pMap.containsKey("MANUAL_ADD_BT_ADDRESS")) {
		       return (String)pMap.get("MANUAL_ADD_BT_ADDRESS");
		     }
		     return "";
		   }
		   
		   public static boolean getIsBtManualAddSelected()
		   {
		     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);Map<String, ?> pMap = sharedPref.getAll();
		     if (pMap.containsKey("MANUAL_ADD_IS_BT_SELECTED")) {
		       return ((Boolean)pMap.get("MANUAL_ADD_IS_BT_SELECTED")).booleanValue();
		     }
		     return true;
		   }
		   
		   public static Boolean isPrinterSelected()
		   {
		     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);Map<String, ?> pMap = sharedPref.getAll();
		     return Boolean.valueOf(pMap.containsKey("CURRENTLY_SELECTED_PRINTER"));
		   }
		   
		   public static String getFileDir()
		   {
		     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);Map<String, ?> pMap = sharedPref.getAll();
		     return (String)pMap.get("FILE_DIRECTORY");
		   }
		   
		   public static String getPrinterLanguage()
		   {
		     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);Map<String, ?> pMap = sharedPref.getAll();
		     return (String)pMap.get("DEFAULT_PRINTER_LANGUAGE");
		   }
		   
		   private static String getCustomHeight()
		   {
		     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);Map<String, ?> pMap = sharedPref.getAll();
		     return (String)pMap.get("CUSTOM_PAGE_HEIGHT");
		   }
		   
		   private static String getCustomWidth()
		   {
		     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);Map<String, ?> pMap = sharedPref.getAll();
		     return (String)pMap.get("CUSTOM_PAGE_WIDTH");
		   }
		   
		   private static float getCustomDpi()
		   {
		     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);Map<String, ?> pMap = sharedPref.getAll();
		     Object o = pMap.get("CUSTOM_DPI");
		     String str = (String)o;float retValue = Float.parseFloat(str);
		     return retValue;
		   }
		   
		   public static void setFileDir(String dir)
		   {
		     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);
		     Editor editor = sharedPref.edit();
		     editor.putString("FILE_DIRECTORY", dir);editor.commit();
		   }
		   
		   public static void setPrinterLanguage(String lang)
		   {
		     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);
		     Editor editor = sharedPref.edit();
		     editor.putString("DEFAULT_PRINTER_LANGUAGE", lang);editor.commit();
		   }
		   
		   public static void setManualAddIpDns(String ipDns)
		   {
		     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);
		     Editor editor = sharedPref.edit();
		     editor.putString("MANUAL_ADD_IP_DNS", ipDns);editor.commit();
		   }
		   
		   public static void setManualAddPort(String port)
		   {
		     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);
		     Editor editor = sharedPref.edit();
		     editor.putString("MANUAL_ADD_PORT", port);editor.commit();
		   }
		   
		   public static void setManualAddBtAddress(String btAddr)
		   {
		     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);
		     Editor editor = sharedPref.edit();
		     editor.putString("MANUAL_ADD_BT_ADDRESS", btAddr);editor.commit();
		   }
		   
		   public static void setManualIsBtSelected(Boolean isBtSelected)
		   {
		     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);
		     Editor editor = sharedPref.edit();
		     editor.putBoolean("MANUAL_ADD_IS_BT_SELECTED", isBtSelected.booleanValue());editor.commit();
		   }
		   
		   
		   public static void setSelectedPrinter(DiscoveredPrinter printer)
		   {
		 		 SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);
		 		 Editor editor = sharedPref.edit();
			     String str;
			     if ((printer instanceof DiscoveredPrinterBluetooth))
			     {
						DiscoveredPrinterBluetooth tmp = (DiscoveredPrinterBluetooth)printer;
						if (tmp.friendlyName == null)
						{
							str = tmp.address;
						}
						else
						{
						 str  = tmp.friendlyName;	
						}
						editor.putString("CURRENTLY_SELECTED_PRINTER", str);
						editor.putBoolean("SELECTED_PRINTER_IS_BLUETOOTH", true);
						editor.putString("SELECTED_PRINTER_IP_DNS", "");
						editor.putString("SELECTED_PRINTER_PORT", "-1");
						editor.putString("SELECTED_PRINTER_FRIENDLY_NAME", tmp.friendlyName);
						editor.putString("SELECTED_PRINTER_MAC_ADDRESS", tmp.address);
				  }
				  else if ((printer instanceof DiscoveredPrinterNetwork))
				  {
					  DiscoveredPrinterNetwork 	tmp = (DiscoveredPrinterNetwork)printer;
					  editor.putString("CURRENTLY_SELECTED_PRINTER", tmp.address);
					  editor.putBoolean("SELECTED_PRINTER_IS_BLUETOOTH", false);
					  editor.putString("SELECTED_PRINTER_IP_DNS", tmp.address);
					  editor.putString("SELECTED_PRINTER_PORT", tmp.getDiscoveryDataMap().get("PORT"));
					  editor.putString("SELECTED_PRINTER_FRIENDLY_NAME", tmp.getDiscoveryDataMap().get("DNS_NAME"));
					  editor.putString("SELECTED_PRINTER_MAC_ADDRESS", "");
				  }
				  editor.commit();
				}

		   
		   public static DiscoveredPrinter getSelectedPrinter()
		   {
//		     if (usbConnection != null)
//		     {
//		    	 return new DiscoveredPrinterUsb(usbConnection.getDeviceName());
//		     }
//		     else
		     {	 
		    	 	 
				     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);
				     Map<String, ?> pMap = sharedPref.getAll();
				     
				     Object mapValue = pMap.get("SELECTED_PRINTER_IS_BLUETOOTH");

				     if (mapValue == null) 
				     {
				       return null;
				     }
				     Boolean localBoolean = (Boolean)mapValue;
				     boolean isBTPrinter = localBoolean.booleanValue();
				     
				     if (isBTPrinter) 
				     {
				    	 String friendlyName = (String)pMap.get("SELECTED_PRINTER_FRIENDLY_NAME");
				    	 String macAddress = (String)pMap.get("SELECTED_PRINTER_MAC_ADDRESS");
			            return new DiscoveredPrinterBluetooth(macAddress, friendlyName);
				     }
				     else
				    {
				    	  String ipAddress = (String)pMap.get("SELECTED_PRINTER_IP_DNS");
						  String portAsString = (String)pMap.get("SELECTED_PRINTER_PORT");
						  int port = -1;
						  try
						     {
						       int i = Integer.parseInt(portAsString);
						       port = i;
						     }
						     catch (NumberFormatException localNumberFormatException)
						     {
						     }
						    String dnsName = (String)pMap.get("SELECTED_PRINTER_FRIENDLY_NAME");
				            DiscoveredPrinterNetwork retVal = new DiscoveredPrinterNetwork(ipAddress, port);
				            retVal.getDiscoveryDataMap().put("DNS_NAME", dnsName);
				            return retVal;
				    }
		     }
		   }

		   
		   public static boolean isBluetoothPrinterSelected()
		   {
		     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);Map<String, ?> pMap = sharedPref.getAll();
		     Object o = pMap.get("SELECTED_PRINTER_IS_BLUETOOTH");
		     if (o == null) {
		       return false;
		     }
		     Boolean localBoolean = (Boolean)o;
				  return localBoolean.booleanValue();
		   }

		   /*
		    * Graba las impresoras conocidas en las preferencias
		    */
		   public static void setKnownPrinters(List<DiscoveredPrinter> knownPrintersForStorage)
		   {
		     SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);
		     Editor editor = sharedPref.edit();
		     
		     int ix = 0;
		     if (ix >= knownPrintersForStorage.size())
		     {
		       editor.putInt("NUMBER_OF_KNOWN_PRINTERS", knownPrintersForStorage.size());
		       editor.commit();
		       return;
		     }
		     
		     for(ix = 0; ix < knownPrintersForStorage.size(); ++ix) 
		     {   
		    	 DiscoveredPrinter printer = knownPrintersForStorage.get(ix);
		    	 if ((printer instanceof DiscoveredPrinterBluetooth))
			     {
			       DiscoveredPrinterBluetooth tmp = (DiscoveredPrinterBluetooth)printer;
			       editor.putString("CURRENTLY_SELECTED_PRINTER" + ix, tmp.friendlyName);
			       editor.putBoolean("SELECTED_PRINTER_IS_BLUETOOTH" + ix, true);
			       editor.putString("SELECTED_PRINTER_IP_DNS" + ix, "");
			       editor.putString("SELECTED_PRINTER_PORT" + ix, "-1");
			       editor.putString("SELECTED_PRINTER_FRIENDLY_NAME" + ix, tmp.friendlyName);
			       editor.putString("SELECTED_PRINTER_MAC_ADDRESS" + ix, tmp.address);
			       //Alejandro
			       ///BluetoothDevice device = ((DiscoveredPrinter) printer);// adapter.getRemoteDevice(strAddress);
			       //BluetoothDevice dev = new BluetoothClass();	   
			       //BluetoothAdapter.getRemoteDevice(printer.address);
			       try
			       {
			    	  
			    	   
			    	   setBluetoothPairingPin((DiscoveredPrinterBluetooth) printer);

			    	   /*if(device.getBondState() != BluetoothDevice.BOND_BONDED){//device  
			    	         try{  
			    	              autoBond(device.getClass(), device, strPin);//pin  
			    	              createBond(device.getClass(), device);  
			    	              //remoteDevice = device;  
			    	          }  
			    	          catch (Exception e) {  
			    	           // TODO: handle exception  
			    	          System.out.println("");  
			    	          }  
			    	 }  
			    	else {  
			    	          //remoteDevice = device;  
			    	}*/
			    	   
			       } catch(Exception ex)
			       {
			    	   String messages = ex.getMessage() + ex.getLocalizedMessage() ;
			    	   messages ="";
			       }
			       
			       
			     }
		    	 else
		    	 {
		    		 DiscoveredPrinterNetwork tmp = (DiscoveredPrinterNetwork)printer;
  	    		     editor.putString("CURRENTLY_SELECTED_PRINTER" + ix, tmp.address);
  	    		     editor.putBoolean("SELECTED_PRINTER_IS_BLUETOOTH" + ix, false);
  	    		     editor.putString("SELECTED_PRINTER_IP_DNS" + ix, tmp.address);
  	    		     editor.putString("SELECTED_PRINTER_PORT" + ix, tmp.getDiscoveryDataMap().get("PORT"));
  	    		     editor.putString("SELECTED_PRINTER_FRIENDLY_NAME" + ix, tmp.getDiscoveryDataMap().get("DNS_NAME"));
  	    		     editor.putString("SELECTED_PRINTER_MAC_ADDRESS" + ix, "");		 
		    	 }
	 
		    	 
		     }
		     
		   }
   
/* 356:    */   public static List<DiscoveredPrinter> getKnownPrinters()
/* 357:    */   {
/* 358:374 */     List<DiscoveredPrinter> resultList = new ArrayList<DiscoveredPrinter>();
				  SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);
/* 359:375 */     Map<String, ?> pMap = sharedPref.getAll();
/* 360:376 */     Object o = pMap.get("NUMBER_OF_KNOWN_PRINTERS");
				  int numberOfKnownPrinters = 0;
/* 361:377 */     if (o == null) {
					numberOfKnownPrinters =0;
				   }
					else {
					numberOfKnownPrinters = ((Integer)o).intValue();
					}
			   
				for(int ix=0; ix < numberOfKnownPrinters; ++ix) 
				{
				    //Object var10;
					DiscoveredPrinter retVal;
					boolean isBTPrinter = ((Boolean)pMap.get("SELECTED_PRINTER_IS_BLUETOOTH" + ix)).booleanValue();
				    //if(((Boolean)pMap.get("SELECTED_PRINTER_IS_BLUETOOTH" + ix)).booleanValue())
					if (isBTPrinter)
				    {
				       String friendlyName = (String)pMap.get("SELECTED_PRINTER_FRIENDLY_NAME" + ix);
					   String macAddress = (String)pMap.get("SELECTED_PRINTER_MAC_ADDRESS" + ix);
					   retVal = new DiscoveredPrinterBluetooth(macAddress, friendlyName);
				    } 
					else 
				    {
				       //String var5 = (String)pMap.get("SELECTED_PRINTER_IP_DNS" + ix);
				       String ipAddress = (String)pMap.get("SELECTED_PRINTER_IP_DNS" + ix);
				       //String var6 = (String)pMap.get("SELECTED_PRINTER_PORT" + ix);
				       String portAsString = (String)pMap.get("SELECTED_PRINTER_PORT" + ix);
				       //int var7 = -1;
				       int port = -1;
			          try 
			          {
			        	  port = Integer.parseInt(portAsString);
			          } catch (NumberFormatException nexc) 
			          {
			      
			          }
				       
				       String dnsName = (String)pMap.get("SELECTED_PRINTER_FRIENDLY_NAME" + ix);
				       retVal = new DiscoveredPrinterNetwork(ipAddress, port);
				       retVal.getDiscoveryDataMap().put("DNS_NAME", dnsName);
				    }
				
				   resultList.add(retVal);
				 }
				return resultList;
			}
	
			public static boolean getRemoteStorageMode()
			{
			  SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT);
					  return sharedPref.getBoolean("REMOTE_STORAGE_CHECKBOX", false);
			}
			
			public static String getCurrentWidth()
			{
			  boolean customPageSizeSelected = getPageSize().equals("Custom...");
			  if (customPageSizeSelected) {
			    return getCustomWidth();
			  }
			  PageSize pageSize = new PageSize(getPageSize());
			  return pageSize.widthInInches;
			}
			
			public static String getCurrentHeight()
			{
			  boolean customPageSizeSelected = getPageSize().equals("Custom...");
			  if (customPageSizeSelected) {
			    return getCustomHeight();
			  }
			  PageSize pageSize = new PageSize(getPageSize());
			  return pageSize.heightInInches;
			}
			
			public static float getCurrentDpi()
			{
			  boolean customPageSizeSelected = getPageSize().equals("Custom...");
			  if (customPageSizeSelected) {
			    return getCustomDpi();
			  }
			  PageSize pageSize = new PageSize(getPageSize());
			  return pageSize.dotsPerInch;
			}
			

			   static class PageSize
			   {
			     public float dotsPerInch;
			     public String heightInInches;
			     public String widthInInches;
			     
			     public PageSize(String pageSizeString)
			     {
			       parsePageSizeString(pageSizeString);
			     }
			     
			     private void parsePageSizeString(String pageSizeString)
			     {
			       HashMap<String, String> nominalToReal = new HashMap();
			       nominalToReal.put("203", "203.2");
			       nominalToReal.put("300", "304.8");
			       nominalToReal.put("600", "609.6");
			       PageSizeParser parser = new PageSizeParser(pageSizeString);
			       this.widthInInches = parser.getWidth();
			       this.heightInInches = parser.getHeight();
			       String realDpi = nominalToReal.get(parser.getDpi());
			       this.dotsPerInch = Float.parseFloat(realDpi);
			     }
			   }
			   
			   static class PageSizeParser
			   {
			     String input;
			     int inputIndex;
			     
			     public PageSizeParser(String input)
			     {
			       this.inputIndex = 0;
			       this.input = input;
			     }
			     
			     public String getWidth()
			     {
			       this.inputIndex = this.input.indexOf("x");
			       String str = this.input;
			       int i = this.inputIndex;
			       this.inputIndex = (i + 1);
			       return str.substring(0, i);
			     }
			     
			     public String getHeight()
			     {
			       int startIx = this.inputIndex;
			       this.inputIndex = this.input.indexOf(" ", this.inputIndex);
			       String str = this.input;
			       int i = this.inputIndex;
			       this.inputIndex = (i + 1);
			       return str.substring(startIx, i);
			     }
			     
			     public String getDpi()
			     {
			       this.inputIndex = (1 + this.inputIndex);
			       int startIx = this.inputIndex;
			       
			       this.inputIndex = this.input.indexOf(" ", this.inputIndex);
			       return this.input.substring(startIx, this.inputIndex);
			     }
			   }

	   
			   static public boolean autoBond(Class btClass,BluetoothDevice device,String strPin) throws Exception {   
			        Method autoBondMethod = btClass.getMethod("setPin", byte[].class);
			        Boolean result = (Boolean)autoBondMethod.invoke(device,new Object[]{strPin.getBytes()});  
			        return result;  
			    }  
			  
			//  
			    static public boolean createBond(Class btClass,BluetoothDevice device) throws Exception {   
			        Method createBondMethod = btClass.getMethod("createBond");   
			        Boolean returnValue = (Boolean) createBondMethod.invoke(device);   
			        
			        return returnValue.booleanValue();   
			    }  
			    
			   static public void ToolsBluetooth(DiscoveredPrinterBluetooth printer)
			   {
				   
				   Object bluetoothService = GlobalStateApp.getInstance().getApplicationContext().getSystemService("bluetooth");
				   Class bluetoothServiceClass = bluetoothService.getClass();
				   Method[] bluetoothMethods = bluetoothServiceClass.getMethods();
				   //LocalBluetoothDevice local = LocalBluetoothDevice.init();
				   String address = printer.address;
				   BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
				   BluetoothDevice device =  adapter.getRemoteDevice(address);
				   
				   
				   
			   }
			   static public void setBluetoothPairingPin(DiscoveredPrinterBluetooth pPrinter)
			    {
				   String strPin = pPrinter.friendlyName;
		    	   if (strPin.length()>=4)
		    	   {
		    		   strPin = strPin.substring(strPin.length()-4);
		    	   }
		    	   String address = pPrinter.address;
		    	   
		    	   BluetoothAdapter mybAdapter = BluetoothAdapter.getDefaultAdapter();
		    	   BluetoothDevice device = mybAdapter.getRemoteDevice(address);
				   
				   Class btClass = device.getClass();
				   
			        byte[] pinBytes = strPin.getBytes();//convertPinToBytes("0000");
			        try {
			        	Boolean returnValue;
			              try {
			            	  Method setPairingConfirmation = device.getClass().getMethod("setPairingConfirmation", boolean.class);
			            	  returnValue = (Boolean) setPairingConfirmation.invoke(device, true);
			            	  Class[] arrayOfClass = new Class[0];
			            	  Method cancelPairingUserInput = device.getClass().getMethod("cancelPairingUserInput", arrayOfClass);
			            	  returnValue = (Boolean) cancelPairingUserInput.invoke(device,(Object[]) null);
			                    
			                    /*
			                    Method createBondMethod = btClass.getMethod("cancelPairingUserInput");
			                    returnValue = (Boolean) createBondMethod.invoke(device);
			                    */
			                    
			                } catch (Exception e) {
			                    e.printStackTrace();
			                } 
			        	
			        	
			              Method autoBondMethod = device.getClass().getDeclaredMethod("setPin", byte[].class);//byte[].class);//,);
			              
			              byte[] pin = (byte[]) BluetoothDevice.class.getMethod("convertPinToBytes", String.class).invoke(BluetoothDevice.class, strPin);
			              returnValue = (Boolean) autoBondMethod.invoke(device, pin);
			              //returnValue = (Boolean) autoBondMethod.invoke(device, new Object[]{strPin.getBytes()});//pinBytes);//,);
			             // returnValue = device.setPin(strPin.getBytes());
			              
			              try {
			            	  Method setPairingConfirmation = device.getClass().getMethod("setPairingConfirmation", boolean.class);
			            	  returnValue = (Boolean) setPairingConfirmation.invoke(device, true);
			            	  Class[] arrayOfClass = new Class[0];
			            	  Method cancelPairingUserInput = device.getClass().getMethod("cancelPairingUserInput", arrayOfClass);
			            	  returnValue = (Boolean) cancelPairingUserInput.invoke(device,(Object[]) null);
			                    
			                    /*
			                    Method createBondMethod = btClass.getMethod("cancelPairingUserInput");
			                    returnValue = (Boolean) createBondMethod.invoke(device);
			                    */
			                    
			                } catch (Exception e) {
			                    e.printStackTrace();
			                }
			              try
			              {
			            	  Method createBondMethod = device.getClass().getMethod("createBond",(Class[]) null);   
			                  returnValue = (Boolean) createBondMethod.invoke(device,(Object[]) null);    
			              } catch (Exception e) {
			                    e.printStackTrace();
			                }
			              
			              
			            } catch (Exception e) {
			              e.printStackTrace();
			            }
			    }
			    
/* 491:    */ }



