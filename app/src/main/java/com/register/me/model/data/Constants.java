package com.register.me.model.data;


import com.register.me.model.data.model.ActiveAuction;
import com.register.me.model.data.model.GetProductModel;
import com.register.me.model.data.model.RREApplication;
import com.register.me.model.data.repository.CacheRepo;

import java.util.List;

public class Constants {

    public static final String ERROR = "error";
    public static final String EMPTY_STRING = "";
    public static final String APPLICATION_CONTEXT = "application";
    public static final String ACTIVITY_CONTEXT = "activity";
    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_PER_PAGE = 40;
    public static final String ANDROID_TAG = "android";

    /*
     * 1 - Screen from Project Portfolio
     * 2 - Screen from Country List / Active Auction
     * 3 - Screen from Active Complete Project
     * 4 - Screen from CRRE Active Auction
     * 5 - Screen from MCRRE Client Product View
     * */
    private int VIEW_SCREEN_FROM;

    /*
     * 0 - Role client
     * 1 - Role RRE
     * 2 - Role CRRE
     * 3-
     * */
    private int USER_ROLE;

    /*
     * 1 - tab 1
     * 2 - tab -2
     * 3 - tab -3
     * 4 - tab -4
     * */

    private int TAB;

    private String productID;

    private String projectID;

//    private String CRRE_projectID;

    private boolean isActiveAuction;
    private boolean isStory;
    private boolean isCertificate;
    private boolean isLibrary;
    private int MasterScreen;


    /*
     * CACHE REPO CONSTANTS*/

    private String CACHE_IS_LOGGED = "isLogged";
    private String CACHE_USERNAME = "username";
    private String CACHE_ROLE = "role";
    private String CACHE_TOKEN = "token";
    private String CACHE_TOKEN_TYPE = "token_type";
    private String CACHE_USER_PROFILE_URL = "profile_url";
    private String CACHE_USER_INFO = "user_profile";
    private String CACHE_SELECTED_TAB = "selected_tab";
    private String CACHE_APPLICATION_DATA="application_data";


//    public static String BASE_URL = "http://192.168.88.62:8092/api/";

    private GetProductModel.Product selectedList;
    private List<GetProductModel.Product> BASE_PRODUCT_LIST;
    List<ActiveAuction.Bidsreadytoevaluate_> BidList;

    private boolean isAcitiveProject;
    private RREApplication ApplicationData;



    /******************************* Getter Setter ******************************************/


    public static String getERROR() {/**/
        return ERROR;
    }

    public static String getEmptyString() {
        return EMPTY_STRING;
    }

    public static String getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

    public static String getActivityContext() {
        return ACTIVITY_CONTEXT;
    }

    public static int getDefaultPage() {
        return DEFAULT_PAGE;
    }

    public static int getDefaultPerPage() {
        return DEFAULT_PER_PAGE;
    }

    public static String getAndroidTag() {
        return ANDROID_TAG;
    }

    public int getviewScreenFrom() {
        return VIEW_SCREEN_FROM;
    }

    public void setviewScreenFrom(int VIEW_SCREEN_FROM) {
        this.VIEW_SCREEN_FROM = VIEW_SCREEN_FROM;
    }

    public int getuserRole() {
        return USER_ROLE;
    }

    public void setuserRole(int USER_ROLE) {
        this.USER_ROLE = USER_ROLE;
    }

    public int getTAB() {
        return TAB;
    }

    public void setTAB(int TAB) {
        this.TAB = TAB;
    }


    public String getcacheIsLoggedKey() {
        return CACHE_IS_LOGGED;
    }


    public String getcacheUsernameKey() {
        return CACHE_USERNAME;
    }


    public String getcacheRoleKey() {
        return CACHE_ROLE;
    }


    public String getcacheTokenKey() {
        return CACHE_TOKEN;
    }


    public String getcacheTokenTypeKey() {
        return CACHE_TOKEN_TYPE;
    }


    public String getcacheUserProfileUrlKey() {
        return CACHE_USER_PROFILE_URL;
    }

    public String getPasswordPattern() {
        return "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
    }

    public GetProductModel.Product getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(GetProductModel.Product selectedList) {
        this.selectedList = selectedList;
    }

    public List<GetProductModel.Product> getBASE_PRODUCT_LIST() {
        return BASE_PRODUCT_LIST;
    }

    public void setBASE_PRODUCT_LIST(List<GetProductModel.Product> BASE_PRODUCT_LIST) {
        this.BASE_PRODUCT_LIST = BASE_PRODUCT_LIST;
    }

    public String getCACHE_USER_INFO() {
        return CACHE_USER_INFO;
    }

    public String getCACHE_SELECTED_TAB() {
        return CACHE_SELECTED_TAB;
    }

    public String getCACHE_APPLICATION_DATA() {
        return CACHE_APPLICATION_DATA;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

 /*   public String getCRRE_projectID() {
        return CRRE_projectID;
    }

    public void setCRRE_projectID(String CRRE_projectID) {
        this.CRRE_projectID = CRRE_projectID;
    }
*/
    public boolean isActiveAuction() {
        return isActiveAuction;
    }

    public void setActiveAuction(boolean activeAuction) {
        isActiveAuction = activeAuction;
    }

    public boolean isStory() {
        return isStory;
    }

    public void setStory(boolean story) {
        isStory = story;
    }

    public boolean isCertificate() {
        return isCertificate;
    }

    public void setCertificate(boolean certificate) {
        isCertificate = certificate;
    }

    public boolean isLibrary() {
        return isLibrary;
    }

    public void setLibrary(boolean library) {
        isLibrary = library;
    }

    public int getMasterScreen() {
        return MasterScreen;
    }

    public void setMasterScreen(int masterScreen) {
        MasterScreen = masterScreen;
    }

    public List<ActiveAuction.Bidsreadytoevaluate_> getBidList() {
        return BidList;
    }

    public void setBidList(List<ActiveAuction.Bidsreadytoevaluate_> bidList) {
        BidList = bidList;
    }

    public boolean isAcitiveProject() {
        return isAcitiveProject;
    }

    public void setAcitiveProject(boolean acitiveProject) {
        isAcitiveProject = acitiveProject;
    }

    public RREApplication getApplicationData() {
        return ApplicationData;
    }

    public void setApplicationData(RREApplication applicationData) {
        ApplicationData = applicationData;
    }
}
