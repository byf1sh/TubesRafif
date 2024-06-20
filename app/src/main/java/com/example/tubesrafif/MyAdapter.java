package com.example.tubesrafif;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<DataModel> dataList;
    private Context context;

    public MyAdapter(List<DataModel> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataModel data = dataList.get(position);
        holder.textView1.setText(data.getNamaDeck());

        int harga = Integer.parseInt(data.getHarga());
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        String formattedHarga = numberFormat.format(harga);
        String harganew = "Rp. " + formattedHarga;

        holder.textView2.setText(harganew);

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetaildeckActivity.class);
                intent.putExtra("harga", harganew);
                intent.putExtra("title", data.getNamaDeck());
                context.startActivity(intent);
            }
        });
        // Anda juga dapat menambahkan pemuatan gambar dengan library seperti Picasso atau Glide untuk urlFoto
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2;
        ImageButton imageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.title_deck);
            textView2 = itemView.findViewById(R.id.avail_deck);
            imageButton = itemView.findViewById(R.id.availabledeck);
        }
    }
}



