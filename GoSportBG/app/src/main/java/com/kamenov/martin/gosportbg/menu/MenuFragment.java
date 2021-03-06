package com.kamenov.martin.gosportbg.menu;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.kamenov.martin.gosportbg.GoSportApplication;
import com.kamenov.martin.gosportbg.R;
import com.kamenov.martin.gosportbg.base.contracts.BaseContracts;
import com.kamenov.martin.gosportbg.constants.Constants;
import com.kamenov.martin.gosportbg.login.LoginActivity;
import com.kamenov.martin.gosportbg.new_event.NewEventActivity;

import static android.media.MediaCodec.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment implements MenuContracts.IMenuView, View.OnClickListener {


    private View root;
    private TextureView mVideo;
    private MediaPlayer _introMediaPlayer;
    private MenuContracts.IMenuPresenter presenter;

    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.root = inflater.inflate(R.layout.fragment_menu, container, false);
        root.findViewById(R.id.container).setBackgroundColor(Constants.MAINCOLOR);
        presenter.subscribe(this);
        mVideo = root.findViewById(R.id.menu_video);
        // Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.clip);
        mVideo.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {

            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
                try {
                    _introMediaPlayer = MediaPlayer.create(getActivity(), R.raw.clip);
                    _introMediaPlayer.setSurface(new Surface(surfaceTexture));
                    _introMediaPlayer.setLooping(true);
                    _introMediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                    _introMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mediaPlayer.setVolume(0f, 0f);
                            mediaPlayer.setLooping(true);
                            mediaPlayer.start();
                        }
                    });

                } catch (Exception e) {
                    System.err.println("Error playing intro video: " + e.getMessage());
                }
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

            }
        });
        setListeners();
        return root;
    }

    private void destoryIntroVideo() {
        if (_introMediaPlayer != null) {
            _introMediaPlayer.stop();
            _introMediaPlayer.release();
            _introMediaPlayer = null;
        }
    }

    /*@Override
    public void onResume() {
        super.onResume();
        //mVideo.start();
    }*/

    @Override
    public void onDestroy() {
        presenter.unsubscribe();
        destoryIntroVideo();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.new_event:
                newEventButtonPressed();
                break;
            case R.id.incoming_event:
                showEventsButtonPressed();
                break;
            case R.id.logout_btn:
                logoutButtonPressed();
                break;
            case R.id.messages_btn:
                messagesButtonPressed();
                break;
            case R.id.teams_btn:
                teamsButtonPressed();
                break;
            case R.id.settings_btn:
                settingsButtonPressed();
                break;
        }

    }

    private void setListeners() {
        Button newEventButton = root.findViewById(R.id.new_event);
        newEventButton.setOnClickListener(this);

        Button incomingEventButton = root.findViewById(R.id.incoming_event);
        incomingEventButton.setOnClickListener(this);

        Button logoutButton = root.findViewById(R.id.logout_btn);
        logoutButton.setOnClickListener(this);

        Button messagesButton = root.findViewById(R.id.messages_btn);
        messagesButton.setOnClickListener(this);

        Button teamsButton = root.findViewById(R.id.teams_btn);
        teamsButton.setOnClickListener(this);

        Button settingsButton = root.findViewById(R.id.settings_btn);
        settingsButton.setOnClickListener(this);
    }

    @Override
    public void setPresenter(BaseContracts.Presenter presenter) {
        this.presenter = (MenuContracts.IMenuPresenter) presenter;
    }

    @Override
    public GoSportApplication getGoSportApplication() {
        return (GoSportApplication)getActivity().getApplication();
    }

    @Override
    public void newEventButtonPressed() {
        presenter.navigateToCreateNewEvents();
    }

    @Override
    public void showEventsButtonPressed() {
        presenter.navigateToShowEvents();
    }

    @Override
    public void logoutButtonPressed() {
        presenter.logout();
    }

    @Override
    public void teamsButtonPressed() {
        presenter.navigateToTeams();
    }

    @Override
    public void messagesButtonPressed() {
        presenter.navigateToMessages();
    }

    @Override
    public void settingsButtonPressed() {
        presenter.navigateToSettings();
    }

    @Override
    public void navigateToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void showEventsDialog() {
        CustomDialogClass customDialog = new CustomDialogClass(getActivity(), presenter);
        customDialog.show();
    }
}
