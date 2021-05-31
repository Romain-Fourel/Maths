package fr.romain.Maths.R_VectorSpace.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import fr.romain.Maths.R_VectorSpace.graphTheory.*;

class GraphTests {

	@Test
	void test() {
		Graph<String> graph = new Graph<String>(Arrays.asList("A","B","C","D"),
												new double[][] {{0,1,1,0},
																{1,0,0,1},
																{0,0,0,1},
																{0,0,0,0}});
		/*
		System.out.println(graph.getAdjacencyMatrix());
		assertEquals(5, graph.size());
		*/
		
		System.out.println(graph.remove(1));
		System.out.println(graph.getAdjacencyMatrix());
		assertEquals(2, graph.size());
		
		/*
		System.out.println(graph.remove(2));
		System.out.println(graph.getAdjacencyMatrix());
		assertEquals(1, graph.size());
		
		System.out.println(graph.remove(16));
		System.out.println(graph.getAdjacencyMatrix());
		*/
	}

}





