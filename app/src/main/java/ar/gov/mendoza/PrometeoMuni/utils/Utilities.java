package ar.gov.mendoza.PrometeoMuni.utils;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//import org.simpleframework.xml.Serializer;
//import org.simpleframework.xml.core.Persister;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import ar.gov.mendoza.PrometeoMuni.core.domain.ActaConstatacion;
import ar.gov.mendoza.PrometeoMuni.core.domain.Departamento;
import ar.gov.mendoza.PrometeoMuni.core.domain.Entidad;
import ar.gov.mendoza.PrometeoMuni.core.domain.Equipo;
import ar.gov.mendoza.PrometeoMuni.core.domain.FechasConfiguradas;
import ar.gov.mendoza.PrometeoMuni.core.domain.InfraccionClasificada;
import ar.gov.mendoza.PrometeoMuni.core.domain.InfraccionNomenclada;
import ar.gov.mendoza.PrometeoMuni.core.domain.Localidad;
import ar.gov.mendoza.PrometeoMuni.core.domain.Seccional;
import ar.gov.mendoza.PrometeoMuni.core.domain.SuportTable;
import ar.gov.mendoza.PrometeoMuni.core.domain.TipoVehiculo;
import ar.gov.mendoza.PrometeoMuni.core.util.Tools;
import ar.gov.mendoza.PrometeoMuni.rules.DepartamentoRules;
import ar.gov.mendoza.PrometeoMuni.rules.EntidadRules;
import ar.gov.mendoza.PrometeoMuni.rules.EquipoRules;
import ar.gov.mendoza.PrometeoMuni.rules.FechasConfiguradasRules;
import ar.gov.mendoza.PrometeoMuni.rules.InfraccionClasificadaRules;
import ar.gov.mendoza.PrometeoMuni.rules.InfraccionNomencladaRules;
import ar.gov.mendoza.PrometeoMuni.rules.LocalidadRules;
import ar.gov.mendoza.PrometeoMuni.rules.SeccionalRules;
import ar.gov.mendoza.PrometeoMuni.rules.SuportTableRules;
import ar.gov.mendoza.PrometeoMuni.rules.TipoVehiculoRules;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalStateApp;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalVar;

public  class Utilities {

	
	public static String NVLString(String valor)
	{
		if(valor==null)
			return "";
		else
			return valor.trim();
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
	public static byte[] ReadBytes(String sFileName)
	{
		Context context = GlobalStateApp.getInstance().getApplicationContext();
		Uri imageUriToSaveCameraImageTo = Uri.parse("file://" + sFileName);//.fromFile(new File(context.getFilesDir(), sFileName));
		try
		{
		return ReadBytes(imageUriToSaveCameraImageTo);
		}
		catch(IOException ex)
		{
			return null;
		}
	}
	public static byte[] ReadBytes(Uri uri) throws IOException
	{
		  Context context = GlobalStateApp.getInstance().getApplicationContext();
		  // this dynamically extends to take the bytes you read
          java.io.InputStream inputStream = context.getContentResolver().openInputStream(uri);
          
          ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

          // this is storage overwritten on each iteration with bytes
          int bufferSize = 1024;
          byte[] buffer = new byte[bufferSize];

          // we need to know how may bytes were read to write them to the byteBuffer
          int len = 0;
          while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
          }

          // and then we can return your byte array.
          return byteBuffer.toByteArray();
		//Uri imageUriToSaveCameraImageTo = Uri.fromFile(new File(QuizCreateActaActivity.this.getFilesDir(), strAvatarFilename));
	}
	public static ActaConstatacion CargarInformacionAdicional(ActaConstatacion pActa)
	{
		ActaConstatacion fullActa = pActa;
		
		Context context = GlobalStateApp.getInstance().getApplicationContext();
		String codigoBarra = pActa.getCodigoBarra();
		pActa.setCodigoBarra(codigoBarra);
		
		InfraccionNomencladaRules infraccionRules = new InfraccionNomencladaRules(context);

		Integer idInfraccion = pActa.getIdInfraccion1();
		if (idInfraccion!=null) 
		{
			try
			{
				InfraccionNomenclada oInfraccionNomenclada1 = infraccionRules.getInfraccionById(idInfraccion);
				pActa.setoInfraccionNomenclada1(oInfraccionNomenclada1);
			}
			catch(Exception ex)
			{
				
			}
		}
		
		idInfraccion = pActa.getIdInfraccion2();
		if (idInfraccion!=null) 
		{
			try
			{
				InfraccionNomenclada oInfraccionNomenclada2 = infraccionRules.getInfraccionById(idInfraccion);
				pActa.setoInfraccionNomenclada2(oInfraccionNomenclada2);
			}
			catch(Exception ex)
			{
				
			}
		}
		
		idInfraccion = pActa.getIdInfraccion3();
		if (idInfraccion!=null) 
		{
			try
			{
				InfraccionNomenclada oInfraccionNomenclada3 = infraccionRules.getInfraccionById(idInfraccion);
				pActa.setoInfraccionNomenclada3(oInfraccionNomenclada3);
			}
			catch(Exception ex)
			{
				
			}
		}
		
		idInfraccion = pActa.getIdInfraccion4();
		if (idInfraccion!=null) 
		{
			try
			{
				InfraccionNomenclada oInfraccionNomenclada4 = infraccionRules.getInfraccionById(idInfraccion);
				pActa.setoInfraccionNomenclada4(oInfraccionNomenclada4);
			}
			catch(Exception ex)
			{
				
			}
		}
		idInfraccion = pActa.getIdInfraccion5();
		if (idInfraccion!=null) 
		{
			try
			{
				InfraccionNomenclada oInfraccionNomenclada5 = infraccionRules.getInfraccionById(idInfraccion);
				pActa.setoInfraccionNomenclada5(oInfraccionNomenclada5);
			}
			catch(Exception ex)
			{
				
			}
		}
		idInfraccion = pActa.getIdInfraccion6();
		if (idInfraccion!=null) 
		{
			try
			{
				InfraccionNomenclada oInfraccionNomenclada6 = infraccionRules.getInfraccionById(idInfraccion);
				pActa.setoInfraccionNomenclada6(oInfraccionNomenclada6);
			}
			catch(Exception ex)
			{
				
			}
		}
		
		Integer idJuzgado = pActa.getIdJuzgado();
		if (idJuzgado!=null)
		{
			EntidadRules entidadRules = new EntidadRules(context);
			try
			{
				Entidad oEntidad = entidadRules.getEntidadById(idJuzgado);
				pActa.setoEntidad(oEntidad);
			}
			catch(Exception ex)
			{
				
			}
		}

		String idSeccional = pActa.getIdSeccional();
		if (idSeccional!=null)
		{
			SeccionalRules seccionalRules = new SeccionalRules(context);
			try
			{
				Seccional oSeccional = seccionalRules.getSeccionalById(idSeccional);
				pActa.setoSeccional(oSeccional);
			}
			catch(Exception ex)
			{
				
			}
		}
		
		Integer idDepartamentoInfraccion = pActa.getIdDepartamentoInfraccion();
		if (idDepartamentoInfraccion!=null)
		{
			DepartamentoRules departamentoRules = new DepartamentoRules(context);
			try
			{
				Departamento oDepartamentoInfraccion = departamentoRules.getDepartamentoById(idDepartamentoInfraccion);
				pActa.setoDepartamentoInfraccion(oDepartamentoInfraccion);
			}
			catch(Exception ex)
			{
				
			}
		}		
		Integer idLocalidadInfraccion = pActa.getIdLocalidadInfraccion();
		if (idLocalidadInfraccion!=null)
		{
			LocalidadRules localidadRules = new LocalidadRules(context);
			try
			{
				Localidad oLocalidadInfraccion = localidadRules.getLocalidadById(idLocalidadInfraccion);
				pActa.setoLocalidadInfraccion(oLocalidadInfraccion);
			}
			catch(Exception ex)
			{
				
			}
		}		
		
				
		String idTipoVehiculo = pActa.getIdTipoVehiculo();
		if (idTipoVehiculo!=null)
		{
			TipoVehiculoRules tipoVehiculoRules = new TipoVehiculoRules(context);
			try
			{
				TipoVehiculo oTipoVehiculo = tipoVehiculoRules.getTipoVehiculoById(idTipoVehiculo);
				pActa.setoTipoVehiculo(oTipoVehiculo);
			}
			catch(Exception ex)
			{
				
			}
		}
		/*
		String idSexo = pActa.getIdSexo();
		if(idSexo!=null)
		{
			GeneroRules generoRules = new GeneroRules(context);
			try
			{
				Genero oGenero= generoRules.getGeneroById(idSexo);
				pActa.setoGenero(oGenero);
			}
			catch(Exception ex)
			{
				
			}			
		}
		*/
	   /*
		String idSexoTitular = pActa.getIdSexoTitular();
		if(idSexoTitular!=null)
		{
			GeneroRules generoRules = new GeneroRules(context);
			try
			{
				Genero oGenero= generoRules.getGeneroById(idSexoTitular);
				pActa.setoGeneroTitular(oGenero);
			}
			catch(Exception ex)
			{
				
			}			
		}
		*/
		return fullActa;
	}
	public static Double ObtenerMontoConcursoConDescuento(Context pContext, boolean alcoholemia)
	{
		Double monto = 0.0;
    	Double ImporteNP = GlobalVar.getInstance().getSuportTable().getImporteNP();
    	Double PorcDescuento = GlobalVar.getInstance().getSuportTable().getPorcDescuento();

    	Integer CostoAsociadoConcurso;
    	if (alcoholemia){
			CostoAsociadoConcurso = GlobalVar.getInstance().getSuportTable().getUfAlcoholemia();
		} else{
			CostoAsociadoConcurso = GlobalVar.getInstance().getSuportTable().getUnidadesFijasConcurso();
		}

    	if (ImporteNP>0)
    	{
    		monto = CostoAsociadoConcurso * ImporteNP;
    		if (PorcDescuento>0)
    		{
    			monto = monto - (monto * PorcDescuento /100);
    		}
    	}
		return monto;
	}
	public static Double ObtenerMontoConcurso(Context pContext)
	{
		Double monto = 0.0;
    	Double ImporteNP = GlobalVar.getInstance().getSuportTable().getImporteNP();
    	Integer CostoAsociadoConcurso = GlobalVar.getInstance().getSuportTable().getUnidadesFijasConcurso();
    	if (ImporteNP>0)
    	{
    		monto = CostoAsociadoConcurso * ImporteNP;
    	}
		return monto;
	}

	public static Double ObtenerMonto(Context pContext,Integer pIdInfraccion)
	{
		Double monto = 0.0;
		if (pIdInfraccion== null) return monto;
		
	    InfraccionNomencladaRules infraccionRules = new InfraccionNomencladaRules(pContext);		
	    
	    InfraccionNomenclada infraccion = infraccionRules.getInfraccionById(pIdInfraccion);
	    if (infraccion!=null && infraccion.getId().equals(pIdInfraccion))
	    {
	    	Double ImporteNP = GlobalVar.getInstance().getSuportTable().getImporteNP();
	    	if (ImporteNP>0)
	    	{
	    		if (pIdInfraccion.equals(158) || pIdInfraccion == 158){
	    			monto = GlobalVar.getInstance().getSuportTable().getUfAlcoholemia() * ImporteNP;
				} else {
					monto = infraccion.getCostoAsociado() * ImporteNP;
				}
	    	}
	    }
		
		return monto;
	}
	public static Double ObtenerMontoConDescuento(Context pContext,Integer pIdInfraccion)
	{
		Double monto = 0.0;
		if (pIdInfraccion== null) return monto;
		
	    InfraccionNomencladaRules infraccionRules = new InfraccionNomencladaRules(pContext);		
	    
	    InfraccionNomenclada infraccion = infraccionRules.getInfraccionById(pIdInfraccion);
	    if (infraccion!=null && infraccion.getId().equals(pIdInfraccion))
	    {
	    	Double ImporteNP = GlobalVar.getInstance().getSuportTable().getImporteNP();
	    	Double PorcDescuento = GlobalVar.getInstance().getSuportTable().getPorcDescuento();
	    	if (ImporteNP>0)
	    	{
	    		monto = infraccion.getCostoAsociado() * ImporteNP;
	    		if (PorcDescuento>0)
	    		{
	    			
	    			monto = monto - (monto * PorcDescuento /100);
	    		}
	    	}
	    }
	    //aaqui va la lgogica
		
		return monto;
	}

	public static Double ConfigMontoTotal(Context pContext,Integer pIdInfraccion01, Integer pIdInfraccion02)
	{
		Double monto = 0.0;
	    Double MontoTemp= 0.0;
	    MontoTemp =	ObtenerMonto(pContext, pIdInfraccion01) + ObtenerMonto(pContext, pIdInfraccion02);
	    monto = monto + MontoTemp;
		
		return monto;
	}

	public static Double ConfigMontoTotalMendoza(Context pContext,List<Integer> lstIdInfracciones)
	{
		Double monto = 0.0;
	    Double MontoTemp= 0.0;
	    if(lstIdInfracciones.size()>0)
	    {
	    	if (lstIdInfracciones.size()>1)
	    	{
	    		MontoTemp = ObtenerMontoConcurso(pContext);
	    	}
	    	else
	    	{
	    		Integer pIdInfraccion01 = lstIdInfracciones.get(0); //obtenemos el monto de la unica infraccion cargada
	    	    MontoTemp =	ObtenerMonto(pContext, pIdInfraccion01);
	    	}
	    }
	    
	    monto = monto + MontoTemp;
		
		return monto;
		
	}
	public static Double ConfigMontoTotalMendozaConDescuento(Context pContext,List<Integer> lstIdInfracciones)
	{
		Double monto = 0.0;
	    Double MontoTemp= 0.0;
	    if(lstIdInfracciones.size()>0)
	    {
	    	if (lstIdInfracciones.size()>1)
	    	{
	    		if (lstIdInfracciones.contains(158)){
					MontoTemp = ObtenerMontoConcursoConDescuento(pContext, true);
				} else{
					MontoTemp = ObtenerMontoConcursoConDescuento(pContext, false);
				}
	    	}
	    	else
	    	{
	    		Integer pIdInfraccion01 = lstIdInfracciones.get(0); //obtenemos el monto de la unica infraccion cargada
	    	    MontoTemp =	ObtenerMontoConDescuento(pContext, pIdInfraccion01);
	    	}
	    }
	    
	    monto = monto + MontoTemp;
		
		return monto;
	}
	public static Boolean EsParaJuzgadoPolicial(Context pContext,Integer pIdInfraccion01, Integer pIdInfraccion02)
	{
		Boolean bRespuesta = false;
		
		if (pIdInfraccion01 == null && pIdInfraccion02==null) return false;
		
		InfraccionClasificadaRules infraccionRules = new InfraccionClasificadaRules(pContext);	
		
		if (pIdInfraccion01!=null && pIdInfraccion01 >0) 
		{
			// verificar si es juzgado Policial y si es asi retornar con true
			 InfraccionClasificada inf = infraccionRules.getInfraccionByIdClasificacion(pIdInfraccion01, "JUZGADO_POLICIAL");
			 if (inf!=null && inf.getId()>0)
			 {
				 return true;
			 }
		}
		if (pIdInfraccion02!=null && pIdInfraccion02 >0) 
		{
			// verificar si es juzgado Policial y si es asi retornar con true
			 InfraccionClasificada inf = infraccionRules.getInfraccionByIdClasificacion(pIdInfraccion02, "JUZGADO_POLICIAL");
			 if (inf!=null && inf.getId()>0)
			 {
				 return true;
			 }
		}
		return bRespuesta;
	}
	public static Date ConfigFechaVencimiento(Context pContext,int pIdJuzgado )
	{
			Date fechaVencimiento = Tools.Today(); 
			Calendar c = Calendar.getInstance();
			Integer dias = 15;  // por defecto son 15 dias habiles    se sumaran 15 dias a partir del dia actual  sin considerar los fines de semana ni las fechas laborables de algunos juzgados
			try
			{
				
				dias = GlobalVar.getInstance().getSuportTable().getCantDias1erVtoActa();
			}
			catch(Exception ex)
			{
				
			}
			
		    int diasLaborables =0;
			int maximoNumeroIteracciones = 60;
            int Iteracciones = 0;

            FechasConfiguradasRules  fechasRules = new FechasConfiguradasRules(pContext);
     		
			do
			{
				Iteracciones++;
				c.add(Calendar.DATE, 1);
				
				int diadelasemana = c.get(Calendar.DAY_OF_WEEK);
				 if (diadelasemana != 1 && diadelasemana != 7)
	             { // si no es Ni Domingo Ni Sabado  verificar si es un dia No Laborable (variable Hashtable)
					//verificamos los Feriados tambien
					 Date dtFechaAVerificar =c.getTime();
					 String sFechayyyMMdd = Tools.DateValueOf(dtFechaAVerificar, "yyyyMMdd");
					 List<FechasConfiguradas> lstFechas = fechasRules.getFechaConfiguradaByFecha(Integer.parseInt(sFechayyyMMdd), "FERIADO");		 
					 if (lstFechas ==null || lstFechas.isEmpty())
						 diasLaborables++; 
	             }
				 if (Iteracciones >= maximoNumeroIteracciones)
	             { //salimos del bucle, por demasaidas iteracciones, algun problema ha ocurrido
	                    break;
	             }
				//fechaVencimiento.
				
				
			} while(diasLaborables<dias); // mientras los dias laborables contabilizados sea menor a los dias configurados como minimos
            
			fechaVencimiento = c.getTime();
			return fechaVencimiento;
	}
	public static Date ConfigFechaVencimiento2do(Context pContext,int pIdJuzgado )
	{
			Date fechaVencimiento = Tools.Today(); 
			Calendar c = Calendar.getInstance();
			Integer dias = 30;  // por defecto son 15 dias habiles    se sumaran 15 dias a partir del dia actual  sin considerar los fines de semana ni las fechas laborables de algunos juzgados
			
			try
			{
				
				// como no esta configurada por suportable  dejamos 30dias
				dias = GlobalVar.getInstance().getSuportTable().getCantDias2doVtoActa();
				
			}
			catch(Exception ex)
			{
				
			}
			
		    int diasLaborables =0;
			int maximoNumeroIteracciones = 60;
            int Iteracciones = 0;
            FechasConfiguradasRules  fechasRules = new FechasConfiguradasRules(pContext);
			do
			{
				Iteracciones++;
				c.add(Calendar.DATE, 1);
				int diadelasemana = c.get(Calendar.DAY_OF_WEEK);
				 if (diadelasemana != 1 && diadelasemana != 7)
	             {  // si no es Ni Domingo Ni Sabado  verificar si es un dia No Laborable (variable Hashtable)
					//verificamos los Feriados tambien
					 Date dtFechaAVerificar =c.getTime();
					 String sFechayyyMMdd = Tools.DateValueOf(dtFechaAVerificar, "yyyyMMdd");
					 List<FechasConfiguradas> lstFechas = fechasRules.getFechaConfiguradaByFecha(Integer.parseInt(sFechayyyMMdd), "FERIADO");		 
					 if (lstFechas ==null || lstFechas.isEmpty())
						 diasLaborables++; 
	             }
				 if (Iteracciones >= maximoNumeroIteracciones)
	             { //salimos del bucle, por demasaidas iteracciones, algun problema ha ocurrido
	                    break;
	             }
				//fechaVencimiento.
				
				
			} while(diasLaborables<dias); // mientras los dias laborables contabilizados sea menor a los dias configurados como minimos
            
			fechaVencimiento = c.getTime();
			return fechaVencimiento;
	}
	public static void ConfigGlobalVars(Context pContext)
	{
		
		
		String identifier = null;
 		TelephonyManager tm = (TelephonyManager)pContext.getSystemService(Context.TELEPHONY_SERVICE);
 		if (tm != null)
 		      identifier = tm.getDeviceId();
 		
 		if (identifier == null || identifier .length() == 0){
			//identifier = Secure.getString(pContext.getContentResolver(),Secure.ANDROID_ID); // esto da un numero que no es el imei
			identifier = "356939100756724";
			GlobalVar.getInstance().setImei(identifier);
		}

 		int phoneType = tm.getPhoneType();
 		String TipoDispostivo="";
 		 switch(phoneType){
 		 case TelephonyManager.PHONE_TYPE_NONE:
 			TipoDispostivo = "NONE: " + identifier;
 			break;
 		 case TelephonyManager.PHONE_TYPE_GSM:
 			TipoDispostivo ="GSM: IMEI=" + identifier;
 			break;
 		 case TelephonyManager.PHONE_TYPE_CDMA:
 			TipoDispostivo = "CDMA: MEID/ESN=" + identifier;
 			break;
 		 /*
 		  *  for API Level 11 or above
 		  *  case TelephonyManager.PHONE_TYPE_SIP:
 		  *   return "SIP";
 		  */
 		 default:
 			TipoDispostivo =  "UNKNOWN: ID=" + identifier;
 		 }
 	    
 		String IMSI = tm.getSubscriberId();
 		Integer idMovil;
 		
 		// reemplazamos el imei por la 997 para hacer pruebas en el Dispositivo
 		
 		//identifier ="351932070524136";//"355177060242523";
 		//identifier = "355177060242523";
 
 		EquipoRules equipoRules = new EquipoRules(pContext);
		if(GlobalVar.getInstance().isBeingDebugged())
 		{
 			if (identifier.equals("355177060242523"))
 				equipoRules.setIdEquipoPrueba(identifier);
 		}
 
 		
 		Equipo equipo = equipoRules.getByImei(identifier);
 		idMovil = equipo.getIdEquipo();
 		

 		SuportTableRules suportTableRules = new SuportTableRules(pContext);
 		
 		// para configurar el PDA en zona RAC
 		//suportTableRules.setZona("RAC");
 		
 		
 		
 		
 		suportTableRules.setupVariables();
 		SuportTable suportTable = suportTableRules.getSuportTable();
 		
 		String location  = suportTable.getWsLocation();
 		
 		GlobalVar.getInstance().setIdMovil(idMovil);
 		GlobalVar.getInstance().setImei(identifier);
 		GlobalVar.getInstance().setImsi(IMSI);
 		GlobalVar.getInstance().setSuportTable(suportTable);
 		GlobalVar.getInstance().setVersionSoftwarePDA("CATS40-0001");
 		String sNumeroSerie = equipoRules.getNumeroSerie();
 		GlobalVar.getInstance().setNumeroSerie(sNumeroSerie);
 		String sLetraSerie = equipoRules.getLetraSerie();
 		GlobalVar.getInstance().setLetraSerie(sLetraSerie);

 		
	}
	public static void ShowToast(Context pContext,String pText)
	{
		
		Toast.makeText(pContext, pText,Toast.LENGTH_LONG).show();
		
	}

	public static void PersitirToFichero(Object objeto)
	{
		
		try
    	{

    		//Serializer serializer = new Persister();
    		File sdCardDirectory = Environment.getExternalStorageDirectory();
    		File result = new File(sdCardDirectory,"acta2.json");
    		java.io.OutputStream outputStream= new FileOutputStream(result);
    			 
    		try {
    		  //outputStream = openFileOutput(filename, Context.MODE_WORLD_READABLE);
    		  outputStream.write(objeto.toString().getBytes());
    		  
    		} catch (Exception e) {
    		  //e.printStackTrace();
    			String actaError = e.getMessage();
    		}
    		finally{
    			outputStream.close();
    		}
    	}
    	catch(Exception ex)
    	{
    		String msg = ex.getMessage();
    		msg ="";
    		
    	}
	}

	public static int diasHabilesACorridos(int diasHabiles){

		int diasCorridos = 0;

		Date fecha = new Date();
		Calendar c = Calendar.getInstance();

		c.setTime(fecha);

		do {
			c.add(Calendar.DATE, 1);

			if (Calendar.DAY_OF_WEEK > 1 && Calendar.DAY_OF_WEEK < 7){
				diasHabiles -= 1;
			}
			diasCorridos += 1;

		} while (diasHabiles > 0);


		return diasCorridos;
	}

	public static String formatearFecha (String fecha){
	   	String result = "";

	   	result = fecha.replace("T", " ");
	   	result = result.replace("Z", "");

	   	return result;
	}

}

