package co.netguru.android.umeshandroidtest;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Contains different types of error or success codes which can come when the api call fails.
 */
public class RequestStatusWrapper {

    public static final int
            CODE_NULL = -1,
            CODE_LOADING = 0,
            CODE_SUCCESS = 200,
            CODE_CREATED = 201,

    /**
     * Internet Connection Issues
     **/
    CODE_NO_INTERNET_CONNECTION = 65535,
            CODE_SOCKET_TIMEOUT = 65534,

    /**
     * Empty Data Issues
     **/
    CODE_NO_DATA = 204,

    /**
     * Server/API Issue
     **/
    CODE_SERVER_ERROR = 503,

    CODE_INVALID_API_ERROR_CODE = 63000,

    CODE_OFFER_EXPIRED = 555,

    CODE_INVALID_PARTNER_ID = 4001,
            CODE_INVALID_TIMESTAMP = 4002,
            CODE_INVALID_AUTHORIZATION_HEADER = 4003;
    @Code
    public int status = CODE_NULL;

    @Code
    public int getStatusCode() {
        return this.status;
    }

    public void setStatusCode(@Code int code) {
        this.status = code;
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({CODE_INVALID_PARTNER_ID, CODE_INVALID_TIMESTAMP, CODE_INVALID_AUTHORIZATION_HEADER, CODE_NULL, CODE_LOADING, CODE_SERVER_ERROR, CODE_SUCCESS, CODE_NO_DATA, CODE_NO_INTERNET_CONNECTION, CODE_SOCKET_TIMEOUT, CODE_OFFER_EXPIRED})
    public @interface Code {
    }
}
