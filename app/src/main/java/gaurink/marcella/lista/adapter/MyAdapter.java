package gaurink.marcella.lista.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import gaurink.marcella.lista.R;
import gaurink.marcella.lista.activity.MainActivity;
import gaurink.marcella.lista.model.MyItem;


public class MyAdapter extends RecyclerView.Adapter{
    MainActivity mainActivity;
    List<MyItem>itens;


    public MyAdapter(MainActivity mainActivity,List<MyItem> itens) {
        this.mainActivity = mainActivity;
        this.itens = itens;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflador de layouts, usado para ler arquivo xml do layout do item e assim, criar os elementos do dito cujo
        LayoutInflater inflater = LayoutInflater.from(mainActivity);
        View v = inflater.inflate(R.layout.item_list,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    //preencher a UI com os dados do item
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyItem myItem = itens.get(position);
        View v = holder.itemView;
        ImageView imvfoto = v.findViewById(R.id.imvFoto);
        imvfoto.setImageBitmap(myItem.photo);
        TextView tvTitle = v.findViewById(R.id.tvTitle);
        tvTitle.setText(myItem.title);
        TextView tvdesc = v.findViewById(R.id.tvDesc);
        tvdesc.setText(myItem.description);
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }
}
