package app.light;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract.Constants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import lib.pulltorefresh.PullToRefreshAttacher;


public class TimelineFrag extends CommonFragment implements PullToRefreshAttacher.OnRefreshListener {
	
	private PullToRefreshAttacher mPullToRefreshAttacher;
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
		ViewGroup container, Bundle savedInstanceState) {
		//return inflater.inflate(R.layout.timeline_frag, container, false);
		
		View view = inflater.inflate(R.layout.timeline_frag, container, false);
		
		ScrollView scrollView = (ScrollView) view.findViewById(R.id.timeline_scroll);
		mPullToRefreshAttacher = ((FragmentActivity) getActivity()).getPullToRefreshAttacher();
		
		 // Now set the ScrollView as the refreshable view, and the refresh listener (this)
        mPullToRefreshAttacher.addRefreshableView(scrollView, this);

    

        return view;
	}

	@Override
    public void onRefreshStarted(View view) {
        /**
         * Simulate Refresh with 4 seconds sleep
         */
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);

                // Notify PullToRefreshAttacher that the refresh has finished
                mPullToRefreshAttacher.setRefreshComplete();
            }
        }.execute();
    }
	
}

