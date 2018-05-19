import { Injectable } from '@angular/core';

import { Quote } from './quote';

import * as EventSource from 'eventsource';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class QuoteReactiveService {

  quotes: Quote[] = [];
  url: string = 'http://localhost:8080/quotes-reactive';
  urlPaged: string = 'http://localhost:8080/quotes-reactive-paged';

  getQuoteStream(page?: number, size?: number): Observable<Array<Quote>> {
    this.quotes = [];
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
      eventSource.onerror = (error) => {
        // readyState === 0 (closed) means the remote source closed the connection,
        // so we can safely treat it as a normal situation. Another way of detecting the end of the stream
        // is to insert a special element in the stream of events, which the client can identify as the last one.
        if(eventSource.readyState === 0) {
          console.log('The stream has been closed by the server.');
          eventSource.close();
          observer.complete();
        } else {
          observer.error('EventSource error: ' + error);
        }
      }
    });
  }

}
