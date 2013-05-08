package com.ruby.rkandro;


import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdView;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import com.ruby.rkandro.service.AdPopupService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class RkListDetail extends Activity {

    public static final long NOTIFY_INTERVAL = 10 * 1000; // 10 seconds

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;

	private String description;
	
	TextView textDescription;
    ImageView closeButton;
    RelativeLayout adsLayout;
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rk_description);
		
		Bundle extra = getIntent().getExtras();
		
		description = extra != null ? extra.getString("description") : " ";
		
		textDescription = (TextView) findViewById(R.id.TextDescription);
		
		textDescription.setText(Html.fromHtml(description));
		
		 // Look up the AdView as a resource and load a request.
        AdView adView = (AdView)this.findViewById(R.id.adview);
        adView.loadAd(new AdRequest());

        AdView adViewPopup = (AdView)this.findViewById(R.id.adviewpopup);
        adViewPopup.loadAd(new AdRequest());

        adsLayout = (RelativeLayout) findViewById(R.id.popupWithCross);
        closeButton = (ImageView) findViewById(R.id.closeBtn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adsLayout.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Close ads", Toast.LENGTH_SHORT).show();
            }
        });

        if (mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
        mTimer.scheduleAtFixedRate(new PopupDisplayTimerTask(), 0, NOTIFY_INTERVAL);
    }

    @Override
    protected void onDestroy() {
        if (mTimer != null) {
            mTimer.cancel();
            Toast.makeText(getApplicationContext(), "Stop timer",
                    Toast.LENGTH_SHORT).show();
        }
        super.onDestroy();
    }

    class PopupDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    // Make popup ad visible
                    if(adsLayout.getVisibility() == View.GONE){
                        adsLayout.setVisibility(View.VISIBLE);
                        // display toast
                        Toast.makeText(getApplicationContext(), "Popup show",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }

    }
}
