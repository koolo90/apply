import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Planet} from '../model/planet';

@Injectable({
  providedIn: 'root'
})
export class PlanetService {
  private readonly planetApiUrl: string;

  constructor(private readonly httpClient: HttpClient) {
    this.planetApiUrl = 'http://localhost:8080/apply-spring/planet';
  }

  public findAll(): Observable<Planet[]> {
    return this.httpClient.get<Planet[]>(`${this.planetApiUrl}/all`);
  }

  public save(Planet: Planet): Observable<Planet> {
    return this.httpClient.post<Planet>(`${this.planetApiUrl}/new`, Planet);
  }

  public getByUsername(username: any):Observable<Planet> {
    return this.httpClient.get<Planet>(`${this.planetApiUrl}/${username}`);
  }
}
