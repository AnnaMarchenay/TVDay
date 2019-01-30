package pm.iut.fr.tvday.programmeDUneChaine;

import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import pm.iut.fr.tvday.R;
import pm.iut.fr.tvday.masterDetail.Content;

public class ProgrammeChaineActivity extends AppCompatActivity {
    private boolean mTwoPane;
    public static final String CHAINE = "nom_chaine";
    String nomChaine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programme_chaine);
        if (findViewById(R.id.programme_detail_container_ch) != null) {
            mTwoPane = true;
        }

        nomChaine = getIntent().getExtras().getString(ProgrammeChaineActivity.CHAINE);

        Toolbar toolbar =  findViewById(R.id.toolbar_pch);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View recyclerView = findViewById(R.id.programme_chaine_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        setTitle(nomChaine);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        Content d = new Content(nomChaine);
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapterProgrammeChaine(this, d.ITEMS, mTwoPane));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
