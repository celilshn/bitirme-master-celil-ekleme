package com.example.bitirme;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bitirme.newAdapters.LessonLinkAdapter;
import com.example.bitirme.newModels.Lesson;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DersSecim extends AppCompatActivity {
    private FirebaseFirestore myStore;
    private CollectionReference lessonCollectionReference;
    private ArrayList<Lesson> lessons;
    private ArrayList<String> docIds;
    private RecyclerView recyclerView;
    private LessonLinkAdapter lessonLinkAdapter;
    private static final String TAG = "DersSecim";

    @Override
    protected void onResume() {
        super.onResume();
        lessons.clear();
        docIds.clear();
        getLessons();
    }
private void getLessons(){
    lessonCollectionReference.whereEqualTo("activity", true).addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
            if (e != null) {
                Log.d(TAG, "Error:" + e.getMessage());
            } else {
                for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                    Log.d(TAG, "onEvent: ");
                    Lesson lesson = queryDocumentSnapshot.toObject(Lesson.class);
                    lessons.add(lesson);
                    docIds.add(queryDocumentSnapshot.getId());
                    lessonLinkAdapter.notifyDataSetChanged();

                }
            }
        }
    });
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ders_secim);

        myStore = FirebaseFirestore.getInstance();
        lessonCollectionReference = myStore.collection(getResources().getString(R.string.lessons));
        lessons = new ArrayList<>();
        docIds = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerv_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        lessonLinkAdapter = new LessonLinkAdapter(lessons, docIds, getApplicationContext());
        recyclerView.setAdapter(lessonLinkAdapter);
    }

}
