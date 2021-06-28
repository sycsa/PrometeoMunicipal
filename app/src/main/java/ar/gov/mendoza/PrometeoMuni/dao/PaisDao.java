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
import ar.gov.mendoza.PrometeoMuni.core.domain.Pais;
import ar.gov.mendoza.PrometeoMuni.core.providers.PaisProvider;


public class PaisDao extends BaseDao<Pais>
{

	public PaisDao(){//Class<Pais> class1) {
		super(Pais.class);//class1);
	}
	
	public void Initialize(SQLiteDatabase db)
	{  
		 ContentValues values = new ContentValues();
		 
		 Resources res = mContext.getResources();
	     XmlResourceParser _xml = res.getXml(R.xml.paises_records);
	     
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
	                    values.put(PaisProvider.ID_PAIS, _id);
	                    values.put(PaisProvider.NOMBRE, _name);
	                    db.insert(PaisProvider.TABLE, null,values);              
	                }
	                eventType = _xml.next();
	            }
	        }
	        //Catch errors
	        catch (XmlPullParserException e)
	        {       
	            Log.e("Initialize Paises", e.getMessage(), e);
	        }
	        catch (IOException e)
	        {
	            Log.e("Initialize Paises", e.getMessage(), e);
	             
	        }           
	        finally
	        {           
	            //Close the xml file
	            _xml.close();
	        }
	}
}

