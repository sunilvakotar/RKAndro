package com.ruby.rkandro;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;
import com.ruby.rkandro.pojo.RkListItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.ruby.rkandro.adapter.RkListAdapter;
import com.ruby.rkandro.soap.SoapWebServiceInfo;
import com.ruby.rkandro.soap.SoapWebServiceUtility;


public class RkList extends Activity {

    private ListView lv;
    private ProgressDialog progressDialog;

    private List<RkListItem> RkList = new ArrayList<RkListItem>();
    RkListAdapter rkListAdapter;

    ConnectionDetector cd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_rk_list);

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
                    bundle.putString("description", rkDetail.getDescription());
                    i.putExtras(bundle);
                    startActivity(i);

                }
            });
            new RkDetail().execute(new Object());

            /*RkListItem rkListItem = new RkListItem();
            rkListItem.setId(0);
            rkListItem.setTitle("Test 1");
            rkListItem.setDescription("Sunil Desc The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described Sunil Desc The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described Sunil Desc The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described Sunil Desc The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described");
            RkList.add(rkListItem);
            rkListItem = new RkListItem();
            rkListItem.setId(0);
            rkListItem.setTitle("Test 2");
            rkListItem.setDescription("Sunil Desc The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described Sunil Desc The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described");
            RkList.add(rkListItem);
            rkListItem = new RkListItem();
            rkListItem.setId(0);
            rkListItem.setTitle("Test 3");
            rkListItem.setDescription("Sunil Desc The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described Sunil Desc The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described");
            RkList.add(rkListItem);
            rkListItem = new RkListItem();
            rkListItem.setId(0);
            rkListItem.setTitle("Test 4");
            rkListItem.setDescription("Sunil Desc The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described Sunil Desc The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described The database adapters will be different from the ones you may be used to. They're based on JDBC. For example, the MySQL adapter install is described");
            RkList.add(rkListItem);
            rkListAdapter = new RkListAdapter(RkList.this, RkList);
            lv.setAdapter(rkListAdapter);*/

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_rk_list, menu);
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
            rkListItem.setTitle((String) detailArray.get(1));
            rkListItem.setDescription((String) detailArray.get(2).toString());

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
                String envelop = String.format(SoapWebServiceInfo.RKLIST_ENVELOPE);
                result = SoapWebServiceUtility.callWebService(envelop, SoapWebServiceInfo.RKLIST_SOAP_ACTION, SoapWebServiceInfo.RKLIST_RESULT_TAG);
                //if(result != null){
                    JSONObject resJsonObj = new JSONObject(result);
                    RkList = convertJsonToList(resJsonObj);
                //}
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
