import java.io.BufferedReader;
import java.io.FileReader;


public class SondaEspacial {

	public static void main(String[] args) {
		
		AnalisaToken analisador = new AnalisaToken();
		String actualLine = "";
		String [] frase;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("foo.in"));
	         
	        while ((actualLine = br.readLine()) != null) {
	        	frase = analisador.verificaPrecedencia(actualLine.split(" "));
	        	 //analisador.analisa(frase);

	        }       
	         br.close();
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
		
	}

}
