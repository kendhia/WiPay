package kendhia.co.wi_pay.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import kendhia.co.wi_pay.R;
import me.ydcool.lib.qrmodule.activity.QrScannerActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class PayBillFragment extends Fragment {

    String key;
    View mRootView;
    public PayBillFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_pay_bill, container, false);

        return  mRootView;
    }

    public static void bindData(final Context context, String key) {

        Toast.makeText(context, key, Toast.LENGTH_SHORT).show();

        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("transitions").child(key)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(context, "sqfqdf", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
