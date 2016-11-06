package kendhia.co.wi_pay;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import kendhia.co.wi_pay.Fragments.PayBillFragment;
import me.ydcool.lib.qrmodule.activity.QrScannerActivity;

public class PayBill extends AppCompatActivity {
    final String TAG = "PayBill";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_bill);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = new Intent(this, QrScannerActivity.class);
        startActivityForResult(intent, QrScannerActivity.QR_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QrScannerActivity.QR_REQUEST_CODE) {
            String key =data.getExtras().getString(QrScannerActivity.QR_RESULT_STR);
            Log.d(TAG, resultCode == RESULT_OK
                    ? key
                    : "Scanned Nothing!");
            PayBillFragment.bindData(getApplicationContext(), key);
        }
    }

}
