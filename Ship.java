import java.util.ArrayList;

public abstract class Ship{
	protected int[][] x_y_coord;
        protected int decks;
	public int range, boarding, damage, view, rivalIndexToBoard, coof; 
        public boolean dead, factOfMoove, headHasBeenDestroyed;
	public String sign;
	protected boolean senseTo_sail = true;
	protected ArrayList<int[]> arrList_damage = new ArrayList<int[]>();

	public void setSign(String sign){
		this.sign = sign;
	}

	public void sailUp(){	
		coordMakeSense(1,-1);
		sailCoord(1,-1);
	}
	public void sailDown(){
                coordMakeSense(1,1);
		sailCoord(1,1);
	}
	public void sailLeft(){
		coordMakeSense(0,-1);
		sailCoord(0,-1);
	}
	public void sailRight(){
		coordMakeSense(0,1);
		sailCoord(0,1);
	}

	protected void deleteCell(int x, int y){
		Field.field[y - 1][x - 1] = "_";
	}
	
	public void createShip(){
		for(int i = 0; i < decks; i++){
			Field.field[ x_y_coord[i][1] - 1 ][ x_y_coord[i][0] - 1 ] = sign;		
		}
		for(int i = 0; i < arrList_damage.size(); i++){
				Field.field[ arrList_damage.get(i)[1] - 1][ arrList_damage.get(i)[0] - 1 ] = "X";
		}
	}

	protected void coordMakeSense(int x_OR_y, int one){
		for(int i = 0; i < decks; i++){
			if(x_y_coord[i][x_OR_y] + one <= 0 | x_y_coord[i][x_OR_y] + one > SeaWarsChess.d){
				senseTo_sail = false;
			}else if(Field.field[ x_y_coord[i][1] - 1 + one*x_OR_y][ x_y_coord[i][0] - x_OR_y*one - 1 + one] != "_" & 
				Field.field[ x_y_coord[i][1] - 1 + one*x_OR_y][ x_y_coord[i][0] - x_OR_y*one - 1 + one] != sign & 
				Field.field[ x_y_coord[i][1] - 1 + one*x_OR_y][ x_y_coord[i][0] - x_OR_y*one - 1 + one] != "X" & 
				Field.field[ x_y_coord[i][1] - 1 + one*x_OR_y][ x_y_coord[i][0] - x_OR_y*one - 1 + one] != "-"){
				senseTo_sail = false;
			}else if(Field.field[x_y_coord[i][1] - 1 + one*x_OR_y][ x_y_coord[i][0] - x_OR_y*one - 1 + one] == "X" &
						 !doYouBit(x_y_coord[i][1] + one*x_OR_y, x_y_coord[i][0] - x_OR_y*one + one)){
				senseTo_sail = false;
			}		
		}
	}
	
	protected void sailCoord(int x_OR_y, int one){
		if(senseTo_sail){
			for(int i = 0; i < decks; i++){
				deleteCell(x_y_coord[i][0],x_y_coord[i][1]);
				x_y_coord[i][x_OR_y] += one;		
			}
			for(int i = 0; i < arrList_damage.size(); i++){
				arrList_damage.get(i)[x_OR_y] += one;
			}
			createShip();
			factOfMoove = true;
		}else{factOfMoove = false;}
		senseTo_sail = true;
	}
	
	public void shoot(int x, int y){
		Field.field[ x - 1 ][ y - 1 ] = "o";
	}

	public int coofOfBoarding(ArrayList<Ship> myShips, ArrayList<Ship> rivalShips){
		int youCanBoardThisShip = 0;
		if(boarding >= rivalShips.get(rivalIndexToBoard).boarding ){
			if(boarding == rivalShips.get(rivalIndexToBoard).boarding ){
				youCanBoardThisShip = SeaWarsChess.getRandomInt(coof,100);
			}else{
				youCanBoardThisShip = 100;
			}
		}
		return youCanBoardThisShip;
	}

	public void boarding(ArrayList<Ship> myShips, ArrayList<Ship> rivalShips){
		int youCanBoardThisShip = coofOfBoarding(myShips, rivalShips);
		if(youCanBoardThisShip > 80){
			myShips.add(rivalShips.get(rivalIndexToBoard));
			SeaWarsChess.terminateOne(rivalShips, rivalIndexToBoard);
			try{
				if(Integer.parseInt( myShips.get(0).sign) != -1){
					myShips.get(myShips.size()-1).setSign( Integer.toString(myShips.size()) );
				}
			}catch(NumberFormatException e){
				if(myShips.get(0).sign == "P"){
					myShips.get(myShips.size()-1).setSign( "P" );
				}else{
					myShips.get(myShips.size()-1).setSign( SeaWarsChess.villianSigns[myShips.size()-1] );
				}
			}
			myShips.get(myShips.size()-1).createShip();
			
			if(rivalIndexToBoard == 0){
				headHasBeenDestroyed = true;
			}
		}
	}

	public void getDamage(){
		for(int i = 0; i < decks; i++){
			if(Field.field[ x_y_coord[i][1] - 1 ][ x_y_coord[i][0] - 1 ] == "o"){
//System.out.println("get damage");
				Field.field[ x_y_coord[i][1] - 1 ][ x_y_coord[i][0] - 1 ] = "X";
				
				damage++;
				view = (int)Math.ceil((double)(decks - 1)/2) + view - (int)Math.ceil((double)(decks)/2);
				range -= 1;
				if(boarding != 1){
					boarding -= 1;
				}
				SeaWarsChess.factOfDamage = true;
				arrList_damage.add(new int[]{x_y_coord[i][0],x_y_coord[i][1]});
//System.out.println("damage ="+damage+" decks = "+decks);
				if(damage == decks){
//System.out.println("dead");
					dead = true;	
					for(int j = 0; j < decks; j++){
						Field.field[ x_y_coord[j][1] - 1 ][ x_y_coord[j][0] - 1 ] = "_";
					}
				}
			}
		}
	}
	public void isItMissed(int x, int y){
		if(!SeaWarsChess.factOfDamage){
			Field.field[x - 1][y - 1] = "_";
		}
	}

	public boolean doYouBit(int x, int y){
		boolean bit = false;
		for(int i = 0; i < decks; i++){	
			if(x_y_coord[i][1] == x & x_y_coord[i][0] == y){
				bit = true;
				break;
			}
		}
		return bit;
	}

	public boolean rangeIsEnough(int x,int y){	
		return oportunityIsEnough(x,y,range);
	}

	public boolean doYouSeeThisCell(int x,int y){	
		return oportunityIsEnough(x,y,view);
	}

	public boolean canUBoard(ArrayList<Ship> rivalShips){	
		boolean uCanBoard = false;
		for(int i = 0; i < x_y_coord.length; i++){
			for(int j = -1; j < 2; j++){
				for(int k = -1; k < 2; k++){
					if(checkingCell(x_y_coord[i][1] + j, x_y_coord[i][0] +k, rivalShips) ){
						uCanBoard = true;
						break;
					}
				}
				if(uCanBoard){break;}
			}
			if(uCanBoard){break;}
		}
		return uCanBoard;
	}

	private boolean checkingCell(int x, int y, ArrayList<Ship> rivalShips){
		boolean uCanBoard = false;
		if(x*y != 0 & x<9 & y<9){
			for(int i=0; i<rivalShips.size(); i++){
				if(rivalShips.get(i).doYouBit(x,y)){
					uCanBoard = true;
					rivalIndexToBoard = i;
					break;
				}
			}
		}
		return 	uCanBoard;
	}

	public boolean oportunityIsEnough(int x,int y,int opportunity){	
		boolean enough = false;
		for(int i = 0; i < x_y_coord.length; i++){
			int xLength = Math.abs(x_y_coord[i][1]-x);
			int yLength = Math.abs(x_y_coord[i][0]-y);
			if(xLength <= opportunity & yLength <= opportunity){
				enough = true;
				break;
			}
		}
		return enough;
	}
}