package ar.gov.mendoza.PrometeoMuni.sync;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import ar.gov.mendoza.PrometeoMuni.core.exceptions.DeviceActasSynchronizationException;

//import com.cids.siga.core.base.Enumeraciones.EstadoNovedadMovilEnum;
//import com.cids.siga.core.data.DaoUti;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalVar;
import ar.gov.mendoza.PrometeoMuni.sync.dto.RestriccionDTO;

//import com.cids.siga.sync.dto.DtoInicioInspeccion;
//import com.cids.siga.sync.dto.DtoInspeccion;


public class VerificacionPolicialSync extends SyncBase {	  
	//OBJETOS DE BUSQUEDA
	public static String PERSONAS ="PERSONAS";
	public static String AUTOMOTORES ="AUTOMOTORES";
	public static String MOTOVEHICULOS ="MOTOVEHICULOS";
	public static String LICENCIA ="LICENCIAS";
	public static String TESTIMEI = "TESTIMEI";
	
	//TIPOS DE BUSQUEDA
	public static String NUMERO_DOCUMENTO ="DOCUMENTO";
    public static String DOMINIO ="DOMINIO";
	public static String NUMERO_CHASIS ="NUMERO CHASIS";
	public static String NUMERO_MOTOR ="NUMERO MOTOR";
	
	public static String NUMERO_LICENCIA ="LICENCIA";
	
	public VerificacionPolicialSync(Context context) {
		 super(context, "VerificacionPolicialSync");
	}

  
	public RestriccionDTO BuscarRestricciones(String pTipoObjetoBusqueda,String pTipoBusqueda,String pDatoBusqueda)
	{
		RestriccionDTO restriccion = null;
		String sxml ="";
		if (pTipoObjetoBusqueda.equals(VerificacionPolicialSync.PERSONAS))
		{
			setServiceMethodName("ConsultaDatosdePersona");
			//sxml = persona2XML(pTipoBusqueda,pDatoBusqueda);
		}
		
		else if(pTipoObjetoBusqueda.equals(VerificacionPolicialSync.AUTOMOTORES))
		{
			setServiceMethodName("ConsultaDatosdeVehiculo");
			//sxml = rodado2XML(pTipoObjetoBusqueda, pTipoBusqueda, pDatoBusqueda);
		}
		else if(pTipoObjetoBusqueda.equals(VerificacionPolicialSync.LICENCIA))
		{
			return null;
		}
		else if(pTipoObjetoBusqueda.equals(VerificacionPolicialSync.TESTIMEI))
		{
			setServiceMethodName("TestIMEI");
		}
		else 
		{   
			return null;
		}
	
		
		//String parametro = Proteger(sxml);
	    HashMap<String, Object> values = new HashMap<String, Object> ();
		values.put(pTipoBusqueda, pDatoBusqueda);//parametro);
		
		try
		{
			 this.serviceUrl = "http://www.2112soft.com.ar/desa/sav/pda/setuppda/123456";
			 /*try{
				 String urlSuport = GlobalVar.getInstance().getSuportTable().getWebServicePolicia();
				 if (urlSuport!=null && !urlSuport.trim().equals(""))
				 {	 
					 this.serviceUrl = urlSuport;	 
				 }
			 }
			 catch(Exception ex){
				 
			 }*/
			 String xmlEnc = super.callServiceRestGetGenerico(values);
		     if (xmlEnc.equals(""))
	         {
	             return null;
	         }
	         else
	         {
	        	 JSONObject jsonObj = new JSONObject(xmlEnc);
	             
	               // Getting JSON Array node
	        	   String strTodo = "";
	               JSONArray contacts = jsonObj.getJSONArray("items");
	               for (int i = 0; i < contacts.length(); i++) {
	                   JSONObject c = contacts.getJSONObject(i);
	                   String Serie = c.getString("Serie");
	                   String Numero = c.getString("Numero");
	                   String IMEI = c.getString("IMEI");
	                   strTodo = Serie + "\n" + Numero + "\n" + IMEI;
	               }
	         
	              /* 
	        	GsonBuilder b = new GsonBuilder();	    
	 		    Gson gson = b.create();
	 		    
	 		    Type type = new TypeToken<DtoInicioInspeccion>(){}.getType();
	 		     dto = gson.fromJson(result, type);
	 		    
	 		    if(dto != null && dto.getError() != null) {
	 		    	error = dto.getError(); 
	 		    }
	        	 */
	             //xmlEnc = xmlEnc.replace(" ", "+");
			    restriccion = new RestriccionDTO();
			    if (strTodo.equals(""))
			    {
			    	restriccion.setHayDatos("N");
			    }	
			    else
			    {
			    	restriccion.setHayDatos("S");
			    }
		    	restriccion.setResultado(strTodo);

				return restriccion;
	         }
			
		    /* 		 
			 ArrayList<HashMap<String, Object>> valuesARetornar = new ArrayList<HashMap<String,Object>>();
			 
			 String xmlRespuesta = ProcesarRetorno(xmlEnc);
			 String patron = "//root/filas/fila" ;///root/filas/fila
			 valuesARetornar = CargarValores(xmlRespuesta,patron);
			 if (!valuesARetornar.isEmpty())
			 {
				HashMap<String,Object> valores   = (HashMap<String,Object>) valuesARetornar.get(0);
			    restriccion = new RestriccionDTO();
				restriccion.setHayDatos(valores.get("HAY_DATOS").toString());
				restriccion.setResultado(valores.get("RESULTADO").toString());
				return restriccion;
			 }
			 */
		} 
		catch (DeviceActasSynchronizationException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			String m =	e.getMessage();
		}
		return restriccion;
	}	
	private String rodado2XML(String pTipoObjetoBusqueda,String pTipoBusqueda, String pDatoBusqueda)
	{
		String p_TipoVehiculo = pTipoObjetoBusqueda;  // AUTOMOTORES  MOTOVEHICULOS
		String p_TipoBusqueda = pTipoBusqueda;  // DOMINIO  NUMERO_CHASIS  NUMERO_MOTOR
		//preparar el xml
        StringBuilder strb = new StringBuilder();
        strb.append("<root>");
        strb.append("<values>");

        strb.append("<TipoVehiculo>");
        strb.append(p_TipoVehiculo.trim());
        strb.append("</TipoVehiculo>");

        strb.append("<TipoBusqueda>");
        strb.append(p_TipoBusqueda.trim());
        strb.append("</TipoBusqueda>");

        strb.append("<DatoBusqueda>");
        strb.append(pDatoBusqueda.trim());
        strb.append("</DatoBusqueda>");

        strb.append("<idUsuario>");
        strb.append(GlobalVar.getInstance().getIdUsuario().trim());
        strb.append("</idUsuario>");
        try
        {
            strb.append("<Login>");
            strb.append(GlobalVar.getInstance().getLogin().trim());
            strb.append("</Login>");
        }
        catch (Exception ex)
        { 
        	
        }

        strb.append("<IMEI>");
        strb.append(GlobalVar.getInstance().getImei().trim());
        strb.append("</IMEI>");

        strb.append("<Lat>");
        strb.append(GlobalVar.getInstance().getLatitud().toString().trim());
        strb.append("</Lat>");
        strb.append("<Lon>");
        strb.append(GlobalVar.getInstance().getLongitud().toString().trim());
        strb.append("</Lon>");

        strb.append("</values>");
        strb.append("</root>");
        
        return strb.toString();
		
	}
	private String persona2XML(String pTipoBusqueda,String pDatoBusqueda)
    {
		//el TipoBusqueda es siempre por DOCUMENTO //por ahora no se usaria este parametro
        StringBuilder strBuilder = new StringBuilder();

        strBuilder.append("<root>");
        strBuilder.append("<values>");

        strBuilder.append("<NroDocumento>");
        strBuilder.append(pDatoBusqueda.trim());
        strBuilder.append("</NroDocumento>");

        strBuilder.append("<idUsuario>");
        strBuilder.append(GlobalVar.getInstance().getIdUsuario().trim());
        strBuilder.append("</idUsuario>");
        try
        {
            strBuilder.append("<Login>");
            strBuilder.append(GlobalVar.getInstance().getLogin().trim());
            strBuilder.append("</Login>");
        }
        catch(Exception ex)
        {	
        }
        

        strBuilder.append("<IMEI>");
        strBuilder.append(GlobalVar.getInstance().getImei().trim());
        strBuilder.append("</IMEI>");

        strBuilder.append("<Lat>");
        strBuilder.append(GlobalVar.getInstance().getLatitud().toString().trim());
        strBuilder.append("</Lat>");
        strBuilder.append("<Lon>");
        strBuilder.append(GlobalVar.getInstance().getLongitud().toString().trim());
        strBuilder.append("</Lon>");

        strBuilder.append("</values>");
        
        strBuilder.append("</root>");
        return strBuilder.toString();
   
    }
	
	
	
}