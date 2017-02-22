package com.example.thanhnguyen.firebase_dynamic_links;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitationResult;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Build GoogleApiClient with AppInvite API for receiving deep links
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(AppInvite.API)
                .build();

        // Check if this app was launched from a deep link. Setting autoLaunchDeepLink to true
        // would automatically launch the deep link if one is found.
        boolean autoLaunchDeepLink = false;
        AppInvite.AppInviteApi.getInvitation(mGoogleApiClient, this, autoLaunchDeepLink)
                .setResultCallback(new ResultCallback<AppInviteInvitationResult>() {
                    @Override
                    public void onResult(@NonNull AppInviteInvitationResult result) {
                        if (result.getStatus().isSuccess()) {
                            // Extract deep link from Intent
                            Intent intent = result.getInvitationIntent();
                            String deepLink = AppInviteReferral.getDeepLink(intent);

                            // Handle the deep link.
                            Log.d("Deep link : ", deepLink);

                            if (!TextUtils.isEmpty(deepLink)) {
                                startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                            } else {
                                Log.d("getInvitation : ", "no deep link found.");
                            }
                        } else {
                            Log.d("getInvitation : ", "no deep link found.");
                        }
                    }
                });


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
