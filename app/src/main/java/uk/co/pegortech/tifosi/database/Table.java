package uk.co.pegortech.tifosi.database;

/*
 * Copyright (c) 2015. Pegor Technical Services Ltd.
 */

/**
 * Created by kevin on 15/02/2015.
 */

// Todo Table is a bad name for this : it implies an implementation : Need to change it


    //Todo : Possible add in isStale() function to identify whether the underlying record has
    // been changed by someone else since it was read from the Persistance store. ?? Does it belong here
    // or as part of the Persistable interface

    // ToDo : Need some transcation Management functionality
    // StartTranasction??
    // CommitTransaction??
    //RollbackTranasction??
    // inTransaction??

    //Todo : do we need to pass a parameter to teh add/update/delete to indicate
    // whether it is being done as part of an existing tranasction?

    //Todo : Do all members of the interface nned to be public? does protected/package access
    // make any logical sense
public interface Table {
    public Integer add(Persistable p);
    public int update(Persistable p);
    public int delete(Persistable p);
    public Persistable pkFetch( Table tbl, Integer k);
    public Integer getNextPK();
}
