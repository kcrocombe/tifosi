package uk.co.pegortech.tifosi;

/*
 * Copyright (c) 2015. Pegor Technical Services Ltd.
 */

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import uk.co.pegortech.tifosi.database.DbHelper;
import uk.co.pegortech.tifosi.database.Persistable;
import uk.co.pegortech.tifosi.database.Persister;
import uk.co.pegortech.tifosi.database.RideServerContract;
import uk.co.pegortech.tifosi.database.Table;

//Todo : Generally Need to consider where the various classes/interfaces sit in terms of the package structure
//Todo : Need to review the visibility of the various methods onece the package structure is established to make sure everything is as protected as possible.

//ToDo : Need to start Documenting!!

public class DiaryActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);


        // ToDo : URGENT - Get it into Git HUb!

        // Todo : not sure the structure of Persister as a container class is sufficiently rich.
        // Do we need to be able to add in the set of views that are possible on teh container??
        // eg Iterators that will return all the members of the Container??


        //Todo : To do need to abstract the Content adapters to Iterators or some such and MUST get that working ok
        // with the concept of a Dataloader to keep everything off the UI thread.

        Persister pageGrps = new Persister(this, RideServerContract.Tables.PaceGroups.TABLENAME);

        Persister clubs = new Persister(this, RideServerContract.Tables.Clubs.TABLENAME);

        Persister members = new Persister(this, RideServerContract.Tables.Members.TABLENAME);


        PaceGroup pg1 = new PaceGroup(pageGrps,"PG1", "Third PG", 10, "Green");

        PaceGroup pg2 = new PaceGroup(pageGrps,"PG2", "Third PG", 20, "Red");







        Log.d("KJC", "pg1 Prior to insert= " + pg1.toString());
        Integer pkAdded = pg1.post();
        Log.d("KJC", "pg1 post insert= " + pg1.toString());

        Log.d("KJC", "pg2 Prior to insert= " + pg2.toString());
        pkAdded = pg2.post();
        Log.d("KJC", "pg2 post insert= " + pg2.toString());




        ArrayList founder = new ArrayList<Member1>(1);

        Member1 mbr= new Member1( members, "james",null);

        founder.add(mbr);

        Club clb = new Club(clubs,"ICC",founder);

        Log.d("KJC", "clb Prior to insert= " + clb.toString());
        Log.d("KJC", "mbr Prior to insert= " + mbr.toString());

        pkAdded = clb.post();
        Log.d("KJC", "clb Post insert= " + clb.toString());

        //pkAdded = mbr.post();
        Log.d("KJC", "mbr Post to insert= " + mbr.toString());



        //pkAdded = mbr.post();
        //Log.d("KJC", "mbr Post to insert= " + mbr.toString());


/*
        pg1.setDisplayColour("Blue");
        pg1Persister.update();
        Log.d("KJC", "pg1 post update= " + pg1.toString());


        pg1Persister.delete();
        Log.d("KJC", "pg1 post delete= " + pg1.toString())
        PaceGroup pg4 = (PaceGroup) pg2Persister.pKfetch(new Integer(10));

        Log.d("KJC", "pg4 post query= " + pg4.toString());

*/


        //Todo ; this needs to come out



        // NB This does all the work on the UI thread, so is not
        // recommended for larger datasets.

        PaceGroupAdapter pgAdapter = new PaceGroupAdapter(this,R.layout.pace_group_layout);

        ListView listview = (ListView) findViewById(R.id.list);

        listview.setAdapter(pgAdapter);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diary, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
