import { Routes } from '@angular/router';
import {PlanetComponent} from './planet/planet.component';
import {ListPlanetsComponent} from './list/list.component';
import {PlanetFormComponent} from './form/planet-form.component';

export const routes: Routes = [
  { path: 'planet/:username', component: PlanetComponent },
  { path: 'list', component: ListPlanetsComponent },
  { path: 'new', component: PlanetFormComponent },
  { path: 'edit/:username', component: PlanetFormComponent }
];
