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

package es.miapp.ad.ej2amigosagenda.view.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import es.miapp.ad.ej2amigosagenda.R;
import es.miapp.ad.ej2amigosagenda.databinding.FragmentFriendListBinding;
import es.miapp.ad.ej2amigosagenda.model.room.pojo.Friend;
import es.miapp.ad.ej2amigosagenda.model.room.pojo.NumCalls;
import es.miapp.ad.ej2amigosagenda.util.Permissions;
import es.miapp.ad.ej2amigosagenda.view.adapters.FriendAdapter;
import es.miapp.ad.ej2amigosagenda.view.listeners.InterfaceListenerFriend;
import es.miapp.ad.ej2amigosagenda.viewmodel.ViewModel;

public class FriendList extends Fragment implements InterfaceListenerFriend {

    private final List<NumCalls> friendList = new ArrayList<>();
    private FragmentFriendListBinding b;
    private ViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentFriendListBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        Permissions permissions = new Permissions(requireContext(), requireActivity(), b);

        if (permissions.hasAllPerms(permissions.getPERMISSIONS())) {
            permissions.permissionsApp();
            return;
        }

        init();
    }

    private void init() {

        FriendAdapter adapter = new FriendAdapter(friendList, this);

        b.rvContacts.setAdapter(adapter);
        b.rvContacts.setLayoutManager(new LinearLayoutManager(getActivity()));

        viewModel.getNumCallsListLiveData().observe(requireActivity(), listado -> {
            friendList.clear();
            friendList.addAll(listado);
            adapter.notifyDataSetChanged();
        });

        b.fabDeleteAll.setOnClickListener(v -> {
            if (!friendList.isEmpty()) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

                builder.setTitle("Eliminar todos los contactos").setMessage("¿Seguro que quieres eliminar toda tu " +
                        "lista de amigos existentes?, perderás las llamadas asignadas a ese contacto también." +
                        "\n\n¿Estás seguro?");

                builder.setPositiveButton("Sí", (dialog, id) -> viewModel.deleteAll());

                builder.setNegativeButton("No", (dialog, id) -> dialog.dismiss());

                AlertDialog alert = builder.create();
                alert.show();

                Button bTNegative = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                Button bTPositive = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                bTNegative.setTextColor(Color.RED);
                bTPositive.setTextColor(Color.GREEN);
            } else {
                Snackbar.make(b.getRoot().getRootView(), "¡Agrega a algún amigo primero!", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClickFriend(Friend friend) {
        viewModel.setFriend(friend);
        NavHostFragment.findNavController(this).navigate(R.id.action_nav_listFragment_to_callHistory);
    }

    @Override
    public void onClickDelete(Friend friend) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext());

        builder.setTitle("Eliminar contacto").setMessage("¿Seguro que quieres eliminar este contacto?");

        builder.setPositiveButton("Aceptar", (dialog, id) -> viewModel.delete(friend));

        builder.setNegativeButton("Cancelar", (dialog, id) -> dialog.dismiss());

        AlertDialog alert = builder.create();
        alert.show();

        Button bTNegative = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button bTPositive = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        bTNegative.setTextColor(Color.RED);
        bTPositive.setTextColor(Color.GREEN);
    }

    @Override
    public void onClickEdit(Friend friend) {
        viewModel.setFriend(friend);
        NavHostFragment.findNavController(this).navigate(R.id.action_nav_listFragment_to_friendEdit);
    }
}