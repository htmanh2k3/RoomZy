package com.app.roomzy.Adapter;//package com.app.roomzy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.roomzy.Models.Voucher;
import com.app.roomzy.R;
import com.bumptech.glide.Glide;

import java.util.List;

    public class VoucherAdapter extends ArrayAdapter<Voucher> {
        private Context context;
        private int layoutItem;
        private List<Voucher> voucherList;

        public VoucherAdapter(Context context, int layoutItem, List<Voucher> voucherList) {
            super(context, layoutItem, voucherList);
            this.context = context;
            this.layoutItem = layoutItem;
            this.voucherList = voucherList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(layoutItem, parent, false);
            }

            Voucher voucher = voucherList.get(position);

            ImageView imgVoucher = convertView.findViewById(R.id.imgVoucher);
            TextView tvGiamGia = convertView.findViewById(R.id.tvGiamGia);
            TextView tvName = convertView.findViewById(R.id.tvNameVoucher);
            TextView tvMoTa = convertView.findViewById(R.id.tvMoTa);

            if (voucher != null) {
                tvGiamGia.setText(voucher.getGiamGia());
                tvName.setText(voucher.getTenVoucher());
                tvMoTa.setText(voucher.getMoTa());

                Glide.with(context)
                        .load(voucher.getImageURL())
                        .into(imgVoucher);
            }

            return convertView;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return getView(position, convertView, parent);
        }
    }

