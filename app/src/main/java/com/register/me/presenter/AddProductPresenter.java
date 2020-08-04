package com.register.me.presenter;

import android.content.Context;
import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.ClientNetworkCall;
import com.register.me.R;
import com.register.me.model.JsonBuilder;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.AddProductModel;
import com.register.me.model.data.model.GetProductModel;
import com.register.me.model.data.model.QandA;
import com.register.me.model.data.model.RREApplication;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;


public class AddProductPresenter implements ClientNetworkCall.NetworkCallInterface, Utils.UtilNetworkInterface {
    private Context context;
    private IAddProduct listener;
    @Inject
    Utils utils;
    @Inject
    Constants constants;
    @Inject
    CacheRepo repo;
    @Inject
    Retrofit retrofit;
    @Inject
    ClientNetworkCall networkCall;
    @Inject
    JsonBuilder builder;
    private List<QandA> questList;

    private boolean isEdit = false;
    private Integer productId;
    private Resources resource;

    public void init(Context context, IAddProduct listener) {
        this.context = context;
        this.listener = listener;
        ((BaseActivity) context).injector().inject(this);
        resource = context.getResources();
    }

    public List<QandA> getQuestions() {
        ArrayList<QandA> quest = new ArrayList<>();

        /* TYPE
         * case 1 : Edit Text
         * case 2 : Radio Button
         * case 3 : Spinner */

        /* INPUT TYPE
         * 1 - text
         * 2 - email
         * 3- password
         * */
        /* ACTION
         * 1 - action_next
         * 2 - action_done
         *
         * */
        quest.add(new QandA(context.getString(R.string.product_number), null, 1, 1, 1, "productnumber", null, null));
        quest.add(new QandA(context.getString(R.string.product_name), null, 1, 1, 1, "productname", null, null));
        quest.add(new QandA(context.getString(R.string.product_description), null, 1, 1, 2, "productdesc", null, null));
        quest.add(new QandA(context.getString(R.string.product_classification), null, 3, 0, 0, "deviceclassification", null, null));
        quest.add(new QandA(context.getString(R.string.is_it_idv), null, 2, 0, 0, "isivd", null, null));
        quest.add(new QandA(context.getString(R.string.is_this_a_combination_product), null, 2, 0, 0, "iscombination", null, null));
        quest.add(new QandA(context.getResources().getString(R.string.Is_the_device_life_supporting), null, 2, 0, 0, "lifesupportorsustain", null, null));
        QandA implant = new QandA(context.getResources().getString(R.string.Is_the_device_or_component_an_implant), null, 2, 0, 0, "isimplant", null, null);
        quest.add(new QandA(context.getResources().getString(R.string.are_there_any_direct_or_indirect_patient_contacting_components), null, 4, 0, 0, "patientcomponent", implant, null));
        QandA isDigital = new QandA(context.getResources().getString(R.string.is_the_device_digital_health_technology_or_does_it_contain_digital_health_technology), null, 2, 0, 0, "isdigitalhealth", null, null);
        quest.add(new QandA(context.getResources().getString(R.string.does_the_device_use_software_firmware), null, 4, 0, 0, "softorfirmware", isDigital, null));
        quest.add(new QandA(context.getString(R.string.connection_type), null, 3, 0, 0, "conntype", null, null));
        QandA sterile = new QandA(context.getResources().getString(R.string.select_the_type_of_sterile_processing), null, 3, 0, 0, "sterileprocess", null, null);
        quest.add(new QandA(context.getResources().getString(R.string.does_the_device_or_a_component_need_sterilization_by_the_manufacturer_or_user), null, 5, 0, 0, "sterilization", sterile, null));
        quest.add(new QandA(context.getString(R.string.indications_for_use), null, 3, 0, 0, "indications", null, null));
        quest.add(new QandA(context.getString(R.string.intended_population), null, 3, 0, 0, "indentpopulation", null, null));
        quest.add(new QandA(context.getString(R.string.device_type), null, 3, 0, 0, "devicetype", null, null));
        quest.add(new QandA(context.getString(R.string.usage_environment), null, 3, 0, 0, "useenvironment", null, null));
        QandA deviceType = new QandA(context.getResources().getString(R.string.select_electrical_device_type), null, 3, 0, 0, "eledevicetype", null, null);
        quest.add(new QandA(context.getString(R.string.is_the_product_electrical), null, 5, 0, 0, "iselectrical", deviceType, null));
        quest.add(new QandA(context.getString(R.string.is_the_device_for_pediatric_use), null, 2, 0, 0, "ispediatric", null, null));
        quest.add(new QandA(context.getString(R.string.does_the_device_emit_radiation), null, 2, 0, 0, "isemitrad", null, null));
        quest.add(new QandA(context.getString(R.string.do_you_have_substance_of_biological_origin_in_your_device), null, 2, 0, 0, "biolorigin", null, null));
        quest.add(new QandA(context.getString(R.string.will_the_device_be_reprocessed_for_resale), null, 2, 0, 0, "isresale", null, null));

        return quest;
    }

    public boolean getStatusAddOrEdit() {
        return constants.getSelectedList() == null;
    }

    public String getLable() {
        return constants.getSelectedList() == null ? resource.getString(R.string.add_product) : resource.getString(R.string.update_product);
    }

    public void setIsEdit() {
        isEdit = true;
    }

    public List<QandA> getEditData() {
        ArrayList<QandA> quest = new ArrayList<>();
        GetProductModel.Product_ data = constants.getSelectedList().getProduct();
        productId = constants.getSelectedList().getProductId();

        quest.add(new QandA(context.getString(R.string.product_number), data.getProductNumber(), 1, 1, 1, "productnumber", null, null));
        quest.add(new QandA(context.getString(R.string.product_name), data.getProductName(), 1, 1, 1, "productname", null, null));
        quest.add(new QandA(context.getString(R.string.product_description), data.getDescription(), 1, 1, 2, "productdesc", null, null));
        quest.add(new QandA(context.getString(R.string.product_classification), getStringList((ArrayList<String>) data.getDeviceClassification()), 3, 0, 0, "deviceclassification", null, data.getDeviceClassification()));
        quest.add(new QandA(context.getString(R.string.is_it_idv), String.valueOf(data.getIvd()), 2, 0, 0, "isivd", null, null));
        quest.add(new QandA(context.getString(R.string.is_this_a_combination_product), String.valueOf(data.getIsCombinationDevice()), 2, 0, 0, "iscombination", null, null));
        quest.add(new QandA(context.getResources().getString(R.string.Is_the_device_life_supporting), String.valueOf(data.getLifeSupporting()), 2, 0, 0, "lifesupportorsustain", null, null));
        QandA implant = new QandA(context.getResources().getString(R.string.Is_the_device_or_component_an_implant), String.valueOf(data.getIsitAnImplant()), 2, 0, 0, "isimplant", null, null);
        quest.add(new QandA(context.getResources().getString(R.string.are_there_any_direct_or_indirect_patient_contacting_components), String.valueOf(data.getPatientContactComponent()), 4, 0, 0, "patientcomponent", implant, null));
        QandA isDigital = new QandA(context.getResources().getString(R.string.is_the_device_digital_health_technology_or_does_it_contain_digital_health_technology), String.valueOf(data.getDigitalHealthTechnology()), 2, 0, 0, "isdigitalhealth", null, null);
        quest.add(new QandA(context.getResources().getString(R.string.does_the_device_use_software_firmware), String.valueOf(data.getUseSoftware()), 4, 0, 0, "softorfirmware", isDigital, null));
        quest.add(new QandA(context.getString(R.string.connection_type), getStringList((ArrayList<String>) data.getConnectionType()), 3, 0, 0, "conntype", null, data.getConnectionType()));

        QandA sterile = new QandA(context.getResources().getString(R.string.select_the_type_of_sterile_processing), getStringList((ArrayList<String>) data.getSterilizationType()), 3, 0, 0, "sterileprocess", null, data.getSterilizationType());
        quest.add(new QandA(context.getResources().getString(R.string.does_the_device_or_a_component_need_sterilization_by_the_manufacturer_or_user), String.valueOf(data.getRequiredSterilization()), 5, 0, 0, "sterilization", sterile, null));

        quest.add(new QandA(context.getString(R.string.indications_for_use), getStringList((ArrayList<String>) data.getIndications()), 3, 0, 0, "indications", null, data.getIndications()));
        quest.add(new QandA(context.getString(R.string.intended_population), getStringList((ArrayList<String>) data.getIntendedPopulation()), 3, 0, 0, "indentpopulation", null, data.getIntendedPopulation()));
        quest.add(new QandA(context.getString(R.string.device_type), getStringList((ArrayList<String>) data.getDeviceType()), 3, 0, 0, "devicetype", null, data.getDeviceType()));
        quest.add(new QandA(context.getString(R.string.usage_environment), getStringList((ArrayList<String>) data.getUseEnvironment()), 3, 0, 0, "useenvironment", null, data.getUseEnvironment()));

        QandA deviceType = new QandA(context.getResources().getString(R.string.select_electrical_device_type), getStringList((ArrayList<String>) data.getElectricalDeviceType()), 3, 0, 0, "eledevicetype", null, data.getElectricalDeviceType());
        quest.add(new QandA(context.getString(R.string.is_the_product_electrical), String.valueOf(data.getIsElectricalDevice()), 5, 0, 0, "iselectrical", deviceType, null));

        quest.add(new QandA(context.getString(R.string.is_the_device_for_pediatric_use), String.valueOf(data.getIsPediatric()), 2, 0, 0, "ispediatric", null, null));
        quest.add(new QandA(context.getString(R.string.does_the_device_emit_radiation), String.valueOf(data.getEmitRadiation()), 2, 0, 0, "isemitrad", null, null));
        quest.add(new QandA(context.getString(R.string.do_you_have_substance_of_biological_origin_in_your_device), String.valueOf(data.getBiologicalOrigin()), 2, 0, 0, "biolorigin", null, null));
        quest.add(new QandA(context.getString(R.string.will_the_device_be_reprocessed_for_resale), String.valueOf(data.getReprocessSUD()), 2, 0, 0, "isresale", null, null));
        return quest;
    }

    public List<QandA> getRREApplication() {
        RREApplication rreApplication = constants.getApplicationData();
        // For Low memory devices the data will be cleared in backgroud to handle such case following code is used.
        if(rreApplication == null){
            rreApplication = new Gson().fromJson(repo.getData(constants.getCACHE_APPLICATION_DATA()), RREApplication.class);
        }
        ArrayList<QandA> quest = new ArrayList<>();
        final RREApplication.Data data = rreApplication.getData();
        if(data == null ){
            return null;
        }
        RREApplication.Application application = data.getApplication();
        if(application!=null) {
            quest.add(new QandA("How many product registrations have you completed?", application.getRegisteredproducts() == null ? "" : application.getRegisteredproducts().toString(), 1, 4, 1, "registeredproducts", null, null));
            quest.add(new QandA("Characterize your successful product registrations", application.getRegisteredProductDescription() == null ? "" : application.getRegisteredProductDescription(), 1, 1, 1, "productdescription", null, null));
            quest.add(new QandA("Product Classification", application.getDeviceClassification() == null ? "" : getStringList((ArrayList<String>) application.getDeviceClassification()), 3, 1, 2, "deviceclassification", null, application.getDeviceClassification()));
            quest.add(new QandA("Have any been IVD?", application.getIvd() == null ? "" : String.valueOf(application.getIvd()), 2, 0, 0, "ivd", null, null));
            quest.add(new QandA("Have any been life supporting / life sustaining?", application.getLifeSupporting() == null ? "" : String.valueOf(application.getLifeSupporting()), 2, 0, 0, "lifesupporting", null, null));
            QandA implant = new QandA("If yes, is it an implant?", application.getIsItAnImplant() == null ? "" : application.getIsItAnImplant().toString(), 2, 0, 0, "isitanimplant", null, null);
            quest.add(new QandA("Have any had direct or indirect patients contacting components?", application.getPatientContactComponents() == null ? "" : String.valueOf(application.getPatientContactComponents()), 4, 0, 0, "patientcontactcomponents", implant, null));
            quest.add(new QandA("How many registered products used software and / or firmware?", application.getUsedSoftwareProducts() == null ? "" : String.valueOf(application.getUsedSoftwareProducts()), 1, 4, 0, "usedsoftwareproducts", null, null));
            quest.add(new QandA("Connection Type", application.getConnectionType() == null ? "" : getStringList((ArrayList<String>) application.getConnectionType()), 3, 3, 1, "connectiontype", null, application.getConnectionType()));
            quest.add(new QandA("How many registered products were sterilized products?", application.getSterilizedProducts() == null ? "" : application.getSterilizedProducts().toString(), 1, 4, 1, "sterilizedproducts", null, null));
            quest.add(new QandA("Product Type", application.getDeviceType() == null ? "" : application.getDeviceType().toString(), 3, 0, 0, "devicetype", null, application.getDeviceType()));
            quest.add(new QandA("Usage Environment", application.getUseEnvironment() == null ? "" : getStringList((ArrayList<String>) application.getUseEnvironment()), 3, 0, 0, "useenvironment", null, application.getUseEnvironment()));
            quest.add(new QandA("Have any been combination devices?", application.getIsCombinationDevice() == null ? "" : application.getIsCombinationDevice().toString(), 2, 0, 0, "iscombinationdevice", null, null));
            QandA deviceType = new QandA("Electrical Product Type", application.getElectricalDeviceType() == null ? "" : getStringList((ArrayList<String>) application.getElectricalDeviceType()), 3, 0, 0, "electricaldevicetype", null, application.getElectricalDeviceType());
            quest.add(new QandA("Have any been electrical?", application.getIsElectricalDevice() == null ? "" : application.getIsElectricalDevice().toString(), 5, 0, 0, "iselectricaldevice", deviceType, null));
            quest.add(new QandA("Have any been adult (pediatric)?", application.getIsPediatric() == null ? "" : application.getIsPediatric().toString(), 2, 0, 0, "ispediatric", null, null));
        }
        return quest;
    }

    public String getStringList(List<String> list) {
        StringBuilder builder = new StringBuilder();
        int x = 0;
        for (String data : list) {
            if (x != list.size() - 1) {
                builder.append(data.trim()).append(",");
            } else {
                builder.append(data.trim());
            }
            x++;
        }
        return builder.toString();
    }

    public boolean[] getBoolArray(String[] array, List<String> subList) {
        boolean[] selected = new boolean[array.length];
        int i = 0;
        boolean isEqual = false;
        for (String string : array) {
            if(string.contains("Transitional Adolescent")){
                string = string.replace(",",";");
            }
            for (String sub : subList) {
                if (string.equals(sub.trim())) {
                    isEqual = true;
                    break;
                }
            }
            selected[i] = isEqual;
            isEqual = false;
            i++;
        }
        return selected;
    }


    public void validateAnswers(List<QandA> questList) {
        for (QandA item : questList) {
            String ans = item.getAnswer();
            if (ans == null||ans.isEmpty()) {
                listener.showErrorMessage(item.getApiKey().toUpperCase() + " is missing");
                return;
            } else if (ans.equals("true") && item.getSubQA() != null && item.getSubQA().getAnswer() == null) {
                listener.showErrorMessage(item.getSubQA().getApiKey().toUpperCase() + " is missing");
                return;
            }
        }
        this.questList = questList;
        if (getRole() == 0) {
            apicall();
        }else {
            JsonObject data = builder.addProductJson(questList);
            listener.triggerApi(data);
        }
    }

    private void apicall() {
        if (utils.isOnline(context)) {
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            String token = repo.getData(constants.getcacheTokenKey());
            JsonObject data = builder.addProductJson(questList);
            if (isEdit) {
                networkCall.editProduct(apiInterface, token, data, productId, this);
            } else {
                networkCall.addProduct(apiInterface, token, data, this);
            }
        } else {
           utils.showNetworkAlert(context,this);
        }
    }

    public int getInputType(int inputType) {
        return utils.getInputType(inputType);
    }

    @Override
    public void onCallSuccess(Object response) {
        if (response instanceof AddProductModel) {
            AddProductModel body = ((AddProductModel) response);
            Boolean status = body.getData().getStatus();
            if (Boolean.TRUE.equals(status) && !isEdit) {
                listener.showErrorMessage("Product Added Successfully");
            } else {
                listener.showErrorMessage("Product Updated Successfully");
            }
        }
    }

    @Override
    public void onCallFail(String message) {
        listener.showErrorMessage(message);
    }

    @Override
    public void sessionExpired() {
        listener.showErrorMessage("Session Expired");
        repo.storeData(constants.getcacheIsLoggedKey(), "false");
        repo.storeData(constants.getCACHE_USER_INFO(), null);
        utils.sessionExpired(context, repo);
    }

    public int getRole() {
        return constants.getuserRole();
    }

    @Override
    public void refreshNetwork() {
        utils.dismissAlert();
        apicall();
    }


    public interface IAddProduct {
        void showErrorMessage(String message);
        void triggerApi(JsonObject data);
    }
}
