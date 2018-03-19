package com.example.win7.exinputdata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity
{
    private TextView txtID,txtPW,txtContent;
    private EditText edtID,edtPW,edtContent;
    private Button btnAppend,btnClear,btnEnd;
    private static final String FILENAME = "login.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtID = (EditText)findViewById(R.id.edtID);
        edtPW = (EditText)findViewById(R.id.edtPW);
        edtContent = (EditText)findViewById(R.id.edtContent);

        btnAppend = (Button)findViewById(R.id.btnAppend);
        btnClear = (Button)findViewById(R.id.btnClear);
        btnEnd = (Button)findViewById(R.id.btnEnd);

        btnAppend.setOnClickListener(myListener);
        btnClear.setOnClickListener(myListener);
        btnEnd.setOnClickListener(myListener);

        DisplayFile(FILENAME);

    }

    private  Button.OnClickListener myListener = new Button.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
         switch(v.getId())
         {
             case R.id.btnAppend:   //  加入資料
                 // 檢查帳號與密碼是否都有輸入
                 if(edtID.getText().toString().equals("") || edtPW.getText().toString().equals(""))
                 {
                     Toast.makeText(getApplicationContext(),"帳號及密碼都必須輸入! ",Toast.LENGTH_LONG).show();
                     break;
                 }
                 FileOutputStream fout = null; //   建立寫入資料流
                 BufferedOutputStream buffout = null;

                 try
                 {
                     fout = openFileOutput(FILENAME,MODE_APPEND);
                     buffout = new BufferedOutputStream(fout);
                     // 寫入帳號及密碼
                     buffout.write(edtID.getText().toString().getBytes());
                     buffout.write("\n".getBytes());
                     buffout.write(edtPW.getText().toString().getBytes());
                     buffout.write("\n".getBytes());
                     buffout.close();
                 }
                 catch(Exception e)
                 {
                     e.printStackTrace();
                 }
                 DisplayFile(FILENAME);
                 edtID.setText("");
                 edtPW.setText("");
                 break;
             case R.id.btnClear:
                 try
                 {
                     // 以覆寫方式開啟檔案
                     fout = openFileOutput(FILENAME,MODE_PRIVATE);
                     fout.close();

                 }
                 catch (Exception e)
                 {
                     e.printStackTrace();
                 }
                    DisplayFile(FILENAME);
                    break;
             case R.id.btnEnd:
             {
                 finish();
                 break;
             }
         }
        }
    };

    private void DisplayFile(String fname)
    {
        FileInputStream fin = null ; // 建立讀取資料流
        BufferedInputStream buffin = null;

        try
        {
            fin = openFileInput(fname);
            buffin = new BufferedInputStream(fin);
            byte[] buffbyte = new byte[20];
            edtContent.setText("");

            //  讀取資料，直到檔案結束
            do
            {
                int flag = buffin.read(buffbyte);
                if(flag==-1)
                    break;
                else
                    edtContent.append(new String(buffbyte),0,flag); //顯示資料
            }
            while(true);
            buffin.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
