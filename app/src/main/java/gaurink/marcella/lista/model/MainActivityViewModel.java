package gaurink.marcella.lista.model;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

// viewmodel que irá guardar os dados referentes a mainActivity
public class MainActivityViewModel extends ViewModel {

    List<MyItem> itens = new ArrayList<>();

    public List<MyItem> getItens() {
        return itens;
    }
}
