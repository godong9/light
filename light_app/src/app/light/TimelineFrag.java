package app.light;

import java.util.LinkedList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class TimelineFrag extends CommonFragment {
	
	private LinkedList<String> mListItems;
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
		ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.timeline_frag, container, false);
		
		
	}
			      
}
