package com.lazylibs.tfw.forward;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.role.RoleManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.lazylibs.tfw.sms.SmsHelper;
import com.lazylibs.tfw.forward.databinding.ActivityMainBinding;
import com.lazylibs.tfw.forward.databinding.TplMsgItemBinding;
import com.lazylibs.tfw.room.SmsContent;
import com.lazylibs.utils.cache.Cache;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    List<SmsContent> smsList = new ArrayList<>();
    RecyclerView.Adapter<SmsItemViewHolder> adapter;

    static class SmsItemViewHolder extends RecyclerView.ViewHolder {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Check to see if SMS is enabled.
        checkForSmsPermission();
        String cache = Cache.get("smsList");
        if (!TextUtils.isEmpty(cache)) {
            smsList = JSON.parseObject(Cache.get("smsList"), new TypeReference<List<SmsContent>>() {
            });
        }
        binding.forwardList.setAdapter(adapter = new RecyclerView.Adapter<SmsItemViewHolder>() {
            @NonNull
            @Override
            public SmsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new SmsItemViewHolder(TplMsgItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull SmsItemViewHolder holder, int position) {
                holder.bind(smsList.get(position));
            }

            @Override
            public int getItemCount() {
                return smsList.size();
            }
        });
//        binding.forwardList.setHasFixedSize(true);
    }

    private static final int REQUEST_ID = 1;

    public void requestRole() {
        RoleManager roleManager = (RoleManager) getSystemService(ROLE_SERVICE);
        Intent intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING);
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                //            if (resultCode == android.app.Activity.RESULT_OK) {
//                // Your app is now the call screening app
//            } else {
//                // Your app is not the call screening app
//            }
            }
        });
        launcher.launch(intent);
    }

    /**
     * Checks whether the app has SMS permission.
     */
    @SuppressLint("NotifyDataSetChanged")
    private void checkForSmsPermission() {requestRole();
        binding.title.setText("权限检测中...");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED ) {
            smsHelper = SmsHelper.create(this, smsContent -> {
                smsList.add(smsContent);
                Cache.put("smsList", JSON.toJSONString(smsList));
                runOnUiThread(() -> {
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                });
            });
            binding.title.setText("监听中...");
            binding.title.setOnClickListener(null);
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                binding.title.setText("无权限...");
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
                    requestPermissions(new String[]{Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_STATE}, 10086);
                } else {

                }
            } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                binding.title.setText("无权限...");
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                    requestPermissions(new String[]{Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_STATE}, 10086);
                } else {

                }
            }
        }
    }

    /**
     * Processes permission request codes.
     *
     * @param requestCode  The request code passed in requestPermissions()
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // For the requestCode, check if permission was granted or not.
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10086) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted. Enable sms button.
                smsHelper = SmsHelper.create(this, smsContent -> {
                    smsList.add(smsContent);
                    Cache.put("smsList", JSON.toJSONString(smsList));
                    runOnUiThread(() -> {
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                    });
                });
                binding.title.setText("监听中...");
                binding.title.setOnClickListener(null);
            } else {
                binding.title.setText("权限被拒...点击重新获取");
                binding.title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkForSmsPermission();
                    }
                });
            }
        }
    }

    SmsHelper smsHelper;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SmsHelper.destroy(smsHelper);
    }
}