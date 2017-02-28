package chema.egea.canales.AccesoSIM;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    private TelephonyManager mTelephonyMgr;
    private final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (comprobarPermisosSMS())
        {
            MostrarInfoTelefono();
        }
    }

    private void MostrarInfoTelefono()
    {
        mTelephonyMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        String miICCID = mTelephonyMgr.getSimSerialNumber(); //Numero de serie de la SIM
        String suscripcionID = mTelephonyMgr.getSubscriberId(); //Devuelve el IMSI
        String nombreOperadora = mTelephonyMgr.getSimOperatorName(); //Nombre de la operadora de telefono
        String operadorInternet = mTelephonyMgr.getNetworkOperatorName(); //Nombre de la operadora de internet
        String tipoRed = "" + mTelephonyMgr.getNetworkType(); //devuelve tipo conexion red
        String miImei = mTelephonyMgr.getDeviceId(); //devuelve el imei del telefono


        String texto = String.format("Información de la SIM del teléfono.\n\nNumero de serie de la SIM (ICCID): \n%s\nIMSI del teléfono: \n%s\nOperadora de telefonía: \n%s\nOperadora de internet: \n%s\nTipo de red de la SIM: \n%s\nIMEI del teléfono: \n%s", miICCID, suscripcionID, nombreOperadora, operadorInternet, tipoRed, miImei);

        TextView textoinfo = (TextView) findViewById(R.id.infoTel);

        textoinfo.setText(texto);
    }

    private boolean comprobarPermisosSMS()
    {
        String permission = Manifest.permission.READ_PHONE_STATE;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        // Here, thisActivity is the current activity
        if ( grant != PackageManager.PERMISSION_GRANTED)
        {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
        else
        {
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    MostrarInfoTelefono();
                }
                else
                {
                    Toast.makeText(this, "NECESITAS CONCEDER PERMISOS PARA VER DATOS DEL TELÉFONO, CIERRA LA APP Y VUELVE A INTENTARLO", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
