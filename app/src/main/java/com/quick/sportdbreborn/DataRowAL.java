package com.quick.sportdbreborn;

public class DataRowAL {
    String M_ID_LEAGUE;
    String M_LEAGUE_NAME;
    String M_SPORT_TYPE;
    String M_LEAGUE_ALT_NAME;
    String _id;

    void setData(String ID_LEAGUE,
                 String LEAGUE_NAME,
                 String SPORT_TYPE,
                 String LEAGUE_ALT_NAME,
                 String id){
        M_ID_LEAGUE             = ID_LEAGUE;
        M_LEAGUE_NAME           = LEAGUE_NAME;
        M_SPORT_TYPE            = SPORT_TYPE;
        M_LEAGUE_ALT_NAME       = LEAGUE_ALT_NAME;
        _id                     = id;
    }
}
