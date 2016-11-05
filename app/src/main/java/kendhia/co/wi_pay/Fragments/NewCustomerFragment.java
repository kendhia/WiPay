package kendhia.co.wi_pay.Fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import kendhia.co.wi_pay.R;
import kendhia.co.wi_pay.Utils.Utils;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewCustomerFragment extends Fragment {
    EditText mName;
    EditText mAccount;
    Button mSubmit;
    View mRootView;

    public NewCustomerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_new_customer, container, false);

        mName = (EditText) mRootView.findViewById(R.id.input_customer_name);
        mAccount = (EditText) mRootView.findViewById(R.id.input_iban_num);
        mSubmit = (Button) mRootView.findViewById(R.id.btn_submit);

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vt = mAccount.getText().toString();
                Integer t = Integer.valueOf(vt);
                Utils.saveNewCustomer(mName.getText().toString(), t);

            }
        });
        return mRootView;
    }


}
