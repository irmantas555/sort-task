import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {SortingTask} from 'src/app/model/sorting-task';
import {AlgorithmType} from 'src/app/model/algorithm-type';
import {TaskAnswer} from 'src/app/model/task-answer';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {
  sortTask = new SortingTask();
  answer: TaskAnswer | null = null;
  algorithmOptions: string[] = [];
  selected = '';
  typeArray = new Map<string, AlgorithmType>();

  constructor(private http: HttpClient) { }

  assignFile(target: EventTarget | null) {
    // @ts-ignore
    this.sortTask.file = target.files[0];
  }

  ngOnInit(): void {
    this.assignAlgorithmOtions()
  }

  assignAlgorithm() {
    switch (this.selected) {
      case 'Bubble algorithm': {
        this.sortTask.algorithmType = AlgorithmType.BUBBLE;
        break;
      }
      case 'Heap algorithm': {
        this.sortTask.algorithmType = AlgorithmType.HEAP;
        break;
      }
      case 'Merge algorithm': {
        this.sortTask.algorithmType = AlgorithmType.MERGE;
        break;
      }
    }
  }

  submitInput() {
    if (this.sortTask?.algorithmType && this.sortTask?.file) {
      const formData: FormData = new FormData();
      formData.append('file', this.sortTask.file, this.sortTask.file.name);
      this.http.post<TaskAnswer>('http://localhost:8080/newTask/' + this.sortTask.algorithmType, formData).subscribe(task => {
        this.answer = task;
      })
    }
  }

  assignNames(type: AlgorithmType) {
    switch (type) {
      case 'BUBBLE': {
        return 'Bubble algorithm';
      }
      case 'HEAP': {
        return 'Heap algorithm'
      }
      case 'MERGE': {
        return 'Merge algorithm'
      }
      default:
        return '';
    }
  }

  private assignAlgorithmOtions() {
    for (let item of Object.values(AlgorithmType)) {
      let string = this.assignNames(AlgorithmType[item]);
      this.algorithmOptions.push(string);
      this.typeArray.set(string, item);
    }
    this.selected = this.algorithmOptions[0];
    this.sortTask.algorithmType = AlgorithmType.BUBBLE;
  }
}
