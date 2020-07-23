package com.register.me.model.data.util;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;

import com.register.me.R;
import com.register.me.model.data.model.CurrencyCode;
import com.register.me.model.data.model.LocationModel;
import com.register.me.model.data.model.SuccessCertificate;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.view.Adapter.SpinAdapter;
import com.register.me.view.activity.LoginActivity;
import com.thomashaertel.widget.MultiSpinner;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class Utils {

    private String commentTopic;
    private String editRegion;
    private AlertDialog networkAlertDialog;
    private List<CurrencyCode.Currencydetail> currencydetails;
    private TextView converted_txt;
    private EditText edt_amount;
    private EditText edt_duration;
    private TextView dateConvert_txt;
    private List<SuccessCertificate.Uncertifiedregion> uncertifiedregions;
    private List<LocationModel.Location> locationList;
    private ArrayList<LocationModel.Location> selectedList;

    public boolean isOnline(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void showCalendar(Context context, UtilDateTimeInterface listener, int call) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.calendar_view);
        final int[] min = new int[1];
        final int[] hour = new int[1];
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,2);

        CalendarView calendarView = dialog.findViewById(R.id.calendar);
        calendarView.setMinDate(cal.getTimeInMillis());
        TimePicker picker = (TimePicker) dialog.findViewById(R.id.timePicker1);
        Button ok = dialog.findViewById(R.id.ok_btn);
        if (call == 0) {

            picker.setVisibility(View.GONE);
            ok.setVisibility(View.GONE);

            calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
                Calendar c = Calendar.getInstance();
                c.set(year, month, dayOfMonth);

                Log.d("Calendar", dayOfMonth + "-" + month + "-" + year);
                Log.d("Calendar", c.getTimeInMillis() + "");
                listener.onDateSet(c.getTimeInMillis());
                dialog.dismiss();
            });
        } else {
            calendarView.setVisibility(View.GONE);
            hour[0] = picker.getCurrentHour();
            min[0] = picker.getCurrentMinute();
            picker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {


                @Override
                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                    hour[0] = hourOfDay;
                    min[0] = minute;
                }
            });
            ok.setOnClickListener(v -> {
                listener.onTimeSet(hour[0], min[0]);
                dialog.dismiss();
            });
        }


        dialog.show();
    }

    /*
     *   case 1 - Registration Success Alert
     *   case 2 - Forgot Password Alert
     *   case 3 - Password Link Alert
     *   case 4 - Password update
     *   case 5 - Logout
     *   case 6 - Update User
     *   case 7 - Direct Assign
     *   case 8 - Cancel Project
     *   case 9 - Request Region
     *   case 10 - Add new comments
     *   case 11 - Add new comments
     *   case 12 - Cancle Bid
     *   case 13 - Submit Bid
     *   case 14 - Cancle Project
     *   case 15 - Request Country
     *   case 16 - Delete File
     *   case 17 - Interview Comment
     *   case 18 - Delete Schedule in MCRRE
     *   case 19 - Add new Geo location
     *   case 20 - Accept Requested Region
     *   case 21 - Cancel Requested Region
     *   case 22 - Edit Geo Region
     *   case 23 - Remove Geo Region
     *   case 24 - Certified as CRRE
     *   case 25 - Change Email
     * */
    public void showAlert(Context context, int viewCase, UtilAlertInterface listener) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_view, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);

        LinearLayout layoutRegistrationSuccess = dialogView.findViewById(R.id.layout_registration_success);
        LinearLayout layoutForgotPassword = dialogView.findViewById(R.id.layout_forgot_password);
        LinearLayout layoutPasswordLinkSuccess = dialogView.findViewById(R.id.layout_password_link_success);
        LinearLayout layoutPasswordUpdateSuccess = dialogView.findViewById(R.id.layout_pass_update_success);
        LinearLayout layoutLogout = dialogView.findViewById(R.id.layout_logout);
        LinearLayout layoutUpdate = dialogView.findViewById(R.id.layout_user_update);
        LinearLayout layoutDirectAssign = dialogView.findViewById(R.id.layout_direct_assign);
        LinearLayout layoutCancelProject = dialogView.findViewById(R.id.layout_cancel_project);
        LinearLayout layoutRequestRegion = dialogView.findViewById(R.id.layout_request_region);
        LinearLayout layoutComments = dialogView.findViewById(R.id.layout_comments);
        LinearLayout layoutSubmitBid = dialogView.findViewById(R.id.layout_submitBid);
        LinearLayout layout_request_country = dialogView.findViewById(R.id.layout_request_country);
        LinearLayout layout_interview_comment = dialogView.findViewById(R.id.layout_interview_comment);
        AlertDialog alertDialog = dialog.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        switch (viewCase) {
            case 1:
                layoutRegistrationSuccess.setVisibility(View.VISIBLE);
                Button ok = dialogView.findViewById(R.id.btn_ok);
                ok.setOnClickListener(v -> {
                    alertDialog.dismiss();
                    ((Activity) context).finish();
                });
                break;
            case 2:
                layoutForgotPassword.setVisibility(View.VISIBLE);
                EditText edtEmailAddress = dialogView.findViewById(R.id.edt_emailAddress);
                Button btnEmail = dialogView.findViewById(R.id.btn_email);
                Button btnForCancel = dialogView.findViewById(R.id.btn_fCancel);
                btnEmail.setOnClickListener(v -> {
                    String val = edtEmailAddress.getText().toString();
                    if (val.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(val).matches()) {
                        listener.alertResponse("$ERROR$ Please enter a valid email");
                    } else {
                        listener.alertResponse("$EMAIL$" + val);
                        alertDialog.dismiss();
                    }

                });
                btnForCancel.setOnClickListener(view -> alertDialog.dismiss());

                break;
            case 3:
            case 24:
                layoutPasswordLinkSuccess.setVisibility(View.VISIBLE);
                if (viewCase == 24) {
                    TextView header = dialogView.findViewById(R.id.header);
                    TextView s_header = dialogView.findViewById(R.id.s_header);
                    header.setText("Certification Completed");
                    s_header.setText("You can now login as CRRE");
                }
                Button btnPasswordOk = dialogView.findViewById(R.id.btn_password_ok);
                btnPasswordOk.setOnClickListener(v ->
                {
                    if(viewCase == 24){
                        listener.alertResponse("Dismiss");
                    }
                    alertDialog.dismiss();
                });
                break;
            case 4:
                layoutPasswordUpdateSuccess.setVisibility(View.VISIBLE);
                Button btnOk = dialogView.findViewById(R.id.btn_pass_ok);
                btnOk.setOnClickListener(v -> {
                    listener.alertResponse("$Success$");
                    alertDialog.dismiss();
                });
                break;
            case 5:
            case 16:
            case 18:
            case 20:
            case 23:
                layoutLogout.setVisibility(View.VISIBLE);
                TextView body_txt = dialogView.findViewById(R.id.body_txt);
                Button btnPassYes = dialogView.findViewById(R.id.btn_pass_yes);
                Button btnPassCancel = dialogView.findViewById(R.id.btn_pass_cancel);
                switch (viewCase) {
                    case 16:
                        body_txt.setText("Are you sure to delete the file ? ");
                        break;
                    case 18:
                        body_txt.setText("Are you sure to delete the scheduled interview ? ");
                        break;
                    case 20:
                        body_txt.setText("Are you sure you want to accept this request ? ");
                        break;
                    case 23:
                        body_txt.setText("Are you sure you want to delete this region ? ");
                        break;
                }

                btnPassYes.setOnClickListener(v -> {
                    alertDialog.dismiss();

                    switch (viewCase) {
                        case 5:
                            listener.alertResponse("$LOGOUT");
                            break;
                        case 16:
                        case 18:
                        case 23:
                            listener.alertResponse("$DELETE");
                            break;
                        case 20:
                            listener.alertResponse("$ACCEPT");
                            break;

                    }

                });
                btnPassCancel.setOnClickListener(v -> {
                            alertDialog.dismiss();
                            if (viewCase == 23) {
                                listener.alertResponse("$Dismiss$");
                            }
                        }
                );
                break;
            case 6:
                layoutUpdate.setVisibility(View.VISIBLE);
                Button btnUserOk = dialogView.findViewById(R.id.btn_user_ok);
                btnUserOk.setOnClickListener(v -> {
                    listener.alertResponse("$SUCCESS$");
                    alertDialog.dismiss();
                });
                break;
            case 7:
                layoutDirectAssign.setVisibility(View.VISIBLE);
                Button btnAssignYes = dialogView.findViewById(R.id.btn_assign_yes);
                Button btnAssignCancel = dialogView.findViewById(R.id.btn_assign_cancel);
                btnAssignYes.setOnClickListener(v -> {
                    alertDialog.dismiss();
                    listener.alertResponse("$OK$");
                });
                btnAssignCancel.setOnClickListener(v -> alertDialog.dismiss());
                break;
            case 8:
            case 14:
            case 19:
            case 21:
            case 22:
            case 25:
                layoutCancelProject.setVisibility(View.VISIBLE);
                Button btnSubmit = dialogView.findViewById(R.id.btn_submit);
                Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
                EditText edtCancel = dialogView.findViewById(R.id.edt_reason);
                TextView bodytxt = dialogView.findViewById(R.id.txtTitle);

                switch (viewCase) {
                    case 14:
                        bodytxt.setText("Are you sure you want to cancel the submitted bid?");
                        break;
                    case 19:
                        bodytxt.setText("Add Geographic Location");
                        break;
                    case 21:
                        bodytxt.setText("Are you sure you want to cancel this request ? ");
                        break;
                    case 22:
                        bodytxt.setText("Edit Geographic Location ");
                        if (getEditRegion() != null || !getEditRegion().isEmpty()) {
                            edtCancel.setText(getEditRegion());
                        }
                        break;
                    case 25:
                        bodytxt.setText("Change Email Address");
                        btnSubmit.setText("SUBMIT");
                        break;
                }


                btnCancel.setOnClickListener(view -> {
                    listener.alertResponse("$Dismiss$");
                    alertDialog.dismiss();
                });
                btnSubmit.setOnClickListener(view -> {
                    String reason = edtCancel.getText().toString();
                    if (!reason.isEmpty()) {
                        alertDialog.dismiss();

                        switch (viewCase) {
                            case 8:
                                listener.alertResponse("$SUCCESS$" + reason);
                                break;
                            case 19:
                                listener.alertResponse("$ADD$" + reason);
                                break;
                            case 21:
                                listener.alertResponse("$REJECT:" + reason);
                                break;
                            case 22:
                                listener.alertResponse("$EDIT:" + reason);
                                break;
                            case 25:
                                listener.alertResponse("$EMAIL:"+reason);
                            default:
                                listener.alertResponse("$CANCEL:" + reason);
                                break;
                        }
                    } else {
                        listener.alertResponse("$ALERT$");
                    }
                });
                break;
            case 9:
                layoutRequestRegion.setVisibility(View.VISIBLE);
                Button btnRegSubmit = dialogView.findViewById(R.id.btn_reg_submit);
                Button btnRegCancel = dialogView.findViewById(R.id.btn_reg_cancel);
                EditText edtRegion = dialogView.findViewById(R.id.edt_region);
                EditText edtDescription = dialogView.findViewById(R.id.edt_description);
                btnRegCancel.setOnClickListener(view -> alertDialog.dismiss());
                btnRegSubmit.setOnClickListener(view -> {
                    String region = edtRegion.getText().toString().trim();
                    String description = edtDescription.getText().toString().trim();
                    if (!TextUtils.isEmpty(region) && !TextUtils.isEmpty(description)) {
                        alertDialog.dismiss();
                        listener.alertResponse(region + ":" + description);
                    } else {
                        listener.alertResponse("$ALERT$");
                    }
                });
                break;
            case 10:
                layoutComments.setVisibility(View.VISIBLE);
                EditText edtTopic = dialogView.findViewById(R.id.edt_topic);
                EditText edtComment = dialogView.findViewById(R.id.edt_comment);
                Button btnSubmitComment = dialogView.findViewById(R.id.btn_submit_comment);
                Button btnCancelComment = dialogView.findViewById(R.id.btn_cancel_comment);
                if (getCommentTopic() != null && !getCommentTopic().isEmpty()) {
                    edtTopic.setText(getCommentTopic());
                    edtTopic.setFocusable(false);
                }
                btnCancelComment.setOnClickListener(view -> alertDialog.dismiss());
                btnSubmitComment.setOnClickListener(view -> {
                    String topic = edtTopic.getText().toString().trim();
                    String comment = edtComment.getText().toString().trim();
                    if (!TextUtils.isEmpty(topic) && !TextUtils.isEmpty(comment)) {
                        alertDialog.dismiss();
                        listener.alertResponse(topic + ":" + comment);
                    } else {
                        listener.alertResponse("$ALERT$");
                    }
                });
                break;
            case 11:
                layoutComments.setVisibility(View.VISIBLE);
                EditText topic = dialogView.findViewById(R.id.edt_topic);
                EditText rre_edtComment = dialogView.findViewById(R.id.edt_comment);
                Button rre_btnSubmitComment = dialogView.findViewById(R.id.btn_submit_comment);
                Button rre_btnCancelComment = dialogView.findViewById(R.id.btn_cancel_comment);
                topic.setVisibility(View.GONE);
              /*  if(getCommentTopic()!=null &&!getCommentTopic().isEmpty()){
                    rre_edtTopic.setText(getCommentTopic());
                    rre_edtTopic.setFocusable(false);
                }*/
                rre_btnCancelComment.setOnClickListener(view -> alertDialog.dismiss());
                rre_btnSubmitComment.setOnClickListener(view -> {
//                    String topic = rre_edtTopic.getText().toString().trim();
                    String comment = rre_edtComment.getText().toString().trim();
                    if (!TextUtils.isEmpty(comment)) {
                        alertDialog.dismiss();
                        listener.alertResponse(comment);
                    } else {
                        listener.alertResponse("$ALERT$");
                    }
                });
                break;
            case 12:
                layoutLogout.setVisibility(View.VISIBLE);
                TextView bodyTxt = dialogView.findViewById(R.id.body_txt);
                Button btn_Yes = dialogView.findViewById(R.id.btn_pass_yes);
                Button btn_Cancel = dialogView.findViewById(R.id.btn_pass_cancel);
                bodyTxt.setText("Once you decline the bid, you cannot re-submit the bid again. Are you sure to decline the bid?");
                btn_Yes.setOnClickListener(v -> {
                    alertDialog.dismiss();
                    listener.alertResponse("$DECLINE");

                });
                btn_Cancel.setOnClickListener(v -> alertDialog.dismiss());
                break;
            case 13:
                layoutSubmitBid.setVisibility(View.VISIBLE);
                bindandValidateSubmitBid(context, alertDialog, dialogView, listener);
                break;
            case 15:
                layout_request_country.setVisibility(View.VISIBLE);
                requestCountry(context, alertDialog, dialogView, listener);
                break;
            case 17:
                LinearLayout eligibleLay = dialogView.findViewById(R.id.eligibleLayout);
                LinearLayout geoLay = dialogView.findViewById(R.id.geoLayout);
                layout_interview_comment.setVisibility(View.VISIBLE);
                EditText comment = dialogView.findViewById(R.id.interview_cmt);
                Button btn_submit_int = dialogView.findViewById(R.id.btn_submit_int);
                Button btn_cancel_int = dialogView.findViewById(R.id.btn_cancel_int);
                Button btn_inter_submit = dialogView.findViewById(R.id.btn_inter_submit);
                eligibleLay.setVisibility(View.VISIBLE);
                btn_cancel_int.setOnClickListener(v -> {
                    alertDialog.dismiss();
                });

                btn_submit_int.setOnClickListener(v -> {
                    String ct = comment.getText().toString();
                    if (!ct.equals("")) {
                        eligibleLay.setVisibility(View.GONE);
                        geoLay.setVisibility(View.VISIBLE);
                        TextView txtSpinnerTitle = dialogView.findViewById(R.id.textSpinnerTitle);
                        TextView txtSelect = dialogView.findViewById(R.id.spinner_text);
                        MultiSpinner mSpinner = (MultiSpinner) dialogView.findViewById(R.id.spinnerMulti);

                        txtSpinnerTitle.setText("Eligible Geographic Locations (Select one or more)");
                        txtSpinnerTitle.setTextColor(context.getResources().getColor(R.color.white));
                        txtSelect.setText("Select an option");
                        ArrayAdapter<LocationModel.Location> adapter = null;
                        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
                        adapter.addAll(getLocationList());
                        ArrayList<LocationModel.Location> selectedList = new ArrayList<>();
                        setSpinnerAdapter(mSpinner, txtSelect, adapter, selectedList);


                    } else {
                        Toast.makeText(context, "Please enter your comment", Toast.LENGTH_SHORT).show();
                    }

                    btn_inter_submit.setOnClickListener(v1 -> {
                        if (getSelectedLocationList() != null) {
                            listener.alertResponse(ct);
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(context, "Please select a location", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + viewCase);
        }


        alertDialog.show();
    }

    private void setSpinnerAdapter(MultiSpinner mSpinner, TextView txtSelect, ArrayAdapter<LocationModel.Location> adapter, ArrayList<LocationModel.Location> selectedList) {
        MultiSpinner.MultiSpinnerListener onSelectedListener = new MultiSpinner.MultiSpinnerListener() {
            public void onItemsSelected(boolean[] selected) {
                txtSelect.setText("");
                selectedList.clear();
                for (int i = 0; i < selected.length; i++) {
                    if (selected[i]) {
                        LocationModel.Location item = adapter.getItem(i);
                        selectedList.add(item);
                        setSelectedLocation(selectedList);
                    }
                }
            }
        };
        mSpinner.setAdapter(adapter, false, onSelectedListener);
    }

    private void setSelectedLocation(ArrayList<LocationModel.Location> selectedList) {
        this.selectedList = selectedList;
    }

    public ArrayList<LocationModel.Location> getSelectedLocationList() {
        return selectedList;
    }

    private void requestCountry(Context context, AlertDialog alertDialog, View dialogView, UtilAlertInterface listener) {
        Spinner countrySpinner = dialogView.findViewById(R.id.countrySpinner);
        Button btn_ct_submit = dialogView.findViewById(R.id.btn_ct_submit);
        Button btn_ct_cancel = dialogView.findViewById(R.id.btn_ct_cancel);

        SpinAdapter adapter = new SpinAdapter(context, android.R.layout.simple_spinner_item, uncertifiedregions, new SuccessCertificate.Uncertifiedregion());
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        countrySpinner.setAdapter(adapter);
        final Integer[] cID = new Integer[1];
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                cID[0] = uncertifiedregions.get(position).getLocationid();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_ct_submit.setOnClickListener(v -> {
            String success = "$LocationID:" + cID[0].toString();
            listener.alertResponse(success);
            alertDialog.dismiss();
        });
        btn_ct_cancel.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
    }

    private void bindandValidateSubmitBid(Context context, AlertDialog alertDialog, View dialogView, UtilAlertInterface listener) {
        edt_amount = dialogView.findViewById(R.id.edt_amount);
        Spinner spinner_amount = dialogView.findViewById(R.id.spinner_amount);
        edt_duration = dialogView.findViewById(R.id.edt_duration);
        Spinner spinner_duration = dialogView.findViewById(R.id.spinner_duration);
        EditText remarks = dialogView.findViewById(R.id.remarks);
        CardView btn_submit_bid = dialogView.findViewById(R.id.btn_submit_bid);
        CardView btn_cancel_bid = dialogView.findViewById(R.id.btn_cancel_bid);
        dateConvert_txt = dialogView.findViewById(R.id.dateConvert_txt);
        converted_txt = dialogView.findViewById(R.id.converted_txt);
        final String[] CURRENCY = {""};
        final String[] DMY = {""};
        final Integer[] idCountry = new Integer[1];


        SpinAdapter<CurrencyCode.Currencydetail> adapter = new SpinAdapter<CurrencyCode.Currencydetail>(context, android.R.layout.simple_spinner_item, currencydetails, new CurrencyCode.Currencydetail());
        spinner_amount.setAdapter(adapter);


       /* ArrayAdapter<CurrencyCode.Currencydetail> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
        String[] array = context.getResources().getStringArray(R.array.currency);
        adapter.addAll(currencydetails);
        spinner_amount.setAdapter(adapter);*/

        ArrayAdapter<String> dmyAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
        String[] dmy = context.getResources().getStringArray(R.array.DMY);
        dmyAdapter.addAll(dmy);
        spinner_duration.setAdapter(dmyAdapter);


        spinner_amount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CURRENCY[0] = adapter.getItem(position).getCurrencycode();
                idCountry[0] = adapter.getItem(position).getId();
                converted_txt.setText("Click to get amount in $");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_duration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DMY[0] = dmyAdapter.getItem(position);
                dateConvert_txt.setText("Click to get completion date");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_submit_bid.setOnClickListener(v -> {
            if (edt_amount.getText().toString().trim().isEmpty()) {
                listener.alertResponse("Enter Amount");
            } else if (edt_duration.getText().toString().isEmpty() || spinner_duration.getSelectedItem().equals("Select")) {
                listener.alertResponse("Enter Duration");
            } else if (spinner_amount.getSelectedItem().equals("Select")) {
                listener.alertResponse("Select Currency");
            } else {
                String response = edt_amount.getText().toString()
                        + ":" + idCountry[0]
                        + ":" + edt_duration.getText().toString()
                        + ":" + spinner_duration.getSelectedItem().toString();
                if (!remarks.getText().toString().isEmpty()) {
                    response = response + ":" + remarks.getText().toString();
                }
                listener.alertResponse("$SUBMITBID$" + response);
                alertDialog.dismiss();
            }
        });
        btn_cancel_bid.setOnClickListener(v -> {
            alertDialog.dismiss();
        });
        converted_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.alertResponse("$TRIGGERAPI$." + CURRENCY[0]);
            }
        });
        dateConvert_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                String duration = spinner_duration.getSelectedItem().toString();
                int dateTxt = Integer.parseInt(edt_duration.getText().toString());
                switch (duration) {
                    case "Days":
                        c.add(Calendar.DATE, dateTxt);
                        break;
                    case "Month":
                        c.add(Calendar.MONTH, dateTxt);
                        break;
                    case "Year":
                        c.add(Calendar.YEAR, dateTxt);
                        break;
                }
                Date finalDate = c.getTime();
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                dateConvert_txt.setText("Completion Date : " + dateFormat.format(finalDate));
            }
        });
    }

    public void updateText(Double usd) {
        double text = Double.parseDouble(edt_amount.getText().toString());
        double val = text * usd;
        DecimalFormat dform = new DecimalFormat("##.##");
        converted_txt.setText("$ " + dform.format(val) + " USD (translated)");
    }

    public void showNetworkAlert(Context context, UtilNetworkInterface networkWatcher) {
        AlertDialog.Builder networkAlert = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.network_layout, null);
        networkAlert.setView(dialogView);
        networkAlert.setCancelable(false);
        ImageView refreshBtn = dialogView.findViewById(R.id.refresh);
        refreshBtn.setOnClickListener(v -> {
            networkWatcher.refreshNetwork();
        });
        networkAlertDialog = networkAlert.create();
        networkAlertDialog.show();
    }

    public int getInputType(int inputType) {
        switch (inputType) {
            case 1:
                return InputType.TYPE_CLASS_TEXT;
            case 2:
                return InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
            case 3:
                return InputType.TYPE_TEXT_VARIATION_PASSWORD;
            case 4:
                return InputType.TYPE_CLASS_NUMBER;
            default:
                return inputType;
        }
    }

    public void sessionExpired(Context context, CacheRepo repo) {
        repo.clearCacheRepo();
        context.startActivity(new Intent(context, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    public void setCommentTopic(String commentTopic) {
        this.commentTopic = commentTopic;
    }

    public String getCommentTopic() {
        return commentTopic;
    }


    public String getEditRegion() {
        return editRegion;
    }

    public void setEditRegion(String editRegion) {
        this.editRegion = editRegion;
    }

    //false = not showing
//    true = is showing
    public boolean checkAlert() {
        boolean showing;
        if (networkAlertDialog == null) {
            showing = false;
        } else {
            showing = networkAlertDialog.isShowing();
        }
        return showing;
    }

    public void dismissAlert() {
        if (networkAlertDialog != null) {
            networkAlertDialog.dismiss();
        }
    }

    public void triggerAlert(List<CurrencyCode.Currencydetail> currencydetails) {
        this.currencydetails = currencydetails;
    }

    public void setCountrySpinner(List<SuccessCertificate.Uncertifiedregion> uncertifiedregions, Context context, int i, UtilAlertInterface listener) {
        this.uncertifiedregions = uncertifiedregions;
        showAlert(context, i, listener);
    }

    public void setLocationList(List<LocationModel.Location> locationList) {
        this.locationList = locationList;
    }

    public List<LocationModel.Location> getLocationList() {
        return locationList;
    }

    public interface UtilDateTimeInterface {
        void onDateSet(long timeInMillis);

        void onTimeSet(Integer currentHour, Integer currentMinute);
    }

    public interface UtilAlertInterface {
        void alertResponse(String success);
    }

    public interface UtilNetworkInterface {
        void refreshNetwork();
    }

    public void showToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public void showKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        }
    }

    public void hideKeyboard(View v, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
