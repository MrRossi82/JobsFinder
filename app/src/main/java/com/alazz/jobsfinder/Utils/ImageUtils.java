package com.alazz.jobsfinder.Utils;

import android.content.Context;
import android.widget.ImageView;

import com.alazz.jobsfinder.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class ImageUtils {


    public static  void onLoadImage(Context context, String url, ImageView imageView){

        Glide.with(context)
                .load(url)
                .apply(RequestOptions.circleCropTransform()
                        .override(200,200)
                        .placeholder(R.drawable.img_logo_placeholder).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imageView);

    }
}
