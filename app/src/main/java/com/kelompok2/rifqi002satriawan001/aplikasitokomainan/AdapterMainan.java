package com.kelompok2.rifqi002satriawan001.aplikasitokomainan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class AdapterMainan extends RecyclerView.Adapter<AdapterMainan.MyViewHolder> {

    private final List<ModelMainan> mList;
    private final Activity activity;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public AdapterMainan(List<ModelMainan>mList,Activity activity){
            this.mList = mList;
            this.activity = activity;
    }
    @NonNull
    @Override
    public AdapterMainan.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.item_row_mainan, parent, false);
        return new MyViewHolder(viewItem);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterMainan.MyViewHolder holder, int position) {
        final ModelMainan data = mList.get(position);
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(data.getHarga());
        if(data.getKey()!=null){
            holder.tv_id.setText("Id : "+ data.getKey());
        }

        holder.tv_item_name.setText("Nama Mainan : " + data.getNama_mainan());
        holder.tv_item_merk.setText("Merk : " + data.getMerk());
        holder.tv_item_harga.setText("Harga : Rp" + formattedNumber +".00");
        holder.tv_item_satuan.setText("Satuan : " + data.getSatuan());
        holder.tv_item_qty.setText("Stok : " + data.getStok_mainan());

        holder.bDelete.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setPositiveButton("Iya", (dialogInterface, i) -> database.child("mainan")
                    .child(data.getKey()).removeValue()
                    .addOnSuccessListener(unused ->
                            Toast.makeText(activity, "Data berhasil dihapus", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(activity, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
                    )).setNegativeButton("Tidak", (dialogInterface, i) ->
                    dialogInterface.dismiss()).setMessage("Yakin ingin hapus data ini ? "+data.getNama_mainan() );
            builder.show();
        });
        holder.card_hasil.setOnLongClickListener(view -> {
            FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
            DialogForm dialog = new DialogForm(
                    data.getNama_mainan(),
                    data.getMerk(),
                    data.getSatuan(),
                    data.getComp_nama_merk(),
                    data.getKey(),
                    data.getStok_mainan(),
                    "Ubah",
                    data.getHarga(),
                    data.getCreated_at(),
                    data.getUpdate_at()
                    );
            dialog.show(manager, "form");
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_item_name, tv_item_merk,tv_item_harga,tv_item_satuan,tv_item_qty, tv_id;
        ImageView bDelete;
        CardView card_hasil;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_item_id);
            tv_item_name = itemView.findViewById(R.id.tv_item_key);
            tv_item_merk = itemView.findViewById(R.id.tv_item_mainan);
            tv_item_harga = itemView.findViewById(R.id.tv_item_user);
            tv_item_satuan = itemView.findViewById(R.id.tv_total_harga);
            tv_item_qty = itemView.findViewById(R.id.tv_itemQTY);
            card_hasil = itemView.findViewById(R.id.rl_hasil);
            bDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
}
