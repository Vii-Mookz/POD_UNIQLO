package com.hitachi_tstv.mist.it.pod_uniqlo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hitachi_tstv.mist.it.pod_uniqlo.Fragment.ListJobFragment;
import com.hitachi_tstv.mist.it.pod_uniqlo.Fragment.MainFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


EditText EditTxtUsername,EditTxtPassword;
Button btnLogin;
    String[] loginStrings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            MainFragment mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.contentFragment, mainFragment).commit();
        }

        bindwidget();
        onViewClicked();

    }

    private void bindwidget() {
        EditTxtUsername = findViewById(R.id.usernameEditText);
        EditTxtPassword = findViewById(R.id.passwordEditText);
        btnLogin = findViewById(R.id.login_btn);

    }

    class SynGetUser extends AsyncTask<Void, Void, String> {

        private String usernameString, passwordString;

        SynGetUser(String usernameString, String passwordString) {
            this.usernameString = usernameString;
            this.passwordString = passwordString;

        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                Log.d("Login", "Send ==> " + usernameString + " , " + passwordString);
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("isAdd", "true")
                        .add("username", usernameString)
                        .add("password", passwordString)
                        .build();
                Request.Builder builder = new Request.Builder();
                Request request = builder.post(requestBody).url(Constant.urlGetUserLogin).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!s.equals("null")) {
                Log.d("NK-Tag-MA", s);
                try {

                    JSONArray jsonArray = new JSONArray(s);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String truckIdString = jsonObject.getString("TruckID");
                    String truckRegString = jsonObject.getString("TruckReg");
                    String truckTypeIdString = jsonObject.getString("TruckTypeID");
                    String driverNameString = jsonObject.getString("DriverName");
                    String driverSurname = jsonObject.getString("DriverSurname");
                    String[] loginStrings = new String[]{truckIdString, driverNameString, driverSurname, truckRegString, truckTypeIdString, usernameString};
                    Intent intent = new Intent(MainActivity.this, ListJobFragment.class);
                    intent.putExtra("Login", loginStrings);
                    intent.putExtra("Date", "");
                    startActivity(intent);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("NK-Tag-MA", String.valueOf(e) + " Line: " + e.getStackTrace()[0].getLineNumber());
                }
            } else {

            }
        }
    }


    public void onViewClicked() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SynGetUser synGetUser = new SynGetUser(EditTxtUsername.getText().toString(), EditTxtPassword.getText().toString());
                synGetUser.execute();
            }
        });

    }
}
