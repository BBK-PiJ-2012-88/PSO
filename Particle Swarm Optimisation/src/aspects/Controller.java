import java.io.File;

import loadTesting.LoadTestRunner;

import org.aspectj.lang.Aspects;

import tests.BinaryDeJong1;
import tests.BinaryDeJong2;
import tests.BinaryDeJong3;
import tests.BinaryDeJong4;
import tests.BinaryDeJong5;
import tests.CombinatorialFunction;
import tests.DeJong1;
import tests.DeJong2;
import tests.DeJong3;
import tests.DeJong4;
import tests.DeJong5;
import tests.IOManager;
import swarm.*;

@SuppressWarnings("unused")
public class Controller {
	
	private IOManager ioManager = new IOManager();
	
	private Logger log = Logger.aspectOf();
	
	private String fileLocation  = "/Users/williamhogarth/Documents/ComSci/Project/Results/AspectJResults/data.txt";
	
	private CombinatorialSwarm comb = new CombinatorialSwarm();
	
	private BinarySwarm bin = new BinarySwarm(); 
	
	private IterationHalt halt = new IterationHalt(100);
	
	private VanillaSwarm vanilla = new VanillaSwarm();
	
	private DataAnalyser data = new DataAnalyser();
	
	private File file;
	
	private Function f;
	
	private Neighbourhood neighbourhood = new CuboidLattice();
	
	public static void main(String[] args){
		Controller c = new Controller();
		c.runVanilla();
	}
	
	private void runCombinatorial(){
		comb.setHaltingCriteria(halt);
		comb.setNeighbourhood(neighbourhood);
		comb.setNumberOfParticles(27);
		ioManager.writeTofile("Combinatorial", file);
		f = new CombinatorialFunction("/Users/williamhogarth/Downloads/a280.tsp");
		testFunction(f, "a280");
		f = new CombinatorialFunction("/Users/williamhogarth/Downloads/kroA150.tsp");
		testFunction(f, "kroA150.tsp");
		f = new CombinatorialFunction("/Users/williamhogarth/Downloads/eil51.tsp");
		testFunction(f, "eil51.tsp");
		f = new CombinatorialFunction("/Users/williamhogarth/Downloads/eil101.tsp");
		testFunction(f, "eil101.tsp");
		f = new CombinatorialFunction("/Users/williamhogarth/Downloads/kroA100.tsp");
		testFunction(f, "kroA100.tsp");
		ioManager.writeTofile("Genetic", file);
		f = new CombinatorialFunction("/Users/williamhogarth/Downloads/a280.tsp");
		testGeneticFunction(f, "a280");
		f = new CombinatorialFunction("/Users/williamhogarth/Downloads/kroA150.tsp");
		testGeneticFunction(f, "kroA150.tsp");
		f = new CombinatorialFunction("/Users/williamhogarth/Downloads/eil51.tsp");
		testGeneticFunction(f, "eil51.tsp");
		f = new CombinatorialFunction("/Users/williamhogarth/Downloads/eil101.tsp");
		testGeneticFunction(f, "eil101.tsp");
		f = new CombinatorialFunction("/Users/williamhogarth/Downloads/kroA100.tsp");
		testGeneticFunction(f, "kroA100.tsp");
		LoadTestRunner.main(null);
	}

	public void runVanilla() {
		file = ioManager.createNewFile(fileLocation);
		vanilla.setHaltingCriteria(halt);
		vanilla.setNeighbourhood(neighbourhood);
		vanilla.setNumberOfParticles(27);
		ioManager.writeTofile("Vanilla", file);
		f = new DeJong1(10);
		testFunction(5.12, -5.12, f);
		f = new DeJong2(10);
		testFunction(2.048, -2.048, f);
		f = new DeJong3();
		testFunction(5.12, -5.12, f);
		f = new DeJong4(10);
		testFunction(1.28, -1.28, f);
		f = new DeJong5();
		testFunction(65.536, -65.536, f);
		runBinary();
	}

	private void runBinary() {
		bin.setHaltingCriteria(halt);
		bin.setNeighbourhood(neighbourhood);
		bin.setNumberOfParticles(27);
		ioManager.writeTofile("binary", file);
		f = new BinaryDeJong1();
		testBinaryFunction(f, 5.12, -5.12);
		f = new BinaryDeJong2();
		testBinaryFunction(f, 2.048, -2.048);
		f = new BinaryDeJong3();
		testBinaryFunction(f, 5.12, -5.12);
		f = new BinaryDeJong4();
		testBinaryFunction(f, 5.12, -5.12);
		f = new BinaryDeJong5();
		testBinaryFunction(f, 65.536, -65.536);
		ioManager.writeTofile("Genetic", file);
		f = new BinaryDeJong1();
		genTestFunction(f, 5.12, -5.12);
		f = new BinaryDeJong2();
		genTestFunction(f, 2.048, -2.048);
		f = new BinaryDeJong3();
		genTestFunction(f, 5.12, -5.12);
		f = new BinaryDeJong4();
		genTestFunction(f, 1.28, -1.28);
		f = new BinaryDeJong5();
		genTestFunction(f, 65.536, -65.536);
		runCombinatorial();
	}

	private void genTestFunction(Function f2, double d, double e) {
		log.clearLog();
		data = new DataAnalyser();
		for(int i = 0; i < 100; i++){
			bin.constrainedGeneticOptimise(f, d, e);
			data.getData().add(log.getLogData());
			log.clearLog();
		}
		writeDataToFile(f2);
	}

	private void testBinaryFunction(Function f2, double d, double e) {
		log.clearLog();
		data = new DataAnalyser();
		for(int i = 0; i < 100; i++){
			bin.constrainedOptimise(f, d, e);
			data.getData().add(log.getLogData());
			log.clearLog();
		}
		writeDataToFile(f2);
	}

	private void testFunction(double d, double e, Function f2) {
		log.clearLog();
		data = new DataAnalyser();
		for(int i = 0; i < 100; i++){
			vanilla.constrainedOptimise(f, d, e);
			data.getData().add(log.getLogData());
			log.clearLog();
		}
		writeDataToFile(f2);
	}
	
	private void writeDataToFile(Function f){
		ioManager.writeTofile(f.getClass().toString(), file);
		ioManager.writeTofile("Index Changes", file);
		ioManager.writeTofile("Best Index: " + data.calculateMostIndexChanges(), file);
		ioManager.writeTofile("least index: " + data.calculateWorstIndexChanges(), file);
		ioManager.writeTofile("Average No changes: " + data.calculateAvgIndexChanges(), file);
		ioManager.writeTofile("std Dev: " + data.calculateStdDevIndexChanges(), file);
		ioManager.writeTofile("Improvement", file);
		ioManager.writeTofile("Worst Improvement: " + data.calculateLeastImprovement(), file);
		ioManager.writeTofile("Best improvement: " + data.calculateMostImprovement(), file);
		ioManager.writeTofile("Average Improvement: " + data.calculateAverageImprovement(), file);
		ioManager.writeTofile("std Dev: " + data.calculateImprovementStdDev(), file);
	}

	private void testGeneticFunction(Function f2, String string) {
		log.clearLog();
		data = new DataAnalyser();
		for(int i = 0; i < 100; i++){
			comb.geneticOptimise(f2);
			data.getData().add(log.getLogData());
			log.clearLog();
		}
		ioManager.writeTofile(string, file);
		writeDataToFile(f2);
	}

	private void testFunction(Function f2, String string) {
		log.clearLog();
		data = new DataAnalyser();
		for(int i = 0; i < 100; i++){
			comb.optimise(f2);
			data.getData().add(log.getLogData());
			log.clearLog();
		}
		ioManager.writeTofile(string, file);
		writeDataToFile(f2);
	}
	
	
}