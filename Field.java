import java.io.IOException;

public class Field {     
        static String[][] field;
	private int dimention;

	public Field(int dimention){
		field = new String[dimention][dimention];
		this.dimention = dimention;
		for(int i = 0; i<dimention; i++){
			for(int j = 0; j<dimention; j++){
				field[i][j] =  "_";
			}
		}
	}
      
        public void viewField() throws IOException, InterruptedException {
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		System.out.println(" ");
	        System.out.println(" ");
                System.out.println("               THE SEA WARS CHESS "); 
		System.out.println(" ");

		lineNum();
                for(int x=0; x<dimention; x++){
			System.out.print("\r\n");
			System.out.print("   ");
			System.out.print(x+1);
			System.out.print(" ");
                        for(int y=0; y<dimention; y++){   
				int o = 1;
				for(int k = 0; k < SeaWarsChess.arrMyShip.size(); k++){
					if(SeaWarsChess.arrMyShip.get(k).doYouSeeThisCell(x+1,y+1) | field[x][y] == "X"){
						o *= 0;
					}
				}
				if(o == 0){                  
                        		System.out.print(" ["+field[x][y]+"] "); 
				}else{
					System.out.print(" [-] "); 
				}
                        }
			System.out.print(" ");
			System.out.print(x+1);
                        System.out.print("\r\n");
              	 }   
		 lineNum();
	}

	void lineNum(){
		 System.out.print("\r\n");
		 System.out.print("     ");
		 
                 for(int y=0; y<dimention; y++){                     
                       System.out.print("  "+(y+1)+"  "); 
                 }
                 System.out.print("\r\n");
	}
}