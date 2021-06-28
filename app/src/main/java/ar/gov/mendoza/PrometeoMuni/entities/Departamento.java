package ar.gov.mendoza.PrometeoMuni.entities;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import ar.gov.mendoza.PrometeoMuni.core.providers.DepartamentoProvider;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalStateApp;
public class Departamento {
	    private String _id;
	    private String _name;
	    private String _idProvincia;
	    
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
	    public String getIdProvincia() {
	        return _idProvincia;
	    }

	    /**
	     * @param name the name to set
	     */
	    public void setIdProvincia(String idProvincia) {
	        this._idProvincia = idProvincia;
	    }

	    
	    static public List<Departamento> lstDepartamentos= new  ArrayList<Departamento>();
	    
	    @Override
	    public String toString() {
	        return this._name;            
	    }
	    public Departamento(String pId,String pName,String pIdProvincia)
	    {
	    	_id = pId;
	    	_name =pName;
	    	_idProvincia = pIdProvincia;
	    	
	    }
	    
	    static public void InitializeCollection()
	    {
	    	if (lstDepartamentos.size()>0) return;
	    	
	    	//lstRutas.add(new Provincia(1,"1",2));
	    	
	    	//cargaremos el array desde base de datos
	    	Context ctx = GlobalStateApp.getInstance().getApplicationContext();
	    	String[] projections = new String[] { DepartamentoProvider.ID_DEPARTAMENTO,DepartamentoProvider.NOMBRE,DepartamentoProvider.ID_PROVINCIA};
			Cursor cursor = ctx.getContentResolver().query(DepartamentoProvider.CONTENT_URI,	projections, null, null,DepartamentoProvider.NOMBRE);
			if(cursor != null && cursor.getCount() > 0 && cursor.moveToFirst())	
			{
				 do
	                {
	                    String pId = cursor.getString(cursor.getColumnIndex(DepartamentoProvider.ID_DEPARTAMENTO));
	                    String pName =cursor.getString(cursor.getColumnIndex(DepartamentoProvider.NOMBRE));
	                    String pIdProvincia = cursor.getString(cursor.getColumnIndex(DepartamentoProvider.ID_PROVINCIA));
	                    Departamento departamento = new Departamento (pId, pName,pIdProvincia);	
	                    lstDepartamentos.add(departamento);
	                }
	                // move the cursor's pointer up one position.
	                while (cursor.moveToNext());
				
			}
			cursor.close();
	    }
	    
	   static public List<Departamento> All(){
			   InitializeCollection();
		   
		   return lstDepartamentos;
	   }
	   
	   static public List<Departamento> GetByProvincia(String pIdProvincia)
	   {
			   InitializeCollection();
		   /**
	        ** Creating Filter Logic
	        **/
	        Filter<Departamento, String> filter = new Filter<Departamento,String>() {
	            public boolean isMatched(Departamento object, String entero) {
	                return object.getIdProvincia().equals(entero);
	            }
	        };
		/**
		* Pass filter to filter the original list
		*/
	        List<Departamento> sortEmpList =  new FilterList<String>().filterList(lstDepartamentos, filter,pIdProvincia);
	       return sortEmpList;
	   }
}
