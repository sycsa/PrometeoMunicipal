package ar.gov.mendoza.PrometeoMuni.screens.actas;

import android.graphics.Bitmap;

import ar.gov.mendoza.PrometeoMuni.core.domain.ActaConstatacion;
import ar.gov.mendoza.PrometeoMuni.core.domain.InformeActa;
import ar.gov.mendoza.PrometeoMuni.core.util.Tools;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalVar;
import ar.gov.mendoza.PrometeoMuni.utils.StringBuilderForPrint;

import com.zebra.android.zebrautilitiesmza.screens.trafficticket.TrafficTicketViolatorData;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.graphics.internal.ZebraImageAndroid;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.SGD;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.util.internal.ZPLUtilities;


public class PrintInformeActaHelper
{

	   private static final int DEFAULT_PRINT_WIDTH = 832;
	   private Connection Connection;
	   private ZebraPrinter printer;
	   
	   public PrintInformeActaHelper(Connection Connection, ZebraPrinter printer)
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
	   public  void printCreateInformeActaCPCL(String pFechaDesde, String pFechaHasta,InformeActa[] data)
			     throws ConnectionException
			   {
			   
			    	 try
			    	 {
                     Thread.sleep(2000,0);
			    	 } catch(InterruptedException ex)
			    	 {}
			    	 // revisar la parte de las infracciones longitud
			    	 this.sendCommand(generateInformeActaFormatCPCL(pFechaDesde,pFechaHasta,data));
			    	 this.sendCommand("PRINT\r\n");
			    	 /*
			    	 this.sendCommand(generateActaParte2FormatCPCL(data));
			    	 this.sendCommand("PRINT\r\n");*/
			    	 
			    	 
				     
			    	 
	
		}
	   private static String generateInformeActaFormatCPCL(String pFechaDesde,String pFechaHasta, InformeActa[] listaDeInformes)
	   {
	       Object[] arrayOfObject = new Object[6];
	       
	       InformeActa[] acta = listaDeInformes;
	       
	       int xalto = 230 + listaDeInformes.length * 35;
	       
	       

           StringBuilderForPrint dataToPrint = new StringBuilderForPrint();
           dataToPrint.append("! 0 200 200 " + xalto + " 1")
           			  .append("IN-DOTS")
           			  .append("PW 800")
           			  .append("LMARGIN 20")		//
           			  .append("T 7 1 20 0 PROVINCIA  DE  MENDOZA                  ")
           			  .append("T 7 1 20 33 Ministerio de Seguridad                  ")
           			  .append("T 7 0 20 110 Detalle de actas labradas.Del " + pFechaDesde + " a " + pFechaHasta)
            		  .append("T 7 0 20 140  Usuario       Fec. y hora         Nro refer.         Estado");

            Integer espacioLinea = 170;
           Integer espacioTexto = 150;
            InformeActa informeActa = null;
            
            for (int i = 0; i < listaDeInformes.length; i++)
            {
                informeActa = new InformeActa();
                informeActa = listaDeInformes[i];

                espacioTexto += 20;
                //dataToPrint.Append("T 7 0 20 " + espacioTexto.ToString() + informeActa.Usuario.PadRight(10) + "   " + informeActa.FechaYhora.ToString("dd/MM/yyyy HH:mm") + "   " + informeActa.NroReferencia + "     " + informeActa.Estado).Append("\r\n");
                dataToPrint.append("T 7 0 20 " + espacioTexto.toString() + informeActa.getUsuario() + "   " + informeActa.getFechayHoraString() + "   " + informeActa.getNumeroReferencia() + "     " + informeActa.getEstado());
                espacioLinea += 40;
            }
            
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
	       
	       // p_PrinterController.Print(dataToPrint.toString(), false);

           dataToPrint = new StringBuilder();


           //if (Global.GlobalFunc.esCodigoBarraKolektor(acta.Cod_barra))
           {
               dataToPrint.append("! 0 200 200 500 1").append("\r\n");
           }
           /*else
           {
               dataToPrint.append("! 0 200 200 380 1").append("\r\n");
           }*/

           String monto_infracc_sin_dto = Tools.DecimalFormat(acta.getMontoCupon() * 2);//.toString("0.00")
           String monto_dto = Tools.DecimalFormat(acta.getMontoCupon(),"0.00");
           String sPorcentajeDescuento =  Tools.DecimalFormat(GlobalVar.getInstance().getSuportTable().getPorcDescuento(),"0");
           
           dataToPrint.append("IN-DOTS").append("\r\n");
           dataToPrint.append("PW 800").append("\r\n");
           dataToPrint.append("LMARGIN 20").append("\r\n");
           dataToPrint.append("T 7 0 0 0 " + Tools.formatearIzquierda("-", 65, '-')).append("\r\n");

           dataToPrint.append("T 7 1 100 25 CUPÓN DE PAGO PARA EL BANCO - (CON DESCUENTO)").append("\r\n");
           dataToPrint.append("T 7 0 20 75 Referencia Nro.Acta: " + acta.getNumeroActa()).append("\r\n");
           dataToPrint.append("T 7 0 580 75 Vto. " + Tools.DateValueOf(acta.getFechaVencimientoCupon(),"dd/MM/yyyy") ).append("\r\n");
           dataToPrint.append("T 7 0 20 100 N.Doc. " + acta.getNumeroDocumento()).append("\r\n");

           dataToPrint.append("T 7 0 20 150 Importe de la Sanción....................... $    ").append("\r\n");
           dataToPrint.append("T 7 0 650 150 " + Tools.formatearIzquierda(monto_infracc_sin_dto, 12, ' ')).append("\r\n");
           dataToPrint.append("T 7 0 20 175 Descuento Pago Voluntario " + sPorcentajeDescuento + "% .................... $ (-)").append("\r\n");
           dataToPrint.append("T 7 0 650 175 " + Tools.formatearIzquierda(monto_dto, 12, ' ')).append("\r\n");
           dataToPrint.append("T 7 0 650 175 " + Tools.formatearIzquierda(monto_dto, 12, ' ')).append("\r\n");
           dataToPrint.append("T 7 1 200 225 IMPORTE A PAGAR                 $ ").append("\r\n");
           dataToPrint.append("T 7 1 650 225 " + Tools.formatearIzquierda(monto_dto, 12, ' ')).append("\r\n");

           //if (Global.GlobalFunc.esCodigoBarraKolektor(acta.Cod_barra))
           {   //{command} {type} {width} {ratio} {height} {x} {y} {data}
               dataToPrint.append("B I2OF5 1 1 50 75 280 " + acta.getCodigoBarra()).append("\r\n");
               dataToPrint.append("T 7 0 125 340 " + acta.getCodigoBarra()).append("\r\n");
               dataToPrint.append("B I2OF5 1 1 50 75 380 " + acta.getCodigoBarra2Do()).append("\r\n");
               dataToPrint.append("T 7 0 125 440 " + acta.getCodigoBarra2Do()).append("\r\n");
               dataToPrint.append("L 0 560 800 560 1").append("\r\n");
           }
           /*else
           {
               dataToPrint.append("B I2OF5 1 1 50 25 280 " + acta.Cod_barra).append("\r\n");
               dataToPrint.append("T 7 0 100 340 " + acta.Cod_barra).append("\r\n");
           }
           */
          /* dataToPrint.append("PRINT").append("\r\n");
           p_PrinterController.Print(dataToPrint.toString(),false);
           dataToPrint = new StringBuilder(); 
	       */
	       
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
           Double monto_infracc_sin_dto = acta.getMontoCupon() * 2;
           Double monto_dto = acta.getMontoCupon();
           String smonto_dto = Tools.DecimalFormat(monto_dto);
           String sDescuento = Tools.DecimalFormat(GlobalVar.getInstance().getSuportTable().getPorcDescuento(),"0");
           //  fin obtener el monto del total de la infraccion sin dto //

           dataToPrint.append("! 0 200 200 265 1").append("\r\n");
           dataToPrint.append("IN-DOTS").append("\r\n");
           dataToPrint.append("PW 800").append("\r\n");
           dataToPrint.append("LMARGIN 20").append("\r\n");
           dataToPrint.append("T 7 0 0 0 " + Tools.formatearIzquierda("-", 65, '-')).append("\r\n");

           dataToPrint.append("T 7 1 100 25 CUPÓN DE PAGO PARA EL INFRACTOR - (CON DESCUENTO)").append("\r\n");
           dataToPrint.append("T 7 0 20 75 Referencia Nro.Acta: " + acta.getNumeroActa()).append("\r\n");
           dataToPrint.append("T 7 0 580 75 Vto. " + Tools.DateValueOf(acta.getFechaVencimientoCupon(), "dd/MM/yyyy")).append("\r\n");
           dataToPrint.append("T 7 0 20 100 N.Doc. " + acta.getNumeroDocumento()).append("\r\n");

           dataToPrint.append("T 7 0 20 150 Importe de la Sanción....................... $    ").append("\r\n");
           dataToPrint.append("T 7 0 650 150 " + Tools.formatearIzquierda(Tools.DecimalFormat(monto_infracc_sin_dto), 12, ' ')).append("\r\n");
           dataToPrint.append("T 7 0 20 175 Descuento Pago Voluntario " + sDescuento + "% .................... $ (-)").append("\r\n");
           dataToPrint.append("T 7 0 650 175 " + Tools.formatearIzquierda(smonto_dto, 12, ' ')).append("\r\n");
           dataToPrint.append("T 7 0 200 200 " + Tools.formatearIzquierda("-", 50, '-')).append("\r\n");
           dataToPrint.append("T 7 1 200 225 IMPORTE A PAGAR                 $ ").append("\r\n");
           dataToPrint.append("T 7 1 650 225 " + Tools.formatearIzquierda(smonto_dto, 12, ' ')).append("\r\n");
           //dataToPrint.append("PRINT").append("\r\n");
           
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
               dataToPrint.append("! 0 200 200 510 1").append("\r\n");
               dataToPrint.append("IN-DOTS").append("\r\n");
               dataToPrint.append("PW 800").append("\r\n");
               dataToPrint.append("LMARGIN 20").append("\r\n");
              // if (Global.GlobalFunc.esCodigoBarraKolektor(acta.Cod_barra))
               {

            	   dataToPrint.append("T 7 0 20 0 El pago del presente CUPÓN CON DESCUENTO podra efectuarse en").append("\r\n");
                   //dataToPrint.append("T 7 0 20 30 el Bco. de , Rapipago o Cobro Express.").append("\r\n");
            	   dataToPrint.append("T 7 0 20 30 el Bco. de , Rapipago o Cobro Express.").append("\r\n");
                   dataToPrint.append("T 7 0 20 60 Recuerde que puede presentar su descargo escrito dentro de ").append("\r\n");

                   dataToPrint.append("T 7 0 20 90 los 10 días habiles de labrada la presente acta, ofreciendo").append("\r\n");
                   dataToPrint.append("T 7 0 20 120 las pruebas  que considere oportunas en el juzgado de ").append("\r\n");
                   dataToPrint.append("T 7 0 20 150 faltas sito en:").append("\r\n");
                   dataToPrint.append("T 7 0 20 195 Domicilio: " + acta.getCalleJuzgado() + " " + Tools.formatearDerecha(acta.getAlturaJuzgado(), 6, ' ') + " C.P.:" + Tools.formatearDerecha(acta.getCodigoPostalJuzgado(), 6, ' ')).append("\r\n");
                   //dataToPrint.append("T 7 0 20 225 Loc.: " + Tools.formatearDerecha(acta.getLocalidadJuzgado(), 25, ' ') + " PROVINCIA DE ").append("\r\n");
                   dataToPrint.append("T 7 0 20 225 Loc.: " + Tools.formatearDerecha(acta.getLocalidadJuzgado(), 25, ' ') + " PROVINCIA DE MENDOZA").append("\r\n");

                   dataToPrint.append("T 7 0 20 265 Una vez vencido el plazo de pago, deberá solicitar un nuevo").append("\r\n");

                   dataToPrint.append("T 7 0 20 295 cupón  en los Juzgados de Falta, en cualquier delegación  ").append("\r\n");

                   dataToPrint.append("T 7 0 20 325 de la DGR de Mdza. o a través de www.dgrmdza.gov.ar").append("\r\n");

                   dataToPrint.append("T 7 0 20 355 Ante el incumplimiento del pago en tiempo y forma se iniciarón").append("\r\n");
                   dataToPrint.append("T 7 0 20 385 las correspondientes acciones legales, sometiendose las partes").append("\r\n");

                   dataToPrint.append("T 7 0 20 415 a la juridicción de los Tribunales Ordinarios de ,").append("\r\n");

                   dataToPrint.append("T 7 0 20 445 renunciando expresamente a cualquier fuero de excepción que").append("\r\n");
                   dataToPrint.append("T 7 0 20 475 pudiera corresponder.").append("\r\n");

               }

               return dataToPrint.toString();
               
		   
	   }
	   private static String generateActaParte2FormatCPCL(ActaConstatacion data)
	   {
	       Object[] arrayOfObject = new Object[6];
	       
	       ActaConstatacion acta = data;
	       StringBuilder dataToPrint = new StringBuilder();
	      
           dataToPrint.append("! U1 SETLP 7 0 10 " + acta.getObservaciones() + " \r \n");
           
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


           dataToPrint.append("! 0 200 200 348 1").append("\r\n");
           dataToPrint.append("IN-DOTS").append("\r\n");
           dataToPrint.append("PW 800").append("\r\n");
           dataToPrint.append("LMARGIN 20").append("\r\n");
           dataToPrint.append("T 7 0 20 0 IMPORTANTE: Queda Ud. debidamente NOTIFICADO de la presenta ACTA.").append("\r\n");
           dataToPrint.append("T 7 0 40 140 ------------------").append("\r\n");
           dataToPrint.append("T 7 0 480 140 ------------------").append("\r\n");

           dataToPrint.append("T 7 0 40 160 Firma y aclaración").append("\r\n");

           dataToPrint.append("T 7 0 480 160 Firma y aclaración ").append("\r\n");
           dataToPrint.append("T 7 0 35 190 del notificado - DNI ").append("\r\n");
           dataToPrint.append("T 7 0 410 190 del funcionario interviniente ").append("\r\n");
           /*
           if (Global.GlobalVar.ImprimirOperadorPDA.Equals("S"))
           {
               dataToPrint.append("T 7 0 410 210 " + Global.GlobalVar.OperadorPDA).append("\r\n");
               try
               {
                   UsuariosServices regUsuarios = new UsuariosServices();
                   string nombreapellido = regUsuarios.getApellidoNombredelUsuario(acta.Id_Usuario_Logueado);
                   dataToPrint.append("T 7 0 410 230 " + nombreapellido).append("\r\n");
               }
               catch { }
           }
           */
           dataToPrint.append("T 7 1 50 255 NO DEBE ABONAR MONTO DE DINERO ALGUNO A LA AUTORIDAD ").append("\r\n");

           dataToPrint.append("T 7 1 190 295 QUE CONFECCIONÓ LA PRESENTE ACTA! ").append("\r\n");
           dataToPrint.append("L 0 345 800 345 1").append("\r\n");
           
           
           return dataToPrint.toString();
           
           //dataToPrint.append("PRINT").append("\r\n");
          
	   }
	   private static String generateActaParte1FormatCPCL(ActaConstatacion data)
	   {
	       Object[] arrayOfObject = new Object[6];
	       
	       ActaConstatacion acta = data;
	       
	     //Comienzo impresion
           StringBuilder dataToPrint = new StringBuilder();
           dataToPrint.append("! 0 200 200 600 1\r\n");
           dataToPrint.append("IN-DOTS\r\n");
           dataToPrint.append("PW 800\r\n");
           dataToPrint.append("LMARGIN 20\r\n");		//
           //dataToPrint.append("T 7 1 20 0 PROVINCIA  DE  \r\n");
           dataToPrint.append("T 7 1 20 0 PROVINCIA  DE  MENDOZA\r\n");
           //dataToPrint.append("T 7 1 20 33 Ministerio de Seguridad\r\n");
           dataToPrint.append("T 7 0 533 57 " + acta.getNumeroActa()).append("\r\n");
           dataToPrint.append("B I2OF5 1 1 50 500 0 ").append(acta.getNumeroActa()).append("\r\n");
           dataToPrint.append("T 7 0 20 120 Ruta: ").append(acta.getTipoRuta()).append(" Nro.Ruta: ").append(acta.getNumeroRuta()).append(" Km: ").append(acta.getKm()).append("  Fecha: ").append(Tools.DateValueOf(acta.getFechaHoraLabrado(),"dd/MM/yyyy HH:mm")).append("\r\n");
           dataToPrint.append("L 0 150 800 150 1\r\n");

           //Licencia
           String strUnicaProv = acta.getLicenciaUnicaProvincial() == "S" ? "Si" : "No";
           String strRetenida = acta.getLicenciaRetendia() == "S" ? "Si" : "No";

           String numeroLicencia = acta.getNumeroLicencia()==null?"":acta.getNumeroLicencia();
           if (numeroLicencia.equals("")) { strUnicaProv =""; strRetenida="";}

           String claseLicencia = acta.getClaseLicencia()==null?"":acta.getClaseLicencia();
           String sFechaVencimientoLicencia = acta.getFechaVencimientoLicencia()==null?"":Tools.DateValueOf(acta.getFechaVencimientoLicencia(),"dd/MM/yy");
           if (numeroLicencia.equals("")) sFechaVencimientoLicencia ="";
           
           dataToPrint.append("T 7 0 20 160 Cod.Seg.Lic: " + numeroLicencia + " U.Prov:" + strUnicaProv + " Clase:" + claseLicencia + "  Vto:" + sFechaVencimientoLicencia + " Retenida: " + strRetenida).append("\r\n");
           																	//PaisActual																//strProvLic
           String paisLicencia = acta.getPaisLicencia()==null?"":acta.getPaisLicencia();
           String provinciaLicencia= acta.getProvinciaLicencia()==null?"":acta.getProvinciaLicencia();
           
           dataToPrint.append("T 7 0 20 185 País: " + Tools.formatearDerecha(paisLicencia, 25, ' ') + "  Provincia: " + Tools.formatearDerecha(provinciaLicencia, 25, ' ')).append("\r\n");
           
           String departamentoLicencia = acta.getDepartamentoLicencia()==null?"":acta.getDepartamentoLicencia();
           String localidadLicencia = acta.getLocalidadLicencia()==null?"":acta.getLocalidadLicencia();
           //dptoLicencia																		//localidadActual	
           dataToPrint.append("T 7 0 20 210 Dpto.: " + Tools.formatearDerecha(departamentoLicencia, 25, ' ') + "  Localidad: " + Tools.formatearDerecha(localidadLicencia, 25, ' ')).append("\r\n");
           dataToPrint.append("L 0 240 800 240 1").append("\r\n");

           //conductor																			//strTipDoc							//sexoPrint // ver luego de cambiar 01 02
           dataToPrint.append("T 7 0 20 250 Nro.Doc: " + acta.getNumeroDocumento() + " Tipo: " + acta.getTipoDocumento() ).append("\r\n");//+ " Sexo: " + acta.getSexo()).append("\r\n");
           dataToPrint.append("T 7 0 20 275 Apellido: " + Tools.formatearDerecha(acta.getApellido(), 25, ' ')).append("\r\n");
           dataToPrint.append("T 7 0 400 275 Nombre: " + Tools.formatearDerecha(acta.getNombre(), 25, ' ')).append("\r\n");
           dataToPrint.append("T 7 0 20 300 Domicilio: " + Tools.formatearDerecha(acta.getCalle(), 25, ' ').trim() + " Nro:" + Tools.formatearDerecha(acta.getAltura(), 5, ' ') + " Piso:" + Tools.formatearDerecha(acta.getPiso(), 3, ' ') + " Dpto:" + Tools.formatearDerecha(acta.getDepartamento(), 5, ' ')).append("\r\n");
           																	//strPaisConductor

           dataToPrint.append("T 7 0 20 325 País: " + Tools.formatearDerecha(acta.getPaisDomicilio(), 25, ' ')).append("\r\n");
           																	//strProvConductor		
           dataToPrint.append("T 7 0 400 325 Prov: " + Tools.formatearDerecha(acta.getProvinciaDomicilio(), 25, ' ')).append("\r\n");
           																//dptoConductor
           dataToPrint.append("T 7 0 20 350 Dpto: " + Tools.formatearDerecha(acta.getDepartamentoDomicilio(), 25, ' ')).append("\r\n");
           																	//localidadCond
           dataToPrint.append("T 7 0 400 350 Loc: " + Tools.formatearDerecha(acta.getLocalidadDomicilio(), 25, ' ')).append("\r\n");
           dataToPrint.append("T 7 0 20 375 Barrio: " + Tools.formatearDerecha(acta.getBarrio(), 25, ' ')).append("\r\n");
           dataToPrint.append("T 7 0 460 375 C.P. " + Tools.formatearDerecha(acta.getCodigoPostal(), 15, ' ')).append("\r\n");

           //Dominio
           String strMarca = acta.getMarca();//acta.Id_Marca.Equals(-1) || acta.Id_Marca.Equals(100) ? acta.MarcaNueva : marca.N_Marca;
           String strColor = acta.getColor();//acta.Id_color.Equals(-1) ? acta.ColorNuevo : color.N_color;
           dataToPrint.append("L 0 405 800 405 1").append("\r\n");
           dataToPrint.append("T 7 0 20 415 Dominio: " + Tools.formatearDerecha(acta.getDominio(), 15, ' ')).append("\r\n");
           dataToPrint.append("T 7 0 250 415 Marca: " + Tools.formatearDerecha(strMarca, 15, ' ')).append("\r\n");
           dataToPrint.append("L 0 445 800 445 1").append("\r\n");

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
           dataToPrint.append("T 7 0 20 455 Infracciones: ").append("\r\n");
           if (acta.getCodigoInfraccion1()!=null && !acta.getCodigoInfraccion1().equals(""))
           {
        	   
        	    String sDescripcion =  Tools.formatearDerecha(acta.getDescripcionInfraccion1(), 100, '.');
     	     	String sDescripcionParte1 = sDescripcion.substring(0, 49);
     	     	String sDescripcionParte2 = sDescripcion.substring(49);
              /*if(objInf1.ImprimirPuntos.Equals("S"))*/
     	     	if(acta.getImprimirPuntos1()!=null && acta.getImprimirPuntos1().equals("S"))
                   dataToPrint.append("T 7 0 20 480 Cod:" + acta.getCodigoInfraccion1() + "(Pts:" + acta.getPuntos1().toString() + ")" + sDescripcionParte1 ).append("\r\n");
                else																								//objInf1.DescripcionCorta		
                  dataToPrint.append("T 7 0 20 480 Cod:"  + acta.getCodigoInfraccion1() + " " + sDescripcionParte1).append("\r\n");
                 														//objInf1.DescripcionCorta
               dataToPrint.append("T 7 0 20 505 " + sDescripcionParte2).append("\r\n");

           }
           if (acta.getCodigoInfraccion2()!=null && !acta.getCodigoInfraccion2().equals(""))
           {
       	    String sDescripcion =  Tools.formatearDerecha(acta.getDescripcionInfraccion2(), 100, '.');
    	     	String sDescripcionParte1 = sDescripcion.substring(0, 49);
    	     	String sDescripcionParte2 = sDescripcion.substring(49);
              /*if (objInf2.ImprimirPuntos.Equals("S")) */
     	     	if(acta.getImprimirPuntos2()!=null && acta.getImprimirPuntos2().equals("S"))
                   dataToPrint.append("T 7 0 20 530 Cod:" + acta.getCodigoInfraccion2() + "(Pts:" + acta.getPuntos2().toString() + ")" + sDescripcionParte1 ).append("\r\n");
               else																								//objInf2.DescripcionCorta
                   dataToPrint.append("T 7 0 20 530 Cod:" + acta.getCodigoInfraccion2() + " " + sDescripcionParte1).append("\r\n");
                   														//objInf2.DescripcionCorta								//47
               dataToPrint.append("T 7 0 20 555 " + sDescripcionParte2).append("\r\n");
           }

           dataToPrint.append("T 7 0 20 580 Observaciones: \r\n");
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
