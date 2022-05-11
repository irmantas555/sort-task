import {AlgorithmType} from 'src/app/model/algorithm-type';

export class SortingTask {
  algorithmType: AlgorithmType | undefined;
  file: File | undefined;

  constructor(algorithmType?: AlgorithmType, file?: File) {
    this.algorithmType = algorithmType;
    this.file = file;
  }
}
