import java.util.Scanner;

public class MyShip extends Ship{

	public MyShip(int decks, int point, String sign){
		this.range = decks;
		this.view = (int)Math.ceil((double)decks/2)*3;
		this.coof = 10;
		this.boarding = 1;
		this.decks = decks;
		this.sign = sign;
		x_y_coord = new int[decks][2];
		for(int i = 0; i < decks; i++){
			x_y_coord[i][0] = point;
			x_y_coord[i][1] = 8 - i;
		}
		super.createShip();
		damage = 0;
	}

	public void upGrade(){
		System.out.println("Congratulations! You've destroyed enemy's ship.");
		System.out.println("And now, you can UPGRADE your ship");
		boolean rightComm = false;
		while(!rightComm){
			System.out.println("");
			System.out.println("What exactly would you like to UPGRADE?");
			System.out.println("- range");
			System.out.println("- view");
			System.out.println("- boarding");
			System.out.println("- nothing");
			System.out.println("");
			Scanner sc = new Scanner(System.in);
			switch(sc.nextLine()){
				case "range":
					if(range<8){
						upGradeCannons();
						rightComm = true;
					}else{
						System.out.println("You've reach maximum range. Try to upgrade anything else.");
					}
					break;
				case "view":
					if(view<8){
						upGradeView();
						rightComm = true;
					}else{
						System.out.println("You've reach maximum view. Try to upgrade anything else.");
					}
					break;
				case "boarding":
					trainBattle();
					rightComm = true;
					break;
				case "nothing":
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
	public void upGradeView(){
		view++;
	}
	public void trainBattle(){
		this.coof *= 2;
	}

	public int sellShip(){
		return (decks - damage);
	}
}