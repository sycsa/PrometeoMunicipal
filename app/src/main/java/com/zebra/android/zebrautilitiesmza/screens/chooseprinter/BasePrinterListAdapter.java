 package com.zebra.android.zebrautilitiesmza.screens.chooseprinter;
 
 
		import android.graphics.Bitmap;
 import android.graphics.Typeface;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.BaseAdapter;
 import android.widget.ImageView;
 import android.widget.TextView;

 //import com.zebra.android.zebrautilitiesmza.R;
 import ar.gov.mendoza.PrometeoMuni.R;
 import com.zebra.sdk.printer.discovery.DiscoveredPrinter;
 import com.zebra.sdk.printer.discovery.DiscoveredPrinterBluetooth;

        import java.util.List;

 public abstract class BasePrinterListAdapter extends BaseAdapter
		{
   protected Bitmap bluetoothIcon;
   protected List<DiscoveredPrinter> discoveredPinters;
   protected LayoutInflater mInflater;
   protected Bitmap networkIcon;
   
   public int getCount()
   {
     return this.discoveredPinters.size();
   }
   
   public Object getItem(int position)
   {
     return Integer.valueOf(position);
   }
   
   public long getItemId(int position)
   {
     return (long)position;
   }

	  public View getView(int position, View convertView, ViewGroup parent)
	  {
		//2130903048
		  View retVal = convertView;
		if(retVal==null) 
			retVal = this.mInflater.inflate(R.layout.list_item_with_image_and_two_lines ,parent,false);//(ViewGroup) null
		
//		TextView firstLine = (TextView)retVal.findViewById(2131296292);
//		firstLine.setTypeface(Typeface.defaultFromStyle(1));
//	    TextView secondLine = (TextView)retVal.findViewById(2131296293);
//	    ImageView icon = (ImageView)retVal.findViewById(2131296291);
		TextView firstLine = retVal.findViewById(R.id.list_item_text_1);//2131296292
		firstLine.setTypeface(Typeface.defaultFromStyle(1));
	    TextView secondLine = retVal.findViewById(R.id.list_item_text_2);//2131296293
	    ImageView icon = retVal.findViewById(R.id.list_item_image);//2131296291

	    DiscoveredPrinter printer = this.discoveredPinters.get(position);
	    String friendlyField="";
	    Bitmap localBitmap =null;
//	    if(printer instanceof DiscoveredPrinterNetwork) 
//	    {
//	         friendlyField = (String)((DiscoveredPrinterNetwork)printer).getDiscoveryDataMap().get("DNS_NAME");
//	         localBitmap = this.networkIcon;
//	    } 
//	    else
	    if (printer instanceof DiscoveredPrinterBluetooth)
	    {
	         friendlyField = ((DiscoveredPrinterBluetooth)printer).friendlyName;
	         localBitmap = this.bluetoothIcon;
	    }
		  firstLine.setText(printer.address);
		  secondLine.setText(friendlyField);
		  icon.setImageBitmap(localBitmap);
		  return retVal;
	  }
}
