
public class AnalisaToken {

	private String[] direcoes = {"FRENTE", "ESQUERDA", "DIREITA", "TRAS"};
	private String[] ordem = {"ENTAO","APOS"};
	
	public void analisa(String comandos[]){
		
		for(int i=0; i<comandos.length; i++){
			
		}
		
	}
	
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
		//chama o comando para caminhar
		
	}
	
	public void caminha(){
		
		//modifica o mapa
		//matriz
		//0,0 no centro
	
	}

}
