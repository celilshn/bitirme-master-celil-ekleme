package com.example.bitirme;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.CollationElementIterator;

public class FirebaseViewHolder extends RecyclerView .ViewHolder{

    public TextView ders, derskodu;
    // public ImageView image;

    public FirebaseViewHolder(@NonNull View itemView) {
        super(itemView);

        ders = itemView.findViewById(R.id.post_ders);
        derskodu = itemView.findViewById(R.id.post_kod);
        //image = itemView.findViewById(R.id.post_image);
    }

}
