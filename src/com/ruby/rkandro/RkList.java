package com.ruby.rkandro;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.ruby.rkandro.pojo.RkListItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.ruby.rkandro.adapter.RkListAdapter;
import com.ruby.rkandro.soap.SoapWebServiceInfo;
import com.ruby.rkandro.soap.SoapWebServiceUtility;
import com.apperhand.device.android.AndroidSDKProvider;
import com.startapp.android.publish.HtmlAd;
import com.startapp.android.publish.model.AdPreferences;


public class RkList extends SherlockActivity {

    private ListView lv;
    private ProgressDialog progressDialog;

    private List<RkListItem> RkList = new ArrayList<RkListItem>();
    RkListAdapter rkListAdapter;

    ConnectionDetector cd;

    private HtmlAd htmlAd = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_rk_list);
        getSupportActionBar().setTitle(Html.fromHtml("<b><font color='#333333'>"+getString(R.string.app_name)+"</font></b>"));

        //AndroidSDKProvider.setTestMode(true);
        AndroidSDKProvider.initSDK(this);
        AdPreferences adPreferences = new AdPreferences("105147417", "205460888", AdPreferences.TYPE_INAPP_EXIT);

        htmlAd = new HtmlAd(this);
        htmlAd.load(adPreferences, null);

        // creating connection detector class instance
        cd = new ConnectionDetector(getApplicationContext());
        // get Internet status
        boolean isInternetPresent = cd.isConnectingToInternet();

        if (isInternetPresent) {
            // Internet Connection is Present
            lv = (ListView) findViewById(R.id.lviRkList);
            lv.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    RkListItem rkDetail = (RkListItem) parent.getItemAtPosition(position);
                    Intent i = new Intent(RkList.this, RkListDetail.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ID", rkDetail.getId().toString());
                    i.putExtras(bundle);
                    startActivity(i);

                }
            });
            new RkDetail().execute(new Object());

            // Look up the AdView as a resource and load a request.
            AdView adView = (AdView) this.findViewById(R.id.adview);
            adView.loadAd(new AdRequest());
        } else {
            // Internet connection is not present
            // Ask user to connect to Internet
            showAlertDialog(RkList.this, "No Internet Connection",
                    "You don't have internet connection.");
        }

    }


    @Override
    public void onBackPressed() {
        if(htmlAd != null){
            htmlAd.show();
        }
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("refresh").setIcon(R.drawable.ic_refresh_inverse).
                setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String title = (String) item.getTitle();
        if (title.equalsIgnoreCase("refresh")) {
            new RkDetail().execute(new Object());
        }
        return true;
    }

    private List<RkListItem> convertJsonToList(JSONObject jsonObject)
            throws JSONException {
        List<RkListItem> details = new ArrayList<RkListItem>();
        int total = (Integer) jsonObject.get("Total");
        JSONArray detailArray;
        RkListItem rkListItem;

        for (int i = 0; i < total; i++) {
            detailArray = (JSONArray) jsonObject.get("Record" + i);
            rkListItem = new RkListItem();
            rkListItem.setId((Integer) detailArray.get(0));
            rkListItem.setTitle((i+1) + ". " + detailArray.get(1));

            details.add(rkListItem);
        }
        return details;
    }

    class RkDetail extends AsyncTask<Object, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(RkList.this, "",
                    "Loading....", true, false);
        }

        protected String doInBackground(Object... parametros) {

            String result = null;
            try {
                String envelop = String.format(SoapWebServiceInfo.GETLIST_ENVELOPE);
                result = SoapWebServiceUtility.callWebService(envelop, SoapWebServiceInfo.GETLIST_SOAP_ACTION, SoapWebServiceInfo.GETLIST_RESULT_TAG);
                if(result != null){
                    JSONObject resJsonObj = new JSONObject(result);
                    RkList = convertJsonToList(resJsonObj);
                }
            } catch (Exception e) {
                progressDialog.dismiss();
            }

            return result;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (RkList.size() > 0) {
                rkListAdapter = new RkListAdapter(RkList.this, RkList);
                lv.setAdapter(rkListAdapter);
                rkListAdapter.notifyDataSetChanged();
            }

            progressDialog.dismiss();
        }
    }


    public void showAlertDialog(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting alert dialog icon
        alertDialog.setIcon(R.drawable.fail);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
