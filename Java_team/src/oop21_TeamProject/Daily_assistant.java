package oop21_TeamProject;

import java.util.*;

public class Daily_assistant  {
	public static void PickLunch()  {
		System.out.println("PickLunch started");
		PickLunch pl = new PickLunch();
		pl.MenuCategory();
	}
	
	public static void DailyPlanner() {
		System.out.println("DailyPlanner started");
		DailyPlanner dp = new DailyPlanner();
		dp.PlannerMenu();
	}
	
	public static void Umbrella() {
		System.out.println("Umbrella started");
		Umbrella um = new Umbrella();
		um.PrintWeather();
		
	}
	
	public static void InputName() {
		Scanner keyboard = new Scanner(System.in);
		String name;
		System.out.print("Please enter your name: ");
		name = keyboard.nextLine();
		System.out.println("Welcome " + name + "!");
	}
	
	public static void DecesionMenu() {
		Scanner keyboard = new Scanner(System.in);
		int Service;
		while(true) {
			System.out.println("Pleae enter the number : ");
			System.out.println("1.PickLunch 2.DailyPlanner 3.Umbrella 4.Exit ");
			Service = keyboard.nextInt();
			if(Service == 1) {
				PickLunch();
			}
			if(Service == 2) {
				DailyPlanner();
			}
			if(Service == 3) {
				Umbrella();
			}
			if(Service == 4) {
				System.out.println("Exit Selected. Have a nice Day ^^");
				System.exit(0);
			}
		}
	}

	public static void main(String[] args) {
		InputName();
		DecesionMenu();
	}
}
