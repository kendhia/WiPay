package kendhia.co.wi_pay.Beans;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 11/5/2016.
 */

@IgnoreExtraProperties
public class Bill {

    public ArrayList<String> mItems;
    public ArrayList<String> mprices;
    public String mMarketId;
    public String mCustomerId;
    public String mTotal;
    public Boolean mPaid;

     public Bill() {

     }

    public Bill(ArrayList<String> items, ArrayList<String> prices, String marketid, String total) {
        mMarketId = marketid;
        mItems = items;
        mprices = prices;
        mTotal = total;
        mPaid = false;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("mMarketId", mMarketId);
        result.put("mCustomerId", mCustomerId);
        result.put("mTotal", mTotal);
        result.put("mPaid", mPaid);
        result.put("mItems", mItems);
        result.put("mprices", mprices);
        return result;
    }
}
