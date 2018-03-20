package nl.besselking.dao;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DAO{

    protected PreparedStatement stmt;
    @Inject
    protected DatabaseService db;
    protected Connection conn;

    protected void prepareStmt(String s) throws SQLException, IOException {
        conn = db.getConn();
        stmt = conn.prepareStatement(s);
    }

    protected ResultSet getResultSet() throws SQLException {
        return stmt.executeQuery();
    }

    protected void closeConn() throws SQLException {
        stmt.close();
        conn.close();
    }
}