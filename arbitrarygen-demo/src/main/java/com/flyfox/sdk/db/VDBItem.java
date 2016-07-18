package com.flyfox.sdk.db;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by albieliang on 16/7/17.
 */
public abstract class VDBItem {
    public static final String COL_ROWID = "rowId";
    protected static final int rowId_HASHCODE = "rowId".hashCode();

    protected long rowId = -1;
    protected boolean hasRowId;

    public void convertFrom(Cursor fromType) {
        // TODO Auto-generated method stub
    }

    public ContentValues convertTo() {
        // TODO Auto-generated method stub
        return null;
    }

    public void reset() {
        // TODO Auto-generated method stub
    }

    public void clear() {
        // TODO Auto-generated method stub
    }

    public Object getValue(String key) {
        // TODO Auto-generated method stub
        return null;
    }
}
