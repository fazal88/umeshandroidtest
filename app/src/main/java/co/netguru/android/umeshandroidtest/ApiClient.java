package co.netguru.android.umeshandroidtest;


import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import com.readystatesoftware.chuck.ChuckInterceptor;

public class ApiClient {
    public static final int CONNECTION_TIMEOUT = 300;
    public static final int SOCKET_TIMEOUT = 300;
    public static HttpLoggingInterceptor logging;

    //public static String BASE_URL = "http://haste.razrstudio.com/api/1.0/"; // DEV URL
    //public static String BASE_URL = "http://services.razrstudio.com/api/1.0/"; // Pre-Production URL
    //public static String BASE_URL = "http://haste.razrmedia.com/api/1.0/"; // LIVE URL
    //public static String BASE_URL = "https://services.armsprime.com/api/1.0/"; // Production Api Url with SSL
     public static String BASE_URL = "https://api.androidhive.info/"; // CF Url

    //public static String LIVE_STREAM_URL = "";
    public static String LIVE_STREAM_URL = "http://d2fy11zd7yuyoz.cloudfront.net/poonam/myStream/playlist.m3u8";// Live URL
    //public static String LIVE_STREAM_URL = "http://d2fy11zd7yuyoz.cloudfront.net/zareen/myStream/playlist.m3u8";// Live URL
    //public static String LIVE_STREAM_URL = "http://d2fy11zd7yuyoz.cloudfront.net/razrcorp/myStream/playlist.m3u8";// Testing URL

    public static String STREAM_START_URL = "https://d2fy11zd7yuyoz.cloudfront.net/";
    public static String STREAM_END_URL = "/myStream/playlist.m3u8";
    public static String SOCKET_URL_PORT = "";
    public static String ARTIST_KEY = "";

    public static String debugUrl;
    public static boolean shouldDebug;
    private static API REST_CLIENT;

    private static Retrofit restAdapter;

    static {
        setupRestClient();
    }

    private ApiClient() {
    }

    public static API get() {
        return REST_CLIENT;
    }

    public static void setLiveStreamUrl(String artistKey) {
        LIVE_STREAM_URL = STREAM_START_URL + "" + artistKey + "" + STREAM_END_URL;
    }

    public static String getLiveStreamUrl() {
        return LIVE_STREAM_URL;
    }

    private static void setupRestClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        httpClient.readTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS);

        long httpCacheSize = 10 * 1024 * 1024; // 10 MiB
//        File httpCacheDir = Utils.getExternalCacheDir(PoonamApplication.getPoonamAppContext());
        // TODO Implement a fallback mechanism if external cache directory is not available
//        Cache cache = null;
//        try {
//            if (httpCacheDir != null) {
//                cache = new Cache(httpCacheDir, httpCacheSize);
//            }
//        } catch (Throwable t) {
//            cache = null;
//        }
//        if (cache != null) {
//            httpClient.cache(cache);
//        }

        //**//**//**//** Interceptors (Order is important) **//**//**//**//*
        // Request Header interceptor - Adds the request headers and tests for error codes on response
        httpClient.interceptors().add(new HeaderInterceptor());

        // For debugging
        // Add debug interceptors only if its a debug build
        if (BuildConfig.DEBUG) {

//            httpClient.addInterceptor(new ChuckInterceptor(PoonamPandey.getZarinAppContext()));

            // Logging Interceptor (Add this as the last interceptor)
            logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.interceptors().add(logging);
        }

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getRootUrl())
                .addConverterFactory(GsonConverterFactory.create(MiscUtils.getGsonInstance()))
                .client(httpClient.build());

        restAdapter = builder.build();
        REST_CLIENT = restAdapter.create(API.class);
    }

    private static String getRootUrl() {
        if (shouldDebug) {
            return (debugUrl != null && !debugUrl.isEmpty()) ? debugUrl : BASE_URL;
        } else {
            return BASE_URL;
        }
    }

    public static void setRootUrl(String url) {
        ApiClient.BASE_URL = (url.startsWith("http") ? "" : "http://") +
                url +
                (url.endsWith("/") ? "" : "/");
        // ApiClient.setupRestClient();
    }

    public static void setDebugMode(boolean shouldDebug) {
        ApiClient.shouldDebug = shouldDebug;
    }

    public static Retrofit getRetrofitInstance() {
        return restAdapter;
    }

    public static final class ERROR_CODES {
        public static final int ERROR_CODE_UNKNOWN_HOST = 1;
        public static final int ERROR_CODE_JSON_EXCEPTION = 2;
        public static final int ERROR_CODE_SOCKET_TIMEOUT = 5;
        public static final int ERROR_CODE_UNKNOWN = 99;
    }
}
