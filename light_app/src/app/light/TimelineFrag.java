package app.light;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class TimelineFrag extends CommonFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, 
		ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.timeline_frag, container, false);
	}
			      
}
