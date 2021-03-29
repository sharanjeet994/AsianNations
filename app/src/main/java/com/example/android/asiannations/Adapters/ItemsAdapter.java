package com.example.android.asiannations.Adapters;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.example.android.asiannations.Items;
import com.example.android.asiannations.R;
import com.example.android.asiannations.room.Database;
import com.example.android.asiannations.room.Nations;
import com.example.android.asiannations.svg.SvgSoftwareLayerSetter;

import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;


public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {

    private Context context;
    private ArrayList<Items> itemsArrayList;
    private RequestBuilder<PictureDrawable> requestBuilder;


    public ItemsAdapter(Context context, ArrayList<Items> itemsArrayList) {
        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items,parent,false);
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {

        Items currentItem = itemsArrayList.get(position);

        String flag = currentItem.getFlag();
        String nation = currentItem.getNation();
        String capital = currentItem.getCapital();
        String region = currentItem.getRegion();
        String subRegion = currentItem.getSubRegion();
        long population = currentItem.getPopulation();
        String borders = currentItem.getBorders();
        String languages = currentItem.getLanguages();

        Nations nations = new Nations(nation,capital,region,subRegion,population,borders,languages);

        Database database = Room.databaseBuilder(context,Database.class,"NationsDb").allowMainThreadQueries().build();
        database.dao().insertNation(nations);

        requestBuilder =
                Glide.with(context)
                        .as(PictureDrawable.class)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .fitCenter()
                        .error(R.drawable.ic_launcher_foreground)
                        .transition(withCrossFade())
                        .listener(new SvgSoftwareLayerSetter());
        Uri uri = Uri.parse(flag);
        requestBuilder.load(uri).into(holder.flag);


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
        return itemsArrayList.size();
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
