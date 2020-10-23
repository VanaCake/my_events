package com.example.my_events;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EventListAdaptor extends RecyclerView.Adapter<EventListAdaptor.EventViewHolder> {
    private ArrayList<Event> events;
    public EventListAdaptor(ArrayList<Event> events){
        this.events = events;
    }
    @NonNull
    @Override
    public EventListAdaptor.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item, parent, false);
        return new EventListAdaptor.EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.date.setText(events.get(position).getDate());
        holder.description.setText(events.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        TextView date, description;
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.dateTV);
            description = itemView.findViewById(R.id.descriptionTV);
        }
    }
}


