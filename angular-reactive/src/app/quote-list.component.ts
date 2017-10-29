import { Component, Input } from '@angular/core';
import { Quote } from './quote';

@Component({
  selector: 'app-quote-list',
  templateUrl: './quote-list.component.html'
})
export class QuoteListComponent {
  @Input() quote: Quote;
}
