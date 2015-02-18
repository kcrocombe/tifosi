package uk.co.pegortech.tifosi.database;

/*
 * Copyright (c) 2015. Pegor Technical Services Ltd.
 */

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by kevin on 09/02/2015.
 */
public class RideServerContract {
    // DB specific constants

    // File that will contain db
    public static final String DB_NAME = "tifosi.db";

    // Version of the database schema
    public static final int DB_VERSION = 2;



    // Provider specific constants
    // content://uk.co.pegortech.tifosi.RideProvider/rides
    public static final String AUTHORITY = "uk.co.pegortech.tifosi.RideProvider";

    /*
     * public static final int STATUS_ITEM = 1;
     * public static final int STATUS_DIR = 2;
     * public static final String STATUS_TYPE_ITEM = "vnd.android.cursor.item/vnd.com.marakana.android.yamba.provider.status";
     * public static final String STATUS_TYPE_DIR = "vnd.android.cursor.dir/vnd.com.marakana.android.yamba.provider.status";
     */

    /* Note the following are the definitions of the content provided. These won't generally
     * correspond 1:1 with the underlying tables. This is supposed to abstract the presentation
     * away from the actual underlying implementation. The content may be served
     * a) direct from a table
     * b) from a view
     * c) from some calculated presentation
     */


    /* Storage Classes provided by SQLite
     *
     */
    public class SQLiteType {
        public static final String INT = "integer";
        public static final String TEXT = "text";
        public static final String FLOAT = "float";
        public static final String BLOB = "blob";

        public static final String NULL = "";
        public static final String NOTNULL = "NOT NULL";
    }


    public class SQLiteSequence {
        // These are the constants used to query the sqlite_sequence
        // system catalog table and so identify the largest PK
        // allocated so far. THis is then used to calculate teh next key to use.

        public static final String SEQUENCE_TABLE="sqlite_sequence";
        public static final String SEQUENCE_COLUMN="seq";
        public static final String SEQUENCE_WHERE_CLAUSE="name=?";
    }

     public class Tables {
         // Defines the underlying database tables

         public class Records {
             // These hold the constants common to all tables. They derive
             // from the super Class Record which the other Classes extend.
             public class Columns {
                 // The convention is to always call the identifying column _ID
                 public static final String ID = BaseColumns._ID;
                 public static final String CREATION_TIMESTAMP = "creationTimestamp";
                 public static final String MODIFICATION_TIMESTAMP = "modificationTimestamp";
             }

             public static final String ID_PK_WHERE_CLAUSE = Columns.ID + "=?";
         }

         public class Rides {
             public static final String TABLENAME = "Rides";

             public class Columns {
                 public static final String SCHEDULED_DATE = "scheduledDate";
                 public static final String START_TIME = "startTime";
                 public static final String START_POINT = "startpoint";
                 public static final String ROUTE_ID = "routeId";
                 public static final String PACE_GROUP_ID = "paceGroupID";
                 public static final String TARGET_PACE = "targetPace";
                 public static final String GENDER_SPECIFICITY = "genderSpecificity";
                 public static final String FOCUS = "focus";
             }
         }

         public class PaceGroups {
             public static final String TABLENAME = "PaceGroup";

             public class Columns {
                 public static final String NAME = "name";
                 public static final String TYPICAL_PACE = "typicalPace";
                 public static final String DESCRIPTION = "description";
                 public static final String DISPLAYCOLOUR = "displayColour";
             }
         }

         public class Clubs {
             public static final String TABLENAME = "Club";

             public class Columns {
                 public static final String NAME = "name";
                 public static final String FOUNDER= "founder";
             }
         }

         public class Members {
             public static final String TABLENAME = "Member";

             public class Columns {
                 public static final String NAME = "name";
                 public static final String CLUB= "club";
             }
         }

         public class Routes {
             public static final String TABLENAME = "Route";

             public class Columns {
                 public static final String NAME = "name";
                 public static final String LENGTH = "lengthKm";
                 public static final String CLIMBAGE = "climbageM";
                 public static final String DEFAULT_STARTPOINT = "defaultStartPoint";
             }
         }
     }

    public class Content {
        // Defines the external interface of the provider.
        public class RideDay {
            // Servers one record for each Day where there is at least one ride, with the list of
            // rides for that day served as a structured field ( rideList).

            public static final String CONTENTNAME = "RideDays";

            public class Fields {
                // The convention is to always call the identifying column _ID
                public static final String ID = BaseColumns._ID;
                public static final String SCHEDULED_DATE = "scheduledDate";
                public static final String NUMBER_OF_RIDES = "noOfRides";
                public static final String RIDELIST = "rideList";
            }

            public static final String DEFAULT_SORT = Fields.SCHEDULED_DATE + " DESC";

            public final Uri URI = Uri.parse("content://" + AUTHORITY + "/" + CONTENTNAME);
        }
    }
}
