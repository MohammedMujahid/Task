package com.task.my.task.database;


public final class TableFields {

    public static final class DateEntry {

        public static final String TABLE_NAME = "day";
        public static final String _ID = "_id";
        public static final String DAYS_DATE = "date";
        public static final String DAYS_WIN = "win";

        public static final int CHECKED = 1;

        public static final String[] DAYS_COLUMNS =
                {DAYS_DATE, DAYS_WIN};

    }

    public static final class EventEntry {

        public static final String TABLE_NAME = "event";
        public static final String _ID = "_id";
        public static final String EVENT_CREATED = "eventCreated";
        public static final String EVENT_TEXT = "eventText";
        public static final String EVENT_CHECKED = "eventChecked";

        public static final int CHECKED = 1;

        public static final String[] EVENT_COLUMNS =
                {EVENT_CREATED, EVENT_TEXT, EVENT_CHECKED};


    }

}
