public class Ai{
	String[] arrOfWays = {"go left", "go right", "go up", "go down", "shoot"};

	public void computerTurn(){
		while(!SeaWarsChess.factOfComputerMoove){
			int numOfShip = SeaWarsChess.getRandomInt(0,SeaWarsChess.arrComShip.size() - 1);
			int numOfWay = SeaWarsChess.getRandomInt(0, 4);
			SeaWarsChess.whatToDo(arrOfWays[numOfWay], SeaWarsChess.arrComShip, SeaWarsChess.arrMyShip, numOfShip, false);
		}
	}
}