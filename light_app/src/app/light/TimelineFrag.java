package app.light;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TimelineFrag extends CommonFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, 
		ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.timeline_frag, container, false);
	}
			      
}
