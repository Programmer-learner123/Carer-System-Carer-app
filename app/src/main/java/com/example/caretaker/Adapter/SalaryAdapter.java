package com.example.caretaker.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.caretaker.Model.ModelSalary;
import com.example.caretaker.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class SalaryAdapter extends RecyclerView.Adapter<SalaryAdapter.ViewHolder> {

    private Context context;

    SharedPreferences sp;
    SharedPreferences.Editor ed;

    //List of superHeroes
    List<ModelSalary> Heroes;

    public SalaryAdapter(List<ModelSalary> Heroes, Context context) {
        super();
        //Getting all the heroes
        this.Heroes = Heroes;
        this.context = context;

        sp = context.getSharedPreferences("CART", Context.MODE_PRIVATE);
        ed = sp.edit();
    }

    @Override
    public SalaryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_salary, parent, false);
        SalaryAdapter.ViewHolder viewHolder = new SalaryAdapter.ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final SalaryAdapter.ViewHolder holder, int position) {

        final ModelSalary Hero = Heroes.get(position);


        holder.textcarerEmail.setText(Hero.getCarer_email());
        holder.Textsalary.setText(Hero.getSalary());
        holder.Textdays.setText(Hero.getDays());
        holder.textdate.setText(Hero.getDate());


        // holder.imageView.setImageUrl(Hero.getImage(), imageLoader);


    }

    @Override
    public int getItemCount() {
        return Heroes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        public TextView textcarerEmail;
        public TextView Textsalary;

        public TextView Textdays;

        public TextView textdate;


        public ViewHolder(View itemView) {
            super(itemView);


            textcarerEmail = (TextView) itemView.findViewById(R.id.textcarerEmail);


            Textsalary = (TextView) itemView.findViewById(R.id.textSalary);
            Textdays = (TextView) itemView.findViewById(R.id.textDays);



            textdate = (TextView) itemView.findViewById(R.id.textDate);


        }
    }
}
