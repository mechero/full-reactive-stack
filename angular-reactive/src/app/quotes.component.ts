import { Component, OnInit } from '@angular/core';

import { Quote } from './quote';
import { QuoteReactiveService } from './quote-reactive.service';

import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-component-quotes',
  providers: [QuoteReactiveService],
  templateUrl: './quotes.component.html'
})
export class QuotesComponent implements OnInit {
  title = 'These are the available quotes from the server:';
  quotes: Observable<Quote[]>;
  selectedQuote: Quote;

  constructor(private quoteReactiveService: QuoteReactiveService) {}

  ngOnInit(): void {
    this.quotes = this.quoteReactiveService.getQuoteStream();
  }

  onSelect(quote: Quote): void {
    this.selectedQuote = quote;
  }
}
