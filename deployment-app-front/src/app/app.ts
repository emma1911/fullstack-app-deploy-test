import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class AppComponent {
  title = 'frontend';
  message = '';
  data: any = null;

  constructor(private http: HttpClient) {
    this.loadMessage();
  }

  loadMessage() {
    this.http.get('http://localhost:8080/api/hello', { responseType: 'text' })
      .subscribe({
        next: (res) => this.message = res,
        error: (err) => console.error('Error:', err)
      });

    this.http.get('http://localhost:8080/api/message')
      .subscribe({
        next: (res) => this.data = res,
        error: (err) => console.error('Error:', err)
      });
  }
}