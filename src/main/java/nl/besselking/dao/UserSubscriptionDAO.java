package nl.besselking.dao;

import nl.besselking.domain.Subscription;
import nl.besselking.domain.User;
import nl.besselking.dto.SubscriptionRequest;
import nl.besselking.services.DBConnection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UserSubscriptionDAO extends DAO {
    @Inject
    private DBConnection db;

    public UserSubscriptionDAO() {
    }

    @PostConstruct
    private void init() throws IOException {
        conn = db.getConn();
    }

    public void addUserSubscription(User user, SubscriptionRequest newSub) {
        try {
            prepareStmt("INSERT INTO user_abonnement(id, userid, startDatum, verdubbeling, status)" +
                    "VALUES (?,?,?,?,?);");
            stmt.setInt(1, newSub.getId());
            stmt.setInt(2, user.getId());
            stmt.setDate(3, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            stmt.setString(4, "standaard");
            stmt.setString(5, "actief");
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public List<Subscription> allUserSubscriptions(User user) {
        List<Subscription> subs = new ArrayList<>();
        findAllUserSubscriptions(subs, user);
        return subs;
        
    }

    private void findAllUserSubscriptions(List<Subscription> subs, User user) {
        try {
            prepareStmt("SELECT a.id, aanbieder, dienst " +
                    "FROM abonnement a " +
                    "INNER JOIN user_abonnement ua " +
                    "ON a.id = ua.id " +
                    "AND ua.userid = ?");
            stmt.setInt(1, user.getId());

            addNewUserSubscriptionsFromDatabase(subs, stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addNewUserSubscriptionsFromDatabase(List<Subscription> subs, PreparedStatement stmt) throws SQLException {
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            addNewUserSubscriptionFromResultSet(subs, resultSet);
        }
    }

    private void addNewUserSubscriptionFromResultSet(List<Subscription> subs, ResultSet rs) throws SQLException {
        subs.add(mapResultSetToSubscription(rs));
    }

    private Subscription mapResultSetToSubscription(ResultSet rs) throws SQLException {
        return new Subscription(
                rs.getInt("id"),
                rs.getString("aanbieder"),
                rs.getString("dienst")
        );
    }
}
