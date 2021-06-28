package ar.gov.mendoza.PrometeoMuni.adapters;

import android.widget.ArrayAdapter;
import ar.gov.mendoza.PrometeoMuni.entities.Ruta;
import android.content.*;
import android.view.*;
import android.widget.*;
import android.graphics.*;

public class SpinAdapter extends ArrayAdapter<Ruta>{
	
	  // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    private Ruta[] values;

    public SpinAdapter(Context context, int textViewResourceId,Ruta[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount(){
       return values.length;
    }

    public Ruta getItem(int position){
       return values[position];
    }

    public long getItemId(int position){
       return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(values[position].getName());

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
