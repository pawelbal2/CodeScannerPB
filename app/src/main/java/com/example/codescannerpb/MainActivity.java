package com.example.codescannerpb;

import android.os.Bundle;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;


import com.google.zxing.Result;


import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    private CodeScanner mCodeScanner;
    ListView lstSkany = null;
    ArrayList<String> arrSkany = new ArrayList<>();
    ArrayAdapter arrayAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstSkany = (ListView)findViewById(R.id.lstSkany);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback()
        {
            @Override
            public void onDecoded(@NonNull final Result result)
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        //Toast.makeText(MainActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
                        NaZeskanowanieKodu(result);
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mCodeScanner.startPreview();
            }
        });

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,arrSkany);
        lstSkany.setAdapter(arrayAdapter);
    }

    private void NaZeskanowanieKodu(Result result)
    {
        arrSkany.add( result.getText());
        arrayAdapter.notifyDataSetChanged();
       // mCodeScanner.startPreview();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause()
    {
        mCodeScanner.releaseResources();
        super.onPause();
    }


}