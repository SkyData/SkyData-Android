package skydata.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;
	private final Boolean[] sync;

	public MyArrayAdapter(Context context, String[] values, Boolean[] sync) {
		super(context, R.layout.list_row, values);
		this.context = context;
		this.values = values;
		this.sync = sync;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.list_row, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.syncicon);
		textView.setText(values[position]);
		// Change the icon for Windows and iPhone
		String s = values[position];
		if (sync[position]) {
			imageView.setImageResource(R.drawable.sync);
		}

		return rowView;
	}
}
