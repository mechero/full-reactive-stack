import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { QuoteReactiveService } from './quote-reactive.service';
import { AppComponent } from './app.component';
import { QuotesComponent } from './quotes.component';
import { QuoteDetailComponent } from './quote-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    QuotesComponent,
    QuoteDetailComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [
    QuoteReactiveService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
