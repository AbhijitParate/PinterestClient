package com.github.abhijit.pinterestclient.ui.home.fragment.board;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.abhijit.pinterestclient.R;
import com.pinterest.android.pdk.PDKBoard;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardHolder> {

    private List<PDKBoard> boardList = new ArrayList<>();

    private Context context;
    private OnBoardClickListener listener;

    public BoardAdapter(Context context, OnBoardClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public BoardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_board_item, parent, false);
        return new BoardHolder(v);
    }

    @Override
    public void onBindViewHolder(BoardHolder holder, int position) {
        PDKBoard board = boardList.get(position);
        Glide.with(context)
                .load(board.getImageUrl())
                .asBitmap()
                .into(holder.boardImage);
        holder.boardDescription.setText(board.getName());
        holder.boardPinCount.setText(String.valueOf(board.getPinsCount()));
    }

    @Override
    public int getItemCount() {
        return boardList.size();
    }

    public void addBoard(List<PDKBoard> list){
        if (list != null) {
            boardList.clear();
            boardList.addAll(list);
            notifyDataSetChanged();
        }
    }

    class BoardHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.boardImage)
        ImageView boardImage;

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
                        PDKBoard board = boardList.get(getAdapterPosition());
                        listener.onBoardClick(board);
                    }
                }
            });
        }
    }

    interface OnBoardClickListener {
        void onBoardClick(PDKBoard board);
    }

}
