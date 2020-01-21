package com.example.bitirme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;
    private TextView txtResult,tv_uid,tv_lessonCode;
    private String uid,lessonCode, qrVal,docId;
    private Dialog dialog;
    private static final String TAG = "Scanner";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        uid = getIntent().getStringExtra("user_uid");
        lessonCode = getIntent().getStringExtra("lesson_code");
        qrVal = getIntent().getStringExtra("qrVal");
        docId = getIntent().getStringExtra("docId");
        Log.d(TAG, "onCreate: "+docId);
        tv_uid = findViewById(R.id.textView);
        tv_lessonCode = findViewById(R.id.textView2);
        tv_uid.setText(uid);
        tv_lessonCode.setText(lessonCode);
        dialog = new Dialog(Scanner.this);
        dialog.setTitle("QR okundu. Kaydın yapılıyor.");
        scannerView =(ZXingScannerView)findViewById(R.id.zxscan);
        txtResult =(TextView)findViewById(R.id.txt_result);

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                    scannerView.setResultHandler(Scanner.this);
                    scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(Scanner.this,"Kabul et!",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();
    }

    @Override
    protected void onDestroy() {
        scannerView.stopCamera();
        super.onDestroy();
    }

    @Override
    public void handleResult(Result rawResult) {
        String result = rawResult.getText().trim();
        if(result.equals(qrVal))
        {
            Log.d(TAG, "handleResult: "+result);
            Log.d(TAG, "qrval: "+qrVal);
            if(dialog!=null)
                dialog.show();
            signUsertoLesson();
        }
        // Raw Result Buraya Gelıyor !!
        txtResult.setText(rawResult.getText());
       /* Intent karekod=new Intent(Scanner.this,DersSecim.class);//yeni açaçağımız sayfa
        karekod.putExtra("veri",txtResult.getText().toString());//açtığımız sayfaya değer gönderme
        startActivity(karekod);
*/
    }
    private void signUsertoLesson(){
        DocumentReference documentReference = FirebaseFirestore.getInstance().collection(getResources().getString(R.string.lessons)).document(docId);
        documentReference.update("checkedUsers", FieldValue.arrayUnion(uid)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    dialog.hide();
                    Toast.makeText(Scanner.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(Scanner.this, "Bir hata meydana geldi "+task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.hide();

                Toast.makeText(Scanner.this, "Hata! "+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
