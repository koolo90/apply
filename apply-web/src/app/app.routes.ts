import { Routes } from '@angular/router';
import {PlanetComponent} from './planet/planet.component';
import {ListPlanetsComponent} from './list/list.component';
import {PlanetFormComponent} from './form/planet-form.component';

export const routes: Routes = [
  { path: 'list', component: ListPlanetsComponent },
  { path: 'new', component: PlanetFormComponent },
  { path: 'planet/:username', component: PlanetComponent },
  { path: 'edit/:username', component: PlanetFormComponent },
  { path: 'delete/:username', component: ListPlanetsComponent }
];
