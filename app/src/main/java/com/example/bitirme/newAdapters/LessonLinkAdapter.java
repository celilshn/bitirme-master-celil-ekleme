package com.example.bitirme.newAdapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bitirme.DersSecim;
import com.example.bitirme.R;
import com.example.bitirme.Scanner;
import com.example.bitirme.newModels.Lesson;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class LessonLinkAdapter extends RecyclerView.Adapter<LessonLinkAdapter.ViewHolder> {
    private static final String TAG = "LessonLinkAdapter";
    private ArrayList<Lesson> lessons;
    private ArrayList<String> docIds;
    private Context context;
    private FirebaseUser firebaseUser;

    public LessonLinkAdapter(ArrayList<Lesson> lessons,ArrayList<String> docIds, Context context) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        this.lessons = lessons;
        this.context = context;
        this.docIds= docIds;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.listitem, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: ");
        holder.post_ders.setText(lessons.get(position).getLessonName());
        holder.post_kod.setText(lessons.get(position).getLessonCode());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!lessons.get(position).getCheckedUsers().contains(firebaseUser.getUid()))
                        {
                            Intent intent = new Intent(context, Scanner.class);
                            intent.putExtra("user_uid", FirebaseAuth.getInstance().getUid());
                            intent.putExtra("lesson_code", lessons.get(position).getLessonCode());
                            intent.putExtra("qrVal", lessons.get(position).getQrVal());
                            intent.putExtra("docId", docIds.get(position));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                        else
                            Toast.makeText(context, "Zaten kaydın mevcut!", Toast.LENGTH_SHORT).show();
                    }

                });
    }


    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView post_ders, post_kod;

        private ViewHolder(View itemView) {
            super(itemView);
            this.post_ders = itemView.findViewById(R.id.post_ders);
            this.post_kod = itemView.findViewById(R.id.post_kod);

        }
    }
}