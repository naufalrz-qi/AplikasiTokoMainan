package com.kelompok2.rifqi002satriawan001.aplikasitokomainan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class AdapterMainanMasuk extends RecyclerView.Adapter<AdapterMainanMasuk.MyViewHolder> {

    private final List<ModelMainan> mList;
    private final Activity activity;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public AdapterMainanMasuk(List<ModelMainan>mList,Activity activity){
        this.mList = mList;
        this.activity = activity;
    }


    @NonNull
    @Override
    public AdapterMainanMasuk.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.item_row_mainan_masuk, parent, false);
        return new AdapterMainanMasuk.MyViewHolder(viewItem);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterMainanMasuk.MyViewHolder holder, int position) {
        final ModelMainan data = mList.get(position);
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(data.getHarga());
        holder.tv_id.setText("Id : "+data.getKey());
        holder.tv_item_name.setText("Nama Mainan : " + data.getNama_mainan());
        holder.tv_item_merk.setText("Merk : " + data.getMerk());
        holder.tv_item_harga.setText("Harga : Rp" + formattedNumber +".00");
        holder.tv_item_satuan.setText("Satuan : " + data.getSatuan());
        holder.tv_item_qty.setText("Stok : " + data.getStok_mainan());

        holder.bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
                DialogFormMasuk dialog = new DialogFormMasuk(
                        data.getNama_mainan(),
                        data.getMerk(),
                        data.getSatuan(),
                        data.getComp_nama_merk(),
                        data.getKey(),
                        "Ubah",
                        data.getCreated_at(),
                        data.getUpdate_at(),
                        data.getHarga(),
                        data.getStok_mainan()

                );
                dialog.show(manager, "form");

            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_item_name, tv_item_merk,tv_item_harga,tv_item_satuan,tv_item_qty, tv_id;
        ImageView bAdd;
        CardView card_Masuk;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_item_idMasuk);
            tv_item_name = itemView.findViewById(R.id.tv_item_name2);
            tv_item_merk = itemView.findViewById(R.id.tv_item_merk2);
            tv_item_harga = itemView.findViewById(R.id.tv_item_harga2);
            tv_item_satuan = itemView.findViewById(R.id.tv_item_satuan2);
            tv_item_qty = itemView.findViewById(R.id.tv_item_qty2);
            card_Masuk = itemView.findViewById(R.id.cardMasuk);
            bAdd = itemView.findViewById(R.id.imgAdd);


        }
    }
}
