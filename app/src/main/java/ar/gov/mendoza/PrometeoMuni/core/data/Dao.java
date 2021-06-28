package ar.gov.mendoza.PrometeoMuni.core.data;

import android.content.Context;

import java.util.ArrayList;

public abstract class Dao <TEntity,TId>
{
	private DatabaseHelper helper;
	
	public Dao(Context myContext)
	{
		this.helper = new DatabaseHelper(myContext);
	}
	
	public long insert(TEntity entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void update(TEntity entity) {
		// TODO Auto-generated method stub
		
	}

	public void delete(TId id) {
		// TODO Auto-generated method stub
		
	}

	public TEntity getById(TId id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public ArrayList<TEntity> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
