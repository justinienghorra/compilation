package plic.arbre.expression.binaire;

import plic.arbre.expression.Expression;

/**
 * 3 déc. 2015
 *
 * @author brigitte wrobel-dautcourt
 */

public class Different extends Comparaison {

    public Different(Expression gauche, Expression droite) {
        super(gauche, droite);
    }

    @Override
    public String operateur() {
        return " != ";
    }

	@Override
	public String toMIPS() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("# ----- Différent ----- \n");
		sb.append("# Ranger operande gauche dans $v0 \n");
		sb.append(gauche.toMIPS());
		sb.append("sw $v0, 0($sp) \n");
		sb.append("add $sp, $sp, -4 \n");
		sb.append("# Ranger operande droite dans $v0 \n");
		sb.append(droite.toMIPS());
		sb.append("add $sp, $sp, 4 \n");
		sb.append("lw $t8, ($sp) \n");
		sb.append("sne $v0, $t8, $v0 \n");
		sb.append("# ---- Fin Différent ---- \n\n");
		
		return sb.toString();
	}

	public boolean verifier() {
		boolean valide = super.verifier();
		String g = gauche.getType(), d = droite.getType();
		if (!g.equals(d)) {
			System.err.println("ERREUR SEMANTIQUE : ligne " + droite.getNoLigne() + " : l'expression ne peut pas être comparée");
			valide = false;
		}
		return valide;
	}

	public String getType() {
		return "booleen";
	}
  
}
