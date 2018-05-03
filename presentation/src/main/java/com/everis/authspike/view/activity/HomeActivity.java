package com.everis.authspike.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.everis.authspike.R;
import com.everis.authspike.presenter.HomePresenter;
import com.everis.authspike.presenter.HomePresenterImpl;
import com.everis.authspike.utils.PreferenceManager;
import com.everis.authspike.view.views.HomeView;
import com.everis.domain.model.IntroMessage;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity implements HomeView{

    private static final String TAG = HomeActivity.class.getSimpleName();

    @BindView(R.id.user_state_tv)
    TextView userStateTv;

    @BindView(R.id.intro_message_tv)
    TextView introMessageTv;

    private HomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ButterKnife.bind(this);
        presenter = new HomePresenterImpl(this);
        preferenceManager = new PreferenceManager(this);
        presenter.loadUserState();
        presenter.loadIntroMessage();
        presenter.loadUserEnabled(userUid);
        presenter.loadUserByPhoneReference("993441766");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        if(!preferenceManager.getActiveSessionPreference()){
            presenter.logOut();
        }
        presenter.onDestroy();
        super.onDestroy();
    }

    @OnClick(R.id.log_out_button)
    void logOutClicked(){
        presenter.logOut();
    }

    @OnClick(R.id.delete_user_button)
    void deleteUserClicked(){
        presenter.deleteUser();
    }

    @OnClick(R.id.open_contacts_button)
    void openContactsClicked(){
        navigator.navigateToContactsActivity(this);
    }

    @Override
    public void showLoading() {
        //no loading for now
    }

    @Override
    public void hideLoading() {
        //no loading for now
    }

    @Override
    public void changeUserStateText(String userStateString) {
        this.userStateTv.setText(userStateString);
    }

    @Override
    public void setIntroMessageText(IntroMessage introMessage) {
        this.introMessageTv.setText(introMessage.getDisplayMessage());
    }

    @Override
    public void logOutView() {
        navigator.navigateToRegisterActivity(this);
    }
}
