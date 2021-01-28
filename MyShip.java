import java.util.Scanner;

public class MyShip extends Ship{

	public MyShip(boolean wduLike_range, boolean armor, boolean speedUp, int decks, int point, String sign){
		this.range = (wduLike_range ? 1 : 0) + decks;
		this.armor = armor;
		this.speed = 1 + (speedUp ? 1 : 0);
		this.decks = decks;
		this.sign = sign;
		x_y_coord = new int[decks][2];
		for(int i = 0; i < decks; i++){
			x_y_coord[i][0] = point;
			x_y_coord[i][1] = 8 - i;
		}
		super.createShip();
	}

	public boolean rangeIsEnough(int x,int y){	
		boolean enough = false;
		for(int i = 0; i < x_y_coord.length; i++){
			double length = Math.sqrt((double)(Math.abs(x_y_coord[i][1]-x)*Math.abs(x_y_coord[i][1]-x) +
			                Math.abs(x_y_coord[i][0]-y)*Math.abs(x_y_coord[i][0]-y)));
			if(length < range+1){
				enough = true;
			}
		}
		return enough;
	}

	public void upGrade(){
		System.out.println("Congratulations! You've destroyed enemy's ship.");
		System.out.println("And now, you can UPGRADE your ship");
		boolean rightComm = false;
		while(!rightComm){
			System.out.println("What exactly would you like to UPGRADE?");
			System.out.println("-range");
			System.out.println("-speed");
			System.out.println("-accuracy");
			System.out.println("-boarding");
			Scanner sc = new Scanner(System.in);
			switch(sc.nextLine()){
				case "range":
					upGradeCannons();
					rightComm = true;
					break;
				case "speed":
					upGradeSail();
					rightComm = true;
					break;
				case "accuracy":
					trainSooting();
					rightComm = true;
					break;
				case "boarding":
					trainBattle();
					rightComm = true;
					break;
				default:
					System.out.println("wrong command. Try again");
					break;
			}
		}
	}

	public void upGradeCannons(){
		range++;
	}
	public void upGradeSail(){
		speed++;
	}
	public void trainSooting(){
		accuracy++;
	}
	public void trainBattle(){
		boarding++;
	}

	public int sellShip(){
		return (decks - damage);
	}
}