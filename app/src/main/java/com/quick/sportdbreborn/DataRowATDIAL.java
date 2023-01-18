package com.quick.sportdbreborn;

public class DataRowATDIAL {
    String M_TEAM_NAME;
    String M_TEAM_BADGE;
    String _id;

    void setData(String TEAM_NAME,
                 String TEAM_BADGE,
                 String id){
        M_TEAM_NAME             = TEAM_NAME;
        M_TEAM_BADGE            = TEAM_BADGE;
        _id                     = id;
    }
}
