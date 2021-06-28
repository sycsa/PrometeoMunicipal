package ar.gov.mendoza.PrometeoMuni.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import ar.gov.mendoza.PrometeoMuni.core.domain.ActualizacionWebService;

public class ActualizacionWebServiceDao extends BaseDao<ActualizacionWebService>
{

	public ActualizacionWebServiceDao() {
		
		super(ActualizacionWebService.class);
	}
	
	public ActualizacionWebService getByIdActualizacionRepat(String idActualizacionRepat)
	{ 
		ActualizacionWebService actualizacionWebService = new ActualizacionWebService();
		
		List<ActualizacionWebService> lstItems= new ArrayList<ActualizacionWebService>();
		ActualizacionWebServiceDao actualizacionWebServiceDao = new ActualizacionWebServiceDao();

		ContentValues cvalues = new ContentValues();
		cvalues.put("ID_ACTUALIZACION_REPAT", idActualizacionRepat);
		
		lstItems= actualizacionWebServiceDao.getByFilter(cvalues);
		
		if (lstItems.size()>0)
			actualizacionWebService = lstItems.get(0);
			
		return actualizacionWebService;


	
	}
}
