package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class StartActivity extends AppCompatActivity {
    private ImageView iv_00;
    private ProgressBar prb_00;
    private TextView tv_00;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity_layout);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }

        iv_00 = findViewById(R.id.iv_00);
        prb_00 = findViewById(R.id.prb_00);
        tv_00 = findViewById(R.id.tv_00);

        iv_00.setBackgroundResource(R.drawable.start);

        hideBottomUIMenu();

        final MyAsyncTask Task = new MyAsyncTask(prb_00,StartActivity.this);
        Task.execute(1000);

        /*AlphaAnimation aa = new AlphaAnimation(1.0f,1.0f);
        aa.setDuration(5000);
        iv_00.startAnimation(aa);
        aa.setAnimationListener(new AaImpl());*/

        prb_00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skip();
                Task.cancel(true);
            }
        });



    }

    private class AaImpl implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {
           iv_00.setBackgroundResource(R.drawable.start);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            skip();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    private class MyAsyncTask extends AsyncTask<Integer,Integer,String> {

        private ProgressBar prb;
        Context mContext;

        public MyAsyncTask(ProgressBar prb, Context mContext) {
            super();
            this.prb = prb;
            this.mContext = mContext;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            skip();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            DelayOperator delayOperator = new DelayOperator();
            int i;
            for (i = 1;i <= 5;i++) {
                delayOperator.delay();
                prb.setProgress(i,true);
            }
            return  i +  integers[0].intValue() + "";
        }
    }

    class DelayOperator {
        //延时操作,用来模拟下载
        public void delay()
        {
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();;
            }
        }
    }


    private  void skip(){
        Intent intent = new Intent(StartActivity.this,MainActivity.class);
        startActivity(intent);
    }

    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
