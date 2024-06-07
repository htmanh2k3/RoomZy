package com.app.roomzy.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.roomzy.Models.CategoriesModel;
import com.app.roomzy.R;
import com.app.roomzy.ViewAllActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.Viewholder> {
    ArrayList<CategoriesModel> categoriesList = new ArrayList<>();
    Context mContext;
    int mLayout;
    public CategoriesAdapter(ArrayList<CategoriesModel> categoriesList, Context context) {
        this.categoriesList=categoriesList;
        this.mContext=context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_categories,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  CategoriesAdapter.Viewholder holder, int position) {
        CategoriesModel model = categoriesList.get(position);
        holder.categoryName.setText(model.getName());
        Picasso.get().load(model.getImage())
                .into(holder.categoryImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getName().equalsIgnoreCase("jewellery")){

                    Intent intent = new Intent(mContext, ViewAllActivity.class);
                    intent.putExtra("type","category");
                    intent.putExtra("category","jewellery");
                    mContext.startActivity(intent);

                }else if(model.getName().equalsIgnoreCase("Home decor")){
                    Intent intent = new Intent(mContext, ViewAllActivity.class);
                    intent.putExtra("type","category");
                    intent.putExtra("category","decor");
                    mContext.startActivity(intent);
                }else if(model.getName().equalsIgnoreCase("Ayurvedic")){
                    Intent intent = new Intent(mContext, ViewAllActivity.class);
                    intent.putExtra("type","category");
                    intent.putExtra("category","ayurvedic");
                    mContext.startActivity(intent);
                }else if(model.getName().equalsIgnoreCase("Furniture")){
                    Intent intent = new Intent(mContext, ViewAllActivity.class);
                    intent.putExtra("type","category");
                    intent.putExtra("category","decor");
                    mContext.startActivity(intent);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{

        public TextView categoryName;
        public CircleImageView categoryImage;
        public Viewholder(@NonNull  View itemView) {
            super(itemView);
            categoryName = (TextView) itemView.findViewById(R.id.categoryName);
            categoryImage = (CircleImageView) itemView.findViewById(R.id.categoryImage);
        }
    }
}
