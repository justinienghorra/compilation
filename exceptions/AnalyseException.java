package plic.exceptions;

/**
 * 10 déc. 2015
 *
 * @author brigitte wrobel-dautcourt
 */

public abstract class AnalyseException extends RuntimeException {
    
    protected AnalyseException(String m) {
    	System.err.println(m);
    }

}
