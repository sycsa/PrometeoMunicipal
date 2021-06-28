 package com.zebra.android.zebrautilitiesmza.screens.trafficticket;
 
 public class TrafficTicketViolatorData
 {
   public String address;
   public String firstName;
   public String lastName;
   public String plateNumber;
   public String priors;
   public String violation;
   
   public TrafficTicketViolatorData(String firstName, String lastName, String address, String priors, String plateNumber, String violation)
   {
     this.firstName = firstName;
     this.lastName = lastName;
     this.address = address;
     this.priors = priors;
     this.plateNumber = plateNumber;
     this.violation = violation;
   }
 }