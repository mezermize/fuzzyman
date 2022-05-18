package simulator;

import net.sourceforge.jFuzzyLogic.FIS;



public class codumelios {
    public static void main(String[] args) throws Exception {
        // Load from 'FCL' file
        String fileName = "codumelos.fcl";
        FIS fis = FIS.load(fileName,true);
        // Error while loading?
        if( fis == null ) { 
            System.err.println("Can't load file: '" 
                                   + fileName + "'");
            return;
        }

        // Set inputs
        fis.setVariable("distance", 1);
        fis.setVariable("danger", 5);

        // Evaluate
        fis.evaluate();

        // Print ruleSet
        System.out.println("Actione: " + fis.getVariable("action").defuzzify());
    }
}