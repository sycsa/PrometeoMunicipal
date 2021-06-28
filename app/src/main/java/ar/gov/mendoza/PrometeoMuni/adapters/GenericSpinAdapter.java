package ar.gov.mendoza.PrometeoMuni.adapters;

import java.util.List;

import android.content.*;

import ar.gov.mendoza.PrometeoMuni.core.domain.ISeleccionable;

public class GenericSpinAdapter <T extends ISeleccionable<String>> extends GenericAdapter<T> 
{
//<T extends ISeleccionable<String>> extends ArrayAdapter<T>{//we added Generic Class T at the last
	

    public GenericSpinAdapter(Context context, int textViewResourceId,T[] values) {
        super(context, textViewResourceId, values);
    }

    public GenericSpinAdapter(Context context, int textViewResourceId,List<T> lstValues) {
    	super(context, textViewResourceId,lstValues);
    	
    }
    
    @Override
    public int getCount() {
        int count = super.getCount();
        return count > 0 ? count - 1 : count;
    }



}
