import { Injectable } from '@angular/core';

import { Quote } from './quote';

import * as EventSource from 'eventsource';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class QuoteReactiveService {

  quotes: Quote[] = new Array();
  url: string = 'http://localhost:8080/quotes-reactive';
  urlPaged: string = 'http://localhost:8080/quotes-reactive-paged';

  getQuoteStream(page?: number, size?: number): Observable<Array<Quote>> {
    this.quotes = new Array();
    return Observable.create((observer) => {
      let url = this.url;
      if (page != null) {
        url = this.urlPaged + '?page=' + page + '&size=' + size;
      }
      let eventSource = new EventSource(url);
      eventSource.onmessage = (event) => {
        console.debug('Received event: ', event);
        let json = JSON.parse(event.data);
        this.quotes.push(new Quote(json['id'], json['book'], json['content']));
        observer.next(this.quotes);
      };
      eventSource.onerror = (error) => observer.error('EventSource error: ' + error);
    });
  }

}
