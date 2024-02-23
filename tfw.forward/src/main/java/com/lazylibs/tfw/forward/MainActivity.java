package com.lazylibs.tfw.forward;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.role.RoleManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.lazylibs.tfw.forward.databinding.TplTfwMainBinding;
import com.lazylibs.tfw.sms.SmsHelper;
import com.lazylibs.tfw.forward.databinding.ActivityMainBinding;
import com.lazylibs.tfw.forward.databinding.TplMsgItemBinding;
import com.lazylibs.tfw.room.SmsContent;
import com.lazylibs.tfw.sms.SmsItemViewHolder;
import com.lazylibs.utils.cache.Cache;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private void v2TelFw() {
        TplTfwMainBinding tfwMainBinding = TplTfwMainBinding.inflate(getLayoutInflater());
        binding.vfMain.addView(tfwMainBinding.getRoot());
        binding.vfMain.showNext();
    }

    private void v2UnPermission() {
        binding.tvNoPermission.setOnClickListener(view -> {
            checkForSmsPermission();
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        roleManager = (RoleManager) getSystemService(ROLE_SERVICE);
        callScreeningLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == android.app.Activity.RESULT_OK) {
                v2TelFw();
            } else {
                Toast.makeText(MainActivity.this, "权限获取失败！", Toast.LENGTH_SHORT).show();
                v2UnPermission();
            }
        });
        settingsLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "权限获取失败！", Toast.LENGTH_SHORT).show();
                v2UnPermission();
            } else {
                checkForCallScreeningPermission();
            }
        });
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            checkForSmsPermission();
            return;
        }
        RoleManager roleManager = (RoleManager) getSystemService(ROLE_SERVICE);
        if (!roleManager.isRoleHeld(RoleManager.ROLE_CALL_SCREENING)) {
            checkForCallScreeningPermission();
            return;
        }
        callScreeningLauncher = null;
        settingsLauncher = null;
        v2TelFw();
    }

    RoleManager roleManager;
    ActivityResultLauncher<Intent> callScreeningLauncher, settingsLauncher;

    private void checkForCallScreeningPermission() {
        if (roleManager.isRoleHeld(RoleManager.ROLE_CALL_SCREENING)) {
            v2TelFw();
        } else {
            callScreeningLauncher.launch(roleManager.createRequestRoleIntent(RoleManager.ROLE_CALL_SCREENING));
        }
    }

    public static final int REQUEST_READ_SMS_PERMISSION = 10086;

    @SuppressLint("NotifyDataSetChanged")
    private void checkForSmsPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
            checkForCallScreeningPermission();//  替换电话接听软件
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_SMS)) {
                // TODO 弹窗解释一下子再获取
                requestPermissions(new String[]{Manifest.permission.READ_SMS}, REQUEST_READ_SMS_PERMISSION);
            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("权限设置").setMessage("应用缺乏必要的权限，是否前往手动授予该权限？").setPositiveButton("前往", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        settingsLauncher.launch(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName())));
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "权限获取失败！", Toast.LENGTH_SHORT).show();
                        v2UnPermission();
                    }
                }).create();
                alertDialog.show();
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // For the requestCode, check if permission was granted or not.
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_SMS_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkForCallScreeningPermission();
            } else {
                Toast.makeText(this, "权限获取失败！", Toast.LENGTH_SHORT).show();
                v2UnPermission();
            }
        }
    }
}