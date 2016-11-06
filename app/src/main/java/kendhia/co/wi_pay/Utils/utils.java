package kendhia.co.wi_pay.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import kendhia.co.wi_pay.Beans.Customer;
import kendhia.co.wi_pay.Beans.Market;
import kendhia.co.wi_pay.Beans.Bill;

/**
 * Created by Administrator on 11/5/2016.
 */

public class Utils {
    static DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    static StorageReference mStorageReference = FirebaseStorage.getInstance().getReference();

    public static void saveNewCustomer(String name, Integer num) {
        Customer customer = new Customer(name, num);
        mDatabaseReference.child("customers").child(name).setValue(customer);
    }

    public static void saveNewMarket(String name, Integer num) {
        Market market = new Market(name, num);
        mDatabaseReference.child("markets").child(name).setValue(market);
    }

 /*   public static void saveNewTransition(String key, HashMap<String, String> items, String market, String total, Uri qrCode) {
        Bill bill = new Bill(items, market, total);
        mDatabaseReference.child("transitions").child(key).setValue(bill);
        mStorageReference.child("transitions").child(key).putFile(qrCode);
    }
*/
    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


}
