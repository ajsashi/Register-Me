package com.register.me.view.fragments.Client.portfolio.country;

/**
 * Created by Jennifer - AIT on 11-02-2020.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.register.me.R;
import com.register.me.model.data.model.ProjectModel;
import com.register.me.model.data.util.Utils;
import com.register.me.presenter.CountryPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;


public class CountryFragment extends BaseFragment implements IFragment, CountryPresenter.ICountryListener {

    private static final String FRAGMENT_NAME = "Country";

    @BindView(R.id.country_container)
    LinearLayout container;
    @BindView(R.id.progressbar)
    ConstraintLayout progressLayout;
    @BindView(R.id.pNumber)
    TextView number;
    @BindView(R.id.pName)
    TextView name;
    @Inject
    CountryPresenter presenter;
    @Inject
    Utils utils;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        presenter.init(getContext(), this);
        fragmentChannel.setTitle(getResources().getString(R.string.country));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.triggerApi();
        number.setText(presenter.getNumber());
        name.setText(presenter.getName());
        container.removeAllViews();
    }

    @NotNull
    private View getView(ProjectModel.Project cList) {
        if(getContext()==null){
            return null ;
        }
        View inflatedView = LayoutInflater.from(getContext()).inflate(R.layout.country_item, null, false);
        TextView country = inflatedView.findViewById(R.id.txt_pCountry);
        TextView startDate = inflatedView.findViewById(R.id.txt_startDate);
        TextView status = inflatedView.findViewById(R.id.txt_status);
        CardView card_status = inflatedView.findViewById(R.id.card_status);
        ImageView viewIcon = inflatedView.findViewById(R.id.view);
        ImageView directAssignIcon = inflatedView.findViewById(R.id.direct_assign);
        ImageView cancelIcon = inflatedView.findViewById(R.id.close);
//        GetProductModel.Project project = cList.get(i);
        String region = cList.getProjectlocations().getRegion();
        country.setText(region);
        String bidStartDate = cList.getProductOppurtunity().getBidStartDate();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date = new Date();
            String dateTime = dateFormat.format(date);
            System.out.println("Current Date Time : " + dateTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

        startDate.setText(bidStartDate);
        String bidstatus = cList.getBidstatus();
        status.setText(bidstatus);
        final String s = bidstatus.toLowerCase();
        if(s.contains("cancel")){
            card_status.setCardBackgroundColor(getContext().getResources().getColor(R.color.red));
            status.setTextColor(getContext().getResources().getColor(R.color.white));
        }else if(s.contains("open")){
            card_status.setCardBackgroundColor(getContext().getResources().getColor(R.color.yellow));
            status.setTextColor(getContext().getResources().getColor(R.color.black));
        }else if(s.contains("assignment")){
            card_status.setCardBackgroundColor(getContext().getResources().getColor(R.color.blue));
            status.setTextColor(getContext().getResources().getColor(R.color.white));
        }else if(s.contains("response")){
            card_status.setCardBackgroundColor(getContext().getResources().getColor(R.color.yellow));
            status.setTextColor(getContext().getResources().getColor(R.color.black));
        }
        if(bidstatus.equals("Waiting for Assignment")){
            status.setOnClickListener(view -> {
                fragmentChannel.showProjectAssign(presenter.getName(),cList.getProjectlocations().getLocationid(),cList.getProjectlocations().getRegion());
            });
        }
        viewIcon.setVisibility(cList.getIsView() ? View.VISIBLE : View.GONE);
        directAssignIcon.setVisibility(cList.getIsdirectassignment() ? View.VISIBLE : View.GONE);
        cancelIcon.setVisibility(cList.getIsCancel() ? View.VISIBLE : View.GONE);
        viewIcon.setOnClickListener(view -> {
            setId(cList);
            fragmentChannel.showViewProductDetails();
            presenter.setViewScreen();
        });

        directAssignIcon.setOnClickListener(view -> {
            setId(cList);
            presenter.directAssign();
        });

        cancelIcon.setOnClickListener(view -> {
            setId(cList);
            presenter.cancelProject();
        });
        return inflatedView;
    }

    private void setId(ProjectModel.Project cList) {
        String id = String.valueOf(cList.getProductOppurtunity().getProjectid());
        presenter.setProject(id);
    }


    @Override
    public String getFragmentName() {
        return FRAGMENT_NAME;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_country_list;
    }

    public static CountryFragment newInstance() {
        return new CountryFragment();
    }

    @Override
    public void showMessage(String message) {
        utils.showToastMessage(getContext(),message);
    }

    @Override
    public void navigate() {
        fragmentChannel.popUp();
    }

    @Override
    public void updateUI(List<ProjectModel.Project> projects) {
        container.removeAllViews();
        for (int i = 0; i < projects.size(); i++) {
            View inflatedView = getView(projects.get(i));
            if(inflatedView!=null) {
                container.addView(inflatedView);
            }else {
                return;
            }
        }
    }

    @Override
    public void showProgress() {
        progressLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgress() {
        progressLayout.setVisibility(View.GONE);
    }
}
