package com.app.roomzy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.roomzy.Adapter.VoucherAdapter;
import com.app.roomzy.Models.Voucher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BookingActivity extends AppCompatActivity {
    TextView txtCheckin, txtCheckout, txtSoNgay;
    Button btncheckInDate,btncheckOutDate;
    private Calendar checkInDate;
    private Calendar checkOutDate;
    private Spinner spinnerVouchers;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private VoucherAdapter adapter;
    private ArrayList<Voucher> voucherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        addControl();
        addEvent();
        voucherList = new ArrayList<>();
        adapter = new VoucherAdapter(this, R.layout.layout_item_voucher, voucherList);
        spinnerVouchers.setAdapter(adapter);
        setupSpinner();
    }
    void addControl()
    {
        txtCheckin = (TextView) findViewById(R.id.txtCheckin);
        txtCheckout = (TextView) findViewById(R.id.txtCheckout);
        btncheckInDate = (Button) findViewById(R.id.checkInDate);
        btncheckOutDate = (Button) findViewById(R.id.checkOutDate);
        txtSoNgay = (TextView) findViewById(R.id.txtSoNgay);
        spinnerVouchers = (Spinner) findViewById(R.id.spinnerVoucher);

    }
    void addEvent()
    {
        btncheckInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(true);
            }
        });
        btncheckOutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(false);
            }
        });
    }
    private void showDatePickerDialog(final boolean isCheckIn) {
        // Lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                BookingActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, monthOfYear, dayOfMonth);

                        if (isCheckIn) {
                            if (selectedDate.before(calendar)) {
                                Toast.makeText(BookingActivity.this, "Ngày check-in không được nhỏ hơn ngày hiện tại", Toast.LENGTH_SHORT).show();
                            } else {
                                checkInDate = selectedDate;
                                String selectedDateString = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                txtCheckin.setText(selectedDateString);
                            }
                        } else {
                            if (checkInDate == null) {
                                Toast.makeText(BookingActivity.this, "Vui lòng chọn ngày check-in trước", Toast.LENGTH_SHORT).show();
                            } else if (!selectedDate.after(checkInDate) || daysBetween(checkInDate, selectedDate) < 1) {
                                Toast.makeText(BookingActivity.this, "Ngày check-out phải lớn hơn ngày check-in ít nhất 1 ngày", Toast.LENGTH_SHORT).show();
                            } else {
                                checkOutDate = selectedDate;
                                String selectedDateString = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                txtCheckout.setText(selectedDateString);
                                updateTotalDays();
                            }
                        }
                    }
                },
                year, month, day
        );

        datePickerDialog.show();
    }
    private long daysBetween(Calendar startDate, Calendar endDate) {
        long startTime = startDate.getTimeInMillis();
        long endTime = endDate.getTimeInMillis();
        return (endTime - startTime) / (1000 * 60 * 60 * 24);
    }
    private void updateTotalDays() {
        if (checkInDate != null && checkOutDate != null) {
            long totalDays = daysBetween(checkInDate, checkOutDate);
            txtSoNgay.setText("" + totalDays);
        }
    }
    //=====xu ly spinner
    private void setupSpinner() {
//        voucherList = new ArrayList<>();
//
//        database = FirebaseDatabase.getInstance();
//        myRef = database.getReference("Voucher");
//        adapter = new VoucherAdapter(this, R.layout.layout_item_voucher, voucherList);
//        spinnerVouchers.setAdapter(adapter);
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                voucherList.clear();
//                for (DataSnapshot voucherSnapshot : snapshot.getChildren()) {
//                    Voucher voucher = voucherSnapshot.getValue(Voucher.class);
//                    if (voucher != null) {
//                        voucherList.add(voucher);
//                    }
//                }
//                //adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(BookingActivity.this, "Failed to read data", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

        FirebaseDatabase.getInstance().getReference("Voucher").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //voucherList.clear();
                for (DataSnapshot voucherSnapshot : snapshot.getChildren()) {
                    Voucher voucher = voucherSnapshot.getValue(Voucher.class);
                    if (voucher != null) {
                        voucherList.add(voucher);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }
}