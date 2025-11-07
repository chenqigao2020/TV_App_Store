package com.app.ui.ranking.division.app;

import com.app.bean.App;
import com.app.bean.AppClassify;

import java.util.List;

public class Ranking {

    public AppClassify classification;

    public List<App> apps;

    public Ranking(AppClassify classification, List<App> apps) {
        this.classification = classification;
        this.apps = apps;
    }
}
