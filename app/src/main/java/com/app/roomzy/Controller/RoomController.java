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
        databaseReference = FirebaseDatabase.getInstance().getReference("rooms");
    }

    // Hàm lấy danh sách phòng từ Firebase
    public void getAllRooms(final OnRoomDataLoadedListener listener) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Room> roomList = new ArrayList<>();
                for (DataSnapshot roomSnapshot : dataSnapshot.getChildren()) {
                    Room room = roomSnapshot.getValue(Room.class);
                    roomList.add(room);
                }
                // Gọi callback khi dữ liệu được load thành công
                listener.onRoomDataLoaded(roomList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
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
                    Room room = roomSnapshot.getValue(Room.class);
                    if (room.getName().toLowerCase().contains(keyword.toLowerCase())) {
                        roomList.add(room);
                    }
                }
                // Gọi callback khi dữ liệu được tìm kiếm thành công
                listener.onRoomDataLoaded(roomList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu có
                Log.e("RoomController", "Failed to read value.", databaseError.toException());
            }
        });
    }

    // Interface để định nghĩa callback cho việc load dữ liệu phòng
    public interface OnRoomDataLoadedListener {
        void onRoomDataLoaded(ArrayList<Room> roomList);
    }
}
