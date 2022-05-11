package lt.irmantasm.uniquex.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lt.irmantasm.uniquex.dto.SortingTask;
import lt.irmantasm.uniquex.model.Student;
import lt.irmantasm.uniquex.services.SortingService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class SortingController {

    private final SortingService sortingService;

    @PostMapping("newTask")
    ResponseEntity<List<Student>> sortList(@RequestBody SortingTask task) {
        return ResponseEntity.ok(sortingService.sortStudentsByAlgorithm(task));
    }

    @GetMapping("existingTask")
    ResponseEntity<List<Student>> sortPreparedList() {
        return ResponseEntity.ok(sortingService.sortPreparedTask());
    }
}
