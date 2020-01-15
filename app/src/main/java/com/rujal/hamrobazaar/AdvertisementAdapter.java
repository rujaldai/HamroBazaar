package com.rujal.hamrobazaar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdvertisementAdapter extends RecyclerView.Adapter<AdvertisementAdapter.AdvertisementViewHolder> {

    Context mContext;
    List<Advertisement> advertisements;

    AdvertisementAdapter(Context mContext, List<Advertisement> advertisements) {
        this.mContext = mContext;
        this.advertisements = advertisements;
    }

    @NonNull
    @Override
    public AdvertisementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_advertisement, parent, false);
        return new AdvertisementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertisementViewHolder holder, int position) {
        Advertisement advertisement = advertisements.get(position);
        holder.imageAd.setImageResource(Integer.valueOf(advertisement.getImageId()));
        holder.tvProductType.setText(advertisement.getProductType());
        holder.tvAdName.setText(advertisement.getName());
        holder.tvAdPrice.setText(advertisement.getPrice());
    }

    @Override
    public int getItemCount() {
        return advertisements.size();
    }

    class AdvertisementViewHolder extends RecyclerView.ViewHolder {
        ImageView imageAd;
        TextView tvAdName, tvAdPrice, tvProductType;

        AdvertisementViewHolder(@NonNull View itemView) {
            super(itemView);
            imageAd = itemView.findViewById(R.id.imageAd);
            tvAdName = itemView.findViewById(R.id.tvAdName);
            tvAdPrice = itemView.findViewById(R.id.tvAdPrice);
            tvProductType = itemView.findViewById(R.id.tvProductType);
        }
    }
}
