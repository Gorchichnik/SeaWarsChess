import java.util.ArrayList;

public abstract class Ship{
	protected int[][] x_y_coord;
        protected int decks;
	public int range, accuracy, boarding, damage, speed; 
        public boolean armor, dead, factOfMoove;
	protected String sign;
	protected boolean senseTo_sail = true;
	protected ArrayList<int[]> arrList_damage = new ArrayList<int[]>();

	public void sailUp(){	
		minusCoordMakeSense(1);
		sailMinusCoord(1);
	}
	public void sailDown(){
                plusCoordMakeSense(1);
		sailPlusCoord(1);
	}
	public void sailLeft(){
		minusCoordMakeSense(0);
		sailMinusCoord(0);
	}
	public void sailRight(){
		plusCoordMakeSense(0);
		sailPlusCoord(0);
	}

	protected void deleteCell(int x, int y){
		Field.field[y - 1][x - 1] = "_";
	}
	
	protected void createShip(){
		for(int i = 0; i < decks; i++){
			Field.field[ x_y_coord[i][1] - 1 ][ x_y_coord[i][0] - 1 ] = sign;		
		}
		for(int i = 0; i < arrList_damage.size(); i++){
				Field.field[ arrList_damage.get(i)[1] - 1][ arrList_damage.get(i)[0] - 1 ] = "X";
		}
	}
	protected void minusCoordMakeSense(int x_OR_y){
		for(int i = 0; i < decks; i++){
			if(x_y_coord[i][x_OR_y] - 1 <= 0 | x_y_coord[i][x_OR_y] - 1 > 8){
				senseTo_sail = false;
			}else if(Field.field[ x_y_coord[i][1] - 1 - x_OR_y][ x_y_coord[i][0] - 2 + x_OR_y] != "_" & Field.field[ x_y_coord[i][1] - 1 - x_OR_y][ x_y_coord[i][0] - 2 + x_OR_y] != sign & Field.field[ x_y_coord[i][1] - 1 - x_OR_y][ x_y_coord[i][0] - 2 + x_OR_y] != "X"){
				senseTo_sail = false;
			}		
		}
	}
	protected void plusCoordMakeSense(int x_OR_y){
		for(int i = 0; i < decks; i++){
			if(x_y_coord[i][x_OR_y] + 1 <= 0 | x_y_coord[i][x_OR_y] + 1 > 8){
				senseTo_sail = false;
			}else if(Field.field[ x_y_coord[i][1] - 1 + x_OR_y][ x_y_coord[i][0] - x_OR_y] != "_" & Field.field[ x_y_coord[i][1] - 1 + x_OR_y][ x_y_coord[i][0] - x_OR_y] != sign & Field.field[ x_y_coord[i][1] - 1 - x_OR_y][ x_y_coord[i][0] - 2 + x_OR_y] != "X"){
				senseTo_sail = false;
			}		
		}
	}
	protected void sailMinusCoord(int x_OR_y){
		if(senseTo_sail){
			for(int i = 0; i < decks; i++){
				deleteCell(x_y_coord[i][0],x_y_coord[i][1]);
				x_y_coord[i][x_OR_y]--;		
			}
			for(int i = 0; i < arrList_damage.size(); i++){
				arrList_damage.get(i)[x_OR_y]--;
			}
			createShip();
			factOfMoove = true;
		}
		senseTo_sail = true;
	}
	protected void sailPlusCoord(int x_OR_y){
		if(senseTo_sail){
			for(int i = 0; i < decks; i++){
				deleteCell(x_y_coord[i][0],x_y_coord[i][1]);
				x_y_coord[i][x_OR_y]++;		
			}
			for(int i = 0; i < arrList_damage.size(); i++){
				arrList_damage.get(i)[x_OR_y]++;
			}
			createShip();
			factOfMoove = true;
		}
		senseTo_sail = true;
	}
	
	public void shoot(int x, int y){
		Field.field[ x - 1 ][ y - 1 ] = "o";
	}
	public void boarding(){
		System.out.println("There will be boarding...");
	}
	public void getDamage(){
		for(int i = 0; i < decks; i++){
			if(Field.field[ x_y_coord[i][1] - 1 ][ x_y_coord[i][0] - 1 ] == "o"){
				Field.field[ x_y_coord[i][1] - 1 ][ x_y_coord[i][0] - 1 ] = "X";
				damage++;
				SeaWarsChess.factOfDamage = true;
				arrList_damage.add(new int[]{x_y_coord[i][0],x_y_coord[i][1]});
				if(damage == decks){
					dead = true;	
				}
			}
		}
	}
	public void isItMissed(int x, int y){
		if(!SeaWarsChess.factOfDamage){
			Field.field[x - 1][y - 1] = "_";
		}
	}
}