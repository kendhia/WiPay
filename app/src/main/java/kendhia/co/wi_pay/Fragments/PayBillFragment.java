package kendhia.co.wi_pay.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kendhia.co.wi_pay.Beans.Bill;
import kendhia.co.wi_pay.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PayBillFragment extends Fragment {

    static ItemsList mItemsList;
    RecyclerView mRecyclerView;
    public PayBillFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mRootView = inflater.inflate(R.layout.fragment_pay_bill, container, false);

        mItemsList = new ItemsList();
        mRecyclerView = (RecyclerView)mRootView.findViewById(R.id.items_list);
        mRecyclerView.setAdapter(mItemsList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        return  mRootView;
    }



    public static void bindData(final Context context, String key) {

        Toast.makeText(context, key, Toast.LENGTH_SHORT).show();
        FirebaseDatabase.getInstance().getReference()
                .child("bills").child(key)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Bill bill = dataSnapshot.getValue(Bill.class);
                if (bill != null || bill.mItems != null) {
                    ArrayList<String> names = bill.mItems;
                    for (int pos = 0; pos < names.size(); pos++) {
                        mItemsList.addItem(bill.mItems.get(pos), bill.mprices.get(pos));
                        mItemsList.notifyItemInserted(mItemsList.getItemCount()-1);
                        Log.e("ss", bill.mItems.get(pos));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    class ItemsList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        ArrayList<String> names;
        ArrayList<String> prices;

        public ItemsList() {
            names = new ArrayList<>();
            prices = new ArrayList<>();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BillViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (names.get(position) != null) {
                BillViewHolder rootView = (BillViewHolder)holder;
                rootView.mName.setText(names.get(position));
                rootView.mPrice.setText(prices.get(position));

            }
        }

        @Override
        public int getItemCount() {
            return names.size();
        }

        public void addItem(String name, String price) {
            names.add(name);
            prices.add(price);
        }


        class BillViewHolder extends RecyclerView.ViewHolder {
            TextView mName;
            TextView mPrice;

            public BillViewHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_to_pay, parent, false));
                mName = (TextView) itemView.findViewById(R.id.item_name);
                mPrice = (TextView) itemView.findViewById(R.id.item_price);

            }
        }
    }


}
