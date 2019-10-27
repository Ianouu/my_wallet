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
import com.iancl.mywallet.activities.Main2Activity;
import com.iancl.mywallet.R;
import com.iancl.mywallet.model.Client;
import com.iancl.mywallet.model.Coin;
import com.iancl.mywallet.model.PERCENT_CHANGED;


/**
 * Created by iancl on 02/12/2017.
 */

public class CoinAdapter extends ArrayAdapter<Coin> {

    private Client client;
    //tweets est la liste des models à afficher
    public CoinAdapter(Context context, Client client) {
        super(context, 0, client.getCoins());
        this.client = client;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_coin,parent, false);
        }

        CoinViewHolder viewHolder = (CoinViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new CoinViewHolder();

            viewHolder.name = (TextView) convertView.findViewById(R.id.text_name);
            viewHolder.price = (TextView) convertView.findViewById(R.id.text_price);
            viewHolder.amount = (TextView) convertView.findViewById(R.id.text_ammount);
            viewHolder.sold = (TextView) convertView.findViewById(R.id.text_sold);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.layout = (LinearLayout) convertView.findViewById(R.id.fullLayout);

            convertView.setTag(viewHolder);
        }

        Coin coin = getItem(position);

        viewHolder.name.setText(coin.getName());
        viewHolder.price.setText(coin.getFormattedPrice());
        viewHolder.amount.setText(coin.getAmount());
        viewHolder.id = coin.getId();
        viewHolder.sold.setText(coin.getFormattedSold());





        // If colors is activated
            int[] colors = new int[2];
            colors[0] = Color.TRANSPARENT;
            colors[1] = Color.TRANSPARENT;
            if (Main2Activity.walletApp.getTime_to_refresh().equals(PERCENT_CHANGED.NA)){
                colors[0] = Color.TRANSPARENT;
            }
            else if ( coin.isSoldUp()) {
                colors[0] = getContext().getResources().getColor(R.color.colorUpCoin);
            } else if (!coin.isSoldUp()) {
                colors[0] = getContext().getResources().getColor(R.color.colorDownCoin);
            } else {
                colors[0] = Color.TRANSPARENT;
            }

            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, colors);
            gd.setGradientType(GradientDrawable.RADIAL_GRADIENT);
            gd.setGradientRadius(700f);
            gd.setCornerRadius(0f);
            convertView.setBackgroundDrawable(gd);



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


    public void notifyDataSetChangedFirstLoad() {
        super.notifyDataSetChanged();
        Main2Activity.totalCoins.setText(Main2Activity.walletApp.getFormatedTotalClient());
        synchronized (Main2Activity.totalCoins) {
            Main2Activity.totalCoins.invalidate();
        }
    }

    public class CoinViewHolder{
        public TextView name;
        public TextView price;
        public TextView amount;
        public TextView sold;
        public ImageView image;
        public LinearLayout layout;
        public int id;

        public Coin getAssociateCoin() {
            Coin c = new Coin();
            c.setName(this.name.getText().toString());
            c.setPrice("0");
            c.setAmount(this.amount.getText().toString());
            c.setId(this.id);
            return c;
        }
    }
}
