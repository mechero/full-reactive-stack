import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component
} from '@angular/core';

import {Quote} from './quote';
import {QuoteReactiveService} from './quote-reactive.service';
import {QuoteBlockingService} from './quote-blocking.service';

import {Observable} from 'rxjs';

@Component({
  selector: 'app-component-quotes',
  providers: [QuoteReactiveService],
  templateUrl: './quotes.component.html'
})
export class QuotesComponent {

  quoteArray: Quote[] = [];
  selectedQuote: Quote;
  mode: String;
  pagination: boolean;
  page: number;
  size: number;

  constructor(private quoteReactiveService: QuoteReactiveService, private quoteBlockingService: QuoteBlockingService, private cdr: ChangeDetectorRef) {
    this.mode = "reactive";
    this.pagination = true;
    this.page = 0;
    this.size = 50;
  }

  requestQuoteStream(): void {
    let quoteObservable: Observable<Quote>;
    if (this.pagination === true) {
      quoteObservable = this.quoteReactiveService.getQuoteStream(this.page, this.size);
    } else {
      quoteObservable = this.quoteReactiveService.getQuoteStream();
    }
    quoteObservable.subscribe(quote => {
      this.quoteArray.push(quote);
      this.cdr.detectChanges();
    });
  }

  requestQuoteBlocking(): void {
    if (this.pagination === true) {
      this.quoteBlockingService.getQuotes(this.page, this.size)
        .subscribe(q => this.quoteArray = q);
    } else {
      this.quoteBlockingService.getQuotes()
        .subscribe(q => this.quoteArray = q);
    }
  }

  onSelect(quote: Quote): void {
    this.selectedQuote = quote;
  }
}
