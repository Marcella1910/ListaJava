package gaurink.marcella.lista.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import gaurink.marcella.lista.R;
import gaurink.marcella.lista.adapter.MyAdapter;
import gaurink.marcella.lista.model.MainActivityViewModel;
import gaurink.marcella.lista.model.MyItem;
import gaurink.marcella.lista.util.Util;

public class MainActivity extends AppCompatActivity {

    static int NEW_ITEM_REQUEST = 1;
    List<MyItem> itens = new ArrayList<>();

    MyAdapter myAdapter;

    @Override
    //criação de metodo para mostrar na interface
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        //colocar conteudo na interface
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ConstraintLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //criacao do botao para publicação
        FloatingActionButton fabAddItem = findViewById(R.id.fabAddNewItem);
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            //metodo para quando clicar ocorrer a publicação
            public void onClick(View v) {
                Intent i = new Intent (MainActivity.this,NewItemActivity.class);
                startActivityForResult(i,NEW_ITEM_REQUEST);
            }
        });

        // configuracao do recycleview
        RecyclerView rvItens = findViewById(R.id.rvItens);
        MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        List<MyItem> itens = vm.getItens();
        myAdapter = new MyAdapter(this,itens);
        rvItens.setAdapter(myAdapter);

        rvItens.setHasFixedSize(true);

        //controle do layout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvItens.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration =new DividerItemDecoration(rvItens.getContext(),DividerItemDecoration.VERTICAL);
        rvItens.addItemDecoration(dividerItemDecoration);
    }
    @Override
    //metodo para receber o resultado de outra activity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode,resultCode,data);
      //se o item nao estiver vazio
      if(requestCode == NEW_ITEM_REQUEST) {
          //se o item estiver como true
          if(resultCode == Activity.RESULT_OK) {
              MyItem myItem = new MyItem();
              myItem.title = data.getStringExtra("title");
              myItem.description = data.getStringExtra("description");
              Uri selectedPhotoURI = data.getData();

              try {
                  //carrega a imagem e a guarda dentro de um Bitmap
                  Bitmap photo = Util.getBitmap(MainActivity.this, selectedPhotoURI, 100,100);

                  myItem.photo = photo;
              } catch ( FileNotFoundException e) {
                  e.printStackTrace();
              }

              MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
              List<MyItem> itens = vm.getItens();

              //adicionar os itens
              itens.add(myItem);
              //mostrar pra interface que um item ja a estar preenchendo
              myAdapter.notifyItemInserted(itens.size()-1);
          }
      }
    }
}