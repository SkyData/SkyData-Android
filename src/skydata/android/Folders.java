package skydata.android;

import java.util.ArrayList;

import skydata.core.WebService.DirList;
import skydata.core.WebService.Login;
import skydata.interfaces.Interface;
import android.app.ListActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Folders extends ListActivity{
	
	private Handler handler = new Handler();
	private static NotificationManager mNotificationManager;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.folders);
        getListView().setBackgroundResource(R.raw.skydataback); 
        updateFolders();
        Interface.setHandler(handler);
        Interface.setClass(this);
        Thread dirth = new Thread(new DirList());
    	dirth.start();
        /*
        ListView lv = (ListView) findViewById(R.id.folderList);
        lv.add*/
    	notification();
	}
	
	public void notification() {
		String ns = Context.NOTIFICATION_SERVICE;
        mNotificationManager = (NotificationManager) getSystemService(ns);
        
        int icon = R.drawable.icon;
        CharSequence tickerText = "Skydata is runnig";
        long when = System.currentTimeMillis();

        Notification notification = new Notification(icon, tickerText, when);
        
        Context context = getApplicationContext();
        CharSequence contentTitle = "Skydata";
        CharSequence contentText = "Skydata is runnig";
        Intent notificationIntent = new Intent(this, Folders.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
        notification.flags = notification.flags | Notification.FLAG_ONGOING_EVENT;
        
        mNotificationManager.notify(1, notification);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String item = (String) getListAdapter().getItem(position);
		FolderEdit.folder = item;
		Intent i = new Intent(getApplicationContext(), FolderEdit.class);
		startActivity(i);
	}

	public void updateFolders() {
		ArrayList<ArrayList<String>> a = Main.lc.getFolders();
		String[] folders = new String[a.size()];
		Boolean[] sync = new Boolean[a.size()];
		for(int i = 0; i < a.size(); ++i) {
			folders[i] = a.get(i).get(0);
			sync[i] = false;
			if(a.get(i).get(2).equals("1")) sync[i] = true;
		}
        setListAdapter(new MyArrayAdapter(this, folders,sync));
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(Main.mt != null) Main.mt.finish();
		mNotificationManager.cancelAll();
	}

}
