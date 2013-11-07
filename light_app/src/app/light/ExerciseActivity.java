package app.light;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ExerciseActivity extends Activity {
	private Intent intent;
	@Override	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exercise);
		intent = getIntent();	
		numCount();
	}
	public void numCount(){
		String num = "10";
		intent.putExtra("data_num",num);
		setResult(RESULT_OK,intent); // 추가 정보를 넣은 후 다시 인텐트를 반환합니다.
		finish(); // 액티비티 종료	
	}

}
