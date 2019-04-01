package co.netguru.android.umeshandroidtest;

import android.text.TextUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Response of the API calls(after successful) is processed here
 *
 * @param <T>
 */
public abstract class RestCallBack<T> implements Callback<T> {

    public RestCallBack() {
    }

    public abstract void onResponseSuccess(Response<T> response);

    public abstract void onResponseFailure(int errorCode, String msg);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        String errCode;
        int statusCode = response.code();
        String errMsg = response.message();

        if (response.isSuccessful()) {
            errCode = response.headers().get("X-API-ErrorCode");
            errMsg = response.headers().get("X-API-ErrorMessage");
            statusCode = (TextUtils.isEmpty(errCode)) ? 200 : (Integer.parseInt(errCode));
            if (statusCode != RequestStatusWrapper.CODE_SUCCESS) {
                processStatusCode(statusCode, errMsg);
            } else {
                responseSuccess(response);
            }
        } else {
            processStatusCode(statusCode, errMsg);
        }
    }

    private void processStatusCode(int statusCode, String errMsg) {
        responseFailure(statusCode, errMsg);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (!call.isCanceled()) {
            responseFailure(ApiClient.ERROR_CODES.ERROR_CODE_JSON_EXCEPTION, t.getMessage());
        }
    }

    private void responseSuccess(Response<T> response) {
        try {
            onResponseSuccess(response);
        } catch (Throwable t) {
        }
    }

    private void responseFailure(int statusCode, String errMsg) {
        try {
            onResponseFailure(statusCode, errMsg);
        } catch (Throwable t) {
        }
    }
}
