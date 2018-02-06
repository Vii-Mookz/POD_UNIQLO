package com.hitachi_tstv.mist.it.pod_uniqlo.Fragment;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hitachi_tstv.mist.it.pod_uniqlo.Constant;
import com.hitachi_tstv.mist.it.pod_uniqlo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
//    private final String NAMESPACE = "http://tempuri.org/";
//    private final String URL = "http://203.154.103.42/ServiceTransport/Service.svc?wsdl";
//    private final String SOAP_ACTION = "http://203.154.103.42/ServiceTransport/Service.svc?wsdl/GetUserLogin";
//    private final String METHOD_NAME = "GetUserLogin";


    @BindView(R.id.usernameEditText)
    EditText usernameEditText;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;
    @BindView(R.id.login_btn)
    Button loginBtn;
    Unbinder unbinder;
    @BindView(R.id.txtResult)
    TextView txtResult;

    private String usernameString, passwordString;
    private String[] loginStrings;
    String TAG = MainFragment.class.getSimpleName();

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                } else {
                    //not granted
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //What is permission be request
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET,Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.BLUETOOTH,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_CONTACTS}, 101);

    }

    //Check the permission is already have
    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.GET_ACCOUNTS);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (!checkIfAlreadyhavePermission()) {
            requestForSpecificPermission();
        }

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.login_btn)
    public void onViewClicked() {
        usernameString = usernameEditText.getText().toString();
        passwordString = passwordEditText.getText().toString();

        SyncCheckLogin syncCheckLogin = new SyncCheckLogin(getActivity(), usernameString, passwordString);
        syncCheckLogin.execute();

    }
    private class SyncCheckLogin extends AsyncTask<Void, Void, String>  {
        private Context context;
        private String usernameString, passwordString;

        public SyncCheckLogin(Context context, String usernameString, String passwordString) {
            this.context = context;
            this.usernameString = usernameString;
            this.passwordString = passwordString;
        }


        @Override
        protected String doInBackground(Void... voids) {

            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("isAdd", "true")
                        .add("username", usernameString)
                        .add("password", passwordString)
                        .build();
                Request request = new Request.Builder().url(Constant.urlGetUser).post(requestBody).build();
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
            if (!(s == null)) {
                try {
                    Log.d(TAG, "onPostExecute: " + s);

//                    JSONArray jsonArray = new JSONArray(s);
                    JSONObject jsonObject = new JSONObject(s);
//                    JSONObject jsonObject = jsonArray.getJSONObject(0);
//                    String truckIdString = jsonObject.getString("TruckID");
                    String truckRegString = jsonObject.getString("TruckReg");
//                    String truckTypeIdString = jsonObject.getString("TruckType");
                    String driverNameString = jsonObject.getString("DriverName");
                    String driverSurname = jsonObject.getString("DriverSurname");



                    String[] loginStrings = new String[]{driverNameString, driverSurname, truckRegString, usernameString};

                    FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    //Send Arguments
                    ListJobFragment listJobFragment = new ListJobFragment();
                    Bundle args = new Bundle();
                    args.putStringArray("Login", loginStrings);
                    args.putString("Date","");
                    listJobFragment.setArguments(args);

                    fragmentTransaction.replace(R.id.contentFragment, listJobFragment);
                    fragmentTransaction.commit();



                    Log.d(TAG,"result" + loginStrings);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("UNIQLO-TAG", String.valueOf(e) + " Line: " + e.getStackTrace()[0].getLineNumber());
                }

            } else {
                Toast.makeText(context, "Network Crash, Try Again Later", Toast.LENGTH_SHORT).show();
            }
        }

    }
}