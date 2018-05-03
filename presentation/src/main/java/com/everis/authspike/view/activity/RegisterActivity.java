package com.everis.authspike.view.activity;

import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.everis.authspike.R;
import com.everis.authspike.presenter.RegisterPresenter;
import com.everis.authspike.presenter.RegisterPresenterImpl;
import com.everis.authspike.utils.PreferenceManager;
import com.everis.authspike.view.views.RegisterView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements RegisterView {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.tiet_email)
    TextInputEditText tietEmail;

    @BindView(R.id.tiet_password)
    TextInputEditText tietPassword;

    @BindView(R.id.keep_session_checkbox)
    AppCompatCheckBox keepSessionChck;

    private RegisterPresenter presenter;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        presenter = new RegisterPresenterImpl(this);
        preferenceManager = new PreferenceManager(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.presenter.onDestroy();
    }

    @Override
    public void showLoading() {
        this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        this.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showLoggedInScreen() {
        navigator.navigateToHomeActivity(this);
    }

    @OnClick(R.id.register_button)
    void emailPasswordRegisterClicked(){
        String email = this.tietEmail.getText().toString();
        String password = this.tietPassword.getText().toString();
        preferenceManager.setActiveSession(keepSessionChck.isChecked());
        Log.d(TAG, "Sesion activa: " + keepSessionChck.isChecked());
        presenter.createUser(email,password);

    }

    @OnClick(R.id.login_button)
    void emailPasswordLogin(){
        String email = this.tietEmail.getText().toString();
        String password = this.tietPassword.getText().toString();
        preferenceManager.setActiveSession(keepSessionChck.isChecked());
        Log.d(TAG, "Sesion activa: " + keepSessionChck.isChecked());
        presenter.signInUser(email,password);
    }
}
