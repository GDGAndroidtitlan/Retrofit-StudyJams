package gdgandroidtitlan.retrofitstudyjams.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import gdgandroidtitlan.retrofitstudyjams.R;
import gdgandroidtitlan.retrofitstudyjams.api.ClientService;
import gdgandroidtitlan.retrofitstudyjams.api.GitHubService;
import gdgandroidtitlan.retrofitstudyjams.api.RetrofitErrorHandler;
import gdgandroidtitlan.retrofitstudyjams.models.Contributor;
import gdgandroidtitlan.retrofitstudyjams.models.GitHubUserProfile;
import gdgandroidtitlan.retrofitstudyjams.util.CirclePictures;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

/**
 * Created by Jhordan on 20/02/15.
 */
public class FragmentGithub extends Fragment implements View.OnClickListener {

    public FragmentGithub() {
    }

    public static FragmentGithub newInstance() {

        FragmentGithub fragmentDescubre = new FragmentGithub();
        Bundle extraArguments = new Bundle();
        fragmentDescubre.setArguments(extraArguments);
        return fragmentDescubre;
    }


    private TextView txtName;
    private ImageView imageViewAvatar;
    private CardView cardViewProfile, cardViewStudyJams;
    private EditText editTxtUserGitHub;
    private GitHubUserProfile gitHubUserProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_github, container, false);

        cardViewProfile = (CardView) rootView.findViewById(R.id.card_view_github_profile);
        cardViewStudyJams = (CardView) rootView.findViewById(R.id.card_view_study_jams);
        editTxtUserGitHub = (EditText) rootView.findViewById(R.id.editTxt_user_github);
        txtName = (TextView)rootView.findViewById(R.id.txt_profile_name);
        imageViewAvatar = (ImageView)rootView.findViewById(R.id.imageView_profile);
        (rootView.findViewById(R.id.btn_search_github_user)).setOnClickListener(this);

        cardViewProfile.setVisibility(View.INVISIBLE);
        cardViewStudyJams.setVisibility(View.INVISIBLE);
        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_search_github_user:

                requestRetrofit(editTxtUserGitHub.getText().toString());

                try {
                    retrofitCallback();
                } catch (RetrofitError error) {
                    System.out.println("RETRO: " + error.getMessage());
                }
                break;
        }

    }


    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void retrofitCallback() {

        final GitHubService gitHubService = ClientService.buildGithubRestAdapter(getActivity()).create(GitHubService.class);

        gitHubService.getUser("erikcaffrey", new Callback<GitHubUserProfile>() {
            @Override
            public void success(GitHubUserProfile gitHubUserProfile, Response response) {
                System.out.println("retrofit with Callback: " + gitHubUserProfile.getName());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Log.e(RetrofitErrorHandler.ERROR, Integer.toString(error.getResponse().getStatus()));
            }
        });

    }

    private void requestRetrofit(final String avatarName) {


        final GitHubService gitHubService = ClientService.buildGithubRestAdapter(getActivity()).create(GitHubService.class);


        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        gitHubUserProfile = gitHubService.getGithubUser(avatarName);
                        dataAvaliable();
                        //listContributors(gitHubService);

                    } catch (RetrofitError e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();


        } catch (RetrofitError e) {
            e.printStackTrace();
        }

    }


    public void dataAvaliable() {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Toast.makeText(getActivity(), gitHubUserProfile.getName(), Toast.LENGTH_SHORT).show();
                Log.i("retrofit", "name  " + gitHubUserProfile.getName());
                Log.i("retrofit", "htmlUrl  " + gitHubUserProfile.getHtml_url());
                Log.i("retrofit", "avatarURL " + gitHubUserProfile.getAvatar_url());
                Log.i("retrofit", "follower list  " + gitHubUserProfile.getFollowers_url());

                cardViewProfile.setVisibility(View.VISIBLE);
                cardViewStudyJams.setVisibility(View.VISIBLE);
                txtName.setText(gitHubUserProfile.getName());
                Picasso.with(getActivity()).load(gitHubUserProfile.getAvatar_url())
                        .placeholder(R.drawable.octoc).transform(new CirclePictures()).into(imageViewAvatar);


            }
        });
    }

    private void listContributors(GitHubService gitHubService) {
        List<Contributor> contributors = gitHubService.contributors("square", "okhttp");
        for (Contributor contributor : contributors) {
            System.out.println("Contribuidor: " + contributor.login + " Contribuciones: " + contributor.contributions);
        }
    }


}
