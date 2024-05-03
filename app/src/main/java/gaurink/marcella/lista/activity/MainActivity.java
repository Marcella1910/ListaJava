package gaurink.marcella.lista.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import gaurink.marcella.lista.R;
import gaurink.marcella.lista.model.MyItem;

public class MainActivity extends AppCompatActivity {

    static int NEW_ITEM_REQUEST = 1;
    List<MyItem> itens = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ConstraintLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FloatingActionButton fabAddItem = findViewById(R.id.fabAddNewItem);
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (MainActivity.this,NewItemActivity.class);
                startActivityForResult(i,NEW_ITEM_REQUEST);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode,resultCode,data);
      if(requestCode == NEW_ITEM_REQUEST) {
          if(resultCode == Activity.RESULT_OK) {
              MyItem myItem = new MyItem();
              myItem.title = data.getStringExtra("title");
              myItem.description = data.getStringExtra("description");
              myItem.photo = data.getData();

              itens.add(myItem);
          }
      }
    }
}