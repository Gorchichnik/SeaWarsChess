import java.util.ArrayList;

public class Ai{
	String[] arrOfWays = {"go left", "go right", "go up", "go down", "shoot"};
	int numOfShip;
	int[] x_y = new int[2];

	public void computerTurn(){
		while(!SeaWarsChess.factOfComputerMoove){
			numOfShip = SeaWarsChess.getRandomInt(0,SeaWarsChess.arrComShip.size() - 1);
			int numOfWay = SeaWarsChess.getRandomInt(3, 4);
			if(numOfWay == 3){
				numOfWay = SeaWarsChess.getRandomInt(0, 3);
			}
			SeaWarsChess.whatToDo(arrOfWays[numOfWay], SeaWarsChess.arrComShip, SeaWarsChess.arrMyShip, numOfShip, false);
		}
	}

	public int getX(){
		return x_y[0];
	}

	public int getY(){
		return x_y[1];
	}

	public void createRandomXY(){
		boolean weCanShoot = false;
//System.out.println("THE Begining size ="+SeaWarsChess.arrComShip.size());
		do{
			boolean findOut = false;
			for(int i=0; i<SeaWarsChess.d; i++){
				for(int j=0; j<SeaWarsChess.d; j++){
					for(int k=0; k<SeaWarsChess.arrComShip.size(); k++){
						if(SeaWarsChess.arrComShip.get(k).doYouSeeThisCell(i+1,j+1)){
							if(SeaWarsChess.field.field[ i ][ j ] != "X" & SeaWarsChess.field.field[ i ][ j ] != "_" & SeaWarsChess.field.field[ i ][ j ] != "-" & chekingAll(i+1,j+1,SeaWarsChess.arrComShip, numOfShip)){
								x_y[0] = i+1;
								x_y[1] = j+1;
								findOut = true;
								weCanShoot = true;
							}
						}
						if(findOut){break;}
					}
					if(findOut){break;}
				}
				if(findOut){break;}
			}
			if(!findOut){
				x_y[0] = SeaWarsChess.getRandomInt(1,SeaWarsChess.d);
				x_y[1] = SeaWarsChess.getRandomInt(1,SeaWarsChess.d);
				if(SeaWarsChess.field.field[ x_y[0] - 1 ][ x_y[1] - 1 ] != "X"){
					weCanShoot = chekingAll(x_y[0],x_y[1],SeaWarsChess.arrComShip, numOfShip);
				}
			}	
			System.out.println("weCAnShoot = " + Boolean.toString(weCanShoot));
		}while(!weCanShoot);
//System.out.println("THe end");
	}

	public boolean chekingAll(int x, int y, ArrayList<Ship> ships, int shipNumber){
		boolean weCanShoot = false;
		if(ships.get(shipNumber).rangeIsEnough(x,y)){
			for(int i = 0; i < ships.size(); i++){
				boolean doYouBitYourself = ships.get(i).doYouBit(x,y);
				if(doYouBitYourself){
//System.out.println("0");
					weCanShoot = false;
					break;
				}else{
//System.out.println("1");
					weCanShoot = true;
				}
			}
		}else{
			weCanShoot = false;
		}
		return weCanShoot;
	}
}