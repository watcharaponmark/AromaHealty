package biz.markgo.dispensergeniusrobot.aroma;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private String key = "CWHOOGCo6p5VtnsUSa9gUcxYN8tTKXtd0uY8A2aZ";
    private String Thing = "NodeMCU_relay";

    private Switch Switch1;
    private Switch Switch2;
    private Switch Switch3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Switch1 = (Switch) findViewById(R.id.switch_1);
        Switch2 = (Switch) findViewById(R.id.switch_2);
        Switch3 = (Switch) findViewById(R.id.switch_3);


        //#######################################################
        NodeMCU_relay_get nodeMCU_relay1_get =new NodeMCU_relay_get("relay1");
        nodeMCU_relay1_get.execute(key,Thing,"relay1");
        //#######################################################
        NodeMCU_relay_get nodeMCU_relay2_get =new NodeMCU_relay_get("relay2");
        nodeMCU_relay2_get.execute(key,Thing,"relay2");
        //#######################################################
        NodeMCU_relay_get nodeMCU_relay3_get =new NodeMCU_relay_get("relay3");
        nodeMCU_relay3_get.execute(key,Thing,"relay3");
        //#######################################################

            Switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {

                        String Channel = "relay1";
                        String Value = "1";

                        Log.e(TAG,"relay1 : 1");
                        NodeMCU_relay_set nodeMCU_relay_set =new NodeMCU_relay_set("1");
                        nodeMCU_relay_set.execute(key,Thing,Channel,Value);


                    }else{

                        String Channel = "relay1";
                        String Value = "0";

                        Log.e(TAG,"relay1 : 0");
                        NodeMCU_relay_set httpGetRequest_nodeMCU_servo =new NodeMCU_relay_set("0");
                        httpGetRequest_nodeMCU_servo.execute(key,Thing,Channel,Value);

                    }

                }
            });


        Switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    String Channel = "relay2";
                    String Value = "1";

                    Log.e(TAG,"relay2 : 1");
                    NodeMCU_relay_set nodeMCU_relay_set =new NodeMCU_relay_set("1");
                    nodeMCU_relay_set.execute(key,Thing,Channel,Value);


                }else{

                    String Channel = "relay2";
                    String Value = "0";

                    Log.e(TAG,"relay2 : 0");
                    NodeMCU_relay_set nodeMCU_relay_set =new NodeMCU_relay_set("0");
                    nodeMCU_relay_set.execute(key,Thing,Channel,Value);

                }

            }
        });

        Switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    String Channel = "relay3";
                    String Value = "1";

                    Log.e(TAG,"relay3 : 1");
                    NodeMCU_relay_set nodeMCU_relay_set =new NodeMCU_relay_set("1");
                    nodeMCU_relay_set.execute(key,Thing,Channel,Value);


                }else{

                    String Channel = "relay3";
                    String Value = "0";

                    Log.e(TAG,"relay3 : 0");
                    NodeMCU_relay_set nodeMCU_relay_set =new NodeMCU_relay_set("0");
                    nodeMCU_relay_set.execute(key,Thing,Channel,Value);

                }

            }
        });




    }


    public class NodeMCU_relay_set extends AsyncTask<String, Void, String> {

        private String relay_status;

        //#####################################################################################
        public NodeMCU_relay_set(String relay_status) {
            this.relay_status = relay_status;
        }

        //#####################################################################################
        @Override
        protected String doInBackground(String... params) {
            String Key = params[0];
            String Thing = params[1];
            String Channel = params[2];
            String Value = params[3];

            String Str_URL = "https://api.anto.io/channel/set/" + Key + "/" + Thing + "/" + Channel + "/" + Value;

            String result;
            String inputLine;
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(Str_URL);
                //Create a connection
                HttpURLConnection connection = (HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod("GET");
                connection.setReadTimeout((1000 * 60) * 5);
                connection.setConnectTimeout((1000 * 60) * 5);

                //Connect to our url
                connection.connect();
                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
                result = null;
            }
            return result;
        }

        //#####################################################################################
        protected void onPostExecute(String result) {

            String err = null;
            String status = "";
            Log.e(TAG, result);
            try {

                JSONObject root = new JSONObject(result);
                status = root.getString("result");

                Log.e(TAG, status);


                if (status.equals("true")) {

                    if (relay_status.equals("1")) {

                        Toast toast = Toast.makeText(getApplication(), "กำลังปล่อยกลิ่น", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();

                    }else{

                        Toast toast = Toast.makeText(getApplication(), "ปิดการปล่อยกลิ่นเรียบร้อย", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();

                    }

                } else {

                    if (relay_status.equals("1")) {

                        Toast toast = Toast.makeText(getApplication(), "ปล่อยกลิ่นไม่สำเร็จ่", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();

                    }else{

                        Toast toast = Toast.makeText(getApplication(), "ปิดการปล่อยกลิ่นไม่สำเร็จ!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();

                    }
                }


            } catch (JSONException e) {

                e.printStackTrace();
                err = "Exception: " + e.getMessage();

            }
        }
        //#####################################################################################
    }



    public class NodeMCU_relay_get extends AsyncTask<String, Void, String> {

        private String relay;

        public  NodeMCU_relay_get(String relay){
            this.relay=relay;
        }
        //#####################################################################################
        @Override
        protected String doInBackground(String... params) {
            String Key = params[0];
            String Thing = params[1];
            String Channel = params[2];

            String Str_URL = "https://api.anto.io/channel/get/" + Key + "/" + Thing + "/" + Channel;

            String result;
            String inputLine;
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(Str_URL);
                //Create a connection
                HttpURLConnection connection = (HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod("GET");
                connection.setReadTimeout((1000 * 60) * 5);
                connection.setConnectTimeout((1000 * 60) * 5);

                //Connect to our url
                connection.connect();
                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
                result = null;
            }
            return result;
        }

        //#####################################################################################
        protected void onPostExecute(String result) {

            String err = null;
            String status = "";
            Log.e(TAG, result);
            try {

                JSONObject root = new JSONObject(result);
                Log.e(TAG,result);
                status = root.getString("result");

                Log.e(TAG, status);

                if (status.equals("true")) {

                    String Check_relay = root.getString("value");
                    Log.e(TAG, Check_relay);

                    if(relay.equals("relay1")){

                        if(Check_relay.equals("1")) {
                            Switch1.setChecked(true);
                        }else{
                            Switch1.setChecked(false);
                        }

                    }else if(relay.equals("relay2")){

                        if(Check_relay.equals("1")) {
                            Switch2.setChecked(true);
                        }else{
                            Switch2.setChecked(false);
                        }

                    }else if(relay.equals("relay3")){

                        if(Check_relay.equals("1")) {
                            Switch3.setChecked(true);
                        }else{
                            Switch3.setChecked(false);
                        }

                    }


                }


            } catch (JSONException e) {

                e.printStackTrace();
                err = "Exception: " + e.getMessage();

            }
        }
        //#####################################################################################
    }



}
