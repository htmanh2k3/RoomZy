package com.app.roomzy.Controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.app.roomzy.Models.ConFigManager;
import com.app.roomzy.Models.Room;
import com.app.roomzy.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RoomController {
    private final DatabaseReference databaseReference;

    public RoomController() {
        databaseReference = FirebaseDatabase.getInstance(ConFigManager.getFirebaseUrl()).getReference("Room");
    }



        public void getAllRooms(final OnRoomDataLoadedListener listener) {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<Room> roomList = new ArrayList<>();
                    for (DataSnapshot roomSnapshot : dataSnapshot.getChildren()) {
                        Room room = parseRoomSnapshot(roomSnapshot);
                        if (room != null) {
                            roomList.add(room);
                        }
                    }
                    listener.onRoomDataLoaded(roomList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("RoomController", "Failed to read value.", databaseError.toException());
                }
            });
        }

        public void searchRoomsByName(final String keyword, final OnRoomDataLoadedListener listener) {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<Room> roomList = new ArrayList<>();
                    for (DataSnapshot roomSnapshot : dataSnapshot.getChildren()) {
                        Room room = parseRoomSnapshot(roomSnapshot);
                        if (room != null && (room.getName().toLowerCase().contains(keyword.toLowerCase()) || room.getAddress().toLowerCase().contains(keyword.toLowerCase()))) {
                            roomList.add(room);
                        }
                    }
                    listener.onRoomDataLoaded(roomList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("RoomController", "Failed to read value.", databaseError.toException());
                }
            });
        }

        public void searchRoomsByFilters(final String keyword, final String category, final String location, final String minPrice, final String maxPrice, final OnRoomDataLoadedListener listener) {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<Room> roomList = new ArrayList<>();
                    int min = minPrice.isEmpty() ? Integer.MIN_VALUE : Integer.parseInt(minPrice);
                    int max = maxPrice.isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(maxPrice);

                    for (DataSnapshot roomSnapshot : dataSnapshot.getChildren()) {
                        Room room = parseRoomSnapshot(roomSnapshot);
                        if (room != null &&
                                (keyword.isEmpty() || room.getName().toLowerCase().contains(keyword.toLowerCase())
                                        || room.getAddress().toLowerCase().contains(keyword.toLowerCase())) &&
                                (category.equals("All") || room.getCategoriesId().equals(category)) &&
                                (location.equals("All") || room.getLocationId().equals(location)) &&
                                (room.getPrice() >= min && room.getPrice() <= max)) {
                            roomList.add(room);
                        }
                    }
                    listener.onRoomDataLoaded(roomList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("RoomController", "Failed to read value.", databaseError.toException());
                }
            });
        }

        private Room parseRoomSnapshot(DataSnapshot roomSnapshot) {
            try {
                String id = roomSnapshot.child("Id").getValue(String.class);
                String name = roomSnapshot.child("Name").getValue(String.class);
                String address = roomSnapshot.child("Address").getValue(String.class);
                String imageUrl = roomSnapshot.child("Image").getValue(String.class);
                String type = roomSnapshot.child("Type").getValue(String.class);
                int price = roomSnapshot.child("Price").getValue(Integer.class);
                int rate = roomSnapshot.child("Rate").getValue(Integer.class);
                ArrayList<String> subImages = (ArrayList<String>) roomSnapshot.child("SubImage").getValue();
                ArrayList<String> subRooms = (ArrayList<String>) roomSnapshot.child("SubRoom").getValue();
                String description = roomSnapshot.child("description").getValue(String.class);
                String locationId = roomSnapshot.child("LocationId").getValue(String.class);
                String categoriesId = roomSnapshot.child("categoriesId").getValue(String.class);

                return new Room(address, id, imageUrl, name, price, rate, type, subImages, subRooms, description, locationId, categoriesId);
            } catch (Exception e) {
                Log.e("RoomController", "Error parsing room data", e);
                return null;
            }
        }

        public interface OnRoomDataLoadedListener {
            void onRoomDataLoaded(ArrayList<Room> roomList);
        }
}
