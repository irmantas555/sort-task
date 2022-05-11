package lt.irmantasm.uniquex.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.SneakyThrows;
import lt.irmantasm.uniquex.dto.SortingTask;
import lt.irmantasm.uniquex.model.Student;

@Service
public class SortingService {

    public List<Student> sortStudentsByAlgorithm(SortingTask task) {

        List<Student> parsedList = null;
        try {
            parsedList = parseFile(task.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (parsedList != null) {
            return switch (task.getAlgorithmType()) {
                case BUBBLE -> sortByBubble(parsedList);
                case HEAP -> sortByHeap(parsedList);
                case MERGE -> sortByMerge(parsedList);
            };
        } else {
            throw new IllegalArgumentException("No results available. There was some problem with given input data");
        }
    }

    private List<Student> sortByBubble(List<Student> list) {
        Student[] studentsArray = list.toArray(new Student[0]);
        int n = studentsArray.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (studentCompare(studentsArray[j], studentsArray[j + 1]) > 0) {
                    Student temp = studentsArray[j];
                    studentsArray[j] = studentsArray[j + 1];
                    studentsArray[j + 1] = temp;
                }
            }
        }
        Arrays.stream(studentsArray).forEach(System.out::println);
        return Arrays.asList(studentsArray);
    }

    private List<Student> sortByHeap(List<Student> list) {
        Student[] studentsArray = list.toArray(new Student[0]);
        int n = studentsArray.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(studentsArray, n, i);
            for (int j = n - 1; j > 0; j--) {
                Student temp = studentsArray[0];
                studentsArray[0] = studentsArray[j];
                studentsArray[j] = temp;

                heapify(studentsArray, j, 0);
            }
        }
        Arrays.stream(studentsArray).forEach(System.out::println);
        return Arrays.asList(studentsArray);
    }

    private List<Student> sortByMerge(List<Student> list) {
        Student[] studentsArray = list.toArray(new Student[0]);
        int n = studentsArray.length;
        sortMerge(studentsArray, 0, studentsArray.length - 1);
        Arrays.stream(studentsArray).forEach(System.out::println);
        return Arrays.asList(studentsArray);
    }

    private void sortMerge(Student[] studentsArray, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;

            sortMerge(studentsArray, l, m);
            sortMerge(studentsArray, m + 1, r);

            mergeMerge(studentsArray, l, m, r);
        }
    }

    void mergeMerge(Student[] arr, int l, int m, int r)
    {
        int n1 = m - l + 1;
        int n2 = r - m;

        Student[] L = new Student[n1];
        Student[] R = new Student[n2];

        for (int i = 0; i < n1; ++i) {
            L[i] = arr[l + i];
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = arr[m + 1 + j];
        }

        int i = 0, j = 0;

        int k = l;
        while (i < n1 && j < n2) {
            if (studentCompare(L[i],R[j]) <= 0) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    void heapify(Student[] arr, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        if (l < n && studentCompare(arr[l], arr[largest]) > 0) {largest = l;}
        if (r < n && studentCompare(arr[r], arr[largest]) > 0) {largest = r;}

        if (largest != i) {
            Student swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            heapify(arr, n, largest);
        }
    }



    private List<Student> parseFile(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        List<String> textLines = readStream(inputStream);
        return parseStudents(textLines);
    }

    private List<Student> parseStudents(List<String> textLines) {
        return textLines.stream()
                        .map(this::buildStudent)
                        .filter(Objects::nonNull)
                        .toList();
    }

    private List<String> readStream(InputStream stream) {
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        return in.lines().toList();
    }

    Student buildStudent(String studentString) {
        return Stream.of(studentString)
                     .map(s -> s.split(","))
                     .map(arr -> new Student(arr[0], Double.parseDouble(arr[1])))
                     .findFirst()
                     .orElse(null);

    }

    double studentCompare(Student student1, Student student2) {
        return student1.getMark() - student2.getMark();
    }

    @SneakyThrows
    public List<Student> sortPreparedTask() {
        String filePath = "C:\\Users\\Irmantas\\IdeaProjects\\uniquex\\uniquex\\src\\main\\resources\\test1.txt";
        Path path = Paths.get(filePath);
        List<String> strings = Files.readAllLines(path);
        List<Student> studentList = parseStudents(strings);
        return sortByMerge(studentList);
    }
}
