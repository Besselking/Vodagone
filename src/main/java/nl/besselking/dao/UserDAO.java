package nl.besselking.dao;

import nl.besselking.domain.User;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DAO {

    public UserDAO() {
    }


    public List<User> list() {
        List<User> users = new ArrayList<>();
        findAllUsers(users);
        return users;
    }

    private void findAllUsers(List<User> users) {
        try {
            prepareStmt("SELECT * FROM user");
            addNewUsersFromDatabase(users, stmt);
            closeConn();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addNewUsersFromDatabase(List<User> users, PreparedStatement stmt) throws SQLException {
        ResultSet resultSet = stmt.executeQuery();
        while (resultSet.next()) {
            addNewUserFromResultSet(users, resultSet);
        }
    }

    private void addNewUserFromResultSet(List<User> users, ResultSet rs) throws SQLException {
        users.add(mapResultSetToUser(rs));
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUser(rs.getString("user"));
        user.setPassword(rs.getString("password"));
        user.setToken(rs.getString("token"));
        user.setFirstName(rs.getString("firstname"));
        user.setLastname(rs.getString("lastname"));
        return user;
    }

    public User findByUserName(String username) {
        try {
            prepareStmt("SELECT u.id, user, password, token, firstname, lastname FROM user u INNER JOIN subscriber s " +
                    "ON u.id = s.id  WHERE user = ?");
            stmt.setString(1, username);
            return getUser();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User findByToken(String token) {
        try {
            prepareStmt("SELECT u.id, user, password, token, firstname, lastname FROM user u INNER JOIN subscriber s ON u.id = s.id WHERE token = ?");
            stmt.setString(1, token);
            return getUser();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User getUser() throws SQLException {
        ResultSet rs = getResultSet();
        User user = null;
        if (rs.next()) {
            user =  mapResultSetToUser(rs);
        }
        closeConn();
        return user;
    }

    public void updateToken(User user) {
        try {
            prepareStmt("UPDATE user SET token = ? where id = ?");
            stmt.setString(1, user.getToken());
            stmt.setInt(2, user.getId());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
