export class Product {
  [x: string]: any | string;
  constructor(
    public id: number,
    public name: string,
    public description: string,
    public imageUrl: string,
    public unitPrice: number,
    public active: boolean,
    public ingredients: string,
    public preparationTime: string,
    public dateCreated: Date,
    public vegan: boolean,
    public dairyFree: boolean,
    public nutFree: boolean,
    public vegetarian: boolean,
    public glutenFree: boolean,
    public mealType: string
  ) {}
}
