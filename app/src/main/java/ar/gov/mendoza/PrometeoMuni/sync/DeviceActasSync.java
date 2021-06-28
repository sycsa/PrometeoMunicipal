package ar.gov.mendoza.PrometeoMuni.sync;

import java.text.SimpleDateFormat;
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
import ar.gov.mendoza.PrometeoMuni.dao.EquipoDao;
import ar.gov.mendoza.PrometeoMuni.rules.ActualizacionWebServiceRules;
import ar.gov.mendoza.PrometeoMuni.rules.DeviceActasRules;
import ar.gov.mendoza.PrometeoMuni.rules.SuportTableRules;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalStateApp;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalVar;
import ar.gov.mendoza.PrometeoMuni.sync.dto.ActualizacionDTO;

//import com.cids.siga.sync.dto.DtoInicioInspeccion;
//import com.cids.siga.sync.dto.DtoInspeccion;


public class DeviceActasSync extends SyncBase {	  
	
	
	public DeviceActasSync(Context context) {
		 super(context, "DeviceActasSync");
	}

  public Boolean EnviarCodigoQR(String pParametro)
  {
	  Boolean bResultado = false;

	  setServiceMethodName("Execute");//SendActa");
		
		
		String sxml = pParametro;
		String parametro = Proteger(sxml);
	    HashMap<String, Object> values = new HashMap<String, Object> ();
		values.put("Logobs", parametro);
		
		
	  try
		{
			String xmlEnc2 = super.callServiceRestStyleNet(pParametro);
			xmlEnc2 ="";
			bResultado = true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	  return bResultado;
  }
	
   public String ObtenerXML(String parametro)
   {
	   setServiceMethodName("GetXml");
	   HashMap<String, Object> values = new HashMap<String, Object> ();
		values.put("parametro", parametro);
		
		try
		{	
			 String xmlEnc = super.callService(values);
		     if (xmlEnc.equals(""))
	         {
	             return "" ;
	         }
	         else
	         {
	             xmlEnc = xmlEnc.replace(" ", "+");
	         }
			
		     		 
			 ArrayList<HashMap<String, Object>> valuesARetornar = new ArrayList<HashMap<String,Object>>();
			 
			// String xmlRespuesta = ProcesarRetorno(xmlEnc);
			 return xmlEnc;
			 /*String patron = "//root/filas/fila" ;///root/filas/fila
			 valuesARetornar = CargarValores(xmlRespuesta,patron);
			 if (!valuesARetornar.isEmpty())
			 {
				HashMap<String,Object> valores   = (HashMap<String,Object>) valuesARetornar.get(0);
			 }
			 
			 return ;*/
		} 
		catch (DeviceActasSynchronizationException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			String m =	e.getMessage();
		}
		
		return "";
   }
   public String ObtenerXMLEncriptado(String numeroSerie,String nroDominio,String nroDocumento,String nroActa,String nroLicencia,int idSexo,int idActa	)
   {
	   
	   
	   setServiceMethodName("GetXmlEncrypt");
	   HashMap<String, Object> values = new HashMap<String, Object> ();
		values.put("numeroSerie", numeroSerie);
		values.put("nroDominio", nroDominio);
		values.put("nroDocumento", nroDocumento);
		values.put("nroActa", nroActa);
		values.put("nroLicencia", nroLicencia);
		values.put("idSexo", idSexo);
		values.put("idActa", idActa);
		
		try
		{	
			 String xmlEnc = super.callService(values);
		     if (xmlEnc.equals(""))
	         {
	             return "" ;
	         }
	         else
	         {
	             xmlEnc = xmlEnc.replace(" ", "+");
	         }
			
		     		 
			 ArrayList<HashMap<String, Object>> valuesARetornar = new ArrayList<HashMap<String,Object>>();
			 
			 //String xmlRespuesta = ProcesarRetorno(xmlEnc);
			 return xmlEnc;
			 /*String patron = "//root/filas/fila" ;///root/filas/fila
			 valuesARetornar = CargarValores(xmlRespuesta,patron);
			 if (!valuesARetornar.isEmpty())
			 {
				HashMap<String,Object> valores   = (HashMap<String,Object>) valuesARetornar.get(0);
			 }
			 
			 return ;*/
		} 
		catch (DeviceActasSynchronizationException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			String m =	e.getMessage();
		}
		
		return "";
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
		String parametro = Proteger(sxml);
	    HashMap<String, Object> values = new HashMap<String, Object> ();
		values.put("param", parametro);
		try
		{	
			 String xmlEnc = super.callService(values);
		     if (xmlEnc.equals(""))
	         {
	             return ;
	         }
	         else
	         {
	             xmlEnc = xmlEnc.replace(" ", "+");
	         }
			
		     		 
		    	 
 			ArrayList<HashMap<String, Object>> valuesARetornar = new ArrayList<HashMap<String,Object>>();
			 
			 String xmlRespuesta = ProcesarRetorno(xmlEnc);
			 String patron = "//root/filas/fila" ;///root/filas/fila
			 valuesARetornar = CargarValores(xmlRespuesta,patron);
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
		} 
		catch (DeviceActasSynchronizationException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			String m =	e.getMessage();
		}
   
   }
   
	private String obtenerHabilitacion2XML()
    {
        StringBuilder strBuilder = new StringBuilder();
        
        String sImei = GlobalVar.getInstance().getImei().trim();
        String sLat = Tools.DecimalFormat(GlobalVar.getInstance().getLatitud(),"0.00000");
        String sLon = Tools.DecimalFormat(GlobalVar.getInstance().getLongitud(),"0.00000");
        Date date = Tools.Today();
        String lFechaEquipo =  Tools.DateValueOf(date, "dd/MM/yyyy HH:mm:ss").trim();


        
        strBuilder.append("<root>");
        strBuilder.append("<values>");
        strBuilder.append("<IMEI>");
        strBuilder.append(sImei);
        strBuilder.append("</IMEI>");
        strBuilder.append("<Lat>");
        strBuilder.append(sLat.trim());
        strBuilder.append("</Lat>");
        strBuilder.append("<Lon>");
        strBuilder.append(sLon.trim());
        strBuilder.append("</Lon>");
        strBuilder.append("<FECHA_SOLICITUD>");
        strBuilder.append(lFechaEquipo.trim());
        strBuilder.append("</FECHA_SOLICITUD>");

        strBuilder.append("</values>");
        strBuilder.append("</root>");
        
        
        
        return strBuilder.toString();
   
    }

   public void ObtenerHabilitacionPDA()
   {
	   setServiceMethodName("ObtenerHabilitacionPDA");
	   
	   String sxml = obtenerHabilitacion2XML();
		String parametro = Proteger(sxml);
	    HashMap<String, Object> values = new HashMap<String, Object> ();
		values.put("param", parametro);
		try
		{	
			 String xmlEnc = super.callService(values);
		     if (xmlEnc.equals(""))
	         {
	             return ;
	         }
	         else
	         {
	             xmlEnc = xmlEnc.replace(" ", "+");
	         }
			
		     		 
		    	 
 			ArrayList<HashMap<String, Object>> valuesARetornar = new ArrayList<HashMap<String,Object>>();
			 
			 String xmlRespuesta = ProcesarRetorno(xmlEnc);
			 String patron = "//root/filas/fila" ;///root/filas/fila
			 valuesARetornar = CargarValores(xmlRespuesta,patron);
			 if (!valuesARetornar.isEmpty())
			 {
				 HashMap<String,Object> valores   = valuesARetornar.get(0);
					try
					{
						String imei = valores.get("IMEI").toString();
						String numeroSerie = valores.get("Numero_Serie").toString();
						String habilitado = valores.get("Habilitado").toString();	
						try
						{
							EquipoDao edao = new EquipoDao();
							Equipo item = new Equipo();
							item.setHabilitado(habilitado);
							int inumeroSerie = Integer.parseInt(numeroSerie);
							item.setIdEquipo(inumeroSerie);
							item.setImei(imei);
							edao.insert(item); // puede dar error si se intenta insertar 2 veces el mismo equipo
						}
						catch(Exception ex)
						{
							String sex = ex.getMessage();
							
						}
						//Grabar Numero de Serie en T_EQUIPO_IMEI COMO HABILITADO O NO SEGUN VENGA
					
					} catch(Exception ex){} 
					
					 
				
			 }
			 
			 return ;
		} 
		catch (DeviceActasSynchronizationException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			String m =	e.getMessage();
		}
	   
   }
   
   public void ActualizarPosicion()
   {
	   setServiceMethodName("versionGPS");
	   
	   String sxml = versionGPS2XML();
		String parametro = Proteger(sxml);
	    HashMap<String, Object> values = new HashMap<String, Object> ();
		values.put("param", parametro);
		try
		{	
			 String xmlEnc = super.callService(values);
		     if (xmlEnc.equals(""))
	         {
	             return ;
	         }
	         else
	         {
	             xmlEnc = xmlEnc.replace(" ", "+");
	         }
			
		     		 
		    	 
 			ArrayList<HashMap<String, Object>> valuesARetornar = new ArrayList<HashMap<String,Object>>();
			 
			 String xmlRespuesta = ProcesarRetorno(xmlEnc);
			 String patron = "//root/filas/fila" ;///root/filas/fila
			 valuesARetornar = CargarValores(xmlRespuesta,patron);
			 if (!valuesARetornar.isEmpty())
			 {
				HashMap<String,Object> valores   = valuesARetornar.get(0);
			 }
			 
			 return ;
		} 
		catch (DeviceActasSynchronizationException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			String m =	e.getMessage();
		}
	   
   }

   public String versionGPS2XML()
   {
       StringBuilder strBuilder = new StringBuilder();
       String lIdUsuario = GlobalVar.getInstance().getIdUsuario()==null?"":GlobalVar.getInstance().getIdUsuario(); //Global.GlobalVar.Id_Usuario;
       String lIMEI = GlobalVar.getInstance().getImei();//Global.GlobalVar.IMEI;
       Double lLat = GlobalVar.getInstance().getLatitud();//Global.GlobalVar.Lat;
       Double lLon = GlobalVar.getInstance().getLongitud();//Global.GlobalVar.Lon;
       String lIMSI = GlobalVar.getInstance().getImsi();//Global.GlobalVar.imsi;
       String lVersionPDA = GlobalVar.getInstance().getVersionSoftwarePDA();//Global.GlobalVar.VersionPDA;
       String lExisteMemoriaExterna = "N";//Global.GlobalVar.ExisteMemoriaExterna ==true?"S":"N";
       String lExisteBasePadrones = "S";//Global.GlobalVar.ExisteBasePadrones == true ? "S" : "N";
       Date dFechaEjecutable =Tools.ConvertLongToDate(Tools.getAppFirstInstallTime(GlobalStateApp.getInstance().getApplicationContext()));//Global.GlobalVar.FechaEjecutable;
	   SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
       String lFechaEjecutable =formatter.format(dFechaEjecutable);
       String lNivelBateria = "0";//Global.GlobalVar.NivelBateria.ToString();
       String lSerial = "0";//Global.GlobalVar.SerialMemoriaExterna;
       String lCapacidad  = "0";//Global.GlobalVar.CapacidadMemoriaExterna ;
       String lEspacioLibre = "0"; //Global.GlobalVar.EspacioLibreMemoriaExterna ;
       String lEspacioUsado = "0";// Global.GlobalVar.EspacioUsadoMemoriaExterna;

       String lImpresoraConfigurada = "";
       
       try { lImpresoraConfigurada = GlobalVar.getInstance().getImpresoraConfigurada();}//Global.GlobalVar.RW420FriendlyName; }
       catch(Exception ex) { }

       String lMacAddress = "";
       try { lMacAddress = "";}//Global.GlobalVar.RW420MacAddress; }
       catch(Exception ex) { }
       
       
       Date date = Tools.Today();
       
       
       String lFechaEquipo =  Tools.DateValueOf(date, "dd/MM/yyyy HH:mm:ss").trim();
       SuportTableRules suportTableRules = new SuportTableRules(GlobalStateApp.getInstance().getApplicationContext());
       
       SuportTable TablaConfiguracion = suportTableRules.getSuportTable();
       String lZona = TablaConfiguracion.getIdZona();
       String lImporteNP = TablaConfiguracion.getImporteNP().toString();
       
       String lUltimoNumeroActa = TablaConfiguracion.getUltimoNumeroActa();	
       String lActasNoSincronizadas = TablaConfiguracion.getActasNoSincronizadas();
       
       
       
	   strBuilder.append("<root>");
       strBuilder.append("<values>");

       strBuilder.append("<idUsuario>");
       strBuilder.append(lIdUsuario.trim());
       strBuilder.append("</idUsuario>");

       strBuilder.append("<IMEI>");
       strBuilder.append(lIMEI.trim());
       strBuilder.append("</IMEI>");

       strBuilder.append("<Lat>");
       strBuilder.append(lLat.toString().trim());//"#0.000000"
       strBuilder.append("</Lat>");

       strBuilder.append("<Lon>");
       strBuilder.append(lLon.toString().trim());//"#0.000000"
       strBuilder.append("</Lon>");

       strBuilder.append("<IMSI>");
       strBuilder.append(lIMSI.trim());
       strBuilder.append("</IMSI>");

       strBuilder.append("<ExisteBasePadrones>");
       strBuilder.append(lExisteBasePadrones.trim());
       strBuilder.append("</ExisteBasePadrones>");

       strBuilder.append("<ExisteMemoriaExterna>");
       strBuilder.append(lExisteMemoriaExterna.trim());
       strBuilder.append("</ExisteMemoriaExterna>");

       strBuilder.append("<FechaEjecutable>");
       strBuilder.append(lFechaEjecutable.trim());
       strBuilder.append("</FechaEjecutable>");

       strBuilder.append("<NivelBateria>");
       strBuilder.append(lNivelBateria.trim());
       strBuilder.append("</NivelBateria>");

       strBuilder.append("<SerialSD>");
       strBuilder.append(lSerial.trim());
       strBuilder.append("</SerialSD>");

       strBuilder.append("<SizeSD>");
       strBuilder.append(lCapacidad.trim());
       strBuilder.append("</SizeSD>");

       strBuilder.append("<UsoSD>");
       strBuilder.append(lEspacioUsado.trim());
       strBuilder.append("</UsoSD>");


       strBuilder.append("<VersionPDA>");
       strBuilder.append(lVersionPDA.trim());
       strBuilder.append("</VersionPDA>");

       strBuilder.append("<Impresora>");
       strBuilder.append(lImpresoraConfigurada.trim());
       strBuilder.append("</Impresora>");

       strBuilder.append("<MacAddress>");
       strBuilder.append(lMacAddress.trim());
       strBuilder.append("</MacAddress>");

       
       strBuilder.append("<UltimoNumeroActa>");
       try
       {
           strBuilder.append(lUltimoNumeroActa);
       }
       catch(Exception ex)
       {
           strBuilder.append("0");
       }
       strBuilder.append("</UltimoNumeroActa>");


       strBuilder.append("<ActasNoSincronizadas>");
       try
       {
           strBuilder.append(lActasNoSincronizadas);
       }
       catch(Exception ex)
       {
           strBuilder.append("-1");
       }
       strBuilder.append("</ActasNoSincronizadas>");
       strBuilder.append("<FechaEquipo>");
       try
       {
			
           String sFechaEquipo = lFechaEquipo;
           strBuilder.append(sFechaEquipo);
       }
       catch(Exception ex)
       {
           strBuilder.append("");
       }
       strBuilder.append("</FechaEquipo>");
       strBuilder.append("<Zona>");
       try
       {
           String sZona= lZona;
           strBuilder.append(sZona);
       }
       catch(Exception ex)
       {
           strBuilder.append("");
       }
       strBuilder.append("</Zona>");

       strBuilder.append("<ImporteNP>");
       try
       {
           String sImporteNP = lImporteNP;
           strBuilder.append(sImporteNP);
       }
       catch(Exception ex)
       {
           strBuilder.append("");
       }

       strBuilder.append("</ImporteNP>");

       strBuilder.append("</values>");
       strBuilder.append("</root>");
       
       return strBuilder.toString();
	   
   }
   
    public Boolean ConfirmarActualizaciones(String pIdsActualizaciones)
    {   Boolean bRetorno= true;
		
		setServiceMethodName("wpconfirmaractualizaciones.php");
		setServiceNameMethodName("wpconfirmaractualizaciones.php");
		
		setServiceNameMethodName("confirmarActualizaciones");
		
    	String idsActualizaciones = pIdsActualizaciones;
		String sxml = peticionConfirmarActualizacion2XML(idsActualizaciones);

		String parametro = Proteger(sxml);
	    HashMap<String, Object> values = new HashMap<String, Object> ();
		values.put("param", parametro);
		values.put("encriptado", "S");
		
		try
		{	
			 String xmlEnc = super.callServiceRestGenerico(values);
		     if (xmlEnc.equals(""))
	         {
	             return false;
	         }
	         else
	         {
	        	 String strPrimerParte = buscarCadenaKLK(xmlEnc); 
	        	 xmlEnc = strPrimerParte;
	             xmlEnc = xmlEnc.replace(" ", "+");
	         }
			
		     		 
			 ArrayList<HashMap<String, Object>> valuesARetornar = new ArrayList<HashMap<String,Object>>();
			 
			 String xmlRespuesta = ProcesarRetorno(xmlEnc);
			 String patron = "//root/filas" ;///root/filas/fila
			 valuesARetornar = CargarValores(xmlRespuesta,patron);
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
		setServiceMethodName("wpobteneractualizaciones.php");
		setServiceNameMethodName("wpobteneractualizaciones.php");
		setServiceNameMethodName("ObtenerActualizaciones");
		List<ActualizacionDTO> listActualizaciones = new ArrayList<ActualizacionDTO>();
		
		pTipoActualizacion ="SQL";
		String sxml = peticionActualizacion2XML(pTipoActualizacion);

			
		String parametro = Proteger(sxml);
	    HashMap<String, Object> values = new HashMap<String, Object> ();
		values.put("param", parametro);
		values.put("encriptado", "S");
		
		try
		{	
			 String xmlEnc = super.callServiceRestGenerico(values);
		     if (xmlEnc.equals(""))
	         {
	             return null;
	         }
	         else
	         {
	             xmlEnc = xmlEnc.replace(" ", "+");
	         }
			
		     		 
			 ArrayList<HashMap<String, Object>> valuesARetornar = new ArrayList<HashMap<String,Object>>();
			 
			 String xmlRespuesta = ProcesarRetorno(xmlEnc);
			 String patron = "//root/filas/fila" ;///root/filas/fila
			 valuesARetornar = CargarValores(xmlRespuesta,patron);
			 if (!valuesARetornar.isEmpty())
			 {
				HashMap<String,Object> valores   = valuesARetornar.get(0);
				
				String actualizaciones = valores.get("valor").toString();
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
		} 
		catch (DeviceActasSynchronizationException e) {
			e.printStackTrace();
		}
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