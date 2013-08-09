package app.light;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RivalFrag extends CommonFragment {
	
	private Context context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
		ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.frag_rival, container, false);
	}
		
	@Override
	public void onStart() {
		super.onStart();
		context = getActivity();
	
	}
}
