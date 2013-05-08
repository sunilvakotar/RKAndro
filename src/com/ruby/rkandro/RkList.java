package com.ruby.rkandro;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rk_list);

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
        //new RkDetial().execute(new Object());

        RkListItem rkListItem = new RkListItem();
        rkListItem.setId(0);
        rkListItem.setTitle("sunil");
        rkListItem.setDescription("Sunil Desc");
        RkList.add(rkListItem);
        rkListAdapter = new RkListAdapter(RkList.this, RkList);
        lv.setAdapter(rkListAdapter);

        // Look up the AdView as a resource and load a request.
        AdView adView = (AdView) this.findViewById(R.id.adview);
        adView.loadAd(new AdRequest());
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

    class RkDetial extends AsyncTask<Object, Void, String> {
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
                JSONObject resJsonObj = new JSONObject(result);
                RkList = convertJsonToList(resJsonObj);
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

}
