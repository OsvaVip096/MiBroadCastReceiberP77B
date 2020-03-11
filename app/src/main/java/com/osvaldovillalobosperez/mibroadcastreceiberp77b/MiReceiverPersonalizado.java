package com.osvaldovillalobosperez.mibroadcastreceiberp77b;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class MiReceiverPersonalizado extends BroadcastReceiver {

    private static final String TAG = "MyBroadcastReceiver";

    /**
     * @param context La aplicación a la que se va a enviar.
     * @param intent  Recibe información de la difusión. Tiene el action.
     */
    @Override
    public void onReceive(final Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving

        final String textoSMS = MainActivity.intent.getStringExtra("responseText");
        final String textoTel = MainActivity.intent.getStringExtra("responseTel");

        //Toast.makeText(context, "El SMS: " + textoSMS + " - El Tel: " + textoTel, Toast.LENGTH_LONG).show();

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(
                Service.TELEPHONY_SERVICE
        );

        telephonyManager.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String phoneNumber) {
                super.onCallStateChanged(state, phoneNumber);

                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    if (phoneNumber.equals("+52" + textoTel)) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(textoTel, null, textoSMS, null, null);
                        Toast.makeText(context, "Mensaje SMS envíado correctamente.", Toast.LENGTH_LONG).show();
                    } else if (phoneNumber.equals(textoTel)) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(textoTel, null, textoSMS, null, null);
                        Toast.makeText(context, "Mensaje SMS envíado correctamente.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "El número ingresado en la caja de texto: " +
                                textoTel + "\nes diferente del número entrante: " +
                                phoneNumber, Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }
}
