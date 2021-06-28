package ar.gov.mendoza.PrometeoMuni.adapters;

import java.util.List;

import android.widget.TextView;
import android.app.Activity;
import android.view.*;
import android.widget.*;

import ar.gov.mendoza.PrometeoMuni.R;
import ar.gov.mendoza.PrometeoMuni.core.domain.ISeleccionable;

public class GenericListAdapter<T extends ISeleccionable<String>> extends GenericAdapter<T>
{
	 private final Activity context;
	 private final int resourceId;
	  static class ViewHolder {
		    public TextView textPrincipal;
		    public TextView textResumen;
		    public ImageView   btnItem;
		  }
	  
	 
	  
    public GenericListAdapter(Activity context, int textViewResourceId,T[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.resourceId = textViewResourceId;
    }

    public GenericListAdapter(Activity context, int textViewResourceId,List<T> lstValues) {
    	super(context, textViewResourceId,lstValues);
    	this.context = context;
    	this.resourceId = textViewResourceId;
    }

  

    @Override
    public int getCount() {
        // don't display last item. It is used as hint.
        int count = super.getCount();
        return count ;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	View rowView = convertView;
        // reuse views
        if (rowView == null) {
          LayoutInflater inflater = this.context.getLayoutInflater();
          rowView = inflater.inflate(this.resourceId, null);//R.layout.list_item_with_two_text
          // configure view holder
          ViewHolder viewHolder = new ViewHolder();
          viewHolder.textPrincipal = rowView.findViewById(R.id.id_entry);
          viewHolder.textResumen = rowView.findViewById(R.id.description_entry);
          viewHolder.btnItem = rowView.findViewById(R.id.itemButton);
          rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        String s = this.getItem(position).getItemName();
        holder.textPrincipal.setText(s);
        if( holder.textResumen!=null && this.getItem(position).getItemDescription()!=null)
        {
        	holder.textResumen.setText(this.getItem(position).getItemDescription());
        }
        if(holder.btnItem!=null )
        {
        	String itemid =this.getItem(position).getItemId(); 
        	String itemname=this.getItem(position).getItemName();
        	if(itemid.equals("-1"))
        		holder.btnItem.setVisibility(View.GONE);
        	
        	holder.btnItem.setTag(this.getItem(position));
        }
//        if (s.startsWith("Windows7") || s.startsWith("iPhone")
//            || s.startsWith("Solaris")) {
//          holder.image.setImageResource(R.drawable.no);
//        } else {
//          holder.image.setImageResource(R.drawable.ok);
//        }

        return rowView;
//        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
//        //TextView label = new TextView(context);
//    	TextView label = new TextView(this.getContext());
//        label.setTextColor(Color.BLACK);
//        // Then you can get the current item using the values array (Users array) and the current position
//        // You can NOW reference each method you has created in your bean object (User class)
//        
//        //label.setText(values[position].toString());
//        label.setText(this.getItem(position).getItemName());
//
//        label.setHeight(30);
//        label.setMinimumHeight(55);
//        label.setTextSize(20);
//        
//        
//        // And finally return your dynamic (or custom) view for each spinner item
//        return label;
    }

}
