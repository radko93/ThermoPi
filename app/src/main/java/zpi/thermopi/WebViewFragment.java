package zpi.thermopi;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class WebViewFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    private int page;
    private WebView webView;
    private SwipeRefreshLayout swipeLayout;
    private int[] devices;

    public static WebViewFragment newInstance(int page, int[] devices) {
        WebViewFragment fragmentFirst = new WebViewFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putIntArray("devices",devices);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        devices = getArguments().getIntArray("devices");


    }


    @Override public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {

                swipeLayout.setRefreshing(false);
            }
        }, 900);
        webView.loadUrl("javascript:refresh()");
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);
        webView = (WebView) view.findViewById(R.id.web);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);



        switch (page)
        {

            case 0:
                webView.loadUrl("http://thermopi.azurewebsites.net/PieChartMobile.html?temp=" + devices[0] + "&hum=" + devices[1] + "&light=" + devices[2]);
                break;
            case 1:
                webView.loadUrl("http://thermopi.azurewebsites.net/LineChartMobile.html?temp=" + devices[0] + "&hum=" + devices[1] + "&light=" + devices[2]);
                break;

        }


        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        return view;
    }
}