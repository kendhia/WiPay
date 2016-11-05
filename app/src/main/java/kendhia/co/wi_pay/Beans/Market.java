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
public class Market {
    public String mName;
    public Integer mAccountNum;
    public String mBalance;
    public ArrayList<String> mTransitions;
    public String mMarketId;

    public Market() {

    }

    public Market(String marketName, Integer accountNum) {
        mName = marketName;
        mAccountNum = accountNum;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("mid", mMarketId);
        result.put("marketname", mName);
        result.put("account_num", mAccountNum);
        result.put("old_transitions", mTransitions);
        result.put("balance", mBalance);
        return result;
    }
}
