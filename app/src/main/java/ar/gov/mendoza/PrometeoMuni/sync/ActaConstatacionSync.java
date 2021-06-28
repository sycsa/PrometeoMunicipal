package ar.gov.mendoza.PrometeoMuni.sync;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import ar.gov.mendoza.PrometeoMuni.core.exceptions.DeviceActasSynchronizationException;

//import com.cids.siga.core.base.Enumeraciones.EstadoNovedadMovilEnum;
//import com.cids.siga.core.data.DaoUti;
import ar.gov.mendoza.PrometeoMuni.core.domain.*;

import ar.gov.mendoza.PrometeoMuni.core.util.Tools;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalVar;
import ar.gov.mendoza.PrometeoMuni.sync.dto.ActaConstatacionDTO;
import ar.gov.mendoza.PrometeoMuni.sync.dto.DtoUsuarioValidado;
import ar.gov.mendoza.PrometeoMuni.utils.Utilities;
//import com.cids.siga.sync.dto.DtoInicioInspeccion;
//import com.cids.siga.sync.dto.DtoInspeccion;


//public class ActaConstatacionSync extends SyncBase {
public class ActaConstatacionSync extends BaseRestSync {

	//OBJETOS DE BUSQUEDA
		public static String ACTA_CONSTATACION ="ACTA_CONSTATACION";
		
	//TIPOS DE BUSQUEDA
		public static String NUMERO_DOCUMENTO ="DOCUMENTO";
		public static String NUMERO_ACTA ="ACTA";
		
		
	public ActaConstatacionSync(Context context) {
		 super(context, "SyncActa");
	}

	
	private  String BuscarActa2XML(String pTipoBusqueda,String pDatoBusqueda)
    {
		//el TipoBusqueda es siempre por DOCUMENTO //por ahora no se usaria este parametro
        StringBuilder strBuilder = new StringBuilder();

        strBuilder.append("<root>");
        strBuilder.append("<values>");

        if(pTipoBusqueda.equals(NUMERO_ACTA))
        {
        	strBuilder.append("<nroActa>");
            strBuilder.append(pDatoBusqueda.trim());
            strBuilder.append("</nroActa>");
            strBuilder.append("<nroDocumento></nroDocumento>");
        }
        if(pTipoBusqueda.equals(NUMERO_DOCUMENTO))
        {
        	strBuilder.append("<nroActa></nroActa>");
	        strBuilder.append("<nroDocumento>");
	        strBuilder.append(pDatoBusqueda.trim());
	        strBuilder.append("</nroDocumento>");
        }
        strBuilder.append("</values>");
        
        strBuilder.append("</root>");
        return strBuilder.toString();
        
    }
	public List<ActaConstatacionDTO> BuscarActaConstatacion(String pTipoObjetoBusqueda,String pTipoBusqueda,String pDatoBusqueda)
	{
		List<ActaConstatacionDTO> listActaConstatacion = new ArrayList<>();
        JSONObject jsonActas;
        JSONArray jsonArray;
        HashMap<String, Object> values = new HashMap<String, Object> ();

		if (pTipoObjetoBusqueda.equals(ActaConstatacionSync.ACTA_CONSTATACION))
		{
			setServiceMethodName("getacta");

            values.put("tipoBusqueda", pTipoBusqueda);
            values.put("datoBusqueda", pDatoBusqueda);
		}
		/*else if(pTipoObjetoBusqueda.equals(VerificacionPolicialSync.AUTOMOTORES) || pTipoObjetoBusqueda.equals(VerificacionPolicialSync.MOTOVEHICULOS))
		{
			setServiceMethodName("ConsultaDatosdeVehiculo");
			sxml = rodado2XML(pTipoObjetoBusqueda, pTipoBusqueda, pDatoBusqueda);
		}
		else if(pTipoObjetoBusqueda.equals(VerificacionPolicialSync.LICENCIA))
		{
			return null;
		}*/
		else 
		{   
			return null;
		}

		try
		{	
			 String xmlEnc = super.callServiceRestGetGenerico(values);
            //String xmlEnc = "{\"items\":[{\"id_acta\":2335,\"acta\":\"M23\",\"fecha_labrado\":\"2019-09-05T14:12:05Z\",\"documento\":\"24606397\",\"nombre_apellido\":\"LUQUE, PEDRO HORACIO\",\"infraccion\":\"LICENCIA ADULTERADA O FALSEADA\"},{\"id_acta\":2335,\"acta\":\"M23\",\"fecha_labrado\":\"2019-09-05T14:12:05Z\",\"documento\":\"24606397\",\"nombre_apellido\":\"LUQUE, PEDRO HORACIO\",\"infraccion\":\"LICENCIA VENCIDA\"}],\"first\":{\"$ref\":\"http://2112soft.com.ar/desa/sav/pda/getacta/356939100756724\"}}\n";
		     if (xmlEnc.equals(""))
	         {
	             return null;
	         }
		     else{
		         jsonActas = new JSONObject(xmlEnc);
             }

		     if (jsonActas != null && jsonActas.length() > 0) {
                 for (int i = 0; i < jsonActas.length(); i++) {

                     ActaConstatacionDTO actaConstatacion = new ActaConstatacionDTO();

                     JSONArray items = jsonActas.getJSONArray("items");

                     actaConstatacion.setIdActaConstatacion(Integer.parseInt(items.getJSONObject(i).getString("id_acta")));
                     actaConstatacion.setNumeroActa(items.getJSONObject(i).getString("acta"));
                     try {
                         actaConstatacion.setFechaLabrado(Utilities.formatearFecha(items.getJSONObject(i).getString("fecha_labrado")));
                     }
                     catch (Exception e) {

                     }
                     actaConstatacion.setNumeroDocumento(items.getJSONObject(i).getString("documento"));
                     actaConstatacion.setApellido(items.getJSONObject(i).getString("nombre_apellido"));

                     listActaConstatacion.add(actaConstatacion);
                 }
             }
		}
		/*catch (DeviceActasSynchronizationException e) {
			e.printStackTrace();
		}*/
		catch (Exception e) {
			String m =	e.getMessage();
		}
		return listActaConstatacion;
	}	
	

	public Boolean sincronizarFotoActa(String pNumeroActa ,String pFoto, Bitmap bitmap)
	{
		Boolean bResultado = false;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] data = baos.toByteArray();

        Bitmap foto = BitmapFactory.decodeByteArray(data,0,data.length);

        String strFoto = Tools.encodeTobase64(foto);
		
		/*StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("<root><acta>");
        strBuilder.append("<NumeroActa>");
        strBuilder.append(pNumeroActa.trim());
        strBuilder.append("</NumeroActa>");
        strBuilder.append("<NombreArchivo>");
        strBuilder.append(pFoto);
        strBuilder.append("</NombreArchivo>");
        strBuilder.append("<ImgBase64>");
        
		strBuilder.append(strFoto);
		// la cambiamos por si se logro comprimir strNombreArchivoFoto);
        strBuilder.append("</ImgBase64>");
        strBuilder.append("</acta></root>");
        */
        HashMap<String, Object> values = new HashMap<String, Object> ();
        //String sxml = strBuilder.toString();
		//String parametro = sxml;//Proteger(sxml);
        values.put("acta", pNumeroActa);
		values.put("nombre_foto", pFoto);
		values.put("foto", strFoto);
        
		// hasta aqui vamos bien  
        setServiceMethodName("sync/images");

        try
		{	
			 //String xmlEnc = super.callService(values);
			 String xmlEnc = "";
			 //xmlEnc = super.callServiceRestGrabarFoto(pFoto, bitmap);
			 xmlEnc = super.callServiceRestGrabarFoto(values);

		     if (xmlEnc.equals(""))
	         {
	             return false;
	         }
	         else
	         {
	             xmlEnc = xmlEnc.replace(" ", "+");

                 JSONObject resultado = new JSONObject(xmlEnc);
                 String sincronizado = resultado.getString("result");

                 if (sincronizado == "TRUE" || sincronizado.equals("TRUE")){
                     bResultado = true;
                 }
                 else {
                     bResultado = false;
                 }
	         }

		     /*ArrayList<HashMap<String, Object>> valuesARetornar = new ArrayList<HashMap<String,Object>>();
			 
			 String xmlRespuesta = ProcesarRetorno(xmlEnc);
			 String patron = "//root/filas" ;///root/filas/fila
			 valuesARetornar = CargarValores(xmlRespuesta,patron);
			 if (!valuesARetornar.isEmpty())
			 {
				HashMap<String,Object> valores   = valuesARetornar.get(0);
				String result = valores.get("fila").toString();
				if(result!=null && (result.toUpperCase().trim().equals("OK") || result.equals("Nro de Acta ya existe en la base de datos")))
					return true;
			 }
		     
//		     if (xmlEnc.equals("true") || xmlEnc.toUpperCase().equals("TRUE"))
//		    	 return true;
//		     else
//		    	 return false;
		     
		      en este caso el web service devuelve true o false...
			 ArrayList<HashMap<String, Object>> valuesARetornar = new ArrayList<HashMap<String,Object>>();
			 
			 String xmlRespuesta = ProcesarRetorno(xmlEnc);
			 String patron = "//root/filas" ;///root/filas/fila
			 valuesARetornar = CargarValores(xmlRespuesta,patron);
			 if (!valuesARetornar.isEmpty())
			 {
				HashMap<String,Object> valores   = (HashMap<String,Object>) valuesARetornar.get(0);
				String result =valores.get("fila").toString();
				if(result.equals("ok") || result.equals("OK"))
					return true;
			 }
			 */
		} 
	/*	catch (DeviceActasSynchronizationException e) {
			e.printStackTrace();
		}*/
		catch (Exception e) {
			e.printStackTrace();
		}
		return bResultado;
	}

	public Boolean sincronizarActa(ActaConstatacion pActaConstatacion)
	{
		Boolean bResultado = false;
		//esta modificacion es por si hay mas de un servicio
		//setServiceNameMethodName("com.ktksuitelr.ktkmod.awssoapregistralog?wsdl");
		//setServiceMethodName("Execute");//SendActa");
		
		//Tools.WriteToLogTxt(pActaConstatacion,pActaConstatacion.getIdActaConstatacion().toString());
		
	
		
		pActaConstatacion = Utilities.CargarInformacionAdicional(pActaConstatacion);
		
		String sxml ;
		
		try {
			sxml = acta2XML(pActaConstatacion);
	
			//if (pActaConstatacion!=null) //esto es solo para pruebas
			//	return false;
		} catch (Exception e1) {
			
			Tools.WriteToLogTxt(pActaConstatacion,pActaConstatacion.getIdActaConstatacion().toString());
			return false;
		}
		
		//String parametro = Proteger(sxml);
	    HashMap<String, Object> values = new HashMap<String, Object> ();
		//values.put("Logobs", parametro);
		values.put("params", sxml);
		values.put("acta", pActaConstatacion.getNumeroActa());
		values.put("user", pActaConstatacion.getIdUsuarioPDA().toUpperCase());
		
		/* para restaurar Modo Depuracion
		String restModoDebug = "N";
		try { restModoDebug = GlobalVar.getInstance().getRestModoDebug();} catch(Exception e){restModoDebug = "N";}

		if (restModoDebug.equals("S"))
		{
			try
			{
				setServiceNameMethodName("wpregistraacta.php");
				
				String xmlEnc2 = super.callServiceRestGenericoPHP(values);
				xmlEnc2 ="";
				bResultado = false;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		*/
		setServiceMethodName("sync");

		try
		{
			 String xmlEnc = "";
			 xmlEnc = super.callServiceRestGetGenerico(values);

			 if (xmlEnc==null || xmlEnc.equals(""))
	         {
	             return false;
	         }
	         else
	         {
	        	 JSONObject resultado = new JSONObject(xmlEnc);
                 String sincronizado = resultado.getString("result");

                 if (sincronizado == "TRUE" || sincronizado.equals("TRUE")){
                     bResultado = true;
                 }
                 else {
                     bResultado = false;
                 }
	         }
			 //ArrayList<HashMap<String, Object>> valuesARetornar;
			 
			 {
				 FileWriter f;
				 try {
					  String strFilename = "xmlencriptado.txt";
					  File sdCardDirectory = Environment.getExternalStorageDirectory();
					  f = new FileWriter(new File(sdCardDirectory, strFilename));
				      f.write(xmlEnc);
				  f.flush();
				  f.close();
				 }
				 catch(Exception e)
				 {
					System.out.println("Error "  + e.getMessage()); 
				 }
			 }
			 
			 /*String xmlRespuesta = ProcesarRetorno(xmlEnc);
			 //xmlRespuesta = xmlEnc;
			 {
				 FileWriter f;
				 try {
				  String strFilename = "xmldescencriptado.txt";
				  File sdCardDirectory = Environment.getExternalStorageDirectory();
					
				  f = new FileWriter(new File(sdCardDirectory, strFilename));
				      f.write(xmlRespuesta);
				  f.flush();
				  f.close();
				 }
				 catch(Exception e)
				 {
					System.out.println("Error "  + e.getMessage()); 
				 }
			 }
			 
			 String patron = "//root/filas" ;///root/filas/fila
			 valuesARetornar = CargarValores(xmlRespuesta,patron);
			 if (!valuesARetornar.isEmpty())
			 {
				HashMap<String,Object> valores   = valuesARetornar.get(0);
				String result =valores.get("fila").toString();
				if(result !=null && (result.toLowerCase().equals("ok") || result.equals("iNro de Acta ya existe en la base de datos")))
					return true;
			 }*/
		} 
		catch (DeviceActasSynchronizationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bResultado;
	}
	private String generarXMLInfraccion(InfraccionNomenclada infraccion)
	{
		String strXMLRetorno ="";
		StringBuilder sbXMLInfraccion = new StringBuilder();
		
		sbXMLInfraccion.append("<Infraccion>");
		
		sbXMLInfraccion.append("<Codigo>");
		sbXMLInfraccion.append(infraccion.getId());
		sbXMLInfraccion.append("</Codigo>");
		
		sbXMLInfraccion.append("<Descripcion>");
		sbXMLInfraccion.append(this.encodeValue(Utilities.NVLString(infraccion.getDescripcion().trim())));
		sbXMLInfraccion.append("</Descripcion>");

	
		sbXMLInfraccion.append("</Infraccion>");

		strXMLRetorno = sbXMLInfraccion.toString();
		return strXMLRetorno;
	}
	
	private  String acta2XML(ActaConstatacion acta) throws Exception
    {
        StringBuilder strBuilder = new StringBuilder();

    try 
    {
        //String  formatoFechaHoraMC7596 = "dd/MM/yyyy hh:mm:ss";
    	String  formatoFechaHora = "dd/MM/yyyy HH:mm:ss";
    	
    	//String  formatoFechaHora_en_US = "MM/dd/yyyy h:mm:ss a";
    	String  formatoFecha = "dd/MM/yyyy";
        
    	
    	strBuilder.append("<SDTActaCaminera xmlns='VialMza'>");
    	strBuilder.append("<Acta>");
    	strBuilder.append("<Id_Acta>").append(acta.getIdActaConstatacion()).append("</Id_Acta>");
    	strBuilder.append("<Nro_Acta>").append(acta.getNumeroActa()).append("</Nro_Acta>");
    	strBuilder.append("<Letra_Serie>").append(acta.getLetraSerie()).append("</Letra_Serie>");
    	/*
    	String idTipoRuta = acta.getIdTipoRuta();
    	String tipoRuta = acta.getTipoRuta();
    	String descripcionRuta = acta.getNumeroRuta();
    	String kmRutaoAltura = acta.getKm();
    	*/
    	String sDescripcionLugarInfraccion ="";
    	try{
    		sDescripcionLugarInfraccion = this.encodeValue(Utilities.NVLString(acta.getDescripcionUbicacion().trim()));
    	}catch(Exception ex){
    	}

        String strLocalidadInfraccion="";
        try{
        	strLocalidadInfraccion = acta.getIdLocalidadInfraccion().toString();
        }catch(Exception ex){}
        
        String strDepartamentoInfraccion ="";
        try{
        	strDepartamentoInfraccion = acta.getIdDepartamentoInfraccion().toString();
        }catch(Exception ex){}
       
        strBuilder.append("<Infraccion_Departamento>").append(strDepartamentoInfraccion).append("</Infraccion_Departamento>");
        strBuilder.append("<Infraccion_Localidad>").append(strLocalidadInfraccion).append("</Infraccion_Localidad>");
        
    	strBuilder.append("<Infraccion_Lugar>").append(acta.getTipoRuta().trim().toUpperCase()).append("</Infraccion_Lugar>");
    	strBuilder.append("<Infraccion_Numero_Ruta>").append(acta.getNumeroRuta().trim()).append("</Infraccion_Numero_Ruta>");
    	strBuilder.append("<Infraccion_Descripcion_Lugar>").append(sDescripcionLugarInfraccion).append("</Infraccion_Descripcion_Lugar>");    	
    	strBuilder.append("<Infraccion_KmAltura>").append(acta.getKm().trim()).append("</Infraccion_KmAltura>");
    	
    	strBuilder.append("<Infraccion_Referencia>").append(this.encodeValue(Utilities.NVLString(acta.getReferencia())).trim()).append("</Infraccion_Referencia>");
    	//strBuilder.append("<Infraccion_Localidad>").append("").append("</Infraccion_Localidad>");
    	//strBuilder.append("<Infraccion_Depto>").append("").append("</Infraccion_Depto>");
    	
    	
    	String sFechaLabrado = Tools.DateValueOf(acta.getFechaHoraLabrado(),formatoFechaHora).trim();
    	
    	strBuilder.append("<Fec_Hora_Labrada>").append(sFechaLabrado).append("</Fec_Hora_Labrada>");
    	
        String sFechaVencimientoCupon = Tools.DateValueOf(acta.getFechaVencimientoCupon(),formatoFecha);
        
        strBuilder.append("<Fec_Vencimiento>").append(sFechaVencimientoCupon).append("</Fec_Vencimiento>");
        strBuilder.append("<Retiene_Licencia>").append(acta.getLicenciaRetendia().trim()).append("</Retiene_Licencia>");
        
        strBuilder.append("<Retiene_Vehiculo>").append(acta.getVehiculoRetenido().trim()).append("</Retiene_Vehiculo>");
        strBuilder.append("<Deja_Copia>").append(acta.getDejaCopia().trim()).append("</Deja_Copia>");
        
        TipoVehiculo tipoVehiculo = acta.getoTipoVehiculo();
        String sNombreTipoVehiculo ="";
        if (tipoVehiculo!=null)
        	sNombreTipoVehiculo = tipoVehiculo.getNombre().trim();
        
        strBuilder.append("<TipoVehiculo>").append(sNombreTipoVehiculo).append("</TipoVehiculo>");
        
        String idTipoPatente =  acta.getIdTipoPatente();
        strBuilder.append("<TipoPatente>").append(acta.getDominio().trim()).append("</TipoPatente>");
        
        strBuilder.append("<ConduccionPeligrosa>").append(acta.getConduccionPeligrosa().trim()).append("</ConduccionPeligrosa>");
        strBuilder.append("<Dominio>").append(acta.getDominio().trim()).append("</Dominio>");
        strBuilder.append("<Marca>").append(this.encodeValue(acta.getMarca().trim().toUpperCase())).append("</Marca>");
        strBuilder.append("<Color>").append(this.encodeValue(acta.getColor().trim().toUpperCase())).append("</Color>");
        String sModeloVehiculo ="";
        try {
        	sModeloVehiculo = acta.getModeloVehiculo().trim().toUpperCase();
        }catch(Exception ex){
        }
        strBuilder.append("<ModeloVehiculo>").append(this.encodeValue(sModeloVehiculo)).append("</ModeloVehiculo>");

        /* Inicio Datos Titular */
        strBuilder.append("<SinTitular>").append(acta.getSinTitular().trim()).append("</SinTitular>");
        strBuilder.append("<Tipo_Dni_Propietario>").append(Utilities.NVLString(acta.getTipoDocumentoTitular()).trim()).append("</Tipo_Dni_Propietario>");
        strBuilder.append("<Dni_Propietario>").append(Utilities.NVLString(acta.getNumeroDocumentoTitular())).append("</Dni_Propietario>");
        strBuilder.append("<Nombre_Propietario>").append(this.encodeValue(Utilities.NVLString(acta.getNombreTitular()))).append("</Nombre_Propietario>");
        strBuilder.append("<Apellido_Propietario>").append(this.encodeValue(Utilities.NVLString(acta.getApellidoTitular()))).append("</Apellido_Propietario>");
        strBuilder.append("<Razon_Social_Propietario>").append("").append("</Razon_Social_Propietario>");
        strBuilder.append("<Calle_Propietario>").append(this.encodeValue(Utilities.NVLString(acta.getCalleTitular()))).append("</Calle_Propietario>");
        strBuilder.append("<Num_Calle_Propietario>").append(this.encodeValue(Utilities.NVLString(acta.getAlturaTitular()))).append("</Num_Calle_Propietario>");
        
        strBuilder.append("<Piso_Propietario>").append(this.encodeValue(Utilities.NVLString(acta.getPisoTitular()))).append("</Piso_Propietario>");
        strBuilder.append("<DeptoDomicilio_Propietario>").append(this.encodeValue(Utilities.NVLString(acta.getDepartamentoTitular()))).append("</DeptoDomicilio_Propietario>");
        strBuilder.append("<Barrio_Propietario>").append(this.encodeValue(Utilities.NVLString(acta.getBarrioTitular()))).append("</Barrio_Propietario>");
        strBuilder.append("<Anio>").append(this.encodeValue(Utilities.NVLString(acta.getAnioModeloVehiculo()))).append("</Anio>");
        
        
        String strLocalidad="";
        try{
        strLocalidad = acta.getLocalidadDomicilioTitular();
        if (acta.getIdLocalidadDomicilioTitular() != -1) strLocalidad = acta.getIdLocalidadDomicilioTitular().toString(); 
        }catch(Exception ex){}
        
        String strDepartamento ="";
        try{
        strDepartamento = acta.getDepartamentoDomicilioTitular();
        if (acta.getIdDepartamentoDomicilioTitular() != -1) strDepartamento= acta.getIdDepartamentoDomicilioTitular().toString();
        }catch(Exception ex){}
        
        String strProvincia ="";
        try{
        strProvincia = acta.getProvinciaDomicilioTitular();
        if (!acta.getIdProvinciaDomicilioTitular().equals("-1")) strProvincia= acta.getIdProvinciaDomicilioTitular();
        }catch(Exception ex){}
        
        String strPais ="";
        try{
        strPais = acta.getPaisDomicilioTitular();
        if (!acta.getIdPaisDomicilioTitular().equals("-1")) strPais = acta.getIdPaisDomicilioTitular();
        }catch(Exception ex){}
        
        strBuilder.append("<Localidad_Propietario>").append(this.encodeValue(Utilities.NVLString(strLocalidad))).append("</Localidad_Propietario>");
        strBuilder.append("<Depto_Propietario>").append(Utilities.NVLString(strDepartamento)).append("</Depto_Propietario>");
        strBuilder.append("<Pcia_Propietario>").append(Utilities.NVLString(strProvincia)).append("</Pcia_Propietario>");
        strBuilder.append("<Pais_Propietario>").append(Utilities.NVLString(strPais)).append("</Pais_Propietario>");
        
        /* Fin Datos Titular */
        /* Inicio Datos Infractor */
        strBuilder.append("<SinConductor>").append(acta.getSinConductor().trim()).append("</SinConductor>");
        strBuilder.append("<Tipo_Dni_Infractor>").append(Utilities.NVLString(acta.getTipoDocumento())).append("</Tipo_Dni_Infractor>");
        strBuilder.append("<Dni_Infractor>").append(Utilities.NVLString(acta.getNumeroDocumento())).append("</Dni_Infractor>");
        strBuilder.append("<Nombre_Infractor>").append(this.encodeValue(Utilities.NVLString(acta.getNombre()))).append("</Nombre_Infractor>");
        strBuilder.append("<Apellido_Infractor>").append(this.encodeValue(Utilities.NVLString(acta.getApellido()))).append("</Apellido_Infractor>");
        strBuilder.append("<Telefono>").append(this.encodeValue(Utilities.NVLString(acta.getTelefonoConductor()))).append("</Telefono>");
        strBuilder.append("<Email>").append(this.encodeValue(Utilities.NVLString(acta.getEmailConductor()))).append("</Email>");
        strBuilder.append("<Alcoholemia>").append(this.encodeValue(Utilities.NVLString(acta.getGrad_alcohol()))).append("</Alcoholemia>");

        strBuilder.append("<Calle_Infractor>").append(this.encodeValue(Utilities.NVLString(acta.getCalle()))).append("</Calle_Infractor>");
        strBuilder.append("<Num_Calle_Infractor>").append(this.encodeValue(Utilities.NVLString(acta.getAltura()))).append("</Num_Calle_Infractor>");
        
        strBuilder.append("<Piso_Infractor>").append(this.encodeValue(Utilities.NVLString(acta.getPiso()))).append("</Piso_Infractor>");
        strBuilder.append("<DeptoDomicilio_Infractor>").append(this.encodeValue(Utilities.NVLString(acta.getDepartamento()))).append("</DeptoDomicilio_Infractor>");
        strBuilder.append("<Barrio_Infractor>").append(this.encodeValue(Utilities.NVLString(acta.getBarrio()))).append("</Barrio_Infractor>");

        strLocalidad ="";
        try{
        strLocalidad = acta.getLocalidadDomicilio();
        if (acta.getIdLocalidadDomicilio() != -1) strLocalidad = acta.getIdLocalidadDomicilio().toString(); 
        } catch(Exception ex){}
        
        strDepartamento ="";
        try{
        strDepartamento = acta.getDepartamentoDomicilio();
        if (acta.getIdDepartamentoDomicilio() != -1) strDepartamento= acta.getIdDepartamentoDomicilio().toString();
        } catch(Exception ex){}
        
        strProvincia = "";
        try{
        strProvincia = acta.getProvinciaDomicilio();
        if (!acta.getIdProvinciaDomicilio().equals("-1")) strProvincia= acta.getIdProvinciaDomicilio();
        }catch(Exception ex){}
        
        strPais= "";
        try{
        strPais = acta.getPaisDomicilio();
        if (!acta.getIdPaisDomicilio().equals("-1")) strPais = acta.getIdPaisDomicilio();
        }catch(Exception ex){}

        
        strBuilder.append("<Localidad_Infractor>").append(this.encodeValue(Utilities.NVLString(strLocalidad))).append("</Localidad_Infractor>");
        strBuilder.append("<Depto_Infractor>").append(Utilities.NVLString(strDepartamento)).append("</Depto_Infractor>");
        strBuilder.append("<Pcia_Infractor>").append(Utilities.NVLString(strProvincia)).append("</Pcia_Infractor>");
        strBuilder.append("<Pais_Infractor>").append(Utilities.NVLString(strPais)).append("</Pais_Infractor>");
        
        /* Fin Datos Infractor */
        
        String sNumeroLicencia = acta.getNumeroLicencia()==null?"":acta.getNumeroLicencia().trim();
        sNumeroLicencia = this.encodeValue(Utilities.NVLString(sNumeroLicencia));
        
        strBuilder.append("<Numero_Licencia>").append(sNumeroLicencia).append("</Numero_Licencia>");
        
        String sClaseLicencia =acta.getClaseLicencia()==null?"":acta.getClaseLicencia().trim();
        sClaseLicencia = this.encodeValue(Utilities.NVLString(sClaseLicencia));
        strBuilder.append("<Categoria_Licencia>").append(sClaseLicencia).append("</Categoria_Licencia>");
        
        String sFechaVencimientoLicencia ="";
        if(acta.getFechaVencimientoLicencia()!=null)
        	sFechaVencimientoLicencia = Tools.DateValueOf(acta.getFechaVencimientoLicencia(),formatoFecha).trim();

        strBuilder.append("<Vencimiento_Licencia>").append(sFechaVencimientoLicencia).append("</Vencimiento_Licencia>");
        
        String procedencia = "";
        try
        {
        	procedencia = Utilities.NVLString(acta.getPaisLicencia()) + " " + Utilities.NVLString(acta.getProvinciaLicencia());
        }catch(Exception ex)
        {
        	
        }
        strBuilder.append("<Procedencia_Licencia>").append( this.encodeValue(procedencia)).append("</Procedencia_Licencia>");

        /* INICIO DATOS DEL TESTIGO */
        strBuilder.append("<SinTestigo>").append(acta.getSinTestigo().trim()).append("</SinTestigo>");
        strBuilder.append("<Tipo_Dni_Testigo>").append(Utilities.NVLString(acta.getTipoDocumentoTestigo())).append("</Tipo_Dni_Testigo>");
        strBuilder.append("<Dni_Testigo>").append(Utilities.NVLString(acta.getNumeroDocumentoTestigo())).append("</Dni_Testigo>");
        //strBuilder.append("<Sexo_Testigo>").append(acta.getIdSexoTitular().trim()).append("</Sexo_Testigo>");
        strBuilder.append("<Nombre_Testigo>").append(this.encodeValue(Utilities.NVLString(acta.getNombreTestigo()))).append("</Nombre_Testigo>");
        strBuilder.append("<Apellido_Testigo>").append(this.encodeValue(Utilities.NVLString(acta.getApellidoTestigo()))).append("</Apellido_Testigo>");
        //strBuilder.append("<Razon_Social_Testigo>").append("S/D").append("</Razon_Social_Testigo>");
        strBuilder.append("<Calle_Testigo>").append(this.encodeValue(Utilities.NVLString(acta.getCalleTestigo()))).append("</Calle_Testigo>");
        strBuilder.append("<Calle_Nro_Testigo>").append(this.encodeValue(Utilities.NVLString(acta.getAlturaTestigo()))).append("</Calle_Nro_Testigo>");
        
        strBuilder.append("<Piso_Testigo>").append(this.encodeValue(Utilities.NVLString(acta.getPisoTestigo()))).append("</Piso_Testigo>");
        strBuilder.append("<DeptoDomicilio_Testigo>").append(this.encodeValue(Utilities.NVLString(acta.getDepartamentoTestigo()))).append("</DeptoDomicilio_Testigo>");
        strBuilder.append("<Barrio_Testigo>").append(this.encodeValue(Utilities.NVLString(acta.getBarrioTestigo()))).append("</Barrio_Testigo>");

        strLocalidad ="";
        try{
        strLocalidad = acta.getLocalidadDomicilioTestigo();
        if (acta.getIdLocalidadDomicilioTestigo() != -1) strLocalidad = acta.getIdLocalidadDomicilioTestigo().toString(); 
        }catch(Exception ex){}
        
        strDepartamento ="";
        try{
        strDepartamento = acta.getDepartamentoDomicilioTestigo();
        if (acta.getIdDepartamentoDomicilioTestigo() != -1) strDepartamento= acta.getIdDepartamentoDomicilioTestigo().toString();
        }catch(Exception ex){}
        
        strProvincia = "";
        try{
        strProvincia = acta.getProvinciaDomicilioTestigo();
        if (!acta.getIdProvinciaDomicilioTestigo().equals("-1")) strProvincia= acta.getIdProvinciaDomicilioTestigo();
        } catch(Exception ex){}
       
        strPais = "";
        try{
        strPais = acta.getPaisDomicilioTestigo();
        if (!acta.getIdPaisDomicilioTestigo().equals("-1")) strPais= acta.getIdPaisDomicilioTestigo();
        } catch(Exception ex){}

        
        strBuilder.append("<Localidad_Testigo>").append(this.encodeValue(Utilities.NVLString(strLocalidad))).append("</Localidad_Testigo>");
        strBuilder.append("<Depto_Testigo>").append(Utilities.NVLString(strDepartamento)).append("</Depto_Testigo>");
        strBuilder.append("<Pcia_Testigo>").append(Utilities.NVLString(strProvincia)).append("</Pcia_Testigo>");
        strBuilder.append("<Pais_Testigo>").append(Utilities.NVLString(strPais)).append("</Pais_Testigo>");
        
        strBuilder.append("<ManifestacionTestigo>");
        try
        {
          strBuilder.append(this.encodeValue(Utilities.NVLString(acta.getManifestacionTestigo())));
        } catch(Exception ex)
        {}
        strBuilder.append("</ManifestacionTestigo>");
        /* FIN DATOS DEL TESTIGO */
        
        
        
        strBuilder.append("<Infracciones>");
        String xmlInfraccion ="";
    
        if (acta.getoInfraccionNomenclada1()!=null){	
        	xmlInfraccion = generarXMLInfraccion(acta.getoInfraccionNomenclada1());
        	strBuilder.append(xmlInfraccion);
        }
        if (acta.getoInfraccionNomenclada2()!=null){	
        	xmlInfraccion = generarXMLInfraccion(acta.getoInfraccionNomenclada2());
        	strBuilder.append(xmlInfraccion);
        }
        
        if (acta.getoInfraccionNomenclada3()!=null){	
        	xmlInfraccion = generarXMLInfraccion(acta.getoInfraccionNomenclada3());
        	strBuilder.append(xmlInfraccion);
        }
        if (acta.getoInfraccionNomenclada4()!=null){	
        	xmlInfraccion = generarXMLInfraccion(acta.getoInfraccionNomenclada4());
        	strBuilder.append(xmlInfraccion);
        }
        if (acta.getoInfraccionNomenclada5()!=null){	
        	xmlInfraccion = generarXMLInfraccion(acta.getoInfraccionNomenclada5());
        	strBuilder.append(xmlInfraccion);//Utilities.NVLString(xmlInfraccion)));
        }
        if (acta.getoInfraccionNomenclada6()!=null){	
        	xmlInfraccion = generarXMLInfraccion(acta.getoInfraccionNomenclada6());
        	strBuilder.append(xmlInfraccion);//this.encodeValue(Utilities.NVLString(xmlInfraccion))
        }
        strBuilder.append("</Infracciones>");
        
        strBuilder.append("<Observacion>");
        try
        {
          strBuilder.append(this.encodeValue(Utilities.NVLString(acta.getObservaciones())));
        } catch(Exception ex)
        {}
        strBuilder.append("</Observacion>");
        
        
        strBuilder.append("<Cod1_Barra>").append(acta.getCodigoBarra()).append("</Cod1_Barra>");
        strBuilder.append("<Cod2_Barra>").append(acta.getCodigoBarra2Do()).append("</Cod2_Barra>");
  
        strBuilder.append("<Cod1_Barra_Descuento>").append(acta.getCodigoBarra()).append("</Cod1_Barra_Descuento>");
        strBuilder.append("<Cod2_Barra_Descuento>").append(acta.getCodigoBarra2Do()).append("</Cod2_Barra_Descuento>");

        strBuilder.append("<Id_Movil>").append(acta.getIdMovil().toString().trim()).append("</Id_Movil>");
        strBuilder.append("<Id_Usuario_PDA>").append(acta.getIdUsuarioPDA().trim()).append("</Id_Usuario_PDA>");
        strBuilder.append("<Lat>").append(this.encodeValue(acta.getLatitud().trim())).append("</Lat>");
        strBuilder.append("<Lon>").append(this.encodeValue(acta.getLongitud().trim())).append("</Lon>");
        String lIMEI = GlobalVar.getInstance().getImei();// Global.GlobalVar.IMEI;
        strBuilder.append("<imei>").append(lIMEI).append("</imei>");
        
        String sJuzgado = acta.getIdJuzgado().toString().trim();
       
        strBuilder.append("<Juzgado>").append(this.encodeValue(sJuzgado)).append("</Juzgado>");
        strBuilder.append("<Juzgado_Calle>").append(this.encodeValue(acta.getCalleJuzgado().trim())).append("</Juzgado_Calle>");
        strBuilder.append("<Juzgado_Numero>").append(this.encodeValue(acta.getAlturaJuzgado().trim())).append("</Juzgado_Numero>");
        strBuilder.append("<Juzgado_Localidad>").append(this.encodeValue(acta.getLocalidadJuzgado().trim())).append("</Juzgado_Localidad>");
        strBuilder.append("<Juzgado_CP>").append(acta.getCodigoPostalJuzgado().trim()).append("</Juzgado_CP>");
        
        String sSeccional = acta.getIdSeccional().toString();
        strBuilder.append("<Id_Seccional>").append(this.encodeValue(sSeccional)).append("</Id_Seccional>");
        
        strBuilder.append("</Acta>");
        strBuilder.append("</SDTActaCaminera>");
    	}
        catch(Exception ex)
        {
        	Tools.Log(Log.ERROR, "ActasConstatacionSync", "Acta2XML : " + strBuilder.toString());
        	Tools.WriteToLogTxt(strBuilder, acta.getNumeroActa());
        	 throw new Exception("Error al Cargar el Acta en XML");
        }
    	
        return strBuilder.toString();
    }
	public DtoUsuarioValidado validarUsuarioEnRepat(String userName, String passWord)
	{
		//passWord = Proteger(passWord);
		DtoUsuarioValidado dtoUsuarioValidado = new DtoUsuarioValidado();

		setServiceMethodName("login");

        //String sxml = strb.toString();
        //String passwordEncr = Proteger(passWord);
        HashMap<String, Object> values = new HashMap<String, Object> ();
		values.put("username", userName);
		values.put("password", passWord);
		String usuario;
		String habilitado;
	
	try
	{	
		 String xmlEnc;
 		 xmlEnc = super.callServiceRestGetGenerico(values);
	     if (xmlEnc.equals(""))
         {
            return dtoUsuarioValidado;
         }
         else
         {/*
        	 String strPrimerParte = buscarCadenaKLK(xmlEnc); 
        	 xmlEnc = strPrimerParte;
             xmlEnc = xmlEnc.replace(" ", "+");*/
			 try{
			 	JSONObject jsonUsuario = new JSONObject(xmlEnc);
				habilitado = jsonUsuario.getString("allowed");
				usuario = jsonUsuario.getString("nombre");
			 }
			 catch(org.json.JSONException e) {
				 habilitado = "NO";
				 usuario = "";
			 }
        }
        dtoUsuarioValidado.habilitado = habilitado;
	    dtoUsuarioValidado.apellidoNombre = usuario;
        dtoUsuarioValidado.idUsuario = values.get("username").toString();
        dtoUsuarioValidado.clave = values.get("password").toString();

		if (dtoUsuarioValidado.idUsuario!= null && dtoUsuarioValidado.idUsuario.trim().length()> 0)
		{
			dtoUsuarioValidado.login = dtoUsuarioValidado.idUsuario;
		}

		 /*ArrayList<HashMap<String, Object>> valuesARetornar = new ArrayList<HashMap<String,Object>>();
		 
		 String xmlRespuesta = ProcesarRetorno(xmlEnc);
		 String patron = "//root/filas/fila";///fila" ;
		 valuesARetornar = CargarValores(xmlRespuesta,patron);
		 if (!valuesARetornar.isEmpty())
		 {
			HashMap<String,Object> valores   = valuesARetornar.get(0);
			try
			{
				dtoUsuarioValidado.idUsuario = valores.get("idUsuario").toString();//Integer.parseInt(valores.get("idUsuario").toString());
			} catch(Exception ex){} 
			if (dtoUsuarioValidado.idUsuario!= null && dtoUsuarioValidado.idUsuario.trim().length()> 0)
			{
				dtoUsuarioValidado.login = dtoUsuarioValidado.idUsuario; //valores.get("Login").toString();
				//String sclave =  valores.get("Clave").toString();
				//dtoUsuarioValidado.clave = Proteger(sclave);
				//dtoUsuarioValidado.bloqueado = valores.get("Bloqueado").toString();
				if(valores.get("estado").toString().equals("Habilitado"))
					dtoUsuarioValidado.bloqueado = "N";
				else
					dtoUsuarioValidado.bloqueado = "S";
				
				//dtoUsuarioValidado.passIngresadoEncrip = (passWord);//valores.get("PassIngresadoEncrip").toString();
				dtoUsuarioValidado.apellidoNombre = valores.get("ApellidoNombre").toString();
			}
		 }*/
	}
	catch (DeviceActasSynchronizationException e) {
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return dtoUsuarioValidado;
		
	}

	public String validarNumeroActa() throws SecurityException, NoSuchMethodException {
		
		 setServiceMethodName("validaracta");
		 
		 String param = "";
		
		 String numeroActa = "";

		HashMap<String, Object> values = new HashMap<String, Object> ();
		values.put("param", param);

		String xmlRespuesta2 =""; 				
			try
			{

				String xmlEnc2 = super.callServiceRestGetGenerico(values);

                if (xmlEnc2.equals(""))
                {
                    return numeroActa;
                }
                else
                {
                    JSONObject jsonActa = new JSONObject(xmlEnc2);
                    numeroActa = jsonActa.getString("result");
                }

			}

		catch (Exception e) {
			// TODO: handle exception
			String sError = e.getMessage();
		}
		
		return numeroActa;
	}
	
	public ActaConstatacionDTO getActa(String pNumeroActa)
	{
		List<ActaConstatacionDTO> listActaConstatacion = new ArrayList<>();
        ActaConstatacionDTO acta = new  ActaConstatacionDTO();
        JSONObject jsonActa;

		setServiceMethodName("getactaverificacion");
        
        HashMap<String, Object> values = new HashMap<String, Object> ();
        
		values.put("acta", pNumeroActa);

		try
		{	
			 String xmlEnc = super.callServiceRestGetGenerico(values);
			 
			 
		     if (xmlEnc.equals(""))
	         {
	             return null;
	         }
             else{
                 jsonActa = new JSONObject(xmlEnc);
             }

            if (jsonActa != null && jsonActa.length() > 0) {

                ActaConstatacionDTO actaConstatacion = new ActaConstatacionDTO();

                actaConstatacion.setIdActaConstatacion(Integer.parseInt(jsonActa.getString("id_acta")));
                actaConstatacion.setNumeroActa(jsonActa.getString("acta"));
                try {
                    actaConstatacion.setFechaLabrado(Utilities.formatearFecha(jsonActa.getString("fecha_labrado")));
                }
                catch (Exception e) {

                }
                actaConstatacion.setNumeroDocumento(jsonActa.getString("documento"));
                actaConstatacion.setApellido(jsonActa.getString("nombre_apellido"));


                listActaConstatacion.add(actaConstatacion);

            }

			 /*
					    actaConstatacion.setRuta(valores.get("ruta").toString());
					    actaConstatacion.setNumeroRuta(valores.get("numeroruta").toString());
					    actaConstatacion.setKm(valores.get("km").toString());
					   
					    actaConstatacion.setObservaciones(valores.get("observaciones").toString());
					    /*try
					    {
					    	String pCodigoInfraccion = valores.get("CODIGOINFRACCION1").toString();
					    	actaConstatacion.setCodigoInfraccion1(pCodigoInfraccion);
					    	Integer pIdInfraccion = Integer.parseInt(valores.get("ID_INFRACCION1").toString());
					    	InfraccionNomencladaRules infracionRules = new InfraccionNomencladaRules(GlobalStateApp.getInstance().getApplicationContext()) ;
					    	InfraccionNomenclada infraccion = infracionRules.getInfraccionByCodigo(pCodigoInfraccion);					    	actaConstatacion.setDescripcionInfraccion1(infraccion.getDescripcion());
					    }
					    catch(Exception ex)
					    {
					    	
					    }
					    
					    try
					    {
					    	String pCodigoInfraccion = valores.get("CODIGOINFRACCION2").toString();
					    	actaConstatacion.setCodigoInfraccion2(pCodigoInfraccion);
					    	Integer pIdInfraccion = Integer.parseInt(valores.get("ID_INFRACCION2").toString());
					    	InfraccionNomencladaRules infracionRules = new InfraccionNomencladaRules(GlobalStateApp.getInstance().getApplicationContext()) ;
					    	InfraccionNomenclada infraccion = infracionRules.getInfraccionByCodigo(pCodigoInfraccion);					    	actaConstatacion.setDescripcionInfraccion1(infraccion.getDescripcion());

					    }
					    catch(Exception ex)
					    {
					    	
					    }*/

					//return actaConstatacion;


		}
		catch (DeviceActasSynchronizationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return listActaConstatacion.get(0);
	}
	
	
	
	
	
}