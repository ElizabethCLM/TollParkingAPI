# Toll Parking Java API Library

A toll parking contains multiple parking slots. It contains parking slots for:
- gasoline vehicles
- parking slots with 20KW supply for electric vehicles
- parking slots with 50KW supply for electric vehicles

50Kw vehicles cannot use the 50Kw supply and vice-versa.

Every parking is free to implement its own pricing policy.
- Some only bills their customer for each hour spent in the parking `(nb hours * hour price)`
- Some other bill a fixed amount + each hour spent in the parking `(fixed amount + nb hours * hour price)`

In the future, there will be other pricing policies implemented.
Cars of all types come in and out - the API will :
- Send them to the right parking slot of refuse them if there is no slot (of the right type) left.
- Mark the parking slot as Free when the car leaves it
- Bill the customer when the car leaves

## Prerequisites
To build the project and use the API

In Linux: Start by updating the package version: `sudo apt update`

### Install Java 8
Linux: `sudo apt-get install oracle-java8-installer`

Windows: Please follow the installation guide: https://www.java.com/en/download/help/windows_manual_download.xml

Mac OS X: Please follow steps:  https://www.java.com/en/download/help/mac_install.xml

### Install maven 3.6.3 (latest version at this moment)
Linux: `sudo apt install maven`

Windows and Mac OS X: Please follow the installation guide https://maven.apache.org/install.html

### Clone the project
Clone the project from Github: https://github.com/ElizabethCLM/TollParking.git
`git clone https://github.com/ElizabethCLM/TollParking.git`

### Install project dependencies/ build project
In a new terminal, go in the cloned directory where the `pom.xml` file is situated and do a 
- `mvn clean install`

## How to run tests

### To run all tests from one class
In a terminal, go the directory where the `pom.xml` is situated and use the command
- `mvn -Dtest=TestClassNameTest test`

Example:  To run all the tests in class `ParkingTest` class use the command: 
- `mvn -Dtest=ParkingTest test`

### To run a single test from one class
In a terminal, go the directory where the `pom.xml` is situated and use the command 
- `mvn -Dtest=TestClassName#testName test`

Example: To run the test `addParkingWithNegativeNumberOfSlots` in the class `ParkingTest` use the command:
- `mvn -Dtest=ParkingTest#addParkingWithNegativeNumberOfSlots test`

## Examples of usage
### Create a new parking: 

- `Parking p = new Parking(10, 20, 30);` 

creates a new Parking having 10 standard slots, 20 parking slots with 20KW supply and 30 parking slots with 50KW supply.
If no policy is specified, a default policy is associated with a default price of 0 per hour (free parking)

### Check in a car in the parking
- `Ticket ticket = parking.parkVehicle(VehicleType.ELECTRIC_20KW);` 
if successful responds with a Ticket object given to the customer

### Check out a car from the parking
- `Ticket billedTicket = parking.removeVehicle(ticket);` 
if successful returns the same Ticket with the amount to be billed filled on the ticket.

## License




