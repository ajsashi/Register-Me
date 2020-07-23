package com.register.me.view.fragments.Client.portfolio.initiateProductRegistration;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


import com.register.me.R;
import com.register.me.model.data.model.CrreList;
import com.register.me.model.data.model.KeyValue;
import com.register.me.model.data.util.Utils;
import com.register.me.presenter.InitiateRegistrationPresenter;
import com.register.me.view.Adapter.SpinAdapter;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Jennifer - AIT on 11-02-2020.
 */
public class InitiateRegistrationFragment extends BaseFragment implements IFragment, InitiateRegistrationPresenter.InitiateRegListener, Utils.UtilDateTimeInterface {
    private static final String FRAGMENT_NAME = "InitiateProductRegistration";

    @BindView(R.id.chk_date)
    CheckBox date;
    @BindView(R.id.date)
    TextView txtDate;
    @BindView(R.id.txt_pName)
    TextView txtPName;
    @BindView(R.id.chk_CRRE)
    CheckBox crre;
    @BindView(R.id.rdGroup_crre)
    RadioGroup radioGroup;
    @BindView(R.id.rd_master)
    RadioButton rdMaster;
    @BindView(R.id.rd_registration)
    RadioButton rdRegistration;
    @BindView(R.id.layout_spinner)
    LinearLayout layoutSpinner;
    @BindView(R.id.layout_crreSelection)
    LinearLayout layoutCrreSelection;
    @BindView(R.id.spinner_crre)
    Spinner spinnerCrre;
    boolean isMasterAssignment;
    String crreId = "";


    @Inject
    InitiateRegistrationPresenter registrationPresenter;
    @Inject
    Utils util;
    private Spinner sItems;
    private ArrayList<Integer> locationId;
    private String location_Id = null;
    private boolean isCRREEnabled;
    private boolean isDateEnabled;
    private List<CrreList.Expertlist> cList;
    private SpinAdapter<CrreList.Expertlist> crreAdapter;
    @Inject
    Utils utils;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        registrationPresenter.init(getContext(), this);
        fragmentChannel.setTitle(getResources().getString(R.string.product_registration));

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sItems = view.findViewById(R.id.spinner_loc);
        txtPName.setText(registrationPresenter.geName());

        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Integer id = locationId.get(i);

                if (id != -1) {
                    location_Id = id + "";
                    registrationPresenter.getCrreList(location_Id);
                } else {
                    location_Id = null;
                }
                layoutCrreSelection.setVisibility(View.GONE);
                radioGroup.clearCheck();
                crre.setChecked(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isDateEnabled = isChecked;
                if (isDateEnabled) {
                    layoutCrreSelection.setVisibility(View.GONE);
                    util.showCalendar(getContext(), InitiateRegistrationFragment.this, 0);
                } else if (txtDate.getVisibility() == View.VISIBLE) {
                    txtDate.setText("");
                    txtDate.setVisibility(View.GONE);
                }
            }
        });

        crre.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isCRREEnabled = b;
                if (isCRREEnabled && !isMasterAssignment) {
                    layoutCrreSelection.setVisibility(View.VISIBLE);

                } else if (isCRREEnabled && isMasterAssignment) {
                    layoutCrreSelection.setVisibility(View.GONE);
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                if (id == rdMaster.getId()) {
                    layoutSpinner.setVisibility(View.GONE);
                } else if (id == rdRegistration.getId()) {
                    isCRREEnabled = false;
                    layoutSpinner.setVisibility(View.VISIBLE);
                }
            }
        });

        spinnerCrre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CrreList.Expertlist crreSelection = crreAdapter.getItem(i);
                Toast.makeText(getContext(), crreSelection.getExpertid() + "", Toast.LENGTH_SHORT).show();
                crreId = crreSelection.getExpertid() + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @OnClick(R.id.submit)
    public void onSubmitClick() {
        if (location_Id != null) {
            ArrayList<KeyValue> list = new ArrayList<>();
            list.add(new KeyValue("productid", registrationPresenter.getProductID(), null));

            list.add(new KeyValue("locationid", location_Id, null));
            list.add(new KeyValue("directassignment", String.valueOf(isCRREEnabled), null));
            list.add(new KeyValue("deadlinedate", txtDate.getText().toString(), null));
            list.add(new KeyValue("creeid", crreId, null));

            registrationPresenter.triggerApi(list);
        }else {
            showMessage("Please select the country you wish to register");
        }

    }

    @OnClick(R.id.cancel_icon)
    public void onCancelClick() {
        fragmentChannel.popUp();
    }

    @OnClick(R.id.txt_request_region)
    public void onClickRequestRegion() {
        registrationPresenter.requestRegion();
    }

    @Override
    public String getFragmentName() {
        return FRAGMENT_NAME;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_initiate_product;
    }

    public static InitiateRegistrationFragment newInstance() {
        return new InitiateRegistrationFragment();
    }


    @Override
    public void onDateSet(long timeInMillis) {
        txtDate.setVisibility(View.VISIBLE);
        DateFormat formatter = new SimpleDateFormat("MMMM dd,yyyy");
        txtDate.setText(formatter.format(timeInMillis));
    }

    @Override
    public void onTimeSet(Integer currentHour, Integer currentMinute) {
        //Nil
    }

    @Override
    public void showMessage(String msg) {
        if(msg!=null) {
            utils.showToastMessage(getContext(), msg);
        }
    }

    @Override
    public void updateSpinner(ArrayList<Integer> locationId, ArrayList<String> locationList) {
        this.locationId = locationId;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(), android.R.layout.simple_spinner_item, locationList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sItems.setAdapter(adapter);
    }

    @Override
    public void navigate() {
        fragmentChannel.popUp();
    }

    @Override
    public void updateCrreSpinner(List<CrreList.Expertlist> cList) {
        this.cList = cList;
        crreId = "";
        if (cList.size() == 0) {
            crre.setText(getContext().getResources().getString(R.string.direct_master_crre_assignment));
            isMasterAssignment = true;

        } else {
            crre.setText(getContext().getResources().getString(R.string.direct_master_crre_registration_expert_assignment));
            isMasterAssignment = false;
        }
        crreAdapter = new SpinAdapter<>(getContext(), android.R.layout.simple_spinner_item, cList, new CrreList());
        crreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCrre.setAdapter(crreAdapter);
    }

    @Override
    public void setMasterAssignment() {
        isMasterAssignment = true;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }
}
