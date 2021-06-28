package ar.gov.mendoza.PrometeoMuni.rules;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import ar.gov.mendoza.PrometeoMuni.core.domain.ActualizacionWebService;
import ar.gov.mendoza.PrometeoMuni.dao.ActualizacionWebServiceDao;
import ar.gov.mendoza.PrometeoMuni.dao.DeviceActasDao;
import ar.gov.mendoza.PrometeoMuni.sync.DeviceActasSync;
import ar.gov.mendoza.PrometeoMuni.sync.dto.ActualizacionDTO;

/*
	 * Bussines Rules for Countries
	 */
public class DeviceActasRules {

	private Context context;
	
	public DeviceActasRules (Context context) {
		this.context = context;
	}
	
	
	public void actualizarPosicion()
	{
		DeviceActasSync syncDeviceActas = new DeviceActasSync(this.context);
 		
/* 	    String sNumeroActa ="";
 	    String imei = GlobalVar.getInstance().getImei();
 	    

 	    String numeroSerie = "";
 	    EquipoDao equipoDao = new EquipoDao();
 	    Equipo equipo  = equipoDao.getByImei(imei);
 	    numeroSerie = equipo.getNumeroSerie();*/
 	   
 	    
 	    try
 	    {
     	    syncDeviceActas.ActualizarPosicion();
 	    } 
 	    catch (Exception e) 
 	    {
 	       
 	    }
 	    finally
 	    {
 	    	syncDeviceActas=null;
 	    }
		
	}
	
	public Boolean grabarConfirmacion(ActualizacionWebService item)
	{
		
		
			Boolean bResultado = false;
			
			try
			{
			DeviceActasDao deviceActasDao = new DeviceActasDao();
			String sStatement = "UPDATE T_ACTUALIZACION_WEBSERVICE SET CONFIRMADO ='S' WHERE ID_ACTUALIZACION_REPAT = " + item.getIdActualizacionRepat() ; 
			if (sStatement!=null)
				bResultado = deviceActasDao.execSQL(sStatement);
			}
			catch(Exception ex)
			{
				
			}
			return bResultado;
		
	}
	public List<ActualizacionWebService> getActualizacionesPendientes ()
	{
		List<ActualizacionWebService> lstActualizaciones= new ArrayList<ActualizacionWebService>();
		ActualizacionWebServiceDao actualziacionesDao = new ActualizacionWebServiceDao();
		ContentValues cvalues = new ContentValues();
		
		cvalues.put("CONFIRMADO", "N");
		
		lstActualizaciones  = actualziacionesDao.getByFilter(cvalues);
		
		return lstActualizaciones;
		
	}
	public Boolean aplicarActualizacion(ActualizacionDTO actualizacion)
	{
		Boolean bResultado = false;
		
		DeviceActasDao deviceActasDao = new DeviceActasDao();
		String sStatement = actualizacion.getComando(); 
		if (sStatement!=null)
			bResultado = deviceActasDao.execSQL(sStatement);
		
		return bResultado;
	}

	
}
