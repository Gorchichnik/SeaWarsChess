import java.util.ArrayList;

public class Ai{
	String str = "lrudsb";
	char[] arrOfWays = str.toCharArray();
	static int numOfShip, numOfWay;
	static int[] x_y = new int[2];
	static boolean findRivalShip = false; 

	public void piratesTurn(){
		findRivalShip = false;
		numOfShip = 0;
		shootingIfICan(SeaWarsChess.pirates, SeaWarsChess.arrMyShip);
		if(findRivalShip & SeaWarsChess.pirates.get(numOfShip).canUBoard(SeaWarsChess.arrMyShip) & SeaWarsChess.pirates.get(numOfShip).boarding >= SeaWarsChess.arrMyShip.get(SeaWarsChess.pirates.get(numOfShip).rivalIndexToBoard).boarding ){
//System.out.println("pirates boarding my ship");
			SeaWarsChess.whatToDo(arrOfWays[5], SeaWarsChess.pirates, SeaWarsChess.arrMyShip, numOfShip, false);
		}else{
			shootingIfICan(SeaWarsChess.pirates, SeaWarsChess.arrComShip);
			if(findRivalShip & SeaWarsChess.pirates.get(numOfShip).canUBoard(SeaWarsChess.arrComShip) & SeaWarsChess.pirates.get(numOfShip).boarding >= SeaWarsChess.arrComShip.get(SeaWarsChess.pirates.get(numOfShip).rivalIndexToBoard).boarding ){
//System.out.println("pirates boarding Com ship");
				SeaWarsChess.whatToDo(arrOfWays[5], SeaWarsChess.pirates, SeaWarsChess.arrComShip, numOfShip, false);
			}else{	
//System.out.println("pirates mooving");
				numOfShip = SeaWarsChess.getRandomInt(0,SeaWarsChess.pirates.size() - 1);
				for(int i = 0; i < 3; i++){
					SeaWarsChess.factOfPiratesMoove = false;
					do{
						numOfWay = SeaWarsChess.getRandomInt(0, 3);
						SeaWarsChess.whatToDo(arrOfWays[numOfWay], SeaWarsChess.pirates, SeaWarsChess.arrMyShip, numOfShip, false);
					}while(!SeaWarsChess.factOfPiratesMoove);
				}
			}
		}
	}

	public void computerTurn(){
		numOfShip = 0;
		findRivalShip = false;
		boolean itIsPirate = false;
		shootingIfICan(SeaWarsChess.arrComShip, SeaWarsChess.arrMyShip);
		if(!findRivalShip){
			itIsPirate = true;
		}
		shootingIfICan(SeaWarsChess.arrComShip, SeaWarsChess.pirates);
		if(!findRivalShip){
			itIsPirate = false;
		}
		while(!SeaWarsChess.factOfComputerMoove){
			if(!findRivalShip){
//System.out.println(" there is no viewed cells to shoot");
				numOfWay = SeaWarsChess.getRandomInt(3, 4);
				numOfShip = SeaWarsChess.getRandomInt(0,SeaWarsChess.arrComShip.size() - 1);	
				if(numOfWay == 4){
					createRandomXY();
					if(!SeaWarsChess.arrComShip.get(numOfShip).doYouSeeThisCell(x_y[0],x_y[1]) ){
						if( doYouBit(x_y[0],x_y[1],SeaWarsChess.arrMyShip) ){
							SeaWarsChess.whatToDo(arrOfWays[numOfWay], SeaWarsChess.arrComShip, SeaWarsChess.arrMyShip, numOfShip, false);
						}else{
							SeaWarsChess.whatToDo(arrOfWays[numOfWay], SeaWarsChess.arrComShip, SeaWarsChess.pirates, numOfShip, false);
						}
					}
				}else{
					numOfWay = SeaWarsChess.getRandomInt(0, 3);
					SeaWarsChess.whatToDo(arrOfWays[numOfWay], SeaWarsChess.arrComShip, SeaWarsChess.arrMyShip, numOfShip, false);
				}		
				
			}else if(SeaWarsChess.arrComShip.get(numOfShip).canUBoard(SeaWarsChess.pirates) & SeaWarsChess.arrComShip.get(numOfShip).boarding>SeaWarsChess.pirates.get(SeaWarsChess.arrComShip.get(numOfShip).rivalIndexToBoard).boarding  ){
				SeaWarsChess.whatToDo(arrOfWays[5], SeaWarsChess.arrComShip, SeaWarsChess.pirates, numOfShip, false);
//System.out.println(" Com board P ship");
			}else if(itIsPirate ){
				SeaWarsChess.whatToDo(arrOfWays[4], SeaWarsChess.arrComShip, SeaWarsChess.pirates, numOfShip, false);
				findRivalShip = false;
			}else if(SeaWarsChess.arrComShip.get(numOfShip).canUBoard(SeaWarsChess.arrMyShip) & SeaWarsChess.arrComShip.get(numOfShip).boarding>=SeaWarsChess.arrMyShip.get(SeaWarsChess.arrComShip.get(numOfShip).rivalIndexToBoard).boarding){
//System.out.println(" Com board Me ");
				SeaWarsChess.whatToDo(arrOfWays[5], SeaWarsChess.arrComShip, SeaWarsChess.arrMyShip, numOfShip, false);
			}else{
//System.out.println("shoot viewed me");
				SeaWarsChess.whatToDo(arrOfWays[4], SeaWarsChess.arrComShip, SeaWarsChess.arrMyShip, numOfShip, false);
				findRivalShip = false;
			}
//System.out.println(" ship No = " + numOfShip);
		}
	}

	public int getX(){
		return x_y[0];
	}

	public int getY(){
		return x_y[1];
	}

	private void shootingIfICan(ArrayList<Ship> arrComShip, ArrayList<Ship> arrMyShip){
		boolean findOut = false;
		for(int i=0; i<SeaWarsChess.d; i++){
			for(int j=0; j<SeaWarsChess.d; j++){
				for(int k=0; k<arrComShip.size(); k++){
//System.out.println("check viewed. x = "+(i+1)+" y = " +(j+1)+" Index of ship = "+ k);
					if(arrComShip.get(k).doYouSeeThisCell(i+1,j+1)){
//System.out.println("see this");
						for(int h=0; h<arrComShip.size(); h++){
//System.out.println("who can shoot = "+h);
//System.out.println("range is enough? "+Boolean.toString( arrComShip.get(h).rangeIsEnough(i+1,j+1) ) );
							if(SeaWarsChess.field.field[ i ][ j ] != "X" & arrComShip.get(h).rangeIsEnough(i+1,j+1) & !doYouBit(i+1,j+1,arrComShip) & doYouBit(i+1,j+1,arrMyShip) ) {
//System.out.println("it can shoot viewed ship");								
								x_y[0] = i+1;
								x_y[1] = j+1;
								findRivalShip = true;
								numOfShip = h;
								numOfWay = 4;
								break;
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
//System.out.println("THE Begining size ="+SeaWarsChess.arrComShip.size());
		do{
			x_y[0] = SeaWarsChess.getRandomInt(1,SeaWarsChess.d);
			x_y[1] = SeaWarsChess.getRandomInt(1,SeaWarsChess.d);
			if( SeaWarsChess.field.field[ x_y[0] - 1 ][ x_y[1] - 1 ] != "X" & SeaWarsChess.arrComShip.get(numOfShip).rangeIsEnough(x_y[0],x_y[1]) & !doYouBit(x_y[0],x_y[1],SeaWarsChess.arrComShip)){
				weCanShoot = true;
			}	
//System.out.println("weCAnShoot = " + Boolean.toString(weCanShoot) +" and ship No = " + numOfShip);
		}while(!weCanShoot);
//System.out.println("THe end");
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
//System.out.println("bit it? "+Boolean.toString(bit));
		return bit;
	}
}