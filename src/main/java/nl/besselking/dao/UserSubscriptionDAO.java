package nl.besselking.dao;

import nl.besselking.domain.Subscription;
import nl.besselking.domain.UserSubscription;
import nl.besselking.rest.dto.SubscriptionListResponse;

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
    private DatabaseService db;

    public UserSubscriptionDAO() {
    }

    @PostConstruct
    private void init() throws IOException {
        conn = db.getConn();
    }

    public void addUserSubscription(int userid, int id) {
        try {
            prepareStmt("INSERT INTO user_abonnement(id, userid, startDatum, verdubbeling, status)" +
                    "VALUES (?,?,?,?,?);");
            stmt.setInt(1, id);
            stmt.setInt(2, userid);
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
    
    public SubscriptionListResponse allUserSubscriptions(int userid) {
        List<Subscription> subs = new ArrayList<>();
        findAllUserSubscriptions(subs, userid);
        double totalPrice = getTotalPrice(userid);

        return new SubscriptionListResponse(subs, totalPrice);
        
    }

    private double getTotalPrice(int userid) {
        try {
            prepareStmt("SELECT SUM(prijs) AS prijs " +
                    "FROM abonnement a " +
                    "INNER JOIN user_abonnement ua " +
                    "ON a.id = ua.id " +
                    "AND ua.userid = ? " +
                    "AND status = 'actief'");
            stmt.setInt(1, userid);
            ResultSet rs = getResultSet();
            if(rs.next())
                return rs.getDouble("prijs");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void findAllUserSubscriptions(List<Subscription> subs, int userid) {
        try {
            prepareStmt("SELECT a.id, aanbieder, dienst " +
                    "FROM abonnement a " +
                    "INNER JOIN user_abonnement ua " +
                    "ON a.id = ua.id " +
                    "AND ua.userid = ?");
            stmt.setInt(1, userid);

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

    public UserSubscription getUserSubscription(int userid, int id) {
        try {
            prepareStmt("SELECT a.id, aanbieder, dienst, prijs, startDatum, verdubbeling, deelbaar, status " +
                    "FROM abonnement a " +
                    "INNER JOIN user_abonnement ua " +
                    "ON a.id = ua.id " +
                    "AND ua.userid = ? " +
                    "AND a.id = ?");
            stmt.setInt(1, userid);
            stmt.setInt(2, id);
            ResultSet rs = getResultSet();
            if(rs.next())
                return mapResultSetToUserSubscription(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private UserSubscription mapResultSetToUserSubscription(ResultSet rs) throws SQLException {
        return new UserSubscription(
                rs.getInt("id"),
                rs.getString("aanbieder"),
                rs.getString("dienst"),
                rs.getDouble("prijs"),
                rs.getDate("startDatum"),
                rs.getString("verdubbeling"),
                rs.getBoolean("deelbaar"),
                rs.getString("status")
        );
    }

    public void terminateSubscription(int userid, int id) {
        try {
            prepareStmt("UPDATE user_abonnement " +
                    "SET status = 'opgezegd' " +
                    "WHERE userid  = ? " +
                    "AND id = ?");
            stmt.setInt(1, userid);
            stmt.setInt(2, id);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
