package com.example.caretaker.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.caretaker.Model.Modalclientview;
import com.example.caretaker.R;

import java.util.List;

public class CardAdapterClient extends RecyclerView.Adapter<CardAdapterClient.ViewHolder> {

    private Context context;

    SharedPreferences sp;
    SharedPreferences.Editor ed;

    //List of superHeroes
    List<Modalclientview> Heroes;

    public CardAdapterClient(List<Modalclientview> Heroes, Context context) {
        super();
        //Getting all the heroes
        this.Heroes = Heroes;
        this.context = context;

        sp = context.getSharedPreferences("CART", Context.MODE_PRIVATE);
        ed = sp.edit();
    }

    @Override
    public CardAdapterClient.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clientsvisit, parent, false);
        CardAdapterClient.ViewHolder viewHolder = new CardAdapterClient.ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final CardAdapterClient.ViewHolder holder, int position) {

        final Modalclientview Hero = Heroes.get(position);


        holder.textcarename.setText(Hero.getClient_name());
        holder.textcareemail.setText(Hero.getClient_email());
        holder.textcarenamernum.setText(Hero.getClient_phone());
        holder.textcareaddress.setText(Hero.getClient_address());
        holder.textcarenamedate.setText(Hero.getStart_date());
        holder.textstime.setText(Hero.getStart_time());
        holder.textetime.setText(Hero.getEnd_time());


        // holder.imageView.setImageUrl(Hero.getImage(), imageLoader);


    }

    @Override
    public int getItemCount() {
        return Heroes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textcarename;
        public TextView textcareemail;


        public TextView textcarenamernum;
        public TextView textcareaddress;

        public TextView textcarenamedate;
        public TextView textstime;
        public TextView textetime;


        public ViewHolder(View itemView) {
            super(itemView);


            textcarename = (TextView) itemView.findViewById(R.id.textcarername);
            textcareemail = (TextView) itemView.findViewById(R.id.textcareremail);


            textcarenamernum = (TextView) itemView.findViewById(R.id.textcarernumber);
            textcareaddress = (TextView) itemView.findViewById(R.id.textcareradd);


            textcarenamedate = (TextView) itemView.findViewById(R.id.textcaredate);

            textstime = (TextView) itemView.findViewById(R.id.textcarestime);
            textetime = (TextView) itemView.findViewById(R.id.textcareretime);


        }
    }
}