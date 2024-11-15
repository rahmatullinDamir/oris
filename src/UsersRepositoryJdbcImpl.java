import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import java.sql.*;

class UsersRepositoryJdbcImpl implements UserRepository {

    private Connection connection;
    private static final String SQL_SELECT_ALL_FROM_DRIVER = "SELECT * FROM driver";
    private static final String SQL_INSERT_TO_DRIVER = "INSERT INTO driver (firstname, lastname, age, city, country, profession) VALUES (?, ?, ?, ?, ?, ?)";

    public UsersRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    public Long getUserIdFromDB(User entity) throws SQLException {
        String sql = String.format("SELECT id FROM driver WHERE firstname = '%s' AND lastname = '%s' AND age = %d AND country = '%s' AND city = '%s' AND profession = '%s'",
                entity.getFirstName(), entity.getLastName(), entity.getAge(), entity.getCountry(), entity.getCity(), entity.getProfession());
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()) {
            return resultSet.getLong(1);
        }

        return null;
    }

    public void printInfoFromDb(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + " " +
                    resultSet.getString(2) + " " + resultSet.getString(3)
            + " " + resultSet.getString(4) + " " + resultSet.getString(5) +
                    " " + resultSet.getString(6));
        }
    }

    @Override
    public List<User> findAll() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_FROM_DRIVER);

        List<User> result = new ArrayList<>();

        while (resultSet.next()) {
            User user = new User(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString("lastname"),
                    resultSet.getInt("age"),
                    resultSet.getString("city"),
                    resultSet.getString("country"),
                    resultSet.getString("profession")
            );
            result.add(user);
        }

        return result;
    }

    @Override
    public Optional<User> findById(Long id) throws SQLException {
        String sql = "SELECT * FROM driver WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return Optional.of(new User(resultSet.getLong(1), resultSet.getString(2),
                    resultSet.getString("lastname"), resultSet.getInt("age"),
                    resultSet.getString("city"), resultSet.getString("country"),
                    resultSet.getString("profession")));
        }

        return Optional.empty();
    }

    @Override
    public void save(User entity) throws SQLException {
        String sql = SQL_INSERT_TO_DRIVER;
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, entity.getFirstName());
        statement.setString(2, entity.getLastName());
        statement.setInt(3, entity.getAge());
        statement.setString(4, entity.getCity());
        statement.setString(5, entity.getCountry());
        statement.setString(6, entity.getProfession());

        statement.executeUpdate();
    }

    @Override
    public void save(List<User> entity, int countOfEntities) throws SQLException {
        String sql = SQL_INSERT_TO_DRIVER;
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < countOfEntities; i++) {
            User user = entity.get(i);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setInt(3, user.getAge());
            statement.setString(4, user.getCity());
            statement.setString(5, user.getCountry());
            statement.setString(6, user.getProfession());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(User entity) throws SQLException {
        Long userID = getUserIdFromDB(entity);
        String sql = "UPDATE driver SET firstname = ?, lastname = ?, age = ?, city = ?, country = ?, profession = ? WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, entity.getFirstName());
        statement.setString(2, entity.getLastName());
        statement.setInt(3, entity.getAge());
        statement.setString(4, entity.getCity());
        statement.setString(5, entity.getCountry());
        statement.setString(6, entity.getProfession());
        statement.setLong(7, userID);

        statement.executeUpdate();
    }

    @Override
    public void remove(User entity) throws SQLException {
        Long userID = getUserIdFromDB(entity);
        removeById(userID);
    }

    @Override
    public void removeById(Long id) throws SQLException {
        String sql = "DELETE FROM driver WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, id);

        statement.executeUpdate();
    }

    @Override
    public void getUsersWithCity(String city) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(String.format(SQL_SELECT_ALL_FROM_DRIVER + " WHERE city = '%s'", city));
        printInfoFromDb(result);
    }

    @Override
    public void getUsersWithCountry(String country) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(String.format(SQL_SELECT_ALL_FROM_DRIVER + " WHERE country = '%s'", country));
        printInfoFromDb(result);
    }

    @Override
    public void getUsersWithProfession(String profession) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(String.format(SQL_SELECT_ALL_FROM_DRIVER + " WHERE profession= '%s'", profession));
        printInfoFromDb(result);
    }

    @Override
    public List<User> findAllByAge(Integer age) throws SQLException {
        String sql = "SELECT * FROM driver WHERE age = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, age);

        ResultSet result = statement.executeQuery();

        List<User> users = new ArrayList<>();
        while(result.next()) {
            User user = new User(
                    result.getLong("id"),
                    result.getString("firstname"),
                    result.getString("lastname"),
                    result.getInt("age"),
                    result.getString("city"),
                    result.getString("country"),
                    result.getString("profession")
            );
            users.add(user);
        }

        return users;
    }
}
