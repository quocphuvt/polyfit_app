package com.example.polyfit_app.utils;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.bumptech.glide.Glide;
import com.example.polyfit_app.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BindingAdapter {
    @androidx.databinding.BindingAdapter("formatDate")
    public static void formatDate(TextView tv_date , String createdAt) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(createdAt);
            String formattedDate = format.format(date);
            tv_date.setText(formattedDate);
        } catch (Exception e) {

        }
    }

    @androidx.databinding.BindingAdapter("setUserStatus")
    public static void setUserStatus(TextView tv_status, Float bmi) {
        if(Util.getLevelId(bmi) == 171) {
            tv_status.setText("Thiếu cân");
        } else if(Util.getLevelId(bmi) == 41) {
            tv_status.setText("Bình thường");
        } else {
            tv_status.setText("Thừa cân");
        }
    }

    @androidx.databinding.BindingAdapter("userAvatar")
    public static void setUserAvatar(ImageView iv_avatar, String imageUrl) {
        Glide.with(iv_avatar.getContext())
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_avatar)
                .into(iv_avatar);
    }
}
