package ar.gov.mendoza.PrometeoMuni.dao;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.ContentValues;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import ar.gov.mendoza.PrometeoMuni.R;
import ar.gov.mendoza.PrometeoMuni.core.domain.Localidad;
import ar.gov.mendoza.PrometeoMuni.core.providers.LocalidadProvider;


public class LocalidadDao extends BaseDao<Localidad>
{

	public LocalidadDao(){//Class<Localidad> class1) {
		super(Localidad.class);//class1);
	
	}
	 public void Initialize(SQLiteDatabase db)
	{
	    	
	   	 ContentValues values = new ContentValues();
		 
		 Resources res = mContext.getResources();
		 
	     XmlResourceParser _xml = res.getXml(R.xml.localidades_records);
	     
	     try
	        {
	            //Check for end of document
	            int eventType = _xml.getEventType();
	            while (eventType != XmlPullParser.END_DOCUMENT) {
	                //Search for record tags
	                if ((eventType == XmlPullParser.START_TAG) &&(_xml.getName().equals("record"))){
	                    //Record tag found, now get values and insert record
	                    String _id = _xml.getAttributeValue(null, "id");
	                    String _name = _xml.getAttributeValue(null, "name");
	                    String _iddepartamento = _xml.getAttributeValue(null, "iddepartamento");
	                    
	                    values.put(LocalidadProvider.ID_LOCALIDAD, _id);
	                    values.put(LocalidadProvider.NOMBRE, _name);
	                    values.put(LocalidadProvider.ID_DEPARTAMENTO, _iddepartamento);
	                    
	                    
	                    
	                    db.insert(LocalidadProvider.TABLE, null,values);              
	                }
	                eventType = _xml.next();
	            }
	        }
	        //Catch errors
	        catch (XmlPullParserException e)
	        {       
	            Log.e("Initialize Localidades", e.getMessage(), e);
	        }
	        catch (IOException e)
	        {
	            Log.e("Initialize Localidades", e.getMessage(), e);
	             
	        }           
	        finally
	        {           
	            //Close the xml file
	            _xml.close();
	        }
	    }
	
}