    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;
    import java.util.stream.Collectors;

    public class HomeTask9 {

        public static void main(String[] args) {
            List<Student> studentsList = new ArrayList<>();
            studentsList.add(new Student("Pavel", Arrays.asList(
                    new Course("IT"), new Course("MATH"), new Course("DRIVING"))));

            studentsList.add(new Student("Daria", Arrays.asList(
                    new Course("IT"), new Course("BIOLOGY"), new Course("CHEMISTRY"), new Course("DRIVING"))));

            studentsList.add(new Student("Elena", Arrays.asList(
                    new Course("MATH"), new Course("TESTING"), new Course("BIOLOGY"), new Course("PHYSICS"))));

            //1
            System.out.println(studentsList.stream()
                    .map(s -> s.getCourses())
                    .flatMap(f -> f.stream())
                    .collect(Collectors.toSet()));

            //2
            System.out.println(studentsList.stream()
                    .sorted((s1, s2) -> s2.getCourses().size() - s1.getCourses().size())
                    .limit(2)
                    .collect(Collectors.toList()));

            //3
            Course course = new Course("MATH");
            System.out.println(studentsList.stream()
                    .filter(s -> s.getCourses().contains(course))
                    .map(s-> s.getName())
                    .collect(Collectors.toList()));
        }
    }
