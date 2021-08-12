package Parking;

public interface ParkingDAO {
    void numberOfParkingSpaces(int n);
    void parkingSpaceReservation(int n);
    void blockingParkingSpaces(int n);
    void parkingSpaceReservationForPerson(Person person);
    void parkingSpaceReservationForDisabledPerson(Person person);
    void numberOfParkingSpacesForDisabled(int n);
    void createTableReservation();
    int numberOfFreePlaces();
}
