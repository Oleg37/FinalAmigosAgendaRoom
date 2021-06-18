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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import es.miapp.ad.ej2amigosagenda.databinding.FragmentCallHistoryBinding;
import es.miapp.ad.ej2amigosagenda.model.room.pojo.Call;
import es.miapp.ad.ej2amigosagenda.model.room.pojo.Friend;
import es.miapp.ad.ej2amigosagenda.view.adapters.CallAdapter;
import es.miapp.ad.ej2amigosagenda.viewmodel.ViewModel;

public class CallHistory extends Fragment {

    private final List<Call> callList = new ArrayList<>();
    private FragmentCallHistoryBinding b;
    private ViewModel viewModel;
    private Friend friend;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentCallHistoryBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        friend = viewModel.getFriend();

        init();
    }

    private void init() {
        b.tvName.setText(friend.getName());

        requireActivity().runOnUiThread(() -> {
            CallAdapter adapter = new CallAdapter(callList);
            b.rvHistory.setAdapter(adapter);
            b.rvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));

            viewModel.getCallListLiveData(friend.getId()).observe(requireActivity(), numLlamadas -> {
                if (numLlamadas.size() == 0) {
                    b.rvHistory.setVisibility(View.GONE);
                    b.tvNoCalls.setVisibility(View.VISIBLE);
                } else {
                    b.rvHistory.setVisibility(View.VISIBLE);
                    b.tvNoCalls.setVisibility(View.GONE);
                }

                callList.clear();
                callList.addAll(numLlamadas);
                adapter.notifyDataSetChanged();
            });
        });
    }
}