package uk.co.pegortech.tifosi;

/*
 * Copyright (c) 2015. Pegor Technical Services Ltd.
 */

import uk.co.pegortech.tifosi.database.Persistable;
import uk.co.pegortech.tifosi.database.Table;

/**
 * Created by kevin on 17/02/2015.
 */

/* ToDo Need to Experiment with different type of structure which implement the Member interface.
 * Need to understand whether there are any real advantages in Building things this way.
 * I sought of think that there are, but I haven't quite got my head around it, yet.
 *
 * It might be that all we actually need is the structure suggested by Member2, possibly
 * with a couple of different constructors in order to make it work.
 */


public interface Member extends Persistable {

    public  String getName();
    public  Club getClub();
    public  void setClub(Club clb);

    public Table getTable();
    public void setPrimaryKey(Integer id);
    //void Member(String name, Club clb);
}
