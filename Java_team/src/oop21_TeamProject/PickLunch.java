package oop21_TeamProject;

import java.util.*;

public class PickLunch extends Daily_assistant {

	public static void MenuCategory() {
		int menuCategory;
		int korean, chinese, japanese, western, other;
		Scanner keyboard = new Scanner(System.in);
			
		System.out.println("*** Let's select Lunch menu! ***");
		try {
			System.out.println("Please enter the number what category of menu you like : ");
			System.out.println("1.Korean 2.Chinese 3.Japanese 4.Western 5.Other");
			menuCategory = keyboard.nextInt();
			
			if((menuCategory < 1) || (menuCategory >= 6)) {
				throw new Exception();
			}
			
//			RandomChoice(menuCategory);		
			
			if(menuCategory == 1) {
				randomKorean();
			}
			else if(menuCategory == 2) {
				randomChinese();
			}
			else if(menuCategory == 3) {
				randomJapanese();
			}
			else if(menuCategory == 4) {
				randomWestern();
			}
			else if(menuCategory == 5) {
				randomOther();
			}
		}
		catch(Exception e) {
			System.out.println("Try again:");
			MenuCategory();
		}
	}
	
	public static void randomKorean() {
		String[] koreanFood = {"¿µ", "ÀÏ", "ÀÌ", "»ï", "»ç", "¿À", "À°", "Ä¥", "ÆÈ", "±¸"};
		Random r = new Random();
		int randomNum = r.nextInt();
		int menuNum = 10;
		randomNum %= menuNum;
		
		System.out.println("Why don't you eat " + koreanFood[randomNum] + " for lunch?");
		System.out.println("You'll have a great lunch!");
		System.out.println("********************************");
	}
	
	public static void randomChinese() {
		String[] chineseFood = {"¿µ", "ÀÏ", "ÀÌ", "»ï", "»ç", "¿À", "À°", "Ä¥", "ÆÈ", "±¸"};
		Random r = new Random();
		int randomNum = r.nextInt();
		int menuNum = 10;
		randomNum %= menuNum;
		
		System.out.println("Why don't you eat " + chineseFood[randomNum] + " for lunch?");
		System.out.println("You'll have a great lunch!");
		System.out.println("********************************");
	}
	
	public static void randomJapanese() {
		String[] japaneseFood = {"¿µ", "ÀÏ", "ÀÌ", "»ï", "»ç", "¿À", "À°", "Ä¥", "ÆÈ", "±¸"};
		Random r = new Random();
		int randomNum = r.nextInt();
		int menuNum = 10;
		randomNum %= menuNum;
		
		System.out.println("Why don't you eat " + japaneseFood[randomNum] + " for lunch?");
		System.out.println("You'll have a great lunch!");
		System.out.println("********************************");
	}
	
	public static void randomWestern() {
		String[] westernFood = {"¿µ", "ÀÏ", "ÀÌ", "»ï", "»ç", "¿À", "À°", "Ä¥", "ÆÈ", "±¸"};
		Random r = new Random();
		int randomNum = r.nextInt();
		int menuNum = 10;
		randomNum %= menuNum;
		
		System.out.println("Why don't you eat " + westernFood[randomNum] + " for lunch?");
		System.out.println("You'll have a great lunch!");
		System.out.println("********************************");
	}
	
	public static void randomOther() {
		String[] otherFood = {"¿µ", "ÀÏ", "ÀÌ", "»ï", "»ç", "¿À", "À°", "Ä¥", "ÆÈ", "±¸"};
		Random r = new Random();
		int randomNum = r.nextInt();
		int menuNum = 10;
		randomNum %= menuNum;
		
		System.out.println("Why don't you eat " + otherFood[randomNum] + " for lunch?");
		System.out.println("You'll have a great lunch!");
		System.out.println("********************************");
	}
	
	/*public static void RandomChoice(int category) {		// ´ÙÇü¼º µîÀ» ÀÌ¿ëÇÑ ´Ù¸¥ ¹æ¹ý???
		Random r = new Random();
		
		int randomNum = r.nextInt(10000000);
		int menuNum = 10;
	randomNum %= menuNum;
		randomNum = randomNum % menuNum;
		
		System.out.println("");
	}*/
	
}
