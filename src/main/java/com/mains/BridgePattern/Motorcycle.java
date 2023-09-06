package com.mains.BridgePattern;
public class Motorcycle extends Vehicle {

   public Motorcycle(RunAPI runAPI) {
      super(runAPI);
   }

   public void run() {  // implement high level logic here
      runAPI.runVehicle("Motorcycle");
   }
}
