import java.security.InvalidParameterException;
import java.util.Arrays;

public class AnalisaToken {

	private int x = 0;
	private int y = 0;
	private int distanciaCaminhada = 0;
	private char direcao = 'N';
	private String retorno = "";
	
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
		String entreParenteses = "";
		int indexFormato = 0;
		int indexAnterior = 0;
		int [] indexAuxiliar = new int[1];
		int nulos = 0;
		
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
	
	public String[] eliminaApos(String [] comandos){
		
		int i = 1; //inicia em 1 para poder apontar para o anterior
		int indexApos = 0;
		String anterior = comandos[i-1];
		String posterior = comandos[i+1];
		String auxiliar = "";	
		
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
			i++;
		}
		return comandos;
	}
	
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
			mudaDirecao(comandos[contador]);
			
			switch(getDirecao()){
			case 'N':
				eixo = 'y';
				contador++; //conta para pegar o próximo que é o valor
				converteValorEcaminha(comandos[contador], false, eixo);
				break;
			case 'L':
				eixo = 'x';
				contador++; //conta para pegar o próximo que é o valor
				converteValorEcaminha(comandos[contador], false, eixo);
				break;
			case 'O':
				eixo = 'x';
				contador++; //conta para pegar o próximo que é o valor
				converteValorEcaminha(comandos[contador], true, eixo);
				break;
			case 'S':
				eixo = 'y';
				contador++; //conta para pegar o próximo que é o valor
				converteValorEcaminha(comandos[contador], true, eixo);
				break;
			}
			
			contador++; //passa para o próximo comando
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
		if(auxiliar[0]==null || auxiliar[0].isEmpty()){
			for(int i=0; i<auxiliar.length-1; i++){
				auxiliar[i] = auxiliar[i+1];
			}
			auxiliar[auxiliar.length-1] = null;
		}
		
		auxiliar = eliminaNulos(auxiliar, contaNulos(auxiliar));
		
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
	
	public void mudaDirecao(String movimento){
		giraSonda(movimento, criaDirecoes(getDirecao()));
	}
	
	public char[] criaDirecoes(char direcaoAtual){
		
		char [] d = null;
		switch(direcaoAtual){
		case 'N':
			char [] direcoesN = {'N', 'O', 'L', 'S'};
			d = direcoesN;
			break;
		case 'O':
			char [] direcoesO = {'O', 'S', 'N', 'L'};
			d = direcoesO;
			break;
		case 'L':
			char [] direcoesL = {'L', 'N', 'S', 'O'};
			d = direcoesL;
			break;
		case 'S':
			char [] direcoesS = {'S', 'L', 'O', 'N'};
			d = direcoesS;
			break;
		}

		return d;
		
	}
	
	public void giraSonda(String movimento, char[] direcoes){
		switch (movimento){
		case "FRENTE":
			setDirecao(direcoes[0]);
			break;
		case "ESQUERDA":
			setDirecao(direcoes[1]);
			break;
		case "DIREITA":
			setDirecao(direcoes[2]);
			break;
		case "TRAS":
			setDirecao(direcoes[3]);
			break;
		}
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
	
	public char getDirecao() {
		return direcao;
	}

	public void setDirecao(char direcao) {
		this.direcao = direcao;
	}
	
	public String getRetorno() {
		return retorno;
	}

	public void setRetorno(String retorno) {
		this.retorno = retorno;
	}

}
