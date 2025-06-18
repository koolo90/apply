import {Component, OnInit} from '@angular/core';
import {Planet} from '../model/planet';
import {PlanetService} from '../services/planet.service';
import {ActivatedRoute, Router} from '@angular/router';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-form',
  imports: [
    FormsModule
  ],
  templateUrl: './planet-form.component.html',
  styleUrl: './planet-form.component.css'
})
export class PlanetFormComponent implements OnInit {
  planet: Planet;
  private username: any;
  private addMode: boolean = true;

  constructor(private readonly route: ActivatedRoute,
              private readonly router: Router,
              private readonly planetService: PlanetService) {
    this.planet = new Planet();
  }

  ngOnInit(): void {
    this.username = this.route.snapshot.params['username'];
    this.addMode = !this.username;
    if(!this.addMode) {
      let byUsername = this.planetService.getByUsername(this.username);
      byUsername.pipe().subscribe((data: any) => { this.planet = data; });
    }
  }


  onSubmit() {
    this.planetService.save(this.planet).subscribe(result => this.goToPlanetList());
  }

  private goToPlanetList() {
    this.router.navigate(['/list']);
  }
}
