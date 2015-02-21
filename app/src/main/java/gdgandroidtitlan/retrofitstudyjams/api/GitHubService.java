package gdgandroidtitlan.retrofitstudyjams.api;

import java.util.List;

import gdgandroidtitlan.retrofitstudyjams.models.Contributor;
import gdgandroidtitlan.retrofitstudyjams.models.GitHubUserProfile;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Jhordan on 20/02/15.
 */
public interface GitHubService {

    @GET("/users/{user}")
    GitHubUserProfile getGithubUser(@Path("user") String user);

    @GET("/repos/{owner}/{repo}/contributors")
    List<Contributor> contributors(
            @Path("owner") String owner,
            @Path("repo") String repo
    );

    @GET("/users/{user}")
    void getUser(@Path("user") String username, Callback<GitHubUserProfile> users);

}
