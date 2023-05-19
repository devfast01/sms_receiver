package com.example.sms_receiver;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class stop_AsyncTask extends AppCompatActivity {

    ImageView imageView= null;
    private Button btnDo, btnCancel;
    private TextView textView;
    private AsyncTask<String, Integer, List<String>> myTask;


    /** Called when the activity is first created. */
    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_async_task);
        Button button=findViewById(R.id.asyncTask);
        imageView=findViewById(R.id.image);
        btnDo = findViewById(R.id.btnDo);
        btnCancel = findViewById(R.id.btnCancel);
        textView = findViewById(R.id.textView);

        btnDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("");
                myTask = new DownloadTask().execute("Task1","Task2", "Task3", "Task4", "Task5");
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTask.cancel(true);
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadTask extends android.os.AsyncTask<String, Integer, List<String>> {

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textView.setTextColor(Color.BLUE);
            textView.setText(textView.getText() + "Starting Task....  ");
        }

        @Override
        protected List<String> doInBackground(String... tasks) {
            int count = tasks.length;
            List<String> taskList= new ArrayList<>(count);
            for(int i =0;i<count;i++){
                String currentTask = tasks[i];
                taskList.add(currentTask);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress((int) (((i+1) / (float) count) * 100));
                if(isCancelled()){
                    break;
                }
            }
            return taskList;
        }
        @SuppressLint("SetTextI18n")
        @Override
        protected void onCancelled() {
            super.onCancelled();
            textView.setTextColor(Color.RED);
            textView.setText(textView.getText() + " Operation is cancelled.. ");
        }
        @SuppressLint("SetTextI18n")
        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            textView.setText(textView.getText()+ " Completed:) " + progress[0] + "%");
        }
        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(List<String> result) {
            super.onPostExecute(result);
            textView.setText(textView.getText() + " Done.... ");

            for (int i=0;i<result.size();i++){
                textView.setText(textView.getText() + "" + result.get(i));
            }
        }

    }
}