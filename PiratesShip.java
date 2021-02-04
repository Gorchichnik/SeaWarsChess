import java.util.Scanner;
import java.util.ArrayList;

public class PiratesShip extends Ship{

	public PiratesShip(int decks, int x, int y){
		this.view = (int)Math.ceil((double)decks/2);
		this.range = 1;
		this.boarding = 1;
		this.decks = decks;
		this.sign = "P";
		this.coof = 60;
System.out.println("damage ="+damage+" decks = "+decks);
		x_y_coord = new int[decks][2];
		for(int i = 0; i < decks; i++){
			x_y_coord[i][0] = y;
			x_y_coord[i][1] = x;
		}
		super.createShip();
		damage = 0;
	}
}
