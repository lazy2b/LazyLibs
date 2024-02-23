package com.lazylibs.tfw.forward.ui.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lazylibs.tfw.forward.databinding.FragmentSmsBinding;
import com.lazylibs.tfw.room.SmsContent;
import com.lazylibs.tfw.sms.SmsItemViewHolder;
import com.lazylibs.utils.cache.Cache;

import java.util.ArrayList;
import java.util.List;

public class SmsFragment extends Fragment {

    private FragmentSmsBinding binding;

    List<SmsContent> smsList = new ArrayList<>();
    RecyclerView.Adapter<SmsItemViewHolder> adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentSmsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String cache = Cache.get("smsList");
        if (!TextUtils.isEmpty(cache)) {
            smsList = JSON.parseObject(Cache.get("smsList"), new TypeReference<List<SmsContent>>() {
            });
        }
//        binding.smsList.setAdapter(adapter = new RecyclerView.Adapter<SmsItemViewHolder>() {
//            @NonNull
//            @Override
//            public SmsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                return new SmsItemViewHolder(TplMsgItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
//            }
//
//            @Override
//            public void onBindViewHolder(@NonNull SmsItemViewHolder holder, int position) {
//                holder.bind(smsList.get(position));
//            }
//
//            @Override
//            public int getItemCount() {
//                return smsList.size();
//            }
//        });
//        binding.forwardList.setHasFixedSize(true);


//        smsList.add(smsContent);
//        Cache.put("smsList", JSON.toJSONString(smsList));
//        runOnUiThread(() -> {
//            if (adapter != null) {
//                adapter.notifyDataSetChanged();
//            }
//        });

//        smsList.add(smsContent);
//        Cache.put("smsList", JSON.toJSONString(smsList));
//        runOnUiThread(() -> {
//            if (adapter != null) {
//                adapter.notifyDataSetChanged();
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}