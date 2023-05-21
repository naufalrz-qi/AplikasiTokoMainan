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

public class AdapterLaporan extends RecyclerView.Adapter<AdapterLaporan.MyViewHolder> {

    private final List<ItemTransaksi> mList;
    private final Activity activity;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public AdapterLaporan(List<ItemTransaksi>mList, Activity activity){
            this.mList = mList;
            this.activity = activity;
    }
    @NonNull
    @Override
    public AdapterLaporan.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.item_row_transaksi, parent, false);
        return new MyViewHolder(viewItem);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdapterLaporan.MyViewHolder holder, int position) {
        final ItemTransaksi data = mList.get(position);
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(data.getTotalHargaJual());
        holder.tv_item_key.setText("Mainan : " + data.getKey_mainan());
        holder.tv_item_kasir.setText("Kasir : "+data.getUser());
        holder.tv_item_mainan.setText("Id : "+data.getKey_tr());
        holder.tv_item_qty.setText("Stok : -" + data.getQty());
        holder.tv_item_total.setText("Harga Jual : +Rp" + formattedNumber +".00");
        holder.waktuTr.setText("Waktu : " + data.getWaktuTransaksi());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_item_key, tv_item_kasir,tv_item_mainan,tv_item_qty,tv_item_total, waktuTr;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_item_key = itemView.findViewById(R.id.tv_item_key);
            tv_item_mainan = itemView.findViewById(R.id.tv_item_mainan);
            tv_item_kasir = itemView.findViewById(R.id.tv_item_user);
            tv_item_total = itemView.findViewById(R.id.tv_total_harga);
            tv_item_qty = itemView.findViewById(R.id.tv_itemQTY);
            waktuTr = itemView.findViewById(R.id.tv_item_waktu);
        }
    }
}
