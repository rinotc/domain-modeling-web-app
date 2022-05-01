import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatToolbarModule} from "@angular/material/toolbar";
import {HeaderComponent} from './views/components/header/header/header.component';
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AkitaNgDevtools} from '@datorama/akita-ngdevtools';
import {environment} from '../environments/environment';
import {BoundedContextsListPageComponent} from './views/pages/boundedContext/list/bounded-contexts-list-page.component';
import {MatCardModule} from "@angular/material/card";
import {
  BoundedContextListComponentComponent
} from './views/components/project/project-list-component/bounded-context-list-component.component';
import {MatTableModule} from "@angular/material/table";
import { WelcomeComponent } from './views/components/welcome/welcome.component';
import { BoundedContextDetailPageComponent } from './views/pages/boundedContext/detail/bounded-context-detail-page.component';
import {FlexLayoutModule} from "@angular/flex-layout";
import { BoundedContextCreatePageComponent } from './views/pages/boundedContext/create/bounded-context-create-page.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    BoundedContextsListPageComponent,
    BoundedContextListComponentComponent,
    WelcomeComponent,
    BoundedContextDetailPageComponent,
    BoundedContextCreatePageComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    FormsModule,
    ReactiveFormsModule,
    environment.production ? [] : AkitaNgDevtools.forRoot(),
    MatCardModule,
    MatTableModule,
    FlexLayoutModule,
    MatFormFieldModule,
    MatInputModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
