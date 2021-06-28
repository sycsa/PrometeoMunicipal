package ar.gov.mendoza.PrometeoMuni.core.data;

import java.util.ArrayList;

public interface IDao<TEntity, TId> {

	long insert(TEntity entity);
	void update(TEntity entity);
	void delete(TId id);
	
	TEntity getById(TId id);
	ArrayList<TEntity> getAll();
}
