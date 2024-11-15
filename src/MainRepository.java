import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainRepository {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "admin";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        UserRepository userRepository = new UsersRepositoryJdbcImpl(connection);

//        User userToAdd = new User("krendel", "damirr", 22, "Votkinsk", "Uzbekistan", "Driver");
//        User userToAdd2 = new User("alesha", "damirr", 11, "Ivanovo", "Alzhir", "Washer");
//
//        userRepository.save(userToAdd);
//        userRepository.save(userToAdd2);

//        userRepository.getUsersWithCity("Izhevsk");
//        userRepository.getUsersWithCountry("Alzhir");
//        userRepository.getUsersWithProfession("Driver");







    }



}
