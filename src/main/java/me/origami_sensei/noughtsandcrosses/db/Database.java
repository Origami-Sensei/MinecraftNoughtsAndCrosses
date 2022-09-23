package me.origami_sensei.noughtsandcrosses.db;

import me.origami_sensei.noughtsandcrosses.NoughtsAndCrosses;
import me.origami_sensei.noughtsandcrosses.models.GameRequest;

import java.sql.*;
import java.time.LocalTime;

public class Database {

    private Connection connection;

    private final NoughtsAndCrosses plugin;

    public Database(NoughtsAndCrosses plugin) {
        this.plugin = plugin;
    }


    //function used to connect to the database
    public Connection getConnection() throws SQLException {
        if(connection != null){
            return connection;
        }
        //standard login is root and no password

        String[] details = (String[]) new CredentialGetter(plugin).getCredentials();

        String url =details[0];
        String user =details[1];
        String password =details[2];

        //actual command to connect to the sql database
        this.connection = DriverManager.getConnection(url,user,password);
        plugin.getLogger().info("Connected to the Noughts and Crosses database");
        //getLogger().info("Connected to the Noughts and Crosses database");

        return this.connection;

    }
    //creates the database and the table(s) if they don't all ready exist
    public void InitialiseDatabase() throws SQLException {
        //creates and executes the statement
        Statement statement = getConnection().createStatement();
        String sql ="CREATE TABLE IF NOT EXISTS game_requests(time_sent TIME,sender VARCHAR(36),recipient VARCHAR(36))";
        statement.execute(sql);
        statement.close();

        plugin.getLogger().info("Created the game_requests table");

    }
    //used to check if a game request has been sent to prevent a player from typing "nacaccept <username>" to start a game without a request being sent
    public GameRequest findGameRequest(String sender, String recipient) throws SQLException{
        PreparedStatement statement =getConnection().prepareStatement("SELECT * FROM game_requests WHERE sender = ? AND recipient =?");
        statement.setString(1, sender);
        statement.setString(2, recipient);

        //results is all the values rows that were in the table where the conditions specified above were met
        ResultSet results = statement.executeQuery();
        //if there is a result that matches our query:
        if(results.next()){
            Time time_Sent =results.getTime("time_sent");
            GameRequest gameRequest =new GameRequest(time_Sent,sender,recipient);

            statement.close();

            return  gameRequest;
        }
        statement.close();
        System.out.println("[NoughtsAndCrosses]no results found");
        return  null;
    }
    //allows a user to add a game request to the table
    public void createGameRequest(String sender, String recipient)throws SQLException{
        //gets the current time (I know it's not necessary ,but it might be in a future update. Plus it's my code not yours)
        LocalTime now = LocalTime.now();
        Time time = Time.valueOf( now );
        //the prepare statement uses "?" to signify a variable. You cant use concatenation since it needs to know the data type
        PreparedStatement statement =getConnection().prepareStatement("INSERT INTO game_requests(time_sent,sender,recipient) VALUES (?, ?, ?)");
        //these replace the ? with the appropriate data and data type
        statement.setTime(1, time);
        statement.setString(2, sender);
        statement.setString(3, recipient);
        statement.executeUpdate();

    }
    //deletes a game request when the game is accepted or after 5 mins
    public void deleteGameRequest(String sender, String recipient) throws SQLException{
        PreparedStatement statement = getConnection().prepareStatement("DELETE FROM game_requests where sender = ? and recipient = ?");
        statement.setString(1, sender);
        statement.setString(2, recipient);
        statement.execute();
    }
}
