package ar.gov.mendoza.PrometeoMuni.entities;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import ar.gov.mendoza.PrometeoMuni.core.providers.PaisProvider;
import ar.gov.mendoza.PrometeoMuni.singleton.GlobalStateApp;
public class Pais {
	
	    private String _id;
	    private String _name;
	    
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
	    
	    

	    
	    static public List<Pais> lstPaises = new  ArrayList<Pais>();
	    
	    @Override
	    public String toString() {
	        return this._name;            
	    }
	    public Pais(String  pId,String pName)
	    {
	    	_id = pId;
	    	_name =pName;
	    	
	    }
	    
	    static public void InitializeCollection()
	    {
	    	if (lstPaises.size()>0) return;
	    	//cargaremos el array desde base de datos
	    	Context ctx = GlobalStateApp.getInstance().getApplicationContext();
	    	String[] projections = new String[] { PaisProvider.ID_PAIS,PaisProvider.NOMBRE};
			Cursor cursor = ctx.getContentResolver().query(PaisProvider.CONTENT_URI,	projections, null, null,PaisProvider.NOMBRE);
			if(cursor != null && cursor.getCount() > 0 && cursor.moveToFirst())	
			{
				 do
	                {
	                    String pId = cursor.getString(cursor.getColumnIndex(PaisProvider.ID_PAIS));
	                    String pName =cursor.getString(cursor.getColumnIndex(PaisProvider.NOMBRE));
	                    Pais pais = new Pais(pId, pName);	
	                    lstPaises.add(pais);
	                }
	                // move the cursor's pointer up one position.
	                while (cursor.moveToNext());
				
			}
			cursor.close();
			
	    }
	    
	   static public List<Pais> All(){
			   InitializeCollection();
		   
		   return lstPaises;
	   }
	   
}
