package com.github.abhijit.pinterestclient.ui.home.fragment.pins;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.abhijit.pinterestclient.R;
import com.pinterest.android.pdk.PDKPin;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PinsAdapter extends RecyclerView.Adapter<PinsAdapter.BoardHolder> {

    private List<PDKPin> pinList = new ArrayList<>();

    private Context context;
    private OnPinClickListener listener;

    public PinsAdapter(Context context, OnPinClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public BoardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_pin_item, parent, false);
        return new BoardHolder(v);
    }

    @Override
    public void onBindViewHolder(BoardHolder holder, int position) {
        PDKPin pin = pinList.get(position);
        Glide.with(context)
                .load(pin.getImageUrl())
                .asBitmap()
                .into(holder.pinImage);
        holder.boardDescription.setText(pin.getNote());
        holder.boardPinCount.setText(String.valueOf("1.3K"));
    }

    @Override
    public int getItemCount() {
        return pinList.size();
    }

    public void addBoard(List<PDKPin> list){
        if (list != null) {
            pinList.clear();
            pinList.addAll(list);
            notifyDataSetChanged();
        }
    }

    class BoardHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.pinImage)
        ImageView pinImage;

        @BindView(R.id.boardDescription)
        TextView boardDescription;

        @BindView(R.id.boardPinCount)
        TextView boardPinCount;

        BoardHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            bindClickListener();
        }

        private void bindClickListener(){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        PDKPin pin = pinList.get(getAdapterPosition());
                        listener.onPinClick(pin);
                    }
                }
            });
        }
    }

    interface OnPinClickListener {
        void onPinClick(PDKPin pin);
    }
}
