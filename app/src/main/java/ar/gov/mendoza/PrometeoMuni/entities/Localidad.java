package ar.gov.mendoza.PrometeoMuni.entities;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import ar.gov.mendoza.PrometeoMuni.core.providers.LocalidadProvider;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalStateApp;
public class Localidad {
	    private String _id;
	    private String _name;
	    private String _idDepartamento;
	    
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
	    public String getIdDepartamento() {
	        return _idDepartamento;
	    }

	    /**
	     * @param name the name to set
	     */
	    public void setIdDepartamento(String idDepartamento) {
	        this._idDepartamento = idDepartamento;
	    }

	    
	    static public List<Localidad> lstLocalidades= new  ArrayList<Localidad>();
	    
	    @Override
	    public String toString() {
	        return this._name;            
	    }
	    public Localidad(String pId,String pName,String pIdDepartamento)
	    {
	    	_id = pId;
	    	_name =pName;
	    	_idDepartamento = pIdDepartamento;
	    	
	    }
	    
	    static public void InitializeCollection()
	    {
	    	if (lstLocalidades.size()>0) return;
	    	
	    	//lstRutas.add(new Provincia(1,"1",2));
	    	
	    	//cargaremos el array desde base de datos
	    	Context ctx = GlobalStateApp.getInstance().getApplicationContext();
	    	String[] projections = new String[] { LocalidadProvider.ID_LOCALIDAD,LocalidadProvider.NOMBRE,LocalidadProvider.ID_DEPARTAMENTO};
			Cursor cursor = ctx.getContentResolver().query(LocalidadProvider.CONTENT_URI,	projections, null, null,LocalidadProvider.NOMBRE);
			if(cursor != null && cursor.getCount() > 0 && cursor.moveToFirst())	
			{
				 do
	                {
	                    String pId = cursor.getString(cursor.getColumnIndex(LocalidadProvider.ID_LOCALIDAD));
	                    String pName =cursor.getString(cursor.getColumnIndex(LocalidadProvider.NOMBRE));
	                    String pIdDepartamento = cursor.getString(cursor.getColumnIndex(LocalidadProvider.ID_DEPARTAMENTO));
	                    Localidad localidad = new Localidad (pId, pName,pIdDepartamento);	
	                    lstLocalidades.add(localidad);
	                }
	                // move the cursor's pointer up one position.
	                while (cursor.moveToNext());
				
			}
			cursor.close();
	    }
	    
	   static public List<Localidad> All(){
		   InitializeCollection();
		   return lstLocalidades;
	   }
	   
	   
	   static public List<Localidad> GetByDepartamento(String pIdDepartamento)
	   {
			   InitializeCollection();
	        Filter<Localidad, String> filter = new Filter<Localidad,String>() {
	            public boolean isMatched(Localidad object, String entero) {
	                return object.getIdDepartamento().equals(entero);
	            }
	        };

	        List<Localidad> sortEmpList =  new FilterList<String>().filterList(lstLocalidades, filter,pIdDepartamento);
	       return sortEmpList;
	   }
	   
	   
}
