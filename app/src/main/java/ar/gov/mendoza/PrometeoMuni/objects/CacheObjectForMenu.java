package ar.gov.mendoza.PrometeoMuni.objects;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import ar.gov.mendoza.PrometeoMuni.core.domain.ActaConstatacion;
import ar.gov.mendoza.PrometeoMuni.rules.ActaConstatacionRules;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalStateApp;

public class CacheObjectForMenu {
	/**
	  *  The constructor of SomeExpensiveObject does nothing, 
	  *  but sleeps for 5 seconds before returning the call.  
	  *  This signifies that its really expensive to create an
	  *  instance of this class.  
	  */
	
	

	

	public List<ActaConstatacion> lstActasConstatacion;
	
	
	private List<ActaConstatacion> getListActasInitialized()
	{
		
		ActaConstatacionRules rulesActas= new ActaConstatacionRules(GlobalStateApp.getInstance().getApplicationContext());
		ContentValues cv = new ContentValues();
		List<ActaConstatacion> lstActas = new ArrayList<ActaConstatacion>();//rulesPais.getActasByFilter(cv);

		lstActas = rulesActas.getAll();
		//Pais pPais = new Pais("","Seleccione un Pais");
		//lstPaises.add(pPais);
		//pPais = new Pais("-1","NO APARECE EN LA LISTA (Lo Escribire Manualmente)");
		//lstPaises.add(0,pPais);
		return lstActas;
	}
	
	
	
	 public CacheObjectForMenu() {
	  
		try {
			
			lstActasConstatacion= this.getListActasInitialized();
			
			//Thread.sleep(500);
		}
		 /*  catch (InterruptedException e) {
			//e.printStackTrace();
		}*/ 
		catch(Exception exc)
		{
			
		}
	 }

}
