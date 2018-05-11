package com.example.jww193.smartvaultapp2;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.content.Context.WIFI_SERVICE;

public class WiFiSettingsTab extends Fragment {
    private static final String TAG = "WiFiFragment";
    private static final int MY_PERMISSIONS_REQUEST_CHANGE_WIFI_STATE = 123;

    TextView varTxt;
    Button varBtn;
    WifiManager mWifiManager;
    List<ScanResult> wifiList;
    StringBuilder sb = new StringBuilder();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wifisettings_frag, container, false);
        varTxt = (TextView) view.findViewById(R.id.idTxt);
        varBtn = (Button) view.findViewById(R.id.idBtn);

        varBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_CHANGE_WIFI_STATE);
                } else {
                    Toast.makeText(getContext(), "Method called", Toast.LENGTH_SHORT).show();
                    MyWifiMethod();
                }
            }
        });

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CHANGE_WIFI_STATE: {
                if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    MyWifiMethod();
                } else {
                    Toast.makeText(getContext(), "You don't have permission to make this action", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void MyWifiMethod() {
        mWifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(WIFI_SERVICE);
        getActivity().registerReceiver(mWifiScanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        mWifiManager.startScan();
    }

    private final BroadcastReceiver mWifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                wifiList = mWifiManager.getScanResults();
                sb = new StringBuilder();
                sb.append("\n" + "Number of WiFi connections: " + wifiList.size() + "\n");
                for (int i = 0; i < wifiList.size(); i++) {
                    sb.append(new Integer(i + 1).toString() + ". ");
                    sb.append("SSID: "+(wifiList.get(i).SSID).toString() +"\n");
                    sb.append("BSSID: "+(wifiList.get(i).BSSID).toString() +"\n");
                    sb.append("capabilities: "+(wifiList.get(i).capabilities).toString() +"\n");
                    sb.append("level: "+(wifiList.get(i).level) +"\n");
                    sb.append("frequency: "+(wifiList.get(i).frequency) +"\n");
                    sb.append("describeContents: "+(wifiList.get(i).describeContents()) +"\n");
                    sb.append("channelWidth: "+(wifiList.get(i).channelWidth) +"\n");
                    sb.append("\n\n");
                }
                varTxt.setText(sb);
            }
        }
    };
}