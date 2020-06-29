package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class hospital_adapterr extends RecyclerView.Adapter<hospital_adapterr.hospitalholder> implements Filterable {
    private RecyclerView recyclerView;
    private Context mContext;
    private List<hosp> mUploads;
    private List<hosp> mfullUploads;
    public static final String SHARED_PREFS="shared_prefs";
    public static final String TEXT="text";


    public hospital_adapterr(RecyclerView recyclerView, Context context, List<hosp> uploads) {
        this.recyclerView = recyclerView;
        mContext = context;
        mUploads = uploads;
        mfullUploads=new ArrayList<>(mUploads);
    }

    @NonNull
    @Override
    public hospitalholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.hospital_item, viewGroup, false);
        return new hospitalholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull hospitalholder hospitalholder, int i) {
        hosp uploadcur = mUploads.get(i);
        hospitalholder.tvname.setText(uploadcur.getName());
        hospitalholder.iv.setImageResource(uploadcur.getIcon());
    }


    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class hospitalholder extends RecyclerView.ViewHolder {
        public TextView tvname;
        public ImageView iv;

        public hospitalholder(View item) {
            super(item);
            tvname = item.findViewById(R.id.tvname);
            iv = item.findViewById(R.id.iv);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = recyclerView.getChildLayoutPosition(v);
                    if(position!=RecyclerView.NO_POSITION) {
                        hospital_view.HOSPITAL = mUploads.get(position).getName();
                        Intent intent = new Intent(mContext, nav_home.class);
                        SharedPreferences sharedPreferences=mContext.getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString(TEXT,hospital_view.HOSPITAL);
                        editor.apply();
                        ((hospital_view)mContext).finish();
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<hosp> filterl=new ArrayList<>();
            if(constraint==null || constraint.length()==0){
                filterl.addAll(mfullUploads);
            }else{
                String filterpat=constraint.toString().toLowerCase().trim();
                for(hosp item:mfullUploads){
                    if(item.getName().toLowerCase().contains(filterpat)){
                        filterl.add(item);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filterl;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mUploads.clear();
            mUploads.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}
