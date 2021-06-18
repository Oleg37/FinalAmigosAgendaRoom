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

import es.miapp.ad.ej2amigosagenda.databinding.ItemContactBinding;
import es.miapp.ad.ej2amigosagenda.model.room.pojo.Call;

public class CallAdapter extends RecyclerView.Adapter<CallAdapter.CallViewHolder> {

    private final List<Call> callList;

    public CallAdapter(List<Call> list) {
        this.callList = list;
    }

    @NonNull
    @Override
    public CallViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CallViewHolder(ItemContactBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull CallViewHolder holder, int position) {
        holder.init(callList.get(position));
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (callList != null) {
            size = callList.size();
        }
        return size;
    }

    public static class CallViewHolder extends RecyclerView.ViewHolder {

        public ItemContactBinding b;

        public CallViewHolder(@NonNull ItemContactBinding b) {
            super(b.getRoot());
            this.b = b;
        }

        public void init(Call call) {
            b.tvNameDate.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(call.getCallDate()));
            b.tvHourPhone.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            b.tvHourPhone.setText(new SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(call.getCallDate()));
        }
    }
}