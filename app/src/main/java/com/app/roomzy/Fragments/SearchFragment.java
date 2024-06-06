package com.app.roomzy.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.roomzy.Adapter.AllCartAdapter;
import com.app.roomzy.Adapter.CartAdapter;
import com.app.roomzy.Adapter.CartAllItemAdapter;
import com.app.roomzy.Controller.FirebaseController;
import com.app.roomzy.Controller.RoomController;
import com.app.roomzy.FilterTheDataActivity;
import com.app.roomzy.MainActivity;
import com.app.roomzy.Models.Room;
import com.app.roomzy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Objects;


public class SearchFragment extends Fragment {

    View mMainView;
    EditText searchBox;
    ImageView image_filter;
    ImageButton searchBtn;
    RecyclerView searchView;
    CartAllItemAdapter cartAdapter;
    ArrayList<Room> arrayList = new ArrayList<>();
    FirebaseController firebaseController;
    RoomController roomController;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roomController = new RoomController();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        firebaseController= new FirebaseController();
        mMainView = inflater.inflate(R.layout.fragment_search, container, false);
        searchBox = (EditText) mMainView.findViewById(R.id.search);
        searchBtn = (ImageButton) mMainView.findViewById(R.id.searchBtn);
        image_filter = (ImageView) mMainView.findViewById(R.id.image_filter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        searchView = (RecyclerView) mMainView.findViewById(R.id.searchView);
        searchView.setLayoutManager(linearLayoutManager);
        cartAdapter = new CartAllItemAdapter(getActivity(),arrayList);
        searchView.setAdapter(cartAdapter);
        search("");
        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(searchBox.getText().toString());
                    return true;
                }
                return false;
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(searchBox.getText().toString());
            }
        });
        image_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Mở Thành Công!!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), FilterTheDataActivity.class);
                startActivity(intent);
            }
        });




        return mMainView;
    }
    private void search(String phrase){
        roomController.searchRoomsByName(phrase, new RoomController.OnRoomDataLoadedListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onRoomDataLoaded(ArrayList<Room> roomList) {
                arrayList.clear();
                arrayList.addAll(roomList);
                cartAdapter.notifyDataSetChanged();
            }
        });
    }
}