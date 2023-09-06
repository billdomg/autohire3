package com.mains.BridgePattern;
public class Car extends Vehicle {

   public Car(RunAPI runAPI) {
      super(runAPI);
   }

   public void run() {  // implement high level logic here
      runAPI.runVehicle("Car");
   }
}
