import {Student} from 'src/app/model/student';

export class TaskAnswer {
  timeSpendInNanoSec: number | undefined;
  studentList: Student[] | undefined;

  constructor(timeSpendInNanoSec?: number, studentList?: Student[]) {
    this.timeSpendInNanoSec = timeSpendInNanoSec;
    this.studentList = studentList;
  }
}
