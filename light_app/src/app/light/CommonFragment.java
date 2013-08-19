package app.light;

import java.sql.Timestamp;
import android.app.Fragment;

public class CommonFragment extends Fragment {
	
	public Timestamp getTimestamp(String str){
		return Timestamp.valueOf(str);
	}
}
