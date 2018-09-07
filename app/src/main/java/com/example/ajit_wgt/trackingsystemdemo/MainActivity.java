package com.example.ajit_wgt.trackingsystemdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_login;
    private final static int PERMISSION = 1000;
    private List<AuthUI.IdpConfig> providers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                startActivityForResult(
                        AuthUI.getInstance().createSignInIntentBuilder()
                                .setAvailableProviders(Collections.singletonList(new AuthUI.IdpConfig.EmailBuilder().setAllowNewAccounts(true).build()))
                                .build(),
                        PERMISSION);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PERMISSION) {
            startNewActivity(resultCode, data);
        }
    }

    private void startNewActivity(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            startActivity(new Intent(this, OnlinePeopleActivity.class));
            finish();
        }
        else {
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        }

    }
}
