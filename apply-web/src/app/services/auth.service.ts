import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Credentials} from '../model/credentials';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly baseUrl = 'http://127.0.0.1:8080/apply-spring/auth';

  constructor(private readonly http: HttpClient) {
  }

  register(user: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, user);
  }

  login(credentials: Credentials): void {
    let headers = new HttpHeaders();
    /*
    credentials ? {
      authorization : 'Basic' + btoa(credentials.username + ":" + credentials.password),
    } : {}
     */
    headers.set('authorization', 'Basic ' + btoa(credentials.username + ":" + credentials.password));

    this.http.get(`${this.baseUrl}/login`, {headers: headers}).subscribe(response => {
      console.log(response);
    });
  }
}
