package com.quick.sportdbreborn;

public class DataRowSK {
    String M_RANK;
    String M_TEAM_NAME;
    String M_TEAM_BADGE;
    String M_PLAYED;
    String M_WIN;
    String M_DRAW;
    String M_LOSS;
    String M_GOALS_FOR;
    String M_GOALS_AGAINST;
    String M_GOAL_DIFFERENCE;
    String M_POINTS;
    String M_FORM;
    String _id;

    void setData(String RANK,
                 String TEAM_NAME,
                 String TEAM_BADGE,
                 String PLAYED,
                 String WIN,
                 String DRAW,
                 String LOSS,
                 String GOALS_FOR,
                 String GOALS_AGAINST,
                 String GOAL_DIFFERENCE,
                 String POINTS,
                 String FORM,
                 String id){
        M_RANK                  = RANK;
        M_TEAM_NAME             = TEAM_NAME;
        M_TEAM_BADGE            = TEAM_BADGE;
        M_PLAYED                = PLAYED;
        M_WIN                   = WIN;
        M_DRAW                  = DRAW;
        M_LOSS                  = LOSS;
        M_GOALS_FOR             = GOALS_FOR;
        M_GOALS_AGAINST         = GOALS_AGAINST;
        M_GOAL_DIFFERENCE       = GOAL_DIFFERENCE;
        M_POINTS                = POINTS;
        M_FORM                  = FORM;
        _id                     = id;
    }
}
