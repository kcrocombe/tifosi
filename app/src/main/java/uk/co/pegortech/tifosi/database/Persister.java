package uk.co.pegortech.tifosi.database;

/*
 * Copyright (c) 2015. Pegor Technical Services Ltd.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Timestamp;
import java.util.UUID;

import uk.co.pegortech.tifosi.PaceGroup;

/**
 * Created by kevin on 14/02/2015.
 */

//Todo : Persister is too abstract a name. This is a particular implementation, and name needs to reflect this.

// Todo : Does it make sense to make parts of the database stuff static, and so shared by all instances of the class
// eg. database name, handles, transaction stuff??

public class Persister implements Table {
        private DbHelper dbh;
        private String TAG=this.getClass().getCanonicalName();
        private Context ctx;
        private String tableName;


    public Persister(Context ctx, String tableName) {
        this.ctx=ctx;
        this.dbh = new DbHelper(ctx);
        this.tableName=tableName;
    }

    private String getTableName() {
        return this.tableName;
    }




    public Integer add( Persistable rec) {
        String TAG=this.TAG + ':' + "add";

        SQLiteDatabase db = null;

        Integer newId;

        try {
            db = dbh.getWritableDatabase();

            //ToDo : This bit is clumsy : Don't like pushing things onto the contentValues
            // only to pop them off and replace them later on.
            // Rework

            //if(rec.getValues().containsKey(RideServerContract.Tables.Records.Columns.ID)) {
            //    rec.getValues().remove(RideServerContract.Tables.Records.Columns.ID);
            //}

            //Integer newPk = getNextPK();

            //rec.getValues().put(RideServerContract.Tables.Records.Columns.ID, newPk);


            // NB newID SHOULD be the same as newPk

            newId = (int)db.insertWithOnConflict(
                        getTableName(), null, rec.getValues(), SQLiteDatabase.CONFLICT_FAIL);

        } finally {
            if( dbh != null ) { dbh.close();}
        }

        Log.d(TAG, String.format(" %s : Row inserted : PK allocated = %d", getTableName(), newId));

        // Now update the with the allocated PK.
        rec.markInserted(newId);

        return newId;

    }


    public int update( Persistable x ) {

        SQLiteDatabase db = null;

        int noOfRowsUpdated;

        final Integer updateKey = x.getId();

        final String[] updateKeyStr = {updateKey.toString()};


        // If there is no change to the record return immediately say ing no rows
        // updated.

        if( !x.getDirty()) { return 0 ;}


        try {
            // ToDo : Do we need to maintain the db handle outside of the insert/update??
            // We will want to handle multiple insert as part of the transaction, so can
            // the transaction survive if we do a close() on the database?? I suspect not.

            db = dbh.getWritableDatabase();

            // Todo : Need to check that the database has not been updated in the meantime.
            noOfRowsUpdated = db.updateWithOnConflict(
                    getTableName(),
                    x.getValues(),
                    RideServerContract.Tables.Records.ID_PK_WHERE_CLAUSE,
                    updateKeyStr,
                    SQLiteDatabase.CONFLICT_FAIL);

            Log.d(TAG,String.format("No of noOfRowsUpdated --> %d",noOfRowsUpdated));

        } finally {
            if( dbh != null ) { dbh.close();}
        }

        Log.d(TAG, String.format("Table: %s : %d rows updated", getTableName(), noOfRowsUpdated));

        // Now flag the record as clean
        x.markUpdated();

        return noOfRowsUpdated;
    }



    public int delete (Persistable x ) {
        SQLiteDatabase db = null;

        int noOfRowsDeleted;

        final Integer deleteKey = x.getId();

        // If not a database record then Id will be NULL
        if(deleteKey == null) return 0;

        final String[] deleteKeyStr = {deleteKey.toString()};

        try {
            db = dbh.getWritableDatabase();

            noOfRowsDeleted = db.delete(
                    getTableName(),
                    RideServerContract.Tables.Records.ID_PK_WHERE_CLAUSE,
                    deleteKeyStr
            );

        } finally {
            if( dbh != null ) { dbh.close();}

        }


       //toDo : Work out how we turn a NULL record : needs to be at minimum, stripped of its
        // pK, dates etc.

        //Todo Need to work out what to do around around Logging and Debugging etc.

        Log.d(TAG, String.format("Table: %s : %d rows deleted.",getTableName(), noOfRowsDeleted));

        return noOfRowsDeleted;

    }




    public Persistable pkFetch( Table tbl, Integer key ) {

        /* Queries a Pace Group on its Primary Key and Returns the PaceGroup
         * Found or a reference to Null if not.
         */





        String[] columnList = {RideServerContract.Tables.Records.Columns.ID
                ,RideServerContract.Tables.PaceGroups.Columns.NAME
                ,RideServerContract.Tables.PaceGroups.Columns.DESCRIPTION
                ,RideServerContract.Tables.PaceGroups.Columns.TYPICAL_PACE
                ,RideServerContract.Tables.PaceGroups.Columns.DISPLAYCOLOUR
                ,RideServerContract.Tables.Records.Columns.CREATION_TIMESTAMP
                ,RideServerContract.Tables.Records.Columns.MODIFICATION_TIMESTAMP};


        // Must get a valid Integer Object Passed
        if(key == null) { throw new IllegalArgumentException("gId cannot be NULL");}


        SQLiteDatabase db = null;

        Persistable pg=null;

        Cursor pgC;

        int noOfRowsReturned;

        final String[] selectionKeyStr = {key.toString()};


        try {
            db = dbh.getReadableDatabase();

            pgC = db.query(
                    getTableName(),
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
            // querying on the PK

            if(noOfRowsReturned > 1){ throw new IllegalStateException("This should never happen!!");}

            pgC.moveToFirst();




            //ToDo : This bit need to be given some more thought?  Interface solution rather than this
            // horrible swithc construct
            //
            // Need to think this bit through...

            switch (getTableName() ){

                case RideServerContract.Tables.PaceGroups.TABLENAME : {

                    pg = new PaceGroup( tbl,
                            pgC.getInt(pgC.getColumnIndexOrThrow(RideServerContract.Tables.Records.Columns.ID))
                            , pgC.getString(pgC.getColumnIndexOrThrow(RideServerContract.Tables.PaceGroups.Columns.NAME))
                            , pgC.getString(pgC.getColumnIndexOrThrow(RideServerContract.Tables.PaceGroups.Columns.DESCRIPTION))
                            , pgC.getInt(pgC.getColumnIndexOrThrow(RideServerContract.Tables.PaceGroups.Columns.TYPICAL_PACE))
                            , pgC.getString(pgC.getColumnIndexOrThrow(RideServerContract.Tables.PaceGroups.Columns.DISPLAYCOLOUR))
                            , Timestamp.valueOf(pgC.getString(pgC.getColumnIndexOrThrow(RideServerContract.Tables.Records.Columns.CREATION_TIMESTAMP)))
                            , Timestamp.valueOf(pgC.getString(pgC.getColumnIndexOrThrow(RideServerContract.Tables.Records.Columns.MODIFICATION_TIMESTAMP)))
                    );
                    break;
                }
                default: {
                    throw new RuntimeException("This shouldn't happen!");
                }
            }
        } finally {
            if( dbh != null ) { dbh.close();}
        }

        //Todo Need to work out what to do around around Logging and Debugging etc.

        Log.d(TAG, String.format("Table: %s : %d rows returned", getTableName(), noOfRowsReturned));



        return pg;
    }




    public Integer getNextPK() {

        /* Pretty Naff function to generate a PK that is available BEFORE the row
         * is inserted. Note that it relies on the tables being created with a
         * sqlite primary key specified in a very particular way.
         *
         * It need to created as follows
         *
         *  create table newTable ( pkId integer primary key autoincrement not null, ....
         *
         *  If the autoincrement clause is not specified, the table will use ROWID as
         *  the PK and NOT create the sqlite_sequence catalog table that this
         *  function relies on.
         */

        //ToDo : URGENT Need to Completeley rework the PK architecure to Base it on UUID's!!

        Integer x = UUID.randomUUID().hashCode();

        String[] columnAsAList = {RideServerContract.SQLiteSequence.SEQUENCE_COLUMN};


        SQLiteDatabase db = null;

        Integer newPk=null;

        Cursor intC;

        int noOfRowsReturned;

        final String[] selectionKeyStr = {getTableName()};


        try {
            db = dbh.getReadableDatabase();

            //Todo : This needs to be trapped to handle the situation where the entry is null
            // or the table doesn't exist.

            intC = db.query(
                    RideServerContract.SQLiteSequence.SEQUENCE_TABLE,
                    columnAsAList,
                    RideServerContract.SQLiteSequence.SEQUENCE_WHERE_CLAUSE,
                    selectionKeyStr,
                    null,
                    null,
                    null,
                    null);

            noOfRowsReturned=intC.getCount();


            // If no rows found then No pk allocated as yet
            if (noOfRowsReturned == 0){
                newPk = 0;
            } else {

                if (noOfRowsReturned > 1) {
                    throw new IllegalStateException("This should never happen!!");
                }

                intC.moveToFirst();


                newPk = intC.getInt(intC.getColumnIndexOrThrow(RideServerContract.SQLiteSequence.SEQUENCE_COLUMN));

            /* Increment the value so theoretically we will get a new unused PK.
             * Note this is dependent on no more insert happening on theis table between now
             * and when the table is used.
             *
             * As long as the table stays in Writeable Mode, then theoretically, no intervening process
             * should be able to do an insert in the meantime.
             */

                //Todo : at the moment the table is taken out of write mode as soon as the query is completed
                // Need to fix this
            }

            ++newPk;


        } finally {
            if( dbh != null ) { dbh.close();}
        }

        //Todo Need to work out what to do around around Logging and Debugging etc.

        Log.d(TAG, String.format("Table: %s : %d rows returned. New PK = %d", getTableName(), noOfRowsReturned, newPk));

        return x;
    }
}

