package main.dettrans.hacks.kwasi.com.detroithealthtransys.models;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;

import com.esri.android.oauth.OAuthView;
import com.esri.core.io.UserCredentials;
import com.esri.core.map.CallbackListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import main.dettrans.hacks.kwasi.com.detroithealthtransys.MainActivity;
import main.dettrans.hacks.kwasi.com.detroithealthtransys.R;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by kwas7493 on 2/1/2017.
 */

public class EsriAuthenticator extends Fragment {

    public static  String credentialFileName;
    private AlertDialog alertDialog;
    private AlertDialog mImplementEncryptionDialog;



    private UserCredentials userLoginCredentials;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        credentialFileName = getString(R.string.credentialfilename);

        createAlertDialog();

        setupEncryptionPatternAlertDialog();

        if(checkFileExist(credentialFileName)){
            alertDialog.show();
        }else {
            showEsriOAuth(getApplicationContext());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState){

        return LayoutInflater.from(getContext()).inflate(R.layout.mainoauth, container, false);

    }


    private void createAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle(getString(R.string.alertdialogtitle));
        alertDialogBuilder.setMessage(getString(R.string.authmessage))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.positivebutton), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            loadEncryptedInfo();
                        }catch (IOException ioException){
                                ioException.printStackTrace();
                        }
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                })
        .setNegativeButton(getString(R.string.negativebutton), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        alertDialog = alertDialogBuilder.create();
    }


    private Object readFromFile(String filename) throws IOException {

        Object object = null;
        FileInputStream fis = null;
        ObjectInputStream is = null;

        try {
            fis = getContext().openFileInput(filename);
            is = new ObjectInputStream(fis);
            object = is.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return object;
    }

    private boolean checkFileExist(String fileName){
        File file = getContext().getFileStreamPath(fileName);
        return file.exists();
    }



    public OAuthView showEsriOAuth(Context context){

        OAuthView esriOAuthView = new OAuthView(context, context.getResources().getString(R.string.esriportalurl),
                context.getResources().getString(R.string.arcgisdevelopercliendID),
        new CallbackListener<UserCredentials>(){
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onCallback(UserCredentials credentials) {
                setUserLoginCredentials(credentials);
                try {
                    saveEncryptedCredentials();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mImplementEncryptionDialog.show();
                    }
                });
            }
        });


        //ssl errors
        esriOAuthView.setOnSslErrorListener(new OAuthView.OnSslErrorListener() {
            @Override
            public void onReceivedSslError(OAuthView oAuthView, SslErrorHandler sslErrorHandler, SslError sslError) {
                //TODO for debugging only remove from production <code></code>
                Log.e("OAuthSSLError", ""+sslError);

            }
        });

        MainActivity.mapView.addView(esriOAuthView, new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT
        ));

        return esriOAuthView;
    }

    public void setupEncryptionPatternAlertDialog() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

        // set title
        alertDialogBuilder.setTitle(getResources().getString(R.string.positivebutton));

        // set dialog message
        alertDialogBuilder.setMessage(getResources().getString(R.string.implement_an_encryption_pattern))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.positivebutton), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        // Start UserContentActivity
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);

                    }
                });

        // create alert dialog
        mImplementEncryptionDialog = alertDialogBuilder.create();

    }


    private void saveEncryptedCredentials() throws IOException{
        writeToFile(credentialFileName, getUserLoginCredentials());
    }

    private void writeToFile(String credentialFileName, Object userLoginCredentials) {
        FileOutputStream fos = null;
        ObjectOutputStream os = null;

        try {
            fos = getContext().openFileOutput(credentialFileName, Context.MODE_PRIVATE);
            os = new ObjectOutputStream(fos);
            os.writeObject(userLoginCredentials);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public UserCredentials getUserLoginCredentials() {
        return userLoginCredentials;
    }


    public void setUserLoginCredentials(UserCredentials userLoginCredentials) {
        this.userLoginCredentials = userLoginCredentials;
    }


    private void loadEncryptedInfo() throws IOException{
        setUserLoginCredentials((UserCredentials)readFromFile(credentialFileName));
    }

   /* @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        alertDialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }*/


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
