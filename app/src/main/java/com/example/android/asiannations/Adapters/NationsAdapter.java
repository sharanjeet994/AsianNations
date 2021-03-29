package com.example.android.asiannations.Adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.asiannations.R;
import com.example.android.asiannations.room.Nations;

import java.util.List;


public class NationsAdapter extends RecyclerView.Adapter<NationsAdapter.ItemsViewHolder> {


    private Context context;
    private List<Nations> nationsArrayList;



    public NationsAdapter(Context context, List<Nations> nationsArrayList) {
        this.context = context;
        this.nationsArrayList = nationsArrayList;
    }

    @NonNull
    @Override
    public NationsAdapter.ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items,parent,false);
        return new NationsAdapter.ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {

        Nations currentNation = nationsArrayList.get(position);

        String nation = currentNation.getNation();
        String capital = currentNation.getCapital();
        String region = currentNation.getRegion();
        String subRegion = currentNation.getSubRegion();
        long population = currentNation.getPopulation();
        String borders = currentNation.getBorders();
        String languages = currentNation.getLanguages();

        holder.nation.setText(    "Nation     :  "+nation);
        holder.capital.setText(   "Capital    :  "+ capital);
        holder.region.setText(    "Region     :  "+ region);
        holder.subRegion.setText( "Sub Region :  "+ subRegion);
        holder.population.setText("Population :  "+ population);
        holder.borders.setText(   "Borders    :  "+ borders);
        holder.languages.setText( "Languages  :  "+ languages);

    }

      @Override
    public int getItemCount() {
        return nationsArrayList.size();
    }

    public class ItemsViewHolder extends RecyclerView.ViewHolder{

        public ImageView flag;
        public TextView nation,capital,region,subRegion,population,borders,languages;


        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            flag = itemView.findViewById(R.id.flag);
            nation = itemView.findViewById(R.id.nation);
            capital = itemView.findViewById(R.id.capital);
            region = itemView.findViewById(R.id.region);
            subRegion = itemView.findViewById(R.id.subRegion);
            population = itemView.findViewById(R.id.population);
            borders = itemView.findViewById(R.id.borders);
            languages = itemView.findViewById(R.id.languages);
        }
    }
}
