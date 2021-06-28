 package com.zebra.android.zebrautilitiesmza.statusdialog;
 
 import android.content.Context;
 import android.graphics.Typeface;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.BaseAdapter;
 import android.widget.TextView;
 import java.util.ArrayList;
 
 public class StatusDialogListAdapter
 extends BaseAdapter
 {
   ArrayList<String> arrayList;
   LayoutInflater mInflater;
   
   public StatusDialogListAdapter(Context context, ArrayList<String> setArrayList)
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
   {
     View localView = this.mInflater.inflate(2130903046, null);
     TextView firstLine = localView.findViewById(2131296284);
     firstLine.setTypeface(Typeface.defaultFromStyle(1));
     firstLine.setText(this.arrayList.get(position));
     return localView;
   }
 }

