package zpi.thermopi;



import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AuthenticationService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return new AccountAuthenticator(this).getIBinder();
    }

}


