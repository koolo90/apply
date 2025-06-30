import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CelestialBodyService } from '../services/celestial-body.service';
import {ActivatedRoute, RouterLink, RouterLinkActive} from '@angular/router';
import { CelestialBody } from '../model/celestialBody';

@Component({
  selector: 'app-planet',
  imports: [
    FormsModule,
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './celestialBody.component.html',
  styleUrl: './celestialBody.component.css'
})
export class CelestialBodyComponent implements OnInit {
  private readonly activatedRoute = inject(ActivatedRoute);
  celestialBody: CelestialBody = new CelestialBody;

  constructor(private readonly route: ActivatedRoute,
              private readonly service: CelestialBodyService) {
  }

  ngOnInit(): void {
    let username: string = this.route.snapshot.params['name'];
    this.service.getByName(username).subscribe(data => {
      this.celestialBody = data;
    });
  }
}
