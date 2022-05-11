package lt.irmantasm.uniquex.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.irmantasm.uniquex.model.AlgorithmType;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SortingTask {
    private AlgorithmType algorithmType;
    private MultipartFile file;
}
