package kendhia.co.wi_pay;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kendhia.co.wi_pay.Beans.Bill;
import kendhia.co.wi_pay.Fragments.PayBillFragment;
import kendhia.co.wi_pay.Utils.Utils;
import me.ydcool.lib.qrmodule.activity.QrScannerActivity;
import me.ydcool.lib.qrmodule.encoding.QrGenerator;

public class NewBill extends AppCompatActivity {

    ItemsList mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bill);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mAdapter = new ItemsList();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.bills_list);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(layoutManager);


        AppCompatButton btn_submit = (AppCompatButton)findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int total = 0;
                for (String price : mAdapter.getPrices()) {
                    total += Integer.valueOf(price);
                }
                Bill bill = new Bill(mAdapter.getItems(), mAdapter.getPrices(), "s", String.valueOf(total));
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                String key = mDatabase.child("bills").push().getKey();
                Map<String, Object> postValues = bill.toMap();
                Map<String, Object> childUpdates = new HashMap<>();
                Bitmap qrCode = null;
                try {
                    qrCode = new QrGenerator.Builder()
                            .content(key)
                            .qrSize(300)
                            .margin(2)
                            .color(Color.BLACK)
                            .bgColor(Color.WHITE)
                            .ecc(ErrorCorrectionLevel.H)
                            .overlay(getApplicationContext(), R.mipmap.ic_launcher)
                            .overlaySize(100)
                            .overlayAlpha(255)
                            .overlayXfermode(PorterDuff.Mode.SRC_ATOP)
                            .encode();
                } catch (WriterException e) {
                    e.printStackTrace();
                }

                FirebaseStorage.getInstance().getReference()
                        .child("transitions").child(key)
                        .putFile(Utils.getImageUri(getApplicationContext(), qrCode));
                childUpdates.put("/bills/" + key, postValues);
                mDatabase.updateChildren(childUpdates);
                finish();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewBill.this, QrScannerActivity.class);
                startActivityForResult(intent, QrScannerActivity.QR_REQUEST_CODE);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QrScannerActivity.QR_REQUEST_CODE) {
            mAdapter.addItem(data.getExtras().getString(QrScannerActivity.QR_RESULT_STR)
                    , String.valueOf((int )(Math. random() * 50 + 1)));
            mAdapter.notifyItemInserted(mAdapter.items.size()-1);
        }
    }

    class ItemsList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        ArrayList<String> items;
        ArrayList<String> prices;

        public ItemsList() {
            items = new ArrayList<>();
            prices = new ArrayList<>();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BillViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (items.get(position) != null) {
                final String item = items.get(position);
                BillViewHolder rootView = (BillViewHolder) holder;
                rootView.mBarcode.setText(item);
                rootView.mPrice.setText(prices.get(position));
                Log.e("item", "item");

            }
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void addItem(String orderNum, String key) {
            items.add(orderNum);
            prices.add(key);
        }

        public ArrayList<String> getItems() {
            return items;
        }

        public ArrayList<String> getPrices() {
            return prices;
        }


        class BillViewHolder extends RecyclerView.ViewHolder {
            TextView mBarcode;
            TextView mPrice;

            public BillViewHolder(LayoutInflater inflater, ViewGroup parent) {
                super(inflater.inflate(R.layout.item_bill_new, parent, false));
                mBarcode = (TextView) itemView.findViewById(R.id.item_name);
                mPrice = (TextView) itemView.findViewById(R.id.item_price);

            }
        }
    }


}
