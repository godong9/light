package app.light;

public class HistoryObj {
	//전체 data type 
	public String date;
	public String status;
	public String food_calorie;
	public String exercise_calorie;
	
	
	public HistoryObj(String date, String status, String food_calorie, String exercise_calorie) {
		this.date = date;
		this.status = status;
		this.food_calorie = food_calorie;
		this.exercise_calorie = exercise_calorie;		
	}

}
