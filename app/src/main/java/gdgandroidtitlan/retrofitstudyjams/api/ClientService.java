package gdgandroidtitlan.retrofitstudyjams.api;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.util.Date;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Jhordan on 20/02/15.
 */
public class ClientService {

    private final static String endPointGitHub = "https://api.github.com";

    private static Gson getGsonBuilder() {

        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter()).create();
    }

    public static RestAdapter buildGithubRestAdapter() {

        return new RestAdapter.Builder().setEndpoint(endPointGitHub).setErrorHandler(new RetrofitErrorHandler())
                .setConverter(new GsonConverter(getGsonBuilder())).build();

    }

}
