package com.osvaldovillalobosperez.mibroadcastreceiberp77b;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving

        String textoSMS = MainActivity.intent.getStringExtra("responseText");
        String textoTel = MainActivity.intent.getStringExtra("responseTel");

        //Toast.makeText(context, "El SMS: " + textoSMS + " - El Tel: " + textoTel, Toast.LENGTH_LONG).show();

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
        int estado = telephonyManager.getCallState();
        String numeroLlamada = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

        if (estado == TelephonyManager.CALL_STATE_OFFHOOK) {
            //Toast.makeText(context, "Llamada activa", Toast.LENGTH_LONG).show();
        }

        if (estado == TelephonyManager.CALL_STATE_IDLE) {
            //Toast.makeText(context, "Llamada cancelada", Toast.LENGTH_LONG).show();
        }

        if (estado == TelephonyManager.CALL_STATE_RINGING) {
            //Toast.makeText(context, "Teléfono sonando", Toast.LENGTH_LONG).show();
            //Toast.makeText(context, "El número de teléfono es: " + numeroLlamada, Toast.LENGTH_LONG).show();
            if (numeroLlamada.equals("+52" + textoTel)) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(textoTel, null, textoSMS, null, null);
                Toast.makeText(context, "Mensaje SMS envíado correctamente.", Toast.LENGTH_LONG).show();
            } else if (numeroLlamada.equals(textoTel)) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(textoTel, null, textoSMS, null, null);
                Toast.makeText(context, "Mensaje SMS envíado correctamente.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "El número ingresado en la caja de texto: " +
                        textoTel + "\nes diferente del número entrante: " +
                        numeroLlamada, Toast.LENGTH_LONG).show();
            }
        }
    }
}
