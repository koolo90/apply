import { Component, inject, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PlanetService } from '../services/planet.service';
import {ActivatedRoute, RouterLink, RouterLinkActive} from '@angular/router';
import { Planet } from '../model/planet';

@Component({
  selector: 'app-planet',
  imports: [
    FormsModule,
    RouterLink,
    RouterLinkActive
  ],
  templateUrl: './planet.component.html',
  styleUrl: './planet.component.css'
})
export class PlanetComponent implements OnInit {
  private readonly activatedRoute = inject(ActivatedRoute);
  planet: Planet = new Planet;

  constructor(private readonly route: ActivatedRoute,
              private readonly service: PlanetService) {
  }

  ngOnInit(): void {
    let username: string = this.route.snapshot.params['username'];
    this.service.getByUsername(username).subscribe(data => {
      this.planet = data;
    });
  }


}
