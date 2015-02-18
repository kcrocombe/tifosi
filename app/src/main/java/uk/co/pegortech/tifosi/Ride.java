package uk.co.pegortech.tifosi;

/*
 * Copyright (c) 2015. Pegor Technical Services Ltd.
 */


import java.util.Date;
import java.sql.Time;
import java.sql.Timestamp;


import uk.co.pegortech.tifosi.database.Record;

/**
 * Created by kevin on 10/02/2015.
 */
public class Ride extends Record {
    public enum GenderSpecificity { Male, Female, Mixed};

    public enum Focus { Social, Structured, Training, Race };

    private Date scheduledDate;
    private Time startTime;
    private PaceGroup paceGrp;
    private String startPoint;
    private Route route;
    private GenderSpecificity genderSpecificity ;
    private Focus  focus;



    /*Constructors*********************************************************************************/

    public Ride(Date scheduledDate, Time startTime, PaceGroup paceGrp, String startPoint, Route route, GenderSpecificity genderSpecificity, Focus focus) {

        super(null,null,null,null);

        initRide(scheduledDate, startTime, paceGrp, startPoint, route, genderSpecificity, focus);
    }

    /* generally this will make a Ride from a pre-existent record in the database
     */

    public Ride(
            Integer id,
            Date scheduledDate,
            Time startTime,
            PaceGroup paceGrp,
            String startPoint,
            Route route,
            GenderSpecificity genderSpecificity,
            Focus focus,
            Timestamp creationTimestamp,
            Timestamp modificationTimestamp) {

        super(null, id, creationTimestamp, modificationTimestamp);

        initRide(scheduledDate, startTime, paceGrp, startPoint, route, genderSpecificity, focus);
    }


    //used soley in the constructor
    private void initRide(
            Date scheduledDate,
            Time startTime,
            PaceGroup paceGrp,
            String startPoint,
            Route route,
            GenderSpecificity genderSpecificity,
            Focus focus) {

        this.scheduledDate = scheduledDate;
        this.startTime = startTime;
        this.paceGrp = paceGrp;
        this.startPoint = startPoint;
        this.route = route;
        this.genderSpecificity = genderSpecificity;
        this.focus = focus;
    }


    /**********************************************************************************************/


    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        if(scheduledDate == null ) {throw(new IllegalArgumentException("scheduledDate cannot be NULL"));}
        this.scheduledDate = scheduledDate;
        super.markModified();
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        if(startTime == null ) {throw(new IllegalArgumentException("startTime cannot be NULL"));}
        this.startTime = startTime;
        super.markModified();
    }

    public PaceGroup getPaceGrp() {
        return paceGrp;
    }

    public void setPaceGrp(PaceGroup paceGrp) {
        if(paceGrp == null ) {throw(new IllegalArgumentException("paceGrp cannot be NULL"));}
        this.paceGrp = paceGrp;
        super.markModified();
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        if(startPoint == null ) {throw(new IllegalArgumentException("startPoint cannot be NULL"));}
        this.startPoint = startPoint;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        if(route == null ) {throw(new IllegalArgumentException("route cannot be NULL"));}
        this.route = route;
        super.markModified();
    }

    public GenderSpecificity getGenderSpecificity() {
        return genderSpecificity;
    }

    public void setGenderSpecificity(GenderSpecificity genderSpecificity) {
        if(genderSpecificity == null ) {throw(new IllegalArgumentException("genderSpecifity cannot be NULL"));}
        this.genderSpecificity = genderSpecificity;
        super.markModified();
    }

    public Focus getFocus() {
        return focus;
    }

    public void setFocus(Focus focus) {
        if(focus == null ) {throw(new IllegalArgumentException("focus cannot be NULL"));}
        this.focus = focus;
        super.markModified();
    }

}

