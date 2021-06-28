package ar.gov.mendoza.PrometeoMuni.sync;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.Context;

import ar.gov.mendoza.PrometeoMuni.core.exceptions.DeviceActasSynchronizationException;

//import com.cids.siga.core.base.Enumeraciones.EstadoNovedadMovilEnum;
//import com.cids.siga.core.data.DaoUti;
import ar.gov.mendoza.PrometeoMuni.core.domain.*;
import ar.gov.mendoza.PrometeoMuni.core.util.Tools;
import ar.gov.mendoza.PrometeoMuni.rules.ActualizacionWebServiceRules;
import ar.gov.mendoza.PrometeoMuni.rules.DeviceActasRules;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalStateApp;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalVar;
import ar.gov.mendoza.PrometeoMuni.sync.dto.ActualizacionDTO;

//import com.cids.siga.sync.dto.DtoInicioInspeccion;
//import com.cids.siga.sync.dto.DtoInspeccion;

import org.json.JSONObject;

public class DeviceActasRestSync extends BaseRestSync {
	
	
	public DeviceActasRestSync(Context context) {
		 super(context, "DeviceActasSync");
	}


   public String confirmarActualizacionesXML()
   {
       StringBuilder strBuilder = new StringBuilder();
      
       String lIMEI = GlobalVar.getInstance().getImei();//Global.GlobalVar.IMEI;
      
       //buscar las actualizaciones
       
       
	   strBuilder.append("<root>");
       strBuilder.append("<values>");

       strBuilder.append("<IMEI>");
       strBuilder.append(lIMEI.trim());
       strBuilder.append("</IMEI>");
       strBuilder.append("<actualizaciones>");
       
       
       
        DeviceActasRules arules = new  DeviceActasRules (GlobalStateApp.getInstance().getApplicationContext());
        List<ActualizacionWebService> lista =  arules.getActualizacionesPendientes();
        for(ActualizacionWebService actualiza :lista)
		{
        	strBuilder.append("<actualizacion>");
       		strBuilder.append("<id_actualizacion>");
       			strBuilder.append(actualiza.getIdActualizacionRepat());
       		strBuilder.append("</id_actualizacion>");
       		strBuilder.append("<observacion>");
       			strBuilder.append(actualiza.getObservacion());
       		strBuilder.append("</observacion>");
       		strBuilder.append("</actualizacion>");
		}
       	
       

       
       strBuilder.append("</actualizaciones>");
       strBuilder.append("</values>");
       strBuilder.append("</root>");
       
       return strBuilder.toString();
	   
   }


   public void ConfirmarActualizacionesPDA()
   {
	   setServiceMethodName("ConfirmarActualizacionesPDA");
	   
	   String sxml = confirmarActualizacionesXML();
		//String parametro = Proteger(sxml);
	    HashMap<String, Object> values = new HashMap<String, Object> ();
		//values.put("param", parametro);
		try
		{	
			 String xmlEnc = "";//super.callService(values);
		     if (xmlEnc.equals(""))
	         {
	             return ;
	         }
	         else
	         {
	             xmlEnc = xmlEnc.replace(" ", "+");
	         }
			
		     		 
		    	 
 			ArrayList<HashMap<String, Object>> valuesARetornar = new ArrayList<HashMap<String,Object>>();
			 
			 String xmlRespuesta =xmlEnc;// ProcesarRetorno(xmlEnc);
			 String patron = "//root/filas/fila" ;///root/filas/fila
			 //valuesARetornar = CargarValores(xmlRespuesta,patron);
			 if (!valuesARetornar.isEmpty())
			 {
				 HashMap<String,Object> valores   = valuesARetornar.get(0);
					String result =valores.get("fila").toString();
					if(result !=null && (result.toLowerCase().equals("ok") ))
					{
						try
						{
			        	 DeviceActasRules derules = new  DeviceActasRules(GlobalStateApp.getInstance().getApplicationContext());
						 List<ActualizacionWebService> lista =  derules.getActualizacionesPendientes();
					        for(ActualizacionWebService actualiza :lista)
							{
					        	derules.grabarConfirmacion(actualiza);
							}
						}
						catch(Exception ex)
						{
							String sex = ex.getMessage();
						}
						//Marcar todas las actualizaciones como confirmadas para no reenviar
					}
			 }
			 
			 return ;
		} /*
		catch (DeviceActasSynchronizationException e) {
			e.printStackTrace();
		}*/
		catch (Exception e) {
			String m =	e.getMessage();
		}
   
   }


    public Boolean ConfirmarActualizaciones(String pIdsActualizaciones)
    {   Boolean bRetorno= true;
		
		//String sImei = GlobalVar.getInstance().getImei().trim();
		setServiceMethodName("setuppda");

    	String idsActualizaciones = pIdsActualizaciones;
		//String sxml = peticionConfirmarActualizacion2XML(idsActualizaciones);

		//String parametro = "";//Proteger(sxml);
	    //HashMap<String, Object> values = new HashMap<String, Object> ();
		//values.put("param", sxml);
		//values.put("encriptado", "S");
		
		try
		{	
			 String xmlEnc = super.callServiceRestPostGenerico(idsActualizaciones);
		     if (xmlEnc.equals(""))
	         {
	             return false;
	         }
		     /*
	         else
	         {
	        	 String strPrimerParte = buscarCadenaKLK(xmlEnc); 
	        	 xmlEnc = strPrimerParte;
	             xmlEnc = xmlEnc.replace(" ", "+");
	         }*/
			
		     		 
			 ArrayList<HashMap<String, Object>> valuesARetornar = new ArrayList<HashMap<String,Object>>();
			 
			 String xmlRespuesta = xmlEnc;//ProcesarRetorno(xmlEnc);
			 String patron = "//root/filas" ;///root/filas/fila
			 //valuesARetornar = CargarValores(xmlRespuesta,patron);
			 if (!valuesARetornar.isEmpty())
			 {
				HashMap<String,Object> valores   = valuesARetornar.get(0);
				
				String result = valores.get("fila").toString();
				if(result !=null && (result.toLowerCase().equals("ok") ))
				{	// grabamos y ponemos todas las actualizaciones en confirmado
                    String[] aActualizaciones = idsActualizaciones.split(";");
					if (aActualizaciones!=null && aActualizaciones.length>0)
					{
						for(int i =0; i<aActualizaciones.length;i++)
						{	
							if (aActualizaciones[i]!=null && !aActualizaciones[i].trim().equals(""))
							{
								ActualizacionDTO actualizacion = new ActualizacionDTO();
								String itemActualizacion = aActualizaciones[i];
								actualizacion.setId(itemActualizacion);
								actualizacion.setTipo("SQL");
								try
								{
									ActualizacionWebServiceRules actualiza = new ActualizacionWebServiceRules(GlobalStateApp.getInstance().getApplicationContext());
									ActualizacionWebService item = new ActualizacionWebService();
									item.setIdActualizacionRepat(actualizacion.getId());
									Date hoy = Tools.Today();
									item.setFechaEjecucion(hoy);
									item.setConfirmado("S");
									
									actualiza.confirmarActualizacion(item);
								}
								catch(Exception ex)
								{
									String sexc = ex.getMessage();
								}

							}		
						}
					}
				}
			 }
				return true;
		} 
		catch (DeviceActasSynchronizationException e) {
			e.printStackTrace();
			bRetorno = false;
		}
		catch (Exception e) {
			String m =	e.getMessage();
			bRetorno = false;
		}
		
    	return bRetorno;
	}



	public List<ActualizacionDTO> BuscarActualizaciones(String pTipoActualizacion)
	{
		String sImei = GlobalVar.getInstance().getImei().trim();
		setServiceMethodName("setuppda");

		List<ActualizacionDTO> listActualizaciones = new ArrayList<ActualizacionDTO>();
		
		//pTipoActualizacion ="SQL";
		String sxml = peticionActualizacion2XML(pTipoActualizacion);

			
		String parametro = "";//Proteger(sxml);
	    HashMap<String, Object> values = new HashMap<String, Object> ();
		//values.put("param", parametro);
		//values.put("encriptado", "N");
		
		try
		{	
			 String xmlEnc = super.callServiceRestGetGenerico(values);
		     if (xmlEnc.equals(""))
	         {
	             return null;
	         }


			 String xmlRespuesta = xmlEnc;//ProcesarRetorno(xmlEnc);

			JSONObject reader = new JSONObject(xmlRespuesta);

			String sys  = reader.getString("actualizaciones");

			 if (sys!=null && !sys.isEmpty())
			 {
				//HashMap<String,Object> valores   = (HashMap<String,Object>) valuesARetornar.get(0);
				
				String actualizaciones = sys;//valores.get("valor").toString();
                 String[] aActualizaciones = actualizaciones.split(";");
				if (aActualizaciones!=null && aActualizaciones.length>0)
				{
					for(int i =0; i<aActualizaciones.length;i++)
					{	
						if (aActualizaciones[i]!=null && !aActualizaciones[i].trim().equals(""))
						{
							ActualizacionDTO actualizacion = new ActualizacionDTO();
							String itemActualizacion = aActualizaciones[i];
							String[] idAndcomando = itemActualizacion.split("\\|");
							String idComando = idAndcomando[0];
							String comando = idAndcomando[1];
							actualizacion.setId(idComando);
							actualizacion.setComando(comando);
							actualizacion.setTipo("SQL");
							
							listActualizaciones.add(actualizacion);
							try
							{
								ActualizacionWebServiceRules actualiza = new ActualizacionWebServiceRules(GlobalStateApp.getInstance().getApplicationContext());
								ActualizacionWebService item = new ActualizacionWebService();
								item.setComando(actualizacion.getComando());
								item.setIdActualizacionRepat(actualizacion.getId());
								Date hoy = Tools.Today();
								item.setFechaRegistro(hoy);
								actualiza.grabarActualizacion(item);
							}
							catch(Exception ex)
							{
								String sexc = ex.getMessage();
							}
						}
					}
				}
			 }
			 
			 return listActualizaciones;
		} /*
		catch (DeviceActasSynchronizationException e) {
			e.printStackTrace();
		}*/
		catch (Exception e) {
			String m =	e.getMessage();
		}
		return listActualizaciones;
	}	
	
	private String peticionActualizacion2XML(String pTipoActualizacion)
    {
		//el TipoBusqueda es siempre por DOCUMENTO //por ahora no se usaria este parametro
        StringBuilder strBuilder = new StringBuilder();
        String sIdUsuario = GlobalVar.getInstance().getIdUsuario()==null?"0": GlobalVar.getInstance().getIdUsuario().trim();
        String sImei = GlobalVar.getInstance().getImei().trim();
        String sLat = Tools.DecimalFormat(GlobalVar.getInstance().getLatitud(),"0.00000");
        String sLon = Tools.DecimalFormat(GlobalVar.getInstance().getLongitud(),"0.00000");
		
        String pVersionSoftware = GlobalVar.getInstance().getVersionSoftwarePDA();
		pVersionSoftware = pVersionSoftware==null?"A2015001":pVersionSoftware;
        
        strBuilder.append("<root>");
        strBuilder.append("<values>");
        strBuilder.append("<idUsuario>");
        strBuilder.append(sIdUsuario);
        strBuilder.append("</idUsuario>");
        strBuilder.append("<IMEI>");
        strBuilder.append(sImei);
        strBuilder.append("</IMEI>");
        strBuilder.append("<Lat>");
        strBuilder.append(sLat.trim());
        strBuilder.append("</Lat>");
        strBuilder.append("<Lon>");
        strBuilder.append(sLon.trim());
        strBuilder.append("</Lon>");
        strBuilder.append("<VersionPDA>");
        strBuilder.append(pVersionSoftware.trim());
        strBuilder.append("</VersionPDA>");
        strBuilder.append("<TipoActualizacion>");
        strBuilder.append(pTipoActualizacion.trim());
        strBuilder.append("</TipoActualizacion>");

        strBuilder.append("</values>");
        strBuilder.append("</root>");
        
        
        
        return strBuilder.toString();
   
    }
	
	private String peticionConfirmarActualizacion2XML(String pIdsActualizaciones)
    {
		//el TipoBusqueda es siempre por DOCUMENTO //por ahora no se usaria este parametro
        StringBuilder strBuilder = new StringBuilder();
        String sIdUsuario = GlobalVar.getInstance().getIdUsuario()==null?"0": GlobalVar.getInstance().getIdUsuario().trim();
        String sImei = GlobalVar.getInstance().getImei().trim();
        String sLat = Tools.DecimalFormat(GlobalVar.getInstance().getLatitud(),"0.00000");
        String sLon = Tools.DecimalFormat(GlobalVar.getInstance().getLongitud(),"0.00000");
		
        String pVersionSoftware = GlobalVar.getInstance().getVersionSoftwarePDA();
		pVersionSoftware = pVersionSoftware==null?"A2015001":pVersionSoftware;
        
        strBuilder.append("<root>");
        strBuilder.append("<values>");
        strBuilder.append("<idUsuario>");
        strBuilder.append(sIdUsuario);
        strBuilder.append("</idUsuario>");
        strBuilder.append("<IMEI>");
        strBuilder.append(sImei);
        strBuilder.append("</IMEI>");
        strBuilder.append("<idsactualizaciones>");
        strBuilder.append(pIdsActualizaciones);
        strBuilder.append("</idsactualizaciones>");
        strBuilder.append("<Lat>");
        strBuilder.append(sLat.trim());
        strBuilder.append("</Lat>");
        strBuilder.append("<Lon>");
        strBuilder.append(sLon.trim());
        strBuilder.append("</Lon>");
        strBuilder.append("<VersionPDA>");
        strBuilder.append(pVersionSoftware.trim());
        strBuilder.append("</VersionPDA>");

        strBuilder.append("</values>");
        strBuilder.append("</root>");
        
        
        
        return strBuilder.toString();
   
    }
	
	
	
	
}