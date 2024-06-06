package com.app.roomzy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.app.roomzy.Adapter.AllCartAdapter;
import com.app.roomzy.Adapter.CartAllItemAdapter;
import com.app.roomzy.Adapter.CartColumnAdapter;
import com.app.roomzy.Controller.RoomController;
import com.app.roomzy.Models.Room;
import com.app.roomzy.databinding.ActivityViewAllBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Objects;

public class ViewAllActivity extends AppCompatActivity {

    String type;
    int limit =10;
//    DocumentSnapshot lastVisible;
    ActivityViewAllBinding binding;
    private boolean isScrolling = false;
    ArrayList<Room> products = new ArrayList<>();
    ArrayList<Room> locals = new ArrayList<>();
    ArrayList<Room> categoryList = new ArrayList<>();
    private boolean isLastItemReached = false;
//    View productAdapter;
//    ViewAllProductGridADapter gridADapter;
    CartColumnAdapter cartColumnAdapter;
    CartAllItemAdapter cartAllItemAdapter;
    RoomController roomController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        roomController = new RoomController();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_view_all);

        type = getIntent().getStringExtra("type");
        if (type.equalsIgnoreCase("all")){

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            binding.allProductsView.setLayoutManager(linearLayoutManager);
//            getLocalsProducts();
            getCategoryProducts("all");
            cartColumnAdapter = new CartColumnAdapter(this,products);
            binding.allProductsView.setAdapter(cartColumnAdapter);
            binding.allProductsView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
            binding.title.setText("Handmade with ❤");

        }else if (type.equalsIgnoreCase("local")){
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
            binding.allProductsView.setLayoutManager(gridLayoutManager);
//            locals = getIntent().getParcelableArrayListExtra("list");
            cartAllItemAdapter = new CartAllItemAdapter(this,locals);
            binding.allProductsView.setAdapter(cartAllItemAdapter);

            binding.title.setText("Support the locals ❤");

        }
        else{
            String category = getIntent().getStringExtra("category");
            getCategoryProducts(category);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
            binding.allProductsView.setLayoutManager(gridLayoutManager);
            cartAllItemAdapter = new CartAllItemAdapter(this,locals);
            binding.allProductsView.setAdapter(cartAllItemAdapter);
            binding.title.setText(category+" ❤");
        }
//            getLocalsProducts();
        getCategoryProducts("all");
        cartColumnAdapter = new CartColumnAdapter(this,products);
        binding.allProductsView.setAdapter(cartColumnAdapter);
        binding.allProductsView.setLayoutManager(new GridLayoutManager(this, 3));
        binding.title.setText("Tất cả");
    }
    private void getCategoryProducts(String category) {
        roomController.getAllRooms(new RoomController.OnRoomDataLoadedListener() {
            @Override
            public void onRoomDataLoaded(ArrayList<Room> roomList) {
                products.clear();
                products.addAll(roomList);
                Log.e("PhamNguyen", categoryList.toString());
                cartColumnAdapter.notifyDataSetChanged();
            }
        });

    }
}