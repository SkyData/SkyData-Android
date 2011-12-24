package skydata.android;

import java.util.TreeMap;

import skydata.core.WebService.Login;
import skydata.interfaces.Interface;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FolderEdit extends Activity{
	public static String folder = "";
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	setContentView(R.layout.folder_edit);
        TextView tv = (TextView) findViewById(R.id.folderName);
        tv.setText(folder);
        Button btn = (Button) findViewById(R.id.btnaccept);
        TreeMap<String,TreeMap<String,String>> map = Main.lc.getFoldersMap();
		TreeMap<String,String> fmap = map.get(folder);
		CheckBox chk = (CheckBox) findViewById(R.id.chksync);
		chk.setChecked(fmap.get("sync").equals("1"));
		EditText path = (EditText) findViewById(R.id.txtpath);
		path.setText(fmap.get("path"));
        btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TreeMap<String,TreeMap<String,String>> map = Main.lc.getFoldersMap();
				TreeMap<String,String> fmap = map.get(folder);
				EditText path = (EditText) findViewById(R.id.txtpath);
				fmap.put("path",path.getText().toString());
				CheckBox chk = (CheckBox) findViewById(R.id.chksync);
				String sync = "0";
				if(chk.isChecked()) sync = "1";
				fmap.put("sync",sync);
				map.put(folder, fmap);
				Main.lc.setFoldersMap(map);
				Main.mt.addFolder(path.getText().toString());
				Interface.dirListChanged();
				finish();
			}
        });
	}
}
