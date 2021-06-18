/*
 * Copyright (c) 2021. ArseneLupin0.
 *
 * Licensed under the GNU General Public License v3.0
 *
 * https://www.gnu.org/licenses/gpl-3.0.html
 *
 * Permissions of this strong copyleft license are conditioned on making available complete source
 * code of licensed works and modifications, which include larger works using a licensed work,
 * under the same license. Copyright and license notices must be preserved. Contributors provide
 * an express grant of patent rights.
 */

package es.miapp.ad.ej2amigosagenda.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import es.miapp.ad.ej2amigosagenda.databinding.ItemFriendBinding;
import es.miapp.ad.ej2amigosagenda.model.room.pojo.NumCalls;
import es.miapp.ad.ej2amigosagenda.view.listeners.InterfaceListenerFriend;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {

    private final List<NumCalls> numCallsList;
    private final InterfaceListenerFriend listener;

    public FriendAdapter(List<NumCalls> numCallsList, InterfaceListenerFriend listener) {
        this.numCallsList = numCallsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendViewHolder(ItemFriendBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        holder.init(numCallsList.get(position));

        holder.b.btEdit.setOnClickListener(v -> listener.onClickEdit(numCallsList.get(position).getFriend()));

        holder.b.btDelete.setOnClickListener(v -> listener.onClickDelete(numCallsList.get(position).getFriend()));

        holder.itemView.setOnClickListener(v -> listener.onClickFriend(numCallsList.get(position).getFriend()));
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (numCallsList != null) {
            size = numCallsList.size();
        }
        return size;
    }

    public static class FriendViewHolder extends RecyclerView.ViewHolder {

        public ItemFriendBinding b;

        public FriendViewHolder(@NonNull ItemFriendBinding b) {
            super(b.getRoot());
            this.b = b;
        }

        public void init(NumCalls numCalls) {
            b.tvName.setText(numCalls.getFriend().getName());
            b.tvPhone.setText(numCalls.getFriend().getPhNumber());
            if (numCalls.getFriend().getDOBirth() == 0) {
                b.tvDOFBirth.setText("No hay fecha");
            } else {
                b.tvDOFBirth.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(numCalls.getFriend().getDOBirth()));
            }
            b.tvCalls.setText(String.format("%s", numCalls.getTimes()));
        }
    }
}