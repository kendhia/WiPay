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
public class MarketActivityFragment extends Fragment {

    public MarketActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_market, container, false);
    }
}