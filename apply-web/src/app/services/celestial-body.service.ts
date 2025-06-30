import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {CelestialBody} from '../model/celestialBody';

@Injectable({
  providedIn: 'root'
})
export class CelestialBodyService {
  private readonly protocol: string;
  private readonly host: string;
  private readonly port: number;
  private readonly apiUrl: string;
  private readonly apiEndpointUrl: string;

  constructor(private readonly httpClient: HttpClient) {
    this.protocol = 'http';
    this.host = '127.0.0.1';
    this.port = 8080;
    this.apiUrl = 'apply-spring/celestialBody';
    this.apiEndpointUrl = this.protocol + '://' + this.host + ":" + this.port + "/" + this.apiUrl;
  }

  public findAll(): Observable<CelestialBody[]> {
    return this.httpClient.get<CelestialBody[]>(`${this.apiEndpointUrl}/all`);
  }

  public save(planet: CelestialBody): Observable<CelestialBody> {
    return this.httpClient.post<CelestialBody>(`${this.apiEndpointUrl}/new`, planet);
  }

  public getByName(username: any):Observable<CelestialBody> {
    return this.httpClient.get<CelestialBody>(`${this.apiEndpointUrl}/${username}`);
  }

  public removeByName(name: any): Observable<CelestialBody> {
    console.log(new Date() + ": Deleting " + name);
    let deletionCallout = `${this.apiEndpointUrl}/delete/${name}`;
    console.log(new Date() + ": Executing " + deletionCallout);
    return this.httpClient.delete<CelestialBody>(deletionCallout);

  }
}
