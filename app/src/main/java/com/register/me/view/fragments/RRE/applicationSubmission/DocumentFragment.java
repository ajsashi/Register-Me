package com.register.me.view.fragments.RRE.applicationSubmission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.gson.JsonObject;
import com.register.me.APIs.CRRENetworkCall;
import com.register.me.APIs.RRENetworkCall;
import com.register.me.FileUtil;
import com.register.me.R;
import com.register.me.model.data.model.CRREResponse;
import com.register.me.model.data.model.LibraryFiles;
import com.register.me.model.data.model.QandA;
import com.register.me.model.data.model.RREApplication;
import com.register.me.model.data.model.UploadDoc;
import com.register.me.model.data.util.Utils;
import com.register.me.presenter.DocumentPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

/**
 * Created by Jennifer - AIT on 13-02-2020PM 03:05.
 */
public class DocumentFragment extends BaseFragment implements IFragment, DocumentPresenter.IDocument, Utils.UtilNetworkInterface, Utils.UtilAlertInterface {


    private static int locationId;
    @BindView(R.id.card_upload)
    CardView upload;
    @BindView(R.id.layPbar)
    ConstraintLayout layPbar;
    @BindView(R.id.progressbar)
    ConstraintLayout progressBar;
    @BindView(R.id.noContentText)
    TextView noContentLayout;
    @Inject
    DocumentPresenter presenter;
    @Inject
    RRENetworkCall rreNetworkCall;
    @Inject
    CRRENetworkCall crreNetworkCall;
    @Inject
    Utils utils;
    @BindView(R.id.documentContainer)
    LinearLayout documentContainer;

    Observer<UploadDoc> uploadDocObserver;
    Observer<String> message;
    private Observer<RREApplication> applicationObserver;
    private String token;
    private Observer<Response<LibraryFiles>> fileObserver;
    private int role;
    private Observer<Response<CRREResponse>> deleteObserver;
    private Observer<Response<CRREResponse>> crreUpload;
    private JsonObject json;

    public static IFragment newInstance(int id) {
        locationId = id;
        return new DocumentFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_upload_document;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        presenter.init(getContext(), this);


        message = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.d("doc OkHttp -------", s);
                dismissProgress();
                if (utils.isOnline(getContext())) {
                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d("Error", e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                Log.d("onComplete", "");
            }
        };
        uploadDocObserver = new Observer<UploadDoc>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(UploadDoc uploadDoc) {
                rreNetworkCall.clearDisposable();
                dismissProgress();
                String message = uploadDoc.getData().getMessage();
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                showProgress();
                rreNetworkCall.viewRREApplication(applicationObserver);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("onError", e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {

            }
        };

        applicationObserver = new Observer<RREApplication>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(RREApplication rreApplication) {
                dismissProgress();
                rreNetworkCall.clearDisposable();
                presenter.setDocumentList(rreApplication.getData().getDocument());
                buildRREFiles();
            }

            @Override
            public void onError(Throwable e) {
                Log.d("onError", e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {

            }
        };

        fileObserver = new Observer<Response<LibraryFiles>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<LibraryFiles> libraryFilesResponse) {
                dismissProgress();
                buildCRREFiles(libraryFilesResponse.body().getData().getMyLibraryfiles());
                //Toast.makeText(getContext(), libraryFilesResponse.body().getStatusCode() + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Log.d("onError", e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {

            }
        };

        deleteObserver = new Observer<Response<CRREResponse>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<CRREResponse> deleteFileResponse) {
                if (deleteFileResponse.body().getStatusCode() == 200) {
                    crreNetworkCall.getFiles(locationId, fileObserver);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        crreUpload = new Observer<Response<CRREResponse>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<CRREResponse> responseDataResponse) {
                Toast.makeText(getContext(), responseDataResponse.body().getMessage(), Toast.LENGTH_SHORT).show();
                if (responseDataResponse.body().getStatusCode() == 200) {
                    crreNetworkCall.getFiles(locationId, fileObserver);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d("onError", e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        role = presenter.getRole();
        if (role == 1) {
            rreNetworkCall.init(getContext(), message, this);
        } else {
            crreNetworkCall.init(getContext(), message, this);
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        token = presenter.getToken();
        if (presenter.getRole() == 1) {
            dismissProgress();
            buildRREFiles();
        } else {
            crreNetworkCall.getFiles(locationId, fileObserver);
        }
    }

    private void buildRREFiles() {
        List<RREApplication.Document> document = presenter.getDocuments();
        if (document != null && document.size() != 0) {
            documentContainer.removeAllViews();
            documentContainer.setVisibility(View.VISIBLE);
            noContentLayout.setVisibility(View.GONE);
            for (RREApplication.Document doc : document) {
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.document_item, documentContainer, false);
                TextView docName = view1.findViewById(R.id.docName);
                ImageView extImage = view1.findViewById(R.id.ext_image);
                ImageView view = view1.findViewById(R.id.view);
                ImageView delete = view1.findViewById(R.id.delete);

                String docUrl = doc.getDocument();
                String fileName = presenter.getFileName(docUrl);
                String extension = presenter.getExtension();
                Drawable res = null;
                try {
                    String uri = "@drawable/" + extension;
                    int imageResource = getResources().getIdentifier(uri, "drawable", getContext().getPackageName());
                    res = getResources().getDrawable(imageResource, null);
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                extImage.setImageDrawable(res);
                docName.setText(fileName);
                view.setOnClickListener(v -> {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(docUrl));
                    startActivity(i);
                });
                delete.setOnClickListener(v -> {
                    utils.showAlert(getContext(), 16, this::alertResponse);
                    json = presenter.getDeleteJson(doc);

                });
                documentContainer.addView(view1);
            }
        } else if (document.size() == 0) {
            documentContainer.setVisibility(View.GONE);
            noContentLayout.setVisibility(View.VISIBLE);
            /*int childCount = documentContainer.getChildCount();
            if (document.size() == 0 && childCount > 0) {
                documentContainer.removeAllViews();
            }*/
        }
    }

    private void buildCRREFiles(List<LibraryFiles.MyLibraryfile> myLibraryfiles) {
        List<LibraryFiles.MyLibraryfile> document = myLibraryfiles;
        if (document.size() != 0) {
            documentContainer.removeAllViews();
            for (LibraryFiles.MyLibraryfile doc : document) {
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.document_item, documentContainer, false);
                TextView docName = view1.findViewById(R.id.docName);
                ImageView extImage = view1.findViewById(R.id.ext_image);
                ImageView view = view1.findViewById(R.id.view);
                ImageView delete = view1.findViewById(R.id.delete);

                String docUrl = doc.getFilename();

                String fileName = presenter.getFileName(docUrl);
                String extension = presenter.getExtension();
                if (extension.isEmpty()) {
                    fileName = docUrl;
                }
                Drawable res = null;
                try {
                    String uri = "@drawable/" + extension;
                    int imageResource = getResources().getIdentifier(uri, "drawable", getContext().getPackageName());
                    if (imageResource == 0) {
                        res = getResources().getDrawable(R.drawable.blank, null);
                    } else {
                        res = getResources().getDrawable(imageResource, null);
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                extImage.setImageDrawable(res);
                docName.setText(fileName);
                view.setOnClickListener(v -> {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(docUrl));
                    startActivity(i);
                });
                delete.setOnClickListener(v -> {
                    utils.showAlert(getContext(), 16, this);
                    json = new JsonObject();
                    json.addProperty("id", doc.getId());
                    json.addProperty("region", locationId);

                });
                documentContainer.addView(view1);
            }
        } else {
            int childCount = documentContainer.getChildCount();
            if (document.size() == 0 && childCount > 0) {
                documentContainer.removeAllViews();
            }
        }
    }


    @OnClick(R.id.card_upload)
    public void onClickUpload() {
        if (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE) && hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showFileChooser();
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 50);
        }
    }

    public boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (getContext().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }

    public void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(intent, 1);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 50) {
            showFileChooser();
        } else if (requestCode == 1 && data != null) {
            Uri uri = data.getData();
            FileUtil fileUtil = new FileUtil();
            File f = fileUtil.getFile(getContext(), uri);

            try {
                String fileName = fileUtil.getFileName(getContext(), uri);
                if (fileName.contains("mp4") || fileName.contains("mp3")) {
                    Toast.makeText(getContext(), "File format is not supported or File may be corrupted", Toast.LENGTH_SHORT).show();
                    return;
                }
                String data64 = getStringFile(f);
                int pos = fileName.lastIndexOf(".");
                fileName = fileName.substring(0, pos);
                String extension = fileUtil.getExtension(f.getPath());
                if (extension.isEmpty() || extension.equals("tif") || extension.equals("tiff")) {
                    Toast.makeText(getContext(), "File format is not supported or File may be corrupted", Toast.LENGTH_SHORT).show();
                    return;
                }
                String docid = "";
                String action = "up";


                if (role == 1) {
                    ArrayList<QandA> dataList = new ArrayList<>();
                    dataList.add(new QandA("data", data64, 0, 0, 0, "", null, null));
                    dataList.add(new QandA("filename", fileName, 0, 0, 0, "", null, null));
                    dataList.add(new QandA("type", extension, 0, 0, 0, "", null, null));
                    dataList.add(new QandA("docid", docid, 0, 0, 0, "", null, null));
                    dataList.add(new QandA("action", action, 0, 0, 0, "", null, null));
                    JsonObject json = presenter.getDocJson(dataList);
                    showProgress();
                    rreNetworkCall.uploadDocument(getContext(), message, this, token, json, uploadDocObserver);
                } else {
                    ArrayList<QandA> dataList = new ArrayList<>();
                    dataList.add(new QandA("data", data64, 0, 0, 0, "", null, null));
                    dataList.add(new QandA("filename", fileName, 0, 0, 0, "", null, null));
                    dataList.add(new QandA("type", extension, 0, 0, 0, "", null, null));
                    dataList.add(new QandA("locationid", String.valueOf(locationId), 0, 0, 0, "", null, null));
                    JsonObject json = presenter.getDocJson(dataList);
                    showProgress();
                    crreNetworkCall.uploadDocument(token, json, crreUpload);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public String getStringFile(File f) {
        InputStream inputStream = null;
        String encodedFile = "", lastVal;
        try {
            inputStream = new FileInputStream(f.getAbsolutePath());

            byte[] buffer = new byte[10240];//specify the size to allow
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output64.write(buffer, 0, bytesRead);
            }
            output64.close();
            encodedFile = output.toString();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lastVal = encodedFile;
        return lastVal;
    }

    private void showProgress() {
        layPbar.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void dismissProgress() {
        layPbar.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void refreshNetwork() {
        dismissProgress();
       rreNetworkCall.checkNetStatus();
    }

    @Override
    public String getFragmentName() {
        return "DocumentFragment";
    }

    @Override
    public void alertResponse(String success) {
        if (success.equals("$DELETE")) {
            triggerDelete();
        }
    }

    private void triggerDelete() {
        if (role == 1) {
            rreNetworkCall.uploadDocument(getContext(), message, this, token, json, uploadDocObserver);
        } else if (role == 2) {
            crreNetworkCall.deleteFile(json, deleteObserver);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!presenter.getContext()) {
            presenter.init(getContext(), this);
        }
    }
}
