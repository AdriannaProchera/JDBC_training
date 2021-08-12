package Parking;

import Ex2.Movie;

import java.sql.*;

//sql injection

public class ParkingDAOImpl implements ParkingDAO{
    public static final String ERROR_PARKING_SPACE_RESERVATION = "ERROR_PARKING_SPACE_RESERVATION. Brak wystarczajacej ilosci miejsc parkingowych na rezerwacje!!!";
    public static final String ERROR_BLOCKING_PARKING_SPACES = "ERROR_BLOCKING_PARKING_SPACES . Brak wystarczajacej ilosci miejsc parkingowych do zablokowania!!!";
    public static final String ERROR_PARKING_RESERVATION_FOR_PERSON = " ERROR_PARKING_RESERVATION_FOR_PERSON. Brak wolnego miejsca parkingowego!!!";
    public static final String ERROR_NUMBER_OF_PARKING_SPACES_FOR_DISABLED = "ERROR_NUMBER_OF_PARKING_SPACES_FOR_DISABLED.Brak wystarczajacej ilosci wolnych miejsc parkingowych!!!";
    public static final String ERROR_PARKING_RESERVATION_FOR_DISABLED_PERSON = "ERROR_PARKING_RESERVATION_FOR_DISABLED_PERSON. Brak wolnego miejsca parkingowego dla osoby niepelnosprawnej!!!";

    Parking parking = new Parking();
    private final Connection conn;
    public int placesForDisabledPerson;

    public ParkingDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void numberOfParkingSpaces(int n) {
        parking.setSpacesCounter(n);

        String query = "INSERT INTO Reservation (type, isBusy) VALUES (?, ?)";
        try(PreparedStatement pstmt = conn.prepareStatement(query)){
            for(int i = 0; i < n; i++){
                pstmt.setString(1, "AVAILABLE");
                pstmt.setString(2, "NOT");
                pstmt.executeUpdate();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void parkingSpaceReservation(int n) {
        if(parking.getSpacesCounter() >= n){
            String query = "UPDATE Reservation SET type = 'RESERVED' , isBusy = 'YES' WHERE type = 'AVAILABLE' LIMIT ? ;";
            try(PreparedStatement pstmt = conn.prepareStatement(query)){
                pstmt.setInt(1,n);
                pstmt.executeUpdate();
                parking.setSpacesCounter(parking.getSpacesCounter()-n);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            System.out.println( ERROR_PARKING_SPACE_RESERVATION + " Liczba wolnych miejsc: " + parking.getSpacesCounter());
        }

    }

    @Override
    public void blockingParkingSpaces(int n) {
        if(parking.getSpacesCounter() >= n) {
            String query = "UPDATE Reservation SET type = 'BLOCKED' , isBusy = 'YES' WHERE type = 'AVAILABLE' LIMIT ? ;";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, n);
                pstmt.executeUpdate();
                parking.setSpacesCounter(parking.getSpacesCounter()-n);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            System.out.println( ERROR_BLOCKING_PARKING_SPACES + " Liczba wolnych miejsc: " + parking.getSpacesCounter());
        }
    }

    @Override
    public void parkingSpaceReservationForPerson(Person person) {
        if(parking.getSpacesCounter() >= 1) {
            String query = "UPDATE Reservation SET type = 'RESERVED', isBusy = 'YES', firstName = ?, " +
                    "surname = ?, registrationNumber =? WHERE type = 'AVAILABLE' LIMIT 1;";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, person.getFirstName());
                pstmt.setString(2, person.getSurname());
                pstmt.setString(3, person.getRegistrationNumber());
                pstmt.executeUpdate();
                parking.setSpacesCounter(parking.getSpacesCounter() - 1);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            System.out.println(ERROR_PARKING_RESERVATION_FOR_PERSON);
        }
    }

    @Override
    public void parkingSpaceReservationForDisabledPerson(Person person) {
        if(placesForDisabledPerson >= 1){
            String query = "UPDATE Reservation SET type = 'RESERVED FOR DISABLED', isBusy = 'YES', firstName = ?, " +
                    "surname = ?, registrationNumber =? WHERE type = 'FORDISABLED' LIMIT 1;";
            try(PreparedStatement pstmt = conn.prepareStatement(query)){
                pstmt.setString(1, person.getFirstName());
                pstmt.setString(2, person.getSurname());
                pstmt.setString(3, person.getRegistrationNumber());
                pstmt.executeUpdate();
                placesForDisabledPerson -= 1;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            System.out.println(ERROR_PARKING_RESERVATION_FOR_DISABLED_PERSON);
        }

    }

    @Override
    public void numberOfParkingSpacesForDisabled(int n) {
        if(parking.getSpacesCounter() >= n) {
            String query = "UPDATE Reservation SET type = 'FORDISABLED' , isBusy = 'NOT' WHERE type = 'AVAILABLE' LIMIT ? ;";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, n);
                pstmt.executeUpdate();
                parking.setSpacesCounter(parking.getSpacesCounter()-n);
                placesForDisabledPerson = n;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else{
            System.out.println(ERROR_NUMBER_OF_PARKING_SPACES_FOR_DISABLED + " Liczba wolnych miejsc: " + parking.getSpacesCounter());
        }
    }

    @Override
    public void createTableReservation() {
        try(Statement stmt = conn.createStatement()) {
            String query = "CREATE TABLE IF NOT EXISTS Reservation(\n" +
                    "id SERIAL,\n" +
                    "type varchar(255),\n" +
                    "isBusy varchar(255),\n" +
                    "firstName varchar(255),\n" +
                    "surname varchar(255),\n" +
                    "registrationNumber varchar(255)\n);";
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public int numberOfFreePlaces() {
        return parking.getSpacesCounter();
    }

    @Override
    public void showParkingList() {
        try(Statement stmt = conn.createStatement()){
            String query = "SELECT*FROM Reservation;";
            ResultSet result = stmt.executeQuery(query);
            while(result.next()){
                System.out.println(result.getInt(1)+ " "+ result.getString(2)+" "+
                        result.getString(3)+" "+ result.getString(4) +" "
                        + result.getString(5)+" " + result.getString(6));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
