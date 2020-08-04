package com.register.me.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.register.me.R;
import com.register.me.model.data.model.ActiveCompProject;
import com.register.me.model.data.model.KeyValue;
import com.register.me.model.data.model.RREApplication;
import com.register.me.model.data.model.ViewActCompProject;
import com.register.me.presenter.HomePresenter;
import com.register.me.view.activity.WelcomeFragment;
import com.register.me.view.fragmentmanager.FragmentChannel;
import com.register.me.view.fragmentmanager.manager.FragmentManagerHandler;
import com.register.me.view.fragments.CRRE.BankDetailsFragment;
import com.register.me.view.fragments.CRRE.CertificateFragment;
import com.register.me.view.fragments.CRRE.MyActiveAuctionsFragment;
import com.register.me.view.fragments.CRRE.MySuccessStory;
import com.register.me.view.fragments.Client.DashBoardFragment;
import com.register.me.view.fragments.Client.PaymentScreen;
import com.register.me.view.fragments.Client.ProductInfoScreen;
import com.register.me.view.fragments.Client.activeProjects.ActiveProjectCompFragment;
import com.register.me.view.fragments.Client.activeProjects.ActiveProjectsFragment;
import com.register.me.view.fragments.Client.activeProjects.CommentFragment;
import com.register.me.view.fragments.Client.activeProjects.ProjectAssignFragment;
import com.register.me.view.fragments.Client.auctions.AuctionFragment;
import com.register.me.view.fragments.Client.portfolio.PortFolioFragment;
import com.register.me.view.fragments.Client.portfolio.addProducts.AddProductFragment;
import com.register.me.view.fragments.Client.portfolio.country.CountryFragment;
import com.register.me.view.fragments.Client.portfolio.directAssignment.CRREDirectFragment;
import com.register.me.view.fragments.Client.portfolio.initiateProductRegistration.InitiateRegistrationFragment;
import com.register.me.view.fragments.Client.portfolio.viewProductDetails.ViewProductDetailsFragment;
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
import com.register.me.view.fragments.RRE.applicationSubmission.ApplicationSubmissionFragment;
import com.register.me.view.fragments.RRE.applicationSubmission.DocumentFragment;
import com.register.me.view.fragments.RRE.applicationSubmission.PersonalInfoFragment;
import com.register.me.view.fragments.RRE.onlineInterview.OnlineInterviewFragment;
import com.register.me.view.fragments.RRE.policytraining.PolicyTrainingFragment;
import com.register.me.view.fragments.navigation.ChangePasswordFragment;
import com.stripe.android.model.ShippingInformation;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.Gravity.LEFT;


public class HomeActivity extends BaseActivity implements HomePresenter.View, FragmentChannel, NavigationView.OnNavigationItemSelectedListener {

    @Inject
    FragmentManagerHandler fragmentManagerHandler;
    @Inject
    HomePresenter homePresenter;
    @BindView(R.id.activity_home_fl_container)
    FrameLayout flContainer;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.tv_header_home)
    TextView mHeaderText;
    @BindView(R.id.img_back_pressed)
    ImageView mBackPressed;
    @BindView(R.id.layout_welcome)
    LinearLayout layout_welcome;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.img_user_profile)
    ImageView userProfile;
    @BindView(R.id.img_user_notification)
    ImageView notification;
    @BindView(R.id.relative_header)
    ConstraintLayout header;
    private CircleImageView profileImage;
    private boolean isProfileClicked = false;
    private boolean showChangePassword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        injector().inject(this);

        fragmentManagerHandler.setFragmentContainerId(flContainer);
        homePresenter.setView(this);
        homePresenter.init(this);
        showWelcomeScreen();
        navigationView.setNavigationItemSelectedListener(this);
        setNavigationHeader();
        updateProfileImage(null);

    }

    private void setNavigationHeader() {
        View hView = navigationView.getHeaderView(0);
        profileImage = (CircleImageView) hView.findViewById(R.id.profile_image);
        TextView profileName = (TextView) hView.findViewById(R.id.txt_profile_name);
        String profileImage = homePresenter.getProfileImage();
        if (!profileImage.isEmpty()) {
            Glide.with(this).load(profileImage).diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true).into(this.profileImage);
        }

        profileName.setText(homePresenter.getUserName());
        this.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.this.profileImage.setClickable(false);
                homePresenter.pickImage();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                if(fragmentManagerHandler.getStack()>1){
                popUpAll();
                showWelcomeScreen(); }
                break;
            case R.id.nav_change_password:
                if(!showChangePassword) {
                    showChangePassword();
                }else {
                    String ctFragment = fragmentManagerHandler.getFragmentName();
                    if(!ctFragment.equals("ChangePassword")){
                        fragmentManagerHandler.popUpto("ChangePassword");
                    }
                }
                break;
            case R.id.nav_logout:
                homePresenter.logout();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }
        isProfileClicked=false;
        mDrawerLayout.closeDrawer(LEFT);
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            homePresenter.pickImage();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode==100 &&resultCode == RESULT_OK &&
                    data != null && data.getData() != null) {
                Uri imageUri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                homePresenter.apiUpdateAvatar(bitmap);
                bitmap.recycle();
            }
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        fragmentManagerHandler.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fragmentManagerHandler.onRestoreInstanceState(savedInstanceState);
    }


    @OnClick(R.id.img_user_profile)
    public void onClickUserProfile() {
        if (!isProfileClicked) {
            isProfileClicked = true;
            showPersonalInfo();
        }
    }

    @OnClick(R.id.img_user_notification)
    public void onClickNotification() {
        Toast.makeText(this, "Notification Clicked", Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.img_nav_click)
    public void onNavClick() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @OnClick(R.id.img_back_pressed)
    public void onClickBackpressed() {
        onBackPressed();
    }


    @Override
    public void popUp() {
        if (fragmentManagerHandler.getFragmentName().equals("PersonalInfo")) {
            isProfileClicked = false;
        }
        fragmentManagerHandler.popUp();
    }

    @Override
    public void popUpAll() {
        fragmentManagerHandler.popUpAll();
    }

    @Override
    public void updateNavigation() {
        setNavigationHeader();
    }

    @Override
    public void showWelcomeScreen() {
        fragmentManagerHandler.popUpAll();
        fragmentManagerHandler.replaceFragment(WelcomeFragment.newInstance(), this);
    }

    @Override
    public void showHome() {
        showClientDashBoard();
    }

    @Override
    public void setTitle(String title) {
        if (mBackPressed != null) {
            if(!title.isEmpty()) {
                layout_welcome.setVisibility(View.GONE);
                mBackPressed.setVisibility(View.VISIBLE);
                header.setBackground(getResources().getDrawable(R.drawable.view_bg_black,null));
            }else {
                layout_welcome.setVisibility(View.VISIBLE);
                mBackPressed.setVisibility(View.GONE);
                header.setBackgroundColor(getResources().getColor(R.color.transparent));
            }
            if (mHeaderText != null) {
                mHeaderText.setText(title.toUpperCase());
            }
        }
    }

    @Override
    public void showChangePassword() {
        showChangePassword = true;
        fragmentManagerHandler.replaceFragment(ChangePasswordFragment.newInstance(), this);
    }

    @Override
    public void showPortFolio() {
        fragmentManagerHandler.replaceFragment(PortFolioFragment.newInstance(), this);
    }

    @Override
    public void showAddProduct() {
        fragmentManagerHandler.replaceFragment(AddProductFragment.newInstance(), this);
    }


    @Override
    public void showViewProductDetails() {
        fragmentManagerHandler.replaceFragment(ViewProductDetailsFragment.newInstance(), this);
    }

    @Override
    public void showClientDashBoard() {
        fragmentManagerHandler.replaceFragment(DashBoardFragment.newInstance(), this);
    }

    @Override
    public void showNewProject() {
        showAddProduct();
    }

    @Override
    public void showRREDashBoard() {
        showRREDashboard();
    }

    @Override
    public void showOnlineInter() {
        showOnlineInterView();
    }

    @Override
    public void showPolicyTraining() {
        showPolicyTrainingScreen();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void updateProfileImage(String url) {

        if (url == null) {
            url = homePresenter.getProfileImage();
        }
        profileImage.setClickable(true);

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);
        if (!url.isEmpty()) {
            Glide.with(this)
                    .applyDefaultRequestOptions(options)
                    .load(url)
                    .into(profileImage);
        }
    }

    @Override
    public void showActiveProjects() {
        fragmentManagerHandler.replaceFragment(ActiveProjectsFragment.newInstance(), this);
    }

    @Override
    public void showInitiateProductRegistration() {
        fragmentManagerHandler.replaceFragment(InitiateRegistrationFragment.newInstance(), this);
    }

    @Override
    public void showDirectAssignment(String productName) {
        fragmentManagerHandler.replaceFragment(CRREDirectFragment.newInstance(productName), this);
    }

    @Override
    public void showAuctions() {
        fragmentManagerHandler.replaceFragment(AuctionFragment.newInstance(), this);
    }

    @Override
    public void showActiveProjectsSub() {
        fragmentManagerHandler.replaceFragment(ActiveProjectCompFragment.newInstance(), this);
    }

    @Override
    public void showCountryScreen() {
        fragmentManagerHandler.replaceFragment(CountryFragment.newInstance(), this);
    }

    @Override
    public void showRREDashboard() {
        fragmentManagerHandler.replaceFragment(ApplicationSubmissionFragment.newInstance(), this);
        setTitle(getResources().getString(R.string.rre_dashboard));
    }

    @Override
    public void showOnlineInterView() {
        fragmentManagerHandler.replaceFragment(OnlineInterviewFragment.newInstance(), this);
    }

    @Override
    public void showPersonalInfo() {
        fragmentManagerHandler.replaceFragment(PersonalInfoFragment.newInstance(), this);
    }

    @Override
    public void showProjectAssign(String name, int locationid, String region) {
        fragmentManagerHandler.replaceFragment(ProjectAssignFragment.newInstance(name, locationid, region), this);
    }

    @Override
    public void showCommentScreen(List<ViewActCompProject.Comment> comments, int projectAssignId) {
        fragmentManagerHandler.replaceFragment(CommentFragment.newInstance(comments, projectAssignId), this);
    }

    @Override
    public void showMCommentScreen(List<RREApplication.Comment> comments, int projectAssignId) {
        fragmentManagerHandler.replaceFragment(CommentFragment.newMInstance(comments, projectAssignId), this);
    }

    @Override
    public void showPolicyTrainingScreen() {
        fragmentManagerHandler.replaceFragment(PolicyTrainingFragment.newInstance(), this);
    }

    @Override
    public void showCertification() {
        fragmentManagerHandler.replaceFragment(CertificationFragment.newInstance(), this);
    }

    @Override
    public void ActiveAuctions() {
        showMyActiveAuctions();
    }

    @Override
    public void showStory() {
        showMySuccessStory();
    }

  /*  @Override
    public void showCRRECertificate() {
        showCertificationStatus();
    }*/

    @Override
    public void ActiveProjects() {
        showActiveProjectsSub();
    }

    @Override
    public void CompletedProjects() {
        showActiveProjectsSub();
    }

    @Override
    public void showClientDetails() {
        fragmentManagerHandler.replaceFragment(ClientListFragment.newInstance(), this);
    }

    @Override
    public void showRREDetails() {
        fragmentManagerHandler.replaceFragment(RREListFragment.newInstance(), this);
    }

    @Override
    public void showCRREDetails() {
        fragmentManagerHandler.replaceFragment(CRREListFragment.newInstance(), this);
    }

    @Override
    public void showAuctionsWon() {
        fragmentManagerHandler.replaceFragment(AuctionsWonFragment.newInstance(), this);
    }

    @Override
    public void showMRREDetails() {
        fragmentManagerHandler.replaceFragment(MasterCRREListFragment.newInstance(), this);
    }

    @Override
    public void showMasterAvailability() {
        fragmentManagerHandler.replaceFragment(MasterAvailabilityFragment.newInstance(), this);
    }

    @Override
    public void showGeoLocation() {
        fragmentManagerHandler.replaceFragment(GeoLocationFragment.newInstance(), this);
    }

    @Override
    public void showRequestedRegion() {
        fragmentManagerHandler.replaceFragment(RequestedRegionFragment.newInstance(), this);
    }


    @Override
    public void showMyActiveAuctions() {
        fragmentManagerHandler.replaceFragment(MyActiveAuctionsFragment.newInstance(), this);
    }


    @Override
    public void showMySuccessStory() {
        fragmentManagerHandler.replaceFragment(MySuccessStory.newInstance(), this);
    }

    @Override
    public void showCertificationStatus() {
        fragmentManagerHandler.replaceFragment(CertificateFragment.newInstance(), this);
    }

    @Override
    public void showPersonalLibrary(int position) {
        fragmentManagerHandler.replaceFragment(DocumentFragment.newInstance(position), this);
    }

    @Override
    public void showMasterViewDetails(Integer screen, Integer Id, String appID, ArrayList<KeyValue> list) {
        fragmentManagerHandler.replaceFragment(MasterViewDetailFragment.newInstance(screen, Id, appID, list), this);
    }

    @Override
    public void showAddAvailabilityFragment() {
        fragmentManagerHandler.replaceFragment(AddAvailabilityFragment.newInstance(), this);
    }

    @Override
    public void showReqRegionList() {
        fragmentManagerHandler.replaceFragment(RequestedRegionFragment.newInstance(), this);
    }

    @Override
    public void showProductInfo(ActiveCompProject.ActiveProjectDetail actProject) {
        fragmentManagerHandler.replaceFragment(ProductInfoScreen.newInstance(actProject),this);
    }

    @Override
    public void showPaymnetScreen(String nextdueamount, ShippingInformation shippingInfo) {
        fragmentManagerHandler.replaceFragment(PaymentScreen.newInstance(nextdueamount,shippingInfo),this);
    }

    @Override
    public void showBankDetails() {
        fragmentManagerHandler.replaceFragment(BankDetailsFragment.newInstance(),this);
    }

    @Override
    public void welcomeRedirect() {
        homePresenter.redirectForWelcomeScreen();
    }

    @Override
    public void onBackPressed() {
        if (isProfileClicked) {
            isProfileClicked = false;
        }

        final int stack = fragmentManagerHandler.getStack();
        String fName=fragmentManagerHandler.getFragmentName();
        Log.d("fName",fName);
        if(fName.equals("ChangePassword")){
            showChangePassword=false;
        }
        if (stack > 0) {
            fragmentManagerHandler.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        profileImage.setClickable(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("HomeActivity", "OnPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("HomeActivity", "OnPause");
    }
}
