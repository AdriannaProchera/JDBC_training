package Parking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainParking {
    private static final String URL = "jdbc:mysql://localhost:3306/test_base";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) throws SQLException {
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            ParkingDAO parking = new ParkingDAOImpl(conn);
            parking.createTableReservation();
            parking.numberOfParkingSpaces(20);
            parking.parkingSpaceReservation(3);
            parking.blockingParkingSpaces(2);
            parking.numberOfParkingSpacesForDisabled(5);
            parking.parkingSpaceReservationForPerson(new Person("Adrianna", "Prochera", "KR 12345"));
            parking.parkingSpaceReservationForDisabledPerson(new Person("Jan", "Kowalski", "KR 98765"));
            parking.showParkingList();
            System.out.println("Liczba wolnych miejsc parkingowych: " + parking.numberOfFreePlaces());

        }
    }
}

/*
    Ćwiczenie

    Przygotujcie prosze aplikacje do zarzadzania parkingiem.

     Aplikacja powinna przewidywać możliwość:

    - wprowadzenie dowolnej liczby miejsc parkinowych <1000,
    - rezerwacje wylacznie wolnych miejsc przez kierowcow,
    - zablokowywanie miejsc przez zarzadzajacych parkingiem,
    - rezerwacje miejsca przez zarzadzajacych parkingiem dla okreslonego kierowcy,
    - oznaczenie miejsc dla niepełnosprawnych przez zarzadzajacego parkingiem.

    Każdy kierowca powinien byc identyfikowany przez imie, nazwisko, numer rejestracyjny.

    Ćwiczenie do wykonania z wykorzystaniem DAO.
 */