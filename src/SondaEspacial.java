import java.io.BufferedReader;
import java.io.FileReader;


public class SondaEspacial {

	public static void main(String[] args) {
		
		AnalisaToken analisador = new AnalisaToken();
		String actualLine = "";
		String [] comandos;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("foo.in"));
	         
	        while ((actualLine = br.readLine()) != null) {
	        	//comandos = analisador.verificaPrecedencia(actualLine.split(" "));
	        	
	        	//retornar frase somente com os comandos, tipo direita 10, suprimir os entao e etc
	        	comandos = actualLine.split(" "); //teste
	        	analisador.moveSonda(comandos);

	        }       
	         br.close();
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
		
	}

}
