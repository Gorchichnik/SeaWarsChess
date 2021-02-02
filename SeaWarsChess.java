import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class SeaWarsChess {
	public static int d = 8;
	public static Field field = new Field(d);
	public static boolean gameOver, factOfDamage, doYouShoot, factOfComputerMoove, factOfMoove, myShipIsDead, comShipIsDead;
	public static ArrayList<Ship> arrMyShip = new ArrayList<Ship>();
	public static ArrayList<Ship> arrComShip = new ArrayList<Ship>();
	public static String[] villianSigns = {"A","B","C","D","E","F","G","H"};

        public static void main(String... arg) throws IOException, InterruptedException {
        	new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
 
       		coolPrinting("Welcome to the SeaWarsChess game"); 

		pause(600);

		field.viewField();
		
		
		int level = 5;
		boolean rightNum = false;
		while(!rightNum){
			try{
				System.out.println("");
				System.out.println("How many ships you want? ( but it can't be match more then "+ level+" )");
				System.out.println("");
				Scanner sc = new Scanner(System.in);
				int quantityOships = sc.nextInt();
				if(quantityOships <= level  & quantityOships>=(int)Math.floor((double)level/3)){
					CreateViliansShips(arrMyShip, level, quantityOships, false);
					rightNum = true;
				}else{
					System.out.println("Try another one number");
				}
			}catch(Exception e){
				System.out.println("");
				System.out.println("It must be number!!!");
				System.out.println("");
			}
		}

		int vilianLevel = 5;
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
					try{
						System.out.println("Which ship u'd like(U can choose from 1 to "+arrMyShip.size()+" )");
						Scanner sc = new Scanner(System.in);
						int num = sc.nextInt();
						if(num <= arrMyShip.size() & num>0){
							i = num - 1;
							rightValue = true;
						}else{
							System.out.println("Wrong value. Try again");
						}
					}catch(Exception e){
						System.out.println("");
						System.out.println("It must be number!!!");
						System.out.println("");
					}
				}
			}
			factOfMoove = false;
			while(!factOfMoove){
				System.out.println("");
				System.out.println("What would u like to do ?");
				System.out.println("");
				System.out.println("   - go left");
				System.out.println("   - go right");
				System.out.println("   - go up");
				System.out.println("   - go down");
				System.out.println("   - shoot");
				System.out.println("   - exit ( to exit )");
				System.out.println("");
				Scanner sc = new Scanner(System.in);
				whatToDo(sc.nextLine(), arrMyShip, arrComShip, i, true);
				if(!factOfMoove){
					field.viewField();
					System.out.println("");
					System.out.println("You can't go there!");
					System.out.println("");
				}
			}
			if(factOfMoove){
				field.viewField();
				pause(600);
				factOfComputerMoove = false;
				ai.computerTurn();
				field.viewField();
				pause(600);

				if(comShipIsDead){
					gameOver = true;
					System.out.println("You win!");
				}
				if(myShipIsDead){
					gameOver = true;
					System.out.println("You loose!");
				}
				factOfMoove = false;
				factOfComputerMoove = false;
			}else{
				field.viewField();
				System.out.println("");
				System.out.println("You can't go there");
				System.out.println("");
			}
		}

	}

	public static void whatToDo(String way, ArrayList<Ship> ship1, ArrayList<Ship> arrCShip, int indexOfShip, boolean myTurn){
		switch(way){
			case "go left":
				ship1.get(indexOfShip).sailLeft();
				factOfMoove = ship1.get(indexOfShip).factOfMoove;
				factOfComputerMoove = ship1.get(indexOfShip).factOfMoove;
				break;
			case "go right":
				ship1.get(indexOfShip).sailRight();
				factOfMoove = ship1.get(indexOfShip).factOfMoove;
				factOfComputerMoove = ship1.get(indexOfShip).factOfMoove;
				break;
			case "go up":
				ship1.get(indexOfShip).sailUp();
				factOfMoove = ship1.get(indexOfShip).factOfMoove;
				factOfComputerMoove = ship1.get(indexOfShip).factOfMoove;
				break;
			case "go down":
				ship1.get(indexOfShip).sailDown();
				factOfMoove = ship1.get(indexOfShip).factOfMoove;
				factOfComputerMoove = ship1.get(indexOfShip).factOfMoove;
				break;
			case "shoot":
				if(arrComShip.size()!=0){
					doYouShoot = false;
					while(!doYouShoot){
						myShooting(ship1, arrCShip, indexOfShip, myTurn);
					}
					if(arrMyShip.get(0).dead){
						myShipIsDead = true;
					}else if(arrComShip.get(0).dead){
						comShipIsDead = true;
					}
					terminator(ship1);
					terminator(arrCShip);
					factOfMoove = true;
					factOfComputerMoove = true;
				}
				break;
			case "exit":
				System.out.println("");
				System.out.println("- Bye!");
				System.out.println("");
				gameOver = true;
				factOfMoove = true;
				factOfComputerMoove = true;
				break;
			
			default:
				System.out.println("");
				System.out.println("- wrong command!");
				System.out.println("");
				factOfMoove = false;
				break;
		}
	}

	static void terminator(ArrayList<Ship> arrShip){		
		for(int i = 0; i < arrShip.size(); i++){
			if(arrShip.get(i).dead){
				for(int j = arrShip.size()-1; j > i; j--){
					arrShip.get(j).setSign(arrShip.get(j-1).sign);
					arrShip.get(j).createShip();
				}
				arrShip.remove(i);
				break;
			}
		}
	}

	static void myShooting(ArrayList<Ship> ship1, ArrayList<Ship> arrCShip, int indexOfShip, boolean myTurn){
		try{
			int x, y;
			Ai ai = new Ai();
			if(myTurn){
				boolean rightCoord = false;
				do{
					System.out.println("");
					System.out.println("Enter coordinates to shoot:      ( Your range is " + ship1.get(indexOfShip).range + " )" );
					System.out.println("");
					Scanner sc = new Scanner(System.in);
					x = sc.nextInt();
					y = sc.nextInt();
					if(x*y > 0 & x <= d & y <= d & ai.chekingAll(x, y, ship1, indexOfShip)){
						if(field.field[ x - 1 ][ y - 1 ] != "X"){
							rightCoord = true;			
						}else{
							System.out.println("");
							System.out.println("You've already shot there");
							System.out.println("");
						}
						
					}else{
						field.viewField();
						System.out.println("");
						System.out.println("This coordinates is out of field or out of range! ");
						System.out.println("Try another one." );
						System.out.println("");
					}
				}while(!rightCoord);
			}else{
//System.out.println("Try xy");
				ai.createRandomXY();
				x = ai.getX();
				y = ai.getY();
				
//System.out.println("x = "+x+"y = "+y);
			}
				shooting(x, y, ship1, arrCShip, indexOfShip, myTurn);			
		}catch(Exception e){
			System.out.println("");
			System.out.println("It must be number!!!");
			System.out.println("");
		}
	}

	static void shooting(int x, int y, ArrayList<Ship> ship1, ArrayList<Ship> arrCShip, int indexOfShip, boolean myTurn){
		try{
			field.field[ x - 1 ][ y - 1 ] = "X";
			field.viewField();
			pause(2000);
			ship1.get(indexOfShip).shoot(x,y);
			factOfDamage = false;
			for(int i = 0; i < ship1.size(); i++){
				ship1.get(i).getDamage();
				if(factOfDamage){
					System.out.println("");
					System.out.println("You've hit to your own ship. Be careful!");
					System.out.println("");
					pause(2000);
					break;
				}
			}
			try{
				for(int i = 0; i < arrCShip.size(); i++){
					arrCShip.get(i).getDamage();
					if(arrCShip.get(i).dead & myTurn & arrComShip.size()!=1 & i!=0){
						MyShip mine = (MyShip)ship1.get(indexOfShip);
						mine.upGrade();
					}
				}
			}catch(Exception e){
				System.out.println("Exception in 2");
			}
			try{
				arrCShip.get(arrCShip.size()-1).isItMissed(x,y);
				ship1.get(indexOfShip).factOfMoove = true;
				doYouShoot = true;
			}catch(Exception e){
				System.out.println("Exception in 1");
			}
		}catch(Exception e){
			System.out.println("Exception in shooting");
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
				try{ 
					if(compCase){
						p = getRandomInt(1,8);
					}else{
						Scanner sc = new Scanner(System.in);
						System.out.println("");
						System.out.println("what position you want for ship No."+(i+1)+" ?(from 1 to 8, if its not full ofc)");
						System.out.println("");
						p = sc.nextInt();
					}
					if(!point.contains(p)){
						if(compCase){
							arrShip.add( new ComputerShip(1 + howMuchToAdd, p, villianSigns[i]));
						}else{
							arrShip.add( new MyShip(1 + howMuchToAdd, p, Integer.toString(i+1)));
						}
						point.add(p);
						doCircle = false;
					}else{
						System.out.println("this position is full");
					}
				}catch(Exception e){
					System.out.println("");
					System.out.println("It must be number!!!");
					System.out.println("");
				}
			}
			quantityOfShips--;
		}

	}

	static int myCase(int from, int to, int i){
		int howMuchToAdd = 0; 
		if(from == to){
			howMuchToAdd = from;
		}else{
			boolean rightNum = false;
			while(!rightNum){
				try{
					System.out.println("");
					System.out.println("how many decks you want for ship No."+(i+1)+"?");
					System.out.println("You can choose from "+(from + 1)+" to " +(to + 1));
					System.out.println("");
					Scanner sc = new Scanner(System.in);
					int num = sc.nextInt();
					if(num>=from+1 & num<=to + 1){
						howMuchToAdd = num-1;
						rightNum = true;
					}else{
						System.out.println("");
						System.out.println("Enter the right number");
						System.out.println("");
						rightNum = false;
					}
				}catch(Exception e){
					System.out.println("");
					System.out.println("It must be number!!!");
					System.out.println("");
				}
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

	public static int getRandomInt(int a, int b){          
           int s = (int)Math.round(Math.random()*(double)(b-a)+(double)a);
           return s;
	}
}