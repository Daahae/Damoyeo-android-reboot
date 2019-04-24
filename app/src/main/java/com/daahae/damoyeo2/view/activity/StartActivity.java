package com.daahae.damoyeo2.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.daahae.damoyeo2.R;
import com.daahae.damoyeo2.communication.RetrofitCommunication;
import com.daahae.damoyeo2.databinding.ActivityStartBinding;
import com.daahae.damoyeo2.model.LoginCheck;
import com.daahae.damoyeo2.model.UserLoginInfo;
import com.daahae.damoyeo2.handler.BackPressCloseHandler;
import com.daahae.damoyeo2.navigator.StartNavigator;
import com.daahae.damoyeo2.view_model.StartViewModel;
import com.daahae.damoyeo2.view.Constant;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class StartActivity extends AppCompatActivity implements StartNavigator{

    private StartViewModel startViewModel;

    private ActivityStartBinding binding;

    private GoogleSignInClient googleSignInClient;
    private GoogleApiClient googleApiClient;

    private FirebaseAuth mAuth;

    private GoogleSignInAccount account;

    private FirebaseUser user;

    private BackPressCloseHandler backPressCloseHandler;

    Context context;

    private boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        initViewModel();

        bindingView();

        setGoogleLoginSetting();

        setLogoAnimation();

        backPressCloseHandler = new BackPressCloseHandler(this);

    }

    private void initViewModel(){
        startViewModel = new StartViewModel(this);
    }

    private void bindingView(){
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_start);
        binding.setModel(startViewModel);

        startViewModel.onCreate();
    }

    private void setLogoAnimation(){
        ImageView imgLogo = findViewById(R.id.img_logo_start);
        final Animation animationTranslate = AnimationUtils.loadAnimation(this,R.anim.logo_translate);
        imgLogo.startAnimation(animationTranslate);
        visibleLoginButton();
    }

    private void visibleLoginButton(){
        startViewModel.setVisible(false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("context", "Loading finished");
                startViewModel.setVisible(true);
            }
        }, 1500);
    }

    private void setGoogleLoginSetting() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("627171791796-am6cdjotid9qotnejvhagpb2bvs66don.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mAuth = FirebaseAuth.getInstance();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.d(Constant.TAG, "Login fail");
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        autoLogin();
    }

    private void autoLogin(){

        // auto login
        account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            Log.v("account",account.getEmail());
            firebaseAuthWithGoogle(account);
            setAccountInformation();
            updateUserInformation();
            loginCheckCallback();
        }
    }

    private void setAccountInformation() {
        if (user != null) {
            Constant.email = user.getEmail();
            Constant.nickname = user.getDisplayName();
        }
        else {
            Constant.email = account.getEmail();
            Constant.nickname = account.getDisplayName();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == Constant.LOG_IN) {
            try {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(Constant.TAG, "Google sign in failed", e);
            }
        } else if(requestCode == Constant.GOOGLE_LOGIN) {
            if(resultCode == Constant.LOG_OUT) {
                //FirebaseAuth.getInstance().signOut();
                signOut();
                Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 구글 로그인
    private void googleSignIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, Constant.LOG_IN);

    }

    private void loginCheckCallback(){
        RetrofitCommunication.LoginCallBack loginCallBack = new RetrofitCommunication.LoginCallBack() {
            @Override
            public void loginDataPath(LoginCheck loginCheck) {
                Log.v("Login Check", loginCheck.getHistory()+"");

                /*
                Intent intent = new Intent(StartActivity.this, InterestActivity.class);
                intent.putExtra(Constant.LOGIN, Constant.GOOGLE_LOGIN);
                startActivityForResult(intent, Constant.GOOGLE_LOGIN);
                */

                if (loginCheck.getHistory() == 0) {
                    Intent intent = new Intent(context, InterestActivity.class);
                    intent.putExtra(Constant.LOGIN, Constant.GOOGLE_LOGIN);
                    startActivityForResult(intent, Constant.GOOGLE_LOGIN);
                    Log.v("화면전환", "Interest");
                } else {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra(Constant.LOGIN, Constant.GOOGLE_LOGIN);
                    startActivityForResult(intent, Constant.GOOGLE_LOGIN);
                    Log.v("화면전환", "Main");
                }


                Toast.makeText(getApplicationContext(), "안녕하세요, "+ Constant.nickname + "님", Toast.LENGTH_SHORT).show();
            }
        };
        RetrofitCommunication.getInstance().setLoginCheck(loginCallBack);

    }

    // 파이어베이스와 로그인 인증정보 동기화
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(Constant.TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(Constant.TAG, "signInWithCredential:success");
                            user = mAuth.getCurrentUser();

                            setAccountInformation();
                            updateUserInformation();
                            loginCheckCallback();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(Constant.TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    // 로그아웃
    private void signOut() {
        googleApiClient.connect();
        googleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                mAuth.signOut();
                if(googleApiClient.isConnected()) {
                    Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            if(status.isSuccess()) {
                                Log.d(Constant.TAG, "User Logged out");
                                //setResult();
                            }
                        }
                    });
                }
            }

            @Override
            public void onConnectionSuspended(int i) {

            }
        });
    }

    @Override
    public void loginActivity() {
        googleSignIn();
    }

    @Override
    public void sendUserInformation() {
        updateUserInformation();
    }

    private void updateUserInformation(){
        if(Constant.email!=null && !isLogin) {
            isLogin = true;
            UserLoginInfo.getInstance().setEmail(Constant.email);
            UserLoginInfo.getInstance().setNickname(Constant.nickname);
            RetrofitCommunication.getInstance().sendLoginInformation();
        }
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}
