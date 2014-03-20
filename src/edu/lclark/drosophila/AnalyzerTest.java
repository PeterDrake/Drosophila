package edu.lclark.drosophila;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.*;

public class AnalyzerTest {
			public Fly fly;
			public Analyzer analyzer;

			@Before
			public void setUp() throws Exception {
				analyzer = new Analyzer();
			}

			@Test
			public void testAvgFlyVel() {
				Fly fly = new Fly(0, 0, 5);
				double[] myVx = {0, 0, 0, 0, 0};
				double[] myVy = {5, 2, 1, 0, 2};
				fly.setVx(myVx);
				fly.setVy(myVy);
				assertEquals(5, fly.averageVelFly( 0, 0), .1);
				assertEquals(3.5, fly.averageVelFly(0, 1), .1);
				assertEquals(2.6666, fly.averageVelFly( 0, 2), .1);
				assertEquals(2, fly.averageVelFly( 0, 3), .1);
				assertEquals(2, fly.averageVelFly(0, 4), .1);
			}

			@Test
			public void testAvgFlyVel2() {
				Fly fly = new Fly(0, 0, 3);
				double[] myVx = {3, 5, 0};
				double[] myVy = {6, 3, 1};
				fly.setVx(myVx);
				fly.setVy(myVy);
				assertEquals(6.7, fly.averageVelFly(0, 0), .1);
				assertEquals(6.26, fly.averageVelFly(0, 1), .1);
				assertEquals(4.51, fly.averageVelFly(0, 2), .1);
				assertEquals(3.41, fly.averageVelFly(1, 2), .1);
			}

			@Test
			public void testMultipleFliesAvgVel() {
				Fly fly1 = new Fly(0, 0, 5);
				Fly fly2 = new Fly(2, 2, 5);
				Fly fly3 = new Fly(5, 0, 5);
				double[] fly1X= { 0, 2, 0, 4, 0};
				double[] fly1Y= { 0, 0, 1, 0, 1};
				double[] fly2X= { 2, 0, 1, 0, 10};
				double[] fly2Y= { 3, 2, 0, 6, 0};
				double[] fly3X= { 11, 12, 13, 14, 15};
				double[] fly3Y= { 4, 4, 0, 0, 2};
				
				fly1.setVx(fly1X);
				fly1.setVy(fly1Y);
				fly2.setVx(fly2X);
				fly2.setVy(fly2Y);
				fly3.setVx(fly3X);
				fly3.setVy(fly3Y);
				LinkedList<Fly> flyList= new LinkedList<Fly>();
				flyList.add(fly1);
				flyList.add(fly2);
				flyList.add(fly3);
				double[] avgVel = analyzer.averageVelMultFlies(flyList, 0, 5);
				
				assertEquals(5.1, avgVel[0], .1);
				assertEquals(5.54, avgVel[1], .1);
				assertEquals(5, avgVel[2], .1);
			}

			
			@Test
			public void testDist() {
				Fly fly = new Fly(0, 0, 3);
				double[] myX = {0, 0, 0};
				double[] myY = {0, 1, 2};
				fly.setX(myX);
				fly.setY(myY);
				assertEquals(2, fly.totalDistance(0, 2), .1);
				double[] myX2 = {1, 5, 3};
				double[] myY2 = {0, 1, 2};
				fly.setX(myX2);
				fly.setY(myY2);
				assertEquals(6.35, fly.totalDistance(0, 2), .1);
			}
			
			
		

	}


