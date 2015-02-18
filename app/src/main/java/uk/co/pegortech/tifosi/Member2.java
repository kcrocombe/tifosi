package uk.co.pegortech.tifosi;

/*
 * Copyright (c) 2015. Pegor Technical Services Ltd.
 */

import android.content.ContentValues;

import uk.co.pegortech.tifosi.database.Persistable;
import uk.co.pegortech.tifosi.database.Record;
import uk.co.pegortech.tifosi.database.RideServerContract;
import uk.co.pegortech.tifosi.database.Table;

/**
 * Created by kevin on 17/02/2015.
 */
public class Member2 extends Record implements Member {

    Integer id;
    Table db;

    public Member2( Integer memberId, Table db) {
        super( null,null,null,null);

        this.id = memberId;
        this.db = db;
    }

    @Override
    public String getName() {
        return ((Member) db.pkFetch(this.db,this.id)).getName();
    }

    @Override
    public Club getClub() {
        return ((Member) db.pkFetch(this.db,this.id)).getClub();
    }

    @Override
    public void setClub(Club clb) {
        ((Member) db.pkFetch(this.db,this.id)).setClub(clb);

    }

    @Override
    public ContentValues getValues() {

        ContentValues values = new ContentValues();

        values.put(RideServerContract.Tables.Records.Columns.ID, getId());
        values.put(RideServerContract.Tables.Records.Columns.CREATION_TIMESTAMP, getCreationTimestamp().toString());
        values.put(RideServerContract.Tables.Records.Columns.MODIFICATION_TIMESTAMP, getModificationTimestamp().toString());

        values.put(RideServerContract.Tables.Members.Columns.NAME, getName());
        values.put(RideServerContract.Tables.Members.Columns.CLUB, getClub().getId());

        return values;
    }
}

