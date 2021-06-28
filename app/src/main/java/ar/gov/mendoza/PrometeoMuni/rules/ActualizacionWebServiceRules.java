package ar.gov.mendoza.PrometeoMuni.rules;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import ar.gov.mendoza.PrometeoMuni.core.domain.ActualizacionWebService;
import ar.gov.mendoza.PrometeoMuni.dao.ActualizacionWebServiceDao;

/*
	 * Bussines Rules for ActualizacionWebService
	 */
public class ActualizacionWebServiceRules {

	private Context context;
	
	public ActualizacionWebServiceRules (Context context) {
		this.context = context;
	}
	
	
	
	public void confirmarActualizacion(ActualizacionWebService item)
	{
		ActualizacionWebServiceDao actDao = new ActualizacionWebServiceDao();
		try
		{
			if (item.getId()==null || item.getId().equals("0"))
			{
				// buscar el id
				ActualizacionWebService actual = actDao.getByIdActualizacionRepat(item.getIdActualizacionRepat());
				if (actual.getId()!=null && !actual.getId().equals("0"))
				{
					item.setId(actual.getId());
					item.setComando(actual.getComando());
					item.setTipo(actual.getTipo());

				}
			}
			
			actDao.update(item);
			
		} catch(Exception ex)
		{
			
		}
	}
	public void grabarActualizacion(ActualizacionWebService item)
	{
	
			ActualizacionWebServiceDao actDao = new ActualizacionWebServiceDao();
			try
			{
				if (item.getId()== null || item.getId().equals("0"))
				{
					long idCreado =	actDao.insert(item);
				}
				else
				{
					long idActualizado = actDao.update(item);
				}	
			}
			catch(Exception ex)
			{
				String sex = ex.getMessage();
				
			}
			
	}
	/*
	 * Get All Items of Countries from Databases
	 */
	public List<ActualizacionWebService> getAll()
	{
		List<ActualizacionWebService> lstActualizaciones = new ArrayList<ActualizacionWebService>();
		ActualizacionWebServiceDao actualizacionDao = new ActualizacionWebServiceDao();
		String[] aSort = {"NOMBRE"}; 
		lstActualizaciones = actualizacionDao.getAll(aSort);
		
		return lstActualizaciones;
	}
	
}
