package com.example.hotelgo;

import android.content.Context;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookedAdapter extends RecyclerView.Adapter<BookedAdapter.BookedAdapterVH> {

    private List<BookedResponse> bookedResponseList;
    private Context context;
    private ClickedItem clickedItem;

    public BookedAdapter(List<BookedResponse> bookedResponseList, Context context, ClickedItem clickedItem) {
        this.bookedResponseList = bookedResponseList;
        this.context = context;
        this.clickedItem = clickedItem;
    }

    @NonNull
    @Override
    public BookedAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view =layoutInflater.inflate(R.layout.booked_item,parent,false);
        return new BookedAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookedAdapterVH holder, int position) {
        BookedResponse bookedResponse = bookedResponseList.get(position);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d");

        String url = bookedResponse.getHotel_img_url();
        String date = simpleDateFormat.format(new Date(bookedResponse.getCheck_in()))+" "+simpleDateFormat.format(new Date(bookedResponse.getCheck_out()));
        String bookedId = String.valueOf(bookedResponse.getBook_id());
        String noOfGuest = String.valueOf(bookedResponse.getNumber_of_guest());

        holder.hotelName.setText(bookedResponse.getHotel_name());
        holder.checkInOut.setText(date);
        holder.bookingId.setText(bookedId);
        holder.noOfGuest.setText(noOfGuest);
        Picasso.with(context).load(url).into(holder.hotelImage);

        holder.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedItem.ClickedBookedId(bookedResponse.getBook_id());
            }
        });
    }

    public interface ClickedItem{
        public void ClickedBookedId(int bookedId);
    }

    @Override
    public int getItemCount() {
        return bookedResponseList.size();
    }

    public class BookedAdapterVH extends RecyclerView.ViewHolder {

        TextView hotelName;
        TextView checkInOut;
        TextView bookingId;
        TextView noOfGuest;
        ImageView hotelImage;
        Button cancelButton;

        public BookedAdapterVH(@NonNull View itemView) {
            super(itemView);

            hotelName = itemView.findViewById(R.id.booked_hotelName);
            checkInOut = itemView.findViewById(R.id.booked_date);
            bookingId = itemView.findViewById(R.id.booked_id);
            noOfGuest = itemView.findViewById(R.id.booked_no_of_guest);
            hotelImage = itemView.findViewById(R.id.booked_hotel_imageView);
            cancelButton = itemView.findViewById(R.id.booked_cancel_button);

        }
    }
}
