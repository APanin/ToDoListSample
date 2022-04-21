import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {MatTabsModule} from "@angular/material/tabs";
import {MatSidenavModule} from "@angular/material/sidenav";
import {CommonModule} from "@angular/common";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {LoginComponent} from './login/login.component';
import {ApiModule, Configuration} from "./angular-client";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {AuthService} from "./service/auth.service";
import {TaskListComponent} from "./task-list/task-list.component";
import {JwtInterceptor} from "./auth/jwt.interceptor";
import {RouterModule} from "@angular/router";
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatIconModule} from "@angular/material/icon";
import {MatInputModule} from "@angular/material/input";
import {MatListModule} from "@angular/material/list";
import {MatLineModule, MatOptionModule} from "@angular/material/core";
import {HeaderComponent} from "./ui-common/header/header.component";
import {FooterComponent} from "./ui-common/footer/footer.component";
import {MatToolbarModule} from "@angular/material/toolbar";
import {SidenavService} from "./service/sidenav.service";
import { TaskUpdateComponent } from './task-update/task-update.component';
import {MatMenuModule} from "@angular/material/menu";
import {MatSnackBar, MatSnackBarModule} from "@angular/material/snack-bar";
import {MatCheckboxModule} from "@angular/material/checkbox";
import { UserUpdateComponent } from './user-update/user-update.component';
import {MatSelectModule} from "@angular/material/select";
import {UserListComponent} from "./user-list/user-list.component";

// export function apiConfigFactory(): Configuration {
//   const params: ConfigurationParameters = {
//     basePath: 'http://localhost:8080'
//   };
//   return new Configuration(params);
// }

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    TaskListComponent,
    FooterComponent,
    HeaderComponent,
    TaskUpdateComponent,
    UserListComponent,
    UserUpdateComponent

  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    CommonModule,
    MatTabsModule,
    MatSidenavModule,
    HttpClientModule,
    ApiModule.forRoot(() => {
      return new Configuration({
        basePath: `http://localhost:8080`,
      });
    }),
    FormsModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatLineModule,
    MatToolbarModule,
    MatMenuModule,
    MatCheckboxModule,
    MatSelectModule,
    MatOptionModule,


  ],
  exports: [
    MatTabsModule,
    MatSidenavModule,
    MatSnackBarModule,
    RouterModule
  ],
  providers: [AuthService, SidenavService, {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true},],
  bootstrap: [AppComponent]
})
export class AppModule { }
