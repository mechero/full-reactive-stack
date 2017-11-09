import { SelectControlValueAccessor } from '@angular/forms/src/directives';
import { QuotesComponent } from './quotes.component';
import { Component } from '@angular/core';

@Component({
  selector: 'app-quotes',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Reactive Quotes';
}
