package oop21_TeamProject;

import java.util.*;

class Plan {
	public String Date;
	public String Event;
	
	public Plan (String Date, String Event) {
		this.Date = Date;
		this.Event = Event;
	}
	
	public String toString() {
		String str = "Date: " + Date + "\n" + "Event:" + Event;
		return str;
	}
}

public class DailyPlanner extends Daily_assistant {
	
	public static void PlannerMenu() {
		//start();
		ArrayList<Plan> Daily = new ArrayList<Plan>();
		while(true) {
			System.out.println("Pleae enter the number: ");
			System.out.println("1.RegistPlan 2.WeeklyPlan 3.Exit");
			Scanner keyboard = new Scanner(System.in);
			int num = keyboard.nextInt();
			
			if (num == 1) {				
				RegistPlan(Daily);
			}
			
			else if (num == 2) {
				WeeklyPlan(Daily);
			}
			
			else if (num == 3) {
				System.out.println("********************************");
				break; 
			}
			
			else {
				System.out.println("Try again: ");
			}
		}
	}

	public static void RegistPlan(ArrayList list) {
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Enter the date of new event(YYYY-DD-MM): ");
		String date = keyboard.nextLine();
		System.out.print("Enter the name of new event: ");
		String event = keyboard.nextLine();
		
		Arraylist(list, date, event);
	}	
	
	public static void WeeklyPlan(ArrayList list) {
		int size = list.size();
		for (int i = 0; i < size; i++) {
			System.out.println(list.get(i) + "\n");
		}
	}
	
	public static void Arraylist(ArrayList list, String str1, String str2) {
		
		String date = str1;
		String event = str2;
		
		Plan p = new Plan(date, event);
		list.add(p);
	}
}