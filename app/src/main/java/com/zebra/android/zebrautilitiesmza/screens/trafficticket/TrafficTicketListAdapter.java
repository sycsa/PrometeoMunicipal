 package com.zebra.android.zebrautilitiesmza.screens.trafficticket;
 
 import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import ar.gov.mendoza.PrometeoMuni.R;

import java.util.ArrayList;
 
 public class TrafficTicketListAdapter
   extends BaseAdapter
 {
   private static final String[] labels = { "First Name:", "Last Name:", "Address:", "Priors:" };
   ArrayList<String> arrayList;
   LayoutInflater mInflater;
   
   public TrafficTicketListAdapter(Context context, ArrayList<String> setArrayList)
   {
     this.arrayList = setArrayList;
     this.mInflater = LayoutInflater.from(context);
   }
   
   public int getCount()
   {
     return this.arrayList.size();
   }
   
   public Object getItem(int position)
   {
     return this.arrayList.get(position);
   }
   
   public long getItemId(int position)
   {
     return position;
   }
   
   public View getView(int position, View convertView, ViewGroup parent)
   {										//traffic_ticket_list_item
	 View localView = convertView;
	 if (localView==null)
		 localView = this.mInflater.inflate(R.layout.traffic_ticket_list_item, null);//2130903064
	 
     TextView label = localView.findViewById(R.id.violator_cell_label);//2131296353
     
 
     label.setTypeface(Typeface.defaultFromStyle(1));
     TextView info = localView.findViewById(R.id.violator_cell_info);//2131296354
     
 
     info.setTypeface(Typeface.defaultFromStyle(1));
     if (position < 4)
     {
       label.setText(labels[position]);
       info.setText(this.arrayList.get(position));
     }
     return localView;
   }
 }