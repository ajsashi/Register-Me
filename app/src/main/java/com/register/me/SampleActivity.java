package com.register.me;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.convertapi.client.Config;
import com.convertapi.client.ConversionResult;
import com.convertapi.client.ConvertApi;
import com.convertapi.client.Param;
import com.google.gson.Gson;
import com.pdfcrowd.Pdfcrowd;
import com.register.me.APIs.ApiInterface;
import com.register.me.view.BaseActivity;

import org.apache.commons.io.IOUtils;
import org.mockito.internal.util.io.IOUtil;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SampleActivity extends BaseActivity {

    List<String> mLines = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sample;
    }

    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
injector().inject(this);
//        upload_image();

        try {

            AssetManager am = getAssets();

            try {
//                InputStream stream = getApplicationContext().getAssets().open("assets/levels.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().getAssets().open("levels.txt"), "UTF-8"));
                String line;

                while ((line = reader.readLine()) != null)
                    mLines.add(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
            StringBuilder finalString =new StringBuilder();
            for (String st : mLines){
           finalString.append(st);
            }


            final File file = new File(Environment.getExternalStorageDirectory() + "/convertedPdf" + ".pdf");
            if(file.exists()){
                file.delete();
            }
            file.createNewFile();

            byte[] pdfAsBytes = Base64.decode(finalString.toString(), 0);
            FileOutputStream os;
            os = new FileOutputStream(file, false);
            os.write(pdfAsBytes);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

      /*  if (isConnected()) {
            Toast.makeText(this, "USB Connected", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not Connected", Toast.LENGTH_SHORT).show();
        }

        if (canWriteToFlash()) {
            Toast.makeText(this, "Can Write", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Cannot write", Toast.LENGTH_SHORT).show();
        }

        try {
            UsbManager mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

            HashMap<String, UsbDevice> deviceList = null;
            deviceList = mUsbManager.getDeviceList();
            Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
            while (deviceIterator.hasNext()) {
                UsbDevice device = deviceIterator.next();

                String s = device.getDeviceName();

                int pid = device.getProductId();
                int vid = device.getVendorId();
                device = deviceList.get(s);

                Toast.makeText(this, s + "\n" + Integer.toString(pid) + "\n" + Integer.toString(vid), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1000 && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            Uri saveUri = data.getData();
            /*Bitmap photo = (Bitmap) data.getExtras().get("data");
            saveUri = getImageUri(photo);*/
            String TEXT_PLAIN_TYPE = "text/plain";
            String CONTENT_IMAGE = "image/jpeg";
            Map<String, RequestBody> requestBodyMap = new HashMap<>();
            MultipartBody.Part imageFileBody = null;
            FileUtil fileUtil = new FileUtil();
            String path = fileUtil.getPath(this, saveUri);
            String resultPath = path.replace("png","pdf");
            File fileToUpload = new File(path);

          /*  try {
                Config.setDefaultSecret("vhEFzcZwtY9qJMHz");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    List<Path> result = ConvertApi.convert("png", "pdf",
                            new Param("File", Paths.get(path))
                    ).get().saveFilesSync(Paths.get("/path/to/result/dir"));
                    Log.d("result",result.size()+"");
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/




           getStringFile(fileToUpload);
            /* new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        pdfCrowd(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();*/

//            hitApi(CONTENT_IMAGE, fileToUpload);
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
            Base64OutputStream output64 = new Base64OutputStream(output, Base64.NO_WRAP);

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
        Log.d("lastvalue",lastVal);
        return lastVal;
    }

    private void pdfCrowd(String path) throws IOException {
        try {
            // create the API client instance
            Pdfcrowd.ImageToPdfClient client =
                    new Pdfcrowd.ImageToPdfClient("ajsashi", "ce17ce587b52c51e6ec213278eb78dea");

            // run the conversion and write the result to a file
           client.convertFileToFile(path, path.replace("png", "pdf"));
        }
         catch(Pdfcrowd.Error why) {
            // report the error
            System.err.println("Pdfcrowd Error: " + why);

            // rethrow or handle the exception
            throw why;
        }
        catch(IOException why) {
            // report the error
            System.err.println("IO Error: " + why);

            // rethrow or handle the exception
            throw why;
        }

    }

    private void hitApi(String CONTENT_IMAGE, File fileToUpload) {
        MultipartBody.Part imageFileBody;
        RequestBody requestBody = RequestBody.create(MediaType.parse(CONTENT_IMAGE), fileToUpload);
        imageFileBody = MultipartBody.Part.createFormData("picture", fileToUpload.getName(), requestBody);

            /*requestBodyMap.put("Rotate", RequestBody.create(MediaType.parse(TEXT_PLAIN_TYPE), "true"));
            requestBodyMap.put("Deskew", RequestBody.create(MediaType.parse(TEXT_PLAIN_TYPE), "true"));
            requestBodyMap.put("Clear", RequestBody.create(MediaType.parse(TEXT_PLAIN_TYPE), "true"));
            requestBodyMap.put("TrustLevel", RequestBody.create(MediaType.parse(TEXT_PLAIN_TYPE), "1"));
            requestBodyMap.put("CompactationLevel", RequestBody.create(MediaType.parse(TEXT_PLAIN_TYPE), "1"));
            requestBodyMap.put("LanguageValue", RequestBody.create(MediaType.parse(TEXT_PLAIN_TYPE), "PT/BR"));
            requestBodyMap.put("ResolutionLevel", RequestBody.create(MediaType.parse(TEXT_PLAIN_TYPE), "300"));*/
        try {
            /*ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            Call<ResponseBody> call = apiInterface.getpdf1("true","true","true",1, 1, "PT/BR", 300, imageFileBody);
//            Call<ResponseBody> call = apiInterface.getpdf(requestBodyMap, imageFileBody);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if(response.isSuccessful()){
                            Toast.makeText(SampleActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            byte[] fileReader = new byte[1000000];
                            InputStream inputStream = response.body().byteStream();
                            File path = Environment.getExternalStorageDirectory();
                            File file = new File(path, "file_name.txt");
                            FileOutputStream fileOutputStream = new FileOutputStream(file);


                            while (true) {
                                int read = inputStream.read(fileReader);

                                if (read == -1) {
                                    break;
                                }

                                fileOutputStream.write(fileReader, 0, read);
                            }
                            fileOutputStream.flush();
                            *//*Log.d("response body", new Gson().toJson(response));
                            Log.d("response body", new Gson().toJson(response.body()));
                            Headers header = response.headers();
                            Log.d("response header",header.size()+"");
                            ResponseBody responseBody = response.body();
                            InputStream is = responseBody.byteStream();
                            StringBuilder sb = new StringBuilder();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                            String line;
                                try {
                                byte[] bt = response.body().bytes();
                                    Log.d("BT",bt.length+""+new String(bt));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            try {
                                while ((line = reader.readLine()) != null) {
                                    sb.append(line);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            String result = sb.toString();
                            Log.d("response data", result);*//*
                        }else {
                            Toast.makeText(SampleActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(SampleActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getRealPathFromURIPath(Uri contentURI) {
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    public boolean isConnected() {
        Intent intent = registerReceiver(null, new IntentFilter("android.hardware.usb.action.USB_STATE"));
        return intent.getExtras().getBoolean("connected");
    }

    private boolean canWriteToFlash() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // Read only isn't good enough
            return false;
        } else {
            return false;
        }
    }

    public void upload_image() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1000);
    }
    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


}
