import { Component } from '@angular/core';

import { Quote } from './quote';
import { QuoteReactiveService } from './quote-reactive.service';
import { QuoteBlockingService } from './quote-blocking.service';

import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-component-quotes',
  providers: [QuoteReactiveService],
  templateUrl: './quotes.component.html'
})
export class QuotesComponent {
  quotes: Observable<Quote[]>;
  selectedQuote: Quote;
  mode: String;
  pagination: boolean;
  page: number;
  size: number;

  constructor(private quoteReactiveService: QuoteReactiveService, private quoteBlockingService: QuoteBlockingService) {
    this.mode = "reactive";
    this.pagination = true;
    this.page = 0;
    this.size = 50;
  }

  requestQuoteStream(): void {
    if (this.pagination === true) {
      this.quotes = this.quoteReactiveService.getQuoteStream(this.page, this.size);
    } else {
      this.quotes = this.quoteReactiveService.getQuoteStream();
    }
  }

  requestQuoteBlocking(): void {
    if (this.pagination === true) {
      this.quotes = this.quoteBlockingService.getQuotes(this.page, this.size);
    } else {
      this.quotes = this.quoteBlockingService.getQuotes();
    }
  }

  onSelect(quote: Quote): void {
    this.selectedQuote = quote;
  }
}
