public class MyShip extends Ship{

	public MyShip(boolean wduLike_range, boolean armor, boolean speedUp, int decks, String sign){
		this.range = (wduLike_range ? 1 : 0) + decks;
		this.armor = armor;
		this.speed = 1 + (speedUp ? 1 : 0);
		this.decks = decks;
		this.sign = sign;
		x_y_coord = new int[decks][2];
		for(int i = 0; i < decks; i++){
			x_y_coord[i][0] = 4;
			x_y_coord[i][1] = 8 - i;
		}
		super.createShip();
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