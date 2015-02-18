package uk.co.pegortech.tifosi;

/*
 * Copyright (c) 2015. Pegor Technical Services Ltd.
 */

import android.content.ContentValues;


import java.sql.Timestamp;

import uk.co.pegortech.tifosi.database.Persistable;
import uk.co.pegortech.tifosi.database.Record;
import uk.co.pegortech.tifosi.database.RideServerContract;
import uk.co.pegortech.tifosi.database.Table;

/**
 * Created by kevin on 10/02/2015.
 */
public class PaceGroup extends Record implements Persistable {

    private int typicalPace;
    private String description;
    private String name;
    private String displayColour;

//ToDo : add in a Club Entity to that the PaceGroups can be associated with a Club. We can then
// use this to test posting of master-detail type transactions.


    /*Constructors*********************************************************************************/

    /* generally will be made from a pre-existent record in the database
     *
     */
    public PaceGroup(
            Table tbl,
            Integer id,
            String name,
            String description,
            int typicalPace,
            String displayColour,
            Timestamp creationTimestamp,
            Timestamp modificationTimestamp) {

        super(tbl,id, creationTimestamp, modificationTimestamp);
        initPaceGroup(name, description, typicalPace,  displayColour);

    }

    /* generally will be a new record for insertion.
     *
     */

    public PaceGroup(Table tbl, String name, String description, int typicalPace, String displayColour){
        super(tbl, null, null, null);
        initPaceGroup(name, description, typicalPace, displayColour);
    }
    /**********************************************************************************************/


    private void initPaceGroup(String name, String description, int typicalPace, String displayColour){
        this.typicalPace = typicalPace;
        this.description = description;
        this.name = name;
        this.displayColour = displayColour;


    }


    public int getTypicalPace() {
        return typicalPace;
    }

    public void setTypicalPace(int typicalPace) {
        this.typicalPace = typicalPace;
        super.markModified();
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "PaceGroup{" +
                "id=" + (getId() == null ? "null" : getId().toString()) +
                ", typicalPace=" + String.valueOf(typicalPace) +
                ", description='" + (description==null ? "null" : description.toString()) +
                ", name='" + (name==null ? "null" : name.toString()) + '\'' +
                ", displayColour='" + (displayColour==null ? "null" : displayColour.toString()) +
                ", creationTimestamp=" + ( getCreationTimestamp() ==null ? "null" : getCreationTimestamp().toString()) +
                ", modificationTimestamp=" + ( getModificationTimestamp()==null ? "null" : getModificationTimestamp().toString()) +
                ",dirty=" + (getDirty() == null ? "null" : getDirty().toString())  +
                '}';
    }

    public void setDescription(String description) {
        if(description == null ) {throw(new IllegalArgumentException("description cannot be NULL"));}
        this.description = description;
        markModified();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null ) {throw(new IllegalArgumentException("name cannot be NULL"));}
        this.name = name;
        markModified();

    }

    public String getDisplayColour() {
        return displayColour;
    }

    public void setDisplayColour(String displayColour) {
        if(displayColour == null ) {throw(new IllegalArgumentException("displayColour cannot be NULL"));}
        this.displayColour = displayColour;
        markModified();
    }

    @Override
    public void markInserted(Integer i) {
        super.markInserted(i);
    }

    public String getTableName() {
        return RideServerContract.Tables.PaceGroups.TABLENAME;
    }

    public ContentValues getValues(){
        ContentValues values = new ContentValues();

        values.put(RideServerContract.Tables.Records.Columns.ID, getId());
        values.put(RideServerContract.Tables.Records.Columns.CREATION_TIMESTAMP, getCreationTimestamp().toString());
        values.put(RideServerContract.Tables.Records.Columns.MODIFICATION_TIMESTAMP, getModificationTimestamp().toString());

        values.put(RideServerContract.Tables.PaceGroups.Columns.NAME, getName());
        values.put(RideServerContract.Tables.PaceGroups.Columns.TYPICAL_PACE, getTypicalPace());
        values.put(RideServerContract.Tables.PaceGroups.Columns.DESCRIPTION, getDescription());
        values.put(RideServerContract.Tables.PaceGroups.Columns.DISPLAYCOLOUR, getDisplayColour());

        return values;
    }

    @Override
    public Boolean getDirty() {
        return super.getDirty();
    }

    @Override
    protected void markModified() {
        super.markModified();
    }

    @Override
    public void markUpdated() {
        super.markUpdated();
    }


    /**
     * ******************************************************************************************
     */
    @Override
    public Integer getId() {
        return super.getId();
    }




    public Integer post() {

        // Todo :I think the logic in here needs to work out what has changed exactly
        // and insert/update/delete as required.
        //
        // possibly deletes should get handled separately.

        // here is where the business logic should sit.

        //i.e  inserting/updating child records etc

        //ToDo : Problem : traversing to Master is pretty easy, but what about children? There would not usually be
        // a natural pointer to the child. Need to include as an Collection?

        //ToDo need a start transaction, commit transaction function?

        //Todo Need to work out how I can get most of the functionality of manipulating the database
        //bits and pieces into the Record Supertype!.

        //Todo Need to workout how to get tablenames etc into static Class attributes attributes

        //ToDo need to get Iterators implemented to support Cursor type operations.

        //Todo - can we make things a little more Abstract ??

        //Todo - use some Pure Java thing in place of Android ContentValues.



        /* ToDo : URGENT : Need to work out a decent 'Post Architecture'.
         * My feeling here is that the Post operation ( i.e. what kicks off the
         * writing to the database needs to write not just itself, but whatever
         * other objects that it deems sensible to write.
         *
         * The idea is to do this by calling that other objects Post operation
         *
         * A problem I think we need to deal with is where we have reciprocal relationships
         * i.e. A --> B and B --> A.
         *
         * If we have A attempting to write B before it writes itself and
         * B attempting to write A before it writes itself, then we are stuck in a
         * endless loop.
         *
         * Current idea is to have a 'Is-posting' flag, which gets set as the first part
         * of posting ( If its not already set) . The Post operation will then be called on other objects.
         * If the Is-posting on the target object is already set, the Post operation will just
         * return.
         *
         * Once the Flag has been set, the business logic of the objects post will be executed
         * i.e which other objects to post.
         *
         * Then the object will write itself
         *
         * Then maybe ( post some other objects??? ))
         *
         * Finally clear flag ( Not sure if this intoduces a danger of flags being cleared
         * prematurly or not -- Need think it through clearly.
         *
         * Maybe a Transaction No or change No is a better idea than a flag?
         *
         * There are parts of the Post operation that we want to be totally the same ( i.e.
         * the flags handling etc. but other things that we will want to change. ( Ie. the business
         * logic.)
         *
         * Current thinking is to have the post operation has part of the Record super-type, that
         * then calls functions in the sub-type to do the business logic bits. i.e. something
         * like an on-post method.
         *
         * What is the best way of getting the post to call back the sub-type methods?
         * Interface ??
         *
         * We won't always want the same logic to be applied on a post?
         *
         * think need to about this..
         *
         *  postTransactionA
         *
         *      post ( TransactionA )
         *
         *      where TransactionA is another object implementing On-post method
         *
         *      which does
         *              another.Object.post ( TransactionB )
         *              yet another Object.post ( TransactionC )
         *              yet another Object.post ( TransactionD)
         *
         *
         * I think this will work
         *
         * Also is proibably the place to put the Database TRansaction Management Stuff!!
         */

        //if(rec.getValues().containsKey(RideServerContract.Tables.Records.Columns.ID)) {
        //    rec.getValues().remove(RideServerContract.Tables.Records.Columns.ID);
        //}

        Integer newPk = getTable().getNextPK();

        setPrimaryKey(newPk);


        //rec.getValues().put(RideServerContract.Tables.Records.Columns.ID, newPk);

        Integer pkAdded = getTable().add(this);

        return pkAdded;
    }
}

