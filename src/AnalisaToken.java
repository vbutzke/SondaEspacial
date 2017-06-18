import java.security.InvalidParameterException;
import java.util.Arrays;

public class AnalisaToken {

	private int x = 0;
	private int y = 0;
	private int distanciaCaminhada = 0;

/*Precedência
 * Problemas: ENTAO, APOS e ()
 * Trata parênteses como se fosse um comando só no primeiro momento
 * - Primeiro passo é eliminar todos os APOS substituindo-os por ENTAO e mudando os comandos de lugar
 * - Segundo passo é eliminar os parênteses substituindo todo ")" por ENTAO caso o próximo token não seja um ENTAO
*/
	
//---------------------- Token ----------------------
	public String[] precedencia(String [] comandos){
		
		comandos = formataComandos(comandos);
		
		if(comandos.length>2){
			comandos = eliminaApos(comandos);
			comandos = eliminaParenteses(comandos);
			comandos = eliminaEntao(comandos);
		}
		
		comandos = eliminaEspacosEmBranco(comandos);
		
		return comandos;
		
	}
	
	public String[] formataComandos(String[] comandos){
		
		String [] comandosFormatados = new String [comandos.length];
		int indexFormato = 0;
		String entreParenteses = "";
		int indexAnterior = 0;
		int [] indexAuxiliar = new int[1];
		int nulos = 0;
		
		//procura token que contenha "(". Se não contém, adiciona em comandos formatados. Se tem, concatena até ")" e adiciona em comandos formatados
		for(int i=0; i<comandos.length; i++){
			indexAuxiliar[0] = i;
			indexAnterior = i;
			if(comandos[i].contains("(")){
				entreParenteses = concatena(Arrays.copyOfRange(comandos, i, comandos.length), indexAuxiliar);
				comandos[indexAnterior] = entreParenteses;
			}
			
			comandosFormatados[indexFormato] = comandos[indexAnterior];
			indexFormato++;
			i=indexAuxiliar[0];
		}
		
		nulos = contaNulos(comandosFormatados);
		comandosFormatados = eliminaNulos(comandosFormatados, nulos);
		return comandosFormatados;
	}
	
//deixa o que tem nos parênteses como se fosse um comando só
	public String[] eliminaApos(String [] comandos){
		int i = 1;
		String anterior = comandos[i-1];
		String posterior = comandos[i+1];
		String auxiliar = "";
		
		int indexApos = 0;
		
		while(i<comandos.length){
			anterior = comandos[i-1];
			if(i<comandos.length-1){
				posterior = comandos[i+1];
			}
			
			if(comandos[i].contains("APOS")){
				indexApos = comandos[i].indexOf("APOS");
				if(comandos[i].contains("(")){
					auxiliar = comandos[i].substring(indexApos+4)+"ENTAO"+comandos[i].substring(0, indexApos);
					comandos[i] = auxiliar;
					auxiliar = "";
				}else{
					auxiliar = anterior;
					anterior = comandos[i-2];
					comandos[i-2] = posterior;
					comandos[i-1] = comandos[i+2];
					comandos[i+1] = anterior;
					comandos[i+2] = auxiliar;
					comandos[i] = "ENTAO";
				}
				
			}
//avança duas posições pois passa pelo posterior, como A APOS B ENTAO C anterior = A, atual = APOS, posterior = B, atual+2 = ENTAO, anterior = B, posterior = C
			i++;
		}
			
		return comandos;
	}
	
//remove parênteses somente porque todos os comandos já estão com o ENTAO antes, então a precedência dos parênteses não existe mais
	public String [] eliminaParenteses(String [] comandos){
			
		for(int i=0; i<comandos.length; i++){
			comandos[i] = comandos[i].replace("(", "");
			comandos[i] = comandos[i].replace(")", "");
		}
			
		return comandos;
		
	}
	
	public String [] eliminaEntao(String [] comandos){
		
		for(int i=0; i<comandos.length; i++){
			comandos[i] = comandos[i].replaceAll("ENTAO", "");
		}
		
		return comandos;
	}
	
	public String[] eliminaEspacosEmBranco(String[] comandos){
		
		int brancos = contaEspacosEmBranco(comandos);
		String [] comandosSemEspacos = new String[(comandos.length)*2-brancos];
		int indexComandosSemEspacos = 0;
		
		for(int i=0; i<comandos.length; i++){
			if(!comandos[i].equals(" ") && !comandos[i].equals("")){
				String[] auxiliar = eliminaSeparadoresAuxiliares(comandos[i]);		
				for(int j=0; j<auxiliar.length; j++){
					comandosSemEspacos[indexComandosSemEspacos] = auxiliar[j];
					indexComandosSemEspacos++;
				}
			}
		}
		comandosSemEspacos = eliminaNulos(comandosSemEspacos, contaNulos(comandosSemEspacos));
		return comandosSemEspacos;
	
	}

//---------------------- Mapa ----------------------
	public void moveSonda(String comandos[]){
		
		int contador = 0;
		char eixo;
		
		while(contador<comandos.length){
			
			System.out.print("Instrução: "+comandos[contador]+" ");
			
			switch (comandos[contador]){
			case "FRENTE":
				eixo = 'y';
				contador++; //conta para pegar o próximo que é o valor
				converteValorEcaminha(comandos[contador], false, eixo);
				break;
				
			case "ESQUERDA":
				eixo = 'x';
				contador++;
				converteValorEcaminha(comandos[contador], true, eixo);
				break;
				
			case "DIREITA":
				eixo = 'x';
				contador++;
				converteValorEcaminha(comandos[contador], false, eixo);
				break;
				
			case "TRAS":
				eixo = 'y';
				contador++;
				converteValorEcaminha(comandos[contador], true, eixo);
				break;
				
			}
			//passa para o próximo comando
			contador++;
		}
		
	}
	
	public void converteValorEcaminha(String valor, boolean negativo, char eixo){
		
		int passos = 0;
		
		try{
			passos = Integer.parseInt(valor);
			if(negativo){
				passos = (-1)*passos;
			}
			
			System.out.println(passos);
			
			caminha(eixo, passos);
		}catch(NumberFormatException e){
			System.out.println("Não foi possível mover a Sonda Espacial. É necessário um valor numérico após o comando de movimento.");
		}catch(InvalidParameterException e){
			throw e;
		}
	}
	
	public void caminha(char eixo, int valor) throws InvalidParameterException{
		
		//valor já vem convertido para negativo quando necessário
		if(eixo == 'x'){
			setX(getX()+valor);
		}else if(eixo == 'y'){
			setY(getY()+valor);
		}else{
			throw new InvalidParameterException("Não foi possível mudar de posição. Eixo de movimentação inválido");
		}
		
		setDistanciaCaminhada(getDistanciaCaminhada()+Math.abs(valor));
		
		System.out.println("x: "+getX()+", y: "+getY()+", distância: "+getDistanciaCaminhada());
		
	}

//---------------------- Auxiliares ----------------------	
	public int contaNulos(String[] comandos){
		
		int nulos = 0;
		
		for(int i=0; i<comandos.length; i++){
			if(comandos[i] == null){
				nulos++;
			}
		}
		
		return nulos;
	}
	
	public int contaEspacosEmBranco(String[] comandos){
		
		int brancos = 0;
		
		for(int i=0; i<comandos.length; i++){
			if(comandos[i].equals(" ") || comandos[i].equals("")){
				brancos++;
			}
		}
		
		return brancos;
	}
	
	public String[] eliminaSeparadoresAuxiliares(String comandos){
		
		String[] auxiliar = comandos.split("/");
		return auxiliar;
		
	}
	
	public String [] eliminaNulos(String[] comandos, int nulos){

		String[] comandosSemNulos = new String [comandos.length-nulos];
		
		for(int i=0; i<comandosSemNulos.length; i++){
			comandosSemNulos[i] = comandos[i];
		}
		
		return comandosSemNulos;
	}
	
	public String concatena(String[] tokens, int [] index){
		
		int contador = 0;
		String concatenado = "";
		
		while(!tokens[contador].contains(")")){
			concatenado = concatenado+"/"+tokens[contador];
			contador++;
		}
		
		concatenado = concatenado+"/"+tokens[contador];
		index[0]+=contador;
		return concatenado;
		
	}
	
//---------------------- Gets e Sets ----------------------
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getDistanciaCaminhada() {
		return distanciaCaminhada;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setDistanciaCaminhada(int distanciaCaminhada) {
		this.distanciaCaminhada = distanciaCaminhada;
	}

}
