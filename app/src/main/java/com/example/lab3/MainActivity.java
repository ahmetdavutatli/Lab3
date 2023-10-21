package com.example.lab3;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Post> posts = new ArrayList<>();
    static final int POST_REQUEST = 1;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        PostAdapter adapter = new PostAdapter(this, posts);
        listView.setAdapter(adapter);


        final ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        Post post = new Post();
                        post.setImage((Bitmap) data.getExtras().get("bitmap"));
                        post.setMessage(data.getCharSequenceExtra("msg").toString());
                        posts.add(post);
                        ((PostAdapter) listView.getAdapter()).notifyDataSetChanged();
                    }
                });

        Button btnPost = findViewById(R.id.btnPost);
        btnPost.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, PostActivity.class);
            launcher.launch(intent);
        });
    }
}