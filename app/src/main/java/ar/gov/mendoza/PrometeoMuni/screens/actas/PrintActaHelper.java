package ar.gov.mendoza.PrometeoMuni.screens.actas;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import ar.gov.mendoza.PrometeoMuni.core.domain.ActaConstatacion;
import ar.gov.mendoza.PrometeoMuni.core.domain.TipoVehiculo;
import ar.gov.mendoza.PrometeoMuni.core.util.Tools;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalStateApp;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalVar;
import ar.gov.mendoza.PrometeoMuni.utils.StringBuilderForPrint;
import ar.gov.mendoza.PrometeoMuni.utils.Utilities;

import com.zebra.android.zebrautilitiesmza.screens.trafficticket.TrafficTicketViolatorData;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.graphics.internal.ZebraImageAndroid;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.SGD;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.util.internal.ZPLUtilities;


public class PrintActaHelper
{

	   private static final int DEFAULT_PRINT_WIDTH = 832;
	   private Connection Connection;
	   private ZebraPrinter printer;

	   public PrintActaHelper(Connection Connection, ZebraPrinter printer)
	   {
		     this.Connection = Connection;
		     this.printer = printer;
		   }
	   
	  
	
	 
	   public void printTestigo()
	    throws ConnectionException
	   {
		   printer.sendCommand(generateTestigoFormatCPCL());
		   printer.sendCommand("PRINT");
		   
	   }
	   private static String generateTestigoFormatCPCL()
	   {
           StringBuilderForPrint dataToPrint = new StringBuilderForPrint();
           dataToPrint.append("! 0 200 200 10 1")
           			  .append("IN-DOTS")
           			  .append("PW 800")
           			  .append("LMARGIN 20")		//
           			  .append("L 0 5 800 5 1");
           
	       String temp = dataToPrint.toString();//String.format("",arrayOfObject);  
	       return temp; 
	   }
	
	   public void printTestImpresionCPCL(Connection pConnection,ZebraPrinter pPrinter)
	   throws ConnectionException
	   {
		   printer = pPrinter;
		   Connection = pConnection;
 		   
	
	       
           StringBuilderForPrint dataToPrint = new StringBuilderForPrint();
           dataToPrint.append("! 0 200 200 80 1")
           			  .append("IN-DOTS")
           			  .append("PW 800")
           			  .append("LMARGIN 20")		//
           			  .append("T 7 1 20 0 PRUEBA Á á Ñ ñ Ü ú ü í ó  é Ó Í Ú")
           			  .append("PRINT"); 
	       
	       String data = dataToPrint.toString();//strb.toString();
	//       Log.e("Charset",Charset.defaultCharset().toString());
	       //   UTF-8  US-ASCII ISO-8859-1 ISO-8859-2
//	       Charset charset=Charset.forName("ISO-8859-2");
	       
	       try
	       {
	    	   
	    	   byte[] dataBytes = 	convertExtendedAscii(data);   
	    	   Connection.write(dataBytes);
	       } 
	       catch(Exception ex)
	       {
	    	   String ms = ex.getMessage();
	    	   ms="";
	       }
		   
	   }
	   
	   /**
	    * Converts a text string with extended ASCII characters
	    * into a Java-friendly byte array.
	    * @param input
	    * @return
	    */
	   public static byte[] convertExtendedAscii(String input)
	   {
	           int length = input.length();
	           byte[] retVal = new byte[length];
	          
	           for(int i=0; i<length; i++)
	           {
	                     char c = input.charAt(i);
	                    
	                     if (c < 127)
	                     {
	                             retVal[i] = (byte)c;
	                     }
	                     else
	                     {
	                             retVal[i] = (byte)(c - 256);
	                     }
	           }
	          
	           return retVal;
	   }
	 
	   public void sendCommand(String data)
	   {
		   
		   try
	       {
	    	   
	    	   byte[] dataBytes = 	convertExtendedAscii(data);   
	    	   Connection.write(dataBytes);
	       } 
	       catch(Exception ex)
	       {
	    	   String ms = ex.getMessage();
	    	   ms="";
	       }
		   
		   
	   }
	   

		public static Bitmap getBitmapFromAsset(String filePath) {//Context context,
			Context context = GlobalStateApp.getInstance().getApplicationContext(); 
		    AssetManager assetManager = context.getAssets();
		
		    java.io.InputStream istr;
		    Bitmap bitmap = null;
		    try {
		        istr = assetManager.open(filePath);
		        bitmap = BitmapFactory.decodeStream(istr);
		    } catch (IOException e) {
		        // handle exception
		    }
		
		    return bitmap;
		}
	   
		//IMPRESION DEL ACTA MENDOZA 2019
	   public  void printCreateActaCPCL(ActaConstatacion data,boolean ImpresionCompleta, boolean OmitirCodigoBarra)throws ConnectionException {
		   
		 /* Impresion de Escudo */  
		 
		  Bitmap bitmap;
		  
    	 try{
    		 Thread.sleep(2000,0);
    	 } catch(InterruptedException ex){
    	 }
    	 
	     /* 01 - Mendoza */ 
    	 
    	 this.sendCommand(generateHeaderProvincia(data.getNumeroActa()));
    	 try{ 
	    	 bitmap = getBitmapFromAsset("sycsa.jpg");//ImageHelper.getBitmap(sUri[0]);
	    	 if (bitmap != null) {
	    		 printEscudoCPCL(bitmap);
			 }
	     }
	     catch(Exception ex){
	    	 String exs = ex.getMessage();
	     }
    	 this.sendCommand("PRINT\r\n");
    	 
    	 /* 02 - Mendoza  (Muestra la Fecha del Acta + Si retiene la Licencia Vehiculo Conduccion Peligrosa Secuestra Vehiculo ) */
    	 
    	 this.sendCommand(generateActaParteFechaFormatCPCL(data));
    	 this.sendCommand("PRINT\r\n");
    	 
	     /* 03 - Mendoza Imprime el Tipo de Vehiculo  Marca Color Model ConduccionPeligrosa etc */	 
    	 this.sendCommand(generateActaParteVehiculoFormatCPCL(data));
    	 this.sendCommand("PRINT\r\n");
    	 
    	 
    	 this.sendCommand(generateActaParteInfraccionFormatCPCL(data));
    	 this.sendCommand("PRINT\r\n");
    	 this.sendCommand(generateActaParteObservacionesInfraccionFormatCPCL(data));
    	 
    	 //this.sendCommand("PRINT\r\n");
    	 //MENDOZA
    	 this.sendCommand(generateActaParteInfractorFormatCPCL(data));
    	 this.sendCommand("PRINT\r\n");

    	 this.sendCommand(generateActaParteTestigoFormatCPCL(data));
	 	 this.sendCommand("PRINT\r\n");
	 	 this.sendCommand(generateActaParteManifestacionTestigoFormatCPCL(data));
	 	 //this.sendCommand("PRINT\r\n");
	 	 
	 	 this.sendCommand(generateActaParteEspacioParaFirmaTestigoFormatCPCL(data));
	 	this.sendCommand("PRINT\r\n");
	 	 this.sendCommand(generateActaParteFirmaAutoridadInfractor(data, ImpresionCompleta));
    	 this.sendCommand("PRINT\r\n");
    	 if(ImpresionCompleta==true)
    	 {
			 if (OmitirCodigoBarra == true) {
				 this.sendCommand(generateActaParteSinCodigoBarraCPCL(data));
				 //this.sendCommand("PRINT\r\n");
			 } else {
				 this.sendCommand(generateActaParteLugarPagoFormatCPCL(data));
				 //this.sendCommand("PRINT\r\n");
				 this.sendCommand(generateActaParteCuponesFormatCPCL(data));
				 this.sendCommand("PRINT\r\n");

			 }
		 }
	   }

	   private static String generateActaAndroidParte1FormatCPCL(ActaConstatacion data)
	   {
	       Object[] arrayOfObject = new Object[6];
	       
	       ActaConstatacion acta = data;
	       //alto con 2 imagenes 150 milimetros  8dots = 1milimetro    8 * 150 = 
	     //Comienzo impresion
           StringBuilderForPrint dataToPrint = new StringBuilderForPrint();
           dataToPrint.append("! 0 200 200 1400 1")
           			  .append("IN-DOTS")
           			  .append("PW 800")
           			  .append("LMARGIN 20")		//
           			  .append("T 7 1 20 0 PROVINCIA  DE  ")
           			  .append("T 7 1 20 33 Ministerio de Seguridad")
           			  .append("T 7 0 533 57 " + acta.getNumeroActa())
           			  .append("T 7 0 20 70 Acta de Infracción y Emplazamiento-Regimen de Transito y Seg. Vial Ley 4488/98")
           			  .append("T 7 0 20 90 DTOS 146/11 - 877/12 - 2472/12 Resol. 351/11")
           			  //.append("B I2OF5 1 1 50 500 0 " + acta.getNumeroActa().toString())
           			  .append("B 39 1 0 50 500 0 " + acta.getNumeroActa() + "")
           			  .append("T 7 0 20 120 Ruta: " + acta.getTipoRuta() + " Nro.Ruta: " + acta.getNumeroRuta() + " Km: " + acta.getKm() + "  Fecha: " + Tools.DateValueOf(acta.getFechaHoraLabrado(),"dd/MM/yyyy HH:mm"))
           			  .append("L 0 150 800 150 1");
            //Licencia
           String strUnicaProv = acta.getLicenciaUnicaProvincial() == "S" ? "Si" : "No";
           String strRetenida = acta.getLicenciaRetendia() == "S" ? "Si" : "No";
          
           dataToPrint.append("T 7 0 20 160 LICENCIA DE CONDUCIR"); 
              dataToPrint.append("L 0 150 720 150 1")
           			  .append("T 7 0 20 730 DOCUMENTO DEL CONDUCTOR");
              //Dominio
           String strMarca = acta.getMarca();//acta.Id_Marca.Equals(-1) || acta.Id_Marca.Equals(100) ? acta.MarcaNueva : marca.N_Marca;
           String strColor = acta.getColor();//acta.Id_color.Equals(-1) ? acta.ColorNuevo : color.N_color;
           
           dataToPrint.append("L 0 1200 800 1200 1");
           dataToPrint.append("T 7 0 20 1210 Dominio: " + Tools.formatearDerecha(acta.getDominio(), 15, ' '));
           dataToPrint.append("T 7 0 250 1210 Marca: " + Tools.formatearDerecha(strMarca, 15, ' '));
           dataToPrint.append("L 0 1240 800 1240 1");

           //Infracciones

           //AREA INFRACCIONES
           dataToPrint.append("T 7 0 20 1250 Infracciones: ");//.append("\r\n"); 455
           if (acta.getCodigoInfraccion1()!=null && !acta.getCodigoInfraccion1().equals(""))
           {
               /*if(objInf1.ImprimirPuntos.Equals("S"))
                   dataToPrint.append("T 7 0 20 480 Cod:" + acta.CodigoInfraccion1 + "(Pts:" + objInf1.Puntos.toString() + ")" + this.formatearDerecha(objInf1.DescripcionCorta, 100, ' ').Substring(0, 49)).append("\r\n");
                else*/																								//objInf1.DescripcionCorta		
        	     String sDescripcion =  Tools.formatearDerecha(acta.getDescripcionInfraccion1(), 100, '.');
        	     String sDescripcionParte1 = sDescripcion.substring(0, 49);
        	     String sDescripcionParte2 = sDescripcion.substring(49);
                 dataToPrint.append("T 7 0 20 1275 Cod:" + acta.getCodigoInfraccion1() + " " + sDescripcionParte1);
                 														//objInf1.DescripcionCorta
               dataToPrint.append("T 7 0 20 1300 " + sDescripcionParte2);

           }
           if (acta.getCodigoInfraccion2()!=null && !acta.getCodigoInfraccion2().equals(""))
           {
              /*if (objInf2.ImprimirPuntos.Equals("S"))
                   dataToPrint.append("T 7 0 20 530 Cod:" + acta.CodigoInfraccion2 + "(Pts:" + objInf2.Puntos.toString() + ")" + this.formatearDerecha(objInf2.DescripcionCorta, 100, ' ').Substring(0, 49)).append("\r\n");
               else*/																								//objInf2.DescripcionCorta
        	   String sDescripcion =  Tools.formatearDerecha(acta.getDescripcionInfraccion2(), 100, '.');
      	     	String sDescripcionParte1 = sDescripcion.substring(0, 49);
      	     	String sDescripcionParte2 = sDescripcion.substring(49);
        	   dataToPrint.append("T 7 0 20 1325 Cod:" + acta.getCodigoInfraccion2() + " " + sDescripcionParte1);
                   														//objInf2.DescripcionCorta								//47
               dataToPrint.append("T 7 0 20 1350 " + sDescripcionParte2);
           }

           dataToPrint.append("T 7 0 20 1375 Observaciones: ");
          // dataToPrint.append("PRINT\r\n");
           
	       
				//String temp = String.format("! 0 200 200 930 1\r\nPW 384\r\nTONE 0\r\nSPEED 2\r\nON-FEED IGNORE\r\nNO-PACE\r\nPOSTFEED 152\r\nJOURNAL\r\nBOX 11 6 376 797 8\r\nT 5 1 85 27 Traffic Ticket Demo\r\nT 5 1 75 89 %s\r\nT 5 0 35 522 Priors:\r\nT 5 0 35 421 Address:\r\nT 5 0 35 342 Last Name:\r\nT 5 0 35 257 First Name:\r\nT 5 0 35 175 Plate #:\r\nT 5 0 64 210 %s\r\nT 5 0 64 290 %s\r\nT 5 0 64 374 %s\r\nT 5 0 64 466 %s\r\nT 5 0 64 558 %s\r\nB PDF-417 54 639 XD 3 YD 4 C 1 S 0\r\nZebra Technologies makes printing from Smart Phones easy!\r\nENDPDF\r\nBOX 11 789 376 920 8", arrayOfObject);
	       String temp = dataToPrint.toString();//String.format("",arrayOfObject);  
	     return temp;
	   }
	   
	   
	   private  void printSignatureCPCL(Bitmap sig)
			     throws ConnectionException
			   {
				   int xPos = 26;
				     int yPos = 804;
				     int width = 340;
				     int height = 104;
				     
				     printer.printImage(new ZebraImageAndroid(sig), xPos, yPos, width, height, true);
				   /*
			     int xPos = 5;//26;
			     int yPos = 5;//804;
			     int width = 100;//340;
			     int height = 100;//104;
			     */
			     /*int printWidth = getPrintWidth(printer);
			     //Bitmap stripesImage = BitmapFactory.decodeResource(ZebraUtilitiesApplication.ZEBRA_UTIL_APP_CONTEXT.getResources(), 2130837544);
			     int desiredImageWidth = printWidth * 40 / 100;
			     double imageScalingFactor = desiredImageWidth / sig.getWidth();
			     int desiredImageHeight = (int)(imageScalingFactor * sig.getHeight());
			     
			     printer.printImage(new ZebraImageAndroid(sig), printWidth * 5 / 100, printWidth * 10 / 100, desiredImageWidth, desiredImageHeight, true);
			     */
			     //printer.printImage(new ZebraImageAndroid(sig), xPos, yPos, width, height, false);
			     
			   }
	   
	   private static String generateFormatCPCL0()
	   {
	       Object[] arrayOfObject = new Object[6];
				arrayOfObject[0] = "data.violation";
				arrayOfObject[1] = "data.plateNumber";
				arrayOfObject[2] = "data.firstName";
				arrayOfObject[3] = "data.lastName";
				arrayOfObject[4] = "data.address";
				arrayOfObject[5] = "data.priors";
				StringBuilderForPrint dataToPrint = new StringBuilderForPrint();
				dataToPrint.append("! 0 200 200 930 1")
							.append("PW 384") //page width
							.append("TONE 0") // el tono mas lightet  -99  a darkest 200   desde lo mas claro a lo mas oscuro
							.append("SPEED 2") //velocidad de 0 a 5  0 slowest(mas baja velocidad) a 5 
							.append("ON-FEED IGNORE") // es para determinar que debe hacer la impresora cuando se prsione la tecla feed de la impresora  IGNORE FEED  O REPRINT
							.append("NO-PACE") // The printer prints a label, waits for the label to be removed before printing the next label  NO-PACE cancela la espera para retirar los labels
							.append("POSTFEED 152") //instructs the printer to advance the media a specified amount after printing 152 dots  8 dots = 1milimetro
							.append("JOURNAL")// aparentemente es para no checkear la presencia de papel
							.append("BOX 11 6 376 797 8")
							.append("T 5 1 85 27 Traffic Ticket Demo")
							.append("T 5 1 75 89 %s")
							.append("T 5 0 35 522 Priors:")
							.append("T 5 0 35 421 Address:")
							.append("T 5 0 35 342 Last Name:")
							.append("T 5 0 35 257 First Name:")
							.append("T 5 0 35 175 Plate #:")
							.append("T 5 0 64 210 %s")
							.append("T 5 0 64 290 %s")
							.append("T 5 0 64 374 %s")
							.append("T 5 0 64 466 %s")
							.append("T 5 0 64 558 %s")
							.append("B PDF-417 54 639 XD 3 YD 4 C 1 S 0")
							.append("Zebra Technologies makes printing from Smart Phones easy!")
							.append("ENDPDF")
							.append("BOX 11 789 376 920 8");
				String sFormat = dataToPrint.toString();			
				//String temp = String.format("! 0 200 200 930 1\r\nPW 384\r\nTONE 0\r\nSPEED 2\r\nON-FEED IGNORE\r\nNO-PACE\r\nPOSTFEED 152\r\nJOURNAL\r\nBOX 11 6 376 797 8\r\nT 5 1 85 27 Traffic Ticket Demo\r\nT 5 1 75 89 %s\r\nT 5 0 35 522 Priors:\r\nT 5 0 35 421 Address:\r\nT 5 0 35 342 Last Name:\r\nT 5 0 35 257 First Name:\r\nT 5 0 35 175 Plate #:\r\nT 5 0 64 210 %s\r\nT 5 0 64 290 %s\r\nT 5 0 64 374 %s\r\nT 5 0 64 466 %s\r\nT 5 0 64 558 %s\r\nB PDF-417 54 639 XD 3 YD 4 C 1 S 0\r\nZebra Technologies makes printing from Smart Phones easy!\r\nENDPDF\r\nBOX 11 789 376 920 8", arrayOfObject);
				String temp = String.format(sFormat, arrayOfObject);
	     
	 
	 
	     return temp;
	   }
	   private static String generateActaParte5FormatCPCL(ActaConstatacion data)
	   {
		   Object[] arrayOfObject = new Object[6];
	       
	       ActaConstatacion acta = data;
	       StringBuilder dataToPrint = new StringBuilder();
	       
           dataToPrint = new StringBuilder();

           //  fin obtener el monto del total de la infraccion sin dto //
           LabelZebra label = new LabelZebra(630);

           String monto = Tools.DecimalFormat(acta.getMontoCupon() );//.toString("0.00")
           String monto2Do  = Tools.DecimalFormat(acta.getMonto2DoCupon(),"0.00");
           
                      
           
           dataToPrint.append("! 0 200 200 "+ label.LargoPagina + " 1").append("\r\n");
           dataToPrint.append("IN-DOTS").append("\r\n");
           dataToPrint.append("PW 800").append("\r\n");
           dataToPrint.append("LMARGIN 20").append("\r\n");
           dataToPrint.append("T 7 0 0 0 " + Tools.formatearIzquierda("-", 65, '-')).append("\r\n");
           
           dataToPrint.append("T 7 1 100 " + label.AddY(25) + " CUPÓN DE PAGO PARA EL ENTE RECAUDADOR").append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(50) + "  Referencia Nro.Acta: " + acta.getNumeroActa()).append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + "  N.Doc. " + acta.getNumeroDocumento()).append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + "  1er. Vto. " + Tools.DateValueOf(acta.getFechaVencimientoCupon(), "dd/MM/yyyy")).append("\r\n");

           //dataToPrint.append("T 7 0 20 " + label.AddY(25) + " " + Tools.formatearIzquierda("-", 100, '-')).append("\r\n");
           dataToPrint.append("T 7 1 20 " + label.AddY(25) + " IMPORTE A PAGAR                 $ ").append("\r\n");
           dataToPrint.append("T 7 1 650 " + label.PosicionY + " " + Tools.formatearIzquierda(monto, 12, ' ')).append("\r\n");

           //dataToPrint.append("B I2OF5 1 1 50 75 " + label.AddY(48) + " " + acta.getCodigoBarra()).append("\r\n");
           dataToPrint.append("B 39 1 0 80 20 " + label.AddY(48) + " " + acta.getCodigoBarra()).append("\r\n");
           dataToPrint.append("T 7 0 155 " + label.AddY(90) + " *" + acta.getCodigoBarra()).append("*\r\n");

           
           // imprimir Linea
           dataToPrint.append("T 7 0 20 " + label.AddY(45) + " " + Tools.formatearIzquierda("-", 100, '-')).append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + "   2do. Vto. " + Tools.DateValueOf(acta.getFechaVencimiento2DoCupon(), "dd/MM/yyyy")).append("\r\n");

          // dataToPrint.append("T 7 0 200 " + label.AddY(25) + " " + Tools.formatearIzquierda("-", 50, '-')).append("\r\n");
           dataToPrint.append("T 7 1 20 " + label.AddY(25) + " IMPORTE A PAGAR                 $ ").append("\r\n");
           dataToPrint.append("T 7 1 650 " + label.PosicionY + " " + Tools.formatearIzquierda(monto2Do, 12, ' ')).append("\r\n");

           //dataToPrint.append("B I2OF5 1 1 50 75 " + label.AddY(45) + " " + acta.getCodigoBarra2Do()).append("\r\n");
           dataToPrint.append("B 39 1 0 80 20 " + label.AddY(45) + " " + acta.getCodigoBarra2Do()).append("\r\n");
           dataToPrint.append("T 7 0 155 " + label.AddY(90) + " *" + acta.getCodigoBarra2Do()).append("*\r\n");


           
           dataToPrint.append("L 0 560 800 560 1").append("\r\n");
	       
	       return dataToPrint.toString();
	   }
	   private static String generateActaParte4FormatCPCL(ActaConstatacion data)
	   {
		   Object[] arrayOfObject = new Object[6];
	       
	       ActaConstatacion acta = data;
	       StringBuilder dataToPrint = new StringBuilder();
	   
		 //p_PrinterController.Print(dataToPrint.toString(), false);

           dataToPrint = new StringBuilder();

           //    obtener el monto del total de la infraccion sin dto 
           /*
           decimal descuento = SP.PorcDescuento;
           decimal monto_infracc_sin_dto = 0;
           decimal monto_dto = 0;
           if (_reimpresion)
           {
               monto_infracc_sin_dto = (acta.Monto_cupon * 2);
               monto_dto = acta.Monto_cupon;
           }
           else
           {
               monto_infracc_sin_dto = infServices.getMontoInfraccionesSinDto(acta.CodigoInfraccion1, acta.CodigoInfraccion2);
               monto_dto = monto_infracc_sin_dto - (monto_infracc_sin_dto * (descuento / 100));
           }
           */
           
           // no es reimpresion
           
           Double monto = acta.getMontoCupon();
           String smonto = Tools.DecimalFormat(monto);
           
           Double monto2Do = acta.getMonto2DoCupon();
           String smonto2Do = Tools.DecimalFormat(monto2Do);

           //  fin obtener el monto del total de la infraccion sin dto //
           LabelZebra label = new LabelZebra(325);
           
           dataToPrint.append("! 0 200 200 "+ label.LargoPagina + " 1").append("\r\n");
           dataToPrint.append("IN-DOTS").append("\r\n");
           dataToPrint.append("PW 800").append("\r\n");
           dataToPrint.append("LMARGIN 20").append("\r\n");
           dataToPrint.append("T 7 1 100 " + label.AddY(5) + " CUPÓN DE PAGO PARA EL INFRACTOR ").append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(50) + "  Referencia Nro.Acta: " + acta.getNumeroActa()).append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + "  N.Doc. " + acta.getNumeroDocumento()).append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + "  1er. Vto. " + Tools.DateValueOf(acta.getFechaVencimientoCupon(), "dd/MM/yyyy")).append("\r\n");
           
           dataToPrint.append("T 7 1 20 " + label.AddY(25) + " IMPORTE A PAGAR                 $ ").append("\r\n");
           dataToPrint.append("T 7 1 650 " + label.PosicionY + " " + Tools.formatearIzquierda(smonto, 12, ' ')).append("\r\n");

           // imprimir Linea
           dataToPrint.append("T 7 0 20 " + label.AddY(45) + " " + Tools.formatearIzquierda("-", 100, '-')).append("\r\n");
           
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + "   2do. Vto. " + Tools.DateValueOf(acta.getFechaVencimiento2DoCupon(), "dd/MM/yyyy")).append("\r\n");

           //dataToPrint.append("T 7 0 20 " + label.AddY(25) + " " + Tools.formatearIzquierda("-", 100, '-')).append("\r\n");
           dataToPrint.append("T 7 1 20 " + label.AddY(25) + " IMPORTE A PAGAR                 $ ").append("\r\n");
           dataToPrint.append("T 7 1 650 " + label.PosicionY + " " + Tools.formatearIzquierda(smonto2Do, 12, ' ')).append("\r\n");
          
           return dataToPrint.toString();
         
	   }
	   private static String generateActaParte3bFormatCPCL(ActaConstatacion data)
	   {
	       Object[] arrayOfObject = new Object[6];
	       
	       ActaConstatacion acta = data;
	       StringBuilder dataToPrint = new StringBuilder();

           dataToPrint.append("! 0 200 200 348 1").append("\r\n");
           dataToPrint.append("IN-DOTS").append("\r\n");
           dataToPrint.append("PW 800").append("\r\n");
           dataToPrint.append("LMARGIN 20").append("\r\n");
           //dataToPrint.append("T 7 0 20 0 IMPORTANTE: Queda Ud. debidamente NOTIFICADO de la presenta ACTA.").append("\r\n");
           dataToPrint.append("T 7 0 40 140 ------------------").append("\r\n");
           dataToPrint.append("T 7 0 480 140 ------------------").append("\r\n");
           dataToPrint.append("T 7 0 40 160 Firma y aclaración").append("\r\n");
           dataToPrint.append("T 7 0 480 160 Firma y aclaración ").append("\r\n");
           dataToPrint.append("T 7 0 35 190 del notificado - DNI ").append("\r\n");
           dataToPrint.append("T 7 0 410 190 del funcionario interviniente ").append("\r\n");

           dataToPrint.append("T 7 1 50 255 NO DEBE ABONAR MONTO DE DINERO ALGUNO A LA AUTORIDAD ").append("\r\n");
           dataToPrint.append("T 7 1 190 295 QUE CONFECCIONÓ LA PRESENTE ACTA! ").append("\r\n");
           dataToPrint.append("L 0 345 800 345 1").append("\r\n");

           return dataToPrint.toString();

	   }
	   private static String generateActaParte3FormatCPCL(ActaConstatacion data)
	   {
		   Object[] arrayOfObject = new Object[6];
	       
	       ActaConstatacion acta = data;
	       StringBuilder dataToPrint = new StringBuilder();
	       
		   
           //p_PrinterController.Print(dataToPrint.toString(), false);

           dataToPrint = new StringBuilder();

                                       //
               dataToPrint.append("! 0 200 200 715 1").append("\r\n");
               dataToPrint.append("IN-DOTS").append("\r\n");
               dataToPrint.append("PW 800").append("\r\n");
               dataToPrint.append("LMARGIN 20").append("\r\n");
              // if (Global.GlobalFunc.esCodigoBarraKolektor(acta.Cod_barra))
               {
            	   Integer idJuzgado = 0;
            	   String DireccionJuzgado ="";
            	   String TelefonoJuzgado ="";
            	   String MailJuzgado="";

            	   try
            	   {
            		   idJuzgado = acta.getIdJuzgado();
            		   DireccionJuzgado = acta.getCalleJuzgado() + " Nº " + acta.getAlturaJuzgado() + ", de " + acta.getLocalidadJuzgado() + " Mendoza ";
            		   MailJuzgado = "(tel. " + acta.getTelefonoJuzgado() + "/Mail:" + acta.getEmailJuzgado()+ ")";
            	   } catch(Exception ex){}

            	   dataToPrint.append("T 7 0 20 0 Se le notifica que podrá acogerse al beneficio del pago").append("\r\n");

            	   dataToPrint.append("T 7 0 20 30 voluntario en la sede de la DIRECCIÓN DE JUZGAMIENTO ").append("\r\n");
                   dataToPrint.append("T 7 0 20 60 ADMINISTRATIVO DE INFRACCIONES DE TRANSITO, sita en ").append("\r\n");

                   LabelZebra label = new LabelZebra(100);
                   label.PosicionY = 60;
                   dataToPrint.append("T 7 0 20 " + label.AddY(30) +" " + DireccionJuzgado + "").append("\r\n");
                   dataToPrint.append("T 7 0 20 " + label.AddY(30) +" " + MailJuzgado + "").append("\r\n");
                   dataToPrint.append("T 7 0 20 " + label.AddY(30) +" y/o NUEVO BANCO DEL Mendoza S.A. y sucursales y/o ").append("\r\n");
                   dataToPrint.append("T 7 0 20 " + label.AddY(30) +" CAJA MUNICIPAL DE CORRIENTES y sucursales,").append("\r\n");
                   dataToPrint.append("T 7 0 20 " + label.AddY(30) +" y/o CAJA MUNICIPAL DE RESISTENCIA y sucursales, y/o BANCO \r\n");

                   dataToPrint.append("T 7 0 20 " + label.AddY(30) +" NACIÓN ARGENTINA y sucursales, y/o AGENCIAS OFICIALES DE LA ").append("\r\n");

                   dataToPrint.append("T 7 0 20 " + label.AddY(30) +" LOTERIA CHAQUEÑA; conforme al valor de la infraccón dispuesto ").append("\r\n");

                   dataToPrint.append("T 7 0 20 " + label.AddY(30) +" en cupón de pago para el 1º o 2º vencimiento; pago que se ").append("\r\n");


                   
                   dataToPrint.append("T 7 0 20 " + label.AddY(30) +" imputará a cuenta del total en caso de reincidencia. También ").append("\r\n");

                   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " podra efectuar descargo por escrito en la sede de la citada").append("\r\n");
                    
                   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " DIRECCIÓN o en Division de Tránsito Urbano o División de Patrulla").append("\r\n");
                   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " Vial de la ciudad interior que se trate en el termino de (30)").append("\r\n");

                   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " días corridos de fecha de labrada la presente Acta; y ofreciendo ").append("\r\n");

                   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " pruebas que considere oportunas. Cumplido dicho termino").append("\r\n");
                   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " a ofrecer descargo y pruebas (art. 1º ley 6663 - art.1º ").append("\r\n");
                   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " ,queda emplazado por otros (5) días hábiles  ").append("\r\n");
                   
                   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " Resolución 351/11; bajo apercibimiento de declararlo en rebeldía, ").append("\r\n");
                   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " asistiendole el derecho de designar letrado a su costa. Se da por").append("\r\n");
                   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " terminado el acto,previa lectura al infractor, entregándose ").append("\r\n");
                   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " copia del Acta al mismo; como constancia de su notificación.").append("\r\n");
                   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " Certifica.-").append("\r\n");
                   

               }
              
               return dataToPrint.toString();
               
		   
	   }
	   private static String generateActaParte2FormatCPCL(ActaConstatacion data)
	   {
	       Object[] arrayOfObject = new Object[6];
	       
	       ActaConstatacion acta = data;
	       StringBuilder dataToPrint = new StringBuilder();
	      
           dataToPrint.append("! U1 SETLP 7 0 10 " + acta.getObservaciones() + " \r\n");
           
           // imprimer los detalles de las infracciones si las hay
           //if (objInf1.NotasImpresion.toString() != "" || objInf2.NotasImpresion.toString() != "")
           if(acta.getNotasImpresion1()!=null ||  acta.getNotasImpresion2()!=null)
           {

               if (acta.getNotasImpresion1()!=null && !acta.getNotasImpresion1().equals(""))
               {
                   dataToPrint.append("" + acta.getNotasImpresion1()).append("\r\n");
               }

               if (acta.getNotasImpresion2()!=null && !acta.getNotasImpresion2().equals(""))
               {
                   dataToPrint.append("" + acta.getNotasImpresion2()).append("\r\n");
               }
           }



           return dataToPrint.toString();
           
           //dataToPrint.append("PRINT").append("\r\n");
          
	   }
	   /* 01 - Mendoza */
	   private static String generateHeaderProvincia(String codigoBarraNumeroActa)
	   {

		       Object[] arrayOfObject = new Object[6];
		       
		       LabelZebra label = new LabelZebra(156);
	           StringBuilder dataToPrint = new StringBuilder();
	           String sLey = GlobalVar.getInstance().getSuportTable().getDescripcionLey();
	           dataToPrint.append("! 0 200 200 " + label.LargoPagina + " 1\r\n")
				  .append("IN-DOTS\r\n")
				  .append("PW 800\r\n")
				  .append("LMARGIN 20\r\n")
				  .append("L 0 " + label.PosicionY + " 800 " + label.PosicionY + " 1").append("\r\n")
				  .append("T 7 1 150 " + label.AddY(20) + " S Y C S A \r\n")
				  .append("T 7 0 150 " + label.AddY(42) + " Acta de Infracción \r\n")
	              .append("T 7 0 150 " + label.AddY(36) + " Servicios y Consultoria S.A. \r\n")
	              .append("T 7 0 550 " + (label.PosicionY + 10 )  + " " + sLey + "\r\n")
	           	  .append("B 39 1 0 50 462 25 " + codigoBarraNumeroActa).append("\r\n")
				  .append("T 7 0 543 85 *" + codigoBarraNumeroActa).append("*\r\n");
				  
	           
	           dataToPrint.append("L 0 " + label.AddY(60) + " 800 " + label.PosicionY + " 1").append("\r\n");
	           return dataToPrint.toString();
				  
	   }
	   /* 02 - Mendoza */
	   private static String generateActaParteFechaFormatCPCL(ActaConstatacion data)
	   {
	       ActaConstatacion acta = data;

	       LabelZebra label = new LabelZebra(140);//5 demas
           StringBuilder dataToPrint = new StringBuilder();
           dataToPrint.append("! 0 200 200 " + label.LargoPagina + " 1\r\n")
			  .append("IN-DOTS\r\n")
			  .append("PW 800\r\n")
			  .append("LMARGIN 20\r\n")		//
			  .append("T 7 0 20 " + label.AddY(15) + " Fecha y Hora : " + Tools.DateValueOf(acta.getFechaHoraLabrado(),"dd/MM/yyyy HH:mm")).append("\r\n");

           dataToPrint.append("L 0 " + label.AddY(30) + " 800 " + label.PosicionY + " 1").append("\r\n");

           // falta la posibilidad de marcar que el vehiculo este detenido
           String strRetenida = acta.getLicenciaRetendia().equals("S") ? "Si" : "No";
           String strConduccionPeligrosa = acta.getConduccionPeligrosa().equals("S") ? "Si" : "No";
           String strVehiculoRetenido = acta.getVehiculoRetenido().equals("S") ? "Si" : "No";
           String strDejaCopia = acta.getDejaCopia().equals("S") ? "Si" : "No";
           																							 //Conduccion Peligrosa
           dataToPrint.append("T 7 0 20 " + label.AddY(10) + " Retiene Licencia : "  + strRetenida +  "               Viola Art. 81 : " + strConduccionPeligrosa + " " +"\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(20) + " Secuestra Vehiculo : " + strVehiculoRetenido + " " +"\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(20) + " Deja Copia : " + strDejaCopia + " " +"\r\n");
           dataToPrint.append("L 0 " + label.AddY(30) + " 800 " + label.PosicionY + " 1").append("\r\n");
           String temp = dataToPrint.toString();//String.format("",arrayOfObject);  
  	     return temp;

	   }
	   
	   /* 03 - Mendoza Tipo de Vehiculo */
	   private static String generateActaParteVehiculoFormatCPCL(ActaConstatacion data)
	   {

	       ActaConstatacion acta = data;
	       
	     //BEGIN
	       LabelZebra label = new LabelZebra(180+ 25);
	       String sSinTitular = acta.getSinTitular();
	       
	       if (sSinTitular!=null && sSinTitular.equals("S"))
	       {
	    	   label.LargoPagina = label.LargoPagina - 50; 
	       }

           String strMarca = acta.getMarca();//acta.Id_Marca.Equals(-1) || acta.Id_Marca.Equals(100) ? acta.MarcaNueva : marca.N_Marca;
           String strColor = acta.getColor();//acta.Id_color.Equals(-1) ? acta.ColorNuevo : color.N_color;
           TipoVehiculo tipoVehiculo = acta.getoTipoVehiculo();
           
           String sRequiereDominio = null;
           try{
        	   sRequiereDominio = tipoVehiculo.getRequiereDominio();
           } catch(Exception ex){}
           
           String strTipoVehiculo  = "";// por validacion siempre deberia tener un valor
           try{
           strTipoVehiculo  = tipoVehiculo.getNombre();
           } catch(Exception ex){}
           //String strIdTipoVehiculo = tipoVehiculo.getId();

	       if (sRequiereDominio==null || sRequiereDominio.equals("N"))
	       {
	    	   label.LargoPagina = label.LargoPagina - 60;
	       }
	       
           StringBuilder dataToPrint = new StringBuilder();
           dataToPrint.append("! 0 200 200 " + label.LargoPagina + " 1\r\n")
			  .append("IN-DOTS\r\n")
			  .append("PW 800\r\n")
			  .append("LMARGIN 20\r\n");		//
           
           
           if (sRequiereDominio!=null && sRequiereDominio.equals("S"))
           {  /*
        	   dataToPrint.append("! 0 200 200 " + label.LargoPagina + " 1\r\n")
 			  	.append("IN-DOTS\r\n")
 			  	.append("PW 800\r\n")
 			  	.append("LMARGIN 20\r\n");		//
        	   */
        	   String strAnio="";
        	   try { 
        		   strAnio = acta.getAnioModeloVehiculo();
        	   } catch(Exception e){}
        	   dataToPrint.append("T 7 0 20 " + label.AddY(15) + " VEHÍCULO").append("\r\n");
	           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Dominio: " + Tools.formatearDerecha(acta.getDominio(), 15, ' ') + " Tipo: " +  strTipoVehiculo).append("\r\n");
	           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Modelo: " + acta.getModeloVehiculo() + " Año: " + strAnio  ).append("\r\n");
	           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Marca: " + strMarca + " Color: " + strColor ).append("\r\n");
           }
           else
           {   //Integer iLargo = label.LargoPagina -60;
        	   /*
        	    dataToPrint.append("! 0 200 200 " + (iLargo) + " 1\r\n")
 			  	.append("IN-DOTS\r\n")
 			  	.append("PW 800\r\n")
 			  	.append("LMARGIN 20\r\n");		//
        	   */
        	   dataToPrint.append("T 7 0 20 " + label.AddY(15) + " " + strTipoVehiculo.toUpperCase() ).append("\r\n");
        	   
           }
           
           if (sSinTitular==null || sSinTitular.equals("N"))
           {
             dataToPrint.append("T 7 0 20 " + label.AddY(25) + acta.getTipoDocumentoTitular() + " Propietario: " + acta.getNumeroDocumentoTitular()).append("\r\n");
             dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Propietario : ").append("\r\n");
             dataToPrint.append("T 7 0 20 " + label.AddY(25) + " " + acta.getApellidoTitular() + ", " + acta.getNombreTitular()).append("\r\n");
           }
           dataToPrint.append("L 0 " + label.AddY(30) + " 800 " + label.PosicionY + " 1").append("\r\n");
           String temp = dataToPrint.toString();//String.format("",arrayOfObject);  
  	     return temp;

	   }	   
	   private static String generateActaParteInfraccionFormatCPCL(ActaConstatacion data)
	   {

	       ActaConstatacion acta = data;
	       
	     //BEGIN
	       int largoSinInfracciones = 250;
	       
			List<Integer> lstIdInfracciones = new ArrayList<Integer>();

			if (data.getIdInfraccion1()!=null && data.getIdInfraccion1()>0) lstIdInfracciones.add(data.getIdInfraccion1());
			if (data.getIdInfraccion2()!=null && data.getIdInfraccion2()>0) lstIdInfracciones.add(data.getIdInfraccion2());
			if (data.getIdInfraccion3()!=null && data.getIdInfraccion3()>0) lstIdInfracciones.add(data.getIdInfraccion3());
			if (data.getIdInfraccion4()!=null && data.getIdInfraccion4()>0) lstIdInfracciones.add(data.getIdInfraccion4());
			if (data.getIdInfraccion5()!=null && data.getIdInfraccion5()>0) lstIdInfracciones.add(data.getIdInfraccion5());
			if (data.getIdInfraccion6()!=null && data.getIdInfraccion6()>0) lstIdInfracciones.add(data.getIdInfraccion6());

	       
	       int cantidadInfracciones = lstIdInfracciones.size();
	       
	       
	       int largoTotal = largoSinInfracciones + (cantidadInfracciones * 50) + 30 + 30; // los 30 ultimos son para el agregado del GPS lat lon
	       String sIdTipoRuta = acta.getIdTipoRuta();
	       if(sIdTipoRuta!=null && sIdTipoRuta.equals("3"))
	    	   largoTotal = largoTotal + 100;
		   if(!acta.getGrad_alcohol().equals("") && acta.getGrad_alcohol() != ""){
			   largoTotal = largoTotal + 30;
		   }

			   LabelZebra label = new LabelZebra(largoTotal);
           StringBuilder dataToPrint = new StringBuilder();
           dataToPrint.append("! 0 200 200 " + label.LargoPagina + " 1\r\n")
			  .append("IN-DOTS\r\n")
			  .append("PW 800\r\n")
			  .append("LMARGIN 20\r\n");		//

           dataToPrint.append("T 7 0 20 " + label.AddY(10) + " INFRACCIÓN ").append("\r\n");
    	   
           dataToPrint.append("T 7 0 20 " + label.AddY(20) + " Departamento de la Infracción :" + acta.getDepartamentoInfraccion()).append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(20) + " Localidad de la Infracción :" + acta.getLocalidadInfraccion()).append("\r\n");
           
           if (!sIdTipoRuta.equals("3"))
        	   dataToPrint.append("T 7 0 20 " + label.AddY(20) + " Lugar de la Infracción :" + acta.getTipoRuta() + " " + acta.getNumeroRuta() + " " +acta.getDescripcionUbicacion().trim() + " " + acta.getKm() ).append("\r\n");
           else
           {
        	   dataToPrint.append("T 7 0 20 " + label.AddY(20) + " Lugar de la Infracción :" ).append("\r\n");
        	   dataToPrint.append("T 7 0 20 " + label.AddY(25) + acta.getTipoRuta() + " " + acta.getNumeroRuta() + " :").append("\r\n");
        	   //dataToPrint.append("T 7 0 20 " + label.AddY(20)  ).append("\r\n");
        	   dataToPrint.append("T 7 0 20 " + label.AddY(25) + " " + acta.getDescripcionUbicacion().trim() + " Alt. " + acta.getKm() ).append("\r\n"); 
           }
			//dataToPrint.append("T 7 0 20 " + label.AddY(20) + " Localidad :"  ).append("\r\n");
			//dataToPrint.append("T 7 0 20 " + label.AddY(20) + " Departamento :"  ).append("\r\n");
            dataToPrint.append("T 7 0 20 " + label.AddY(30) + " " + data.getLatitud() + " - " + data.getLongitud()).append("\r\n");
			String strReferencia = acta.getReferencia();
			dataToPrint.append("T 7 0 25 " + label.AddY(28) + " Referencia : " ).append("\r\n");
			dataToPrint.append("T 7 0 20 " + label.AddY(20) + " " + strReferencia).append("\r\n");
			dataToPrint.append("T 7 0 25 " + label.AddY(28) + " Infracción"  ).append("\r\n");

           if (acta.getCodigoInfraccion1()!=null && !acta.getCodigoInfraccion1().equals(""))
           {
        	    String sArticulo = acta.getoInfraccionNomenclada1().getArticulo();
        	    String sInciso = acta.getoInfraccionNomenclada1().getInciso();
        	    String sDescripcion =  Tools.formatearDerecha(acta.getDescripcionInfraccion1(), 100, '.');
     	     	String sDescripcionParte1 = sDescripcion.substring(0, 48);
     	     	String sDescripcionParte2 = sDescripcion.substring(48);
     	     	if(acta.getImprimirPuntos1()!=null && acta.getImprimirPuntos1().equals("S"))
                   dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Cod:" + acta.getCodigoInfraccion1() + "(Pts:" + acta.getPuntos1().toString() + ")" + sDescripcionParte1 ).append("\r\n");
                else														
                  dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Cod:"  + acta.getCodigoInfraccion1() + " " + sDescripcionParte1).append("\r\n");
                 													
               dataToPrint.append("T 7 0 20 " + label.AddY(25) + "  " + sDescripcionParte2).append("\r\n");
           }

           if (acta.getCodigoInfraccion2()!=null && !acta.getCodigoInfraccion2().equals(""))
           {
        	    String sArticulo = acta.getoInfraccionNomenclada2().getArticulo();
        	    String sInciso = acta.getoInfraccionNomenclada2().getInciso();
        	    String sDescripcion =  Tools.formatearDerecha(acta.getDescripcionInfraccion2(), 100, '.');
     	     	String sDescripcionParte1 = sDescripcion.substring(0, 48);
     	     	String sDescripcionParte2 = sDescripcion.substring(48);
     	     	if(acta.getImprimirPuntos2()!=null && acta.getImprimirPuntos2().equals("S"))
                   dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Cod:" + acta.getCodigoInfraccion2() + "(Pts:" + acta.getPuntos2().toString() + ")" + sDescripcionParte1 ).append("\r\n");
                else														
                  dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Cod:"  + acta.getCodigoInfraccion2() + " " + sDescripcionParte1).append("\r\n");
                 													
     	     	dataToPrint.append("T 7 0 20 " + label.AddY(25) + "  " + sDescripcionParte2).append("\r\n");
           }

           if (acta.getCodigoInfraccion3()!=null && !acta.getCodigoInfraccion3().equals(""))
           {
        	    String sArticulo = acta.getoInfraccionNomenclada3().getArticulo();
        	    String sInciso = acta.getoInfraccionNomenclada3().getInciso();
        	    String sDescripcion =  Tools.formatearDerecha(acta.getDescripcionInfraccion3(), 100, '.');
     	     	String sDescripcionParte1 = sDescripcion.substring(0, 48);
     	     	String sDescripcionParte2 = sDescripcion.substring(48);
     	     	if(acta.getImprimirPuntos3()!=null && acta.getImprimirPuntos3().equals("S"))
                   dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Cod:" + acta.getCodigoInfraccion3() + "(Pts:" + acta.getPuntos3().toString() + ")" + sDescripcionParte1 ).append("\r\n");
                else														
                  dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Cod:"  + acta.getCodigoInfraccion3() + " " + sDescripcionParte1).append("\r\n");
                 													
               dataToPrint.append("T 7 0 20 " + label.AddY(25) + "  " + sDescripcionParte2).append("\r\n");
           }

           if (acta.getCodigoInfraccion4()!=null && !acta.getCodigoInfraccion4().equals(""))
           {
        	    String sArticulo = acta.getoInfraccionNomenclada4().getArticulo();
        	    String sInciso = acta.getoInfraccionNomenclada4().getInciso();
        	    String sDescripcion =  Tools.formatearDerecha(acta.getDescripcionInfraccion4(), 100, '.');
     	     	String sDescripcionParte1 = sDescripcion.substring(0, 48);
     	     	String sDescripcionParte2 = sDescripcion.substring(48);
     	     	if(acta.getImprimirPuntos4()!=null && acta.getImprimirPuntos4().equals("S"))
                   dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Cod:" + acta.getCodigoInfraccion4() + "(Pts:" + acta.getPuntos4().toString() + ")" + sDescripcionParte1 ).append("\r\n");
                else														
                  dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Cod:"  + acta.getCodigoInfraccion4() + " " + sDescripcionParte1).append("\r\n");
                 													
               dataToPrint.append("T 7 0 20 " + label.AddY(25) + "  " + sDescripcionParte2).append("\r\n");
           }

           if (acta.getCodigoInfraccion5()!=null && !acta.getCodigoInfraccion5().equals(""))
           {
        	    String sArticulo = acta.getoInfraccionNomenclada5().getArticulo();
        	    String sInciso = acta.getoInfraccionNomenclada5().getInciso();
        	    String sDescripcion =  Tools.formatearDerecha(acta.getDescripcionInfraccion5(), 100, '.');
     	     	String sDescripcionParte1 = sDescripcion.substring(0, 48);
     	     	String sDescripcionParte2 = sDescripcion.substring(48);
     	     	if(acta.getImprimirPuntos5()!=null && acta.getImprimirPuntos5().equals("S"))
                   dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Cod:" + acta.getCodigoInfraccion5() + "(Pts:" + acta.getPuntos5().toString() + ")" + sDescripcionParte1 ).append("\r\n");
                else														
                  dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Cod:"  + acta.getCodigoInfraccion5() + " " + sDescripcionParte1).append("\r\n");
                 													
               dataToPrint.append("T 7 0 20 " + label.AddY(25) + "  " + sDescripcionParte2).append("\r\n");
           }

           if (acta.getCodigoInfraccion6()!=null && !acta.getCodigoInfraccion6().equals(""))
           {
        	    String sArticulo = acta.getoInfraccionNomenclada6().getArticulo();
        	    String sInciso = acta.getoInfraccionNomenclada6().getInciso();
        	    String sDescripcion =  Tools.formatearDerecha(acta.getDescripcionInfraccion6(), 100, '.');
     	     	String sDescripcionParte1 = sDescripcion.substring(0, 48);
     	     	String sDescripcionParte2 = sDescripcion.substring(48);
     	     	if(acta.getImprimirPuntos6()!=null && acta.getImprimirPuntos6().equals("S"))
                   dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Cod:" + acta.getCodigoInfraccion6() + "(Pts:" + acta.getPuntos6().toString() + ")" + sDescripcionParte1 ).append("\r\n");
                else														
                  dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Cod:"  + acta.getCodigoInfraccion6() + " " + sDescripcionParte1).append("\r\n");
                 													
               dataToPrint.append("T 7 0 20 " + label.AddY(25) + "  " + sDescripcionParte2).append("\r\n");
           }

           if(lstIdInfracciones.size()>1)
           {
        	   dataToPrint.append("T 7 0 20 " + label.AddY(25) + "  " + "Sanción alcanzada por la especificación del art. 82 ").append("\r\n");
        	   dataToPrint.append("T 7 0 20 " + label.AddY(25) + "  " + "de la ley de tránsito").append("\r\n");
        	   
           }

           if(!acta.getGrad_alcohol().equals("") && acta.getGrad_alcohol() != ""){
			   //dataToPrint.append("PRINT\r\n");
			   //dataToPrint.append("! U1 SETBOLD 2 \r\n");
			   dataToPrint.append("T 7 0 20 " + label.AddY(25) + "  Nivel de Alcohol en Sangre: " + acta.getGrad_alcohol() + " g/L").append("\r\n");
			   //dataToPrint.append("Nivel de Alcohol en Sangre: " + acta.getGrad_9hol() + " g/L " + "! U1 SETBOLD 0").append("\r\n");

		   }

		   dataToPrint.append("T 7 0 20 " + label.AddY(25) + "  Observaciones: \r\n");

           String temp = dataToPrint.toString();//String.format("",arrayOfObject);  
  	     return temp;
	   }
	   
	   private static String generateActaParteObservacionesInfraccionFormatCPCL(ActaConstatacion data)
	   {
	       Object[] arrayOfObject = new Object[6];
	       
	       ActaConstatacion acta = data;
	       StringBuilder dataToPrint = new StringBuilder();
	      
           dataToPrint.append("! U1 SETLP 7 0 10 " + acta.getObservaciones() + " \r\n");
           
           // imprimer los detalles de las infracciones si las hay
           //if (objInf1.NotasImpresion.toString() != "" || objInf2.NotasImpresion.toString() != "")
           if(acta.getNotasImpresion1()!=null ||  acta.getNotasImpresion2()!=null)
           {

               if (acta.getNotasImpresion1()!=null && !acta.getNotasImpresion1().equals(""))
               {
                   dataToPrint.append("" + acta.getNotasImpresion1()).append("\r\n");
               }

               if (acta.getNotasImpresion2()!=null && !acta.getNotasImpresion2().equals(""))
               {
                   dataToPrint.append("" + acta.getNotasImpresion2()).append("\r\n");
               }
           }
           return dataToPrint.toString();
           
	   }

	   private static String generateActaParteInfractorFormatCPCL(ActaConstatacion data)
	   {

	       ActaConstatacion acta = data;
	       
	     //BEGIN
	       LabelZebra label = new LabelZebra(340+25 - 10);//antes 300 -10 porque quitamos una linea
           String sSinConductor = data.getSinConductor();
           if (sSinConductor!=null && sSinConductor.equals("S"))
           {
        	   label.LargoPagina = label.LargoPagina - 175 ;
           }
           
           String sTieneLicencia = data.getTieneLicencia();
	       if(sTieneLicencia!=null && sTieneLicencia.equals("N"))
	       {
	    	   label.LargoPagina = label.LargoPagina - 20 ;
	       }
           
	       StringBuilder dataToPrint = new StringBuilder();
           dataToPrint.append("! 0 200 200 " + label.LargoPagina + " 1\r\n")
			  .append("IN-DOTS\r\n")
			  .append("PW 800\r\n")
			  .append("LMARGIN 20\r\n");		//
           /*****  SECCION INFRACTOR *****/	
           dataToPrint.append("L 0 " + label.PosicionY + " 800 " + label.PosicionY + " 1").append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(15) + " INFRACTOR").append("\r\n");
           
           if (sSinConductor!=null && sSinConductor.equals("S"))
           {
	           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " No se registro Conductor"  ).append("\r\n");
           }
           else
           {  
        	   String strApellidoYNombre = "";
        	   try {
        		   strApellidoYNombre = Utilities.NVLString(acta.getApellido());
        	   } catch(Exception e ){}
        	   
        	   try {
        		   strApellidoYNombre = strApellidoYNombre  + ", " + Utilities.NVLString(acta.getNombre());
        	   } catch(Exception e ){}
        	   
	           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " DNI: " + Utilities.NVLString( acta.getNumeroDocumento())).append("\r\n");
	           dataToPrint.append("T 7 0 20 "  + label.AddY(25)   + " Apellido y Nombre: " + strApellidoYNombre ).append("\r\n");
	           //dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Apellido: " + Tools.formatearDerecha(, 25, ' ')).append("\r\n");
	           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Telefono: " + Tools.formatearDerecha(acta.getTelefonoConductor(), 25, ' ')).append("\r\n");
	           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " EMail: " + Tools.formatearDerecha(acta.getEmailConductor(), 25, ' ')).append("\r\n");
	           //String sFechaVencimientoNacimiento = acta.getFechaNacimiento()==null?"":Tools.DateValueOf(acta.getFechaNacimiento(),"dd/MM/yy");
	           //dataToPrint.append("T 7 0 20 "  + label.AddY(25)  + " Fecha Nacimiento: " + sFechaVencimientoNacimiento).append("\r\n");
	           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Calle : " + Tools.formatearDerecha(acta.getCalle(), 25, ' ').trim() + " Nro:" + Tools.formatearDerecha(acta.getAltura(), 5, ' ') ).append("\r\n");
	           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Piso:" + Tools.formatearDerecha(acta.getPiso(), 3, ' ') + " Dpto:" + Tools.formatearDerecha(acta.getDepartamento(), 5, ' ') + " Barrio : " + acta.getBarrio()).append("\r\n");
	           //dataToPrint.append("T 7 0 20 " + label.AddY(25) ).append("\r\n");

	           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " País: " + Tools.formatearDerecha(acta.getPaisDomicilio(), 25, ' ')).append("\r\n");
	           dataToPrint.append("T 7 0 400 " + label.PosicionY + " Prov: " + Tools.formatearDerecha(acta.getProvinciaDomicilio(), 25, ' ')).append("\r\n");
	           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Dpto: " + Tools.formatearDerecha(acta.getDepartamentoDomicilio(), 25, ' ')).append("\r\n");
	           dataToPrint.append("T 7 0 400 " + label.PosicionY + " Loc: " + Tools.formatearDerecha(acta.getLocalidadDomicilio(), 25, ' ')).append("\r\n");
           }

           String numeroLicencia = acta.getNumeroLicencia()==null?"":acta.getNumeroLicencia();
           String claseLicencia = acta.getClaseLicencia()==null?"":acta.getClaseLicencia();
           String sFechaVencimientoLicencia = acta.getFechaVencimientoLicencia()==null?"":Tools.DateValueOf(acta.getFechaVencimientoLicencia(),"dd/MM/yy");
           if (numeroLicencia.equals("")) sFechaVencimientoLicencia ="   ";

           
           
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " LICENCIA").append("\r\n");
           if (sTieneLicencia!=null && sTieneLicencia.equals("N"))
           {
        	   dataToPrint.append("T 7 0 20 "  + label.AddY(25) + " No Tiene Licencia de Conducir ").append("\r\n");
           }
           else
           {	   
        	   String sProcedencia = "";
        	   String strProvincia = "";
        	   String strPais = "";
        	   try{
        	        strProvincia = "";
        	        try{
        	        strProvincia = acta.getProvinciaLicencia();
        	        //if (!acta.getIdProvinciaLicencia().equals("-1")) strProvincia= acta.getIdProvinciaLicencia();
        	        } catch(Exception ex){}
        	       
        	        strPais = "";
        	        try{
        	        strPais = acta.getPaisLicencia();
        	        //if (!acta.getIdPaisLicencia().equals("-1")) strPais= acta.getIdPaisLicencia();
        	        } catch(Exception ex){}
        		   
        	   }catch(Exception ex){
        		   
        	   }
        	   try
        	   {
        		   sProcedencia = strPais + " " + strProvincia;
        	   }catch(Exception ex){}
        	   
        	   dataToPrint.append("T 7 0 20 "  + label.AddY(25) + " Licencia Nº:" + numeroLicencia + " Categoría:" + claseLicencia).append("\r\n");
        	   dataToPrint.append("T 7 0 20 "  + label.AddY(25) + " Vto:" + sFechaVencimientoLicencia + " Procedencia :" + sProcedencia).append("\r\n");
           }
           //dataToPrint.append("L 0 " + label.AddY(30) + " 800 " + label.PosicionY + " 1").append("\r\n");
           String temp = dataToPrint.toString();//String.format("",arrayOfObject);  
  	     return temp;

	   }	   
	   private static String generateActaParteTestigoFormatCPCL(ActaConstatacion data)
	   {

		   //
		   
	       ActaConstatacion acta = data;
	       
	     //BEGIN
	       Integer iLargo = 150 + 25 -10; //-10 porque quitamos una linea 
	       if(data.getSinTestigo().equals("S"))	 
	    	   iLargo = 10;
	       
	       LabelZebra label = new LabelZebra(iLargo);
           StringBuilder dataToPrint = new StringBuilder();
           dataToPrint.append("! 0 200 200 " + label.LargoPagina + " 1\r\n")
			  .append("IN-DOTS\r\n")
			  .append("PW 800\r\n")
			  .append("LMARGIN 20\r\n");		//

           String strApellidoYNombre = "";
    	   try {
    		   strApellidoYNombre = Utilities.NVLString(acta.getApellidoTestigo());
    	   } catch(Exception e ){}
    	   
    	   try {
    		   strApellidoYNombre = strApellidoYNombre  + ", " + Utilities.NVLString(acta.getNombreTestigo());
    	   } catch(Exception e ){}
           
           String strMarca = acta.getMarca();//acta.Id_Marca.Equals(-1) || acta.Id_Marca.Equals(100) ? acta.MarcaNueva : marca.N_Marca;
           String strColor = acta.getColor();//acta.Id_color.Equals(-1) ? acta.ColorNuevo : color.N_color;
           dataToPrint.append("L 0 " + label.PosicionY + " 800 " + label.PosicionY + " 1").append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(15) + " TESTIGO").append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " DNI: " + Utilities.NVLString(acta.getNumeroDocumentoTestigo()) ).append("\r\n");
           dataToPrint.append("T 7 0 20 "  + label.AddY(25) + " Apellido y Nombre: " + strApellidoYNombre).append("\r\n");
           //dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Apellido: " + Tools.formatearDerecha(acta.getApellidoTestigo(), 25, ' ')).append("\r\n");

           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Calle : " + Tools.formatearDerecha(acta.getCalleTestigo(), 25, ' ').trim() + " Nro:" + Tools.formatearDerecha(acta.getAlturaTestigo(), 5, ' ') ).append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Piso:" + Tools.formatearDerecha(acta.getPisoTestigo(), 3, ' ') + " Dpto:" + Tools.formatearDerecha(acta.getDepartamentoTestigo(), 5, ' ') + " Barrio : " + acta.getBarrioTestigo()).append("\r\n");
           //dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Barrio : " + Tools.formatearDerecha(acta.getBarrioTestigo(), 25, ' ').trim()).append("\r\n");

           //dataToPrint.append("T 7 0 400 " + label.PosicionY + " Prov: " + Tools.formatearDerecha(acta.getProvinciaDomicilio(), 25, ' ')).append("\r\n");
           //dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Dpto: " + Tools.formatearDerecha(acta.getDepartamentoDomicilio(), 25, ' ')).append("\r\n");
           //dataToPrint.append("T 7 0 400 " + label.PosicionY + " Loc: " + Tools.formatearDerecha(acta.getLocalidadDomicilio(), 25, ' ')).append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Manifestacion: " ).append("\r\n");
           
           String temp = dataToPrint.toString();//String.format("",arrayOfObject);  
  	     return temp;

	   }	   
	   private static String generateActaParteManifestacionTestigoFormatCPCL(ActaConstatacion data)
	   {
	       Object[] arrayOfObject = new Object[6];
	       
	       ActaConstatacion acta = data;
	       StringBuilder dataToPrint = new StringBuilder();
	      
           dataToPrint.append("! U1 SETLP 7 0 10 " + Utilities.NVLString(acta.getManifestacionTestigo()) + " \r\n");
           return dataToPrint.toString();
       }

	   private static String generateActaParteEspacioParaFirmaTestigoFormatCPCL(ActaConstatacion data)
	   {
	       ActaConstatacion acta = data;
	       
	       Integer iLargo = 50;
	       if(data.getSinTestigo().equals("S"))	 
	    	   iLargo = 10;
	       
	       LabelZebra label = new LabelZebra(iLargo);
           StringBuilder dataToPrint = new StringBuilder();
           dataToPrint.append("! 0 200 200 " + label.LargoPagina + " 1\r\n")
			  .append("IN-DOTS\r\n")
			  .append("PW 800\r\n")
			  .append("LMARGIN 20\r\n");		//

           dataToPrint.append("T 7 0 20 " + label.AddY(45) + " Firma : ..........................").append("\r\n");
           
           String temp = dataToPrint.toString();//String.format("",arrayOfObject);  
  	     return temp;

	   }	   

	   private static String generateActaParteFirmaAutoridadInfractor(ActaConstatacion data, boolean impresionCompleta)
	   {
		   LabelZebra label = new LabelZebra(350-70+30+60); //-70 PORQUE BORRAMOS LAS LINEAS DE NO DEBE ABONAR .---+30 porque agregamos el Dispositivo que genero el acta
	       Object[] arrayOfObject = new Object[6];
	       
	       ActaConstatacion acta = data;
	       StringBuilder dataToPrint = new StringBuilder();


			//android.resource://ar.gov.mendoza.deviceactas/drawable/sinfoto
		   if (impresionCompleta){
		   	label.LargoPagina -= 30;
		   }
		   
           dataToPrint.append("! 0 200 200 " + label.LargoPagina + " 1").append("\r\n");
           dataToPrint.append("IN-DOTS").append("\r\n");
           dataToPrint.append("PW 800").append("\r\n");
           dataToPrint.append("LMARGIN 20").append("\r\n");
           
           dataToPrint.append("L 0 " + label.PosicionY + " 800 " + label.PosicionY + " 1").append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(20) + " IMPORTANTE: Queda Ud. debidamente NOTIFICADO de la presente ACTA.").append("\r\n");
           dataToPrint.append("T 7 0 40 " + label.AddY(120) + " ------------------").append("\r\n");
           dataToPrint.append("T 7 0 480 " + label.PosicionY + " ------------------").append("\r\n");

           dataToPrint.append("T 7 0 40 " + label.AddY(20) + " Firma y aclaración").append("\r\n");

           dataToPrint.append("T 7 0 480 " + label.PosicionY + " Firma y aclaración ").append("\r\n");
           dataToPrint.append("T 7 0 35 " + label.AddY(30) + " del notificado - DNI ").append("\r\n");
           dataToPrint.append("T 7 0 410 " + label.PosicionY + " del funcionario interviniente ").append("\r\n");
           String strIdMovil = acta.getIdMovil().toString();
           strIdMovil = Tools.formatearIzquierda(strIdMovil, 4, '0');
           dataToPrint.append("T 7 0 20 " + label.AddY(50) + " N° Dispositivo : " + strIdMovil).append("\r\n");

           if (!impresionCompleta){
			   String fotos = acta.getFotos();
			   String[] partes =  fotos.trim().split("\\|");
			   int cantidadFotos = 0;

			   for (int i=0; i < partes.length; i++){
				   if (partes[i].indexOf("sinfoto") < 0){
					   cantidadFotos += 1;
				   }
			   }
			   dataToPrint.append("T 7 0 20 " + label.AddY(20) + " Cantidad de fotos guardadas : " + cantidadFotos).append("\r\n");
		   }

           //

		   dataToPrint.append("L 0 " + label.AddY(60) + "  800 " + label.PosicionY + "  1").append("\r\n");

           return dataToPrint.toString();
		   
	   }
	   

	   private static String generateActaParteLugarPagoFormatCPCL(ActaConstatacion data)
	   {
	       
	       ActaConstatacion acta = data;
	       StringBuilder dataToPrint = new StringBuilder();
	       
           dataToPrint = new StringBuilder();
	       LabelZebra label = new LabelZebra(330+30+30-90+30+150);
       	   dataToPrint.append("! 0 200 200 "  + label.LargoPagina + " 1").append("\r\n");
           dataToPrint.append("IN-DOTS").append("\r\n");
           dataToPrint.append("PW 800").append("\r\n");
           dataToPrint.append("LMARGIN 20").append("\r\n");

           Integer idJuzgado = 0;
    	   String DireccionJuzgado ="";
    	   String LocalidadJuzgado ="";
    	   String TelefonoJuzgado ="";
    	   String MailJuzgado="";
    	   String JuzgadoNombre = "";
    	   try
    	   {   
    		   idJuzgado = acta.getIdJuzgado();

    		   DireccionJuzgado = acta.getCalleJuzgado() ;
    		   LocalidadJuzgado = acta.getLocalidadJuzgado() + ", Mendoza";
    		   //MailJuzgado = "(tel. " + acta.getTelefonoJuzgado() + "/Mail:" + acta.getEmailJuzgado()+ ")";
    		   MailJuzgado = acta.getEmailJuzgado();
    		   JuzgadoNombre = acta.getJuzgado();
    	   } catch(Exception ex){}
    	   
    	   String sSeccional="";
    	   try
    	   { 
    		   sSeccional = acta.getSeccional();
    	   }catch(Exception ex){
    		   
    	   }

    	   dataToPrint.append("T 7 0 20 " + label.AddY(0) + " El pago de este CUPÓN CON DESCUENTO podrá efectuarse dentro").append("\r\n");
    	   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " de los TRES (3) días hábiles de labrada la presente, en el").append("\r\n");
    	   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " Banco Nación, Bolsa de Comercio o Montemar Compañia Financiera").append("\r\n");
    	   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " de la provincia de Mendoza.").append("\r\n");
    	   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " Para pagos fuera de la provincia de Mendoza deberá realizar un").append("\r\n");
		   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " Giro postal del Correo Argentino a nombre del Tesoro Habilitado").append("\r\n");
		   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " del Ministerio de Seguridad, Gobierno de Mendoza. Dicho Giro").append("\r\n");
		   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " deberá dirigirse al Ministerio de Seguridad División Tesorería").append("\r\n");
		   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " ,al domicilio de calle Salta 672 de Godoy Cruz CP 5501. Adjuntar").append("\r\n");
		   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " copia legible del acta de infracción vial a abonar.").append("\r\n");

    	   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " Para consultas, o una vez vencido el plazo de pago, comuniquese").append("\r\n");
    	   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " al ( N° TELEFONO ), o escríbanos al correo ").append("\r\n");
    	   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " ( INSERTAR CORREO ) .").append("\r\n");
    	   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " Recuerde que puede presentar su descargo por escrito dentro de ").append("\r\n");
    	   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " los CINCO (5) días hábiles de labrada el acta, ofreciendo ").append("\r\n");
    	   dataToPrint.append("T 7 0 20 " + label.AddY(30) + " las pruebas que considere oportunas en el ").append("\r\n");
    	   dataToPrint.append("PRINT\r\n");
    	   dataToPrint.append("! U1 SETBOLD 2 \r\n");
    	   //dataToPrint.append(" UNIDAD DE RESOLUCIONES VIALES " + JuzgadoNombre).append("\r\n");
		   dataToPrint.append(" " + JuzgadoNombre).append("\r\n");
    	   dataToPrint.append(" UBICADO EN " + DireccionJuzgado.toUpperCase() + " ").append("\r\n");
    	   dataToPrint.append(" " + LocalidadJuzgado.toUpperCase() + " - " + MailJuzgado + " ! U1 SETBOLD 0 \r\n").append("\r\n");
    	   dataToPrint.append(" ").append("\r\n");
    	   dataToPrint.append(" ").append("\r\n");
    	   

           //dataToPrint.append("T 7 0 20 " + label.AddY(30) + " Seccional " + sSeccional.toLowerCase() + " ").append("\r\n");

           //dataToPrint.append("T 7 0 20 " + label.AddY(30) + " " + data.getLatitud().toString() + " - " + data.getLongitud().toString() ).append("\r\n");
           //dataToPrint.append("L 0 " + label.AddY(40) + "  800 " + label.PosicionY + "  1").append("\r\n");
              
           return dataToPrint.toString();
               
		   
	   }

	   private static String generateActaParteSinCodigoBarraCPCL(ActaConstatacion data)
	   {

			ActaConstatacion acta = data;
			StringBuilder dataToPrint = new StringBuilder();

			//dataToPrint = new StringBuilder();
			LabelZebra label = new LabelZebra(180);//(330+30+30-90+30+150);
			dataToPrint.append("! 0 200 200 "  + label.LargoPagina + " 1").append("\r\n");
			dataToPrint.append("IN-DOTS").append("\r\n");
			dataToPrint.append("PW 800").append("\r\n");
			dataToPrint.append("LMARGIN 20").append("\r\n");

			Integer idJuzgado = 0;
			String DireccionJuzgado ="";
			String LocalidadJuzgado ="";
			String TelefonoJuzgado ="";
			String MailJuzgado="";
			String JuzgadoNombre = "";
			try
			{
				idJuzgado = acta.getIdJuzgado();

				DireccionJuzgado = acta.getCalleJuzgado() ;
				LocalidadJuzgado = acta.getLocalidadJuzgado() + " Mendoza";
				//MailJuzgado = "(tel. " + acta.getTelefonoJuzgado() + "/Mail:" + acta.getEmailJuzgado()+ ")";
				MailJuzgado = acta.getEmailJuzgado();
				JuzgadoNombre = acta.getJuzgado();
			} catch(Exception ex){}

			String sSeccional="";
			try
			{
				sSeccional = acta.getSeccional();
			}catch(Exception ex){

			}

			dataToPrint.append("T 7 0 20 " + label.AddY(0) + " Para acceder al beneficio del descuento por pago voluntario,").append("\r\n");
		    dataToPrint.append("T 7 0 20 " + label.AddY(30) + " deberá presentarse dentro de los TRES (3) días hábiles de").append("\r\n");
			dataToPrint.append("T 7 0 20 " + label.AddY(30) + " labrada la presente.").append("\r\n");
		    dataToPrint.append("T 7 0 20 " + label.AddY(30) + " Usted posee CINCO (5) días hábiles para presentar su descargo ").append("\r\n");
		    dataToPrint.append("T 7 0 20 " + label.AddY(30) + " por escrito, ofreciendo las pruebas que considere oportunas. ").append("\r\n");
			dataToPrint.append("T 7 0 20 " + label.AddY(30) + " En todos los casos, deberá concurrir a la: ").append("\r\n");
		    dataToPrint.append("PRINT\r\n");
			dataToPrint.append("! U1 SETBOLD 2 \r\n");
			dataToPrint.append(" " + JuzgadoNombre).append("\r\n");
			dataToPrint.append(" EN " + DireccionJuzgado.toUpperCase() + " ").append("\r\n");
			dataToPrint.append(" " + LocalidadJuzgado.toUpperCase() + " " + MailJuzgado + " ! U1 SETBOLD 0").append("\r\n");
			dataToPrint.append(" ").append("\r\n");
			dataToPrint.append(" ").append("\r\n");
		    dataToPrint.append(" ").append("\r\n");
		    dataToPrint.append(" ").append("\r\n");
		    dataToPrint.append(" ").append("\r\n");
		    dataToPrint.append(" ").append("\r\n");


			//dataToPrint.append("T 7 0 20 " + label.AddY(30) + " Seccional " + sSeccional.toLowerCase() + " ").append("\r\n");

			//dataToPrint.append("T 7 0 20 " + label.AddY(30) + " " + data.getLatitud().toString() + " - " + data.getLongitud().toString() ).append("\r\n");
			//dataToPrint.append("L 0 " + label.AddY(40) + "  800 " + label.PosicionY + "  1").append("\r\n");

			return dataToPrint.toString();

	   }

	   private static String generateActaParteCuponesFormatCPCL(ActaConstatacion data)
	   {
		   Object[] arrayOfObject = new Object[6];
	       
	       ActaConstatacion acta = data;
	       StringBuilder dataToPrint = new StringBuilder();
           dataToPrint = new StringBuilder();

           /*
           Double monto = acta.getMontoCupon();
           String smonto = Tools.DecimalFormat(monto);
           
           Double monto2Do = acta.getMonto2DoCupon();
           String smonto2Do = Tools.DecimalFormat(monto2Do);
           
           MOTNO  
                          70%       483
                          100
           */ 
           
           Double importeSancion = acta.getImporteSancion();//acta.getMontoCupon() * 100/70; // SUMAMOS EL 30% QUE SE HABIA DESCONTADO
           Double descuentoAplicadoSancion = acta.getDescuentoAplicadoSancion();
           String sDescuento = Tools.DecimalFormat(GlobalVar.getInstance().getSuportTable().getPorcDescuento(),"0");
           Double monto = acta.getMontoCupon();
           
           String sMonto = Tools.DecimalFormat(monto);
           String sImporteSancion = Tools.DecimalFormat(importeSancion);
           String sDescuentoAplicadoSancion= Tools.DecimalFormat(descuentoAplicadoSancion);
           
           //  fin obtener el monto del total de la infraccion sin dto //
           LabelZebra label = new LabelZebra(480 + 380 + 50  ); // borrar los ultimos 40 por que son solo para imprimer la sancion
           dataToPrint.append("! 0 200 200 " + label.LargoPagina + " 1").append("\r\n");
           dataToPrint.append("IN-DOTS").append("\r\n");
           dataToPrint.append("PW 800").append("\r\n");
           dataToPrint.append("LMARGIN 20").append("\r\n");
           dataToPrint.append("T 7 0 0 0 " + Tools.formatearIzquierda("-", 65, '-')).append("\r\n");


           dataToPrint.append("T 7 1 100 " + label.AddY(25) + " CUPÓN DE PAGO PARA EL INFRACTOR - (CON DESCUENTO)").append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(50) + " Referencia Nro.Acta: " + acta.getNumeroActa()).append("\r\n");
           dataToPrint.append("T 7 0 580 " + label.PosicionY + " Vto. " + Tools.DateValueOf(acta.getFechaVencimientoCupon(), "dd/MM/yyyy")).append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " N.Doc. " + acta.getNumeroDocumento()).append("\r\n");

           dataToPrint.append("T 7 0 20 " + label.AddY(50) + " Importe de la Sanción....................... $    ").append("\r\n");
           dataToPrint.append("T 7 0 650 " + label.PosicionY + " " + Tools.formatearIzquierda(sImporteSancion, 12, ' ')).append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Descuento Pago Voluntario " + sDescuento + "% .................... $ (-)").append("\r\n");
           dataToPrint.append("T 7 0 650 " + label.PosicionY + " " + Tools.formatearIzquierda(sDescuentoAplicadoSancion, 12, ' ')).append("\r\n");
           dataToPrint.append("T 7 0 200 " + label.AddY(25) + " " + Tools.formatearIzquierda("-", 50, '-')).append("\r\n");
           dataToPrint.append("T 7 1 200 " + label.AddY(25) + " IMPORTE A PAGAR                 $ ").append("\r\n");
           dataToPrint.append("T 7 1 650 " + label.PosicionY + " " + Tools.formatearIzquierda(sMonto, 12, ' ')).append("\r\n");
           
           dataToPrint.append("L 0 " + label.AddY(50)  + " 800 " + label.PosicionY + " 1").append("\r\n");
           
           dataToPrint.append("T 7 1 100 " + label.AddY(25) + " CUPÓN DE PAGO PARA EL BANCO - (CON DESCUENTO)").append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(50) + " Referencia Nro.Acta: " + acta.getNumeroActa()).append("\r\n");
           dataToPrint.append("T 7 0 580 " + label.PosicionY + " Vto. " + Tools.DateValueOf(acta.getFechaVencimientoCupon(), "dd/MM/yyyy")).append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " N.Doc. " + acta.getNumeroDocumento()).append("\r\n");

           dataToPrint.append("T 7 0 20 " + label.AddY(50) + "Importe de la Sanción....................... $    ").append("\r\n");
           dataToPrint.append("T 7 0 650 " + label.PosicionY + " " + Tools.formatearIzquierda(sImporteSancion, 12, ' ')).append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Descuento Pago Voluntario " + sDescuento + "% .................... $ (-)").append("\r\n");
           dataToPrint.append("T 7 0 650 " + label.PosicionY + " " + Tools.formatearIzquierda(sDescuentoAplicadoSancion, 12, ' ')).append("\r\n");
           dataToPrint.append("T 7 0 200 " + label.AddY(25) + " " + Tools.formatearIzquierda("-", 50, '-')).append("\r\n");
           dataToPrint.append("T 7 1 200 " + label.AddY(25) + " IMPORTE A PAGAR                 $ ").append("\r\n");
           dataToPrint.append("T 7 1 650 " + label.PosicionY + " " + Tools.formatearIzquierda(sMonto, 12, ' ')).append("\r\n");
           
           String codigoBarra1 = acta.getCodigoBarra();
           dataToPrint.append("B 39 1 0 80 105 " + label.AddY(80) + " " + codigoBarra1).append("\r\n");
           //dataToPrint.append("B I2OF5 1 0 80 105 " + label.AddY(80) + " " + codigoBarra1).append("\r\n");
           dataToPrint.append("T 7 0 200 " + label.AddY(90) + " *" + codigoBarra1).append("*\r\n");

           String codigoBarra2 = acta.getCodigoBarra2Do();
           dataToPrint.append("B 39 1 0 80 105 " + label.AddY(48) + " " + codigoBarra2).append("\r\n");
           dataToPrint.append("T 7 0 200 " + label.AddY(90) + " *" + codigoBarra2).append("*\r\n");
           dataToPrint.append("L 0 " + label.AddY(50)  + " 800 " + label.PosicionY + " 1").append("\r\n");
           //dataToPrint.append("PRINT").append("\r\n");
           //dataToPrint.append("T 7 1 200 " + label.AddY(25) + " IMPORTE Sancion $ " +  Tools.DecimalFormat(acta.getImporteSancion())).append("\r\n");
           //dataToPrint.append("T 7 1 200 " + label.AddY(25) + " Descuento Aplicado a la Sancion $ " +  Tools.DecimalFormat(acta.getDescuentoAplicadoSancion())).append("\r\n");
           return dataToPrint.toString();

	   }

	   
	   private static String generateActaParte1FormatCPCL(ActaConstatacion data)
	   {
	       Object[] arrayOfObject = new Object[6];
	       
	       ActaConstatacion acta = data;
	       
	     //BEGIN
	       LabelZebra label = new LabelZebra(1050);
           StringBuilder dataToPrint = new StringBuilder();
           dataToPrint.append("! 0 200 200 " + label.LargoPagina + " 1\r\n")
			  .append("IN-DOTS\r\n")
			  .append("PW 800\r\n")
			  .append("LMARGIN 20\r\n")		//
			  .append("T 7 0 20 " + label.AddY(0) + " ACTA ÚNICA DE INFRACCIÓN Y EMPLAZAMIENTO \r\n")
			  .append("T 7 0 20 " + label.AddY(22) + "  REGIMEN DE TRÁNSITO Y SEGURIDAD VIAL\r\n")
			  .append("T 7 0 90 145 *" + acta.getNumeroActa()).append("*\r\n")
			  .append("T 7 0 20 44 DE LA PROVINCIA DEL Mendoza LEY Ley 4488/98\r\n")
			  .append("T 7 0 20 66 DTOS 146/11 - 877/12 - 2472/12 Resol. 351/11\r\n")
		//.append("B I2OF5 1 1 50 545 0 " + acta.getNumeroActa().toString()).append("\r\n");
			  .append("B 39 1 0 50 50 90 " + acta.getNumeroActa()).append("\r\n");
			  
            
//            if (acta.getIdTipoRuta().equals("3"))
//            	 dataToPrint.append("T 7 0 20 120 Lugar de Constatacion: " + acta.getTipoRuta() + " " + acta.getUbicacion() + " " + acta.getNumeroRuta() + " Altura : " + acta.getKm() + "  Fecha: " + Tools.DateValueOf(acta.getFechaHoraLabrado(),"dd/MM/yyyy HH:mm")).append("\r\n");
//            else
//            	 dataToPrint.append("T 7 0 20 120 Lugar de Constatacion: " + acta.getTipoRuta() + " Nro.Ruta: " + acta.getNumeroRuta() + " Km: " + acta.getKm() + "  Fecha: " + Tools.DateValueOf(acta.getFechaHoraLabrado(),"dd/MM/yyyy HH:mm")).append("\r\n");
            label.PosicionY = 180;
            dataToPrint.append("L 0 " + label.PosicionY + " 800 " + label.PosicionY + " 1").append("\r\n"); 
            dataToPrint.append("T 7 0 20 " + label.AddY(15) + " Fecha y Hora : " + Tools.DateValueOf(acta.getFechaHoraLabrado(),"dd/MM/yyyy HH:mm")).append("\r\n");
            dataToPrint.append("T 7 0 20 " + label.AddY(24) + " LUGAR DE CONSTATACION ").append("\r\n");
            dataToPrint.append("T 7 0 20 " + label.AddY(24) + " " + acta.getTipoRuta() + " " + acta.getNumeroRuta() +  " " + acta.getDescripcionUbicacion()  + "").append("\r\n");
            dataToPrint.append("T 7 0 20 " + label.AddY(24) + " Altura/KM " + acta.getKm()  + "").append("\r\n");
            
            dataToPrint.append("L 0 " + label.AddY(24) + " 800 " + label.PosicionY + " 1").append("\r\n");
           //Licencia
            
           String strUnicaProv = acta.getLicenciaUnicaProvincial() == "S" ? "Si" : "No";
           String strRetenida = acta.getLicenciaRetendia() == "S" ? "Si" : "No";

           String numeroLicencia = acta.getNumeroLicencia()==null?"":acta.getNumeroLicencia();
           if (numeroLicencia.equals("")) { strUnicaProv =""; strRetenida="";}

           String claseLicencia = acta.getClaseLicencia()==null?"":acta.getClaseLicencia();
           String sFechaVencimientoLicencia = acta.getFechaVencimientoLicencia()==null?"":Tools.DateValueOf(acta.getFechaVencimientoLicencia(),"dd/MM/yy");
           if (numeroLicencia.equals("")) sFechaVencimientoLicencia ="";
           
           dataToPrint.append("T 7 0 20 " + label.AddY(15) + " LICENCIA ").append("\r\n");
           //dataToPrint.append("T 7 0 20 "  + label.AddY(25) + " Cod.Seg.Lic:" + numeroLicencia + " U.Prov:" + strUnicaProv + " Clase:" + claseLicencia + " Vto:" + sFechaVencimientoLicencia ).append("\r\n");
           dataToPrint.append("T 7 0 20 "  + label.AddY(25) + " Licencia Nº:" + numeroLicencia + " U.Prov:" + strUnicaProv + " Clase:" + claseLicencia + " Vto:" + sFechaVencimientoLicencia ).append("\r\n");
           dataToPrint.append("T 7 0 20 "  + label.AddY(25) + " Licencia Retenida:"  + strRetenida).append("\r\n"); 											
           //PaisActual																//strProvLic

           String paisLicencia = acta.getPaisLicencia()==null?"":acta.getPaisLicencia();
           String provinciaLicencia= acta.getProvinciaLicencia()==null?"":acta.getProvinciaLicencia();
           
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " País: " + Tools.formatearDerecha(paisLicencia, 25, ' ') + "  Provincia: " + Tools.formatearDerecha(provinciaLicencia, 25, ' ')).append("\r\n");
           
           String departamentoLicencia = acta.getDepartamentoLicencia()==null?"":acta.getDepartamentoLicencia();
           String localidadLicencia = acta.getLocalidadLicencia()==null?"":acta.getLocalidadLicencia();
           //dptoLicencia																		//localidadActual	
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Dpto.: " + Tools.formatearDerecha(departamentoLicencia, 25, ' ') + "  Localidad: " + Tools.formatearDerecha(localidadLicencia, 25, ' ')).append("\r\n");
           dataToPrint.append("L 0 " + label.AddY(30) + " 800 " + label.PosicionY + " 1").append("\r\n");

           
           dataToPrint.append("T 7 0 20 " + label.AddY(15) + " CONDUCTOR").append("\r\n");
           //conductor																			//strTipDoc							//sexoPrint // ver luego de cambiar 01 02
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Nro.Doc: " + acta.getNumeroDocumento() + " Tipo: " + acta.getTipoDocumento()  ).append("\r\n");// + " Sexo: " + acta.getSexo()).append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Apellido: " + Tools.formatearDerecha(acta.getApellido(), 25, ' ')).append("\r\n");
           dataToPrint.append("T 7 0 400 "  + label.PosicionY  + " Nombre: " + Tools.formatearDerecha(acta.getNombre(), 25, ' ')).append("\r\n");
           String sFechaVencimientoNacimiento = acta.getFechaNacimiento()==null?"":Tools.DateValueOf(acta.getFechaNacimiento(),"dd/MM/yy");
           dataToPrint.append("T 7 0 20 "  + label.AddY(25)  + " Fecha Nacimiento: " + sFechaVencimientoNacimiento).append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Domicilio: " + Tools.formatearDerecha(acta.getCalle(), 25, ' ').trim() + " Nro:" + Tools.formatearDerecha(acta.getAltura(), 5, ' ') + " Piso:" + Tools.formatearDerecha(acta.getPiso(), 3, ' ') + " Dpto:" + Tools.formatearDerecha(acta.getDepartamento(), 5, ' ')).append("\r\n");
           																	//strPaisConductor

           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " País: " + Tools.formatearDerecha(acta.getPaisDomicilio(), 25, ' ')).append("\r\n");
           																	//strProvConductor		
           dataToPrint.append("T 7 0 400 " + label.PosicionY + " Prov: " + Tools.formatearDerecha(acta.getProvinciaDomicilio(), 25, ' ')).append("\r\n");
           																//dptoConductor
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Dpto: " + Tools.formatearDerecha(acta.getDepartamentoDomicilio(), 25, ' ')).append("\r\n");
           																	//localidadCond
           dataToPrint.append("T 7 0 400 " + label.PosicionY + " Loc: " + Tools.formatearDerecha(acta.getLocalidadDomicilio(), 25, ' ')).append("\r\n");
           
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Barrio: " + Tools.formatearDerecha(acta.getBarrio(), 25, ' ')).append("\r\n");
           
           dataToPrint.append("T 7 0 460 " +  label.PosicionY + " C.P. " + Tools.formatearDerecha(acta.getCodigoPostal(), 15, ' ')).append("\r\n");

           //Dominio
           
           
           String strMarca = acta.getMarca();//acta.Id_Marca.Equals(-1) || acta.Id_Marca.Equals(100) ? acta.MarcaNueva : marca.N_Marca;
           String strColor = acta.getColor();//acta.Id_color.Equals(-1) ? acta.ColorNuevo : color.N_color;
           dataToPrint.append("L 0 " + label.AddY(30) + " 800 " + label.PosicionY + " 1").append("\r\n"); //add 15 luego
           dataToPrint.append("T 7 0 20 " + label.AddY(15) + " VEHICULO").append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Dominio: " + Tools.formatearDerecha(acta.getDominio(), 15, ' ') + "Tipo: " +  acta.getIdTipoVehiculo()).append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Marca: " + Tools.formatearDerecha(strMarca, 15, ' ') + " Modelo: " + acta.getModeloVehiculo()).append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Titular: " + acta.getApellidoTitular() + ", " + acta.getNombreTitular()).append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Nro.Doc: " + acta.getNumeroDocumentoTitular() + " Tipo: " + acta.getTipoDocumentoTitular()).append("\r\n");// + " Sexo: " + (acta.getSexoTitular()==null?"":acta.getSexoTitular())).append("\r\n");           
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Domicilio: " + Tools.formatearDerecha(acta.getCalleTitular(), 25, ' ').trim() + " Nro:" + Tools.formatearDerecha(acta.getAlturaTitular(), 5, ' ') + " Piso:" + Tools.formatearDerecha(acta.getPisoTitular(), 3, ' ') + " Dpto:" + Tools.formatearDerecha(acta.getDepartamentoTitular(), 5, ' ')).append("\r\n");
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " País: " + Tools.formatearDerecha(acta.getPaisDomicilioTitular(), 25, ' ')).append("\r\n");
				//strProvConductor		
           dataToPrint.append("T 7 0 400 " + label.PosicionY + " Prov: " + Tools.formatearDerecha(acta.getProvinciaDomicilioTitular(), 25, ' ')).append("\r\n");
			//dptoConductor
           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Dpto: " + Tools.formatearDerecha(acta.getDepartamentoDomicilioTitular(), 25, ' ')).append("\r\n");
				//localidadCond
           dataToPrint.append("T 7 0 400 " + label.PosicionY + " Loc: " + Tools.formatearDerecha(acta.getLocalidadDomicilioTitular(), 25, ' ')).append("\r\n");

           dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Cod Postal: " + acta.getCodigoPostal()).append("\r\n");

           dataToPrint.append("L 0 " + label.AddY(30) + " 800 " + label.PosicionY + " 1").append("\r\n");

           //Infracciones
           /*
           InfraccionNomencladaServices infServices = new InfraccionNomencladaServices();

           InfraccionNomenclada objInf1;
           if (acta.IdInfraccion1 != null && acta.IdInfraccion1.Trim().toString() != "")
               objInf1 = infServices.getInfraccionNomencladaByID(acta.IdInfraccion1);//infServices.getInfraccionNomenclada(acta.CodigoInfraccion1);
           else
               objInf1 = infServices.getInfraccionNomenclada(acta.CodigoInfraccion1);

           InfraccionNomenclada objInf2;
           if (acta.IdInfraccion2 != null && acta.IdInfraccion2.Trim().toString() != "")
               objInf2 = infServices.getInfraccionNomencladaByID(acta.IdInfraccion2);
           else
               objInf2 = infServices.getInfraccionNomenclada(acta.CodigoInfraccion2);
           */
           //AREA INFRACCIONES
           
           dataToPrint.append("T 7 0 20 " + label.AddY(10) + " INFRACCION ").append("\r\n");
           
           if (acta.getCodigoInfraccion1()!=null && !acta.getCodigoInfraccion1().equals(""))
           {
        	    String sArticulo = acta.getoInfraccionNomenclada1().getArticulo();
        	    String sInciso = acta.getoInfraccionNomenclada1().getInciso();
                dataToPrint.append("T 7 0 20 " + label.AddY(24) + " Articulo Nº " +  (sArticulo==null?"":sArticulo) + " Inciso Nº " + (sInciso==null?"":sInciso) ).append("\r\n");
        	    String sDescripcion =  Tools.formatearDerecha(acta.getDescripcionInfraccion1(), 100, '.');
     	     	String sDescripcionParte1 = sDescripcion.substring(0, 48);
     	     	String sDescripcionParte2 = sDescripcion.substring(48);
              /*if(objInf1.ImprimirPuntos.Equals("S"))*/
     	     	if(acta.getImprimirPuntos1()!=null && acta.getImprimirPuntos1().equals("S"))
                   dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Cod:" + acta.getCodigoInfraccion1() + "(Pts:" + acta.getPuntos1().toString() + ")" + sDescripcionParte1 ).append("\r\n");
                else																								//objInf1.DescripcionCorta		
                  dataToPrint.append("T 7 0 20 " + label.AddY(25) + " Cod:"  + acta.getCodigoInfraccion1() + " " + sDescripcionParte1).append("\r\n");
                 														//objInf1.DescripcionCorta
               dataToPrint.append("T 7 0 20 " + label.AddY(25) + "  " + sDescripcionParte2).append("\r\n");
               
               
           }
           /*
           if (acta.getCodigoInfraccion2()!=null && !acta.getCodigoInfraccion2().equals(""))
           {
       	    String sDescripcion =  Tools.formatearDerecha(acta.getDescripcionInfraccion2(), 100, '.');
    	     	String sDescripcionParte1 = sDescripcion.substring(0, 49);
    	     	String sDescripcionParte2 = sDescripcion.substring(49,sDescripcion.length());	
              
     	     	if(acta.getImprimirPuntos2()!=null && acta.getImprimirPuntos2().equals("S"))
                   dataToPrint.append("T 7 0 20 530 Cod:" + acta.getCodigoInfraccion2() + "(Pts:" + acta.getPuntos2().toString() + ")" + sDescripcionParte1 ).append("\r\n");
               else																								//objInf2.DescripcionCorta
                   dataToPrint.append("T 7 0 20 530 Cod:" + acta.getCodigoInfraccion2() + " " + sDescripcionParte1).append("\r\n");
                   														//objInf2.DescripcionCorta								//47
               dataToPrint.append("T 7 0 20 555 " + sDescripcionParte2).append("\r\n");
           }*/

           dataToPrint.append("T 7 0 20 " + label.AddY(20) + "  Observaciones: \r\n");
          // dataToPrint.append("PRINT\r\n");
           
	       
				//String temp = String.format("! 0 200 200 930 1\r\nPW 384\r\nTONE 0\r\nSPEED 2\r\nON-FEED IGNORE\r\nNO-PACE\r\nPOSTFEED 152\r\nJOURNAL\r\nBOX 11 6 376 797 8\r\nT 5 1 85 27 Traffic Ticket Demo\r\nT 5 1 75 89 %s\r\nT 5 0 35 522 Priors:\r\nT 5 0 35 421 Address:\r\nT 5 0 35 342 Last Name:\r\nT 5 0 35 257 First Name:\r\nT 5 0 35 175 Plate #:\r\nT 5 0 64 210 %s\r\nT 5 0 64 290 %s\r\nT 5 0 64 374 %s\r\nT 5 0 64 466 %s\r\nT 5 0 64 558 %s\r\nB PDF-417 54 639 XD 3 YD 4 C 1 S 0\r\nZebra Technologies makes printing from Smart Phones easy!\r\nENDPDF\r\nBOX 11 789 376 920 8", arrayOfObject);
	       String temp = dataToPrint.toString();//String.format("",arrayOfObject);  
	     return temp;
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

	   private  void printDocumentoCPCL(Bitmap sig)
			     throws ConnectionException
			   {
				     int xPos = 10;
				     int yPos = 744 + 25;
				     int width = 790;
				     int height = 440;
				     
				     printer.printImage(new ZebraImageAndroid(sig), xPos, yPos, width, height, true);
			     
			   }

	   

	   private  void printEscudoCPCL(Bitmap sig)
	     throws ConnectionException
	   {
		   int xPos = 1;//100;
		     int yPos = 10;
		     int width = 150;//170;//790;
		     int height = 120;//140;//120;//440;
		     
		     printer.printImage(new ZebraImageAndroid(sig), xPos, yPos, width, height, true);
	     
	   }
	   private  void printEscudoPoliciaCPCL(Bitmap sig)
			     throws ConnectionException
			   {
				   int xPos = 550;
				     int yPos = 25;
				     int width = 170;//790;
				     int height = 120;//440;
				     
				     printer.printImage(new ZebraImageAndroid(sig), xPos, yPos, width, height, true);
			     
			   }
	   private  void printLicenciaCPCL(Bitmap sig)
	     throws ConnectionException
	   {
		   int xPos = 10;
		     int yPos = 160 +25;
		     int width = 790;
		     int height = 440;
		     
		     printer.printImage(new ZebraImageAndroid(sig), xPos, yPos, width, height, true);
	     
	   }
	   
		private static int getPrintWidth(ZebraPrinter printer)
		{
		  int printWidth = 832;
		  try
		  {
		    PrinterLanguage pl = printer.getPrinterControlLanguage();
		    if (pl == PrinterLanguage.CPCL)
		    {
		    Connection connection =	printer.getConnection();
		    
		      int sensedWidth  = Integer.parseInt(SGD.GET("media.width_sense.in_dots", connection));
		      if (sensedWidth > 0) 
		      {
		        printWidth = sensedWidth;
		      }
		      if (printWidth > 832) 
		      {
		    	  printWidth = 832;
		      }
		    }
		    return printWidth;
		  }
		  catch (Exception e) {
			  return 832;  
		  }
		  
		}
	   private static String trafficTicketFormatZPL()
	   {
	     return ZPLUtilities.decorateWithFormatPrefix("^XA\r\n^FO32,0^GFA,02048,02048,00016,:Z64:\r\neJzt1E9r1EAUAPCEKZ0elh3Fi4eYWfCuKT2Yw5BBEDz5HYoFPYltD0U03aymsB6kuXoo+kFUmrjQXEr7DWxqxD3Vje6hkY4ZJ6WZmVQQPAp9t19e3ps/zIxhXMT/FIjrURi0ZfaHg5arU/+Kccv5oJ0fmuOWbTTR/clGaaD5PaF7mivi0919zT6jr98qM58FzTRrb5WMGlp/wphnnjNQFu36Xaj8zWfeLFLusD6GBy13USTtW4x2MNJcYcvWHXRdovr3a7tq/L5NLWc+V/1tSlYXpsqoS1ZvTbelo03y4+aJcvqGLPWAWn9uu7nuqeU/NGaxMvHzQVf5mIh8R/mr5y+Fcx+kd4jwZUt6z/OujwCR3vW8BeGg8aZHF9KuK92h9Ipui+JwsuXKegvj56m1rPI4Sibu6ljuF07jsfP5bmMPjYrJ8k4o8ygpxisbpszDjD2+35G2YeE/WLQMmQclvZG5jX17nuHtRUfmbZchnhXS2OoD/j2TRjYVvePGAcJIHPeBqQxiYSCNLmXif+koeumIiwYbD6NXopbhxmEUNafj1KM0Df/mQ/Hhyx3O0zMXeb5fzzZsfJgf1AZnLlemXM8zRxy2mAfwzJXzU0y/RC/QjAvq+3L1Ca9ABgxgOGbtOcIZzEZhOOgltTdsXqLkECSAJ/X+xIgXODbDESiNyliffhTbSWMTJDAT9U/fZVAMl/BwNIyzyli7VyCxm7cTE8BE1K8dlVg4AwMwzHrC1xgVXoYzEBa1j6qAa+tfe8RbfnbCue7147ZpdM46xft2/j28iH+L3/3Pgac=:6881\r\n^FO15,10^GB591,390,8^FS\r\n^FT170,49^A0N,34,33^FH\\^FDTraffic Ticket Demo^FS\r\n^FT307,150^A0N,28,28^FH\\^FDCitation:^FS\r\n^FT173,94^A0N,28,28^FH\\^FDPriors:^FS\r\n^FT37,314^A0N,28,28^FH\\^FDAddress:^FS\r\n^FT312,230^A0N,28,28^FH\\^FDLast Name:^FS\r\n^FT37,230^A0N,28,28^FH\\^FDFirst Name:^FS\r\n^FT37,151^A0N,29,28^FH\\^FDPlate #:^FS\r\n^BY2,4^FT379,382^B7N,4,0,,,N^FH\\^FDZebra Technologies makes printing from Smart Phones easy!^FS\r\n^FT49,359^A0N,28,28^FH\\^FN16\"Address\"^FS\r\n^FT318,272^A0N,28,28^FH\\^FN15\"Last Name\"^FS\r\n^FT49,275^A0N,28,28^FH\\^FN14\"First Name\"^FS\r\n^FT318,187^A0N,28,28^FH\\^FN13\"Citation\"^FS\r\n^FT49,190^A0N,28,28^FH\\^FN12\"Plate\"^FS\r\n^FT260,94^A0N,28,28^FH\\^FN11\"Priors\"^FS\r\n^FO15,392^GB591,120,8^FS");
	   }
	 
	    }
