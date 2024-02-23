package com.lazylibs.tfw.sms;

import androidx.recyclerview.widget.RecyclerView;

import com.lazylibs.tfw.forward.databinding.TplMsgItemBinding;
import com.lazylibs.tfw.room.SmsContent;

public class SmsItemViewHolder extends RecyclerView.ViewHolder {
    TplMsgItemBinding binding;

    public SmsItemViewHolder(TplMsgItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    void bind(SmsContent smsContent) {
        binding.tvSender.setText(smsContent.sender);
        binding.tvContent.setText(smsContent.body);
    }

}
