package app.light;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CommunityFrag extends CommonFragment {
	
	//private Context context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
		ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_community, container, false);
		//context = getActivity();
		
		return view;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
	}			      
}