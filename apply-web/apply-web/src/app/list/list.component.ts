import {Component, OnInit} from '@angular/core';
import {PlanetService} from '../services/planet.service';
import {Planet} from '../model/planet';
import {NgForOf} from '@angular/common';
import {RouterLink, RouterLinkActive} from '@angular/router';

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
export class ListPlanetsComponent implements OnInit {
  planets: Planet[] = [];

  constructor(private readonly service: PlanetService) { }

  ngOnInit() {
    this.service.findAll().subscribe(data => {
      this.planets = data;
    });
  }
}
