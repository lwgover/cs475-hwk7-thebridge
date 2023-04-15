public class OneLaneBridge extends Bridge{
  public int bridgeLimit;
  private Object noEntry = new Object();
  private Object timeLock = new Object();
    /*
   *Inits a OneLaneBridge
   */
  public OneLaneBridge(int bridgeLimit) {
    super();
    this.bridgeLimit = bridgeLimit;
  }

  /*
    * This Bridge method is called by a car when the car wants to enter the bridge. 
    * This method has to determine whether the thread which called it must wait, 
    * or is allowed to proceed on to the bridge. 
    
    * Specifically, a car can’t enter the bridge when there are too many cars on it or if it’s going against the current flow of traffic.
    * When the car is allowed to enter the bridge, 
    * use the car’s setEntryTime(currentTime) method to set the entry time. 
    * Add the car to the bridge list. 
    * Then print the bridge list so we can see (and ensure) that there are no more than 3 cars on the bridge. 
    * Finally, increment currentTime by 1.
    * 
    * @param car: the car that arrives
  */ 
  @Override
  public void arrive(Car car) throws InterruptedException {
    synchronized(noEntry){
      while((bridge.size() >= bridgeLimit) || (bridge.size() != 0 && this.direction != car.getDirection())){
        //System.out.println("Car: " + car + "\nBridge: (dir:" + (direction ? "right" : "left") + ", size: " + bridge.size() + ")");
        noEntry.wait();
      }
      if(bridge.size() == 0 && this.direction != car.getDirection()){
        direction = !direction;
      }
      synchronized(timeLock){
        car.setEntryTime(currentTime);
        bridge.add(car); // add car to bridge safely
        currentTime++;
      }
      System.out.println("Bridge (dir=" + (direction ? "right" : "left") + "): " + bridge.toString());
    }
  }

  /* Method that removes cars from the bridge 
   * 
   * @param car: the car that exits
  */
  @Override
  public void exit(Car car) throws InterruptedException {

    synchronized(noEntry){
      bridge.remove(car);  // remove current car from the bridge list    
      noEntry.notifyAll();
      System.out.println("Bridge (dir=" + (direction ? "right" : "left") + "): " + bridge.toString());
    }
  }  
}