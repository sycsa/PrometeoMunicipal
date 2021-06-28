package ar.gov.mendoza.PrometeoMuni.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;

import ar.gov.mendoza.PrometeoMuni.core.domain.Ruta;
import ar.gov.mendoza.PrometeoMuni.core.domain.Zona;
import ar.gov.mendoza.PrometeoMuni.dao.RutaConvenioDao;
import ar.gov.mendoza.PrometeoMuni.dao.RutaDao;
import ar.gov.mendoza.PrometeoMuni.dao.ZonaDao;
import ar.gov.mendoza.PrometeoMuni.entities.Filter;
import ar.gov.mendoza.PrometeoMuni.entities.FilterList;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalVar;

/*
	 * Business Rules for Countries
	 */
public class RutaRules {

	private Context context;
	
	public RutaRules (Context context) {
		this.context = context;
	}
	/*
	 * Get All Items of Provinces from Databases
	 */
	public List<Ruta> getAll()
	{
		List<Ruta> lstRutas = new ArrayList<Ruta>();
		RutaDao provinciaDao = new RutaDao();
		
		lstRutas  = provinciaDao.getAll();
		
		return lstRutas;
		
	}
	
	/*
	 * Get All Items of Provinces from Databases
	 */
	public List<Ruta> getByTipoRuta(String pIdTipoRuta)
	{
		List<Ruta> lstRutas = new ArrayList<Ruta>();
		RutaDao rutaDao = new RutaDao();
		ContentValues cvalues = new ContentValues();
		
		cvalues.put("ID_TIPO_RUTA", pIdTipoRuta);
		
		lstRutas = rutaDao.getByFilter(cvalues);
		
		return lstRutas;
		
	}

	public List<Ruta> getByTipoRutaForSpinner(String pIdTipoRuta)
	{
		List<Ruta> lstRutas = new ArrayList<Ruta>();
		RutaDao rutaDao = new RutaDao();
		
		String sZona = GlobalVar.getInstance().getSuportTable().getIdZona();
		
		ContentValues cvalues = new ContentValues();
		cvalues.put("ID_TIPO_RUTA", pIdTipoRuta);
		lstRutas = rutaDao.getByFilter(cvalues);
		
		if(sZona!=null && !sZona.trim().equals(""))
		{
	        //buscar las rutas del SuportTable
	        ZonaDao zonaDao = new ZonaDao();
	        Zona zona = zonaDao.getItem(sZona);
	        String pRutas =zona.getRutasHabilitadas();

			 Filter<Ruta, String> filter = new Filter<Ruta,String>() {
		            public boolean isMatched(Ruta object, String rutas) {
		            	String[] arrRutas = rutas.split(",");
		            	List<String> l  = new ArrayList<String>(Arrays.asList(arrRutas));
		            	return l.contains(object.getId());
		            }
		        };
		        lstRutas =  new FilterList<String>().filterList(lstRutas, filter,pRutas);
		}	
		
		
		Ruta pRuta = new Ruta("","Seleccione una Item","");
		lstRutas.add(pRuta);

		return lstRutas;
		
	}

	
	public Boolean EstaEnRangoConvenio(String pConvenio ,Integer pIdRuta,String pKm)
	{
		Boolean bResultado = false;
		
		RutaConvenioDao rutaConvenioDao = new  RutaConvenioDao();
		try
		{
			bResultado = rutaConvenioDao.EstaEnRangoConvenio(pConvenio, pIdRuta, pKm);
		}
		catch(Exception ex)
		{
			String message = ex.getMessage();
			message = "";
		}
		
		return bResultado;
		
	}
}
