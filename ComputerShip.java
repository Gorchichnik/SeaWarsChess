public class ComputerShip extends Ship{

	public ComputerShip(int decks, int point, String sign){
		this.range = decks;
		this.view = (int)Math.ceil((double)decks/2);
		this.boarding = 1;
		this.coof = 10;
		this.decks = decks;
		if(sign == "1"){
			this.sign = "K";
		}else{
			this.sign = sign;
		}
		x_y_coord = new int[decks][2];
		for(int i = 0; i < decks; i++){
			x_y_coord[i][0] = point;
			x_y_coord[i][1] = i+1;
		}
		super.createShip();
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