package com.app.roomzy.Controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.app.roomzy.Models.Room;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RoomController {
    private DatabaseReference databaseReference;

    public RoomController() {
        databaseReference = FirebaseDatabase.getInstance("https://roomzy-cbeb4-default-rtdb.firebaseio.com").getReference("Room");
    }

    public void getAllRooms(final OnRoomDataLoadedListener listener) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Room> roomList = new ArrayList<>();
                for (DataSnapshot roomSnapshot : dataSnapshot.getChildren()) {
                    String id = roomSnapshot.child("Id").getValue(String.class);
                    String name = roomSnapshot.child("Name").getValue(String.class);
                    String address = roomSnapshot.child("Address").getValue(String.class);
                    String imageUrl = roomSnapshot.child("Image").getValue(String.class);
                    String type = roomSnapshot.child("Type").getValue(String.class);
                    int price = roomSnapshot.child("Price").getValue(Integer.class);
                    int rate = roomSnapshot.child("Rate").getValue(Integer.class);

                    Room room = new Room(address,id, imageUrl,name, price, rate, type);
                    roomList.add(room);
                }
                listener.onRoomDataLoaded(roomList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("RoomController", "Failed to read value.", databaseError.toException());
            }
        });
    }

    // Hàm tìm kiếm phòng theo tên
    public void searchRoomsByName(final String keyword, final OnRoomDataLoadedListener listener) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Room> roomList = new ArrayList<>();
                for (DataSnapshot roomSnapshot : dataSnapshot.getChildren()) {
                    String id = roomSnapshot.child("Id").getValue(String.class);
                    String name = roomSnapshot.child("Name").getValue(String.class);
                    String address = roomSnapshot.child("Address").getValue(String.class);
                    String imageUrl = roomSnapshot.child("Image").getValue(String.class);
                    String type = roomSnapshot.child("Type").getValue(String.class);
                    int price = roomSnapshot.child("Price").getValue(Integer.class);
                    int rate = roomSnapshot.child("Rate").getValue(Integer.class);

                    Room room = new Room(address,id, imageUrl,name, price, rate, type);
                    if(room.getName().toLowerCase().contains(keyword.toLowerCase()))
                        roomList.add(room);
                }
                listener.onRoomDataLoaded(roomList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("RoomController", "Failed to read value.", databaseError.toException());
            }
        });
    }


    public interface OnRoomDataLoadedListener {
        void onRoomDataLoaded(ArrayList<Room> roomList);
    }
}
