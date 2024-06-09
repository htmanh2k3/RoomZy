package com.app.roomzy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.app.roomzy.Adapter.VoucherAdapter;
import com.app.roomzy.Controller.ApiClient;
import com.app.roomzy.Controller.ApiService;
import com.app.roomzy.Controller.CurrencyFormatter;
import com.app.roomzy.Models.BookingRequest;
import com.app.roomzy.Models.BookingResponse;
import com.app.roomzy.Models.ConnectionResponse;
import com.app.roomzy.Models.Room;
import com.app.roomzy.Models.Voucher;
import com.app.roomzy.Models.VoucherResponse;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity {
    TextView txtCheckin, txtCheckout, txtSoNgay, roomPrice, hotelName, txtTongTien, txtGiamGia;
    Button btncheckInDate,btncheckOutDate, btnBookingNow;
    private Calendar checkInDate;
    private Calendar checkOutDate;
    private Spinner spinnerVouchers;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private VoucherAdapter adapter;
    private Voucher selectedVoucher;

    private ArrayList<Voucher> voucherList;

    private Button btnLienHe;
    Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        addControl();
        addEvent();
        voucherList = new ArrayList<>();
        Intent intent = getIntent();
        Room room = intent.getParcelableExtra("room_key");

        if (room != null) {
            this.room = room;
        }
        txtTongTien.setText(" 0đ");
        txtSoNgay.setText("" +  "0 Ngày");
        roomPrice.setText(CurrencyFormatter.formatVietnameseCurrency(room.getPrice()));
        hotelName.setText(room.getName());

        checkInDate = Calendar.getInstance();
        String todayString = checkInDate.get(Calendar.DAY_OF_MONTH) + "/" + (checkInDate.get(Calendar.MONTH) + 1) + "/" + checkInDate.get(Calendar.YEAR);
        txtCheckin.setText(todayString);
        fetchVouchers();

//        checkApiConnection();
    }
    void addControl()
    {
        txtCheckin = (TextView) findViewById(R.id.txtCheckin);
        txtCheckout = (TextView) findViewById(R.id.txtCheckout);
        btncheckInDate = (Button) findViewById(R.id.checkInDate);
        btncheckOutDate = (Button) findViewById(R.id.checkOutDate);
        txtSoNgay = (TextView) findViewById(R.id.txtSoNgay);
        btnLienHe = (Button) findViewById(R.id.contactButton);
        roomPrice = (TextView) findViewById(R.id.roomPrice);
        hotelName = (TextView) findViewById(R.id.hotelName);
        txtTongTien = (TextView) findViewById(R.id.txtTotalPayment);
        txtGiamGia = (TextView) findViewById(R.id.txtGiamGia);
        btnBookingNow = (Button) findViewById(R.id.bookNowButton);
        findViewById(R.id.selectVoucherButton).setOnClickListener(v -> showVoucherSelectionDialog());
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
        btnLienHe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                String phoneNumber = room.getSdt();
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });
        btnBookingNow.setOnClickListener(view -> bookRoom());
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

    private long applyVoucherDiscount(long totalPrice, Voucher voucher) {
        String discountString = voucher.getGiamGia().replace("%", "").trim();
        double discount = 0;
        try {
            discount = Double.parseDouble(discountString) / 100.0;
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid discount format", Toast.LENGTH_SHORT).show();
        }

        long discountedAmount = (long) (totalPrice * discount);
        if (discountedAmount > voucher.getGiaToiDa()) {
            discountedAmount = (long) voucher.getGiaToiDa();
        }
        txtGiamGia.setText("- "+ CurrencyFormatter.formatVietnameseCurrency((int) discountedAmount));
        return Math.max(0, totalPrice - discountedAmount);
    }
    private void updateTotalDays() {
        if (checkInDate != null && checkOutDate != null) {
            long totalDays = daysBetween(checkInDate, checkOutDate);
            txtSoNgay.setText(totalDays + " Ngày");
            long totalPrice = room.getPrice() * totalDays;
            if (selectedVoucher != null) {
                totalPrice = applyVoucherDiscount(totalPrice, selectedVoucher);
            }
            txtTongTien.setText(CurrencyFormatter.formatVietnameseCurrency((int) totalPrice));
        }
    }
    private void checkApiConnection() {
        ApiService apiService = ApiClient.getApiService();
        Call<ConnectionResponse> call = apiService.checkConnection();

        call.enqueue(new Callback<ConnectionResponse>() {
            @Override
            public void onResponse(Call<ConnectionResponse> call, Response<ConnectionResponse> response) {
                if (response.isSuccessful()) {
                    ConnectionResponse connectionResponse = response.body();
                    String message = connectionResponse != null ? connectionResponse.getMessage() : "No message";
                    Toast.makeText(BookingActivity.this, "Connection successful: " + message, Toast.LENGTH_SHORT).show();
                    Log.d("Pham Nguyen Booking", "Connection successful: " + message);
                } else {
                    Toast.makeText(BookingActivity.this, "Connection failed: " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.e("Pham Nguyen Booking", "Connection failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ConnectionResponse> call, Throwable t) {
                Toast.makeText(BookingActivity.this, "Connection failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("PhamNguyen Booking", "Connection failed", t);
            }
        });
    }

    private void fetchVouchers() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<VoucherResponse> call = apiService.getUserVouchers("3XsN35waUESo9XEkNBAKcwR1DGI2");

        call.enqueue(new Callback<VoucherResponse>() {
            @Override
            public void onResponse(@NonNull Call<VoucherResponse> call, @NonNull Response<VoucherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    voucherList.addAll(response.body().getVouchers());
                } else {
                    Toast.makeText(BookingActivity.this, "Failed to fetch vouchers", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<VoucherResponse> call, @NonNull Throwable t) {
                Toast.makeText(BookingActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showVoucherSelectionDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.bottom_sheet_voucher, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        RecyclerView recyclerViewVouchers = bottomSheetView.findViewById(R.id.recyclerViewVouchers);
        recyclerViewVouchers.setLayoutManager(new LinearLayoutManager(this));
        VoucherAdapter adapter = new VoucherAdapter(voucherList, voucher -> {
            selectedVoucher = voucher;
            updateSelectedVoucherUI();
            bottomSheetDialog.dismiss();
        });
        recyclerViewVouchers.setAdapter(adapter);

        bottomSheetDialog.show();
    }

    private void updateSelectedVoucherUI() {
        // Update the UI with the selected voucher details
        TextView voucherTextView = findViewById(R.id.selectedVoucherTextView);
        if (selectedVoucher != null) {
            voucherTextView.setText(selectedVoucher.getTenVC() + " - " + selectedVoucher.getGiamGia());
            updateTotalDays();
        }
    }

    private void bookRoom() {
        if (checkInDate == null || checkOutDate == null) {
            Toast.makeText(this, "Vui lòng chọn ngày check-in và check-out", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        long totalPrice = Long.parseLong(txtTongTien.getText().toString().replaceAll("[^\\d]", ""));
        String checkInDateStr = checkInDate.get(Calendar.YEAR) + "-" + (checkInDate.get(Calendar.MONTH) + 1) + "-" + checkInDate.get(Calendar.DAY_OF_MONTH);
        String checkOutDateStr = checkOutDate.get(Calendar.YEAR) + "-" + (checkOutDate.get(Calendar.MONTH) + 1) + "-" + checkOutDate.get(Calendar.DAY_OF_MONTH);

        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setUserId("3XsN35waUESo9XEkNBAKcwR1DGI2");
        bookingRequest.setRoomId(room.getId());
        bookingRequest.setCheckInDate(checkInDateStr);
        bookingRequest.setCheckOutDate(checkOutDateStr);
        bookingRequest.setTotalPrice(totalPrice);
        bookingRequest.setVoucherId(selectedVoucher != null ? selectedVoucher.getMaVCString() : null);
//        Call<BookingResponse> call = apiService.bookRoom("3XsN35waUESo9XEkNBAKcwR1DGI2", room.getId(), checkInDateStr, checkOutDateStr, selectedVoucher != null ? selectedVoucher.getMaVC() : null, totalPrice);
        Call<BookingResponse> call = apiService.bookRoom(bookingRequest);
        call.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(@NonNull Call<BookingResponse> call, @NonNull Response<BookingResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(BookingActivity.this, "Đặt phòng thành công!", Toast.LENGTH_SHORT).show();
                    // Handle successful booking here (e.g., navigate to another screen, show booking details, etc.)
                } else {
                    Toast.makeText(BookingActivity.this, "Đặt phòng thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<BookingResponse> call, @NonNull Throwable t) {
                Toast.makeText(BookingActivity.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


