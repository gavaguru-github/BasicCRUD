export interface IBreadCrumb {
  id?: number;
  path?: string;
  name?: string;
}

export class BreadCrumb implements IBreadCrumb {
  constructor(public id?: number, public path?: string, public name?: string) {}
}
