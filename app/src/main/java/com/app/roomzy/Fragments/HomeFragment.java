package com.app.roomzy.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.roomzy.Adapter.CategoriesAdapter;
import com.app.roomzy.Controller.HistoryUpdated;
import com.app.roomzy.Models.CategoriesModel;
import com.app.roomzy.R;
import com.app.roomzy.ViewAllActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class HomeFragment extends Fragment  implements HistoryUpdated {

    LinearLayout noHistoryImage;
    View mMainView;
    Button viewAllBtn,viewlocalAllBtn;
    RecyclerView mTrendingView,mProductsAcrossView,mCategoriesView,mLocalViews,mRecentView;
    ImageView bannerImage,midbannerImage;

    NestedScrollView nestedScrollView;


    CategoriesAdapter mAdapter3;




    ArrayList<CategoriesModel>categoriesList = new ArrayList<>();


    ArrayList<String>ranks = new ArrayList<>();

    Button clearHistory;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initialize(){
        mTrendingView = (RecyclerView) mMainView.findViewById(R.id.trendingView);
        mProductsAcrossView = (RecyclerView) mMainView.findViewById(R.id.productsAcrossIndia);
        mCategoriesView = (RecyclerView) mMainView.findViewById(R.id.categoriesView);
        mRecentView = (RecyclerView) mMainView.findViewById(R.id.recentProduct);
        mLocalViews = (RecyclerView) mMainView.findViewById(R.id.suportLocals);
        bannerImage = (ImageView) mMainView.findViewById(R.id.bannerHome);
        midbannerImage = (ImageView) mMainView.findViewById(R.id.midBanner);
        clearHistory = (Button) mMainView.findViewById(R.id.clearHistory);
        noHistoryImage = (LinearLayout) mMainView.findViewById(R.id.noHistory);
        viewAllBtn = (Button) mMainView.findViewById(R.id.viewAllBtn);
        viewlocalAllBtn = (Button) mMainView.findViewById(R.id.viewlocalsBtn);
        nestedScrollView = (NestedScrollView) mMainView.findViewById(R.id.nestedScroll);


        midbannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:phamnguyenkali@gmail.com")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_SUBJECT, "here is my valueable suggestion for you.");
                if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        bannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:macrocodes7400@gmail.com")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_SUBJECT, "here is my valueable suggestion for you.");
                if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.fragment_home, container, false);
        initialize();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                addCategories();
                getAllProducts();

//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
//                mAdapter = new TrendingRecyclerAdapter(trendingList,getContext(),requireActivity().getSupportFragmentManager(),historyUpdated);
//                mTrendingView.setLayoutManager(linearLayoutManager);
//                mTrendingView.setAdapter(mAdapter);

                //products accross india adapter
//                mProductsAcrossView.setLayoutManager(new GridLayoutManager(getContext(), 2));
//                mAdapter2 = new AllProductsAdapter(allProducts,getContext(),requireActivity().getSupportFragmentManager(),historyUpdated);
//                mProductsAcrossView.setAdapter(mAdapter2);



                //categories adapter
                LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
                mCategoriesView.setLayoutManager(linearLayoutManager2);
                mAdapter3 = new CategoriesAdapter(categoriesList,getContext());
                mCategoriesView.setAdapter(mAdapter3);


                //support locals adapter
//                mAdapter4 = new AllProductsAdapter(locals,getContext(),requireActivity().getSupportFragmentManager(),historyUpdated);
//                mLocalViews.setLayoutManager(new GridLayoutManager(getContext(), 2));
//                mLocalViews.setAdapter(mAdapter4);
//
//                //Recent Product History
//                productHistory = new ProductHistory(getContext());
//                recentHistory = productHistory.getAllData();
//                DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
//
//                mRecentAdapter = new RecentHistoryAdapter(recentHistory,databaseHelper,getContext(),getFragmentManager());
//                LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(getContext());
//                mRecentView.setLayoutManager(linearLayoutManager4);
//                mRecentView.setAdapter(mRecentAdapter);

            }
        },1000);
        //trending


//        if (recentHistory.size() == 0){
//            noHistoryImage.setVisibility(View.VISIBLE);
//        }else{
//            noHistoryImage.setVisibility(View.GONE);
//        }

        clearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        viewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewAllActivity.class);
                intent.putExtra("type","all");
                startActivity(intent);
            }
        });
        viewlocalAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), ViewAllActivity.class);
//                intent.putParcelableArrayListExtra("list",locals);
//                intent.putExtra("type","local");
//                startActivity(intent);
            }
        });


        return mMainView;
    }
//    private void getBanner(){
////        Picasso.get().load(R.drawable.poster1)
////                .placeholder(R.drawable.img3)
////                .into(bannerImage);
////        CustomDatabase customDatabase = new CustomDatabase() ;
////        CollectionReference products  = customDatabase.getSettings().collection("banner");
////        products.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
////            @Override
////            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
////                for (QueryDocumentSnapshot datasnapshot : queryDocumentSnapshots){
////                    BannerModel bannerModel = datasnapshot.toObject(BannerModel.class);
////                    banners.add(bannerModel);
////
////
////
////                }
////            }
////        });
//
//        if (banners.size() == 1){
//
//
////            String nav = banners.get(0).getNavigate();
////            bannerImage.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    if (nav.equalsIgnoreCase("inApp")){
////                        //Query for searching product with id
////                    }else{
////                        //Search in web
////                    }
////                }
////            });
//        }
//    }
    private void getLocalProducts(){
//        CustomDatabase customDatabase = new CustomDatabase() ;
//        CollectionReference products  = customDatabase.getSettings().collection("locals");
//        products.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot snapshots) {
//                for (QueryDocumentSnapshot datasnapshot : snapshots){
//                    TrendingProducts trendingProducts1 = datasnapshot.toObject(TrendingProducts.class);
//                    locals.add(trendingProducts1);
//                    mAdapter4.notifyDataSetChanged();
//
//                }
//
//            }
//        });
    }
    private void getAllProducts(){
//        allProducts.clear();
//        CustomDatabase customDatabase = new CustomDatabase() ;
//        CollectionReference products  = customDatabase.getSettings().collection("Trending");
//        products.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot snapshots) {
//                for (QueryDocumentSnapshot datasnapshot : snapshots){
//                    TrendingProducts trendingProducts1 = datasnapshot.toObject(TrendingProducts.class);
//                    allProducts.add(trendingProducts1);
//                    mAdapter2.notifyDataSetChanged();
//
//                }
//
//            }
//        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                getLocalProducts();
//            }
//        });
    }
    private void addCategories(){
        categoriesList.clear();
        categoriesList.add( new CategoriesModel("https://statics.vinpearl.com/is-ho-chi-minh-city-safe-01_1689422733.jpg","Hồ Chí Minh"));
        categoriesList.add( new CategoriesModel("https://vcdn1-dulich.vnecdn.net/2022/05/11/hoan-kiem-lake-7673-1613972680-1508-1652253984.jpg?w=0&h=0&q=100&dpr=1&fit=crop&s=2wB1cBTUcNKuk68nrG6LMQ","Hà Nội"));
        categoriesList.add( new CategoriesModel("https://bcp.cdnchinhphu.vn/344443456812359680/2022/12/27/nhattrang3-16721128389061596602579.jpg","Nha Trang"));
        categoriesList.add( new CategoriesModel("https://vcdn1-dulich.vnecdn.net/2022/06/01/CauVangDaNang-1654082224-7229-1654082320.jpg?w=0&h=0&q=100&dpr=2&fit=crop&s=MeVMb72UZA27ivcyB3s7Kg","Đà Nẵng"));
    }


    @Override
    public void getUpdateResult(boolean isUpdated) {
        if (isUpdated){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                }
            },1000);
        }
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}