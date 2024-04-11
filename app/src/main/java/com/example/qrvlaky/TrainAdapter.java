package com.example.qrvlaky;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


// funkce která ukazuje jak mají vypadat jednotlivé prvky vyobrazené v Vlaky.java a přidává jim funkce
public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.TrainViewHolder> {
    private List<Train> trainList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int trainId);
    }

    public TrainAdapter(List<Train> trainList, OnItemClickListener listener) {
        this.trainList = trainList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TrainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_train, parent, false);
        return new TrainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainViewHolder holder, int position) {
        Train train = trainList.get(position);
        holder.bind(train);
    }

    @Override
    public int getItemCount() {
        return trainList.size();
    }

    public class TrainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewCislo;
        private TextView textViewPoznamka;
        private TextView textViewDatum;

        public TrainViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCislo = itemView.findViewById(R.id.textViewCislo);
            textViewPoznamka = itemView.findViewById(R.id.textViewPoznamka);
            textViewDatum = itemView.findViewById(R.id.textViewDatum);
            itemView.setOnClickListener(this);
        }

        public void bind(Train train) {
            textViewCislo.setText(train.getCislo());
            textViewPoznamka.setText(train.getPoznamka());
            textViewDatum.setText(train.getDatum());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(trainList.get(position).getId());
            }
        }
    }
}
