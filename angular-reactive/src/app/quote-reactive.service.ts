import { log } from 'util';
import { Injectable, OnInit } from '@angular/core';

import { Quote } from './quote';

import * as EventSource from 'eventsource';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class QuoteReactiveService {

  quotes: Quote[] = new Array();

  getQuoteStream(): Observable<Array<Quote>> {
    return Observable.create((observer) => {
      let eventSource = new EventSource('http://localhost:8080/quotes');
      eventSource.onmessage = (event) => {
        console.log('Received event: ', event);
        let json = JSON.parse(event.data);
        this.quotes.push(new Quote(json['id'], json['book'], json['content']));
        observer.next(this.quotes);
      };
      eventSource.onerror = (error) => observer.error('EventSource error: ' + error);
    });
  }

}
