package simulator;

import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class decision {
	public static void main(String[] args) {
		// Prepare dataset from file

		try {
			DataSource source = new DataSource("mushroom.arff");
			Instances dataset = source.getDataSet();
			// definir atributo target
			dataset.setClassIndex(dataset.numAttributes() - 1);

			// Generated model
			J48 classifier = new J48();
			classifier.buildClassifier(dataset);
			// Visualize decision tree
			Visualizer v = new Visualizer();
			v.start(classifier);

			// cross validation test
			Evaluation eval = new Evaluation(dataset);
			eval.crossValidateModel(classifier, dataset, 10, new Random(1));
			System.out.println(eval.toSummaryString("Results\n ", false));
			System.out.println(eval.toMatrixString());
			System.out.println(classifier.toString());

			// Test a new instance
			NewInstances ni = new NewInstances(dataset);
			String[] values1 = { "anise" , "brown" , "bell"  };
			String[] values2 = { "fishy" , "buff", "convex" };
			
			ni.addInstance(values1);
			ni.addInstance(values2);
			
			Instances test_dt = ni.getDataset();
			System.out.println("ActualClass \t PredictedClass");
			
			for (int i = 0; i < test_dt.numInstances(); i++) {
				Instance inst = test_dt.instance(i);
				String actual = inst.stringValue(inst.numAttributes() - 1);
				
				double predict = classifier.classifyInstance(inst);
				String pred = test_dt.classAttribute().value((int) (predict));
				
				System.out.println(actual + " \t " + pred);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
