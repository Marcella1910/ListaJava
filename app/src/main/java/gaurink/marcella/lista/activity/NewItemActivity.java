package gaurink.marcella.lista.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import gaurink.marcella.lista.R;
import gaurink.marcella.lista.model.NewItemActivityViewModel;

public class NewItemActivity extends AppCompatActivity {

    //definiçaõ de "caminho" que deve seguir
    static int PHOTO_PICKER_REQUEST = 1;

    //elemento de escolha da foto, no momento, vazio
    Uri photoSelected = null;

    @Override
    //metodo para mostrar na interface
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_item);
        NewItemActivityViewModel vm = new ViewModelProvider(this).get(NewItemActivityViewModel.class);

        Uri selectPhotoLocation = vm.getSelectPhotoLocation();
        if (selectPhotoLocation != null) {
            ImageView imvfotoPrewiew = findViewById(R.id.imvPhotoPrewiew);
            imvfotoPrewiew.setImageURI(selectPhotoLocation);
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ConstraintLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //criacao e definicao do botao que adicionara a imagem
        ImageButton imgCI = findViewById(R.id.imbCl);
        imgCI.setOnClickListener(new View.OnClickListener() {
            @Override
            //metodo do que vai acontecer quando clicar no botao
            public void onClick(View v) {
                //criando intent para a selecao do item
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent,PHOTO_PICKER_REQUEST);
            }
        });

        //criacao e definicao do botao de publicação da imagem
        Button btnAddItem = findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            //metodo do que vai acontecer quando clicar no botao
            public void onClick(View v) {
                //se a foto estiver vazia, nao prosseguira para a proxima tela quando clicar em publicar
                if(photoSelected == null) {
                    Toast.makeText(NewItemActivity.this, "É necessário selecionar uma imagem!", Toast.LENGTH_LONG).show();
                    return;
                }
                //definicao da string do titulo
                EditText etTitle = findViewById(R.id.etTitle);
                String title = etTitle.getText().toString();
                //se o texto estiver vazio, nao prosseguira para a proxima tela quando clicar em publicar
                if(title.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "É necessário inserir um título", Toast.LENGTH_LONG).show();
                    return;
                }
                //criacao da string da descrição
                EditText etDesc = findViewById(R.id.etDesc);
                String description = etDesc.getText().toString();
                //se a descricap estiver vazia, nao prosseguira para a proxima tela quando clicar em publicar
                if(description.isEmpty()) {
                    Toast.makeText(NewItemActivity.this,"É necessário inserir uma descrição", Toast.LENGTH_LONG).show();
                    return;
                }
                //depois de tudo ser preenchido, criar intent e data para guardar todos os itens
                Intent i = new Intent();
                i.setData(photoSelected);
                i.putExtra("title", title);
                i.putExtra("description", description);
                setResult(Activity.RESULT_OK,i);// cria o resultado que vai ser entregue pra ,ainactivi
                //termino da activity
                finish();
            }
        });


    }

    // obter o resultado da activitu que escolhe a foto
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == PHOTO_PICKER_REQUEST) {
            if(resultCode == Activity.RESULT_OK) {
                Uri photoSelected = data.getData();
                ImageView imvfotoPrewiew = findViewById(R.id.imvPhotoPrewiew);
                imvfotoPrewiew.setImageURI(photoSelected);

                NewItemActivityViewModel vm = new ViewModelProvider(this).get(NewItemActivityViewModel.class);
                vm.setSelectPhotoLocation(photoSelected);
                //photoSelected = data.getData();
                //ImageView imvfotoPrewiew = findViewById(R.id.imvPhotoPrewiew);
                //imvfotoPrewiew.setImageURI(photoSelected);
            }
        }
    }
}