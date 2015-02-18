package uk.co.pegortech.tifosi.database;
/*
 * Copyright (c) 2015. Pegor Technical Services Ltd.
 */


/**
 * Created by kevin on 09/02/2015.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.widget.ListAdapter;

import java.sql.Timestamp;

import uk.co.pegortech.tifosi.PaceGroup;

/* The SQLiteOpenHelper takes care of opening the database if it exists, creating it if it does not,
 * and upgrading it as necessary. Should be extended for use and, at minimum, the following
 * methods overridden : onCreate() , onUpgrade
 */

public class DbHelper extends SQLiteOpenHelper {
    //Create a Tag for use in logging identifying the source class
    private static final String TAG = DbHelper.class.getSimpleName();

    public DbHelper(Context context) {
        super(context, RideServerContract.DB_NAME, null, RideServerContract.DB_VERSION);
    }

    // Called only once first time we create the database
    @Override
    public void onCreate(SQLiteDatabase db) {

        /* We are relying on the database to generate primary keys for us.
         *
         * Whenever we attempt to insert no or NULL primary key, it will automatically
         * generate one for us. ( an incrementing number.)
         *
         * For this behaviour to work, the PK need to be of type integer ( not int )
         */

        //ToDo : URGENT !! Need to sort out the New table formats to permit use of Sequences

        String ridesSql = String
                .format("create table %s ("  +
                                "  %s %s %s" +
                                ", %s %s %s" +
                                ", %s %s %s" +
                                ", %s %s %s" +
                                ", %s %s %s" +
                                ", %s %s %s" +
                                ", %s %s %s" +
                                ", %s %s %s" +
                                ", %s %s %s" +
                                ", %s %s %s" +
                                ", %s %s %s" +
                                ", primary key ( %s ))",
                        RideServerContract.Tables.Rides.TABLENAME
                            ,RideServerContract.Tables.Records.Columns.ID, RideServerContract.SQLiteType.INT,RideServerContract.SQLiteType.NOTNULL
                            ,RideServerContract.Tables.Rides.Columns.SCHEDULED_DATE, RideServerContract.SQLiteType.TEXT, RideServerContract.SQLiteType.NOTNULL
                            ,RideServerContract.Tables.Rides.Columns.START_TIME, RideServerContract.SQLiteType.TEXT, RideServerContract.SQLiteType.NOTNULL
                            ,RideServerContract.Tables.Rides.Columns.START_POINT, RideServerContract.SQLiteType.TEXT, RideServerContract.SQLiteType.NOTNULL
                            ,RideServerContract.Tables.Rides.Columns.ROUTE_ID, RideServerContract.SQLiteType.INT, RideServerContract.SQLiteType.NOTNULL
                            ,RideServerContract.Tables.Rides.Columns.PACE_GROUP_ID, RideServerContract.SQLiteType.INT, RideServerContract.SQLiteType.NOTNULL
                            ,RideServerContract.Tables.Rides.Columns.TARGET_PACE, RideServerContract.SQLiteType.INT, RideServerContract.SQLiteType.NOTNULL
                            ,RideServerContract.Tables.Rides.Columns.GENDER_SPECIFICITY, RideServerContract.SQLiteType.TEXT, RideServerContract.SQLiteType.NOTNULL
                            ,RideServerContract.Tables.Rides.Columns.FOCUS, RideServerContract.SQLiteType.TEXT, RideServerContract.SQLiteType.NOTNULL
                            ,RideServerContract.Tables.Records.Columns.CREATION_TIMESTAMP, RideServerContract.SQLiteType.TEXT, RideServerContract.SQLiteType.NOTNULL
                            ,RideServerContract.Tables.Records.Columns.MODIFICATION_TIMESTAMP, RideServerContract.SQLiteType.TEXT, RideServerContract.SQLiteType.NOTNULL
                        ,RideServerContract.Tables.Records.Columns.ID
                );
//todo : Need to sort out whether there ought to be a ONCONFLICT clause on the table.

//todo : Need to enable the unique constraints once testing is done
        String paceGroupSql = String
                .format("create table %s ("  +
                                "  %s %s primary key autoincrement %s " +
                                ", %s %s %s" +
                                ", %s %s %s" +
                                ", %s %s %s" +
                                ", %s %s %s" +
                                ", %s %s %s" +
                                ", %s %s %s" +
                               // ", primary key ( %s )" +
                                //", unique ( %s) " +
                        ")",
                        RideServerContract.Tables.PaceGroups.TABLENAME
                            ,RideServerContract.Tables.Records.Columns.ID, RideServerContract.SQLiteType.INT, RideServerContract.SQLiteType.NOTNULL
                            ,RideServerContract.Tables.PaceGroups.Columns.NAME,RideServerContract.SQLiteType.TEXT, RideServerContract.SQLiteType.NOTNULL
                            ,RideServerContract.Tables.PaceGroups.Columns.TYPICAL_PACE, RideServerContract.SQLiteType.INT, RideServerContract.SQLiteType.NOTNULL
                            ,RideServerContract.Tables.PaceGroups.Columns.DESCRIPTION, RideServerContract.SQLiteType.TEXT, RideServerContract.SQLiteType.NOTNULL
                            ,RideServerContract.Tables.PaceGroups.Columns.DISPLAYCOLOUR, RideServerContract.SQLiteType.TEXT, RideServerContract.SQLiteType.NOTNULL
                            ,RideServerContract.Tables.Records.Columns.CREATION_TIMESTAMP, RideServerContract.SQLiteType.TEXT, RideServerContract.SQLiteType.NOTNULL
                            ,RideServerContract.Tables.Records.Columns.MODIFICATION_TIMESTAMP, RideServerContract.SQLiteType.TEXT, RideServerContract.SQLiteType.NOTNULL
                        //,RideServerContract.Tables.Records.Columns.ID
                        //,RideServerContract.Tables.PaceGroups.Columns.NAME
                );

        //todo : Need to enable foreign key constraints once testing has progressed.
        String routeSql = String
                .format("create table %s ("  +
                                "  %s %s primary key autoincrement %s" +
                                ", %s %s %s" +
                                ", %s %s %s" +
                                ", %s %s %s" +
                                ", %s %s %s" +
                                ", %s %s %s" +
                                ", %s %s %s" +
                           //     ", primary key ( %s )" +
                           //     ", unique ( %s) +
                            ")",
                        RideServerContract.Tables.Routes.TABLENAME
                            ,RideServerContract.Tables.Records.Columns.ID, RideServerContract.SQLiteType.INT, RideServerContract.SQLiteType.NOTNULL
                            ,RideServerContract.Tables.Routes.Columns.NAME,RideServerContract.SQLiteType.TEXT, RideServerContract.SQLiteType.NOTNULL
                            ,RideServerContract.Tables.Routes.Columns.LENGTH, RideServerContract.SQLiteType.INT, RideServerContract.SQLiteType.NOTNULL
                            ,RideServerContract.Tables.Routes.Columns.CLIMBAGE, RideServerContract.SQLiteType.TEXT, RideServerContract.SQLiteType.NOTNULL
                            ,RideServerContract.Tables.Routes.Columns.DEFAULT_STARTPOINT, RideServerContract.SQLiteType.TEXT, RideServerContract.SQLiteType.NOTNULL
                            ,RideServerContract.Tables.Records.Columns.CREATION_TIMESTAMP, RideServerContract.SQLiteType.TEXT, RideServerContract.SQLiteType.NOTNULL
                            ,RideServerContract.Tables.Records.Columns.MODIFICATION_TIMESTAMP, RideServerContract.SQLiteType.TEXT, RideServerContract.SQLiteType.NOTNULL
                      //  ,RideServerContract.Tables.Records.Columns.ID
                     //   ,RideServerContract.Tables.Routes.Columns.NAME
                );

        String clubSql = String
                .format("create table %s ("  +
                                "  %s %s %s" +
                                ", %s %s %s" +
                                ", %s %s %s" +
                                ", %s %s %s" +
                                ", %s %s %s" +
                                ", primary key ( %s )" +
                                //     ", unique ( %s) +
                                ")",
                        RideServerContract.Tables.Clubs.TABLENAME
                        ,RideServerContract.Tables.Records.Columns.ID, RideServerContract.SQLiteType.INT, RideServerContract.SQLiteType.NOTNULL
                        ,RideServerContract.Tables.Clubs.Columns.NAME,RideServerContract.SQLiteType.TEXT, RideServerContract.SQLiteType.NOTNULL
                        ,RideServerContract.Tables.Clubs.Columns.FOUNDER, RideServerContract.SQLiteType.INT, RideServerContract.SQLiteType.NOTNULL
                        ,RideServerContract.Tables.Records.Columns.CREATION_TIMESTAMP, RideServerContract.SQLiteType.TEXT, RideServerContract.SQLiteType.NOTNULL
                        ,RideServerContract.Tables.Records.Columns.MODIFICATION_TIMESTAMP, RideServerContract.SQLiteType.TEXT, RideServerContract.SQLiteType.NOTNULL
                        ,RideServerContract.Tables.Records.Columns.ID
                        //   ,RideServerContract.Tables.Clubs.Columns.NAME
                );

        String memberSql = String
                .format("create table %s ("  +
                                "  %s %s %s" +
                                ", %s %s %s" +
                                ", %s %s %s" +
                                ", %s %s %s" +
                                ", %s %s %s" +
                                ", primary key ( %s )" +
                                //     ", unique ( %s) +
                                ")",
                        RideServerContract.Tables.Members.TABLENAME
                        ,RideServerContract.Tables.Records.Columns.ID, RideServerContract.SQLiteType.INT, RideServerContract.SQLiteType.NOTNULL
                        ,RideServerContract.Tables.Members.Columns.NAME,RideServerContract.SQLiteType.TEXT, RideServerContract.SQLiteType.NOTNULL
                        ,RideServerContract.Tables.Members.Columns.CLUB,RideServerContract.SQLiteType.INT, RideServerContract.SQLiteType.NOTNULL
                        ,RideServerContract.Tables.Records.Columns.CREATION_TIMESTAMP, RideServerContract.SQLiteType.TEXT, RideServerContract.SQLiteType.NOTNULL
                        ,RideServerContract.Tables.Records.Columns.MODIFICATION_TIMESTAMP, RideServerContract.SQLiteType.TEXT, RideServerContract.SQLiteType.NOTNULL
                        ,RideServerContract.Tables.Records.Columns.ID
                        //   ,RideServerContract.Tables.Clubs.Columns.NAME
                );

        //
        String [] sqlToSubmit = {ridesSql, paceGroupSql, routeSql, clubSql, memberSql};

        for( String sql : sqlToSubmit) {
            Log.d(TAG, "onCreate with SQL: " + sql);
            db.execSQL(sql);
        }

        /* add just one row for testing. */
        ///PaceGroup pg = new PaceGroup("PG1", "First PG", 10, "Red");
        //addPaceGroup(pg);
        //Log.d(TAG, "pg =" + pg.toString());

    }

    // Gets called whenever existing version != new version, i.e. schema changed
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Typically you do ALTER TABLE ...

        // Here we just drop and recreate the tables.
        //toDo ; Need a proper onUpgrade in here. THis just drops and recreated the tables
        String ridesSql = String.format("drop table if exists %s ", RideServerContract.Tables.Rides.TABLENAME);
        String routesSql = String.format("drop table if exists %s ", RideServerContract.Tables.Routes.TABLENAME);
        String paceGroupsSql = String.format("drop table if exists %s ", RideServerContract.Tables.PaceGroups.TABLENAME);

        String clubsSql = String.format("drop table if exists %s ", RideServerContract.Tables.Clubs.TABLENAME);
        String membersSql = String.format("drop table if exists %s ", RideServerContract.Tables.Members.TABLENAME);


        String [] sqlToSubmit = {ridesSql, paceGroupsSql, routesSql, clubsSql, membersSql};

        for( String sql : sqlToSubmit) {
            Log.d(TAG, "onUpgrade with SQL: " + sql);
            db.execSQL(sql);
        }

        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //toDo ; Need a proper onDowngrade in here. THis just drops and recreated the tables
        String ridesSql = String.format("drop table if exists %s ", RideServerContract.Tables.Rides.TABLENAME);
        String routesSql = String.format("drop table if exists %s ", RideServerContract.Tables.Routes.TABLENAME);
        String paceGroupsSql = String.format("drop table if exists %s ", RideServerContract.Tables.PaceGroups.TABLENAME);
        String clubsSql = String.format("drop table if exists %s ", RideServerContract.Tables.Clubs.TABLENAME);
        String membersSql = String.format("drop table if exists %s ", RideServerContract.Tables.Members.TABLENAME);

        String [] sqlToSubmit = {ridesSql, paceGroupsSql, routesSql, clubsSql, membersSql};

        for( String sql : sqlToSubmit) {
            Log.d(TAG, "onDownGrade with SQL: " + sql);
            db.execSQL(sql);
        }

        onCreate(db);
    }


    private void putCommonRecordValues(ContentValues values, Record rec){

        // If we attempt to insert with a NULL ID, then the system will autogenerate a new key value
        values.put(RideServerContract.Tables.Records.Columns.ID, rec.getId());
        values.put(RideServerContract.Tables.Records.Columns.CREATION_TIMESTAMP, rec.getCreationTimestamp().toString());
        values.put(RideServerContract.Tables.Records.Columns.MODIFICATION_TIMESTAMP, rec.getModificationTimestamp().toString());
    }

    private void putPaceGroupValues(ContentValues values, PaceGroup paceGrp) {

        putCommonRecordValues( values, (Record)paceGrp);

        values.put(RideServerContract.Tables.PaceGroups.Columns.NAME, paceGrp.getName());
        values.put(RideServerContract.Tables.PaceGroups.Columns.TYPICAL_PACE, paceGrp.getTypicalPace());
        values.put(RideServerContract.Tables.PaceGroups.Columns.DESCRIPTION, paceGrp.getDescription());
        values.put(RideServerContract.Tables.PaceGroups.Columns.DISPLAYCOLOUR, paceGrp.getDisplayColour());

    }



    //ToDo : Add in the addRIde, addRoute

    //ToDo : Add in the update delete operations

    //ToDo : Add in the query ops.

    public void addPaceGroup( PaceGroup paceGrp) {
        ContentValues values = new ContentValues();

        SQLiteDatabase db = null;

        Integer newId;

        putPaceGroupValues(values,paceGrp);

        try {
            db = this.getWritableDatabase();

            newId = (int)db.insertWithOnConflict(RideServerContract.Tables.PaceGroups.TABLENAME, null, values, SQLiteDatabase.CONFLICT_FAIL);

        } finally {
            if( db != null ) { db.close();}
        }

        Log.d(TAG, String.format("%s : Row inserted : PK allocated = %d", RideServerContract.Tables.PaceGroups.TABLENAME, newId));

        // Now update the paceGroup with the allocated PK.
        ((Record) paceGrp).markInserted(newId);

    }



    public int updatePaceGroup( PaceGroup paceGrp) {

        // Todo : How do we prevent simoultaneous updates overwriting each other?
        // i.e. how do we implement select for update?

        /* Only one process is allowed to write to the database at a time, i.e.
         * Need an exclusive database lock to write even a single row.
         *
         * Think that beginning a transaction will get a lock that will
         * prevent any other writes to the entire database until it is
         * released.
         */


        // ToDo : Do we need to protect against accidental use NULLS?
        // what happens if we update where _id = NULL
        ContentValues values = new ContentValues();

        SQLiteDatabase db = null;

        int noOfRowsUpdated;


        final Integer updateKey = ((Record) paceGrp).getId();

        final String[] updateKeyStr = {updateKey.toString()};


        // If there is no change to the record return immediately say ing no rows
        // updated.

        if( !paceGrp.getDirty()) { return 0 ;}

        // load up the columns to update. At the moment all columns are updated
        // not just the ones that have changed.

        putPaceGroupValues(values,paceGrp);

        //Todo ; Need to work out what to do with CONFLICT clauses

        try {
            db = this.getWritableDatabase();

            noOfRowsUpdated = db.updateWithOnConflict(
                    RideServerContract.Tables.PaceGroups.TABLENAME,
                    values,
                    RideServerContract.Tables.Records.ID_PK_WHERE_CLAUSE,
                    updateKeyStr,
                    SQLiteDatabase.CONFLICT_FAIL);

            Log.d(TAG,String.format("No of noOfRowsUpdated --> %d",noOfRowsUpdated));

        } finally {
            if( db != null ) { db.close();}
        }

        //Todo Need to work out what to do around around Logging and Debugging etc.

        Log.d(TAG, String.format("Table: %s : %d rows updated", RideServerContract.Tables.PaceGroups.TABLENAME, noOfRowsUpdated));

        // Now flag the record as clean
        ((Record) paceGrp).markUpdated();

        return noOfRowsUpdated;

    }

    /*
    public PaceGroup fetchAllPaceGroup() {

        // Pulls Back all rows from a PaceGroup


        String[] columnList = {RideServerContract.Tables.Records.Columns.ID
                ,RideServerContract.Tables.PaceGroups.Columns.NAME
                ,RideServerContract.Tables.PaceGroups.Columns.DESCRIPTION
                ,RideServerContract.Tables.PaceGroups.Columns.TYPICAL_PACE               
                ,RideServerContract.Tables.PaceGroups.Columns.DISPLAYCOLOUR
                ,RideServerContract.Tables.Records.Columns.CREATION_TIMESTAMP
                ,RideServerContract.Tables.Records.Columns.MODIFICATION_TIMESTAMP};

        
        SQLiteDatabase db = null;
        
        PaceGroup pg=null;
        
        Cursor pgC;

        int noOfRowsReturned;

        final String[] selectionKeyStr = {pgId.toString()};


        try {
            db = this.getReadableDatabase();

            pgC = db.query(
                    RideServerContract.Tables.PaceGroups.TABLENAME,
                    columnList,
                    RideServerContract.Tables.Records.ID_PK_WHERE_CLAUSE,
                    selectionKeyStr,
                    null,
                    null,
                    null,
                    null);

            noOfRowsReturned=pgC.getCount();

            
            // If no rows found return an empty object
            if(noOfRowsReturned == 0){return null;}

            // This version is expected to return just a single Instance ( since we are
            // querrying on the PK
            
            if(noOfRowsReturned > 1){ throw new IllegalStateException("This should never happen!!");}
            
            pgC.moveToFirst();


            // This SHOULD create a clean Pace Group??

            pg = new PaceGroup( 
                     pgC.getInt( pgC.getColumnIndexOrThrow(RideServerContract.Tables.Records.Columns.ID))
                    ,pgC.getString( pgC.getColumnIndexOrThrow(RideServerContract.Tables.PaceGroups.Columns.NAME))
                    ,pgC.getString( pgC.getColumnIndexOrThrow(RideServerContract.Tables.PaceGroups.Columns.DESCRIPTION))
                    ,pgC.getInt( pgC.getColumnIndexOrThrow(RideServerContract.Tables.PaceGroups.Columns.TYPICAL_PACE))
                    ,pgC.getString( pgC.getColumnIndexOrThrow(RideServerContract.Tables.PaceGroups.Columns.DISPLAYCOLOUR))
                    ,Timestamp.valueOf(pgC.getString( pgC.getColumnIndexOrThrow(RideServerContract.Tables.Records.Columns.CREATION_TIMESTAMP)))
                    ,Timestamp.valueOf(pgC.getString( pgC.getColumnIndexOrThrow(RideServerContract.Tables.Records.Columns.MODIFICATION_TIMESTAMP)))
            );

        } finally {
            if( db != null ) { db.close();}
        }

        //Todo Need to work out what to do around around Logging and Debugging etc.

        Log.d(TAG, String.format("Table: %s : %d rows returned", RideServerContract.Tables.PaceGroups.TABLENAME, noOfRowsReturned));

        return pg;
    }
     */

    /*
    public PaceGroup pKfetchPaceGroup( Integer pgId) {

        /* Queries a Pace Group on its Primary Key and Returns the PaceGroup
         * Found or a reference to Null if not.
         */
    /*
        String[] columnList = {RideServerContract.Tables.Records.Columns.ID
                ,RideServerContract.Tables.PaceGroups.Columns.NAME
                ,RideServerContract.Tables.PaceGroups.Columns.DESCRIPTION
                ,RideServerContract.Tables.PaceGroups.Columns.TYPICAL_PACE
                ,RideServerContract.Tables.PaceGroups.Columns.DISPLAYCOLOUR
                ,RideServerContract.Tables.Records.Columns.CREATION_TIMESTAMP
                ,RideServerContract.Tables.Records.Columns.MODIFICATION_TIMESTAMP};


        // Must get a valid Integer Object Passed
        if(pgId == null) { throw new IllegalArgumentException("gId cannot be NULL");}


        SQLiteDatabase db = null;

        PaceGroup pg=null;

        Cursor pgC;

        int noOfRowsReturned;

        final String[] selectionKeyStr = {pgId.toString()};


        try {
            db = this.getReadableDatabase();

            pgC = db.query(
                    RideServerContract.Tables.PaceGroups.TABLENAME,
                    columnList,
                    RideServerContract.Tables.Records.ID_PK_WHERE_CLAUSE,
                    selectionKeyStr,
                    null,
                    null,
                    null,
                    null);

            noOfRowsReturned=pgC.getCount();


            // If no rows found return an empty object
            if(noOfRowsReturned == 0){return null;}

            // This version is expected to return just a single Instance ( since we are
            // querrying on the PK

            if(noOfRowsReturned > 1){ throw new IllegalStateException("This should never happen!!");}

            pgC.moveToFirst();


            // This SHOULD create a clean Pace Group??

            pg = new PaceGroup(
                    pgC.getInt( pgC.getColumnIndexOrThrow(RideServerContract.Tables.Records.Columns.ID))
                    ,pgC.getString( pgC.getColumnIndexOrThrow(RideServerContract.Tables.PaceGroups.Columns.NAME))
                    ,pgC.getString( pgC.getColumnIndexOrThrow(RideServerContract.Tables.PaceGroups.Columns.DESCRIPTION))
                    ,pgC.getInt( pgC.getColumnIndexOrThrow(RideServerContract.Tables.PaceGroups.Columns.TYPICAL_PACE))
                    ,pgC.getString( pgC.getColumnIndexOrThrow(RideServerContract.Tables.PaceGroups.Columns.DISPLAYCOLOUR))
                    ,Timestamp.valueOf(pgC.getString( pgC.getColumnIndexOrThrow(RideServerContract.Tables.Records.Columns.CREATION_TIMESTAMP)))
                    ,Timestamp.valueOf(pgC.getString( pgC.getColumnIndexOrThrow(RideServerContract.Tables.Records.Columns.MODIFICATION_TIMESTAMP)))
            );

        } finally {
            if( db != null ) { db.close();}
        }

        //Todo Need to work out what to do around around Logging and Debugging etc.

        Log.d(TAG, String.format("Table: %s : %d rows returned", RideServerContract.Tables.PaceGroups.TABLENAME, noOfRowsReturned));

        return pg;
    }
*/

    public int deletePaceGroup( PaceGroup paceGrp) {

        SQLiteDatabase db = null;

        int noOfRowsDeleted;

        final Integer deleteKey = ((Record) paceGrp).getId();

        final String[] deleteKeyStr = {deleteKey.toString()};

        // todo : consider a isDatabaseRecord flag?
        // If there is no change to the record return immediately
        //if( !paceGrp.getDirty()) { return 0 ;}

        try {
            db = this.getWritableDatabase();

            noOfRowsDeleted = db.delete(
                    RideServerContract.Tables.PaceGroups.TABLENAME,
                    RideServerContract.Tables.Records.ID_PK_WHERE_CLAUSE,
                    deleteKeyStr
            );

        } finally {
            if( db != null ) { db.close();}
        }

        //Todo Need to work out what to do around around Logging and Debugging etc.

        Log.d(TAG, String.format("Table: %s : %d rows deleted.",RideServerContract.Tables.PaceGroups.TABLENAME, noOfRowsDeleted));

        // Todo ; How to handle deleted objects. Do we return null
        // and call the function as : obj = delete(obj) ??
        // or do we have some sort of NULL object constant that we return??
        // or do we have a status flag of some sort?
        // Remove the object instance
        paceGrp = null;

        return noOfRowsDeleted;

    }

    /*
    public SimpleCursorAdapter getPaceGroupAdapter(Context ctx, int rowLayout, int[] rowViews ) {

        // Query for all people contacts using the Contacts.People convenience class.
        // Put a managed wrapper around the retrieved cursor so we don't have to worry about
        // requerying or closing it as the activity changes state.
        Cursor mCursor = ctx.getContentResolver().query(Contacts.People.CONTENT_URI, null, null, null, null);
        //startManagingCursor(mCursor);

        // Now create a new list adapter bound to the cursor.
        // SimpleListAdapter is designed for binding to a Cursor.
        ListAdapter adapter = new SimpleCursorAdapter(
                ctx, // Context.
                rowLayout,  // Specify the row template to use (here, two columns bound to the two retrieved cursorrows).
                mCursor,                                              // Pass in the cursor to bind to.
                new String[]{Contacts.People.NAME, Contacts.People.NAME},           // Array of cursor columns to bind to.

                rowViews);
                // Parallel array of which template objects to bind to those columns.

        return adapter;
    }
    */

}

