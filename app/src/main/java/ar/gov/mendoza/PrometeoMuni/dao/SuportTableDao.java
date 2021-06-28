package ar.gov.mendoza.PrometeoMuni.dao;

import ar.gov.mendoza.PrometeoMuni.core.domain.SuportTable;


public class SuportTableDao extends BaseDao<SuportTable>
{

	public SuportTableDao(){
		super(SuportTable.class);
	}
	public SuportTable getSuportTable() 
	{

		SuportTableDao suportTableDao = new SuportTableDao();
		SuportTable suportTable= suportTableDao.getItem("1");
			
		return suportTable;
	}
}

