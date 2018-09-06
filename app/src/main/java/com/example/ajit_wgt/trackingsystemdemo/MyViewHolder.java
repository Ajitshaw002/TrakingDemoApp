package com.example.ajit_wgt.trackingsystemdemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    //ImageView img_status;
    TextView tv_user_email;
    RecyclerItemClick recyclerItemClick;
    public MyViewHolder(View itemView) {
        super(itemView);
        tv_user_email=itemView.findViewById(R.id.tv_user_email);
       // img_status=itemView.findViewById(R.id.img_status);

    }

    public void setRecyclerItemClick(RecyclerItemClick recyclerItemClick) {
        this.recyclerItemClick = recyclerItemClick;
    }

    @Override
    public void onClick(View v) {
        Log.e("@@@@@","RECYCLER CLICKED");
        recyclerItemClick.onItemClick(v,getAdapterPosition());

    }
}
