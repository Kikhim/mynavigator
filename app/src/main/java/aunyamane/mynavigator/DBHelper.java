package aunyamane.mynavigator;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.content.Context;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "requirements";
    private static final String TABLE_DATA = "informations";

    public DBHelper(Context context){
        super(context, DATABASE_NAME , null , DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_DATA + "(id INTEGER PRIMARY KEY," + " location VARCHAR(255),"
                + " price DOUBLE ," + " vehicle VARCHAR(256) )");
        Log.d("CREATE TABLE","Create Table Successfully.");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
