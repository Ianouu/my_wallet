package com.iancl.mywallet.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iancl.mywallet.R;
import com.iancl.mywallet.activities.Main2Activity;
import com.iancl.mywallet.model.AllCoinsApp;
import com.iancl.mywallet.model.Client;
import com.iancl.mywallet.model.Coin;
import com.iancl.mywallet.model.PERCENT_CHANGED;


/**
 * Created by iancl on 02/12/2017.
 */

public class AllCoinAdapter extends ArrayAdapter<Coin> {

    private AllCoinsApp allcoins;
    //tweets est la liste des models à afficher
    public AllCoinAdapter(Context context, AllCoinsApp allcoins) {
        super(context, 0, allcoins.getCoins());
        this.allcoins = allcoins;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_all_coin,parent, false);
        }

        CoinViewHolder viewHolder = (CoinViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new CoinViewHolder();

            viewHolder.name = (TextView) convertView.findViewById(R.id.text_name);
            viewHolder.price = (TextView) convertView.findViewById(R.id.text_price_all);
            viewHolder.variation = (TextView) convertView.findViewById(R.id.text_variation);
            viewHolder.fullName = (TextView) convertView.findViewById(R.id.text_fullname);
            viewHolder.rank = (TextView) convertView.findViewById(R.id.text_rang);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.layout = (LinearLayout) convertView.findViewById(R.id.fullLayout);

            convertView.setTag(viewHolder);
        }

        Coin coin = getItem(position);

        viewHolder.name.setText(coin.getName());
        viewHolder.fullName.setText(coin.getFullName());
        viewHolder.price.setText(coin.getFormattedPrice());
        viewHolder.id = coin.getId();
        viewHolder.variation.setText(coin.getPercentChanged());
        viewHolder.rank.setText(""+coin.getRang());



        if (coin.isSoldUp()) {
            viewHolder.variation.setTextColor(getContext().getResources().getColor(R.color.colorGreen));
            viewHolder.variation.setText("+"+viewHolder.variation.getText());

        } else {
            viewHolder.variation.setTextColor(getContext().getResources().getColor(R.color.colorRed));
        }





        int imageResource = getContext().getResources().getIdentifier(coin.getImagePath(), "drawable", getContext().getPackageName());
        if ( imageResource == 0) {
            imageResource = getContext().getResources().getIdentifier("ic_unk", "drawable", getContext().getPackageName());
        }
        viewHolder.image.setImageResource(imageResource);


        return convertView;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Main2Activity.walletApp.refreshData();
        Main2Activity.totalCoins.setText(Main2Activity.walletApp.getFormatedTotalClient());
        synchronized (Main2Activity.totalCoins) {
            Main2Activity.totalCoins.invalidate();
        }
    }


    public class CoinViewHolder{
        public TextView name;
        public TextView fullName;
        public TextView price;
        public TextView rank;
        public TextView variation;
        public ImageView image;
        public LinearLayout layout;
        public int id;

        public Coin getAssociateCoin() {
            Coin c = new Coin();
            c.setName(this.name.getText().toString());
            c.setPrice("0");
            c.setId(this.id);
            return c;
        }
    }
}
