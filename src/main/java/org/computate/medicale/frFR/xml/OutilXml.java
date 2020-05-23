package org.computate.medicale.frFR.xml;

/**
 * NomCanonique.enUS: org.computate.medicale.enUS.xml.UtilXml
 * enUS: Static methods for escaping XML. 
 */ 
public class OutilXml {
	
	/**
	 * Var.enUS: escape
	 */
	public static String echapper(String str) {
		String o = str;
		
		if(str != null) { 
			o = o.replace("&", "&amp;").replace("<", "&#60;").replace(">", "&#62;");
		}
		
		return o;
	}
	
	/**
	 * Var.enUS: escapeInApostrophes
	 */
	public static String echapperDansApostrophes(String str) {
		String o = str;
		
		if(str != null) { 
			o = o.replace("\n", " ").replace("\t", " ").replace("\r", "").replace("&", "&amp;").replace("<", "&#60;").replace(">", "&#62;").replace("'", "&apos;");
		}
		
		return o;
	}
	
	/**
	 * Var.enUS: escapeInQuotes
	 */
	public static String echapperDansCitatations(String str) {
		String o = str;
		
		if(str != null) { 
			o = o.replace("\n", " ").replace("\t", " ").replace("\r", "").replace("&", "&amp;").replace("<", "&#60;").replace(">", "&#62;").replace("\"", "&quot;");
		}
		
		return o;
	}
	
	/**
	 * Var.enUS: escapeAddQuotes
	 */
	public static String echapperAjouterCitations(String str) {
		String o = str;
		
		if(str != null) { 
			o = "\"" + o.replace("\n", " ").replace("\t", " ").replace("\r", "").replace("&", "&amp;").replace("<", "&#60;").replace(">", "&#62;").replace("\"", "&quot;") + "\"";
		}
		
		return o;
	}
}
