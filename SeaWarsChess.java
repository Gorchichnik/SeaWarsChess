import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class SeaWarsChess {
	public static final int d = 8;
	public static Field field = new Field(d);
	public static boolean gameOver, factOfDamage, doYouShoot, factOfComputerMoove, factOfMoove, myShipIsDead, comShipIsDead, factOfPiratesMoove;
	public static ArrayList<Ship> arrMyShip = new ArrayList<Ship>();
	public static ArrayList<Ship> arrComShip = new ArrayList<Ship>();
	public static ArrayList<Ship> pirates = new ArrayList<Ship>();
	public static String[] villianSigns = {"A","B","C","D","E","F","G","H"};

        public static void main(String... arg) throws IOException, InterruptedException {
        	new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
 
       		coolPrinting("      Welcome to the SeaWarsChess game"); 

		pause(600);

		field.viewField();
		boolean rightNum = false;
		int level = 3;
		try{
			do{
				System.out.println("");
				System.out.print("      Choose level u want(from 1 to 8): ");
				
				Scanner sc = new Scanner(System.in);
				level = sc.nextInt();
				if(level <= 8  & level > 0){
					rightNum = true;
				}else{
					field.viewField();
					System.out.println("");
					System.out.println("     Try another one number");
					System.out.println("");
				}
			}while(!rightNum);
		}catch(Exception e){
			System.out.println("");
			System.out.println("     It must be number!!!");
			System.out.println("");
		}
		rightNum = false;
		while(!rightNum){
			try{
				int quantityOships = 0;	
				System.out.println("");
				System.out.println("   How many ships you want? ");
				System.out.println("  (but it can't be match more then "+ level+" and less then " +(int)Math.ceil((double)level/3)+")");
				System.out.print("   : ");
				Scanner sc = new Scanner(System.in);
				quantityOships = sc.nextInt();
				if(quantityOships <= level  & quantityOships>=(int)Math.ceil((double)level/3)){
					createViliansShips(arrMyShip, level, quantityOships, false);
					rightNum = true;
				}else{
					field.viewField();
					System.out.println("");
					System.out.println("      Try another one number");
					System.out.println("");
				}
			}catch(Exception e){
				System.out.println("");
				System.out.println("      It must be number!!!");
				System.out.println("");
			}
		}

		int vilianLevel = level;
		createViliansShips(arrComShip, vilianLevel, getRandomInt((int)Math.ceil((double)level/3),level), true);
		createPiratesShips((int)Math.ceil( (double)level/2 ) , 1);
		
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
						System.out.println("");
						System.out.println("   Which ship u'd like(U can choose from 1 to "+arrMyShip.size()+" )");
						System.out.print("   : ");
						Scanner sc = new Scanner(System.in);
						int num = sc.nextInt();
						if(num <= arrMyShip.size() & num>0){
							i = num - 1;
							rightValue = true;
						}else{
							field.viewField();
							System.out.println("     Wrong value. Try again");
						}
					}catch(Exception e){
						System.out.println("");
						System.out.println("     It must be number!!!");
						System.out.println("");
					}
				}
			}
			factOfMoove = false;
			while(!factOfMoove){
				System.out.println("");
				System.out.println("What would u like to do ?");
				System.out.println("");
				System.out.println("   Go:");
				System.out.println("      - <l>eft");
				System.out.println("      - <r>ight");
				System.out.println("      - <u>p");
				System.out.println("      - <d>own");
				System.out.println("        <s>hoot");
				if(arrMyShip.get(i).canUBoard(arrComShip) | arrMyShip.get(i).canUBoard(pirates)){
					System.out.println("        <b>oard");
				}
				System.out.println("        <e>xit ( to exit )");
				System.out.println("");
                                System.out.print("   : ");
				Scanner sc = new Scanner(System.in);
				int numBefore = arrMyShip.size();
				char way = sc.next().charAt(0);
				if(arrMyShip.get(i).canUBoard(pirates) & Character.toString(way) == "b"){
					whatToDo(way, arrMyShip, pirates, i, true);
					if(numBefore!=arrMyShip.size()){
						System.out.println("   You've sucessfully captured pirates ship!");
						pause(4000);
					}else{
						System.out.println("   You've gotten PIZDI!");
						pause(4000);
					}
				}else{
					whatToDo(way, arrMyShip, arrComShip, i, true);
					if(numBefore!=arrMyShip.size()){
						System.out.println("   You've sucessfully captured villainous ship!");
						pause(4000);
					}else if(Character.toString(way) == "b"){
						System.out.println("   You've gotten PIZDI!");
						pause(4000);
					}
				}
				
				if(!factOfMoove){
					field.viewField();
					System.out.println("");
					System.out.println("   You can't go there!");
					System.out.println("");
				}
				if(arrMyShip.get(i).headHasBeenDestroyed){
					factOfMoove = false;
					gameOver = true;
					System.out.println("   You win!");
				}
			}
			if(factOfMoove){
				field.viewField();
				pause(600);
				factOfComputerMoove = false;
				int numMyBefore = arrMyShip.size();
				int numPiratesBefore = pirates.size();
				ai.computerTurn();
				if(numMyBefore!=arrMyShip.size() ){
					System.out.println("   Computer has stolen your ship!");
					pause(2000);
				}else if(numPiratesBefore!=pirates.size() ){
					System.out.println("   Intelligence service: Villoin has stolen pirates ship!");
					pause(2000);
				}
				field.viewField();
				pause(600);
				if(arrComShip.get(ai.numOfShip).headHasBeenDestroyed){
					factOfMoove = false;
					gameOver = true;
					System.out.println("   You loose!");
				}

				if(pirates.size()!=0){
//System.out.println("pirates start");
					numMyBefore = arrMyShip.size();
					int numVillainBefore = arrComShip.size();
					ai.piratesTurn();
//System.out.println("pirates have mooved");
					if(numMyBefore!=arrMyShip.size() ){
						System.out.println("   Pirates has stolen your ship!");
						pause(2000);
						if(pirates.get(ai.numOfShip).headHasBeenDestroyed){
							factOfMoove = false;
							gameOver = true;
							System.out.println("   You loose!");
						}
					}else if(numVillainBefore!=arrComShip.size() ){
						System.out.println("   Intelligence service: Pirates have stolen villoinous ship!");
						pause(5000);
						if(pirates.get(ai.numOfShip).headHasBeenDestroyed){
							factOfMoove = false;
							gameOver = true;
							System.out.println("   You win!");
						}
					}
					field.viewField();
					pause(600);
				}
				
				if(comShipIsDead){
					gameOver = true;
					System.out.println("   You win!");
				}
				if(myShipIsDead){
					gameOver = true;
					System.out.println("   You loose!");
				}
				factOfMoove = false;
				factOfComputerMoove = false;
			}else{
				field.viewField();
				System.out.println("");
				System.out.println("   You can't go there");
				System.out.println("");
			}
		}

	}

	public static void whatToDo(char charWay, ArrayList<Ship> ship1, ArrayList<Ship> arrCShip, int indexOfShip, boolean myTurn){
		String way = Character.toString(charWay);
		switch(way){
			case "l":
				ship1.get(indexOfShip).sailLeft();
				factOfMoove = ship1.get(indexOfShip).factOfMoove;
				factOfComputerMoove = ship1.get(indexOfShip).factOfMoove;
				factOfPiratesMoove = ship1.get(indexOfShip).factOfMoove;
				break;
			case "r":
				ship1.get(indexOfShip).sailRight();
				factOfMoove = ship1.get(indexOfShip).factOfMoove;
				factOfComputerMoove = ship1.get(indexOfShip).factOfMoove;
				factOfPiratesMoove = ship1.get(indexOfShip).factOfMoove;
				break;
			case "u":
				ship1.get(indexOfShip).sailUp();
				factOfMoove = ship1.get(indexOfShip).factOfMoove;
				factOfComputerMoove = ship1.get(indexOfShip).factOfMoove;
				factOfPiratesMoove = ship1.get(indexOfShip).factOfMoove;
				break;
			case "d":
				ship1.get(indexOfShip).sailDown();
				factOfMoove = ship1.get(indexOfShip).factOfMoove;
				factOfComputerMoove = ship1.get(indexOfShip).factOfMoove;
				factOfPiratesMoove = ship1.get(indexOfShip).factOfMoove;
				break;
			case "s":
				if(arrComShip.size()!=0){
					doYouShoot = false;
					while(!doYouShoot){
						myShooting(ship1, arrCShip, indexOfShip, myTurn);
					}
					if(arrMyShip.get(0).dead){
						arrMyShip.get(indexOfShip).headHasBeenDestroyed = true;
					}else if(arrComShip.get(0).dead){
						comShipIsDead = true;
					}
					
					terminator(ship1);
					terminator(arrCShip);
//System.out.println("pirates dead =  "+Boolean.toString(pirates.get(0).dead) );
					terminator(pirates);
					factOfMoove = true;
					factOfComputerMoove = true;
				}
				break;
			case "b":
				ship1.get(indexOfShip).boarding(ship1, arrCShip);
				factOfMoove = true;
				factOfComputerMoove = true;
				factOfPiratesMoove = true;
				break;
			case "e":
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
//System.out.println("dead =  "+Boolean.toString(arrShip.get(i).dead) );
			if(arrShip.get(i).dead){
				terminateOne(arrShip, i);
				break;
			}
		}
	}

	static void terminateOne(ArrayList<Ship> arrShip, int i){
		for(int j = arrShip.size()-1; j > i; j--){
			arrShip.get(j).setSign(arrShip.get(j-1).sign);
			arrShip.get(j).createShip();
		}
		arrShip.remove(i);
	}


	static void myShooting(ArrayList<Ship> ship1, ArrayList<Ship> arrCShip, int indexOfShip, boolean myTurn){
		try{
			int x, y;
			Ai ai = new Ai();
			if(myTurn){
				boolean rightCoord = false;
				do{
					System.out.println("");
					System.out.println("   Enter coordinates to shoot ( Your range is " + ship1.get(indexOfShip).range + " )." );
					System.out.println("   (Your range is " + ship1.get(indexOfShip).range + " ).");
					System.out.print("   :");
					Scanner sc = new Scanner(System.in);
					x = sc.nextInt();
					y = sc.nextInt();
					if(x*y > 0 & x <= d & y <= d & !ai.doYouBit(x, y, ship1) & arrMyShip.get(indexOfShip).rangeIsEnough(x,y) ){
						if(field.field[ x - 1 ][ y - 1 ] != "X"){
							rightCoord = true;			
						}else{
							field.viewField();
							System.out.println("");
							System.out.println("   You've already shot there");
							System.out.println("");
						}
						
					}else{
						field.viewField();
						System.out.println("");
						System.out.println("   This coordinates is out of field or out of range! ");
						System.out.println("   Try another one." );
						System.out.println("");
					}
				}while(!rightCoord);
			}else{
//System.out.println("Try xy");
				x = ai.getX();
				y = ai.getY();
				
//System.out.println("x = "+x+"y = "+y);
			}
				shooting(x, y, ship1, arrCShip, indexOfShip, myTurn);			
		}catch(Exception e){
			System.out.println("");
			System.out.println("   It must be number!!!");
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
					System.out.println("   You've hit to your own ship. Be careful!");
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
				for(int i = 0; i < pirates.size(); i++){
//System.out.println("pirates damage");
					pirates.get(i).getDamage();
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

	static void createViliansShips(ArrayList<Ship> arrShip, int level, int quantityOfShips, boolean compCase){
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
						System.out.println("what position you want for ship No."+(i+1)+" ?(from 1 to " +d+ ", if its not full ofc)");
						System.out.println("");
						p = sc.nextInt();
					}
					if(!point.contains(p)){
						field.viewField();
						if(compCase){
							arrShip.add( new ComputerShip(1 + howMuchToAdd, p, villianSigns[i]));
						}else{
							arrShip.add( new MyShip(1 + howMuchToAdd, p, Integer.toString(i+1)));
						}
						point.add(p);
						doCircle = false;
					}else{
						field.viewField();
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

	static void createPiratesShips(int qOfPiratesShips, int level){
		boolean emptyCell;
		for(int i = 0; i < qOfPiratesShips; i++){
			emptyCell = false;
			do{
				int x = getRandomInt(1,SeaWarsChess.d);
				int y = getRandomInt(1,SeaWarsChess.d);
				if(field.field[x - 1][y - 1] == "-" | field.field[x - 1][y - 1] == "_"){
					pirates.add( new PiratesShip(level, x, y));
					emptyCell = true;
				}
			}while(!emptyCell);
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
					field.viewField();
					System.out.println("");
					System.out.println("   How many decks you want for ship No."+(i+1)+"?");
					System.out.println("   You can choose from "+(from + 1)+" to " +(to + 1));
					System.out.print("   : ");
					Scanner sc = new Scanner(System.in);
					int num = sc.nextInt();
					if(num>=from+1 & num<=to + 1){
						howMuchToAdd = num-1;
						rightNum = true;
					}else{
						System.out.println("");
						System.out.println("   Enter the right number");
						System.out.println("");
						pause(3000);
						rightNum = false;
					}
				}catch(Exception e){
					System.out.println("");
					System.out.println("   It must be number!!!");
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