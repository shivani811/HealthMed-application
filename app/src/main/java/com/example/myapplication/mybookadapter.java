package com.example.myapplication;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class mybookadapter extends FirestoreRecyclerAdapter<bookss,mybookadapter.bookholder>{
    private onitemclicklistner listner;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public mybookadapter(@NonNull FirestoreRecyclerOptions<bookss> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull bookholder holder, int position, @NonNull bookss model) {
        holder.tvname.setText(model.getName());
        holder.iv.setImageResource(model.getAccept());
    }

    @NonNull
    @Override
    public bookholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mybookitem,viewGroup,false);
        return new bookholder(v);
    }


    public class bookholder extends RecyclerView.ViewHolder{
        public TextView tvname;
        public ImageView iv;
        public bookholder(View item)
        {
            super(item);
            tvname=item.findViewById(R.id.tvname);
            iv=item.findViewById(R.id.iv);
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
    public interface onitemclicklistner{
        void onitemclick(DocumentSnapshot documentSnapshot,int position);
    }

    public void setonitemclicklistner(onitemclicklistner listner){
        this.listner=listner;

    }
}
