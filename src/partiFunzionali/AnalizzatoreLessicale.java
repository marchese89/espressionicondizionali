package partiFunzionali;

public class AnalizzatoreLessicale {
	
	private String linea = null;
	private int i = 0;
	private int numeroCorrente = 0;
	private String idCorrente = null;
	
	private int posizionePrecedente = 0;
	private boolean primaChiamata = true;
	
	public AnalizzatoreLessicale(String s){
		linea = s;
	}
	
	public void indietro(){
		i = posizionePrecedente;
	}
	
	public Simbolo prossimoSimbolo(){
		
		Simbolo simbolo = Simbolo.CHAR_INVALIDO; 
        if(!primaChiamata){//per poter tornare indietro una volta col cursore...
			posizionePrecedente = i;
		}
		if (linea != null && i < linea.length()) {
			while (i < linea.length() && 
					(linea.charAt(i) == ' ' || linea.charAt(i)=='\n' || linea.charAt(i)=='\t'))//TODO
				i++; // salta spazi
			if (i < linea.length()) {
				if (linea.charAt(i) == '(') {
					simbolo = Simbolo.OPAR;
					i++;
				} else if (linea.charAt(i) == ')') {
					simbolo = Simbolo.CPAR;
					i++;
				} else if((i+2)<linea.length() && 
						linea.substring(i,i+3).equalsIgnoreCase("and")){
					
					simbolo = Simbolo.AND;
					i = i+3;
				}else if((i+1)<linea.length() && linea.substring(i,i+2).equals("&&")){
					simbolo = Simbolo.AND;
					i = i+2;
				}else if ((i+1)<linea.length() && linea.substring(i,i+2).equalsIgnoreCase("or")){
					simbolo = Simbolo.OR;
					i = i+2;
				}
				else if((i+1)<linea.length() && linea.substring(i,i+2).equals("||")){
					simbolo = Simbolo.OR;
					i = i+2;
				}
				else if (linea.charAt(i)=='!' && (i+1)<linea.length() && linea.charAt(i+1)!= '='){
					simbolo = Simbolo.NOT;
				}
				else if((i+2)<linea.length() && linea.substring(i, i+3).equalsIgnoreCase("not")){
					simbolo = Simbolo.NOT;
					i = i+3;
				}else if(linea.charAt(i)=='['){
					simbolo = Simbolo.OPAR1;
					i++;
				}else if(linea.charAt(i)==']'){
					simbolo = Simbolo.CPAR1;
					i++;
				}else if((i+1)<linea.length() && linea.substring(i,i+2).equals("==")){
					simbolo = Simbolo.EQ;
					i = i+2;
				}else if((i+1)<linea.length() && linea.substring(i,i+2).equals("!=")){
					simbolo = Simbolo.NEQ;
					i = i+2;
				}else if((i+1)<linea.length() && linea.charAt(i)=='>' && linea.charAt(i+1)!='='){
					simbolo = Simbolo.GT;
					i++;
				}else if((i+1)<linea.length() && linea.substring(i,i+2)==">="){
					simbolo = Simbolo.GE;
					i = i+2;
				}else if ((i+1)<linea.length() && linea.charAt(i)=='<' && linea.charAt(i+1)!='='){
					simbolo = Simbolo.LT;
					i++;
				}else if((i+1)<linea.length() && linea.substring(i,i+2).equals("<=")){
					simbolo = Simbolo.LE;
					i = i+2;
				}else if(linea.charAt(i)=='^'){
					simbolo = Simbolo.POWER;
					i++;
				}else if(linea.charAt(i)=='/'){
					simbolo = Simbolo.DIV;
					i++;
				}else if(linea.charAt(i)=='%'){
					simbolo = Simbolo.REM;
					i++;
				}else if(linea.charAt(i)=='*'){
					simbolo = Simbolo.MULT;
					i++;
				}else if(linea.charAt(i)=='-'){
					simbolo = Simbolo.MINUS;
					i++;
				}else if(linea.charAt(i)=='+'){
					simbolo = Simbolo.PLUS;
					i++;
				}else if(Character.isLetter(linea.charAt(i))){//trovato un ID
					int j = i;
					while (j < linea.length()
							&& (Character.isLetter(linea.charAt(j))||
									Character.isDigit(linea.charAt(j))))
						j++;
					idCorrente = linea.substring(i, j);
					simbolo = Simbolo.ID;
					i = j;
				}else if(Character.isDigit(linea.charAt(i))){
					int j = i;
					while (j < linea.length() && Character.isDigit(linea.charAt(j)))
						j++;
					numeroCorrente = Integer.parseInt(linea.substring(i, j));
					simbolo = Simbolo.NUM;
					i = j;
				}
				
			}
		}
		primaChiamata = false;
		
		return simbolo;
	}
	public String getID(){
		return idCorrente;
	}
	public int getNumber(){
		return numeroCorrente;
	}
	public boolean hasSymbol(){
		return i<linea.length();
	}

	public static void main(String[]args){
		
	    String s = "(+a<=+b && +c>+1) || +a==+4";
	    AnalizzatoreLessicale al = new AnalizzatoreLessicale(s);
	    
	    while(al.hasSymbol()){
	    	Simbolo si = al.prossimoSimbolo();
	    	System.out.println(si);
	    
	    }
   

	}
}
