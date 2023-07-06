package interfacciaGrafica;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import componentiEspressione.Cond;
import eccezioni.BadFileException;
import eccezioni.BadTypeException;
import eccezioni.SintaxErrorException;

import partiFunzionali.AnalizzatoreLessicale;
import partiFunzionali.EC_BuilderImpl;
import partiFunzionali.EC_VisitaPostFissa;
import partiFunzionali.EC_VisitaSimmetrica;

public class EC_GUI extends JFrame{
	
	private JTextArea input;
	private JTextArea output;
	private JButton valuta;
	private JButton simmetrica;
	private JButton postfissa;
	private JButton fileVariabili;
	
	private AnalizzatoreLessicale a; 
	private EC_BuilderImpl e;
	private String percorsoFile;
	
	public EC_GUI(){
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension d = kit.getScreenSize();
		setLocation(d.width / 4, d.height / 4);
		setSize(d.width / 2, (d.height / 3)+20);
		
		Ascoltatore al = new Ascoltatore();
		JPanel p = new JPanel();
		input = new JTextArea(8,56);
		JScrollPane j = new JScrollPane(input);
		output = new JTextArea(3,45);
		output.setEditable(false);
		JScrollPane j2 = new JScrollPane(output);
		valuta = new JButton("Valuta");
		valuta.addActionListener(al);
		simmetrica = new JButton("Simmetrica");
		simmetrica.addActionListener(al);
		postfissa = new JButton("PostFissa");
		postfissa.addActionListener(al);
		fileVariabili = new JButton("File Variabili");
		fileVariabili.addActionListener(al);
		p.add(j);
		p.add(j2);
		p.add(valuta);
		p.add(simmetrica);
		p.add(postfissa);
		p.add(fileVariabili);
		add(p);
		setTitle("Valutatore Espressioni Condizionali");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	class Ascoltatore implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			if(arg0.getSource() == valuta){
			String s = input.getText();
			a = new AnalizzatoreLessicale(s);
			EC_BuilderImpl e = new EC_BuilderImpl(a);
			try{
			Cond c = e.build();
			if(percorsoFile != null)
			e.SettaTuttiId(percorsoFile);			
			output.setText(new Boolean(c.interpreta()).toString());
			}catch(SintaxErrorException e1){
				JOptionPane.showMessageDialog
				(null, "Errore di sintassi",null,JOptionPane.ERROR_MESSAGE);
				
				return;
			}
			catch(BadFileException e1){
				JOptionPane.showMessageDialog
				(null, "Errore nel file",null,JOptionPane.ERROR_MESSAGE);
				
				return;
			}
		    catch(BadTypeException e1){
		    	JOptionPane.showMessageDialog
				(null, "Errore nel tipo",null,JOptionPane.ERROR_MESSAGE);
				
				return;
		    }
			}
			if(arg0.getSource() == simmetrica){
				output.setText("");
				String s = input.getText();
				a = new AnalizzatoreLessicale(s);
				e = new EC_BuilderImpl(a);
				Cond c = e.build();
				EC_VisitaSimmetrica visita = new EC_VisitaSimmetrica(output);
				c.accept(visita);
			}
			if(arg0.getSource() == postfissa){
				output.setText("");
				String s = input.getText();
				a = new AnalizzatoreLessicale(s);
				e = new EC_BuilderImpl(a);
				Cond c = e.build();
				EC_VisitaPostFissa visita = new EC_VisitaPostFissa(output);
				c.accept(visita);
			}
			if(arg0.getSource() == fileVariabili){
								
				JFileChooser j = new JFileChooser();
				int i = j.showDialog(null, "Seleziona");
				if(i == JFileChooser.APPROVE_OPTION){
				String path = j.getSelectedFile().getAbsolutePath();
				StringBuilder sb = new StringBuilder();
				char [] p = path.toCharArray();
				for(int k = 0; k< p.length;k++){
					if( p[k]!= '\\')
							sb.append(p[k]);
					else
						sb.append("\\\\");
				}
				percorsoFile = sb.toString();
				JOptionPane.showMessageDialog(null, "Variabili Settate");
				}
			}
		}
		
	}
    public static void main(String[]args){
    	EC_GUI e = new EC_GUI();
    }
}
