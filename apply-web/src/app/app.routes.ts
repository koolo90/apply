import {Routes} from '@angular/router';
import {ListCelestialBodiesComponent} from './list/list.component';
import {CelestialBodyFormComponent} from './form/celestial-body-form.component';
import {CelestialBodyComponent} from './celestialBody/celestial-body.component';

export const routes: Routes = [
  {path: 'new', component: CelestialBodyFormComponent},
  {path: 'list', component: ListCelestialBodiesComponent},
  {path: 'details/:name', component: CelestialBodyComponent},
  {path: 'edit/:name', component: CelestialBodyFormComponent},
  {path: 'delete/:name', component: ListCelestialBodiesComponent },
];
