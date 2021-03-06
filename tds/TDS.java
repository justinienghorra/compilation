package plic.tds;

public class TDS {
	
	private static TDS instance = new TDS();
	protected int tailleZoneDesVariables; // Pour savoir ou se trouve la variable la plus haute dans la "pile" S7
	protected TDSLocale bloc; // bloc courant	
	protected int dernierBloc;
	protected boolean changerBloc;
	protected boolean classeExistante;
	
	public static TDS getInstance() {
		return instance;
	}

	public boolean ajouter(String statut, String type, String idf, int n) {
		Entree e;
		if(type.equals("const")) e = new EntreeConst(idf);
		else if(type.equals("classe")) e = new EntreeClass(idf);
		else e = new EntreeVar(idf);
		
		if (bloc.getTable().containsKey(e) && type.equals(bloc.identifier(e).getType())){
			System.out.println("ERREUR SEMANTIQUE : ligne " + n + " : Double déclaration de la variable " + e.getNom());
			return false;
		}
		Symbole s = new Symbole(statut, type, tailleZoneDesVariables);
		bloc.ajouter(e, s);
		tailleZoneDesVariables += 4;
		return true;
	}
	
	public boolean ajouter(String type, String idf, int n) {
		Entree e = type.equals("const") ? new EntreeConst(idf) : new EntreeVar(idf); // idf = (condition) ? valeur si vrai : valeur si faux;
		if (bloc.getTable().containsKey(e) && type.equals(bloc.identifier(e).getType())){
			System.out.println("ERREUR SEMANTIQUE : ligne " + n + " : Double déclaration de l'identifiant " + e.getNom());
			return false;
		}
		Symbole s = type.equals("const") ? new Symbole("publique", type, tailleZoneDesVariables)
										 : new Symbole("privee", type, tailleZoneDesVariables);
		tailleZoneDesVariables += 4;
		bloc.ajouter(e, s);
		return true;
	}
	
	public boolean verifConst(String idf, int n) {
		String nomClasse = this.getBloc().getPere().getNomBloc();
		if(!nomClasse.equals(idf)) {
			System.out.println("ERREUR SEMANTIQUE : ligne " + n + " : Le constructeur doit avoir le meme nom que la classe correspondante");
			return false;
		}
		return true;
	}
	
	public boolean verifierVar(String e) {
		EntreeVar cle = new EntreeVar(e);
		return getBloc().verifierExistence(cle);
	}
	
	public boolean verifierClasse(String e) {
		EntreeClass cle = new EntreeClass(e);
		return getBloc().verifierExistence(cle);
	}
	
	public Symbole identifier(String e){
		EntreeVar cle = new EntreeVar(e);
		return getBloc().identifier(cle);
	}
	
	public int getTailleZoneDesVariables() {
		return this.tailleZoneDesVariables;
	}
	
	public void entreeBloc() {
		this.dernierBloc += 1;
		this.bloc = getBloc().ajouterFils(getDernierBloc());
		this.setChangementBloc(false);
		this.setClasseExistante(true);
	}
	
	public void entreeBloc(int numBloc){
		this.bloc = getBloc().getFils(numBloc);
	}
	
	public void sortieBloc() {
		this.bloc = getBloc().getPere();
	}
	
	public String toMipsEntree() {
		return getBloc().toMipsEntree();
	}
	
	public String toMipsSortie() {
		return getBloc().toMipsSortie();
	}
	
	public TDSLocale getBloc() {
		return this.bloc;
	}
	
	public void setNomBloc(String nom){
		getBloc().setNomBloc(nom);
	}
	
	public String getNomBloc(){
		return getBloc().getNomBloc();
	}
	
	public int getNumBloc() {
		return getBloc().getNumBloc();
	}
	
	public int getDernierBloc() {
		return this.dernierBloc;
	}
	
	public boolean getChangementBloc() {
		return this.changerBloc;
	}
	
	public void setChangementBloc(boolean changement) {
		this.changerBloc = changement;
	}
	
	public boolean isClasseExistante() {
		return this.classeExistante;
	}
	
	public void setClasseExistante(boolean changement) {
		this.classeExistante = changement;
	}

	private TDS() {
		this.tailleZoneDesVariables = 12;
		this.bloc = new TDSLocale(0);
		this.dernierBloc = 0;
		this.classeExistante = false;
	}

}
