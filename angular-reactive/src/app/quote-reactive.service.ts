import { Injectable } from '@angular/core';

import { Quote } from './quote';

import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class QuoteReactiveService {
  
  url: string = 'http://localhost:8080/quotes-reactive';
  urlPaged: string = 'http://localhost:8080/quotes-reactive-paged';
  
  constructor(private http: HttpClient) {
  }

  pushNewQuote(quoteData: Partial<Quote>) {
    return this.http.post(this.url, quoteData).subscribe(() => {
      console.log("done");
    })
  }

  getQuoteStream(page?: number, size?: number): Observable<Quote> {
    return new Observable<Quote>((observer) => {
      let url = this.url;
      if (page != null) {
        url = this.urlPaged + '?page=' + page + '&size=' + size;
      }
      let eventSource = new EventSource(url);
      eventSource.onmessage = (event) => {
        console.debug('Received event: ', event);
        let json = JSON.parse(event.data);
        observer.next(new Quote(json['id'], json['book'], json['content']));
      };
      eventSource.onerror = (error) => {
        // readyState === 0 (closed) means the remote source closed the connection,
        // so we can safely treat it as a normal situation. Another way
        // of detecting the end of the stream is to insert a special element
        // in the stream of events, which the client can identify as the last one.
        if (eventSource.readyState === 0) {
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
