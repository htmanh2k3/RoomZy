package com.app.roomzy.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.roomzy.Controller.CurrencyFormatter;
import com.app.roomzy.Controller.HistoryUpdated;
import com.app.roomzy.ImageViewActivity;
import com.app.roomzy.MainActivity;
import com.app.roomzy.Models.Room;
import com.app.roomzy.R;
import com.app.roomzy.databinding.FragmentProductDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class ProductDetailFragment extends BottomSheetDialogFragment {
    private Context mContext;
    private Room trendingProducts;
    private ArrayList<Room> savedProducts = new ArrayList<>();
    private static final int REQUEST_CALL_PERMISSION = 1;
    ImageView imageView,imageView2;
    ImageView like;
    Button buyBtn, callBtn;
//    DatabaseHelper databaseHelper;
//    ProductHistory productHistory;
    RelativeLayout parent;
    Button report;
    int click =0 ;
    CardView imageRoom1;
    FirebaseUser mAuth;

    HistoryUpdated historyUpdated;
    TextView productName,productPrice,productDesc;
    public ProductDetailFragment(Context mContext, Room trendingProducts, HistoryUpdated historyUpdated) {
        this.mContext=mContext;
        this.trendingProducts = trendingProducts;
        this.historyUpdated=historyUpdated;
    }

    public ProductDetailFragment(Context mContext, Room trendingProducts) {
        this.mContext=mContext;
        this.trendingProducts = trendingProducts;
        this.historyUpdated=null;
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ProductDetailFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ProductDetailFragment newInstance(String param1, String param2) {
//        ProductDetailFragment fragment = new ProductDetailFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail,container);

        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        imageView = view.findViewById(R.id.mainImage);
        imageView2 = view.findViewById(R.id.img1);
        productName = (TextView) view.findViewById(R.id.pName);
        productPrice = (TextView) view.findViewById(R.id.pPrice);
        parent = (RelativeLayout) view.findViewById(R.id.parent);
        productDesc = (TextView) view.findViewById(R.id.pDesc);
        like = (ImageView) view.findViewById(R.id.like);
        report = (Button) view.findViewById(R.id.productReport);
        buyBtn = (Button) view.findViewById(R.id.buyBtn);
        imageRoom1 = (CardView) view.findViewById(R.id.amazonIcon);
        callBtn = (Button) view.findViewById(R.id.buyCall);

//        databaseHelper = new DatabaseHelper(getContext());
//        productHistory = new ProductHistory(getContext());

        ArrayList<Room> arrayList = new ArrayList<>();
//        arrayList = databaseHelper.getAllData();
        for (Room trendingProducts1 : arrayList){
            if (trendingProducts1.getId().equalsIgnoreCase(trendingProducts.getId())){
                like.setImageResource(R.drawable.heart_filled2);
                ++click;
            }
        }

        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        imageRoom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ImageViewActivity.class);
                intent.putExtra("uri",trendingProducts.getImageURL());
                startActivity(intent);
            }
        });
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();

            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click ==0 ){
                    like.setImageResource(R.drawable.heart_filled2);
                    ++click;
//                    if(databaseHelper.addText(trendingProducts))
                        Toast.makeText(mContext, "Added to cart", Toast.LENGTH_SHORT).show();

                }else{
                    like.setImageResource(R.drawable.heart2);
                    --click;
//                    if(databaseHelper.deleteRow(trendingProducts.getId()))
                        Toast.makeText(mContext, "Removed from cart", Toast.LENGTH_SHORT).show();


                }

            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), ReportActivity.class);
//                intent.putExtra("product", trendingProducts);
//                startActivity(intent);
            }
        });

        reported();
        addInHistory();

        productName.setText(trendingProducts.getName());
        productPrice.setText(CurrencyFormatter.formatVietnameseCurrency(trendingProducts.getPrice()));
        productDesc.setText(trendingProducts.getAddress());

        Picasso.get().load(trendingProducts.getImageURL()).into(imageView);
        Picasso.get().load(trendingProducts.getImageURL()).into(imageView2);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ImageViewActivity.class);
                intent.putExtra("uri",trendingProducts.getImageURL());
                startActivity(intent);

            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ImageViewActivity.class);
                intent.putExtra("uri",trendingProducts.getImageURL());
                startActivity(intent);
            }
        });

        return view;
    }
    public void reported(){

    }

    public void addInHistory(){
        if (historyUpdated!=null){

//            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            }
        }
    }

    private void makePhoneCall() {
        // call
//        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
//        } else {
//            String dial = "tel:" + "0366216586";
//            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
//        }
        Intent intent = new Intent(Intent.ACTION_DIAL);
        String phoneNumber = "0366216586";
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }
}