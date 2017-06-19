# SondaEspacial
Analisador sintático para sonda espacial - TGB

Objetivo:
	Este projeto tem como objetivo simular o caminhamento de uma Sonda Espacial por um campo bidimensional através de comandos de estrutura pré-definida (descrita abaixo).

Estrutura:
	A estrutura de movimentação da Sonda Espacial é determinada através da seguinte definição.
	comando -> comando ENTAO comando | comando APOS comando | (comando) | basico
	basico -> FRENTE n | ESQUERDA n | DIREITA n | TRAS n
	
	c1 ENTAO c2 -> executa c1 e depois executa c2
	c1 APOS c2 -> executa c2 e depois executa c1
	FRENTE n -> anda para frente "n" posições
	ESQUERDA n -> vira 90º para a esquerda e anda "n" posições
	DIREITA n -> vira 90º para a direita e anda "n" posições
	TRAS n -> vira 180º e anda "n" posições
	
	Os parênteses não influenciam em sua ordem de execução externa, porém permitem que determinado conteúdo seja isolado, além de ser tratado como um bloco fixo a ser executado pelo algoritmo, o que faz com que as permutações de execução sofram algumas alterações. Exemplo:
	c1 APOS (c2 ENTAO c3) -> executa c2, depois executa c3 e depois executa c1
	c1 APOS (c2 APOS c3) -> executa c3, depois executa c2, depois executa c1
	c1 ENTAO (c2 ENTAO c3) -> executa c1, depois executa c2, depois executa c3
	c1 ENTAO (c2 APOS c3) -> executa c1, depois executa c3, depois executa c2
	

Funcionamento:
	Geral:
		O algoritmo lê um arquivo que possui uma sequência de comandos de movimentação para a sonda espacial conforme estrutura especificada anteriormente. Linha a linha, é feito um parsing dos espaços e armazenado o resultado em um array de Strings, que sofre alteração através de uma operação que identifica a precedência dos comandos. Essa operação retorna um array de Strings com os mesmos comandos passados como parâmetro na ordem correta em que os mesmos devem ser executados. Após isso, este array de comandos é interpretado por uma operação de caminhamento, que interpreta os comandos conforme especificação acima e realiza o caminhamento da sonda em um "mapa", mostrando a cada iteração, um log que contém a instrução realizada, a posição (x,y) atual da sonda e a distância percorrida até o momento.
	
	A. Operação de Precedência:
		A operação de precedência é dividida em cinco etapas.
			1. Formatação de comandos: etapa em que ocorre a junção dos comandos que se encontram entre parênteses para que sejam tratados como um comando único para fins de rearranjo das operações
			2. Eliminação do comando APOS: etapa que consiste em rearranjar os comandos, de forma a modificar o comando APOS para o comando ENTAO e inverter a ordem dos comandos desta sentença, tornando a execução destes comandos sequencial.
			3. Eliminação dos parênteses: Etapa que consiste em eliminar os parênteses da sentença uma vez que as sentenças já estão estruturadas de forma a seguirem sua precedência de execução sequencialmente, fazendo com que os comandos que antes se encontravam entre parênteses voltem a ser tratados como termos individuais.
			4. Eliminação do comando ENTAO: etapa que consiste em eliminar o comando ENTAO, uma vez que as sentenças já estão estruturadas de forma a seguirem sua precedência de execução sequencialmente.
			5. Eliminação de Espaços em Branco: etapa que consiste em buscar por células vazias dentro do array e eliminá-las.

	B. Operação de Movimentação da Sonda
		A operação de movimentação da sonda é dividida em duas partes:
			1. Determinação de direção: etapa que consiste em identificar qual a direção para qual a sonda se encontra virada (Norte, Sul, Leste ou Oeste) e para qual direção a sonda deve se virar para realizar o movimento, qual eixo do mapa esta movimentação afeta e para qual sentido.
			2. Movimentação da Sonda: etapa que consiste interpretar o comando de movimento e em alterar a posição (x,y) em que a sonda se encontra, calculando a distância percorrida até o momento e produzindo um log de movimentação.


Melhorias:
	- Tratamento de exceção para comandos fora do padrão especificado
	- Mecanismo de input para digitação dos comandos
	- Interface gráfica para input
	- Correção de comandos em tempo real
	- Implementação de mapa para a Sonda
	- Interface gráfica para visualização da movimentação da Sonda
	- Suporte a Threads
	- Expansão de espaço bidimensional para tridimensional
	- Simulação de Sonda com movimento real (elíptico)
	- Implementação de algortimo de colisão
	- Geração de múltiplas Sondas simultaneamente
	