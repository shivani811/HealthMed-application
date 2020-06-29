package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class doctoradapter extends FirestoreRecyclerAdapter<doc,doctoradapter.docholder>{
    private ondocclicklistner listner;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public doctoradapter(@NonNull FirestoreRecyclerOptions<doc> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull docholder holder, int position, @NonNull doc model) {
        holder.tvname.setText(model.getDname());
    }

    @NonNull
    @Override
    public docholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.books,viewGroup,false);
        return new docholder(v);
    }

    public class docholder extends RecyclerView.ViewHolder{
        public TextView tvname;
        public docholder(View item)
        {
            super(item);
            tvname=item.findViewById(R.id.tvname);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION && listner!=null){
                        listner.onitemclick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }
    public interface ondocclicklistner{
        void onitemclick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setonitemclicklistner(ondocclicklistner listner){
        this.listner=listner;

    }
}
