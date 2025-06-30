import {Component, OnInit} from '@angular/core';
import {CelestialBody} from '../model/celestialBody';
import {CelestialBodyService} from '../services/celestial-body.service';
import {ActivatedRoute, Router} from '@angular/router';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-form',
  imports: [
    FormsModule
  ],
  templateUrl: './celestial-body-form.component.html',
  styleUrl: './celestial-body-form.component.css'
})
export class CelestialBodyFormComponent implements OnInit {
  celestialBody: CelestialBody;
  private name: any;
  private addMode: boolean = true;

  constructor(private readonly route: ActivatedRoute,
              private readonly router: Router,
              private readonly celestialBodyService: CelestialBodyService) {
    this.celestialBody = new CelestialBody();
  }

  ngOnInit(): void {
    this.name = this.route.snapshot.params['name'];
    this.addMode = !this.name;
    if(!this.addMode) {
      let byUsername = this.celestialBodyService.getByName(this.name);
      byUsername.pipe().subscribe((data: any) => { this.celestialBody = data; });
    }
  }

  onSubmit() {
    this.celestialBodyService.save(this.celestialBody).subscribe(result => this.goToPlanetList());
  }

  private goToPlanetList() {
    this.router.navigate(['/list']);
  }
}
