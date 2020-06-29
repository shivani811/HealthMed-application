package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class feedadapter extends FirestoreRecyclerAdapter<feedba,feedadapter.feedholder>{

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public feedadapter(@NonNull FirestoreRecyclerOptions<feedba> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull feedholder holder, int position, @NonNull feedba model) {
        holder.feed.setText(model.getFeedback());
    }

    @NonNull
    @Override
    public feedholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feeds,viewGroup,false);
        return new feedholder(v);
    }

    public class feedholder extends RecyclerView.ViewHolder{
        public TextView feed;
        public feedholder(View item){
            super(item);
            feed=item.findViewById(R.id.tvfeed);
        }
    }
}
