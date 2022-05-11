package lt.irmantasm.uniquex.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lt.irmantasm.uniquex.model.Student;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDto {
    Long timeSpendInNanoSec;
    List<Student> studentList = new ArrayList<>();
}
