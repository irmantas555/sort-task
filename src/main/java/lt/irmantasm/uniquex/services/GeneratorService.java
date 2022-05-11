package lt.irmantasm.uniquex.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import lombok.SneakyThrows;
import lt.irmantasm.uniquex.model.Student;

@Component
public class GeneratorService {

    @SneakyThrows
    @PostConstruct
    public void generateStudentFiles() {
        Random random = SecureRandom.getInstanceStrong();
        for (int i = 0; i < 4; i++) {
            int studentCount = random.nextInt(20) + 20;
            List<Student> studentList = new ArrayList<>();
            for (int j = 0; j < studentCount; j++) {
                Double randomDouble = BigDecimal.valueOf(random.nextDouble(8) + 2).setScale(1, RoundingMode.HALF_UP).doubleValue();
                studentList.add(new Student("Student" + (j + 1), randomDouble));
            }
            writeNewFile(studentList, i);
        }
    }

    private void writeNewFile(List<Student> studentList, int number) {
        String filePath = "src/main/resources/test" + (number + 1) + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Student student : studentList) {
                writer.write(String.join(",", student.getName(), student.getMark().toString()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
