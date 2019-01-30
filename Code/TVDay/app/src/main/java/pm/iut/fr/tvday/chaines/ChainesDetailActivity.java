package pm.iut.fr.tvday.chaines;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

import pm.iut.fr.tvday.database.FavBDD;
import pm.iut.fr.tvday.notification.NotificationPublisher;
import pm.iut.fr.tvday.R;

import static pm.iut.fr.tvday.notification.Notification.createNotification;
import static pm.iut.fr.tvday.notification.Notification.scheduleNotification;

public class ChainesDetailActivity extends AppCompatActivity {

    FavBDD favBdd = new FavBDD(this);
    Boolean notif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaines_detail);
        Toolbar toolbar =  findViewById(R.id.detail_toolbar_ch);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            notif=false;
            Bundle arguments = new Bundle();
            arguments.putString(ChainesDetailFragment.ARG_ITEM_ID, getIntent().getStringExtra(ChainesDetailFragment.ARG_ITEM_ID));
            ChainesDetailFragment fragment = new ChainesDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.programme_detail_container_ch, fragment).commit();
        }else{
            notif=savedInstanceState.getBoolean("NOTIF_STATE");
        }

        final FloatingActionButton fab = findViewById(R.id.fab_ch);
        if(notif){
            fab.setImageResource(android.R.drawable.star_big_on);
        }else {
            fab.setImageResource(android.R.drawable.star_big_off);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNotification(fab,view);
            }
        });
    }

    private void setNotification(FloatingActionButton fab, View view) {
        if (!notif) {
            Snackbar.make(view, "Notification programmée", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            fab.setImageResource(android.R.drawable.star_big_on);
            TextView programmeChaine = findViewById(R.id.programme_titre_ch);
            CollapsingToolbarLayout programmeDesc =  findViewById(R.id.toolbar_layout_ch);
            Calendar cal = Calendar.getInstance();
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            String hProg = programmeChaine.getText().subSequence(1, 3).toString();
            int duree = hour - Integer.parseInt(hProg);
            duree = duree * 3600000;
            if (duree >= 0) {
                scheduleNotification(createNotification(this), duree,this);
            }
            favBdd.open();
            favBdd.insertFav(programmeChaine.getText().toString(), programmeDesc.getTitle().toString(), 1);
            favBdd.close();
            notif=true;
        }else{
            Snackbar.make(view, "Notification enlevée", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            fab.setImageResource(android.R.drawable.star_big_off);
            Intent intent = new Intent(this, NotificationPublisher.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(),0, intent, 0);
            AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
            assert am != null;
            am.cancel(pendingIntent);
            pendingIntent.cancel();
            notif=false;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, ChainesDetailFragment.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("NOTIF_STATE",notif);
        super.onSaveInstanceState(outState);
    }
}
