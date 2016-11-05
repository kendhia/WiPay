package kendhia.co.wi_pay.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.Inflater;

import kendhia.co.wi_pay.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class MarketActivityFragment extends Fragment {

    View mRootView;
    RecyclerView mRecyclerView;


    public MarketActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_market, container, false);
        mRecyclerView = (RecyclerView)mRootView.findViewById(R.id.transitions_list);

        final BillList billList = new BillList();

        FirebaseDatabase.getInstance().getReference()
                .child("transitions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                int i = 0;
                while (iterator.hasNext()) {
                    i++;
                    DataSnapshot data = iterator.next();
                    String order = "Order Num : " + i;
                    billList.addItem(data.getKey(), order);
                    billList.notifyItemInserted(i-1);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return mRootView;
    }

    class BillList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        ArrayList<String> bills;
        ArrayList<String> billsKey;

        public BillList() {
            bills = new ArrayList<>();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BillViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (bills.get(position) != null) {
                final String bill = bills.get(position);
                BillViewHolder rootView = (BillViewHolder) holder;
                rootView.mBillInfo.setText(bill);
                rootView.mCancelView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase.getInstance().getReference().child("transitions").child(bill).removeValue();
                        bills.remove(position);
                        billsKey.remove(position);
                        notifyItemRemoved(position);
                    }
                });


            }
        }

        @Override
        public int getItemCount() {
            return bills.size();
        }

        public void addItem(String key, String orderNum) {
            bills.add(orderNum);
            billsKey.add(key);
        }


        class BillViewHolder extends RecyclerView.ViewHolder {
            TextView mBillInfo;
            ImageView mCancelView;

            public BillViewHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_bill, parent, false));
                mBillInfo = (TextView) itemView.findViewById(R.id.bill_info);
                mCancelView = (ImageView) itemView.findViewById(R.id.bill_cancel);

            }
        }
    }
}
