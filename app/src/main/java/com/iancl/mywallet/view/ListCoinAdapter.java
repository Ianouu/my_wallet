package com.iancl.mywallet.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.iancl.mywallet.R;
import com.iancl.mywallet.model.Coin;

import java.util.List;

/**
 * Created by iancl on 07/12/2017.
 */

public class ListCoinAdapter extends ArrayAdapter<Coin> {

    public ListCoinAdapter(Context context, List<Coin> coins) {

        super(context, 0, coins);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_list_coin,parent, false);
        }

        ListCoinAdapter.ListCoinViewHolder viewHolder = (ListCoinAdapter.ListCoinViewHolder) convertView.getTag();
        if(viewHolder == null){

            viewHolder.name = (TextView) convertView.findViewById(R.id.text_name);
            viewHolder.fullName = (TextView) convertView.findViewById(R.id.text_nameFull);
            convertView.setTag(viewHolder);
        }

        Coin coin = getItem(position);

        viewHolder.name.setText(coin.getName());
        viewHolder.fullName.setText(coin.getFullName());

        return convertView;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

    }

    public class ListCoinViewHolder{
        public TextView name;
        public TextView fullName;

    }
}
