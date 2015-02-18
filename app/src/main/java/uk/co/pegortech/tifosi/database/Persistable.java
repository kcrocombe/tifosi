package uk.co.pegortech.tifosi.database;

/*
 * Copyright (c) 2015. Pegor Technical Services Ltd.
 */

import android.content.ContentValues;

/**
 * Created by kevin on 14/02/2015.
 */

// Todo : possibly add in an isStale() function to determine whether the underlying record in
// the persistance store has changed. Not sure if it belongs here or in the Table interface.

public interface Persistable {

    ContentValues getValues();
    void markInserted(Integer Id);
    Boolean getDirty();
    void markUpdated();
    Integer getId();

}
