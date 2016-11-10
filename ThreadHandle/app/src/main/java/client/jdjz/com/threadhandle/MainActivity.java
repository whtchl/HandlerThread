package client.jdjz.com.threadhandle;

import android.app.Activity;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    public Handler mSubHandler;
    HandlerThread workHandle;
    private static final String  TAG = "MainActivity";

    public Handler mUIhandler = new Handler(){

        @Override
        public void handleMessage(Message er){
            switch (er.what) {
                case 2:
                    Log.d(TAG, "mUIHandle  thread id:" + Thread.currentThread().getId());
                    break;
                default:
                    break;
            }
        }
    };



    private Handler.Callback mSubCallback = new Handler.Callback(){

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    Log.d(TAG,"get message:"+ msg.what);
                    break;
                case 1:
                    Log.d(TAG,"get Message:"+msg.what);
                    mUIhandler.sendEmptyMessage(2);
                default:
                    break;
            }
            Log.d(TAG,"mSubCallback, thread id= "+Thread.currentThread().getId()+" Thread Name:"+Thread.currentThread().getName());
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG,"thread main id:"+Thread.currentThread().getId()+"  Thread Name:"+Thread.currentThread().getName());
        workHandle = new HandlerThread("handlerThread");
        workHandle.start();
        mSubHandler = new Handler(workHandle.getLooper(),mSubCallback);
        Button btn = (Button) findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mSubHandler.sendEmptyMessage(0);
            }
        });

        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mSubHandler.post(HandleThreadRun);
            }
        });
    }

    Runnable HandleThreadRun = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG,"thread HandleThreadRun id:"+Thread.currentThread().getId()+"  Thread Name:"+Thread.currentThread().getName());
            mSubHandler.sendEmptyMessage(1);
        }
    };
}
