import {Component, OnInit} from '@angular/core';
import {CelestialBodyService} from '../services/celestial-body.service';
import {CelestialBody} from '../model/celestialBody';
import {NgForOf} from '@angular/common';
import {RouterLink, RouterLinkActive} from '@angular/router';
import {subscribeOn} from 'rxjs';

@Component({
  selector: 'app-planet-list',
  templateUrl: './list.component.html',
  imports: [
    NgForOf,
    RouterLink,
    RouterLinkActive
  ],
  styleUrl: './list.component.css'
})
export class ListCelestialBodiesComponent implements OnInit {
  celestialBodies: CelestialBody[] = [];

  constructor(private readonly service: CelestialBodyService) { }

  ngOnInit() {
    this.service.findAll().subscribe(data => {
      this.celestialBodies = data;
    });
  }

  delete(name: string) {
    console.log(new Date() + ": Deleting " + name);
    this.service.removeByName(name).subscribe(value => {
      console.log(new Date() + ": Deleted " + name);
      this.celestialBodies = this.celestialBodies.filter(c => c.name !== name);
      console.log(new Date() + ": Deleted from view" + name);
    });
  }
}
