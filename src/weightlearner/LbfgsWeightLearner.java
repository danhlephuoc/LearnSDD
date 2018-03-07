package weightlearner;

import data.Data;
import learnsdd.Model;
import swig.SWIGTYPE_p_sdd_manager_t;
import swig.SWIGTYPE_p_sdd_node_t;
import utilities.ArrayUtils;

import java.util.ArrayList;

public class LbfgsWeightLearner {
	
	private double l1 = 0.95;
	
	public LbfgsWeightLearner(double l1) {
		this.l1 = l1;
	}
	
	public LbfgsWeightLearner() {
		this(0.95);
		
	}


	public ArrayList<Double> learn(Model model) {
		ArrayList<Double> weights = model.getWeights();
		double[] weightsArray = ArrayUtils.listDoubleToArray(weights);
		SWIGTYPE_p_sdd_node_t sdd = model.getTheory().getSdd().getPointer();
		SWIGTYPE_p_sdd_manager_t mgr = model.getTheory().getSdd().getManager().getPointer();
		Data trainData = model.getTrainingData();
		
		LbfgsWeightLearnerJNI.learnWeights(sdd, mgr, model.getNbOfFeatures(), trainData.getCounts(),  trainData.getNbInstances(), weightsArray, l1);
		return ArrayUtils.arrayDoubleToList(weightsArray);
	}

	
}