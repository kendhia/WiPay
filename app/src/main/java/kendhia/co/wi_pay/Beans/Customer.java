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
public class Customer {
    public String mUser;
    public Integer mAccountNum;
    public ArrayList<String> mKeyOfTransitions;
    public String mBalance;
    public String mCustomerId;

    public Customer() {

    }

    public Customer(String name, Integer accountNum)  {
        mUser = name;
        mAccountNum = accountNum;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("customerid", mCustomerId);
        result.put("customername", mUser);
        result.put("account_num", mAccountNum);
        result.put("old_transitions", mKeyOfTransitions);
        result.put("balance", mBalance);
        return result;
    }


}
