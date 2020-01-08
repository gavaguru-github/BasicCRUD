export interface IApplicationMenu {
  id?: number;
  parentId?: number;
  name?: string;
  englishText?: string;
  frenchPath?: string;
  role?: number;
  order?: number;
}

export class ApplicationMenu implements IApplicationMenu {
  constructor(
    public id?: number,
    public parentId?: number,
    public name?: string,
    public englishText?: string,
    public frenchPath?: string,
    public role?: number,
    public order?: number
  ) {}
}
