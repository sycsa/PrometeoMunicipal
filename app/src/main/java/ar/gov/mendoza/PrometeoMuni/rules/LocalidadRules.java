package ar.gov.mendoza.PrometeoMuni.rules;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;

import ar.gov.mendoza.PrometeoMuni.core.domain.Equipo;
import ar.gov.mendoza.PrometeoMuni.core.domain.Localidad;
import ar.gov.mendoza.PrometeoMuni.core.providers.ActaConstatacionProvider;
import ar.gov.mendoza.PrometeoMuni.dao.EquipoDao;
import ar.gov.mendoza.PrometeoMuni.dao.LocalidadDao;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalVar;
import ar.gov.mendoza.PrometeoMuni.sync.ActaConstatacionSync;

/*
	 * Business Rules for Cities
	 */
public class LocalidadRules {

	private Context context;
	
	public LocalidadRules (Context context) {
		this.context = context;
	}
	/*
	 * Get All Items of Cities from Databases
	 */
	public List<Localidad> getAll()
	{
		List<Localidad> lstLocalidades = new ArrayList<Localidad>();
		LocalidadDao localidadDao = new LocalidadDao();
		String[] aSort = {"NOMBRE"};
		lstLocalidades  = localidadDao.getAll(aSort);
		
		return lstLocalidades;
		
	}

	public Localidad getLocalidadById(Integer pIdLocalidad)
	{
		Localidad entidad = new Localidad();
		LocalidadDao entidadDao = new LocalidadDao();
		
		entidad = entidadDao.getItem(pIdLocalidad.toString());
		 
		return entidad;
	}
	
	/*
	 * Get All Items of Provinces from Databases
	 */
	public List<Localidad> getByNombre (String pNombreLocalidad)
	{
		List<Localidad> lstLocalidades = new ArrayList<Localidad>();
		LocalidadDao localidadDao = new LocalidadDao();
		ContentValues cvalues = new ContentValues();
		
		cvalues.put("NOMBRE", pNombreLocalidad);
		
		lstLocalidades  = localidadDao.getByFilter(cvalues);
		
		return lstLocalidades;
		
	}
	/*
	 * Get All Items of Provinces from Databases
	 */
	public List<Localidad> getByDepartamento (String pIdDepartamento)
	{
		List<Localidad> lstLocalidades = new ArrayList<Localidad>();
		LocalidadDao localidadDao = new LocalidadDao();
		ContentValues cvalues = new ContentValues();
		
		cvalues.put("ID_DEPARTAMENTO", pIdDepartamento);
		
		lstLocalidades  = localidadDao.getByFilter(cvalues);
		
		return lstLocalidades;
		
	}
	/*
	 * Get All Items of Provinces from Databases
	 */
	public List<Localidad> getByDepartamentoForSpinner (String pIdDepartamento)
	{
		List<Localidad> lstLocalidades = new ArrayList<Localidad>();
		LocalidadDao localidadDao = new LocalidadDao();
		ContentValues cvalues = new ContentValues();
		
		cvalues.put("ID_DEPARTAMENTO", pIdDepartamento);
		
		lstLocalidades  = localidadDao.getByFilter(cvalues);
		
		
		Localidad pLocalidad = new Localidad(null,"Seleccione una Localidad (" +  lstLocalidades.size() + ")", "-1");
		lstLocalidades.add(pLocalidad);
		if(pIdDepartamento.equals("-1"))
		{ 
			pLocalidad = new Localidad(-1,"NO APARECE EN LA LISTA (Lo Escribire Manualmente)", "-1");
			lstLocalidades.add(0,pLocalidad);
		}
		
		
		return lstLocalidades;
		
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
