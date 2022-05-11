package lt.irmantasm.uniquex.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lt.irmantasm.uniquex.dto.AnswerDto;
import lt.irmantasm.uniquex.dto.SortingTask;
import lt.irmantasm.uniquex.model.AlgorithmType;
import lt.irmantasm.uniquex.services.SortingService;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class SortingController {

    private final SortingService sortingService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("newTask/{type}")
    ResponseEntity<AnswerDto> sortList(@RequestParam MultipartFile file, @PathVariable("type") AlgorithmType type) {
        return ResponseEntity.ok(sortingService.sortStudentsByAlgorithm(new SortingTask(type, file)));
    }
}
