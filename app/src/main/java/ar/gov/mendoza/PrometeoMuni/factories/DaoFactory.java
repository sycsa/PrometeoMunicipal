package ar.gov.mendoza.PrometeoMuni.factories;

import java.util.HashMap;
import java.util.Map;

import ar.gov.mendoza.PrometeoMuni.dao.IBaseDao;
import ar.gov.mendoza.PrometeoMuni.dao.LocalidadDao;
import ar.gov.mendoza.PrometeoMuni.dao.PaisDao;
import ar.gov.mendoza.PrometeoMuni.dao.PersonaPadronDao;

@SuppressWarnings("rawtypes")
public class DaoFactory {
	
	private static DaoFactory instance;
	
	
	private Map<Class, Object> managers;
	
	private DaoFactory() {
		
		 populateFactory();
		
	}
	
	private void populateFactory()
	  {
	    this.managers = new HashMap<Class, Object>();
	    this.managers.put(PaisDao.class, new PaisDao());
	    this.managers.put(LocalidadDao.class, new LocalidadDao());
	    
	  }
	
	public Object getManager(Class interfaceClass)
	  {
	    if(this.managers.containsKey(interfaceClass))
	      return this.managers.get(interfaceClass);
	    else 
	      return null;
	  }
	
	public static void InitInstance()
	  {
		  if(instance==null)
			  instance = new DaoFactory();
	  }
	  public static DaoFactory getInstance()
	  {
		 InitInstance();
	     return instance;
	  }
	
	public IBaseDao<?> createDao(String tClass)
	{
		
		String sClass = tClass;//.getSimpleName();
		
		switch (sClass) {
		case "Pais":
			return new PaisDao();

		case "Persona":
			return new PersonaPadronDao();
			
		/*case "Persona":
			return (IBaseDao<T>) (new PersonaDao());	*/
			
		default:
			return null;
		}
		
		
	}
		
		
}
