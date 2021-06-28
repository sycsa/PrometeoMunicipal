package ar.gov.mendoza.PrometeoMuni.core.rules;

import java.util.List;

import ar.gov.mendoza.PrometeoMuni.core.data.IDao;
import ar.gov.mendoza.PrometeoMuni.core.exceptions.DeviceActasValidationException;

public abstract class BaseRules <TEntity, TKey> {
	
	protected abstract IDao<TEntity, TKey> getDaoInstance();
	
	public List<TEntity> getAll() {		
		return  getDaoInstance().getAll();
    }
    
    public TEntity getById(TKey id) {
    	TEntity entity = getDaoInstance().getById(id);        
        return entity;
    }
    
    public void insert(TEntity entity) throws DeviceActasValidationException {
    	getDaoInstance().insert(entity);
    }

    public void update(TEntity entity) throws DeviceActasValidationException {
    	getDaoInstance().update(entity);
    }
    
    public void delete(TKey key) throws DeviceActasValidationException {
    	getDaoInstance().delete(key);
    }
}
