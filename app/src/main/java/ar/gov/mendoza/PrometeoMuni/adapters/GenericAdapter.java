	package ar.gov.mendoza.PrometeoMuni.adapters;

import java.util.List;

import android.widget.ArrayAdapter;
import android.content.*;
import android.view.*;
import android.widget.*;
import android.graphics.*;
import ar.gov.mendoza.PrometeoMuni.core.domain.ISeleccionable;

public class GenericAdapter<T extends ISeleccionable<String>> extends ArrayAdapter<T>{//we added Generic Class T at the last
	
	// Your sent context
    //private Context context;
    
    // Your custom values for the spinner (User)
    //private T[] values = null;

    public GenericAdapter(Context context, int textViewResourceId,T[] values) {
        super(context, textViewResourceId, values);
        //this.context = context;
        //this.values = values;
    }

    public GenericAdapter(Context context, int textViewResourceId,List<T> lstValues) {
    	super(context, textViewResourceId,lstValues);
    	
    	//this.context = context;
    	//if(lstValues!=null)
    	//	lstValues.toArray(this.values);
    }
    
    /*
    public T[] getValues()
    {
    	return values;
    }
    */
    /*
    public int getCount(){
       return values.length;
    }*/
    /*
    @Override
    public int getCount() {
        // don't display last item. It is used as hint.
        int count = super.getCount();
        return count > 0 ? count - 1 : count;
    }*/

    /* NO HACE FALTA
    public T getItem(int position){
       return values[position];
    }
    */
    /* NO HACE FALTA
    public long getItemId(int position){
       return position;
    }*/
    

    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    //RESTAURAR 2016
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        //TextView label = new TextView(context);
    	TextView label = new TextView(this.getContext());
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        
        //label.setText(values[position].toString());
        try
        {
            label.setText(this.getItem(position).getItemName());
        } catch(Exception ex)
        {
        	String sex = ex.getMessage();
        }
        
        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
            ViewGroup parent) {
        /*TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(values[position].getName());*/
        //return label;
        return super.getDropDownView(position, convertView, parent);

    }

}
