package com.fpoly.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fpoly.englishfunforkid.R;
import com.fpoly.object.English;

public class ListAdapter extends BaseAdapter {
	ArrayList<English> lists;
	LayoutInflater inflater;
	Context context;

	public ListAdapter(Context context,ArrayList<English>list) {
		this.context = context;
		this.lists = list;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int position) {

		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int postion, View convertView, ViewGroup parent) {
		ViewHolder holder;
		View v = convertView;
		if (convertView == null) {
			v = inflater.inflate(R.layout.textview, null);
			holder = new ViewHolder();
			holder.textItem = (TextView) v
					.findViewById(R.id.txtDetail);
			v.setTag(holder);

		} else {
			holder = (ViewHolder) v.getTag();

		}
		holder.textItem.setText(lists.get(postion).getDecription());
		return v;
	}

	public class ViewHolder {
		TextView textItem;

	}

}
