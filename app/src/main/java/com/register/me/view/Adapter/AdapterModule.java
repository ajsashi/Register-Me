package com.register.me.view.Adapter;

import com.register.me.model.data.model.ScheduleList;

import dagger.Module;
import dagger.Provides;

@Module
public class AdapterModule {
    @Provides
    ProductAdapter provideProductStatusAdapter() {
        return new ProductAdapter();
    }

    @Provides
    AuctionAdapter provideAuctionAdapter() {
        return new AuctionAdapter();
    }

    @Provides
    ProjectAdapter provideProjectAdapter() {
        return new ProjectAdapter();
    }

    @Provides
    CrreAuctionAdapter provideCrreAuctionAdapter() { return new CrreAuctionAdapter();}

    @Provides
    LibraryAdapter provideLibraryAdapter() { return new LibraryAdapter();}

    @Provides
    MasterCRREAdapter provideClientAdapter() { return new MasterCRREAdapter();}

    @Provides
    ProductListAdapter provideProductListAdapter() { return new ProductListAdapter();}

    @Provides
    ScheduleListAdapter provideScheduleListAdapter(){return new ScheduleListAdapter();}

    @Provides
    GeoListAdapter provideGeoListAdapter(){return new GeoListAdapter();}

    @Provides
    ReqRegionAdapter provideReqRegionAdapter(){return new ReqRegionAdapter();}

    @Provides
    AuctionsWonAdapter provideAuctionsWonAdapter(){return new AuctionsWonAdapter();}
}
