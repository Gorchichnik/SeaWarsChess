import java.util.ArrayList;

public class Ai{
	String str = "lrudsb";
	char[] arrOfWays = str.toCharArray();
	static int numOfShip, numOfWay;
	static int[] x_y = new int[2];
	static boolean findRivalShip = false; 

	public void computerTurn(){
		while(!SeaWarsChess.factOfComputerMoove){
			shootingIfICan();
			if(!findRivalShip){
				numOfWay = SeaWarsChess.getRandomInt(3, 4);
				numOfShip = SeaWarsChess.getRandomInt(0,SeaWarsChess.arrComShip.size() - 1);	
				if(numOfWay == 4){
					createRandomXY();
				}		
				if(!SeaWarsChess.arrComShip.get(numOfShip).doYouSeeThisCell(x_y[0],x_y[1]) ){
					SeaWarsChess.whatToDo(arrOfWays[numOfWay], SeaWarsChess.arrComShip, SeaWarsChess.arrMyShip, numOfShip, false);
				}
			}else if(SeaWarsChess.arrComShip.get(numOfShip).canUBoard(SeaWarsChess.arrMyShip) & SeaWarsChess.arrComShip.get(numOfShip).boarding>=SeaWarsChess.arrComShip.get(numOfShip).rivalIndexToBoard){
				SeaWarsChess.whatToDo(arrOfWays[5], SeaWarsChess.arrComShip, SeaWarsChess.arrMyShip, numOfShip, false);
			}else{
				SeaWarsChess.whatToDo(arrOfWays[numOfWay], SeaWarsChess.arrComShip, SeaWarsChess.arrMyShip, numOfShip, false);
				findRivalShip = false;
			}
		}
	}

	public int getX(){
		return x_y[0];
	}

	public int getY(){
		return x_y[1];
	}

	private void shootingIfICan(){
		boolean findOut = false;
		for(int i=0; i<SeaWarsChess.d; i++){
			for(int j=0; j<SeaWarsChess.d; j++){
				for(int k=0; k<SeaWarsChess.arrComShip.size(); k++){
					if(SeaWarsChess.arrComShip.get(k).doYouSeeThisCell(i+1,j+1)){
						for(int h=0; h<SeaWarsChess.arrComShip.size(); h++){
							if(SeaWarsChess.field.field[ i ][ j ] != "X" & SeaWarsChess.arrComShip.get(h).rangeIsEnough(i+1,j+1) & !doYouBit(i+1,j+1,SeaWarsChess.arrComShip) & doYouBit(i+1,j+1,SeaWarsChess.arrMyShip) ) {
								x_y[0] = i+1;
								x_y[1] = j+1;
								findRivalShip = true;
								numOfShip = h;
								numOfWay = 4;
							}
						}
					}
					if(findRivalShip){break;}
				}
				if(findRivalShip){break;}
			}
			if(findRivalShip){break;}
		}
	}

	public void createRandomXY(){
		boolean weCanShoot = false;
System.out.println("THE Begining size ="+SeaWarsChess.arrComShip.size());
		do{
			x_y[0] = SeaWarsChess.getRandomInt(1,SeaWarsChess.d);
			x_y[1] = SeaWarsChess.getRandomInt(1,SeaWarsChess.d);
			if( SeaWarsChess.field.field[ x_y[0] - 1 ][ x_y[1] - 1 ] != "X" & SeaWarsChess.arrComShip.get(numOfShip).rangeIsEnough(x_y[0],x_y[1]) & !doYouBit(x_y[0],x_y[1],SeaWarsChess.arrComShip)){
				weCanShoot = true;
			}	
System.out.println("weCAnShoot = " + Boolean.toString(weCanShoot) +" and ship No = " + numOfShip);
		}while(!weCanShoot);
System.out.println("THe end");
	}

	public boolean doYouBit(int x, int y, ArrayList<Ship> ships){
		boolean bit = false;
		for(int i = 0; i < ships.size(); i++){
			boolean doYouBitYourself = ships.get(i).doYouBit(x,y);
			if(doYouBitYourself){
				bit = true;
				break;
			}
		}
		return bit;
	}
}