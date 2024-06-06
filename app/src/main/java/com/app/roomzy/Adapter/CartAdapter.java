package com.app.roomzy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.roomzy.BuildConfig;
import com.app.roomzy.Controller.CurrencyFormatter;
import com.app.roomzy.Fragments.ProductDetailFragment;
import com.app.roomzy.Models.Room;
import com.app.roomzy.R;
import com.app.roomzy.ViewAllActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholder> {
    ArrayList<Room> products = new ArrayList<>();
    Context context;
    Context mContext;
    FragmentManager fragmentManager;

    public CartAdapter(ViewAllActivity viewAllProductsActivity, ArrayList<Room> products) {
        context = viewAllProductsActivity;
        this.products = products;

    }

    public CartAdapter(FragmentActivity activity, ArrayList<Room> arrayList) {
        this.context = activity;
        this.products = arrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout,parent,false);


        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Room model = products.get(position);
        holder.productName.setText(model.getName());
        holder.productPrice.setText(CurrencyFormatter.formatVietnameseCurrency(model.getPrice()));
        holder.productDescription.setText(model.getAddress());
        Picasso.get().load(model.getImageURL())
                .into(holder.productImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ProductDetailFragment bottomSheet = new ProductDetailFragment(mContext, model);
//                bottomSheet.show(fragmentManager, "ModalBottomSheet");
                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                ProductDetailFragment bottomSheet = new ProductDetailFragment(context, model);
                bottomSheet.show(manager, "ModalBottomSheet");
            }
        });
        holder.productShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Download the RoomZy App from the google play store in order to view the product : https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                context.startActivity(sendIntent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{

        TextView productName,productPrice,productDescription;
        ImageView productImage,productShare;
        public Viewholder(@NonNull  View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.pName);
            productDescription = (TextView) itemView.findViewById(R.id.pDesc);
            productPrice = (TextView) itemView.findViewById(R.id.pPrice);
            productImage = (ImageView) itemView.findViewById(R.id.pImage);
            productShare = (ImageView) itemView.findViewById(R.id.shareBtn);
        }
    }
}

