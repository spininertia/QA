package edu.cmu.nlp.qc;

import edu.stanford.nlp.trees.CollinsHeadFinder;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;
import edu.stanford.nlp.trees.TreebankLanguagePack;

public class ModCollinsHeadFinderLK extends CollinsHeadFinder {

  public ModCollinsHeadFinderLK() {
    this(new PennTreebankLanguagePack());
  }

  public ModCollinsHeadFinderLK(TreebankLanguagePack tlp) {
    super(tlp);

    // just throw away the nonTerminalInfo created by the superclass
    // ModCollins extends Collins just so it can use the same postOperationFix.
    nonTerminalInfo.clear();

    // This version from Collins' diss (1999: 236-238)
    // NNS, NN is actually sensible (money, etc.)!
    // QP early isn't; should prefer JJR NN RB
    // remove ADVP; it just shouldn't be there.
    // NOT DONE: if two JJ, should take right one (e.g. South Korean)

    nonTerminalInfo.put("ADJP", new String[][]{{"left", "NNS", "NN", "$", "QP", "JJ", "VBN", "VBG", "ADJP", "JJR", "NP", "JJS", "DT", "FW", "RBR", "RBS", "SBAR", "RB"}});
    nonTerminalInfo.put("ADVP", new String[][]{{"right", "RB", "RBR", "RBS", "FW", "ADVP", "TO", "CD", "JJR", "JJ", "IN", "NP", "JJS", "NN"}});
    nonTerminalInfo.put("CONJP", new String[][]{{"right", "CC", "RB", "IN"}});
    nonTerminalInfo.put("FRAG", new String[][]{{"right"}}); // crap
    nonTerminalInfo.put("INTJ", new String[][]{{"left"}});
    nonTerminalInfo.put("LST", new String[][]{{"right", "LS", ":"}});
    nonTerminalInfo.put("NAC", new String[][]{{"left", "NN", "NNS", "NNP", "NNPS", "NP", "NAC", "EX", "$", "CD", "QP", "PRP", "VBG", "JJ", "JJS", "JJR", "ADJP", "FW"}});
    nonTerminalInfo.put("NX", new String[][]{{"right", "NP", "NX"}});
    nonTerminalInfo.put("PP", new String[][]{{"right", "IN", "TO", "VBG", "VBN", "RP", "FW"}, {"right", "PP"}});

    // should prefer JJ? (PP (JJ such) (IN as) (NP (NN crocidolite)))

    nonTerminalInfo.put("PRN", new String[][]{{"left", "VP", "NP", "PP", "S", "SINV", "SBAR", "ADJP", "ADVP", "INTJ", "WHNP", "NAC", "VBP", "JJ", "NN", "NNP"}});
    nonTerminalInfo.put("PRT", new String[][]{{"right", "RP"}});
    // add '#' for pounds!!
    nonTerminalInfo.put("QP", new String[][]{{"left", "$", "IN", "NNS", "NN", "JJ", "CD", "PDT", "DT", "RB", "NCD", "QP", "JJR", "JJS"}});
    nonTerminalInfo.put("RRC", new String[][]{{"right", "VP", "NP", "ADVP", "ADJP", "PP"}});

    // delete IN -- go for main part of sentence; add FRAG

    nonTerminalInfo.put("S", new String[][]{{"left", "TO", "VP", "S", "FRAG", "SBAR", "ADJP", "UCP", "NP"}});
    nonTerminalInfo.put("SBAR", new String[][]{{"left", "WHNP", "WHPP", "WHADVP", "WHADJP", "IN", "DT", "S", "SQ", "SINV", "SBAR", "FRAG"}});
    nonTerminalInfo.put("SBARQ", new String[][]{{"left", "NN", "NNS", "NP", "NNP", "WHNP", "SQ", "S", "SINV", "SBARQ", "FRAG"}});
    nonTerminalInfo.put("SINV", new String[][]{{"left", "NP", "VBZ", "VBD", "VBP", "VB", "MD", "VP", "S", "SINV", "ADJP"}});
    nonTerminalInfo.put("SQ", new String[][]{{"left", "NN", "NNS", "NP", "NNP", "VBZ", "VBD", "VBP", "VB", "MD", "VP", "SQ"}});
    nonTerminalInfo.put("UCP", new String[][]{{"right"}});
    // below is weird!! Make 2 lists, one for good and one for bad heads??
    nonTerminalInfo.put("VP", new String[][]{{"left", "TO", "NN", "NNS", "NP", "NNP", "VBD", "VBN", "MD", "VBZ", "VB", "VBG", "VBP", "VP", "ADJP", "JJ"}});
    nonTerminalInfo.put("WHADJP", new String[][]{{"left", "CC", "WRB", "JJ", "ADJP"}});
    nonTerminalInfo.put("WHADVP", new String[][]{{"right", "CC", "WRB"}});
    nonTerminalInfo.put("WHNP", new String[][]{{"left", "NN", "NNS", "NP", "NNP"}});
    nonTerminalInfo.put("WHPP", new String[][]{{"right", "IN", "TO", "FW"}});
    nonTerminalInfo.put("X", new String[][]{{"right", "S", "VP", "ADJP", "NP", "SBAR", "PP", "X"}});
    nonTerminalInfo.put("NP", new String[][]{{"rightdis", "NN", "NNP", "NNPS", "NNS", "NX", "POS", "JJR"}, {"left", "NP", "PRP"}, {"rightdis", "$", "ADJP", "PRN"}, {"right", "CD"}, {"rightdis", "JJ", "JJS", "RB", "QP", "DT", "WDT", "RBR", "ADVP"}});
    nonTerminalInfo.put("POSSP", new String[][]{{"right", "POS"}});

	/* HJT: Adding the following to deal with oddly formed data in
	 (for example) the Brown corpus */
    nonTerminalInfo.put("ROOT", new String[][]{{"left", "S", "SQ", "SINV", "SBAR", "FRAG"}});
    nonTerminalInfo.put("TYPO", new String[][]{{"left", "NN", "NP", "NNP", "NNPS", "TO",
      "VBD", "VBN", "MD", "VBZ", "VB", "VBG", "VBP", "VP", "ADJP", "FRAG"}}); // for Brown (Roger)
    nonTerminalInfo.put("ADV", new String[][]{{"right", "RB", "RBR", "RBS", "FW",
      "ADVP", "TO", "CD", "JJR", "JJ", "IN", "NP", "JJS", "NN"}});
    nonTerminalInfo.put("EDITED", new String[][] {{"left"}});  // crap rule for Switchboard (if don't delete EDITED nodes)
    nonTerminalInfo.put("XS", new String[][] {{"right", "IN"}}); // rule for new structure in QP

  }

  private static final long serialVersionUID = -5870387458902637256L;

}