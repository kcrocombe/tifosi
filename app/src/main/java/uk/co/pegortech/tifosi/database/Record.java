package uk.co.pegortech.tifosi.database;

/*
 * Copyright (c) 2015. Pegor Technical Services Ltd.
 */

import android.os.SystemClock;

import java.sql.Timestamp;

/**
 * Created by kevin on 10/02/2015.
 */


//Todo : When a class implements an interface, but inherits some of the methods from its superClass does that work?
// or do we have to explicitly override the method in the sub-class and call the super method? Or are these problems
// just because the supertype methods are protected ( and so not in the public interface )

public abstract class Record {
    private Table tbl;
    private Integer id;
    private Timestamp creationTimestamp;
    private Timestamp modificationTimestamp;
    private Boolean dirty;                          //i.e not yet written to database

    //Todo May need to introduce a status type flag to indicate NUllness/db/non db Record.

    //Todo ; Consider introducing a system change no to identify changed records>??

    /*Constructors*********************************************************************************/

    public Record(
            Table tbl,
            Integer id,
            Timestamp creationTimestamp,
            Timestamp modificationTimestamp) {

        // Nb : this will be NULL for a record yet to inserted in the database and
        // Not Null if it has been queried.
        this.id = id;
        this.tbl=tbl;

        /* ToDo : Reconsider this. IT may be better not to allocate change/modification times
         * until the records are committed. ( We can use this info to determine whether a record
         * has changed by someone else since it was read.
         */

        //Todo : Possibly need a seperate db time stamp / system change no in order to identify
        // changed records?

        //ToDo : Need a read/only flag for records not to be allowed to change?


        if(creationTimestamp == null ) {
            // If this a true new Record, then no timestamps exist so we have to create an initial one
            this.creationTimestamp = new Timestamp(0);
            this.creationTimestamp.setTime(System.currentTimeMillis());
            this.dirty=true;
        } else {
            // This is a pre-existing Record queried from the db, so will already have a timestamp
            this.creationTimestamp = creationTimestamp;
            this.dirty=false;
        }

        if(modificationTimestamp == null ) {
            // If this a true new Record, then no timestamps exist so we have to create initial one
            this.modificationTimestamp = this.creationTimestamp;
        } else {
            // This is a pre-existing Record queried from the db, so will already have a timestamp
            this.modificationTimestamp = modificationTimestamp;
        }
    }

    /**********************************************************************************************/

    public Table getTable() {
        return this.tbl;
    }

    public Integer getId() {
        return id;
    }

    public void markInserted(Integer id) {
        //ToDo : Need to look at this again in the light of the new mechanism for allocating a PK

        // Indicates that the Record has now been successfully inserted in the database
        // and capture the PK of the record.
        //if (this.id != null) {
        //    throw new IllegalArgumentException("Cannot change value of an Id");
        //}
        //this.id = id;
        this.dirty=false;
    }

    public void setPrimaryKey(Integer id) {

        if (this.id != null) {
            throw new IllegalArgumentException("Cannot change value of an Id");
        }
        this.id = id;
    }

    public void markUpdated() {
        // Indicates that the Record has now been successfully updated in the database.
        // and so is now considered clean.
        this.dirty=false;
    }

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }

    public Timestamp getModificationTimestamp() {
        return modificationTimestamp;
    }

    public Boolean getDirty()  {
        return dirty;
    }

    protected void markModified() {
        //Set the change timestamp and mark the record as dirty i.e requiring a db write
        this.modificationTimestamp.setTime(System.currentTimeMillis());
        this.dirty=true;
    }

}

