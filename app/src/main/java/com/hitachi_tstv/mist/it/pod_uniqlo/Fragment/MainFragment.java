package com.hitachi_tstv.mist.it.pod_uniqlo.Fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hitachi_tstv.mist.it.pod_uniqlo.Constant;
import com.hitachi_tstv.mist.it.pod_uniqlo.R;

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


    @BindView(R.id.usernameEditText)
    EditText usernameEditText;
    @BindView(R.id.passwordEditText)
    EditText passwordEditText;
    @BindView(R.id.login_btn)
    Button loginBtn;
    Unbinder unbinder;

    private String usernameString, passwordString;
    private String[] loginStrings;
    String TAG = MainFragment.class.getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
                        .add("isTrue", "true")
                        .add("username", usernameString)
                        .add("password", passwordString)
                        .build();
                Request request = new Request.Builder().url(Constant.urlGetUserLogin).post(requestBody).build();
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
            try {
                Log.d(TAG, "onPostExecute: " + s);
                if (!(s == null)) {
                    if (!s.equals("false")) {
                        JSONObject jsonObject = new JSONObject(s);
                        loginStrings = new String[Constant.getColumnLoginSize];
                        for (int i =0;i < Constant.getColumnLoginSize;i++) {
                            loginStrings[i] = jsonObject.getString(Constant.getColumnLogin[i]);
                        }

                        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        //Send Arguments
                        ListJobFragment listJobFragment = new ListJobFragment();
                        Bundle args = new Bundle();
                        args.putStringArray("Login",loginStrings);
                        listJobFragment.setArguments(args);

                        fragmentTransaction.replace(R.id.contentFragment, listJobFragment);
                        fragmentTransaction.commit();
                    }
                }else {
                    Toast.makeText(context, "Network Crash, Try Again Later", Toast.LENGTH_SHORT).show();
                }


            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }
}
