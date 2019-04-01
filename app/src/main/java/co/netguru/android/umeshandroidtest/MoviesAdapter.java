package co.netguru.android.umeshandroidtest;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private ArrayList<MovieModel> adapterList = new ArrayList<MovieModel>();
    private ClickItemPosition clickItemPosition;

    public MoviesAdapter(ClickItemPosition clickItemPosition) {
        this.clickItemPosition = clickItemPosition;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_item, parent, false);
        ViewHolder rcv = new ViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItemPosition.clickOnItem(adapterList.get(position));
            }
        });
    }


    public void updateList(ArrayList<MovieModel> adapterList){
        this.adapterList = adapterList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvSubtitle;
        private TextView tvShow;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvSubtitle = (TextView) itemView.findViewById(R.id.tv_subtitle);
            tvShow = (TextView) itemView.findViewById(R.id.tv_show);
        }
    }
}
