package plic.arbre.expression.unaire;

import plic.arbre.expression.Expression;

/**
 * 3 déc. 2015
 *
 * @author brigitte wrobel-dautcourt
 */

public class NonLogique extends Unaire {
	
    public NonLogique(Expression expr) {
        super(expr);
    }

    @Override
    public String operateur() {
        return " non " ;
    }

	@Override
	public String toMIPS() {

		StringBuilder sb = new StringBuilder();
		
		sb.append("# ----- Non Logique ----- \n");
		sb.append("# Ranger exp dans $v0 \n");
		sb.append(expression.toMIPS());
		sb.append("sw $v0, 0($sp) \n");
		sb.append("add $sp, $sp, -4 \n");
		sb.append("# Ranger 1 dans $v0 \n");
		sb.append("li $v0, 1 \n");
		sb.append("add $sp, $sp, 4 \n");
		sb.append("lw $t8, ($sp) \n");
		sb.append("xor $v0, $t8, $v0 \n");
		sb.append("# ---- Fin non logique ---- \n\n");
		
		return sb.toString();
	}

	@Override
	public boolean verifier() {
		boolean valide = super.verifier();
		if (!expression.getType().equals("booleen")) {
			System.err.println("ERREUR SEMANTIQUE : ligne " + expression.getNoLigne() + " : l'expression n'est pas un booléen");
			valide = false;
		}
		return valide;
	}
	
	public String getType() {
		return "booleen";
	}

}
