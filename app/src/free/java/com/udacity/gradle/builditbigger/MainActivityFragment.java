package com.udacity.gradle.builditbigger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mylibrary.JokeActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivityFragment extends Fragment {

    FragmentActivity con;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.adView)
    AdView mAdView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        con = getActivity();
        ButterKnife.bind(this, view);

        ((MainActivity) con).setSupportActionBar(toolbar);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        return view;
    }

    @OnClick(R.id.main_b_show_joke)
    void showNewJoke() {
        final AsyncTask<EndpointsAsyncTask.GotJokeCallback, Void, String> processGetJoke = new EndpointsAsyncTask();

        ProgressBar pb = new ProgressBar(con);
        pb.setIndeterminate(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setMessage(R.string.loading_joke_wait_titile)
                .setView(pb)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        processGetJoke.cancel(true);
                        dialog.dismiss();
                    }
                })
                .setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.show();

        processGetJoke.execute(new EndpointsAsyncTask.GotJokeCallback() {
            @Override
            public void done(final String result, boolean error) {
                dialog.dismiss();
                if (error) {
                    Log.e("error text", result);
                    Toast.makeText(con, result, Toast.LENGTH_SHORT).show();
                } else {
                    showJokeOnUI(result);
                }
            }
        });
    }

    private void showJokeOnUI(String result) {
        Intent i = new Intent(con, JokeActivity.class);
        i.putExtra(JokeActivity.EXTRA_JOKE, result);
        startActivity(i);
    }

}
