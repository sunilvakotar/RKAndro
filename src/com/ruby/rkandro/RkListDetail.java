package com.ruby.rkandro;


import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.ads.AdRequest;
import com.google.ads.AdView;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class RkListDetail extends Activity {
	private String description;
	
	TextView textDescription;
    ImageView closeButton;
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

        closeButton = (ImageView) findViewById(R.id.closeBtn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Close ads", Toast.LENGTH_SHORT).show();
            }
        });

    }
	

}
