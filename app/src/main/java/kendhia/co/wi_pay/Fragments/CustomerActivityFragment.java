package kendhia.co.wi_pay.Fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kendhia.co.wi_pay.BillHistory;
import kendhia.co.wi_pay.PayBill;
import kendhia.co.wi_pay.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class CustomerActivityFragment extends Fragment {

    View mRootView;
    AppCompatButton mBtnScan;
    AppCompatButton mBtnOldBills;


    public CustomerActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_customer, container, false);
        mBtnScan = (AppCompatButton) mRootView.findViewById(R.id.btn_scan_bill);
        mBtnOldBills =(AppCompatButton)mRootView.findViewById(R.id.btn_old_bills);

        mBtnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), PayBill.class);
                startActivity(intent);
            }
        });

        mBtnOldBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), BillHistory.class);
                startActivity(intent);
            }
        });

        return mRootView;
    }
}
