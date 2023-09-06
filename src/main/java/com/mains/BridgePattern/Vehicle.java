package com.mains.BridgePattern;
public abstract class Vehicle {
   protected RunAPI runAPI;
   
   protected Vehicle(RunAPI runAPI){
      this.runAPI = runAPI;
   }
   public abstract void run();	
}