package nl.besselking.dao;

import nl.besselking.domain.Subscriber;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscriberDAO extends DAO {

    public List<Subscriber> list() {
        List<Subscriber> subbers = new ArrayList<>();
        findAllSubscribers(subbers);
        return subbers;
    }

    private void findAllSubscribers(List<Subscriber> subbers) {
        try {
            prepareStmt("SELECT id, firstname, lastname,email FROM subscriber");
            addNewSubscribersFromDatabase(subbers, stmt);
            closeConn();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addNewSubscribersFromDatabase(List<Subscriber> subbers, PreparedStatement stmt) throws SQLException {
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            addNewSubscriberFromResultSet(subbers, resultSet);
        }
    }

    private void addNewSubscriberFromResultSet(List<Subscriber> subbers, ResultSet rs) throws SQLException {
        subbers.add(mapResultSetToSubscriber(rs));
    }

    private Subscriber mapResultSetToSubscriber(ResultSet rs) throws SQLException {
        return new Subscriber(
                rs.getInt("id"),
                rs.getString("firstname"),
                rs.getString("lastname"),
                rs.getString("email")
        );
    }

    public void shareSubscription(int id, int subscriptionid) {

    }
}
