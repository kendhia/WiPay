package kendhia.co.wi_pay.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kendhia.co.wi_pay.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewBillFragment extends Fragment {

    public NewBillFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_bill, container, false);
    }
}
