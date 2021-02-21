package com.example.hotelgo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelAdapterVH> {

    private List<HotelResponse> hotelResponseLists;
    private Context context;
    private ClickedItem clickedItem;

    public HotelAdapter(List<HotelResponse> hotelResponseList, Context context,ClickedItem clickedItem) {
        this.hotelResponseLists = hotelResponseList;
        this.context = context;
        this.clickedItem = clickedItem;
    }

    @NonNull
    @Override
    public HotelAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.hotel_item,parent,false);
        return new HotelAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelAdapterVH holder, int position) {
        HotelResponse hotelResponse = hotelResponseLists.get(position);

        String address =hotelResponse.getStreet()+", "+hotelResponse.getCity()+", "+hotelResponse.getState();
        String url = hotelResponse.getHotel_img_url();


        holder.hotelName.setText(hotelResponse.getHotel_name());
        holder.hotelAddress.setText(address);
        holder.hotelRating.setText(String.valueOf(hotelResponse.getHotel_ratings()));
        holder.hotelPrice.setText("₹"+String.valueOf(hotelResponse.getHotel_price()));

        holder.hotelItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedItem.ClickedHotel(hotelResponse);
            }
        });

        Picasso.with(context).load(url).into(holder.hotelImage);

    }

    public interface ClickedItem{
        public void ClickedHotel(HotelResponse hotelResponse);
    }

    @Override
    public int getItemCount() {
        return hotelResponseLists.size();
    }

    public class HotelAdapterVH extends RecyclerView.ViewHolder {

        TextView hotelName;
        TextView hotelAddress;
        TextView hotelRating;
        TextView hotelPrice;
        CardView hotelItem;
        ImageView hotelImage;

        public HotelAdapterVH(@NonNull View itemView) {
            super(itemView);

            hotelName = itemView.findViewById(R.id.hotelName_textView);
            hotelAddress = itemView.findViewById(R.id.hotelAddress_textView);
            hotelRating = itemView.findViewById(R.id.hotelRating_textView);
            hotelPrice = itemView.findViewById(R.id.hotelPrice_textView);
            hotelItem = itemView.findViewById(R.id.hotel_item);
            hotelImage = itemView.findViewById(R.id.hotel_imageView);

        }
    }
}
