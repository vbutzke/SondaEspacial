import javax.swing.JOptionPane; 

public class SondaEspacial extends javax.swing.JFrame { 

	public SondaEspacial() { 
		initComponents(); 
	}
	private void initComponents() { 
		jLabel1 = new javax.swing.JLabel(); 
		jTextAreaNome = new javax.swing.JTextArea(); 
		jButtonAnalisar = new javax.swing.JButton(); 
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE); 
		jLabel1.setText("Insira os Comandos"); 
		jButtonAnalisar.setText("Analisar"); 
		jButtonAnalisar.addActionListener(new java.awt.event.ActionListener() { public void actionPerformed(java.awt.event.ActionEvent evt) { jButtonAnalisarActionPerformed(evt); } });
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane()); 
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup( layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING) .addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING) .addComponent(jTextAreaNome, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE).addComponent(jLabel1) .addComponent(jButtonAnalisar) .addGroup(layout.createSequentialGroup().addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)) .addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false) .addGroup(javax.swing.GroupLayout.Alignment.LEADING,layout.createSequentialGroup() .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)) .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup() .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED) )) .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2,javax.swing.GroupLayout.PREFERRED_SIZE))) .addGap(37, 37, 37)) );
		layout.setVerticalGroup( layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jLabel1).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED) .addComponent(jTextAreaNome, javax.swing.GroupLayout.DEFAULT_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE) .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED) .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)) .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE))	.addGap(16, 16, 16) .addComponent(jButtonAnalisar) .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED) .addGap(35, 35, 35)) ); 
		pack(); 
	}
	private void jButtonAnalisarActionPerformed(java.awt.event.ActionEvent evt) {
		analisar(); 
	}  
	
	public void analisar(){
		AnalisaToken analisador = new AnalisaToken();
		String actualLine = "";
		String [] comandos;
			
		try {
	        
	        String codigo = jTextAreaNome.getText();
	        String linhas[] = codigo.split("\\n");	
	        
	        for(int i =0; i < linhas.length; i++){
				actualLine = linhas[i];
				comandos = analisador.precedencia(actualLine.split(" "));
	        	analisador.moveSonda(comandos);
			}			
	        
	        JOptionPane.showMessageDialog(null,analisador.getRetorno());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {java.awt.EventQueue.invokeLater(new Runnable() { public void run() {new SondaEspacial().setVisible(true); } });}
	private javax.swing.JButton jButtonAnalisar;
	private javax.swing.JLabel jLabel1; 
	private javax.swing.JTextArea jTextAreaNome; 
}
