import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class SeaWarsChess {

	static Field field = new Field(8);
	public static boolean gameOver, factOfDamage;
	static ArrayList<Ship> arrMyShip = new ArrayList<Ship>();
	static ArrayList<Ship> arrComShip = new ArrayList<Ship>();

        public static void main(String... arg) throws IOException, InterruptedException {
        	new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
 
       		coolPrinting("Welcome to the SeaWarsChess game"); 

		pause(600);

		field.viewField();
		
		
		int level = 3;
		boolean rightNum = false;
		while(!rightNum){
			System.out.println("How many ships u'd like?(but u cant match more then "+ level+" )");
			Scanner sc = new Scanner(System.in);
			int quantityOships = sc.nextInt();
			if(quantityOships <= level  & quantityOships>=(int)Math.floor((double)level/3)){
				CreateViliansShips(arrMyShip, level, quantityOships, false);
				rightNum = true;
			}else{
				System.out.println("Try another one number");
			}
		}

		int vilianLevel = 3;
		CreateViliansShips(arrComShip, vilianLevel, getRandomInt((int)Math.ceil((double)level/3),level), true);
		
		Ai ai = new Ai();
		field.viewField();

		while(!gameOver){
			
			System.out.println("");
			int i = 0;
			if(arrMyShip.size()==1){
				i = 0;
			}else{	
				boolean rightValue = false;
				while(!rightValue){
					System.out.println("Which ship u'd like(U can choose from 1 to "+arrMyShip.size()+" )");
					Scanner sc = new Scanner(System.in);
					int num = sc.nextInt();
					if(num <= arrMyShip.size() & num>0){
						i = num - 1;
						rightValue = true;
					}else{
						System.out.println("Wrong value. Try again");
					}
				}
			}
			while(!arrMyShip.get(i).factOfMoove){
				System.out.println("");
				System.out.println("What would u like to do ?");
				System.out.println("");
				System.out.println("   - go left");
				System.out.println("   - go right");
				System.out.println("   - go up");
				System.out.println("   - go down");
				System.out.println("   - shoot");
				System.out.println("   - exit ( to exit )");
				System.out.println("   - shoot");
				Scanner sc = new Scanner(System.in);
				whatToDo(sc.nextLine(), arrMyShip.get(i), arrComShip);
			}
			if(arrMyShip.get(i).factOfMoove){
				field.viewField();
				pause(600);
				
				ai.computerTurn();
				if(arrComShip.get(0).dead){
					gameOver = true;
					System.out.println("You win!");
				}
				arrMyShip.get(i).factOfMoove = false;
			}else{
				field.viewField();
				System.out.println("");
				System.out.println("You can't go there");
				System.out.println("");
			}
		}

	}

	static void whatToDo(String way, Ship ship1, ArrayList<Ship> arrCShip){
		switch(way){
			case "go left":
				ship1.sailLeft();
				break;
			case "go right":
				ship1.sailRight();
				break;
			case "go up":
				ship1.sailUp();
				break;
			case "go down":
				ship1.sailDown();
				break;
			case "shoot":
				boolean doYouShoot = true;
				while(doYouShoot){
					System.out.println("Enter coordinates to shoot:");
					Scanner sc = new Scanner(System.in);
					int x = sc.nextInt();
					int y = sc.nextInt();
					if(field.field[ x - 1 ][ y - 1 ] != "X"){
						ship1.shoot(x,y);
						factOfDamage = false;
						for(int i = 0; i < arrComShip.size(); i++){
							arrCShip.get(i).getDamage();
						}
						arrCShip.get(arrComShip.size()-1).isItMissed(x,y);
						ship1.factOfMoove = true;
						doYouShoot = false;				
					}else{
						System.out.println("You've already shot there");
					}
				}
				break;
			case "exit":
				System.out.println("- Bye!");
				gameOver = true;
				ship1.factOfMoove = true;
				break;
			
			default:
				System.out.println("- wrong command!");
				ship1.factOfMoove = false;
				break;
		}
	}

	static void CreateViliansShips(ArrayList<Ship> arrShip, int level, int quantityOfShips, boolean compCase){
		int qualityOfShips = level - quantityOfShips;
		ArrayList<Integer> point = new ArrayList<Integer>();
		int howMuchToAdd;

		int qOships = quantityOfShips;

		for(int i = 0; i < qOships; i++){
			int from;
			if(qualityOfShips%quantityOfShips == 0 & qualityOfShips/quantityOfShips == 2){
				from = 2;
			}else if((int)Math.ceil((double)qualityOfShips/2)==quantityOfShips){
				from = 1;
			}else{
				from = 0;
			}
			if(qualityOfShips<3){
				if(compCase){
					howMuchToAdd = getRandomInt(from,qualityOfShips);
				}else{
					howMuchToAdd = myCase(from, qualityOfShips, i);
				}
			}else{
				if(compCase){
					howMuchToAdd = getRandomInt(from,2);
				}else{
					howMuchToAdd = myCase(from, 2, i);
				}
			}
			qualityOfShips -= howMuchToAdd;
			int p = 0;
			boolean doCircle = true;
			while(doCircle){ 
				if(compCase){
					p = getRandomInt(1,8);
				}else{
					Scanner sc = new Scanner(System.in);
					System.out.println("what position you want?(from 1 to 8, if its not full ofc)");
					p = sc.nextInt();
				}
				if(!point.contains(p)){
					if(compCase){
						arrShip.add( new ComputerShip(true, true, true, 1 + howMuchToAdd, p, Integer.toString(i+1)));
					}else{
						arrShip.add( new MyShip(true, true, true, 1 + howMuchToAdd, p, Integer.toString(i+1)));
					}
					point.add(p);
					doCircle = false;
				}else{System.out.println("this position is full");}
			}
			quantityOfShips--;
		}

	}

	static int myCase(int from, int to, int i){
		int howMuchToAdd = 0; 
		Scanner sc = new Scanner(System.in);
		boolean rightNum = false;
		while(!rightNum){
			System.out.println("how many decks you want for ship No."+(i+1)+"?");
			System.out.println("You can choose from "+(from + 1)+" to " +(to + 1));
			int num = sc.nextInt();
			if(num>=from+1 & num<=to + 1){
				howMuchToAdd = num-1;
				rightNum = true;
			}else{
				System.out.println("Enter the right number");
				rightNum = false;
			}
		}
		return howMuchToAdd;
	}

	static void coolPrinting(String s){
		String str = new String(s);
		System.out.println(" ");
                for(int i = 0; i < str.length(); i++){
			if(Character.isSpaceChar(str.charAt(i))){
				textPause(80,150);
				System.out.print(str.charAt(i));
			}else{
				textPause(40,80);
				System.out.print(str.charAt(i));
		        }
		}
		System.out.println(" ");
	}

	static void pause(int time){
		try
			{
   				 Thread.sleep(time);
			}
			catch(InterruptedException ex)
			{
				 Thread.currentThread().interrupt();
			}
	}

	static void textPause(int timeFrom, int timeTo){
		try
			{
   				 Thread.sleep(getRandomInt(timeFrom,timeTo));
			}
			catch(InterruptedException ex)
			{
				 Thread.currentThread().interrupt();
			}
	}

	static int getRandomInt(int a, int b){          
           int s = (int)Math.round(Math.random()*(double)(b-a)+(double)a);
           return s;
	}
}