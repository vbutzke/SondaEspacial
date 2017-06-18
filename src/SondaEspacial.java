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
	        	comandos = analisador.precedencia(actualLine.split(" "));
	        	analisador.moveSonda(comandos);
	        }       
	        br.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
