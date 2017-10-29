export class Quote {
  id: number;
  book: string;
  content: string;

  constructor( id: number, book: string, content: string) {
    this.id = id;
    this.book = book;
    this.content = content;
  }
}
