package zpi.thermopi;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class WebViewFragment extends Fragment {


    private int page;
    private WebView webView;

    public static WebViewFragment newInstance(int page) {
        WebViewFragment fragmentFirst = new WebViewFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
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
               webView.loadUrl("http://thermopi.azurewebsites.net/PieChartMobile.html");
               break;
           case 1:
               webView.loadUrl("http://thermopi.azurewebsites.net/LineChartMobile.html");

       }


        return view;
    }
}