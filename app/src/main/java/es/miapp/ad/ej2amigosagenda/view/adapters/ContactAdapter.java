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

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import es.miapp.ad.ej2amigosagenda.databinding.ItemContactBinding;
import es.miapp.ad.ej2amigosagenda.model.room.pojo.Friend;
import es.miapp.ad.ej2amigosagenda.util.ScheduleThread;
import es.miapp.ad.ej2amigosagenda.view.listeners.InterfaceListenerContact;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private final ContentResolver cr;
    private final InterfaceListenerContact listener;
    private final Cursor cursor;

    public ContactAdapter(Context context, Cursor cursor, InterfaceListenerContact listener) {
        this.cursor = cursor;
        this.listener = listener;
        cr = context.getContentResolver();
    }

    @NotNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ContactViewHolder(ItemContactBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.init(position);
        holder.b.getRoot().setOnClickListener(v -> listener.onClickContact(holder.addAmigo(), holder.phones));
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        public ItemContactBinding b;
        public List<String> phones = new ArrayList<>();
        private String id;

        public ContactViewHolder(@NonNull ItemContactBinding b) {
            super(b.getRoot());
            this.b = b;
        }

        public void init(int position) {
            cursor.moveToPosition(position);

            b.tvNameDate.setText(cursor.getString(cursor.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME)));
            id = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.Contacts._ID));

            Cursor cursor = cr
                    .query(ContactsContract.Contacts.CONTENT_URI,
                            null,
                            null,
                            null,
                            null);

            if (cursor == null) {
                return;
            }

            Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID + " = " + id,
                    null,
                    null);

            if (phones == null) {
                return;
            }

            ScheduleThread.threadExecutorPool.execute(() -> {
                while (phones.moveToNext()) {
                    String phoneNumber = phones.getString(
                            phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    this.phones.add(phoneNumber);
                    if (this.phones.size() > 1) {
                        b.tvHourPhone.setText("Hay más de un teléfono");
                    } else if (this.phones.size() == 0) {
                        b.tvHourPhone.setText("No hay números disponibles");
                    } else {
                        b.tvHourPhone.setText(phoneNumber);
                    }
                }
                phones.close();
            });
            cursor.close();
        }

        public Friend addAmigo() {
            return new Friend(Long.parseLong(id), b.tvNameDate.getText().toString(), null, 0);
        }
    }
}