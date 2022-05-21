package simulator;

import net.sourceforge.jFuzzyLogic.FIS;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MushroomCatcher {

	public static void main(String[] args) throws Exception {

		decider();

	}

	public static void decider() {


		try {
			DataSource source = new DataSource("mushroom.arff");
//			System.out.println(source);

			Instances dataset = source.getDataSet();
//			System.out.println(dataset);

			String fileName = "codumelos.fcl";
			FIS fis = FIS.load(fileName, true);

			dataset.setClassIndex(dataset.numAttributes() - 1);

			//Generated model
			J48 classifier = new J48();
			classifier.buildClassifier(dataset);

			//cross validation test
			Evaluation eval = new Evaluation(dataset);
			eval.crossValidateModel(classifier, dataset, 10, new Random(1));
			System.out.println(eval.toSummaryString("Results\n ", false));
			System.out.println(eval.toMatrixString());
			System.out.println(classifier.toString());

			Simulator simulador = new Simulator();

			while(true) {

				String[] attributes = simulador.getMushroomAttributes();

				NewInstances instance = new NewInstances(dataset) ;

				if (attributes != null) {
					System.out.println("attribu " + attributes[0] + " " + attributes[1] + " " + attributes[2]);
					instance.addInstance(attributes);
					Instances nome = instance.getDataset();

					int action = ((int) classifier.classifyInstance(nome.instance(0)));
//					System.out.println("action: " + action);
//					int toma = fuzzao(action);
//					System.out.println("action TOMA : " + toma);

					fis.setVariable("danger", action);
					int toma = ((int) fis.getVariable("action").defuzzify());
					System.out.println("action TOMA : " + toma);

					switch (toma) {
						case 0 :
							simulador.setAction(Action.PICK_UP);
							System.out.println("Action is PICK_UP");
							simulador.step();

						case 1:
							simulador.setAction(Action.NO_ACTION);
							System.out.println("Action is NO_ACTION");
							simulador.step();
							Double sensorMovement = fuzzy(simulador.getDistanceR(),simulador.getDistanceL(), simulador.getDistanceC());
							simulador.setRobotAngle(sensorMovement);

						case 2 :
							simulador.setAction(Action.DESTROY);
							System.out.println("Action is DESTROY");
							simulador.step();
					}

				}else{

					simulador.setAction(Action.NO_ACTION);//
					simulador.step();
					Double sensorMovement = fuzzy(simulador.getDistanceR(),simulador.getDistanceL(), simulador.getDistanceC());
					simulador.setRobotAngle(sensorMovement);

				}

//				Double sensorMovement = fuzzy(simulador.getDistanceR(),simulador.getDistanceL(), simulador.getDistanceC());
//				simulador.setRobotAngle(sensorMovement);
//


//				System.out.println("distance Right " + simulador.getDistanceR() + " distance left " + simulador.getDistanceL() + " distance center " + simulador.getDistanceC());
//				System.out.println(sensorMovement);
//				System.out.println(attributes + "---" + instance);
			}


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Double fuzzy(Double sensorRight, Double sensorLeft, Double sensorCenter) {
		String fileName = "codumelos.fcl";
		FIS fis = FIS.load(fileName, true);

		if (fis == null) {
			System.err.println("Can't load file: '" + fileName + "'");
		}

		fis.setVariable("sensor_left", sensorLeft);
		fis.setVariable("sensor_right", sensorRight);
		fis.setVariable("sensor_center", sensorCenter);

		// Evaluate
		fis.evaluate();

		Double rotation = fis.getVariable("rotation").defuzzify();


		// Print ruleSet
//		System.out.println("Rotation is : " + rotation);


		System.out.println();

		return rotation;
	}

	public static int fuzzao(int danger) {
		String fileName = "codumelos.fcl";
		FIS fis = FIS.load(fileName, true);

		if (fis == null) {
			System.err.println("Can't load file: '" + fileName + "'");
		}

		fis.setVariable("danger", danger);

		int action = ((int) fis.getVariable("action").defuzzify());
		System.out.println("Action is: " + action);

		return action;
	}
}