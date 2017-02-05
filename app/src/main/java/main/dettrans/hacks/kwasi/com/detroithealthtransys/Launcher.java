package main.dettrans.hacks.kwasi.com.detroithealthtransys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;

public class Launcher extends AppCompatActivity {

    private static final long DELAYED_RESPONSE = 1000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.viewlauncher);
        Bitmap bitmapTransformed = new CircularTransform().transform(bitmap);
        ImageView view = (ImageView) findViewById(R.id.launcher_logo);
        view.setImageBitmap(bitmapTransformed);
        
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainActivityIntent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(mainActivityIntent);
                finish();
            }
        }, DELAYED_RESPONSE);
    }
}
