package nl.besselking.dao;

import nl.besselking.domain.Subscription;
import nl.besselking.domain.UserSubscription;

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
    private DatabaseConnection db;

    public UserSubscriptionDAO() {
    }

    @PostConstruct
    private void init() throws IOException {
        conn = db.getConn();
    }

    public void addUserSubscription(int subscriberid, int id) {
        try {
            prepareStmt("INSERT INTO subscriber_subscription(id, subscriberid, startDatum, verdubbeling, status)" +
                    "VALUES (?,?,?,CASE WHEN (SELECT verdubbelbaar FROM subscription where id = ?) = true THEN 'standaard' ELSE 'niet-beschikbaar' END,?);");
            stmt.setInt(1, id);
            stmt.setInt(2, subscriberid);
            stmt.setDate(3, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            stmt.setInt(4, id);
            stmt.setString(5, "actief");
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public List<Subscription> allUserSubscriptions(int subscriberid) {
        List<Subscription> subs = new ArrayList<>();
        findAllUserSubscriptions(subs, subscriberid);
        double totalPrice = getTotalPrice(subscriberid);
        return subs;
        
    }

    public double getTotalPrice(int subscriberid) {
        try {
            prepareStmt("SELECT " +
                    "SUM(CASE WHEN verdubbeling = 'verdubbeld' THEN prijs * 1.5 ELSE prijs END) AS prijs " +
                    "FROM subscription s " +
                    "INNER JOIN subscriber_subscription ss " +
                    "ON s.id = ss.id " +
                    "AND ss.subscriberid = ? " +
                    "AND status = 'actief'");
            stmt.setInt(1, subscriberid);
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

    private void findAllUserSubscriptions(List<Subscription> subs, int subscriberid) {
        try {
            prepareStmt("SELECT s.id, aanbieder, dienst " +
                    "FROM subscription s " +
                    "INNER JOIN subscriber_subscription ss " +
                    "ON s.id = ss.id " +
                    "AND ss.subscriberid = ?");
            stmt.setInt(1, subscriberid);

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
            prepareStmt("SELECT s.id, aanbieder, dienst, CASE WHEN status = 'opgezegd' THEN 0 " +
                    "WHEN verdubbeling = 'verdubbeld' THEN prijs * 1.5 " +
                    "ELSE prijs END AS prijs, " +
                    "startDatum, verdubbeling, deelbaar, status " +
                    "FROM subscription s " +
                    "INNER JOIN subscriber_subscription ss " +
                    "ON s.id = ss.id " +
                    "AND ss.subscriberid = ? " +
                    "AND s.id = ?");
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

    public void terminateSubscription(int subscriberid, int id) {
        try {
            prepareStmt("UPDATE subscriber_subscription " +
                    "SET status = 'opgezegd' " +
                    "WHERE subscriberid  = ? " +
                    "AND id = ?");
            stmt.setInt(1, subscriberid);
            stmt.setInt(2, id);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void upgradeSubscription(int id, int subscriberid, String verdubbeling) {
        try {
            prepareStmt("UPDATE subscriber_subscription " +
                    "SET verdubbeling = ? " +
                    "WHERE subscriberid  = ? " +
                    "AND id = ?");
            stmt.setString(1, verdubbeling);
            stmt.setInt(2, subscriberid);
            stmt.setInt(3, id);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
