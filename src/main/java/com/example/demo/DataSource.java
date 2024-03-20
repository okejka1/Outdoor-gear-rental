package com.example.demo;

import com.example.demo.Containers.*;

import java.sql.*;
import java.time.LocalDate;

public class DataSource {

    public static final String CONNECTION_STRING = "jdbc:sqlserver://DESKTOP-I3G35GN;Database=Wypozyczalnia;IntegratedSecurity=true;encrypt=false";
    public static String VIEW = "SELECT * FROM %s";

    public static Connection connection;

    private static Employee employee;



    public static boolean connect() {
        try {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Connection already established");
                return true;
            }
            connection = DriverManager.getConnection(CONNECTION_STRING);
            System.out.println("Connection established");
            return true;
        } catch (SQLException e) {
            System.err.println("Unable to connect to the database");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from the database");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error while disconnecting from the database");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean authenticateUser(String username, String password) {
        String query = "SELECT * FROM Pracownik WHERE login = ? AND haslo = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password); // Assuming the password is already hashed
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) { // Check if there is at least one row
                    int addressId = resultSet.getInt("ADRESID");
                    String query2 = "SELECT * FROM ADRES WHERE ID = ?";
                    try (PreparedStatement preparedStatement2 = connection.prepareStatement(query2)) {
                        preparedStatement2.setInt(1, addressId);
                        try (ResultSet resultSet2 = preparedStatement2.executeQuery()) {
                            if (resultSet2.next()) { // Check if there is at least one row
                                if (resultSet.getInt("STANOWISKOID") == 1) {
                                    employee = new Administrator(resultSet.getInt("ID"), resultSet.getString("imie"), resultSet.getString("nazwisko"),
                                            resultSet.getString("nr_telefonu"), resultSet.getString("email"),
                                            resultSet.getString("nr_dowodu"),
                                            resultSet.getString("login"), resultSet.getString("haslo"), new Position(resultSet.getInt("STANOWISKOID")),
                                            new Adress(resultSet2.getInt("ID"), resultSet2.getString("miasto"), resultSet2.getString("ulica"),
                                                    resultSet2.getString("numer_domu"), resultSet2.getString("numer_mieszkania"),
                                                    resultSet2.getString("kod_pocztowy")));
                                } else {
                                    employee = new Employee(resultSet.getInt("ID"), resultSet.getString("imie"), resultSet.getString("nazwisko"),
                                            resultSet.getString("nr_telefonu"), resultSet.getString("email"),
                                            resultSet.getString("nr_dowodu"),
                                            resultSet.getString("login"), resultSet.getString("haslo"), new Position(resultSet.getInt("STANOWISKOID")),
                                            new Adress(resultSet2.getInt("ID"), resultSet2.getString("miasto"), resultSet2.getString("ulica"),
                                                    resultSet2.getString("numer_domu"), resultSet2.getString("numer_mieszkania"),
                                                    resultSet2.getString("kod_pocztowy")));
                                }

                                return true;
                            } else {
                                System.err.println("No matching address found for user");
                                return false;
                            }
                        }
                    }
                } else {
                    System.err.println("No matching user found");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during user authentication");
            e.printStackTrace();
            return false;
        }
    }


    public static ResultSet performWypozyczenie_view() {
        String query = "SELECT * FROM wypozyczenie_view";
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }



    public static ResultSet performWypozyczenie_view2() {
        String query = "SELECT * FROM wypozyczenie_view2";
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }



    public static ResultSet performEgzemplarz_view() {
        String query = "SELECT * FROM egzemplarz_view";
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet performKlient_view2() {
        String query = "SELECT * FROM klient_view2";
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet performKlient_view() {
        String query = "SELECT * FROM klient_view";
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet performSprzet_view() {
        String query = "SELECT * FROM sprzet_view";
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet performPracownik_view() {
        String query = "SELECT * FROM pracownik_view";
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet performZwrot_view() {
        String query = "SELECT * FROM wypozyczenie_zwrot_view";
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet performSelectEquipCategory() {
        String query = "SELECT * FROM Kategoria";
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean performInsertNewCategory(String categoryName) {
        String query = "INSERT INTO Kategoria (nazwa) VALUES (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, categoryName);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static int manageAddress(Adress address, String action) {
        try (CallableStatement statement = connection.prepareCall("{call ManageAddress(?, ?, ?, ?, ?, ?, ?)}")) {
            statement.registerOutParameter(1, Types.INTEGER); // Register the output parameter

            // Set input parameters
            statement.setString(2, address.getCity());
            statement.setString(3, address.getStreet());
            statement.setString(4, address.getHouseNumber());
            statement.setString(5, address.getFlatNumber());
            statement.setString(6, address.getPostalCode());
            statement.setString(7, action);

            // Execute the stored procedure
            statement.execute();

            // Retrieve the generated AddressID from the output parameter
            int generatedAddressID = statement.getInt(1);

            // Debugging line to print the generated AddressID
            System.out.println("Generated AddressID: " + generatedAddressID);

            return generatedAddressID;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
            return -1; // Indicates an error
        }
    }

    public static void addClient(Client client) {
        try (CallableStatement statement = connection.prepareCall("{call ManageClient(?, ?, ?, ?, ?, ?, ?, ?)}")) {
            // Set parameters
            statement.setInt(1, client.getId());
            statement.setString(2, client.getName());
            statement.setString(3, client.getSurname());
            statement.setString(4, client.getTelephoneNumber());
            statement.setString(5, client.getEmail());
            statement.setString(6, client.getIdNumber());

            // Extracted address management logic using the modified manageAddress method
            int addressID = manageAddress(client.getAdress(), "Insert");

            if (addressID != -1) {
                // If manageAddress was successful, set the addressID in the client's address
                client.getAdress().setId(addressID);
                statement.setInt(7, addressID);
            } else {
                System.err.println("Error adding new Address");
                return; // Don't proceed with the client addition if there's an issue with the address
            }

            statement.setString(8, "Insert");

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // Handle the exception according to your needs
        }
    }

    public static int manageEquipment(String equipmentName, float rentalPrice, String promotion, String categoryName, String action) {
        try (CallableStatement statement = DataSource.connection.prepareCall("{call ManageEquipment(?, ?, ?, ?, ?, ?)}")) {
            statement.registerOutParameter(1, Types.INTEGER); // Register the output parameter

            // Set input parameters
            statement.setString(2, equipmentName);
            statement.setFloat(3, rentalPrice);
            statement.setString(4, promotion);
            statement.setString(5, categoryName);
            statement.setString(6, action);

            // Execute the stored procedure
            statement.execute();

            // Retrieve the generated EquipmentID from the output parameter
            return statement.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Indicates an error
        }
    }


    public static int manageEquipmentEntity(String equipmentEntityColor, String equipmentEntitySize, String equipmentEntityStatus, int equipmentID, String action) {
        try (CallableStatement statement = DataSource.connection.prepareCall("{call ManageEquipmentEntity(?, ?, ?, ?, ?, ?)}")) {
            statement.registerOutParameter(1, Types.INTEGER); // Register the output parameter

            // Set input parameters
            statement.setString(2, equipmentEntityColor);
            statement.setString(3, equipmentEntitySize);
            statement.setString(4, equipmentEntityStatus);
            statement.setInt(5, equipmentID);
            statement.setString(6, action);

            // Execute the stored procedure
            statement.execute();

            // Retrieve the generated EquipmentID from the output parameter
            return statement.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Indicates an error
        }
    }

    public static boolean performSetOnEquipmentEntityStatus(int equipmentId, String newStatus) {
        String query = "UPDATE EGZEMPLARZ_SPRZETU SET status = ? WHERE ID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, equipmentId);

            int rowsUpdated = preparedStatement.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int manageReturns(int rentalId, String action) {
        try (CallableStatement statement = DataSource.connection.prepareCall("{call ManageReturns(?, ?, ?, ?)}")) {
            statement.registerOutParameter(1, Types.INTEGER); // Register the output parameter
            statement.setDate(2, Date.valueOf(LocalDate.now()));
            statement.setInt(3, rentalId);
            statement.setString(4, action);

            statement.execute();

            return statement.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int manageEntityRentals(int rentalId, int equipmentEntityId, String action) {
        try (CallableStatement statement = DataSource.connection.prepareCall("{call ManageEntityRentals(?, ?, ?, ?)}")) {
            statement.registerOutParameter(1, Types.INTEGER); // Register the output parameter
            statement.setInt(2, rentalId);
            statement.setInt(3, equipmentEntityId);
            statement.setString(4, action);

            statement.execute();

            return statement.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }


    public static int manageRentals(LocalDate dateOfReturn, double totalPrice, LocalDate dateOfRental, int employeeID, int clientID, String action) {
        try (CallableStatement statement = DataSource.connection.prepareCall("{call ManageRentals(?, ?, ?, ?, ?, ?, ?)}")) {
            statement.registerOutParameter(1, Types.INTEGER); // Register the output parameter
            statement.setDate(2, Date.valueOf(dateOfReturn));
            statement.setDouble(3, totalPrice);
            statement.setDate(4, Date.valueOf(dateOfRental));
            statement.setInt(5, employeeID);
            statement.setInt(6, clientID);
            statement.setString(7, action);

            statement.execute();

            // Retrieve the generated EquipmentID from the output parameter
            return statement.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Indicates an error
        }
    }

    public static int manageEmployees(String firstName, String lastName, String phoneNumber, String email, String idNumber, String username, String password, Integer addressID, String action) {
        try (CallableStatement statement = DataSource.connection.prepareCall("{call ManageEmployees(?,?,?,?,?,?,?,?,?,?,?,?)}")){
            statement.registerOutParameter(1, Types.INTEGER); // Register the output parameter
            statement.setString(2, firstName);
            statement.setString(3, lastName);
            statement.setString(4, phoneNumber);
            statement.setString(5, email);
            statement.setString(6, idNumber);
            statement.setDate(7, Date.valueOf(LocalDate.now()));
            statement.setString(8, username);
            statement.setString(9, password);
            statement.setInt(10, 2);
            statement.setInt(11, addressID);
            statement.setString(12, action);

            statement.execute();

            return  statement.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Indicates an error
        }
    }


    public static ResultSet performSelectEquipmentEntities(int rentalId){
        String query = "SELECT * FROM WYPOZYCZENIE_SPRZETU WHERE WYPOZYCZENIEID = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, rentalId);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            closePreparedStatement(preparedStatement); // close only the PreparedStatement
            return null;
        }
    }


    // Modified method to close ResultSet
    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void closePreparedStatement(PreparedStatement preparedStatement) {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Employee getEmployee() {
        return employee;
    }
}
