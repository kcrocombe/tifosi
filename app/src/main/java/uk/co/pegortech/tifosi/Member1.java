package uk.co.pegortech.tifosi;

/*
 * Copyright (c) 2015. Pegor Technical Services Ltd.
 */

import android.content.ContentValues;

import uk.co.pegortech.tifosi.database.Persistable;
import uk.co.pegortech.tifosi.database.Persister;
import uk.co.pegortech.tifosi.database.Record;
import uk.co.pegortech.tifosi.database.RideServerContract;
import uk.co.pegortech.tifosi.database.Table;

/**
 * Created by kevin on 17/02/2015.
 */

public class Member1 extends Record implements Member {
    private String name;
    private Club club;

    public Member1(Table tbl, String name, Club clb) {
        super(tbl,null,null,null);

        this.name = name;
        this.club = club;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club clb) {
        this.club = clb;
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

    public Integer post() {


        Integer pkAdded = getTable().add(this);

        return pkAdded;
    }
}


