package kendhia.co.wi_pay.Fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.ArrayList;
import java.util.HashMap;

import kendhia.co.wi_pay.R;
import kendhia.co.wi_pay.Utils.Utils;
import me.ydcool.lib.qrmodule.encoding.QrGenerator;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewBillFragment extends Fragment {

    EditText text;
    ImageView imageview;
    public NewBillFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_bill, container, false);


       /* button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bitmap qrCode = new QrGenerator.Builder()
                            .content(text.getText().toString())
                            .qrSize(300)
                            .margin(2)
                            .color(Color.BLACK)
                            .bgColor(Color.WHITE)
                            .ecc(ErrorCorrectionLevel.H)
                            .overlay(getContext(),R.mipmap.ic_launcher)
                            .overlaySize(100)
                            .overlayAlpha(255)
                            .overlayXfermode(PorterDuff.Mode.SRC_ATOP)
                            .encode();
                    imageview.setImageBitmap(qrCode);

                    String key = FirebaseDatabase.getInstance().getReference().child("bills").push().getKey();
                    Uri uri = Utils.getImageUri(getContext(), qrCode);
                    Utils.saveNewTransition(key, new HashMap<String, String>(), text.getText().toString(),
                            "", uri);

                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }
        });*/
        return rootView;
    }



}
