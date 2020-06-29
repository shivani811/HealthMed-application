package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class image_adapter extends RecyclerView.Adapter<image_adapter.Imageviewholder> {
    private RecyclerView recyclerView;
    private Context mContext;
    private List<upload> mUploads;
    public image_adapter(RecyclerView recyclerView,Context context,List<upload> uploads)
    {
        this.recyclerView=recyclerView;
        mContext=context;
        mUploads=uploads;
    }

    @NonNull
    @Override
    public Imageviewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.image_item,viewGroup,false);
        return new Imageviewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Imageviewholder imageviewholder, int i) {
        upload uploadcur=mUploads.get(i);
        imageviewholder.tvname.setText(uploadcur.getRname());
        imageviewholder.tvmessage.setText(uploadcur.getMessage());
        imageviewholder.tvpdf.setText(mUploads.get(i).getFname());
        imageviewholder.tvcontact.setText(uploadcur.getContact());
        imageviewholder.tvhosp.setText(uploadcur.getHospital());
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class Imageviewholder extends RecyclerView.ViewHolder{
        public TextView tvname,tvmessage,tvpdf,tvcontact,tvhosp;
        public Imageviewholder(@NonNull View itemView) {
            super(itemView);
            tvname=itemView.findViewById(R.id.tvname);
            tvmessage=itemView.findViewById(R.id.tvmessage);
            tvpdf=itemView.findViewById(R.id.tvpdf);
            tvcontact=itemView.findViewById(R.id.tvcontact);
            tvhosp=itemView.findViewById(R.id.tvhosp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=recyclerView.getChildLayoutPosition(v);
                    Intent intent=new Intent();
                    intent.setDataAndType(Uri.parse(mUploads.get(pos).getRurl()),Intent.ACTION_VIEW);
                    mContext.startActivity(intent);
                }
            });

        }
    }
}
