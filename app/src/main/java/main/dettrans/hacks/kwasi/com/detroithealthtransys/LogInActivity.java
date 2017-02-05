package main.dettrans.hacks.kwasi.com.detroithealthtransys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.esri.core.io.UserCredentials;
import com.esri.core.portal.Portal;
import com.esri.core.portal.PortalAccess;
import com.esri.core.portal.PortalInfo;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


public class LogInActivity extends AppCompatActivity {

    private Button signInButton;
    private LoginButton facebookButton;
    private CallbackManager callbackManager;
    private EditText usernameEditText;
    private EditText passwordEditText;
    public static Portal portal;
    private PortalInfo portalInfos;
    private boolean loggedIn = false;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_log_in);
        usernameEditText = (EditText) findViewById(R.id.username);
        passwordEditText = (EditText) findViewById(R.id.password);
        //activate facebook login logic
        initiateFacebookLogin();

        //activate arcgis.com login option
        initiateArcGISLogin();

        //activate remember-me method
        initiateRememberMe();
    }

    /**
     * Call this method to initiate persistence login
     */

    private void initiateRememberMe() {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * Call this method to intiate arcgis.com login when the user
     * click on the supplied sign in button
     */
    private void initiateArcGISLogin() {

        signInButton = (Button) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    signInToArcGIS(new UserCredentials());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Call this method to initiate facebook login when user click
     * the facebook login button
     */
    private void initiateFacebookLogin() {
        facebookButton = (LoginButton) findViewById(R.id.facebook_login_button);
        facebookButton.setReadPermissions("public_profile", "email", "user_friends");
        callbackManager = CallbackManager.Factory.create();
        facebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                //get user profile and send along with intent
                new ProfileTracker() {


                    @Override
                    protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("FBUserFullName", currentProfile.getName());
                        intent.putExtra("FBUserProfile", currentProfile.getProfilePictureUri(10, 10).toString());
                        startActivity(intent);
                    }
                };

            }

            @Override
            public void onCancel() {
                Snackbar.make(getCurrentFocus(), "Login cancelled by user", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Snackbar.make(getCurrentFocus(), error.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });

    }


    /**
     * Use this method to log in to arcgis.com
     *
     * @param userCredentials
     * @return
     */
    private Portal signInToArcGIS(UserCredentials userCredentials) throws Exception {
        boolean checkedLogIn = true;
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().toString();
        userCredentials.setUserAccount(username, password);
        portal = new Portal("http://www.arcgis.com", userCredentials);
        ArcGISAsyncLogIn logInHelper = new ArcGISAsyncLogIn();
        logInHelper.execute(portal);
        return portal;
    }


    class ArcGISAsyncLogIn extends AsyncTask<Portal, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            //progressDialog = ProgressDialog.show(getApplicationContext(), "www.arcgis.com", "Attempting arcgis.com login....");
        }


        @Override
        protected Boolean doInBackground(Portal... portals) {
            try {
                portalInfos = portals[0].fetchPortalInfo();
                if (portalInfos.getAccess() == PortalAccess.PUBLIC) {
                    loggedIn = true;
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("PortalUserFullName", portal.fetchUser().getFullName());
                    intent.putExtra("ArcGISLogIn", "Log in for ArcGIS");
                    startActivity(intent);
                }

            } catch (Exception ex) {
                Log.e("Exception", ex.getMessage());
            }
            return loggedIn;
        }

        @Override
        protected void onPostExecute(Boolean results) {
            /*if (progressDialog != null) {
                progressDialog.dismiss();
            }*/
        }
    }


}
