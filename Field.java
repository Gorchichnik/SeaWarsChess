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
                        	System.out.print(" ["+field[x][y]+"] "); 
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