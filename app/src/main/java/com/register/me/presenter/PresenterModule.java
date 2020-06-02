package com.register.me.presenter;


import dagger.Module;
import dagger.Provides;


@Module()
public class PresenterModule {

    @Provides
    public LoginPresenter provideLoginPresenter() {
        return new LoginPresenter();
    }

    @Provides
    public SignUpPresenter provideSignUpPresenter() {
        return new SignUpPresenter();
    }

    @Provides
    public ChangePasswordPresenter provideChangePasswordPresenter() {
        return new ChangePasswordPresenter();
    }

    @Provides
    public HomePresenter provideHomePresenter() {
        return new HomePresenter();
    }

    @Provides
    public DashBoardPresenter provideDashBoardPresenter() {
        return new DashBoardPresenter();
    }

    @Provides
    public PortFolioPresenter providePortFolioPresenter() {
        return new PortFolioPresenter();
    }


    @Provides
    public AddProductPresenter provideAddProductPresenter() {
        return new AddProductPresenter();
    }

    @Provides
    public ViewProductPresenter provideViewProductPresenter() {
        return new ViewProductPresenter();
    }

    @Provides
    public ActiveProjectPresenter provideProjectPresenter() {
        return new ActiveProjectPresenter();
    }

    @Provides
    public InitiateRegistrationPresenter provideInitiateRegistrationPresenter() {
        return new InitiateRegistrationPresenter();
    }

    @Provides
    public PersonalInfoPresenter providePersonalInfoPresenter() {
        return new PersonalInfoPresenter();
    }

    @Provides
    public CountryPresenter provideCountryPresenter() {
        return new CountryPresenter();
    }

    @Provides
    public WelcomePresenter provideWelcomePresenter() {
        return new WelcomePresenter();
    }

    @Provides
    public ActiveAuctionPresenter provideActiveAuctionPresenter() {
        return new ActiveAuctionPresenter();
    }

    @Provides
    public ActiveCompProjectPresenter provideAuctionPresenter() {
        return new ActiveCompProjectPresenter();
    }

    @Provides
    public ProjectAssignPresenter provideProjectAssignPresenter() {
        return new ProjectAssignPresenter();
    }

    @Provides
    public CommentPresenter provideCommentPresenter() {
        return new CommentPresenter();
    }

    @Provides
    public DocumentPresenter provideDocumentPresenter() {return new DocumentPresenter();}

 @Provides
    public ActivityPresenter provideActivityPresenter() {return new ActivityPresenter();}

 @Provides
    public OnlineInterviewPresenter provideOnlineInterviewPresenter() {return new OnlineInterviewPresenter();}

 @Provides
    public PolicyPresenter providePolicyPresenter() {return new PolicyPresenter();}

    @Provides
    public MasterViewDetailPresenter provideMasterViewDetailPresenter() {return new MasterViewDetailPresenter();}

    @Provides
    public MasterAvailabilityPresenter provideMasterAvailabilityPresenter(){return new MasterAvailabilityPresenter();}

    @Provides
    public AddAvailabilityPresenter provideAddAvailabilityPresenter(){return new AddAvailabilityPresenter();}

    @Provides
    public GeoLocationPresenter provideGeoLocationPresenter(){return new GeoLocationPresenter();}

    @Provides
    public RequestedRegionPresenter provideRequestedRegionPresenter(){return new RequestedRegionPresenter();}
}
