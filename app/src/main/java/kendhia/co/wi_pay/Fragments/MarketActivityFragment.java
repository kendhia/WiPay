package kendhia.co.wi_pay.Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.Inflater;

import kendhia.co.wi_pay.Beans.Bill;
import kendhia.co.wi_pay.MarketActivity;
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        final BillList billList = new BillList();
        mRecyclerView.setAdapter(billList);
        mRecyclerView.setLayoutManager(layoutManager);

        FirebaseDatabase.getInstance().getReference()
                .child("bills").addValueEventListener(new ValueEventListener() {
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
            billsKey = new ArrayList<>();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BillViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (bills.get(position) != null) {
                final String bill = bills.get(position);
                final BillViewHolder rootView = (BillViewHolder) holder;
                rootView.mBillInfo.setText(bill);
                FirebaseDatabase.getInstance().getReference()
                        .child("bills").child(billsKey.get(position)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Bill bill1 = dataSnapshot.getValue(Bill.class);
                        if (bill1.mPaid) {
                            Picasso.with(getContext()).load(R.drawable.comment_check).into(rootView.mCancelView);
                        } else {
                            Picasso.with(getContext()).load(R.drawable.comment_processing_outline).into(rootView.mCancelView);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                rootView.mBillInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.bill_qer_code);
                        ImageButton close = (ImageButton) dialog.findViewById(R.id.btnClose);
                        final ImageView qr_code = (ImageView) dialog.findViewById(R.id.qr_code_img);
                        StorageReference storageRef = FirebaseStorage.getInstance()
                                .getReference().child("transitions").child(billsKey.get(position));

                         storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                             @Override
                             public void onSuccess(Uri uri) {
                                 Picasso.with(getContext()).load(uri).into(qr_code);
                                 Log.e("sss", uri.toString());
                             }
                         });

                         close.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 dialog.dismiss();
                             }
                         });

                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        dialog.show();
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
