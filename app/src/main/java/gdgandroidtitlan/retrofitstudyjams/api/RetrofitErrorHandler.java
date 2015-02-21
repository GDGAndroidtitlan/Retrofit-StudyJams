package gdgandroidtitlan.retrofitstudyjams.api;

import android.content.Context;
import android.util.Log;

import java.net.ConnectException;
import java.util.logging.Logger;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

/**
 * Created by Jhordan on 20/02/15.
 */
public class RetrofitErrorHandler implements ErrorHandler {


    public final static String ERROR_REQUEST = "Error en petición HTTP";
    public final static String ERROR = "error de retrofit";


    @Override
    public Throwable handleError(RetrofitError cause) {


        if (cause.getKind() == RetrofitError.Kind.NETWORK)
            return new ConnectException(cause.getMessage());

        return cause;
    }
}
