package simulator;

import java.util.List;
import java.util.Random;
import net.sourceforge.jFuzzyLogic.FIS;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Decision {

	public static void main(String[] args) {

		// decision();
		try {
			// Prepare dataset from file
			DataSource source = new DataSource("mushroom.arff");
			Instances dataset = source.getDataSet();
			dataset.setClassIndex(dataset.numAttributes() - 1);
			
			// Generated model
			J48 classifier = new J48();
			classifier.buildClassifier(dataset);

			Simulator simulador = new Simulator();
			
			while (true) {
				
				Double action = 1.0;
				//Guardar atributos do cogumelo
				String[] atributes = simulador.getMushroomAttributes();
				
				//CREATE INSTANCE
				NewInstances instance = new NewInstances(dataset);
				
				if (atributes != null) {
					instance.addInstance(atributes);
					Instances nome = instance.getDataset();
					action = classifier.classifyInstance(nome.instance(0));
					simulador.setAction(Action.ordinal(action);
				}else {
					action = 1.0;
					return;
				}
					
				//get the distances 
				simulador.getDistanceC()
				simulador.getDistanceR()
				simulador.getDistanceL()
				
				System.out.println(atributes + "\t" + instance);

				// load FCL FILE
				// Prepare dataset from file
				// Load from 'FCL' file
				String fileName = "codumelos.fcl";
				FIS fis = FIS.load(fileName, true);
				// Error while loading?
				if (fis == null) {
					System.err.println("Can't load file: '" + fileName + "'");
					return;
				}

				// Set inputs
				fis.setVariable("distance", 8);
//				fis.setVariable("danger", 5);
				fis.setVariable("sensor", 90);
				// Evaluate
				fis.evaluate();
				// Print ruleSet
				System.out.println("Actione: " + fis.getVariable("rotation").defuzzify());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * private static void fuzzao() { // Prepare dataset from file // Load from
	 * 'FCL' file String fileName = "codumelos.fcl"; FIS fis = FIS.load(fileName,
	 * true); // Error while loading? if (fis == null) {
	 * System.err.println("Can't load file: '" + fileName + "'"); return; }
	 * 
	 * // Set inputs fis.setVariable("distance", 8); // fis.setVariable("danger",
	 * 5); fis.setVariable("sensor", 90); // Evaluate fis.evaluate(); // Print
	 * ruleSet System.out.println("Actione: " +
	 * fis.getVariable("rotation").defuzzify()); }
	 */
	/*
	 * private static void decision() { try { DataSource source = new
	 * DataSource("mushroom.arff"); Instances dataset = source.getDataSet(); //
	 * definir atributo target dataset.setClassIndex(dataset.numAttributes() - 1);
	 * 
	 * // Generated model J48 classifier = new J48();
	 * classifier.buildClassifier(dataset); // Visualize decision tree Visualizer v
	 * = new Visualizer(); v.start(classifier);
	 * 
	 * // cross validation test Evaluation eval = new Evaluation(dataset);
	 * eval.crossValidateModel(classifier, dataset, 10, new Random(1));
	 * System.out.println(eval.toSummaryString("Results\n ", false));
	 * System.out.println(eval.toMatrixString());
	 * System.out.println(classifier.toString());
	 * 
	 * // Test a new instance NewInstances ni = new NewInstances(dataset); String[]
	 * values1 = { "anise", "brown", "bell" }; String[] values2 = { "fishy", "buff",
	 * "convex" };
	 * 
	 * ni.addInstance(values1); ni.addInstance(values2);
	 * 
	 * Instances test_dt = ni.getDataset();
	 * System.out.println("ActualClass \t PredictedClass");
	 * 
	 * for (int i = 0; i < test_dt.numInstances(); i++) { Instance inst =
	 * test_dt.instance(i); String actual = inst.stringValue(inst.numAttributes() -
	 * 1);
	 * 
	 * double predict = classifier.classifyInstance(inst); String pred =
	 * test_dt.classAttribute().value((int) (predict));
	 * 
	 * System.out.println(actual + " \t " + pred); } } catch (Exception e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } }
	 */

}
