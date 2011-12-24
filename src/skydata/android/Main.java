package skydata.android;

import skydata.core.FileScanner;
import skydata.core.LocalConf;
import skydata.core.MainThread;
import skydata.core.FolderTree.TreeFunctions;
import skydata.core.WebService.Login;
import skydata.core.WebService.TreeService;
import skydata.interfaces.Interface;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Main extends Activity {
	
	public static LocalConf lc;
	private Handler handler = new Handler();
	private ProgressBar pb;
	public static Thread mainth;
	public static MainThread mt;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLogin();
        if (!android.os.Environment.getExternalStorageState().equals( 
        		android.os.Environment.MEDIA_MOUNTED)) return;
        Interface.setHandler(handler);
        Interface.setClass(this);
        Interface.setContext(this.getBaseContext());
        lc = new LocalConf("/sdcard/skydata.conf");
        if(!lc.getUser().equals("")) {
        	EditText usr = (EditText) findViewById(R.id.usrtxt);
			EditText pass = (EditText) findViewById(R.id.passtxt);
			usr.setText(lc.getUser());
			pass.setText(lc.getPass());
			
        	pb.setVisibility(View.VISIBLE);
        	Thread lt = new Thread(new Login(lc.getUser(),lc.getPass()));
        	lt.start();
        	//handler.post(new Login(lc.getUser(),lc.getPass()));
        }
        
        /*
        TreeFunctions tf = new TreeFunctions(new TreeService(""));
        tf.printTree("/sdcard/", FileScanner.scan("/sdcard/"));
        */
    }
    
    public void logIn() {
    	EditText usr = (EditText) findViewById(R.id.usrtxt);
    	pb.setVisibility(View.INVISIBLE);
    	if(Login.token == null) {
    		Toast.makeText(this, "Wrong username or Password", 3).show();
    	}
    	else {
    		mt = new MainThread(lc);
    		mainth = new Thread(mt);
    		mainth.start();
    		Intent i = new Intent(getApplicationContext(), Folders.class);
    		startActivity(i);
    	}
    }
    @Override
    public void onResume() {
    	super.onResume();
    	if(mt != null) mt.finish();
    }
    
    @Override
	public void onDestroy() {
		super.onDestroy();
		if(Main.mt != null) Main.mt.finish();
	}
        
    public void showLogin() {
    	setContentView(R.layout.login);
    	pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setVisibility(View.INVISIBLE);
        Button btn = (Button) findViewById(R.id.loginbtn);
        final Object main = this;
        btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar1);
				pb.setVisibility(View.VISIBLE);
				EditText usr = (EditText) findViewById(R.id.usrtxt);
				EditText pass = (EditText) findViewById(R.id.passtxt);
				lc.setUser(usr.getText().toString());
				lc.setPass(pass.getText().toString());
		        Interface.setHandler(handler);
		        Interface.setClass(main);
				Thread lt = new Thread(new Login(usr.getText().toString(),usr.getText().toString()));
	        	lt.start();
	        	//handler.post(new Login(usr.getText().toString(),pass.getText().toString()));
			}
        });
    }
    
}