package com.mains.BridgePattern;
public class LowPerformance implements RunAPI {
   @Override
   public void runVehicle(String vehicleType) { // Implement specific logic here
      System.out.println(vehicleType + " is Low Performance");
   }
}

