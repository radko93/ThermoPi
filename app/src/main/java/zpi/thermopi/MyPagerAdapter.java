package zpi.thermopi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private final static int NUM_ITEMS = 2;
    private int terarriumId;
    private int[] inputDevices;
    private int[] outputDevices;

    public MyPagerAdapter(FragmentManager fragmentManager, MainActivity mainActivity, int terarriumId) {
        super(fragmentManager);
        this.setTerarriumId(terarriumId);
        getDevices();
    }


    @Override
    public int getCount() {
        return NUM_ITEMS;
    }


    @Override
    public Fragment getItem(int position) {

        return position == 0 ? zpi.thermopi.WebViewFragment.newInstance(position,outputDevices) : zpi.thermopi.WebViewFragment.newInstance(position,inputDevices);

    }

    private void getDevices()
    {
        inputDevices = new int[3];
        outputDevices = new int[3];
        JSONParser jsonParser=new JSONParser();
        String jsonStr=jsonParser.makeServiceCall("http://thermowebapi.azurewebsites.net/Api/TerrariumsDevices/"+terarriumId,1);
        int inputCounter = 0;
        int outputCounter = 0;

        if (jsonStr != null) {
            try {
                JSONObject terarrium=new JSONObject(jsonStr);
                JSONArray devicesJson= terarrium.getJSONArray("Devices");
                for(int i=0;i<devicesJson.length();i++)
                {
                    JSONObject c = devicesJson.getJSONObject(i);

                    if(c.getBoolean("IsOutput"))
                        outputDevices[outputCounter++] = c.getInt("Id");
                    else
                        inputDevices[inputCounter++] = c.getInt("Id");

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void setTerarriumId(int terarriumId) {
        this.terarriumId = terarriumId;
        getDevices();
    }
}
