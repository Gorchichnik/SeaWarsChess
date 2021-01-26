import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class SeaWarsChess {

	static Field field = new Field(8);
	public static boolean gameOver, factOfDamage;

        public static void main(String... arg) throws IOException, InterruptedException {
        	new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
 
       		coolPrinting("Welcome to the SeaWarsChess game"); 

		pause(600);

		field.viewField();
		Scanner sc = new Scanner(System.in);
		
		Ship my_Ship1 = new MyShip(true, true, true, 3, "R");

		ArrayList<Ship> arrComShip = new ArrayList<Ship>();
		int level = 8;
		int quantityOfShips = getRandomInt((int)Math.ceil((double)level/3),level);
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
				howMuchToAdd = getRandomInt(from,qualityOfShips);
			}else{
				howMuchToAdd = getRandomInt(from,2);
			}
			qualityOfShips -= howMuchToAdd;
			int p = 0;
			boolean doCircle = true;
			while(doCircle){ 
				p = getRandomInt(1,8);
				if(!point.contains(p)){
					arrComShip.add( new ComputerShip(true, true, true, 1 + howMuchToAdd, p, Integer.toString(i+1)));
					point.add(p);
					doCircle = false;
				}
			}
			quantityOfShips--;
		}

		Ai ai = new Ai();
		field.viewField();

		while(!gameOver){
			System.out.println("");
			System.out.println("What would u like to do ?");
			System.out.println("");
			System.out.println("   - go left");
			System.out.println("   - go right");
			System.out.println("   - go up");
			System.out.println("   - go down");
			System.out.println("   - shoot");
			System.out.println("   - exit ( to exit )");
		
			whatToDo(sc.nextLine(), my_Ship1, arrComShip);

			if(my_Ship1.factOfMoove){
			
				field.viewField();
				pause(600);
				
				ai.computerTurn();
				for(int i = 0; i < arrComShip.size(); i++){
					if(arrComShip.get(i).dead){
						gameOver = true;
						System.out.println("You win!");
					}
				}
				my_Ship1.factOfMoove = false;
			}else{
				field.viewField();
				System.out.println("");
				System.out.println("You can't go there");
				System.out.println("");
			}
		}

	}

	static void whatToDo(String way, Ship ship1, ArrayList<Ship> arrComShip){
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
							arrComShip.get(i).getDamage();
						}
						arrComShip.get(arrComShip.size()-1).isItMissed(x,y);
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
				ship1.factOfMoove = true;
				break;
		}
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