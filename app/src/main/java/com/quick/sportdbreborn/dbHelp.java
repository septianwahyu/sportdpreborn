package com.quick.sportdbreborn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class dbHelp extends SQLiteOpenHelper {
        String mQuery, mQuery2, mQuery3, mQuery4, mQuery5;
        SQLiteDatabase mDB;
        Context mContext;

        static final String TABLE_AL        = "tb_al";
        final String _id                    = "_id";
        final String ID_LEAGUE              = "ID_LEAGUE";
        final String LEAGUE_NAME            = "LEAGUE_NAME";
        final String SPORT_TYPE             = "SPORT_TYPE";
        final String LEAGUE_ALT_NAME        = "LEAGUE_ALT_NAME";

        static final String TABLE_LD        = "tb_ld";
        final String _id2                   = "_id";
        final String ID_LEAGUE_D            = "ID_LEAGUE_D";
        final String SPORT_TYPE_D           = "SPORT_TYPE_D";
        final String LEAGUE_NAME_D          = "LEAGUE_NAME_D";
        final String LEAGUE_ALT_NAME_D      = "LEAGUE_ALT_NAME_D";
        final String CURRENT_SEASON         = "CURRENT_SEASON";
        final String FORMED_YEAR            = "FORMED_YEAR";
        final String DATE_FIRST_EVENT       = "DATE_FIRST_EVENT";
        final String COUNTRY                = "COUNTRY";
        final String WEBSITE                = "WEBSITE";
        final String FACEBOOK               = "FACEBOOK";
        final String TWITTER                = "TWITTER";
        final String YOUTUBE                = "YOUTUBE";
        final String DESCRIPTION            = "DESCRIPTION";
        final String BANNER                 = "BANNER";
        final String BADGE                  = "BADGE";
        final String LOGO                   = "LOGO";

        static final String TABLE_LS        = "tb_ls";
        final String _id3                   = "_id";
        final String SEASON                 = "SEASON";

        static final String TABLE_DT        = "tb_dt";
        final String _id4                   = "_id";
        final String ID_TEAM                = "ID_TEAM";
        final String TEAM_NAME              = "TEAM_NAME";
        final String TEAM_NAME_SHORT        = "TEAM_NAME_SHORT";
        final String TEAM_ALT_NAME          = "TEAM_ALT_NAME";
        final String DT_FORMED_YEAR         = "DT_FORMED_YEAR";
        final String DT_SPORT_TYPE          = "DT_SPORT_TYPE";
        final String LEAGUE_1               = "LEAGUE_1";
        final String LEAGUE_2               = "LEAGUE_2";
        final String LEAGUE_3               = "LEAGUE_3";
        final String LEAGUE_4               = "LEAGUE_4";
        final String LEAGUE_5               = "LEAGUE_5";
        final String STADIUM                = "STADIUM";
        final String KEYWORDS               = "KEYWORDS";
        final String RSS                    = "RSS";
        final String STADIUM_THUMB          = "STADIUM_THUMB";
        final String STADIUM_DESC           = "STADIUM_DESC";
        final String STADIUM_LOC            = "STADIUM_LOC";
        final String STADIUM_CAP            = "STADIUM_CAP";
        final String DT_WEBSITE             = "DT_WEBSITE";
        final String DT_FACEBOOK            = "DT_FACEBOOK";
        final String DT_TWITTER             = "DT_TWITTER";
        final String INSTAGRAM              = "INSTAGRAM";
        final String DESC_EN                = "DESC_EN";
        final String DT_COUNTRY             = "DT_COUNTRY";
        final String TEAM_BADGE             = "TEAM_BADGE";
        final String TEAM_JERSEY            = "TEAM_JERSEY";
        final String TEAM_BANNER            = "TEAM_BANNER";

        static final String TABLE_SK        = "tb_sk";
        final String _id5                   = "_id";
        final String RANK                   = "RANK";
        final String TEAM_NAME_S            = "TEAM_NAME_S";
        final String TEAM_BADGE_S           = "TEAM_BADGE_S";
        final String PLAYED                 = "PLAYED";
        final String WIN                    = "WIN";
        final String LOSS                   = "LOSS";
        final String DRAW                   = "DRAW";
        final String GOALS_FOR              = "GOALS_FOR";
        final String GOALS_AGAINST          = "GOALS_AGAINST";
        final String GOAL_DIFFERENCE        = "GOAL_DIFFERENCE";
        final String POINTS                 = "POINTS";
        final String FORM                   = "FORM";

    public dbHelp(Context context){
            super(context,"db_data",null,5);
            mContext = context;
            mDB = this.getWritableDatabase();
        }

    @Override
    public void onCreate(SQLiteDatabase db) {
        mQuery = "CREATE TABLE " + TABLE_AL + " (" +
                _id                                + " INTEGER PRIMARY KEY," +
                ID_LEAGUE                          + " INTEGER," +
                LEAGUE_NAME                        + " TEXT, " +
                SPORT_TYPE                         + " TEXT, " +
                LEAGUE_ALT_NAME                    + " TEXT  " +
                ")";
        db.execSQL(mQuery);

        mQuery2 = "CREATE TABLE " + TABLE_LD + " (" +
                _id2                               + " INTEGER PRIMARY KEY," +
                ID_LEAGUE_D                        + " INTEGER," +
                SPORT_TYPE_D                       + " TEXT, " +
                LEAGUE_NAME_D                      + " TEXT, " +
                LEAGUE_ALT_NAME_D                  + " TEXT, " +
                CURRENT_SEASON                     + " TEXT, " +
                FORMED_YEAR                        + " TEXT, " +
                DATE_FIRST_EVENT                   + " TEXT, " +
                COUNTRY                            + " TEXT, " +
                WEBSITE                            + " TEXT, " +
                FACEBOOK                           + " TEXT, " +
                TWITTER                            + " TEXT, " +
                YOUTUBE                            + " TEXT, " +
                DESCRIPTION                        + " TEXT, " +
                BANNER                             + " TEXT, " +
                BADGE                              + " TEXT, " +
                LOGO                               + " TEXT  " +
                ")";
        db.execSQL(mQuery2);

        mQuery3 = "CREATE TABLE " + TABLE_LS + " (" +
                _id3                               + " INTEGER PRIMARY KEY," +
                SEASON                             + " TEXT " +
                ")";
        db.execSQL(mQuery3);

        mQuery4 = "CREATE TABLE " + TABLE_DT + " (" +
                _id4                               + " INTEGER PRIMARY KEY," +
                ID_TEAM                            + " INTEGER," +
                TEAM_NAME                          + " TEXT, " +
                TEAM_NAME_SHORT                    + " TEXT, " +
                TEAM_ALT_NAME                      + " TEXT, " +
                DT_FORMED_YEAR                     + " TEXT, " +
                DT_SPORT_TYPE                      + " TEXT, " +
                LEAGUE_1                           + " TEXT, " +
                LEAGUE_2                           + " TEXT, " +
                LEAGUE_3                           + " TEXT, " +
                LEAGUE_4                           + " TEXT, " +
                LEAGUE_5                           + " TEXT, " +
                STADIUM                            + " TEXT, " +
                KEYWORDS                           + " TEXT, " +
                RSS                                + " TEXT, " +
                STADIUM_THUMB                      + " TEXT, " +
                STADIUM_DESC                       + " TEXT, " +
                STADIUM_LOC                        + " TEXT, " +
                STADIUM_CAP                        + " TEXT, " +
                DT_WEBSITE                         + " TEXT, " +
                DT_FACEBOOK                        + " TEXT, " +
                DT_TWITTER                         + " TEXT, " +
                INSTAGRAM                          + " TEXT, " +
                DESC_EN                            + " TEXT, " +
                DT_COUNTRY                         + " TEXT, " +
                TEAM_BADGE                         + " TEXT, " +
                TEAM_JERSEY                        + " TEXT, " +
                TEAM_BANNER                        + " TEXT  " +
                ")";
        db.execSQL(mQuery4);

        mQuery5 = "CREATE TABLE " + TABLE_SK + " (" +
                _id5                               + " INTEGER PRIMARY KEY," +
                RANK                               + " INTEGER," +
                TEAM_BADGE_S                       + " TEXT, " +
                TEAM_NAME_S                        + " TEXT, " +
                PLAYED                             + " TEXT, " +
                WIN                                + " TEXT, " +
                DRAW                               + " TEXT, " +
                LOSS                               + " TEXT, " +
                GOALS_FOR                          + " TEXT, " +
                GOALS_AGAINST                      + " TEXT, " +
                GOAL_DIFFERENCE                    + " TEXT, " +
                POINTS                             + " TEXT, " +
                FORM                               + " TEXT  " +
                ")";
        db.execSQL(mQuery5);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("NEW",""+newVersion);
        Log.d("OLD",""+oldVersion);
        if(newVersion>oldVersion){
            db.execSQL("DROP TABLE " + TABLE_AL);
            db.execSQL("DROP TABLE " + TABLE_LD);
            db.execSQL("DROP TABLE " + TABLE_LS);
            db.execSQL("DROP TABLE " + TABLE_DT);
            db.execSQL("DROP TABLE " + TABLE_SK);
            onCreate(db);
        }
    }

    public void deleteAL() {
        SQLiteDatabase db = this.getWritableDatabase();
        mQuery = "DELETE FROM tb_al";
        db.execSQL(mQuery);
    }

    public void insertAL(ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("insert", "" + values.toString());
        db.insert("tb_al", null, values);
    }

    public Cursor selectAL() {
        SQLiteDatabase db = this.getWritableDatabase();
        mQuery = "SELECT * FROM tb_al";
        Cursor c = db.rawQuery(mQuery, null);
        return c;
    }

    public Cursor selectALSearch(String search) {
        SQLiteDatabase db = this.getWritableDatabase();
        mQuery = "SELECT * FROM tb_al WHERE LEAGUE_NAME LIKE '%"+search+"%' OR SPORT_TYPE LIKE '%"+search+"%' OR LEAGUE_ALT_NAME LIKE '%\"+search+\"%' ";
        Cursor c = db.rawQuery(mQuery, null);
        return c;
    }

    public void deleteLD() {
        SQLiteDatabase db = this.getWritableDatabase();
        mQuery2 = "DELETE FROM tb_ld";
        db.execSQL(mQuery2);
    }

    public void insertLD(ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("insert", "" + values.toString());
        db.insert("tb_ld", null, values);
    }

    public Cursor selectLD() {
        SQLiteDatabase db = this.getWritableDatabase();
        mQuery2 = "SELECT ID_LEAGUE_D, SPORT_TYPE_D, LEAGUE_NAME_D, LEAGUE_ALT_NAME_D, CURRENT_SEASON, FORMED_YEAR, DATE_FIRST_EVENT, COUNTRY, WEBSITE, FACEBOOK, TWITTER, YOUTUBE, DESCRIPTION, BANNER, BADGE, LOGO FROM tb_ld";
        Cursor c = db.rawQuery(mQuery2, null);
        return c;
    }

    public void deleteLS() {
        SQLiteDatabase db = this.getWritableDatabase();
        mQuery3 = "DELETE FROM tb_ls";
        db.execSQL(mQuery3);
    }

    public void insertLS(ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("insert", "" + values.toString());
        db.insert("tb_ls", null, values);
    }

    public Cursor selectLS() {
        SQLiteDatabase db = this.getWritableDatabase();
        mQuery3 = "SELECT * FROM tb_ls";
        Cursor c = db.rawQuery(mQuery3, null);
        return c;
    }

    public void deleteDT() {
        SQLiteDatabase db = this.getWritableDatabase();
        mQuery4 = "DELETE FROM tb_dt";
        db.execSQL(mQuery4);
    }

    public void insertDT(ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("insert", "" + values.toString());
        db.insert("tb_dt", null, values);
    }

    public Cursor selectDT() {
        SQLiteDatabase db = this.getWritableDatabase();
        mQuery4 = "SELECT * FROM tb_dt";
        Cursor c = db.rawQuery(mQuery4, null);
        return c;
    }

    public void deleteSK() {
        SQLiteDatabase db = this.getWritableDatabase();
        mQuery5 = "DELETE FROM tb_sk";
        db.execSQL(mQuery5);
    }

    public void insertSK(ContentValues values) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("insert", "" + values.toString());
        db.insert("tb_sk", null, values);
    }

    public Cursor selectSK() {
        SQLiteDatabase db = this.getWritableDatabase();
        mQuery5 = "SELECT * FROM tb_sk";
        Cursor c = db.rawQuery(mQuery5, null);
        return c;
    }

}
