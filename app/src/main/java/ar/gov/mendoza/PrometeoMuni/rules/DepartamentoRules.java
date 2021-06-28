package ar.gov.mendoza.PrometeoMuni.rules;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import ar.gov.mendoza.PrometeoMuni.core.domain.Departamento;
import ar.gov.mendoza.PrometeoMuni.core.domain.Equipo;
import ar.gov.mendoza.PrometeoMuni.core.providers.ActaConstatacionProvider;
import ar.gov.mendoza.PrometeoMuni.dao.DepartamentoDao;
import ar.gov.mendoza.PrometeoMuni.dao.EquipoDao;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalVar;
import ar.gov.mendoza.PrometeoMuni.sync.ActaConstatacionSync;

/*
	 * Bussines Rules for Countries
	 */
public class DepartamentoRules {

	private Context context;
	
	public DepartamentoRules (Context context) {
		this.context = context;
	}
	/*
	 * Get All Items of Provinces from Databases
	 */
	public List<Departamento> getAll()
	{
		List<Departamento> lstDepartamentos = new ArrayList<Departamento>();
		DepartamentoDao departamentoDao = new DepartamentoDao();
		
		lstDepartamentos  = departamentoDao.getAll();
		
		return lstDepartamentos;
		
	}
	
	
	public Departamento getDepartamentoById(Integer pIdDepartamento)
	{
		Departamento entidad = new Departamento();
		DepartamentoDao entidadDao = new DepartamentoDao();
		
		entidad = entidadDao.getItem(pIdDepartamento.toString());
		 
		return entidad;
	}
	/*
	 * Get All Items of Provinces from Databases
	 */
	public List<Departamento> getByProvincia (String pIdProvincia)
	{
		List<Departamento> lstDepartamentos = new ArrayList<Departamento>();
		DepartamentoDao departamentoDao = new DepartamentoDao();
		ContentValues cvalues = new ContentValues();
		
		cvalues.put("ID_PROVINCIA", pIdProvincia);
		
		lstDepartamentos  = departamentoDao.getByFilter(cvalues);
		
		return lstDepartamentos;
		
	}
	
	/*
	 * Get All Items of Provinces from Databases
	 */
	public List<Departamento> getByProvinciaForSpinner (String pIdProvincia)
	{
		List<Departamento> lstDepartamentos = new ArrayList<Departamento>();
		DepartamentoDao departamentoDao = new DepartamentoDao();
		ContentValues cvalues = new ContentValues();
		
		cvalues.put("ID_PROVINCIA", pIdProvincia);
		
		lstDepartamentos  = departamentoDao.getByFilter(cvalues);
		Departamento pDepartamento = new Departamento(null,"Seleccione un Departamento (" + lstDepartamentos.size() + ")", "");
		lstDepartamentos.add(pDepartamento);
		if(pIdProvincia.equals("-1"))
		{
			pDepartamento = new Departamento(-1,"NO APARECE EN LA LISTA (Lo Escribire Manualmente)", "-1");
			lstDepartamentos.add(0,pDepartamento);
		}
		
		
		return lstDepartamentos;
		
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
