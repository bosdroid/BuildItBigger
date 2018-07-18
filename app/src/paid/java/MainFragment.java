package com.udacity.gradle.builditbigger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainFragment extends Fragment {

    FragmentActivity con;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        con = getActivity();
        ButterKnife.bind(this, view);

        ((MainActivity) con).setSupportActionBar(toolbar);

        return view;
    }

    @OnClick(R.id.main_b_show_joke)
    void showNewJoke() {
        final AsyncTask<EndpointsAsyncTask.GotJokeCallback, Void, String> processGetJoke = new EndpointsAsyncTask();

        ProgressBar pb = new ProgressBar(con);
        pb.setIndeterminate(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(con);
        builder.setMessage("Loading joke. Please wait...")
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
            public void done(String result, boolean error) {
                dialog.dismiss();
                if (error) {
                    Log.e("error text", result);
                    Toast.makeText(con, result, Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(con, JokeActivity.class);
                    i.putExtra(JokeActivity.EXTRA_JOKE, result);
                    startActivity(i);
                }
            }
        });
    }

}

