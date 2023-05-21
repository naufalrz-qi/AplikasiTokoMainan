package com.kelompok2.rifqi002satriawan001.aplikasitokomainan;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Objects;

public class AdapterListItem extends RecyclerView.Adapter<AdapterListItem.MyViewHolder> {
    private final List<ItemMainan> mList;
    private final Activity activity;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();


    public AdapterListItem(List<ItemMainan> mList, Activity activity) {
        this.mList = mList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdapterListItem.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.item_row_mainan_masuk, parent, false);
        return new MyViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListItem.MyViewHolder holder, int position) {
        final ItemMainan data = mList.get(position);
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(data.getHarga());
        holder.tv_id.setText("Id : "+data.getKey());
        holder.tv_item_name.setText("Nama Mainan : " + data.getNama_mainan());
        holder.tv_item_merk.setText("Merk : " + data.getMerk());
        holder.tv_item_harga.setText("Harga : Rp" + formattedNumber +".00");
        holder.tv_item_satuan.setText("Satuan : " + data.getSatuan());
        holder.tv_item_qty.setText("Stok : " + data.getStok_mainan());

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        String userr =  FirebaseDatabase.getInstance().getReference("users").child(Objects.requireNonNull(currentUser).getUid()).getKey();
        holder.bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(data.getStok_mainan()>0){
                    database.child(userr).child(data.getKey()).setValue(new ItemMainan(data.getNama_mainan(), data.getMerk(), data.getHarga(), data.getSatuan(), data.getStok_mainan(), data.getComp_nama_merk(), data.getQty(), data.getCreated_at(), data.getUpdate_at()));
                    activity.startActivity(new Intent(activity, DataKeluarActivity.class));
                    activity.finish();
                }else {
                    Toast.makeText(activity, "Stok Habis", Toast.LENGTH_SHORT).show();
                }

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
