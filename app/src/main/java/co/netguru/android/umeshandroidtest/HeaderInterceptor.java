package co.netguru.android.umeshandroidtest;

import android.text.TextUtils;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Add headers to the all the public api calls.
 * Set error codes in case API call fails.
 */
public class HeaderInterceptor implements Interceptor {

    public HeaderInterceptor getInstance() {
        return this;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        String query = chain.request().url().query();
        query = TextUtils.isEmpty(query) ? "" : query;

        Request request = chain.request()
                .newBuilder()
                .build();

        try {
            Response response = chain.proceed(request);

            if (response.isSuccessful()) {
                int err = RequestStatusWrapper.CODE_SUCCESS;
                try {
                    String errorCode = response.headers().get("X-API-ErrorCode");
                    err = (errorCode == null) ? RequestStatusWrapper.CODE_SUCCESS : Integer.parseInt(errorCode);
                } catch (NumberFormatException e) {
                    err = RequestStatusWrapper.CODE_INVALID_API_ERROR_CODE;
                }

                String msg = response.headers().get("X-API-ErrorMessage");
                if (err != RequestStatusWrapper.CODE_SUCCESS) {
                    return response.newBuilder()
                            .code(err)
                            .body(response.body())
                            .message((msg == null) ? "failure" : msg)
                            .build();
                }
            }
            return response;
        } catch (UnknownHostException e) {
            return getNoInternetResponse(request, RequestStatusWrapper.CODE_NO_INTERNET_CONNECTION);
        } catch (SocketTimeoutException e) {
            return getNoInternetResponse(request, RequestStatusWrapper.CODE_SOCKET_TIMEOUT);
        } catch (Exception e) {
            return getNoInternetResponse(request, RequestStatusWrapper.CODE_SERVER_ERROR);
        }
    }

    private Response getNoInternetResponse(Request request, int errorCode) {
        String msg = "";
        switch(errorCode){
            case RequestStatusWrapper.CODE_SOCKET_TIMEOUT:
                msg = "Request Timeout. Please try again";
                break;

            case RequestStatusWrapper.CODE_NO_INTERNET_CONNECTION:
                msg = "Please check your internet connection";
                break;

            case RequestStatusWrapper.CODE_SERVER_ERROR:
                msg = "Server error!";
                break;
        }
        return new Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_0)
                .code(errorCode)
                .message(msg)
                .body(ResponseBody.create(MediaType.parse("application/json"), ""))
                .build();
    }
}
