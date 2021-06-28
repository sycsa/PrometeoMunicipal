package ar.gov.mendoza.PrometeoMuni.rules;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import ar.gov.mendoza.PrometeoMuni.core.domain.Equipo;
import ar.gov.mendoza.PrometeoMuni.core.domain.Provincia;
import ar.gov.mendoza.PrometeoMuni.core.providers.ActaConstatacionProvider;
import ar.gov.mendoza.PrometeoMuni.dao.EquipoDao;
import ar.gov.mendoza.PrometeoMuni.dao.ProvinciaDao;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalVar;
import ar.gov.mendoza.PrometeoMuni.sync.ActaConstatacionSync;

/*
	 * Bussines Rules for Countries
	 */
public class ProvinciaRules {

	private Context context;
	
	public ProvinciaRules (Context context) {
		this.context = context;
	}
	/*
	 * Get All Items of Provinces from Databases
	 */
	public List<Provincia> getAll()
	{
		List<Provincia> lstProvincias = new ArrayList<Provincia>();
		ProvinciaDao provinciaDao = new ProvinciaDao();
		
		lstProvincias  = provinciaDao.getAll();
		
		return lstProvincias;
		
	}
	
	/*
	 * Get All Items of Provinces from Databases
	 */
	public List<Provincia> getByPais(String pIdPais)
	{
		List<Provincia> lstProvincias = new ArrayList<Provincia>();
		ProvinciaDao provinciaDao = new ProvinciaDao();
		ContentValues cvalues = new ContentValues();
		
		cvalues.put("ID_PAIS", pIdPais);
		
		lstProvincias  = provinciaDao.getByFilter(cvalues);
		
		return lstProvincias;
		
	}
	
	/*
	 * Get All Items of Provinces from Databases
	 */
	public List<Provincia> getByPaisForSpinner(String pIdPais)
	{
		List<Provincia> lstProvincias = new ArrayList<Provincia>();
		ProvinciaDao provinciaDao = new ProvinciaDao();
		ContentValues cvalues = new ContentValues();
		
		cvalues.put("ID_PAIS", pIdPais);
		
		lstProvincias  = provinciaDao.getByFilter(cvalues);
		
		Provincia pProvincia = new Provincia(null,"Seleccione una Provincia (" + lstProvincias.size() + ")", "");
		lstProvincias.add(pProvincia);
		if(pIdPais.equals("1") || pIdPais.equals("2"))
		{
			//No se agrega el Item
			pProvincia = null;
		}
		else
		{
		   pProvincia = new Provincia("-1","NO APARECE EN LA LISTA (Lo Escribire Manualmente)", "-1");
		   lstProvincias.add(0,pProvincia);
		}
		
		
		return lstProvincias;
		
	}
	/*
	 * Example of Method
	 */
	public void sincronizarUltimaActa()
	{
		ActaConstatacionSync synActa = new ActaConstatacionSync(this.context);
 		
 	    String sNumeroActa ="";
 	    String imei = GlobalVar.getInstance().getImei();
 	    
 	    // buscar localmente nro de serie en base al imei del dispositivo
 	    String numeroSerie = "";
 	   String letraSerie = "";
 	    EquipoDao equipoDao = new EquipoDao();
 	    Equipo equipo  = equipoDao.getByImei(imei);
 	    numeroSerie = equipo.getNumeroSerie();
 	    letraSerie = equipo.getLetraSerie();
 	    try
 	    {
     	    sNumeroActa = synActa.validarNumeroActa();
     	    long numeroActaREPAT = Integer.parseInt(sNumeroActa.substring(4,sNumeroActa.length()-1)); //desde la 4 posicion hasta el largo menos -1 que es el digito verificador
     	    ContentProvider cp = this.context.getContentResolver().acquireContentProviderClient(ActaConstatacionProvider.CONTENT_URI).getLocalContentProvider();
       	    ActaConstatacionProvider  a =(ActaConstatacionProvider) cp; 
     	    a.setSecuenceValue(numeroActaREPAT);
 	    } 
 	    catch (Exception e) 
 	    {
 	       
 	    }
 	    finally
 	    {
 	    	synActa=null;
 	    }
		
	}
}
