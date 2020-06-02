package com.register.me.model.di.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.register.me.APIs.CRRENetworkCall;
import com.register.me.APIs.ClientNetworkCall;
import com.register.me.APIs.MasterNetworkCall;
import com.register.me.APIs.RRENetworkCall;
import com.register.me.model.JsonBuilder;
import com.register.me.model.data.Constants;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Singleton
@Module
public class AndroidModule {
    private final Context context;
//    private String BASE_URL = "http://3.81.189.43/api/";
    private String BASE_URL = "http://my.registermeapp.com/api/";
//    private String BASE_URL = "http://ocr.ibsolution.com.br/Document/";
//    private String BASE_URL = "https://v2.convertapi.com/convert/";

//        private String BASE_URL = "http://192.168.88.68:8090/api/";
//        private String BASE_URL = "http://192.168.2.148:8090/api/";
//        private String BASE_URL = "http://192.168.88.56:8090/api/";

    public AndroidModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    @Named(Constants.APPLICATION_CONTEXT)
    public Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    public SharedPreferences provideSharedPreferences(@Named(Constants.APPLICATION_CONTEXT) Context context) {
        return context.getSharedPreferences("skit_pref", Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    public Resources provideResources(@Named(Constants.APPLICATION_CONTEXT) Context context) {
        return context.getResources();
    }

    @Singleton
    @Provides
    public Constants provideConstants(){
        return new Constants();
    }

    @Singleton
    @Provides
    public Utils utils(){return new Utils();}

    @Singleton
    @Provides
    public JsonBuilder provideJsonBuilder(){
        return new JsonBuilder();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(){
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(180, TimeUnit.SECONDS)
                .connectTimeout(180,TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder().addHeader("Connection", "close").build();
                        return chain.proceed(request);
                    }
                })
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    @Singleton
    @Provides
    ClientNetworkCall provideNetworkCall(){
        return new ClientNetworkCall();
    }

    @Singleton
    @Provides
    RRENetworkCall provideRRENetworkCall(){return new RRENetworkCall(); }

    @Singleton
    @Provides
    CRRENetworkCall provideCRRENetworkCall(){return new CRRENetworkCall(); }

    @Singleton
    @Provides
    MasterNetworkCall provideMasterNetworkCall(){return new MasterNetworkCall(); }

    @Singleton
    @Provides
    CacheRepo provideCacheRepo(@Named(Constants.APPLICATION_CONTEXT) Context context){
        return new CacheRepo(context);
    }
}
