package ar.gov.mendoza.PrometeoMuni.rules;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentProvider;
import android.content.Context;
import ar.gov.mendoza.PrometeoMuni.core.domain.Equipo;
import ar.gov.mendoza.PrometeoMuni.core.providers.ActaConstatacionProvider;
import ar.gov.mendoza.PrometeoMuni.dao.EquipoDao;
import ar.gov.mendoza.PrometeoMuni.dao.PaisDao;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalVar;
import ar.gov.mendoza.PrometeoMuni.sync.ActaConstatacionSync;
import ar.gov.mendoza.PrometeoMuni.core.domain.Pais;

	/*
	 * Bussines Rules for Countries
	 */
public class PaisRules {

	private Context context;
	
	public PaisRules (Context context) {
		this.context = context;
	}
	/*
	 * Get All Items of Countries from Databases
	 */
	public List<Pais> getAll()
	{
		List<Pais> lstPaises = new ArrayList<Pais>();
		PaisDao paisDao = new PaisDao();
		String[] aSort = {"NOMBRE" }; 
		lstPaises  = paisDao.getAll(aSort);
		
		return lstPaises;
		
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
