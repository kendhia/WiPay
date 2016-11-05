package kendhia.co.wi_pay.Beans;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 11/5/2016.
 */

@IgnoreExtraProperties
public class Bill {

    public HashMap<String, String> mItems;
    public String mMarketId;
    public String mCustomerId;
    public String mTotal;
    public Boolean mPaid;

     public Bill() {

     }

    public Bill(HashMap<String, String> items, String marketid, String total) {
        mMarketId = marketid;
        mItems = items;
        mTotal = total;
        mPaid = false;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("marketid", mMarketId);
        result.put("customerid", mCustomerId);
        result.put("total", mTotal);
        result.put("paid", mPaid);
        return result;
    }
}
