import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { QuoteReactiveService } from './quote-reactive.service';
import { QuoteService } from './quote.service';
import { AppComponent } from './app.component';
import { QuotesComponent } from './quotes.component';
import { QuoteListComponent } from './quote-list.component';

@NgModule({
  declarations: [
    AppComponent,
    QuotesComponent,
    QuoteListComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [
    QuoteService,
    QuoteReactiveService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
