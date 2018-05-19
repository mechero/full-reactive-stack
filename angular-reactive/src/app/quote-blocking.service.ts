import { Injectable } from '@angular/core';

import { Quote } from './quote';

import {Observable} from 'rxjs/Observable';
import {HttpClient} from "@angular/common/http";

@Injectable()
export class QuoteBlockingService {

  quotes: Quote[] = [];
  url: string = 'http://localhost:8080/quotes-blocking';
  urlPaged: string = 'http://localhost:8080/quotes-blocking-paged';

  constructor(private http: HttpClient) {}

  getQuotes(page?: number, size?: number): Observable<Array<Quote>> {
    this.quotes = [];
    let url = this.url;
    if (page != null) {
      url = this.urlPaged + '?page=' + page + '&size=' + size;
    }
    return this.http.get<Array<Quote>>(url);
  }

}
