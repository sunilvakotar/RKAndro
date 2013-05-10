package com.ruby.rkandro;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.ads.AdRequest;
import com.google.ads.AdView;

import java.util.Timer;
import java.util.TimerTask;

public class RkListDetail extends SherlockActivity {

    public static final long NOTIFY_INTERVAL = 60 * 1000; // 10 seconds

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
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_rk_description);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
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
                //Toast.makeText(getApplicationContext(), "Close ads", Toast.LENGTH_SHORT).show();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(RkListDetail.this, RkList.class);
        startActivity(i);
        return true;
    }

    @Override
    protected void onDestroy() {
        if (mTimer != null) {
            mTimer.cancel();
            //Toast.makeText(getApplicationContext(), "Stop timer",Toast.LENGTH_SHORT).show();
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
                        //Toast.makeText(getApplicationContext(), "Popup show", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }

    }
}
