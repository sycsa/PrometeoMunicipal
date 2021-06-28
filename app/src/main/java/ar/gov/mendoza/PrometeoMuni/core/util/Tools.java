package ar.gov.mendoza.PrometeoMuni.core.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;

import com.google.gson.Gson;

//import org.simpleframework.xml.Serializer;
//import org.simpleframework.xml.core.Persister;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public  class Tools {

    // Serialize a single object.    
    public static String serializeToJson(Object myClass) {
        Gson gson = new Gson();
        String j = gson.toJson(myClass);
        Tools.Log(Log.ERROR, "serializeToJson", j);
        return j;
    }
    
	public static void WriteToLogTxt(Object obj, String datoAdicional)
	{
		try {
			File image = null;
			File sdCardDirectory = Environment.getExternalStorageDirectory();
			String strAvatarFilename = obj.getClass().getSimpleName() + datoAdicional + ".txt";
			image = new File(sdCardDirectory, strAvatarFilename);
			
			FileOutputStream f = new FileOutputStream(image,false);
			ObjectOutputStream o = new ObjectOutputStream(f);
			
			if (obj instanceof StringBuilder)
			{
				o.writeChars(obj.toString());
			}
			else 
			{
				String datos = Tools.serializeToJson(obj);
				o.writeObject(datos);
			}
			
			

			o.close();
			f.close();

		} catch (FileNotFoundException e) {
			Tools.Log(Log.ERROR, "WriteToLog", "File not found");
		} catch (IOException e) {
			Tools.Log(Log.ERROR, "WriteToLog", "Error initializing stream");
			
		} 

		
	}

	public static byte[] getBytesForCipher(String pValor)
	{
		 Charset charset = Charset.forName("ISO-8859-1");
		 return pValor.getBytes(charset);
		
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
	
	/**
	 * The time at which the app was first installed. Units are as per currentTimeMillis().
	 * @param context
	 * @return
	 */
	public static long getAppFirstInstallTime(Context context){
	    PackageInfo packageInfo;
	    try {
	    if(Build.VERSION.SDK_INT>8/*Build.VERSION_CODES.FROYO*/ ){
	        packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
	        return packageInfo.lastUpdateTime;//.firstInstallTime;
	    }else{
	        //firstinstalltime unsupported return last update time not first install time
	        ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
	        String sAppFile = appInfo.sourceDir;
	        return new File(sAppFile).lastModified();
	    }
	    } catch (NameNotFoundException e) {
	    //should never happen
	    return 0;
	    }
	}
	public static void PersitirToXml(Object objeto)
	{
		/*
		try
    	{

    		Serializer serializer = new Persister();
    		File sdCardDirectory = Environment.getExternalStorageDirectory();
    		File result = new File(sdCardDirectory,"objeto.dat");
    		serializer.write(objeto, result);
    	}
    	catch(Exception ex)
    	{
    		String msg = ex.getMessage();
    		msg ="";
    		
    	}
    	*/
	}
	
	public static String encodeTobase64(Bitmap image)
	{
	    Bitmap immagex=image;
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
	    byte[] b = baos.toByteArray();
	    //String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);
		String imageEncoded = Base64.encodeToString(b,Base64.NO_WRAP);

	    Log.e("LOOK", imageEncoded);
	    return imageEncoded;
	}
	public static Bitmap decodeBase64(String input)
	{
	    byte[] decodedByte = Base64.decode(input, 0);
	    return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	}
	
	public static String DecimalFormat(Double value)
	{
		return DecimalFormat(value,null);
	}

	public static String DecimalFormat (Double value, String sFormatoSalida)
	{
		
		 DecimalFormat df ;
		 if (sFormatoSalida==null)
			 df = new DecimalFormat("#.00");
		 else
			 df = new DecimalFormat(sFormatoSalida);
		 
		 if(value==null)
		 {
			 return "0.0";
		 }
		 else
		 {
		 String resultado = df.format(value);
		 resultado = resultado.replace(",",".");
		 return resultado;
		 }
	}
	public static String DecimalFormatForCodBar(Double value)
	{
		// quitamos coma del valor para ponerlo en formato codigo barra con 2 digitos
		 DecimalFormat df = new DecimalFormat("#.00");
		 String resultado = df.format(value);
		 resultado = resultado.replace(",","");
		 resultado = resultado.replace(".","");
		 return resultado;
		 
	}
	public static String DateValueOf(Date pFecha)
	{ 
		return DateValueOf(pFecha,null);
	}
	public static String DateValueOf(Date pFecha, String sFormatoSalida)
	{
		if (sFormatoSalida==null)
		{
			sFormatoSalida = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(sFormatoSalida, Locale.getDefault());
		/*agregado*/
			DateFormatSymbols symbols = new DateFormatSymbols(Locale.getDefault());
	        symbols.setAmPmStrings(new String[] { "AM", "PM" });
	        dateFormat.setDateFormatSymbols(symbols);
		/*fin agregado*/
        Date date = pFecha;//new Date();
        return dateFormat.format(date);
	}	
	public static Long persistDate(Date date) {
	    if (date != null) {
	        return date.getTime();
	    }
	    return null;
	}
	public static Date ConvertLongToDate(Long lFecha)
	{
		if (lFecha==null)
			return null;
		
		Date dResultado = new Date(lFecha);
		return dResultado;
	}
	
	public static Date loadDate(Cursor cursor, int index) {
	    if (cursor.isNull(index)) {
	        return null;
	    }
	    return new Date(cursor.getLong(index));
	}
	public static Long ConvertDateToLong(Date date)
	{
		 if (date != null) {
		        return date.getTime();
		    }
		    return null;
	}
	
	public static Date ConvertStringToDate(String sFecha)
	{
		String sFormatoOrigen ="dd/MM/yyyy";
		
		Date dResultado = ConvertStringToDate(sFecha, sFormatoOrigen);
		
		return dResultado;
	}
	public static Date ConvertStringToDate(String sFecha, String sFormatoOrigen)
	{
		   if(sFecha ==null) return null;
		   
		   ParsePosition pos = new ParsePosition(0);
		   SimpleDateFormat simpledateformat = new SimpleDateFormat(sFormatoOrigen);
		   Date stringDate = simpledateformat.parse(sFecha, pos);
		   return stringDate;  
	}
	public static Boolean isNumber(String InNumber)
	{
        int idx = 0;
        int strLen = InNumber.length();
        char[] InNumber2 = InNumber.toCharArray();
        Boolean RtrnValue = true;
        if (strLen > 0)
        {
            for (idx = 0; idx < strLen; idx++)
            {
                if (Character.isDigit(InNumber2[idx]) == false) //.IsNumber
                    RtrnValue = false;
            }
        }
        else
        {
            RtrnValue = false;
        }
        return RtrnValue;
	}
	
		
	public static Date Today()
	{
		Calendar now = Calendar.getInstance();
		
		return now.getTime();
	}
	
	
	public static void Log(int pType, String pTag, String pMsg)
	{
		Time now = 	new Time();
		now.setToNow();
		//SimpleDateFormat sdf = new SimpleDateFormat()
		pMsg = pMsg + " At " + now.format("yyyy-MM-dd HH:mm:ss"); 
		
		switch (pType)
		{
			case Log.ERROR:
				Log.e(pTag, pMsg);
				break;
			case Log.DEBUG:
				Log.d(pTag, pMsg);
				break;
			case Log.WARN:
				Log.w(pTag, pMsg);
				break;
			default:
				Log.d(pTag, pMsg);
			
		}
		
	}
	
	public static String getRelleno(int iCantidadCaracteres, char pStuff)
	{
		 StringBuilder sb = new StringBuilder();
		 for(int i = 0; i < iCantidadCaracteres; i++)
		 {
			 sb.append(pStuff);
		 }
		 return sb.toString();
	}
	public static String formatearDerecha(String valor, int iCantidadCaracteres, char pStuff)
	{
		 if(valor==null) valor="";
		 int len=0;
		 len=valor.length();
		 iCantidadCaracteres = iCantidadCaracteres - len;
		 String sRelleno ="";
		 if(iCantidadCaracteres<0)
		 {
			 //deberiamos recortar o no hacer nada
			 sRelleno ="";
		 }
		 else
	     {
			 sRelleno = getRelleno(iCantidadCaracteres,pStuff);
	     }
		 
		 return valor  + sRelleno;
		
	}
	public static String formatearIzquierda(String valor, int iCantidadCaracteres, char pStuff)
	{
		if(valor==null) valor="";
		
		 StringBuilder sb = new StringBuilder();
		 int len=0;
		 
		 len=valor.length();
		 
		 for(int i = iCantidadCaracteres; i > len; i--)
		 {
		        sb.append(pStuff);
	     }
	     sb.append(valor);
		 
		 return sb.toString();
	}
	public static String formatearIzquierda(int valor, int iCantidadCaracteres, char pStuff)
	{
		
		
		boolean negative = false;
	    int value, len = 0;

	    if(valor >= 0){
	        value = valor;
	    } else {
	        negative = true;
	        value = - valor;
	        valor = - valor;
	        len ++;
	    }

	    if(value == 0){
	        len = 1;
	    } else{         
	        for(; value != 0; len ++){
	            value /= 10;
	        }
	    }

	    StringBuilder sb = new StringBuilder();

	    if(negative){
	        sb.append('-');
	    }

	    for(int i = iCantidadCaracteres; i > len; i--){
	        sb.append(pStuff);
	    }

	    sb.append(valor);

	    return sb.toString(); 
		
	}

    public static int GeneraDigitoVerificador(String strCadena)
    {
        String strCadena_dv = "9713971397139713971397139713971397139713971397139713971397139713";
        Integer suma_total = 0;
        String strCadenaCompleta = strCadena;//strCadena.PadLeft(strCadena.length(), '0');
        String strCadenaSinDigito = strCadenaCompleta;
        for (int i = 0; i < strCadenaSinDigito.length(); i++)
        {
            int num1 = Integer.parseInt(strCadenaSinDigito.substring(i, i + 1));
            int num2 = Integer.parseInt(strCadena_dv.substring(i, i+ 1));
            suma_total += (num1 * num2);
        }

        String strSuma = suma_total.toString();
        int idigito = Integer.parseInt(strSuma.substring(strSuma.length() - 1));
        int resultado = 10 - idigito;
        if (resultado == 10)
            return 0;
        else
            return resultado;
    }
    public static int GeneraDigitoVerificadorChaco(String strCadena)
    {			   //de cba   "9713971397139713971397139713971397139713971397139713971397139713";
    			   //de cacho "9713971397139713971397139713971397139713971397139"
        String strCadena_dv = "9713971397139713971397139713971397139713971397139713971397139713";
        Integer suma_total = 0;
        String strCadenaCompleta = strCadena;//strCadena.PadLeft(strCadena.length(), '0');
        String strCadenaSinDigito = strCadenaCompleta;
        for (int i = 0; i < strCadenaSinDigito.length(); i++)
        {
            int num1 = Integer.parseInt(strCadenaSinDigito.substring(i, i + 1));
            int num2 = Integer.parseInt(strCadena_dv.substring(i, i+ 1));
            suma_total += (num1 * num2);
        }

        String strSuma = suma_total.toString();
        int idigito = Integer.parseInt(strSuma.substring(strSuma.length() - 1));
        int resultado = 10 - idigito;
        if (resultado == 10)
            return 0;
        else
            return resultado;
    }
  
    
    public static String actaCodeGetKolektor(String actaID, String pNroDocumento, String pFechaVencimiento_ddmmyyyy, String pMontoMulta0_00)//,ref string pDigitoVerificador )
    {
        StringBuilder strFinal = new StringBuilder();

        String sTipoBoletaComprobante = "500"; // 3 digitos
        String sNumeroComprobante = "";// 16 digitos => Codigo Organismo Encomendante (3) +   Valor Fijo (1) + Numero Acta (12)
        /*****************************************************************************/
        String sCodigoOrganismoEncomendante = "840";                               //  3 digitos Policia Caminera
        String sValorFijo = "0";                                                   // 1 digito
        String sNumeroActa = formatearIzquierda(actaID, 12,'0');                  // 12 digitos
        /*****************************************************************************/
        sNumeroComprobante = sCodigoOrganismoEncomendante + sValorFijo + sNumeroActa;
        String sPorcentajeOtrasCuentas = "000"; //3 digitos
        String sNumeroObligacion = formatearIzquierda(0, 20,'0');// 20 digitos

        StringBuilder strCodigoBarra1 = new StringBuilder();
        strCodigoBarra1.append(sTipoBoletaComprobante).append(sNumeroComprobante).append(sPorcentajeOtrasCuentas).append(sNumeroObligacion);

        /**************************************************************************/
        String sNroDocumento = formatearIzquierda(pNroDocumento, 10,'0'); // 10 digitos
        String sFechaVto = pFechaVencimiento_ddmmyyyy; // ddmmyyyy
        String sHonorarios = formatearIzquierda(0, 12,'0'); // 12 digitos  Honorarios
        pMontoMulta0_00 = pMontoMulta0_00.replace(",", "").replace(".", "");
        String sMontoMulta = formatearIzquierda(pMontoMulta0_00, 10,'0'); // 10 digitos
        /***************************************************************************/
        StringBuilder strCodigoBarra2 = new StringBuilder();
        strCodigoBarra2.append(sNroDocumento).append(sFechaVto).append(sHonorarios).append(sMontoMulta);

        String p_cadena = strCodigoBarra1.append(strCodigoBarra2.toString()).toString();
        String sLastTwoDigits = CalculaDV_pc(p_cadena);
        strFinal.append(p_cadena).append(sLastTwoDigits);
        return strFinal.toString();
    }
    
    public static String actaCodeGetChaco(String actaID, String pNroDocumento, String pFechaVencimiento_ddmmyy, String pMontoMulta0_00)//,ref string pDigitoVerificador )
    {
        StringBuilder strFinal = new StringBuilder();

        String sTipoBoletaComprobante = "008"; // 3 digitos
        
        /*****************************************************************************/
        String sNumeroActa = formatearIzquierda(actaID, 12,'0');                  // 12 digitos
        /*****************************************************************************/
        pMontoMulta0_00 = pMontoMulta0_00.replace(",", "").replace(".", "");
        String sMontoMulta = formatearIzquierda(pMontoMulta0_00, 8,'0'); // 10 digitos
        /***************************************************************************/

        StringBuilder strCodigoBarra1 = new StringBuilder();
        strCodigoBarra1.append(sTipoBoletaComprobante).append(sNumeroActa).append(pFechaVencimiento_ddmmyy).append(sMontoMulta);


        String p_cadena = strCodigoBarra1.toString();
        String sLastDigits = CalculaDV_Chaco(p_cadena);
        strFinal.append(p_cadena).append(sLastDigits);
        return strFinal.toString();
    }
    
    /*Digito Verificador Chaco*/
    public static String CalculaDV_Chaco(String p_cadena)
    {
    	String l_digito;
    	int pares = 0;
    	int impares = 0;
    	int suma ; 
    	int modulo ;
    	
    	 for (int i = 0; i < p_cadena.length(); i++)
         {
    		 if ( (i+1) % 2 == 0)
    			 pares = pares + Integer.parseInt(p_cadena.substring(i,i+1));
    		 else
    			 impares = impares + Integer.parseInt(p_cadena.substring(i,i+1));
         }
    	 
    	 impares = impares * 3; 
         suma = pares + impares;
         modulo = suma % 10;
         
         if (modulo == 0)
        	l_digito ="0";
         else
        	l_digito = String.valueOf(10 - modulo);
         
     return l_digito;    
    }   
    /* Fin Digito Verificador Chaco*/

    /* Inicio Digito Verificador Mendoza */
    public static String CalculaDV_Mendoza(String psCadena)
    {
		String sRetorno = "";
	    
	    Integer wacum ;//(10);
	    Integer p;//(10);
	    String n;// varchar2(50);
	    Integer i;// integer(10);
	    Integer a ;//integer(10);
	    Integer b;// integer(10);
	    String r;// varchar2(50);
	    String w; //varchar2(50);

	    String wcadena;// varchar2(50);

	    
	    wcadena = psCadena;
	    wacum = 0;
        p = 1; 
        Integer[] aseq = {11,13,17,19,23};
		
	    wcadena= wcadena.toUpperCase();
	    i= wcadena.length();
	    
	    while (i>0)
	    {
	    	char nchar;
	    	nchar = wcadena.charAt(i-1); //wcadena.substring(i,i+1); //substr(wcadena,i,1);
	    	System.out.println("n = " + nchar);
            n = String.valueOf(nchar);
	    	if( n.compareTo("9") > 0  || n.compareTo("0") < 0 ) //n > '9' || n < '0')// then 
            {
	    		//System.out.println("n>9 or n<0" );
            	n = n.toUpperCase();// upper(n);
            }
            else 
            {  
            	int ascii = (int) nchar;
            	int ascii0 = (int) '0';
                n = String.valueOf(Character.toChars(ascii- ascii0));//chr(ascii(n) - ascii('0'));
            }//end if;
          
          if (n == "P") //then 
          { 
        	  n = "F";
          }
          //end if;

          wacum = wacum + aseq[p-1] * ((int) n.charAt(0) );
          System.out.println("wacum " + wacum.toString() );
          if (p == 5)// then 
          {
        	  p = 1;
          }
          else
          {	  
            p = p + 1;
          }//end if;
          i = i - 1;
	    	
	    }

	    
        //w = lpad(wacum, 6, '0');
	    w = rellenarCon(6,"0",wacum);
        a = Integer.parseInt(w.substring(4)); //to_number(substr(w, 5));
        b = Integer.parseInt(w.substring(2,4));//to_number(substr(w, 3, 2));
        a = java.lang.Math.abs(a-b);//to_number(abs(a - b));
        r = rellenarCon(4,"0",a);//lpad(a, 4, '0'); 
        //P_DIGITO = substr(r,3,2)
	    sRetorno = r.substring(2, 4);
	    return sRetorno;
	}
	public static String rellenarCon(Integer pLargo , String pRelleno, Integer piwacum)
	{
		String sRetorno ="";
		int pLargoCadena = piwacum.toString().length();
		if (pLargo>pLargoCadena)
		{
			int ilargoRelleno = pLargo - pLargoCadena;
			String sRelleno="";
			for(int i=0;i<ilargoRelleno;i++)
			{
				sRelleno = sRelleno +"0";
			}
			 sRetorno = sRelleno + piwacum.toString();
		}
		else
		{
			sRetorno = piwacum.toString().substring(0,pLargo);
		}
		
		return sRetorno;
	}

    /* Fin Digito Verificador Mendoza*/
    
    
    public static String CalculaDV_pc(String p_cadena)
    {
        String sLastTwoDigits = "";


        int nNum1 = Integer.parseInt(String.valueOf(p_cadena.charAt(0)));//.Substring(1 - 1, 1));
        int nNum2 = Integer.parseInt(String.valueOf(p_cadena.charAt(1)));//.Substring(2 - 1, 1));
        int nNum3 = Integer.parseInt(String.valueOf(p_cadena.charAt(2)));//.Substring(3 - 1, 1));
        int nNum4 = Integer.parseInt(String.valueOf(p_cadena.charAt(3)));//.Substring(4 - 1, 1));
        int nNum5 = Integer.parseInt(String.valueOf(p_cadena.charAt(4)));//.Substring(5 - 1, 1));
        int nNum6 = Integer.parseInt(String.valueOf(p_cadena.charAt(5)));//.Substring(6 - 1, 1));
        int nNum7 = Integer.parseInt(String.valueOf(p_cadena.charAt(6)));//.Substring(7 - 1, 1));
        int nNum8 = Integer.parseInt(String.valueOf(p_cadena.charAt(7)));//.Substring(8 - 1, 1));
        int nNum9 = Integer.parseInt(String.valueOf(p_cadena.charAt(8)));//.Substring(9 - 1, 1));
        int nNum10 = Integer.parseInt(String.valueOf(p_cadena.charAt(9)));//.Substring(10 - 1, 1));
        int nNum11 = Integer.parseInt(String.valueOf(p_cadena.charAt(10)));//.Substring(11 - 1, 1));
        int nNum12 = Integer.parseInt(String.valueOf(p_cadena.charAt(11)));//.Substring(12 - 1, 1));
        int nNum13 = Integer.parseInt(String.valueOf(p_cadena.charAt(12)));//.Substring(13 - 1, 1));
        int nNum14 = Integer.parseInt(String.valueOf(p_cadena.charAt(13)));//.Substring(14 - 1, 1));
        int nNum15 = Integer.parseInt(String.valueOf(p_cadena.charAt(14)));//.Substring(15 - 1, 1));
        int nNum16 = Integer.parseInt(String.valueOf(p_cadena.charAt(15)));//.Substring(16 - 1, 1));
        int nNum17 = Integer.parseInt(String.valueOf(p_cadena.charAt(16)));//.Substring(17 - 1, 1));
        int nNum18 = Integer.parseInt(String.valueOf(p_cadena.charAt(17)));//.Substring(18 - 1, 1));
        int nNum19 = Integer.parseInt(String.valueOf(p_cadena.charAt(18)));//.Substring(19 - 1, 1));
        int nNum20 = Integer.parseInt(String.valueOf(p_cadena.charAt(19)));//.Substring(20 - 1, 1));
        int nNum21 = Integer.parseInt(String.valueOf(p_cadena.charAt(20)));//.Substring(21 - 1, 1));
        int nNum22 = Integer.parseInt(String.valueOf(p_cadena.charAt(21)));//.Substring(22 - 1, 1));
        int nNum23 = Integer.parseInt(String.valueOf(p_cadena.charAt(22)));//.Substring(23 - 1, 1));
        int nNum24 = Integer.parseInt(String.valueOf(p_cadena.charAt(23)));//.Substring(24 - 1, 1));
        int nNum25 = Integer.parseInt(String.valueOf(p_cadena.charAt(24)));//.Substring(25 - 1, 1));
        int nNum26 = Integer.parseInt(String.valueOf(p_cadena.charAt(25)));//.Substring(26 - 1, 1));
        int nNum27 = Integer.parseInt(String.valueOf(p_cadena.charAt(26)));//.Substring(27 - 1, 1));
        int nNum28 = Integer.parseInt(String.valueOf(p_cadena.charAt(27)));//.Substring(28 - 1, 1));
        int nNum29 = Integer.parseInt(String.valueOf(p_cadena.charAt(28)));//.Substring(29 - 1, 1));
        int nNum30 = Integer.parseInt(String.valueOf(p_cadena.charAt(29)));//.Substring(30 - 1, 1));
        int nNum31 = Integer.parseInt(String.valueOf(p_cadena.charAt(30)));//.Substring(31 - 1, 1));
        int nNum32 = Integer.parseInt(String.valueOf(p_cadena.charAt(31)));//.Substring(32 - 1, 1));
        int nNum33 = Integer.parseInt(String.valueOf(p_cadena.charAt(32)));//.Substring(33 - 1, 1));
        int nNum34 = Integer.parseInt(String.valueOf(p_cadena.charAt(33)));//.Substring(34 - 1, 1));
        int nNum35 = Integer.parseInt(String.valueOf(p_cadena.charAt(34)));//.Substring(35 - 1, 1));
        int nNum36 = Integer.parseInt(String.valueOf(p_cadena.charAt(35)));//.Substring(36 - 1, 1));
        int nNum37 = Integer.parseInt(String.valueOf(p_cadena.charAt(36)));//.Substring(37 - 1, 1));
        int nNum38 = Integer.parseInt(String.valueOf(p_cadena.charAt(37)));//.Substring(38 - 1, 1));
        int nNum39 = Integer.parseInt(String.valueOf(p_cadena.charAt(38)));//.Substring(39 - 1, 1));
        int nNum40 = Integer.parseInt(String.valueOf(p_cadena.charAt(39)));//.Substring(40 - 1, 1));
        int nNum41 = Integer.parseInt(String.valueOf(p_cadena.charAt(40)));//.Substring(41 - 1, 1));
        int nNum42 = Integer.parseInt(String.valueOf(p_cadena.charAt(41)));//.Substring(42 - 1, 1));
        int nNum43 = Integer.parseInt(String.valueOf(p_cadena.charAt(42)));//.Substring(43 - 1, 1));
        int nNum44 = Integer.parseInt(String.valueOf(p_cadena.charAt(43)));//.Substring(44 - 1, 1));
        int nNum45 = Integer.parseInt(String.valueOf(p_cadena.charAt(44)));//.Substring(45 - 1, 1));
        int nNum46 = Integer.parseInt(String.valueOf(p_cadena.charAt(45)));//.Substring(46 - 1, 1));
        int nNum47 = Integer.parseInt(String.valueOf(p_cadena.charAt(46)));//.Substring(47 - 1, 1));
        int nNum48 = Integer.parseInt(String.valueOf(p_cadena.charAt(47)));//.Substring(48 - 1, 1));
        int nNum49 = Integer.parseInt(String.valueOf(p_cadena.charAt(48)));//.Substring(49 - 1, 1));
        int nNum50 = Integer.parseInt(String.valueOf(p_cadena.charAt(49)));//.Substring(50 - 1, 1));
        int nNum51 = Integer.parseInt(String.valueOf(p_cadena.charAt(50)));//.Substring(51 - 1, 1));
        int nNum52 = Integer.parseInt(String.valueOf(p_cadena.charAt(51)));//.Substring(52 - 1, 1));
        int nNum53 = Integer.parseInt(String.valueOf(p_cadena.charAt(52)));//.Substring(53 - 1, 1));
        int nNum54 = Integer.parseInt(String.valueOf(p_cadena.charAt(53)));//.Substring(54 - 1, 1));
        int nNum55 = Integer.parseInt(String.valueOf(p_cadena.charAt(54)));//.Substring(55 - 1, 1));
        int nNum56 = Integer.parseInt(String.valueOf(p_cadena.charAt(55)));//.Substring(56 - 1, 1));
        int nNum57 = Integer.parseInt(String.valueOf(p_cadena.charAt(56)));//.Substring(57 - 1, 1));
        int nNum58 = Integer.parseInt(String.valueOf(p_cadena.charAt(57)));//.Substring(58 - 1, 1));
        int nNum59 = Integer.parseInt(String.valueOf(p_cadena.charAt(58)));//.Substring(59 - 1, 1));
        int nNum60 = Integer.parseInt(String.valueOf(p_cadena.charAt(59)));//.Substring(60 - 1, 1));
        int nNum61 = Integer.parseInt(String.valueOf(p_cadena.charAt(60)));//.Substring(61 - 1, 1));
        int nNum62 = Integer.parseInt(String.valueOf(p_cadena.charAt(61)));//.Substring(62 - 1, 1));
        int nNum63 = Integer.parseInt(String.valueOf(p_cadena.charAt(62)));//.Substring(63 - 1, 1));
        int nNum64 = Integer.parseInt(String.valueOf(p_cadena.charAt(63)));//.Substring(64 - 1, 1));
        int nNum65 = Integer.parseInt(String.valueOf(p_cadena.charAt(64)));//.Substring(65 - 1, 1));
        int nNum66 = Integer.parseInt(String.valueOf(p_cadena.charAt(65)));//.Substring(66 - 1, 1));
        int nNum67 = Integer.parseInt(String.valueOf(p_cadena.charAt(66)));//.Substring(67 - 1, 1));
        int nNum68 = Integer.parseInt(String.valueOf(p_cadena.charAt(67)));//.Substring(68 - 1, 1));
        int nNum69 = Integer.parseInt(String.valueOf(p_cadena.charAt(68)));//.Substring(69 - 1, 1));
        int nNum70 = Integer.parseInt(String.valueOf(p_cadena.charAt(69)));//.Substring(70 - 1, 1));
        int nNum71 = Integer.parseInt(String.valueOf(p_cadena.charAt(70)));//.Substring(71 - 1, 1));
        int nNum72 = Integer.parseInt(String.valueOf(p_cadena.charAt(71)));//.Substring(72 - 1, 1));
        int nNum73 = Integer.parseInt(String.valueOf(p_cadena.charAt(72)));//.Substring(73 - 1, 1));
        int nNum74 = Integer.parseInt(String.valueOf(p_cadena.charAt(73)));//.Substring(74 - 1, 1));
        int nNum75 = Integer.parseInt(String.valueOf(p_cadena.charAt(74)));//.Substring(75 - 1, 1));
        int nNum76 = Integer.parseInt(String.valueOf(p_cadena.charAt(75)));//.Substring(76 - 1, 1));
        int nNum77 = Integer.parseInt(String.valueOf(p_cadena.charAt(76)));//.Substring(77 - 1, 1));
        int nNum78 = Integer.parseInt(String.valueOf(p_cadena.charAt(77)));//.Substring(78 - 1, 1));
        int nNum79 = Integer.parseInt(String.valueOf(p_cadena.charAt(78)));//.Substring(79 - 1, 1));
        int nNum80 = Integer.parseInt(String.valueOf(p_cadena.charAt(79)));//.Substring(80 - 1, 1));
        int nNum81 = Integer.parseInt(String.valueOf(p_cadena.charAt(80)));//.Substring(81 - 1, 1));
        int nNum82 = Integer.parseInt(String.valueOf(p_cadena.charAt(81)));//.Substring(82 - 1, 1));
        int nNum83 = 0;

        int nSuma1 = 0;
        int nDivision1 = 0;
        int nResto1;
        Integer nDigitoVer1;
        int nSuma2;
        int nDivision2;
        int nResto2;
        Integer nDigitoVer2 = 0;

        nSuma1 = (nNum1 * 5) + (nNum2 * 4) + (nNum3 * 3) + (nNum4 * 2) + (nNum5 * 7) + (nNum6 * 6) + (nNum7 * 5) + (nNum8 * 4) + (nNum9 * 3) + (nNum10 * 2) + (nNum11 * 7) + (nNum12 * 6) + (nNum13 * 5) + (nNum14 * 4) + (nNum15 * 3) + (nNum16 * 2) + (nNum17 * 7) + (nNum18 * 6) + (nNum19 * 5) + (nNum20 * 4) + (nNum21 * 3) + (nNum22 * 2) + (nNum23 * 7) + (nNum24 * 6) + (nNum25 * 5) + (nNum26 * 4) + (nNum27 * 3) + (nNum28 * 2) + (nNum29 * 7) + (nNum30 * 6) + (nNum31 * 5) + (nNum32 * 4) + (nNum33 * 3) + (nNum34 * 2) + (nNum35 * 7) + (nNum36 * 6) + (nNum37 * 5) + (nNum38 * 4) + (nNum39 * 3) + (nNum40 * 2) + (nNum41 * 7) + (nNum42 * 6) + (nNum43 * 5) + (nNum44 * 4) + (nNum45 * 3) + (nNum46 * 2) + (nNum47 * 7) + (nNum48 * 6) + (nNum49 * 5) + (nNum50 * 4) + (nNum51 * 3) + (nNum52 * 2) + (nNum53 * 7) + (nNum54 * 6) + (nNum55 * 5) + (nNum56 * 4) + (nNum57 * 3) + (nNum58 * 2) + (nNum59 * 7) + (nNum60 * 6) + (nNum61 * 5) + (nNum62 * 4) + (nNum63 * 3) + (nNum64 * 2) + (nNum65 * 7) + (nNum66 * 6) + (nNum67 * 5) + (nNum68 * 4) + (nNum69 * 3) + (nNum70 * 2) + (nNum71 * 7) + (nNum72 * 6) + (nNum73 * 5) + (nNum74 * 4) + (nNum75 * 3) + (nNum76 * 2) + (nNum77 * 7) + (nNum78 * 6) + (nNum79 * 5) + (nNum80 * 4) + (nNum81 * 3) + (nNum82 * 2);

        //--f_debug('Suma 1: '||to_CHAR(nSuma1));

        nResto1 = nSuma1 % 11;//MOD(nSuma1, 11);
        nDigitoVer1 = 11 - nResto1;
        if (nDigitoVer1 == 10)//THEN
        { nDigitoVer1 = 0; }
        else
        {
            if (nDigitoVer1 == 11)// THEN
            { nDigitoVer1 = 1; }
            //end if;
        }
        //end if;
        //--
        nNum83 = nDigitoVer1;
        nSuma2 = (nNum1 * 6) + (nNum2 * 5) + (nNum3 * 4) + (nNum4 * 3) + (nNum5 * 2) + (nNum6 * 7) + (nNum7 * 6) + (nNum8 * 5) + (nNum9 * 4) + (nNum10 * 3) + (nNum11 * 2) + (nNum12 * 7) + (nNum13 * 6) + (nNum14 * 5) + (nNum15 * 4) + (nNum16 * 3) + (nNum17 * 2) + (nNum18 * 7) + (nNum19 * 6) + (nNum20 * 5) + (nNum21 * 4) + (nNum22 * 3) + (nNum23 * 2) + (nNum24 * 7) + (nNum25 * 6) + (nNum26 * 5) + (nNum27 * 4) + (nNum28 * 3) + (nNum29 * 2) + (nNum30 * 7) + (nNum31 * 6) + (nNum32 * 5) + (nNum33 * 4) + (nNum34 * 3) + (nNum35 * 2) + (nNum36 * 7) + (nNum37 * 6) + (nNum38 * 5) + (nNum39 * 4) + (nNum40 * 3) + (nNum41 * 2) + (nNum42 * 7) + (nNum43 * 6) + (nNum44 * 5) + (nNum45 * 4) + (nNum46 * 3) + (nNum47 * 2) + (nNum48 * 7) + (nNum49 * 6) + (nNum50 * 5) + (nNum51 * 4) + (nNum52 * 3) + (nNum53 * 2) + (nNum54 * 7) + (nNum55 * 6) + (nNum56 * 5) + (nNum57 * 4) + (nNum58 * 3) + (nNum59 * 2) + (nNum60 * 7) + (nNum61 * 6) + (nNum62 * 5) + (nNum63 * 4) + (nNum64 * 3) + (nNum65 * 2) + (nNum66 * 7) + (nNum67 * 6) + (nNum68 * 5) + (nNum69 * 4) + (nNum70 * 3) + (nNum71 * 2) + (nNum72 * 7) + (nNum73 * 6) + (nNum74 * 5) + (nNum75 * 4) + (nNum76 * 3) + (nNum77 * 2) + (nNum78 * 7) + (nNum79 * 6) + (nNum80 * 5) + (nNum81 * 4) + (nNum82 * 3) + (nNum83 * 2);

        //--f_debug('Suma 2: '||to_CHAR(nSuma2));

        nResto2 = nSuma2 % 11;  //MOD(nSuma2, 11);
        nDigitoVer2 = 11 - nResto2;
        if (nDigitoVer2 == 10)// THEN
            nDigitoVer2 = 0;
        else
            if (nDigitoVer2 == 11)// THEN
                nDigitoVer2 = 1;
        //   end if;
        //end if;

        //p_digito1   = nDigitoVer1;
        //p_digito2   = nDigitoVer2;
        /* END */
        sLastTwoDigits = nDigitoVer1.toString() + nDigitoVer2.toString();
        return sLastTwoDigits;
    }

    public static String calculoDVPagos360(String codigoBarra){
        int nDigits = codigoBarra.length();

        int nSum = 0;
        boolean isSecond = false;
        for (int i = nDigits - 1; i >= 0; i--)
        {

            int d = codigoBarra.charAt(i) - '0';

            if (isSecond == true)
                d = d * 2;

            // We add two digits to handle
            // cases that make two digits
            // after doubling
            nSum += d / 10;
            nSum += d % 10;

            isSecond = !isSecond;
        }

		return Integer.toString(nSum % 10);
	}

	public static String fechaJuliana(Date fecha){

        DateFormat dt = new SimpleDateFormat("yyDDD");

        String fechaString = dt.format(fecha);

        return fechaString;

	}
}

