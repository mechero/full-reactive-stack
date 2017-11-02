import { Component, Input } from '@angular/core';
import { Quote } from './quote';

@Component({
  selector: 'app-quote-detail',
  templateUrl: './quote-detail.component.html'
})
export class QuoteDetailComponent {
  @Input() quote: Quote;
}
