package com.task.my.task.database;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;


public class QueryHandler extends AsyncQueryHandler {

    public QueryHandler(ContentResolver cr) {
        super(cr);
    }

}
