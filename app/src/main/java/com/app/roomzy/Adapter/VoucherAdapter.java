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
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutItem, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imgVoucher = convertView.findViewById(R.id.imgVoucher);
            viewHolder.tvGiamGia = convertView.findViewById(R.id.tvGiamGia);
            viewHolder.tvName = convertView.findViewById(R.id.tvNameVoucher);
            viewHolder.tvMoTa = convertView.findViewById(R.id.tvMoTa);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Voucher voucher = voucherList.get(position);

        if (voucher != null) {
            viewHolder.tvGiamGia.setText(voucher.getGiamGia());
            viewHolder.tvName.setText(voucher.getTenVC());
            viewHolder.tvMoTa.setText(voucher.getMoTa());
            Glide.with(context)
                    .load(voucher.getHinh())
                    .into(viewHolder.imgVoucher);
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    static class ViewHolder {
        ImageView imgVoucher;
        TextView tvGiamGia;
        TextView tvName;
        TextView tvMoTa;
    }
}



