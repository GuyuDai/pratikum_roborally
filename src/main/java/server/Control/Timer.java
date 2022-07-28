package server.Control;

import java.util.concurrent.TimeUnit;

/**
 * @author Djafari, Dai
 */
public class Timer {

  public static boolean timerStarted = false;

  public static void countDown(int second){
    try {
      timerStarted = true;
      int timeInSeconds = second;
      while (timerStarted && timeInSeconds > 0) {
        //System.out.println(timeInSeconds);  //for testing
        TimeUnit.SECONDS.sleep(1);
        timeInSeconds--;
      }
      timerStarted = false;

    } catch (InterruptedException e) {
      timerStarted = false;
      e.printStackTrace();
    }
  }

}
