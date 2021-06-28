package ar.gov.mendoza.PrometeoMuni.entities;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import ar.gov.mendoza.PrometeoMuni.core.providers.ProvinciaProvider;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalStateApp;
public class Provincia {
	    private String _id;
	    private String _name;
	    private String _idPais;
	    
	    /**
	     * @return the ID
	     */
	    public String getId() {
	        return _id;
	    }

	    /**
	     * @param name the name to set
	     */
	    public void setId(String id) {
	        this._id = id;
	    }

	    /**
	     * @return the ID
	     */
	    public String getName() {
	        return _name;
	    }

	    /**
	     * @param name the name to set
	     */
	    public void setName(String name) {
	        this._name= name;
	    }
	    
	    /**
	     * @return the ID
	     */
	    public String getIdPais() {
	        return _idPais;
	    }

	    /**
	     * @param name the name to set
	     */
	    public void setIdPais(String idPais) {
	        this._idPais = idPais;
	    }

	    
	    static public List<Provincia> lstProvincias= new  ArrayList<Provincia>();
	    
	    @Override
	    public String toString() {
	        return this._name;            
	    }
	    public Provincia(String pId,String pName,String pIdPais)
	    {
	    	_id = pId;
	    	_name =pName;
	    	_idPais = pIdPais;
	    	
	    }
	    
	    static public void InitializeCollection()
	    {
	    	if (lstProvincias.size()>0) return;
	    	
	    	//lstRutas.add(new Provincia(1,"1",2));
	    	
	    	//cargaremos el array desde base de datos
	    	Context ctx = GlobalStateApp.getInstance().getApplicationContext();
	    	String[] projections = new String[] { ProvinciaProvider.ID_PROVINCIA,ProvinciaProvider.NOMBRE,ProvinciaProvider.ID_PAIS};
			Cursor cursor = ctx.getContentResolver().query(ProvinciaProvider.CONTENT_URI,	projections, null, null,ProvinciaProvider.NOMBRE);
			if(cursor != null && cursor.getCount() > 0 && cursor.moveToFirst())	
			{
				 do
	                {
	                    String pId = cursor.getString(cursor.getColumnIndex(ProvinciaProvider.ID_PROVINCIA));
	                    String pName =cursor.getString(cursor.getColumnIndex(ProvinciaProvider.NOMBRE));
	                    String pIdPais = cursor.getString(cursor.getColumnIndex(ProvinciaProvider.ID_PAIS));
	                    Provincia provincia = new Provincia (pId, pName,pIdPais);	
	                    lstProvincias.add(provincia );
	                }
	                // move the cursor's pointer up one position.
	                while (cursor.moveToNext());
				
			}
			cursor.close();
	    }
	    
	   static public List<Provincia> All(){
			   InitializeCollection();
		   
		   return lstProvincias;
	   }
	   
	   static public List<Provincia> GetByPais(String pIdPais)
	   {
			   InitializeCollection();
		   /**
	        ** Creating Filter Logic
	        **/
	        Filter<Provincia, String> filter = new Filter<Provincia,String>() {
	            public boolean isMatched(Provincia object, String entero) {
	                return object.getIdPais().equals(entero);
	            }
	        };
		/**
		* Pass filter to filter the original list
		*/
	        List<Provincia> sortEmpList =  new FilterList<String>().filterList(lstProvincias, filter,pIdPais);
	       return sortEmpList;
	   }
}
