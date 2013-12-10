package org.x4444.revivermx;

import java.util.Random;

/**
 * Plates Simulator It starts Server and 10 Plates
 */
public class PlateSim {
  public static void main(String[] args) {
    // start server
    Server server = new Server();
    server.start();
    System.out.println("Server is started");

    // start 10 plates
    int N = 10;
    PlateRunner[] plateRunnerArr = new PlateRunner[N];
    for (int plateId = 1; plateId <= N; plateId++) {
      Plate plate = new Plate(plateId, server);
      plateRunnerArr[plateId - 1] = new PlateRunner(plate, 2, 10);
    }

    Random rnd = new Random();
    for (int i = 0; i < plateRunnerArr.length; i++) {
      // make a small delay before start next plate
      if (i > 0) {
        try {
          Thread.sleep(rnd.nextInt(1000) + 500);
        } catch (InterruptedException e) {
          // ignore
        }
      }
      plateRunnerArr[i].start();
    }
    System.out.println("All Plates are started");
  }
}
