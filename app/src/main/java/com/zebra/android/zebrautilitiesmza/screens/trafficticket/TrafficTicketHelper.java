package com.zebra.android.zebrautilitiesmza.screens.trafficticket;
import android.graphics.Bitmap;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.graphics.internal.ZebraImageAndroid;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.util.internal.ZPLUtilities;


public class TrafficTicketHelper
{
	   private static ZebraPrinter printer;
	   
	   public static void printTrafficTicketZPL(TrafficTicketViolatorData data, Bitmap sig, ZebraPrinter pPrinter)
	     throws ConnectionException
	   {
	     printer = pPrinter;
			  printer.sendCommand(generateHeader(data));
			  printSignatureZPL(sig);
			  printer.sendCommand(ZPLUtilities.decorateWithFormatPrefix("^XZ"));
	   }
	   
	   private static String addVariable(String varNumber, String varValue)
	   {
	     String temp = String.format("^FN%s^FD%s^FS", varNumber, varValue);
	     ZPLUtilities.decorateWithFormatPrefix(temp);
			  return temp;
	   }
	   
	   private static String generateHeader(TrafficTicketViolatorData data)
	   {
	     StringBuilder sb = new StringBuilder(trafficTicketFormatZPL());
	     if (data != null)
	     {
	       sb.append(addVariable("14", data.firstName));
				sb.append(addVariable("15", data.lastName));
				sb.append(addVariable("16", data.address));
				sb.append(addVariable("11", data.priors));
				sb.append(addVariable("12", data.plateNumber));
				sb.append(addVariable("13", data.violation));
	     }
	     return sb.toString();
	   }
	   
	   private static void printSignatureZPL(Bitmap sig)
	     throws ConnectionException
	   {
	     int xPos = 25;
	     int yPos = 402;
	     int width = 571;
	     int height = 100;
	     if (sig != null) {
	       printer.printImage(new ZebraImageAndroid(sig), xPos, yPos, width, height, true);
	     }
	   }
	   
	   public static void printTrafficTicketCPCL(TrafficTicketViolatorData data, Bitmap sig, ZebraPrinter pPrinter)
	     throws ConnectionException
	   {
	     printer = pPrinter;
			  printer.sendCommand(generateFormatCPCL(data));
	     if (sig != null) {
	       printSignatureCPCL(sig);
	     }
	     printer.sendCommand("PRINT");
	   }
	   
	   private static String generateFormatCPCL(TrafficTicketViolatorData data)
	   {
	       Object[] arrayOfObject = new Object[6];
				arrayOfObject[0] = data.violation;
				arrayOfObject[1] = data.plateNumber;
				arrayOfObject[2] = data.firstName;
				arrayOfObject[3] = data.lastName;
				arrayOfObject[4] = data.address;
				arrayOfObject[5] = data.priors;
				String temp = String.format("! 0 200 200 930 1\r\nPW 384\r\nTONE 0\r\nSPEED 2\r\nON-FEED IGNORE\r\nNO-PACE\r\nPOSTFEED 152\r\nJOURNAL\r\nBOX 11 6 376 797 8\r\nT 5 1 85 27 Traffic Ticket Demo\r\nT 5 1 75 89 %s\r\nT 5 0 35 522 Priors:\r\nT 5 0 35 421 Address:\r\nT 5 0 35 342 Last Name:\r\nT 5 0 35 257 First Name:\r\nT 5 0 35 175 Plate #:\r\nT 5 0 64 210 %s\r\nT 5 0 64 290 %s\r\nT 5 0 64 374 %s\r\nT 5 0 64 466 %s\r\nT 5 0 64 558 %s\r\nB PDF-417 54 639 XD 3 YD 4 C 1 S 0\r\nZebra Technologies makes printing from Smart Phones easy!\r\nENDPDF\r\nBOX 11 789 376 920 8", arrayOfObject);
	     
	 
	 
	     return temp;
	   }
	   
	   private static void printSignatureCPCL(Bitmap sig)
	     throws ConnectionException
	   {
	     int xPos = 26;
	     int yPos = 804;
	     int width = 340;
	     int height = 104;
	     
	     printer.printImage(new ZebraImageAndroid(sig), xPos, yPos, width, height, true);
	   }
	   
	   private static String trafficTicketFormatZPL()
	   {
	     return ZPLUtilities.decorateWithFormatPrefix("^XA\r\n^FO32,0^GFA,02048,02048,00016,:Z64:\r\neJzt1E9r1EAUAPCEKZ0elh3Fi4eYWfCuKT2Yw5BBEDz5HYoFPYltD0U03aymsB6kuXoo+kFUmrjQXEr7DWxqxD3Vje6hkY4ZJ6WZmVQQPAp9t19e3ps/zIxhXMT/FIjrURi0ZfaHg5arU/+Kccv5oJ0fmuOWbTTR/clGaaD5PaF7mivi0919zT6jr98qM58FzTRrb5WMGlp/wphnnjNQFu36Xaj8zWfeLFLusD6GBy13USTtW4x2MNJcYcvWHXRdovr3a7tq/L5NLWc+V/1tSlYXpsqoS1ZvTbelo03y4+aJcvqGLPWAWn9uu7nuqeU/NGaxMvHzQVf5mIh8R/mr5y+Fcx+kd4jwZUt6z/OujwCR3vW8BeGg8aZHF9KuK92h9Ipui+JwsuXKegvj56m1rPI4Sibu6ljuF07jsfP5bmMPjYrJ8k4o8ygpxisbpszDjD2+35G2YeE/WLQMmQclvZG5jX17nuHtRUfmbZchnhXS2OoD/j2TRjYVvePGAcJIHPeBqQxiYSCNLmXif+koeumIiwYbD6NXopbhxmEUNafj1KM0Df/mQ/Hhyx3O0zMXeb5fzzZsfJgf1AZnLlemXM8zRxy2mAfwzJXzU0y/RC/QjAvq+3L1Ca9ABgxgOGbtOcIZzEZhOOgltTdsXqLkECSAJ/X+xIgXODbDESiNyliffhTbSWMTJDAT9U/fZVAMl/BwNIyzyli7VyCxm7cTE8BE1K8dlVg4AwMwzHrC1xgVXoYzEBa1j6qAa+tfe8RbfnbCue7147ZpdM46xft2/j28iH+L3/3Pgac=:6881\r\n^FO15,10^GB591,390,8^FS\r\n^FT170,49^A0N,34,33^FH\\^FDTraffic Ticket Demo^FS\r\n^FT307,150^A0N,28,28^FH\\^FDCitation:^FS\r\n^FT173,94^A0N,28,28^FH\\^FDPriors:^FS\r\n^FT37,314^A0N,28,28^FH\\^FDAddress:^FS\r\n^FT312,230^A0N,28,28^FH\\^FDLast Name:^FS\r\n^FT37,230^A0N,28,28^FH\\^FDFirst Name:^FS\r\n^FT37,151^A0N,29,28^FH\\^FDPlate #:^FS\r\n^BY2,4^FT379,382^B7N,4,0,,,N^FH\\^FDZebra Technologies makes printing from Smart Phones easy!^FS\r\n^FT49,359^A0N,28,28^FH\\^FN16\"Address\"^FS\r\n^FT318,272^A0N,28,28^FH\\^FN15\"Last Name\"^FS\r\n^FT49,275^A0N,28,28^FH\\^FN14\"First Name\"^FS\r\n^FT318,187^A0N,28,28^FH\\^FN13\"Citation\"^FS\r\n^FT49,190^A0N,28,28^FH\\^FN12\"Plate\"^FS\r\n^FT260,94^A0N,28,28^FH\\^FN11\"Priors\"^FS\r\n^FO15,392^GB591,120,8^FS");
	   }
	 }
