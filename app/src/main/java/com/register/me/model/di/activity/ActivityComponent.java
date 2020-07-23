package com.register.me.model.di.activity;

import com.register.me.APIs.CRRENetworkCall;
import com.register.me.APIs.MasterNetworkCall;
import com.register.me.APIs.RRENetworkCall;
import com.register.me.SampleActivity;
import com.register.me.presenter.ActiveAuctionPresenter;
import com.register.me.presenter.ActiveCompProjectPresenter;
import com.register.me.presenter.AddAvailabilityPresenter;
import com.register.me.presenter.AddProductPresenter;
import com.register.me.presenter.AuctionsWonPresenter;
import com.register.me.presenter.CRREDetailPresenter;
import com.register.me.presenter.ChangePasswordPresenter;
import com.register.me.presenter.CommentPresenter;
import com.register.me.presenter.CountryPresenter;
import com.register.me.presenter.DocumentPresenter;
import com.register.me.presenter.GeoLocationPresenter;
import com.register.me.presenter.HomePresenter;
import com.register.me.presenter.InitiateRegistrationPresenter;
import com.register.me.presenter.LoginPresenter;
import com.register.me.presenter.MasterAvailabilityPresenter;
import com.register.me.presenter.MasterCRREDetailPresenter;
import com.register.me.presenter.MasterViewDetailPresenter;
import com.register.me.presenter.OnlineInterviewPresenter;
import com.register.me.presenter.PaymentPresenter;
import com.register.me.presenter.PersonalInfoPresenter;
import com.register.me.presenter.PolicyPresenter;
import com.register.me.presenter.PortFolioPresenter;
import com.register.me.presenter.ProductInfoPresenter;
import com.register.me.presenter.ProjectAssignPresenter;
import com.register.me.presenter.RREListPresenter;
import com.register.me.presenter.RequestedRegionPresenter;
import com.register.me.presenter.SignUpPresenter;
import com.register.me.presenter.ViewProductPresenter;
import com.register.me.presenter.WelcomePresenter;
import com.register.me.view.HomeActivity;
import com.register.me.view.activity.LoginActivity;
import com.register.me.view.activity.SignUpActivity;
import com.register.me.view.activity.WelcomeActivity;
import com.register.me.view.fragments.CRRE.BankDetailsFragment;
import com.register.me.view.fragments.CRRE.MyActiveAuctionsFragment;
import com.register.me.view.fragments.CRRE.MySuccessStory;
import com.register.me.view.fragments.Client.PaymentScreen;
import com.register.me.view.fragments.Client.ProductInfoScreen;
import com.register.me.view.fragments.Client.activeProjects.ActiveProjectCompFragment;
import com.register.me.view.fragments.Client.activeProjects.CommentFragment;
import com.register.me.view.fragments.Client.activeProjects.ProjectAssignFragment;
import com.register.me.view.fragments.Client.auctions.ActiveAuctionFragment;
import com.register.me.view.fragments.Client.auctions.BidsToEvaluateFragment;
import com.register.me.view.fragments.Master.AddAvailabilityFragment;
import com.register.me.view.fragments.Master.AuctionsWonFragment;
import com.register.me.view.fragments.Master.CRREListFragment;
import com.register.me.view.fragments.Master.ClientListFragment;
import com.register.me.view.fragments.Master.GeoLocationFragment;
import com.register.me.view.fragments.Master.MasterAvailabilityFragment;
import com.register.me.view.fragments.Master.MasterCRREListFragment;
import com.register.me.view.fragments.Master.MasterViewDetailFragment;
import com.register.me.view.fragments.Master.RREListFragment;
import com.register.me.view.fragments.Master.RequestedRegionFragment;
import com.register.me.view.fragments.RRE.Certification.CertificationFragment;
import com.register.me.view.fragments.RRE.applicationSubmission.DocumentFragment;
import com.register.me.view.fragments.RRE.applicationSubmission.PersonalInfoFragment;
import com.register.me.view.fragments.RRE.onlineInterview.OnlineInterviewFragment;
import com.register.me.view.fragments.Client.DashBoardFragment;
import com.register.me.view.fragments.Client.activeProjects.ActiveProjectsFragment;
import com.register.me.view.fragments.Client.auctions.AuctionFragment;
import com.register.me.view.fragments.Client.portfolio.PortFolioFragment;
import com.register.me.view.fragments.Client.portfolio.addProducts.AddProductFragment;
import com.register.me.view.fragments.Client.portfolio.country.CountryFragment;
import com.register.me.view.fragments.Client.portfolio.directAssignment.CRREDirectFragment;
import com.register.me.view.fragments.Client.portfolio.initiateProductRegistration.InitiateRegistrationFragment;
import com.register.me.view.fragments.Client.portfolio.viewProductDetails.ViewProductDetailsFragment;
import com.register.me.view.fragments.RRE.applicationSubmission.ApplicationSubmissionFragment;
import com.register.me.view.fragments.RRE.policytraining.PolicyTrainingFragment;
import com.register.me.view.fragments.navigation.ChangePasswordFragment;

import dagger.Subcomponent;


@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {

    void inject(HomeActivity homeActivity);

    void inject(SampleActivity sampleActivity);

    void inject(LoginActivity loginActivity);

    void inject(SignUpActivity signUpActivity);

    void inject(DashBoardFragment dashBoardFragment);

    void inject(WelcomeActivity welcomeActivity);

    void inject(PortFolioFragment portfolioFragment);

    void inject(AddProductFragment addProductFragment);

    void inject(ViewProductDetailsFragment viewProductDetailsFragment);

    void inject(ActiveProjectsFragment activeProjectsFragment);

    void inject(InitiateRegistrationFragment initiateRegistrationFragment);

    void inject(CRREDirectFragment crreDirectFragment);

    void inject(AuctionFragment auctionFragment);

    void inject(ActiveProjectCompFragment activeProjectMenuFragment);

    void inject(CountryFragment countryFragment);

    void inject(ApplicationSubmissionFragment submissionFragment);

    void inject(OnlineInterviewFragment onlineInterviewFragment);

    void inject(ChangePasswordFragment changePasswordFragment);

    void inject(PersonalInfoFragment personalInfoFragment);

    void inject(ActiveAuctionFragment activeAuctionFragment);

    void inject(BidsToEvaluateFragment bidsToEvaluateFragment);

    void inject(ProjectAssignFragment projectAssignFragment);

    void inject(CommentFragment commentFragment);

    void inject(DocumentFragment DocumentFragment);

    void inject(PolicyTrainingFragment policyTrainingScreen);

    void inject(CertificationFragment certificationFragment);

    void inject(MyActiveAuctionsFragment myActiveAuctionsFragment);

    void inject(MySuccessStory mySuccessStory);

    void inject(ClientListFragment clientDetailFragment);

    void inject(RREListFragment rreDetailFragment);

    void inject(CRREListFragment crreListFragment);

    void inject(MasterViewDetailFragment masterViewDetailFragment);

    void inject(MasterAvailabilityFragment masterAvailabilityFragment);

    void inject(AddAvailabilityFragment   addAvailabilityFragment);

    void inject(GeoLocationFragment GeoLocationFragmentFragment);

    void inject(RequestedRegionFragment requestedRegionFragment);

    void inject(MasterCRREListFragment masterCRREDetailFragment);




    /*Presenter*/
    void inject(LoginPresenter loginPresenter);

    void inject(SignUpPresenter signUpPresenter);

    void inject(HomePresenter homePresenter);

    void inject(ChangePasswordPresenter changePasswordPresenter);

    void inject(PersonalInfoPresenter personalInfoPresenter);

    void inject(PortFolioPresenter portFolioPresenter);

    void inject(AddProductPresenter addProductPresenter);

    void inject(ViewProductPresenter viewProductPresenter);

    void inject(CountryPresenter countryPresenter);

    void inject(WelcomePresenter welcomePresenter);

    void inject(InitiateRegistrationPresenter registrationPresenter);

    void inject(ActiveAuctionPresenter activeAuctionPresenter);

    void inject(ActiveCompProjectPresenter activeCompProjectPresenter);

    void inject(ProjectAssignPresenter projectAssignPresenter);

    void inject(CommentPresenter commentPresenter);

    void inject(DocumentPresenter documentPresenter);

    void inject(OnlineInterviewPresenter onlineInterviewPresenter);

    void inject(PolicyPresenter policyPresenter);

    void inject(MasterViewDetailPresenter masterViewDetailPresenter);

    void inject(MasterAvailabilityPresenter masterAvailabilityPresenter);

    void inject(AddAvailabilityPresenter addAvailabilityPresenter);

    void inject(GeoLocationPresenter  geoLocationPresenter);

    void inject(RequestedRegionPresenter requestedRegionPresenter);

    void inject(MasterCRREDetailPresenter masterCRREDetailPresenter);

    void inject(CRREDetailPresenter crreDetailPresenter);

    void inject(RREListPresenter rreListPresenter);


    void inject(RRENetworkCall rreNetworkCall);

    void inject(CRRENetworkCall crreNetworkCall);

    void inject(MasterNetworkCall masterNetworkCall);

    void inject(AuctionsWonFragment auctionsWonFragment);

    void inject(AuctionsWonPresenter auctionsWonPresenter);

    void inject(ProductInfoScreen productInfoScreen);

    void inject(ProductInfoPresenter productInfoPresenter);

    void inject(PaymentScreen paymentScreen);

    void inject(PaymentPresenter paymentPresenter);

    void inject(BankDetailsFragment bankDetailsFragment);


    @Subcomponent.Builder
    interface Builder {
        Builder activityModule(ActivityModule activityModule);

        ActivityComponent build();
    }
}
