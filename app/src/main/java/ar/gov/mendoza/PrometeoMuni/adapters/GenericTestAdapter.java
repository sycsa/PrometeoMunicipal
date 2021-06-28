package ar.gov.mendoza.PrometeoMuni.adapters;

import java.util.List;

import android.content.*;

import ar.gov.mendoza.PrometeoMuni.core.domain.ISeleccionable;

public class GenericTestAdapter<T extends ISeleccionable<String>> extends GenericAdapter<T> 
{
	

    public GenericTestAdapter(Context context, int textViewResourceId,T[] values) {
        super(context, textViewResourceId, values);
    }

    public GenericTestAdapter(Context context, int textViewResourceId,List<T> lstValues) {
    	super(context, textViewResourceId,lstValues);
    	
    }
    
    @Override
    public int getCount() {
        // don't display last item. It is used as hint.
        int count = super.getCount();
        return count;
    }

    

}
