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
    this.planetApiUrl = 'http://127.0.0.1:8080/apply-spring/planet';
  }

  public findAll(): Observable<Planet[]> {
    return this.httpClient.get<Planet[]>(`${this.planetApiUrl}/all`);
  }

  public save(planet: Planet): Observable<Planet> {
    return this.httpClient.post<Planet>(`${this.planetApiUrl}/new`, planet);
  }

  public getByUsername(username: any):Observable<Planet> {
    return this.httpClient.get<Planet>(`${this.planetApiUrl}/${username}`);
  }

  public removeByUsername(username: any): Observable<Planet> {
    return this.httpClient.delete<Planet>(`${this.planetApiUrl}/delete/${username}`);
  }
}
