package com.fpoly.main.menu;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fpoly.englishfunforkid.R;

public class MenuListAdapter extends BaseAdapter {

	private final int MENU_TYPE_HEADER = 0;
	private final int MENU_TYPE_ITEM = 1;
	
	private Context context;
	private ArrayList<MenuItem> navDrawerItems;
	
	
	public MenuListAdapter(Context context, ArrayList<MenuItem>navDrawerItems){
		
		this.context = context;
		this.navDrawerItems = navDrawerItems;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int type = navDrawerItems.get(position).getMENU_TYPE();

		if (type == MENU_TYPE_HEADER)
		{
			if (convertView == null) {
				
				LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = mInflater.inflate(R.layout.menu_header, null);
				
				TextView txtheader = (TextView) convertView.findViewById(R.id.tvHeader);
				txtheader.setText(navDrawerItems.get(position).getTitle());
			}
		}
		else if (type == MENU_TYPE_ITEM)
		{
			if (convertView == null) {
				
				LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = mInflater.inflate(R.layout.menu_item, null);
				
				ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
				TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

				imgIcon.setImageResource( navDrawerItems.get(position).getIcon());
				txtTitle.setText(navDrawerItems.get(position).getTitle());
			}
		}
		
		return convertView;
	}
	

}
