package com.zebra.android.zebrautilitiesmza.screens.chooseprinter;
 
import com.zebra.android.zebrautilitiesmza.util.ZebraUtilitiesOptions;
import com.zebra.sdk.printer.discovery.DiscoveredPrinter;
import com.zebra.sdk.printer.discovery.DiscoveredPrinterBluetooth;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
 
public class ChoosePrinterModel
{
public static final int BLUETOOTH_DISCO_FINISHED = 1;
public static final int NETWORK_DISCO_FINISHED = 2;
private static ChoosePrinterModel instance = null;
private List<DiscoveredPrinter> discoPrintersForGui;
private List<DiscoveredPrinter> knownPrintersForGui;

private ChoosePrinterModel()
{
	this.discoPrintersForGui = new ArrayList<DiscoveredPrinter>();
	this.knownPrintersForGui = new ArrayList<DiscoveredPrinter>();
	populateKnownPrintersFromStorage();
}

				private void populateKnownPrintersFromStorage()
				{
				  this.knownPrintersForGui.clear();
				  List<DiscoveredPrinter> knownPrintersFromStorage = ZebraUtilitiesOptions.getKnownPrinters();
				  for (DiscoveredPrinter p : knownPrintersFromStorage) 
				  {
					addKnownPrinterToGuiAtEnd(p);
				  }
				  // otra forma de iterar
			      /*
			      Iterator ite = ZebraUtilitiesOptions.getKnownPrinters().iterator();
			      while(ite.hasNext()) 
			      {
			    	  this.addKnownPrinterToGuiAtEnd((DiscoveredPrinter)ite.next());
			      }
			      */

				}

public void clearPrinters()
{
	this.discoPrintersForGui.clear();
	populateKnownPrintersFromStorage();
}

   
public static ChoosePrinterModel getInstance()
{
	if (instance == null) 	
	{
		instance = new ChoosePrinterModel();
	}
	return instance;
}
   
public List<DiscoveredPrinter> getPrinterDisplayList()
{
	return this.discoPrintersForGui;
}
   
public List<DiscoveredPrinter> getKnownPrintersList()
{
	return this.knownPrintersForGui;
}
   
public void addDiscoveredPrinter(DiscoveredPrinter printer)
{
	this.discoPrintersForGui.add(printer);
}


public DiscoveredPrinter getPrinter(int position)
{
	return this.discoPrintersForGui.get(position);
}


public void addToKnownPrinters(DiscoveredPrinter printer)
{
	ZebraUtilitiesOptions.setSelectedPrinter(printer);
	addKnownPrinterToGuiAtFront(printer);
	List<DiscoveredPrinter> knownPrintersForStorage = new ArrayList<DiscoveredPrinter>();
				  
	for (DiscoveredPrinter k : this.knownPrintersForGui) 
	{
	       knownPrintersForStorage.add(k);
	}
	ZebraUtilitiesOptions.setKnownPrinters(knownPrintersForStorage);
}

public DiscoveredPrinter checkDiscoListForManuallyAddedBluetoothPrinter(DiscoveredPrinterBluetooth printer)
{
	DiscoveredPrinter retVal = printer;
	for (DiscoveredPrinter aPrinter : this.discoPrintersForGui) 
	{
		if ((aPrinter instanceof DiscoveredPrinterBluetooth))
		{
			DiscoveredPrinterBluetooth printerWhichMatchesOurManuallyAddedPrinter = (DiscoveredPrinterBluetooth)aPrinter;
		
			String friendlyNameOfDiscoveredDevice = printerWhichMatchesOurManuallyAddedPrinter.friendlyName;
			if ((friendlyNameOfDiscoveredDevice != null) && (friendlyNameOfDiscoveredDevice.toLowerCase().equals(printer.friendlyName.toLowerCase()))) 
			{
			   retVal = printerWhichMatchesOurManuallyAddedPrinter;
			}
		}
	}
    return retVal;
}
   
	 private void addKnownPrinterToGui(int index, DiscoveredPrinter printer)
	 {
			  int indexOfPrinter = findPrinter(printer);
			  boolean inList = false;
         inList = indexOfPrinter >= 0;

			 if(inList) 
			 {
			    this.knownPrintersForGui.remove(indexOfPrinter);
			 } 
			 else if(this.knownPrintersForGui.size() >= 3) 
			 {
			    int elementToDelete = -1 + this.knownPrintersForGui.size();
			    this.knownPrintersForGui.remove(elementToDelete );
			 }

		this.knownPrintersForGui.add(index, printer);
	 }
  
			 private int findPrinter(DiscoveredPrinter printer)
				{
				 DiscoveredPrinter p;
				 
				 for(int ix = 0; ix < this.knownPrintersForGui.size(); ++ix) 
				 {
					p = this.knownPrintersForGui.get(ix);
        			if(printer.address.equals(p.address)) 
        			{
        				return ix;
        			}
				 }
				 return -1;
				}


			 	private void addKnownPrinterToGuiAtFront(DiscoveredPrinter printer)
				{
					addKnownPrinterToGui(0, printer);
				}

				private void addKnownPrinterToGuiAtEnd(DiscoveredPrinter printer)
				{
					addKnownPrinterToGui(this.knownPrintersForGui.size(), printer);
				}
				   
				public DiscoveredPrinter getKnownPrinter(int positionInList)
				{
					return this.knownPrintersForGui.get(positionInList);
				}
}



