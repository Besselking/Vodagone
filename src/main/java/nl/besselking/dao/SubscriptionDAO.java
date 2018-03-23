package nl.besselking.dao;

import nl.besselking.domain.Subscription;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDAO extends DAO{
    @Inject
    private DatabaseService db;

    public SubscriptionDAO(){
    }

    @PostConstruct
    private void init() throws IOException {
        conn = db.getConn();
    }


    public List<Subscription> list(String filter) {
        List<Subscription> subs = new ArrayList<>();
        findAllSubscriptions(subs, filter);
        return subs;
    }

    private void findAllSubscriptions(List<Subscription> subs, String filter) {
        try {
            if(!"".equals(filter)) {
                prepareStmt("SELECT id, aanbieder, dienst FROM subscribtion WHERE aanbieder LIKE ? OR dienst LIKE ?");
                stmt.setString(1, "%" + filter + "%");
                stmt.setString(2, "%" + filter + "%");
            } else {
                prepareStmt("SELECT id, aanbieder, dienst FROM subscription");
            }
            addNewSubscriptionsFromDatabase(subs, stmt);
            closeConn();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addNewSubscriptionsFromDatabase(List<Subscription> subs, PreparedStatement stmt) throws SQLException {
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            addNewSubscriptionFromResultSet(subs, resultSet);
        }
    }

    private void addNewSubscriptionFromResultSet(List<Subscription> subs, ResultSet rs) throws SQLException {
        subs.add(mapResultSetToSubscription(rs));
    }

    private Subscription mapResultSetToSubscription(ResultSet rs) throws SQLException {
        return new Subscription(
                rs.getInt("id"),
                rs.getString("aanbieder"),
                rs.getString("dienst")
        );
    }

    public Subscription find(int id) {
        try {
            prepareStmt("SELECT * FROM user WHERE id = ?");
            stmt.setInt(1, id);
            return getSubscription();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Subscription getSubscription() throws SQLException {
        ResultSet rs = getResultSet();
        Subscription sub = null;
        if (rs.next()) {
            sub = mapResultSetToSubscription(rs);
        }
        closeConn();
        return sub;
    }
}

