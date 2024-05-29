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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.roomzy.Adapter.AllCartAdapter;
import com.app.roomzy.Adapter.CategoriesAdapter;
import com.app.roomzy.Adapter.TrendingRecyclerAdapter;
import com.app.roomzy.Controller.HistoryUpdated;
import com.app.roomzy.Controller.RoomController;
import com.app.roomzy.Models.CategoriesModel;
import com.app.roomzy.Models.Room;
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



    ArrayList<Room> trendingList = new ArrayList<>();
    ArrayList<CategoriesModel>categoriesList = new ArrayList<>();
    ArrayList<Room>topVNList = new ArrayList<>();
    ArrayList<Room>toplocationList = new ArrayList<>();



    ArrayList<String>ranks = new ArrayList<>();

    Button clearHistory;
    private RoomController roomController;
    public static HistoryUpdated historyUpdated;


    TrendingRecyclerAdapter trendingRecyclerAdapter;
    AllCartAdapter topVNRecyclerAdapter;
    AllCartAdapter topLocationAdapter;


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
        historyUpdated = (HistoryUpdated) this;


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
                intent.setData(Uri.parse("mailto:phamnguyenkali@gmail.com")); // only email apps should handle this
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
        roomController = new RoomController();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                addCategories();
                getLoCationList();
                getTopVnList();
                getTrending();

                Log.e("PhamNguyen", trendingList.toString());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
                trendingRecyclerAdapter = new TrendingRecyclerAdapter(trendingList,getContext(),requireActivity().getSupportFragmentManager(),historyUpdated);
                mTrendingView.setLayoutManager(linearLayoutManager);
                mTrendingView.setAdapter(trendingRecyclerAdapter);


                //categories adapter
                LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
                mCategoriesView.setLayoutManager(linearLayoutManager2);
                mAdapter3 = new CategoriesAdapter(categoriesList,getContext());
                mCategoriesView.setAdapter(mAdapter3);

                mProductsAcrossView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                topVNRecyclerAdapter = new AllCartAdapter(topVNList,getContext(),requireActivity().getSupportFragmentManager(),historyUpdated);
                mProductsAcrossView.setAdapter(topVNRecyclerAdapter);


                topLocationAdapter = new AllCartAdapter(toplocationList,getContext(),requireActivity().getSupportFragmentManager(),historyUpdated);
                mLocalViews.setLayoutManager(new GridLayoutManager(getContext(), 3));
                mLocalViews.setAdapter(topLocationAdapter);

            }
        },1000);
        //trending




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

    private void getTrending(){
        roomController.getAllRooms(new RoomController.OnRoomDataLoadedListener() {
            @Override
            public void onRoomDataLoaded(ArrayList<Room> roomList) {
                trendingList.clear();
                trendingList.addAll(roomList);
                Log.e("PhamNguyen", trendingList.toString());
                trendingRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }
    private void getLoCationList(){
        roomController.getAllRooms(new RoomController.OnRoomDataLoadedListener() {
            @Override
            public void onRoomDataLoaded(ArrayList<Room> roomList) {
                toplocationList.clear();
                toplocationList.addAll(roomList);
                Log.e("PhamNguyen", toplocationList.toString());
                topLocationAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getTopVnList(){
        roomController.getAllRooms(new RoomController.OnRoomDataLoadedListener() {
            @Override
            public void onRoomDataLoaded(ArrayList<Room> roomList) {
                topVNList.clear();
                topVNList.addAll(roomList);
                Log.e("PhamNguyen", topVNList.toString());
                topVNRecyclerAdapter.notifyDataSetChanged();
            }
        });
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