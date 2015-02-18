package uk.co.pegortech.tifosi;

/*
 * Copyright (c) 2015. Pegor Technical Services Ltd.
 */

import java.sql.Timestamp;

import uk.co.pegortech.tifosi.database.Record;

/**
 * Created by kevin on 10/02/2015.
 */
public class Route extends Record {
    private String name;
    private int lengthKm;
    private int climbageM;
    private String defaultStartPoint;


    /*Constructors*********************************************************************************/

    /* generally this will make a Route from a pre-existent record in the database
     */

    public Route(
            Integer id,
            String name,
            int lengthKm,
            int climbageM,
            String defaultStartPoint,
            Timestamp creationTimestamp,
            Timestamp modificationTimestamp) {

        super(null, id,creationTimestamp,modificationTimestamp);
        initRoute(name, lengthKm, climbageM, defaultStartPoint);
    }

    /* Generally this constuctor will be used to make a new Route as opposed to retrieving
     * on from the db.
     */
    public Route(String name, int lengthKm, int climbageM, String defaultStartPoint) {
        super(null,null, null, null);
        initRoute(name, lengthKm, climbageM, defaultStartPoint);
    }
    /**********************************************************************************************/

    //used soley in the constructor
    private void initRoute(String name, int lengthKm, int climbageM, String defaultStartPoint){
        this.name = name;
        this.lengthKm = lengthKm;
        this.climbageM = climbageM;
        this.defaultStartPoint = defaultStartPoint;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null ) {throw(new IllegalArgumentException("name cannot be NULL"));}
        this.name = name;
        super.markModified();
    }

    public int getLengthKm() {
        return lengthKm;
    }

    public void setLengthKm(int lengthKm) {
        this.lengthKm = lengthKm;
        super.markModified();
    }

    public int getClimbageM() {
        return climbageM;
    }

    public void setClimbageM(int climbageM) {
        this.climbageM = climbageM;
        super.markModified();
    }

    public String getDefaultStartPoint() {
        return defaultStartPoint;
    }

    public void setDefaultStartPoint(String defaultStartPoint) {
        if(defaultStartPoint == null ) {throw(new IllegalArgumentException("defaultStartPoint cannot be NULL"));}
        this.defaultStartPoint = defaultStartPoint;
        super.markModified();
    }

}
