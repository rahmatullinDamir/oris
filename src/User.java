public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private String city;
    private String country;
    private String profession;


    public User(Long id, String firstName, String lastName, Integer age, String city, String country, String profession) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.city = city;
        this.country = country;
        this.profession = profession;
    }

    public User(String firstName, String lastName, Integer age, String city, String country, String profession) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.city = city;
        this.country = country;
        this.profession = profession;
    }

    public  Long getId(){
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getProfession() {
        return profession;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}
