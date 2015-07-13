package zpi.thermopi;

import android.accounts.AccountManager;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;


public class AuthenticationActivity extends AccountAuthenticatorActivity {

    public static final String PARAM_AUTHTOKEN_TYPE = "auth.token";
    public static final String PARAM_CREATE = "create";

    public static final int REQ_CODE_CREATE = 1;

    public static final int REQ_CODE_UPDATE = 2;

    public static final String EXTRA_REQUEST_CODE = "req.code";

    public static final int RESP_CODE_SUCCESS = 0;

    public static final int RESP_CODE_ERROR = 1;

    public static final int RESP_CODE_CANCEL = 2;

    @Override
    protected void onCreate(Bundle icicle) {
        // TODO Auto-generated method stub
        super.onCreate(icicle);
        this.setContentView(R.layout.user_credential);
    }

    public void onCancelClick(View v) {

        this.finish();

    }

    public void onSaveClick(View v) {
        TextView tvUsername;
        TextView tvPassword;

        String username;
        String password;

        boolean hasErrors = false;

        tvUsername = (TextView) this.findViewById(R.id.uc_txt_username);
        tvPassword = (TextView) this.findViewById(R.id.uc_txt_password);

        tvUsername.setBackgroundColor(Color.WHITE);
        tvPassword.setBackgroundColor(Color.WHITE);


        username = tvUsername.getText().toString();
        password = tvPassword.getText().toString();


        if (username.length() < 3) {
            hasErrors = true;
            tvUsername.setBackgroundColor(Color.RED);
        }
        if (password.length() < 3) {
            hasErrors = true;
            tvPassword.setBackgroundColor(Color.RED);
        }

        if (hasErrors) {
            return;
        }


        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("username",username));
        JSONParser jsonParser=new JSONParser();

        String result = jsonParser.makeServiceCall("http://thermowebapi.azurewebsites.net/Api/Users", 1, params);
        String Id = result.substring(1, result.length() - 1);
        Log.d("userid",Id);
        if(Id.length()>79)
            hasErrors=true;



        // finished

        String accountType = this.getIntent().getStringExtra(PARAM_AUTHTOKEN_TYPE);
        if (accountType == null) {
            accountType = "zpi.thermopi";
        }

        AccountManager accMgr = AccountManager.get(this);

        if (hasErrors) {

            Log.d("loginerror", "some error at login"+Id);

        } else {

            // This is the magic that adds the account to the Android Account Manager
            final Account account = new Account(username, accountType);
            accMgr.addAccountExplicitly(account, password, null);

            // Now we tell our caller, could be the Android Account Manager or even our own application
            // that the process was successful

            final Intent intent = new Intent();
            intent.putExtra(AccountManager.KEY_ACCOUNT_NAME, username);
            intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType);
            intent.putExtra(AccountManager.KEY_AUTHTOKEN, accountType);
            this.setAccountAuthenticatorResult(intent.getExtras());
            this.setResult(RESULT_OK, intent);
            this.finish();
            Intent mainIntent = new Intent(this,MainActivity.class);
            Context context = getApplicationContext();
            SharedPreferences sharedPref = context.getSharedPreferences(
                    getString(R.string.prefs_file_name), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("username",Id);
            editor.putBoolean("isAuthenticated",true);
            editor.commit();

            finishActivity(0);
        }

    }


}

