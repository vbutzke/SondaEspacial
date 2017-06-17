import java.security.InvalidParameterException;


public class AnalisaToken {

	private String[] direcoes = {"FRENTE", "ESQUERDA", "DIREITA", "TRAS"};
	private String[] ordem = {"ENTAO","APOS"};
	private int x = 0;
	private int y = 0;
	private int distanciaCaminhada = 0;

//---------------------- Token ----------------------
	public String [] verificaPrecedencia(String[] token){
		
		int contador = 0;
		String precedente [] = new String [token.length];
		String subsequente[] = new String [token.length];
		String fraseFinal[] = new String [token.length];
		int indexPrec = 0;
		int indexSubs = 0;
		int indexFinal = 0;
		
//fazer fun��o recursiva adicional que inclui essa l�gica com um switch
//Enquanto a frase n�o for totalmente analisada, segue
		while(contador<token.length){
			//Se o token tem um par�nteses, indica preced�ncia, ent�o l� tudo dentro dos par�nteses e adiciona no array de preced�ncia
			if(token[contador].contains("(")){
				while(!token[contador].contains(")")){
					precedente[indexPrec] = token[contador];
					indexPrec++;
					contador++;
				}
				precedente[indexPrec] = token[contador];
				indexPrec++;
			}else{
				//Se o token n�o tem par�nteses, adiciona no array de subsequ�ncia
				subsequente[indexSubs] = token[contador];
				indexSubs++;
			}
			contador++;
		}
		
		formaFraseFinal(fraseFinal, precedente, indexFinal);
		formaFraseFinal(fraseFinal, subsequente, indexFinal);
		
		return fraseFinal;
		
	}
	
	public void formaFraseFinal(String [] fraseFinal, String [] frase, int indexFinal){
		
		int indexFrase = 0;
		
		while(!frase[indexFrase].equals("/0")){
			fraseFinal[indexFinal] = frase[indexFrase];
			indexFrase++;
			indexFinal++;
		}
		
	}
	
	public void interpretaToken(String token){
		
		//verifica se faz sentido a ordem
		
	}

//---------------------- Mapa ----------------------
	public void moveSonda(String comandos[]){
		
		int contador = 0;
		char eixo;
		
		while(contador<comandos.length){
			
			System.out.print("Instru��o: "+comandos[contador]+" ");
			
			switch (comandos[contador]){
			case "FRENTE":
				eixo = 'y';
				contador++; //conta para pegar o pr�ximo que � o valor
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
			//passa para o pr�ximo comando
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
			System.out.println("N�o foi poss�vel mover a Sonda Espacial. � necess�rio um valor num�rico ap�s o comando de movimento.");
		}catch(InvalidParameterException e){
			throw e;
		}
	}
	
	public void caminha(char eixo, int valor) throws InvalidParameterException{
		
		//valor j� vem convertido para negativo quando necess�rio
		if(eixo == 'x'){
			setX(getX()+valor);
		}else if(eixo == 'y'){
			setY(getY()+valor);
		}else{
			throw new InvalidParameterException("N�o foi poss�vel mudar de posi��o. Eixo de movimenta��o inv�lido");
		}
		
		setDistanciaCaminhada(getDistanciaCaminhada()+Math.abs(valor));
		
		System.out.println("x: "+getX()+", y: "+getY()+", dist�ncia: "+getDistanciaCaminhada());
		
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
