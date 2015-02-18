package uk.co.pegortech.tifosi;

/*
 * Copyright (c) 2015. Pegor Technical Services Ltd.
 */

import android.content.ContentValues;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import uk.co.pegortech.tifosi.database.Persistable;
import uk.co.pegortech.tifosi.database.Record;
import uk.co.pegortech.tifosi.database.RideServerContract;
import uk.co.pegortech.tifosi.database.Table;

/**
 * Created by kevin on 16/02/2015.
 */
public class Club extends Record implements Persistable {

    private String name;
    private Member founder;
    private Iterable<Member> members;
    private Iterable<PaceGroup> paceGroups;

    // Constructors ********************************************************************************


    public Club(Table tbl, String name, Iterable<Member> founder)
            //throws Exception
            {
        super(tbl, null,null,null);

      //  if(! founder.hasNext()) { throw new Exception("No founding Member!");}
        this.name = name;
        this.members=founder;
        this.paceGroups=null;
        this.founder=founder.iterator().next();

        this.founder.setClub(this);
    };


    // Getters and Setters**************************************************************************

    public Member getFounder() {
        return founder;
    }

    public void setFounder(Member founder) {
        this.founder = founder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Iterable<Member> getMembers() {
        return members;
    }

    public Iterable<PaceGroup> getPaceGroups() {
        return paceGroups;
    }





    // Overrides of Record Super Class**************************************************************

    @Override
    public ContentValues getValues() {

        ContentValues values = new ContentValues();

        values.put(RideServerContract.Tables.Records.Columns.ID, getId());
        values.put(RideServerContract.Tables.Records.Columns.CREATION_TIMESTAMP, getCreationTimestamp().toString());
        values.put(RideServerContract.Tables.Records.Columns.MODIFICATION_TIMESTAMP, getModificationTimestamp().toString());

        values.put(RideServerContract.Tables.Clubs.Columns.NAME, getName());
        values.put(RideServerContract.Tables.Clubs.Columns.FOUNDER, getFounder().getId());

        return values;
    }


    /**
     * Returns a string containing a concise, human-readable description of this
     * object. Subclasses are encouraged to override this method and provide an
     * implementation that takes into account the object's type and data. The
     * default implementation is equivalent to the following expression:
     * <pre>
     *   getClass().getName() + '@' + Integer.toHexString(hashCode())</pre>
     * <p>See <a href="{@docRoot}reference/java/lang/Object.html#writing_toString">Writing a useful
     * {@code toString} method</a>
     * if you intend implementing your own {@code toString} method.
     *
     * @return a printable representation of this object.
     */

    @Override
    public String toString() {
        return "Club {" +
                "id=" + (getId() == null ? "null" : getId().toString()) +
                ", name=" + (name==null ? "null" : name.toString()) +
                ", founder=" + (getFounder() == null ? "null" : getFounder().getName()) +
                " ,first Member=" + getMembers().iterator().next().getName() +
                ", creationTimestamp=" + ( getCreationTimestamp() ==null ? "null" : getCreationTimestamp().toString()) +
                ", modificationTimestamp=" + ( getModificationTimestamp()==null ? "null" : getModificationTimestamp().toString()) +
                ", dirty=" + (getDirty() == null ? "null" : getDirty().toString())  +
                '}';
    }

    public Integer post() {

        Integer newClubPk = getTable().getNextPK();

        setPrimaryKey(newClubPk);

        Integer newMemberPk = getFounder().getTable().getNextPK();

        getFounder().setPrimaryKey(newMemberPk);

        Integer clubPkAdded = getTable().add(this);

        //Add the Founder
        Integer memberPkAdded = getFounder().getTable().add(getFounder());

        return clubPkAdded;
    }
}
