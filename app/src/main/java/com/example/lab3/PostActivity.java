package com.example.lab3;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class PostActivity extends AppCompatActivity {

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        final ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Bundle bundle = result.getData().getExtras();
                        Bitmap image = (Bitmap) bundle.get("data");
                        img.setImageBitmap(image);
                    }
                });

        img = findViewById(R.id.imageView);
        img.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            launcher.launch(intent);
        });

        EditText txtMsg = findViewById(R.id.txtMessage);
        ImageButton btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(view -> {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putParcelable("bitmap", ((BitmapDrawable) img.getDrawable()).getBitmap());
            bundle.putCharSequence("msg", txtMsg.getText());
            intent.putExtras(bundle);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
        ImageButton btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(view -> {
            setResult(Activity.RESULT_CANCELED);
            finish();
        });
    }
}