 package com.zebra.android.zebrautilitiesmza.screens.trafficticket;
 
		 import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
		 //import android.view.View.OnClickListener;
		 import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

//import com.zebra.android.zebrautilitiesmza.R;
import ar.gov.mendoza.PrometeoMuni.R;

import com.zebra.android.zebrautilitiesmza.ZebraActivityBase;
import com.zebra.android.zebrautilitiesmza.util.UIHelper;
import com.zebra.android.zebrautilitiesmza.util.ZebraPrinterTask;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.device.ZebraIllegalArgumentException;

         import java.util.ArrayList;
import java.util.Random;
 
public class TrafficTicket
extends ZebraActivityBase
{
	   private static int indexOfViolator;
	   private static final String[] offenses ;
	   private static Bitmap sig;
	   private static boolean trafficDataShown;
	   private static ArrayList<TrafficTicketViolatorData> violatorArrayList ;
	   private TrafficTicketListAdapter adapter;
	   private ArrayList<String> arrayList;
	   private ImageView imageView;
	   private EditText licenseEditText;
	   private String licensePlate;
	   private ListView listView;
	   
	    static {
	        TrafficTicket.violatorArrayList = new ArrayList<TrafficTicketViolatorData>();
	        TrafficTicket.trafficDataShown = false;
	        TrafficTicket.sig = null;
	        offenses = new String[] { "None", "Expired Parking Meter", "Failure to obey stop light", "No City Sticker", "Speeding", "Expired License Plate", "No Safety Belt" };
	    }
	    
	    public TrafficTicket()
	    {
	    	  super("Traffic Ticket",R.layout.screens_traffic_ticket);// 2130903059
	 		  this.licensePlate = "";
	 		  this.arrayList = new ArrayList<String>();
	 		  createViolators();
	    }
	    
	    private void createViolators()
	    {
	      final TrafficTicketViolatorData ttvd = new TrafficTicketViolatorData("Jane", "Doe", "123 Spring Grove Dr.", "Warrant, detain", "", "");
 		  violatorArrayList.add(ttvd);
 
	     final TrafficTicketViolatorData ttvd2 = new TrafficTicketViolatorData("John", "Doe", "123 Spring Grove Dr.", "First Offense", "", "");
	      violatorArrayList.add(ttvd2);
	      
	      final TrafficTicketViolatorData ttvd3 = new TrafficTicketViolatorData("Matthew", "Smith", "50001 Blueberry Ave.", "Multiple Tickets", "", "");
	      violatorArrayList.add(ttvd3);

	      final TrafficTicketViolatorData  ttvd4 = new TrafficTicketViolatorData("Kate", "Johnson", "8652 Kostner Ave.", "Seize Vehicle", "", "");
	      violatorArrayList.add(ttvd4);

	      final TrafficTicketViolatorData ttvd5 = new TrafficTicketViolatorData("Johhny", "Appleseed", "52 Prairie Ln.", "First Offense", "", "");
	      violatorArrayList.add(ttvd5);
 
	      final TrafficTicketViolatorData ttvd6 = new TrafficTicketViolatorData("Jake", "Adams", "3429 Cornflower Dr.", "Seize Vehicle", "", "");
	      violatorArrayList.add(ttvd6);
	    }
	    
	    private void dismissKeyboard()
	    {
	      InputMethodManager imm = (InputMethodManager)getSystemService("input_method");
	      imm.hideSoftInputFromWindow(this.licenseEditText.getWindowToken(), 0);
	    }
	  	    
	    
	    private void setSpinnerVisibility(int visibility)
	    {
	      findViewById(R.id.traffic_ticket_picker).setVisibility(visibility);// 2131296333
	      findViewById(R.id.offense_text).setVisibility(visibility);//2131296332
	    }
	    
	   private void setViolatorList()
	   {
	     this.arrayList.clear();
	     this.arrayList.add(violatorArrayList.get(indexOfViolator).firstName);
	     this.arrayList.add(violatorArrayList.get(indexOfViolator).lastName);
	     this.arrayList.add(violatorArrayList.get(indexOfViolator).address);
	     this.arrayList.add(violatorArrayList.get(indexOfViolator).priors);
	     this.adapter = new TrafficTicketListAdapter(this, this.arrayList);
	     this.listView.setAdapter(this.adapter);
	   }

	   public boolean dispatchKeyEvent(KeyEvent event)
	   {
	     if (event.getKeyCode() == 4) 
	     {
	       sig = null;
	     }
	     else if (event.getKeyCode() == 66)
	     {
		     dismissKeyboard();
		     return true;
	     }
	     
	     return super.dispatchKeyEvent(event);

	     
	   }
	   
	   @Override 
	   protected void onCreate(Bundle savedInstanceState)
	   {
	     super.onCreate(savedInstanceState);
	     this.listView = findViewById(R.id.violator_list);//2131296335));
		 final Spinner spinner = findViewById(R.id.traffic_ticket_picker);//2131296333);
	     final Button printButton = findViewById(R.id.traffic_ticket_print_button);//2131296338);
	     															   //17367048				
	     ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, offenses);
	     adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//17367049
	     spinner.setAdapter(adapter);
		
	     this.licenseEditText = findViewById(R.id.LicensePlateInput);//2131296331));
		 this.licenseEditText.addTextChangedListener(new TextWatcher()
						    {// CONSTRUTOR new TextWatcher()
						      public void afterTextChanged(Editable s)
						      {
							        if (TrafficTicket.this.licenseEditText.getText().length() == 0)
							        {
								          printButton.setEnabled(false);
								          TrafficTicket.this.setSpinnerVisibility(4);
								          												//2131296334
								          TrafficTicket.this.findViewById(R.id.violator_linear_layout).setVisibility(8);
								          spinner.setSelection(0);
								          return;
							        }
							        else
							        {
								          TrafficTicket.this.setSpinnerVisibility(0);
								          int i = spinner.getSelectedItemPosition();
								          boolean printButtonShouldBeEnabled=false;
									      if (i > 0) 
									      {
									        printButtonShouldBeEnabled = true;
									      }
								          printButton.setEnabled(printButtonShouldBeEnabled);
							        }
							  }
							      
							  public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
							      
							  public void onTextChanged(CharSequence s, int start, int before, int count) {}
							      
						    }// END CONSTRUCTOR new TextWatcher()
				 			);
	     
	     spinner.setOnItemSelectedListener(new OnItemSelectedListener()
							     {// CONSTRUCTOR ITEM SELECTED LISTENER
							       public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3)
							       {
							         if (position != 0)
							         {
							           String temp = TrafficTicket.this.licenseEditText.getText().toString();
							           
							           LinearLayout ll = TrafficTicket.this.findViewById(R.id.violator_linear_layout);//2131296334);
							           if (temp.equals(""))
							           {
							             printButton.setEnabled(false);
							             ll.setVisibility(8);
							           }
							           else
							           {
							        	  printButton.setEnabled(true);
							        	  TrafficTicket.indexOfViolator = (new Random()).nextInt(TrafficTicket.violatorArrayList.size());
										  TrafficTicket.this.setViolatorList();
										  TrafficTicket.this.licensePlate = temp;
										  ll.setVisibility(0);
										  ll.setFocusable(false);
										  TrafficTicket.this.imageView.setImageBitmap(Bitmap.createBitmap(1, 1, Config.ARGB_8888));
										  InputMethodManager imm = (InputMethodManager)TrafficTicket.this.getSystemService("input_method");
										  imm.hideSoftInputFromWindow(TrafficTicket.this.licenseEditText.getWindowToken(), 0);
							           }
							           TrafficTicket.trafficDataShown = true; 
							           
							         }
							         else
							         {
								         LinearLayout ll = TrafficTicket.this.findViewById(R.id.violator_linear_layout);//2131296334);
								         ll.setFocusable(false);
								         printButton.setEnabled(false);
								         ll.setVisibility(8);							        	 
							         }

							       }
							       public void onNothingSelected(AdapterView<?> arg0) {}
							     }// END CONSTRUCTOR ITEM SELECTED  LISTENER
							     );
	     
	     if (trafficDataShown) 
	     {
	       setViolatorList();
	     }
	     
	     this.imageView = findViewById(R.id.signature_image);//2131296337
	     if (sig != null) 
	     {
	       this.imageView.setImageBitmap(sig);
	     }
	     this.imageView.setOnClickListener(new View.OnClickListener()
						     {
						       public void onClick(View v)
						       {
						         TrafficTicket.this.setRequestedOrientation(0);
						         LayoutInflater li = LayoutInflater.from(TrafficTicket.this);
						         View view = li.inflate(R.layout.signature_capture_dialog, null);//2130903062
						         
						         final TrafficTicketSignatureBox signatureBox = view.findViewById(R.id.signature_capture);//2131296342
						         Builder builder = new Builder(TrafficTicket.this);
						         builder.setTitle("Sign Here:");
						         builder.setView(view);
						         builder.setCancelable(false);
						         builder.setPositiveButton("OK", new OnClickListener()
											         {
											           public void onClick(DialogInterface dialog, int which)
											           {
											             TrafficTicket.sig = signatureBox.getBitmap();
											             TrafficTicket.this.imageView.setImageBitmap(TrafficTicket.sig);
											             TrafficTicket.this.setRequestedOrientation(2);
											           }
											         }
						           );
						         builder.create().show();
						       }
						     }
	    		 	);
	     printButton.setOnClickListener(new View.OnClickListener()
	     { 
	       public void onClick(View v)
	       {// inicio  onClick
	         new ZebraPrinterTask<Object, Object, Object>(TrafficTicket.this)
	         {
	           @Override
	           public Object doWork(final Object[] params)
	             throws ConnectionException, ZebraIllegalArgumentException
		           {
		             TrafficTicket.violatorArrayList.get(TrafficTicket.indexOfViolator).plateNumber = TrafficTicket.this.licensePlate;
					 TrafficTicket.violatorArrayList.get(TrafficTicket.indexOfViolator).violation = ((String)spinner.getSelectedItem());
		            // if (this.printer.getPrinterControlLanguage() == PrinterLanguage.CPCL) 
		             {
		               TrafficTicketHelper.printTrafficTicketCPCL(TrafficTicket.violatorArrayList.get(TrafficTicket.indexOfViolator), TrafficTicket.sig, this.printer);
		             }
		            // else
		             {
		             //  TrafficTicketHelper.printTrafficTicketZPL((TrafficTicketViolatorData)TrafficTicket.violatorArrayList.get(TrafficTicket.indexOfViolator), TrafficTicket.sig, this.printer);
		             }
		             return null;
		           }
	           @Override
	           public void handleError(final Exception e)
	           {
	             UIHelper.showErrorOnGuiThread(TrafficTicket.this, e.getLocalizedMessage(), null);
	           }
	         };
	       }// end onClick  
	     });
	   }//end create
	   
}
