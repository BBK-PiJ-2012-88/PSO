package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AcceptableErrorTest.class, AcceptableSolutionHaltTest.class,
		BinaryConstrainerTest.class, BinaryConverterTest.class,
		BinaryInitialiserTest.class, BinaryPositionUpdateTest.class,
		BinarySwarmTest.class, CombinatorialGeneticOperatorTest.class,
		CombinatorialInitialiserTest.class,
		CombinatorialPositionUpdateTest.class, CombinatorialSwarmTest.class,
		CombinatorialVelocityUpdateTest.class,
		ConstrictionCoefficientTest.class, CuboidLatticeTest.class,
		DeJongFunctionsTest.class,
		FitnessCalculatorImplTest.class, GeneticOperatorImplTest.class,
		HaltingCriteriaTest.class, SigmoidFunctionTest.class,
		SwarmMaximumsTest.class, TheFourClustersTest.class, TheRingTest.class,
		TheStarTest.class, TheWheelTest.class, VanillaInitialiserTest.class,
		VanillaPositionUpdateTest.class, VanillaSwarmTest.class,
		VelClampingTest.class })
public class AllTests {

}
