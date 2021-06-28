package com.zebra.android.zebrautilitiesmza.screens.chooseprinter;
 
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
//import com.zebra.android.zebrautilitiesmza.R;
import ar.gov.mendoza.PrometeoMuni.R;

import com.zebra.android.zebrautilitiesmza.util.NetworkHelper;
import com.zebra.android.zebrautilitiesmza.util.UIHelper;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.discovery.DiscoveredPrinter;
import com.zebra.sdk.printer.discovery.DiscoveryException;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
 
public class ChoosePrinterController extends Activity implements Observer
{

	protected ProgressDialog progressDialog;
    ProgressDialog pleaseWaitDialog;
    
static final int RESULT_WIFI_RETURNED = 42;
private static ChoosePrinterBluetoothDiscoverer bluetoothDiscoverer;
private static boolean isDiscovering = false;
//private static ChoosePrinterNetworkDiscoverer networkDiscoverer;
private BaseAdapter discoveredPrintersAdapter;
private BaseAdapter knownPrintersAdapter;




				protected void onCreate(Bundle savedInstanceState)
				{
					super.onCreate(savedInstanceState);
					requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);//5
					//setTitle("Choose Printer");
					setTitle("Seleccione una Impresora");
					setContentView(R.layout.screens_choose_printer);//2130903054);
					setupKnownPrintersList();
					setupDiscoveredPrintersList();
					  if (!isDiscovering) 
					  {
						sequenceDiscoveryOperations(DiscoveryState.NOT_STARTED);
					  }
					  else
					  {
						  setupGuiForReentry();  
					  }

//					Button addPrinterButton = (Button)findViewById(R.id.add_printer_button);//2131296314
//					addPrinterButton.setBackgroundColor(0);
//				    addAddPrinterButton(addPrinterButton); 

				}

			private void setupForNewDiscovery()
			{
				pleaseWaitDialog = ProgressDialog.show(ChoosePrinterController.this,"Impresoras", "Buscando Impresoras ", true, false);
				
				setupDisco();
				if (NetworkHelper.isRunningInEmulator())
				{
					sequenceDiscoveryOperations(DiscoveryState.BT_STARTED);
					return;
				}
				RadioStateManager.manageState(this);
			}

			private void setupGuiForReentry()
			{
				  setProgressBarIndeterminateVisibility(true);
				  setupDiscoveryObservers();
				  redrawWithCurrentListHeight();
			}


			private void setupDisco()
			{
				  //networkDiscoverer = new ChoosePrinterNetworkDiscoverer();
				  bluetoothDiscoverer = new ChoosePrinterBluetoothDiscoverer();
				  setupDiscoveryObservers();
				  ChoosePrinterModel.getInstance().clearPrinters();
			}
   
			private void setupDiscoveryObservers()
			{
//				networkDiscoverer.deleteObservers();
//				networkDiscoverer.addObserver(this);
				bluetoothDiscoverer.deleteObservers();
				bluetoothDiscoverer.addObserver(this);
			}
   
			private void addAddPrinterButton(Button addPrinterButton)
			{
				addPrinterButton.setOnClickListener(new OnClickListener()
						{
							public void onClick(View v)
							{
							Intent intent = new Intent(ChoosePrinterController.this, ManualAddActivity.class);
							ChoosePrinterController.this.startActivityForResult(intent, 1);
							}
						});
			}
			  protected void onActivityResult(int requestCode, int resultCode, Intent data)
			  {
			    super.onActivityResult(requestCode, resultCode, data);
			    if (resultCode == 1) 
					  {
			     		 finish();
			    }
					 else if (requestCode == 42) 
					 {
						 sequenceDiscoveryOperations(DiscoveryState.BT_STARTED);	 
					 }
					
			  }
			  


			  
			  private void setupKnownPrintersList()
			  {
			    ListView knownPrintersList = findViewById(R.id.knownPrintersList);//2131296310);
			    knownPrintersList.setVisibility(View.GONE);
			    TextView knownPrinterLabel = findViewById(R.id.knownPrinterLabel); //2131296309);
			    /*knownPrinterLabel.setTextColor(-1);*/
				/*knownPrinterLabel.setBackgroundColor(-12303292);*/
				this.knownPrintersAdapter = new KnownPrinterListAdapter(this, ChoosePrinterModel.getInstance());
				knownPrintersList.setAdapter(this.knownPrintersAdapter);
			    LayoutParams params = knownPrintersList.getLayoutParams();
			    params.height = getNewListHeight(ChoosePrinterModel.getInstance().getKnownPrintersList());
			    knownPrintersList.setLayoutParams(params);
			    knownPrintersList.setOnItemClickListener(new OnItemClickListener()
				    {
					      public void onItemClick(AdapterView<?> parent, View view, int position, long id)
					      {
					        ChoosePrinterModel.getInstance().addToKnownPrinters(ChoosePrinterModel.getInstance().getKnownPrinter(position));
							ChoosePrinterController.this.finish();
					      }
				    });
			  }

			  private int getNewListHeight(List<DiscoveredPrinter> list)
			  {
			    if (list.size() == 0) {
			      return 0;
			    }
			    
			    ListView discoveredPrintersList = findViewById(R.id.discoveredPrintersList);//2131296312
			    int dividerHeight = discoveredPrintersList.getDividerHeight();
			    //R.layout.list_item_with_image_and_two_lines //2130903048
			    View tempCell = getLayoutInflater().inflate(R.layout.list_item_with_image_and_two_lines, null, false);
			    
			    tempCell.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
			    int cellHeight = dividerHeight + tempCell.getMeasuredHeight();
			    

			    return cellHeight * list.size() - dividerHeight;
			  }

			   
			   private void setupDiscoveredPrintersList()
			   {
			     ListView discoveredPrintersList = findViewById(R.id.discoveredPrintersList);//2131296312);
			     TextView discoLabel = findViewById(R.id.discoPrinterLabel);//2131296311);
			     discoLabel.setTextColor(-1);
				 discoLabel.setBackgroundColor(-12303292);
				 this.discoveredPrintersAdapter = new DiscoveredPrinterListAdapter(this, ChoosePrinterModel.getInstance());
				 discoveredPrintersList.setAdapter(this.discoveredPrintersAdapter);
				 discoveredPrintersList.setOnItemClickListener(new OnItemClickListener()
				     {
				       public void onItemClick(AdapterView<?> parent, View view, int position, long id)
				       {
				         ChoosePrinterModel.getInstance().addToKnownPrinters(ChoosePrinterModel.getInstance().getPrinter(position));
							  ChoosePrinterController.this.finish();
				       }
				     });
				 
				 
				 
			   }
			   
			   private void startBluetoothDiscovery()
			   {
			     if (NetworkHelper.isBluetoothRadioEnabled()) 
			 	  {
			       try
			       {
			         bluetoothDiscoverer.startDiscovery(); 
			       }
			       catch (DiscoveryException e)
			       {
			         UIHelper.showAlertOnGuiThread(this, e.getMessage(), "Error: Discovery", null);
			       }
			       catch (ConnectionException e)
			       {
			         UIHelper.showAlertOnGuiThread(this, e.getMessage(), "Error: Printer Connection", null);
			       }
			       catch (InterruptedException e)
			       {
			         UIHelper.showAlertOnGuiThread(this, e.getMessage(), "Error: Interrupted", null);
			       }
			     }
			     else
			     {
			    	 sequenceDiscoveryOperations(DiscoveryState.BT_FINISHED);
			     }
			   }
			   
			   private void startNetworkDiscovery()
			   {
				   /*
			     if (wifiRadioEnabled()) {
			       try
			       {
			         networkDiscoverer.startDiscovery(); 
			       }
			       catch (DiscoveryException e)
			       {
			         UIHelper.showAlertOnGuiThread(this, e.getMessage(), "Error: Discovery",(Runnable) null);
			       }
			     }
			     else*/
			     {
			    	 sequenceDiscoveryOperations(DiscoveryState.NETWORK_FINISHED);
			     }
			   }
			   
			   void tearDownDisco()
			   {
			     //networkDiscoverer = null;
				   if (pleaseWaitDialog!=null)
				   {	
					   pleaseWaitDialog.dismiss();
		   			   pleaseWaitDialog = null;
				   }
					  bluetoothDiscoverer = null;
			   }
			   
			   
			   private boolean wifiRadioEnabled()
			   {
			     if (NetworkHelper.isRunningInEmulator()) {
			       return true;
			     }
			     WifiManager wifiManager = (WifiManager)getSystemService("wifi");
			     return wifiManager.isWifiEnabled();
			   
			     //return NetworkHelper.isRunningInEmulator()?true:((WifiManager)this.getSystemService("wifi")).isWifiEnabled();
			   }
			   
			   public void sequenceDiscoveryOperations(DiscoveryState currentState)
			   {
			 		 DiscoveryState dis = DiscoveryState.values()[currentState.ordinal()];
			     switch (dis)//(null$SwitchMap$com$zebra$android$zebrautilities$screens$chooseprinter$DiscoveryState[currentState.ordinal()])
			     {
			     	       //BT_FINISHED("BT_FINISHED", 3),
			 			   //BT_STARTED("BT_STARTED", 1),
			 			   //NETWORK_FINISHED("NETWORK_FINISHED", 4),
			 			   //NETWORK_STARTED("NETWORK_STARTED", 2),
			 			   //NOT_STARTED("NOT_STARTED", 0),
			 			   //TEAR_DOWN_DISCO("TEAR_DOWN_DISCO", 5);
			 			   //NOT_STARTED, BT_STARTED, NETWORK_STARTED, BT_FINISHED, NETWORK_FINISHED, TEAR_DOWN_DISCO
			     case  NOT_STARTED://1: 
			       setupForNewDiscovery();
			 				//return;
			 			break;
			     case BT_STARTED ://2: 
			       setProgressBarIndeterminateVisibility(true);
			 			isDiscovering = true;
			 			startBluetoothDiscovery();
			 			//return;
			 			break;					
			     case NETWORK_STARTED://3: 
			       setProgressBarIndeterminateVisibility(false);
			 			isDiscovering = false;
			 			sequenceDiscoveryOperations(DiscoveryState.NETWORK_STARTED);
			 			break;
			 				//return;
			     case BT_FINISHED://4: 
			       setProgressBarIndeterminateVisibility(true);
			 			isDiscovering = true;
			 		    startNetworkDiscovery();
			 		    break;
			 			//	return;
			     case NETWORK_FINISHED://5: 
			       setProgressBarIndeterminateVisibility(false);
			 			isDiscovering = false;
			 			showNumberOfDevicesFound();
			 		    sequenceDiscoveryOperations(DiscoveryState.TEAR_DOWN_DISCO);
			 			//return;
			 		    break;
			 		  case TEAR_DOWN_DISCO://6:
			 			tearDownDisco();  
			 		    break;
			 		  default: 
			 					    
			     }
			     //tearDownDisco();
			   }
			   
			   public void update(Observable observable,final Object data)
			   {
			     runOnUiThread(new Runnable()
			     {
						public void run()
						{
						         if ((data instanceof Integer))
						         {
						           processDiscoveryStepFinishedEvent(((Integer)data).intValue());
						         }
						         else if ((data instanceof String))
						         {
						           processDiscoveryErrorEvent((String)data);
						         }
								 else
								 {	 processPrinterFoundEvent((DiscoveredPrinter)data);
								 }
						}
			      
						private void processDiscoveryErrorEvent(String errorMessage)
						{
							Toast.makeText(ChoosePrinterController.this, " Discovery Error: " + errorMessage, Toast.LENGTH_SHORT).show();
						}

						private void processPrinterFoundEvent(DiscoveredPrinter printer)
						{
						  ChoosePrinterModel.getInstance().addDiscoveredPrinter(printer);
						  ChoosePrinterController.this.discoveredPrintersAdapter.notifyDataSetChanged();
						  ChoosePrinterController.this.redrawWithCurrentListHeight();
						}

						private void processDiscoveryStepFinishedEvent(int returnCode)
						{
						  DiscoveryState nextDiscoState;
						  if (returnCode == 1) 
						  {
							 nextDiscoState = DiscoveryState.BT_FINISHED;
						  }
						  else
						  {
							  nextDiscoState = DiscoveryState.NETWORK_FINISHED;
						  }
			  			 ChoosePrinterController.this.sequenceDiscoveryOperations(nextDiscoState); 
						}
					});
			    }
			   
			   
			   private void redrawWithCurrentListHeight()
			   {
			     ListView discoveredPrintersList = findViewById(R.id.discoveredPrintersList);//2131296312);
			     LayoutParams params = discoveredPrintersList.getLayoutParams();
			     params.height = getNewListHeight(ChoosePrinterModel.getInstance().getPrinterDisplayList());
			     discoveredPrintersList.setLayoutParams(params);
			   }
			   
			   private void showNumberOfDevicesFound()
			   {
			     runOnUiThread(new Runnable()
			     {
			       public void run()
			       {
			         if (!ChoosePrinterController.this.isFinishing()) {
			           Toast.makeText(ChoosePrinterController.this, " Se ha encontrado " + ChoosePrinterModel.getInstance().getPrinterDisplayList().size() + " dispositivos", Toast.LENGTH_SHORT).show();
			         }
			       }
			     });
			   }
			   
			 }