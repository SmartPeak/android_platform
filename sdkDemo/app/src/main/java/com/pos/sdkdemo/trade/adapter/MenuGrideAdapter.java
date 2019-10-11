package com.pos.sdkdemo.trade.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pos.sdkdemo.R;
import com.pos.sdkdemo.trade.bean.MenuBean;

import java.util.List;

public class MenuGrideAdapter extends BaseAdapter {
    private Context context;
    private List<MenuBean> datas;
    private LayoutInflater inflater;

    public MenuGrideAdapter(Context context, List<MenuBean> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_menu_gride_item,parent,false);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageResource(datas.get(position).getResId());
        viewHolder.textView.setText(datas.get(position).getName());
        return convertView;
    }

    private class ViewHolder{
        public ImageView imageView;
        public TextView textView;
    }
}
