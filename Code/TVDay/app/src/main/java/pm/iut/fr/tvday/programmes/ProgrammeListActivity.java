package pm.iut.fr.tvday.programmes;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import pm.iut.fr.tvday.R;
import pm.iut.fr.tvday.chaines.ChainesListActivity;
import pm.iut.fr.tvday.masterDetail.Content;

public class ProgrammeListActivity extends AppCompatActivity{

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programme_list);



        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if(!isConnected){
            TextView tv = findViewById(R.id.text_no_co);
            tv.setText(R.string.info);
        }else {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.setTitle(getTitle());

            if (findViewById(R.id.programme_detail_container) != null) {
                mTwoPane = true;
            }

            final View recyclerView = findViewById(R.id.programme_list);
            assert recyclerView != null;
            setupRecyclerView((RecyclerView) recyclerView);

            FloatingActionButton fab = findViewById(R.id.actualise);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setupRecyclerView((RecyclerView) recyclerView);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.live:
                return true;
            case R.id.tout:
                Intent intent = new Intent(this, ChainesListActivity.class);
                startActivity(intent);
                return true;
            default :
                return super.onOptionsItemSelected(item);

        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        Content d = new Content("live");
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, d.ITEMS, mTwoPane));
    }

}
