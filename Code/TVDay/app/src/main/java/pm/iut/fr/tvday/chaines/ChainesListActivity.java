package pm.iut.fr.tvday.chaines;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import pm.iut.fr.tvday.R;
import pm.iut.fr.tvday.masterDetail.Content;
import pm.iut.fr.tvday.programmes.ProgrammeListActivity;

public class ChainesListActivity extends AppCompatActivity{

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaines);
        if (findViewById(R.id.programme_detail_container_ch) != null) {
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.chaines_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        GridLayoutManager glm = new GridLayoutManager(recyclerView.getContext(),3);
        ((RecyclerView) recyclerView).setLayoutManager(glm);

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
                Intent intent = new Intent(this, ProgrammeListActivity.class);
                startActivity(intent);
                return true;
            case R.id.tout:
                return true;
            default :
                return super.onOptionsItemSelected(item);

        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        Content d = new Content("chaine");
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapterChaines(d.ITEMS));
    }


}
