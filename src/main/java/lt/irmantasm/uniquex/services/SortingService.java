package lt.irmantasm.uniquex.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lt.irmantasm.uniquex.dto.AnswerDto;
import lt.irmantasm.uniquex.dto.SortingTask;
import lt.irmantasm.uniquex.model.Student;

@Service
public class SortingService {

    public AnswerDto sortStudentsByAlgorithm(SortingTask task) {

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

    private AnswerDto sortByBubble(List<Student> list) {
        long beginTime = System.nanoTime();
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
        long endTime = System.nanoTime();
        long nanoSecs = endTime - beginTime;
        return new AnswerDto(nanoSecs, Arrays.asList(studentsArray));
    }

    private AnswerDto sortByHeap(List<Student> list) {
        long beginTime = System.nanoTime();
        Student[] studentsArray = list.toArray(new Student[0]);
        int n = studentsArray.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(studentsArray, n, i);
        }
        for (int i = n - 1; i > 0; i--) {
            Student temp = studentsArray[0];
            studentsArray[0] = studentsArray[i];
            studentsArray[i] = temp;
            heapify(studentsArray, i, 0);
        }
        long endTime = System.nanoTime();
        long nanoSecs = endTime - beginTime;
        return new AnswerDto(nanoSecs, Arrays.asList(studentsArray));
    }

    private AnswerDto sortByMerge(List<Student> list) {
        long beginTime = System.nanoTime();
        Student[] studentsArray = list.toArray(new Student[0]);
        int n = studentsArray.length;
        sortMerge(studentsArray, n);
        long endTime = System.nanoTime();
        long nanoSecs = endTime - beginTime;
        return new AnswerDto(nanoSecs, Arrays.asList(studentsArray));
    }

    private void sortMerge(Student[] studentsArray, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        Student[] l = new Student[mid];
        Student[] r = new Student[n - mid];

        for (int i = 0; i < mid; i++) {
            l[i] = studentsArray[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = studentsArray[i];
        }
        sortMerge(l, mid);
        sortMerge(r, n - mid);

        mergeMerge(studentsArray, l, r, mid, n - mid);
    }

    void mergeMerge(
            Student[] a, Student[] l, Student[] r, int left, int right) {

        int i = 0;
        int j = 0;
        int k = 0;
        while (i < left && j < right) {
            if (l[i].getMark() <= r[j].getMark()) {
                a[k++] = l[i++];
            } else {
                a[k++] = r[j++];
            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
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
}
