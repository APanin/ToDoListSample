import {Component, Input, ViewChild} from '@angular/core';
import {AuthService} from "./service/auth.service";
import {SidenavService} from "./service/sidenav.service";
import {MatSidenav} from "@angular/material/sidenav";
import {Router} from "@angular/router";
import {AdminAuthGuard} from "./auth/admin.auth.guard";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {

  authenticated:boolean = false;
  @ViewChild('sidenav', {static: true}) public sidenav!: MatSidenav;

  isOpen: boolean = false;

  constructor(private authService:AuthService,
              private sidenavService:SidenavService,
              private adminAuthGuard:AdminAuthGuard,
              private router: Router) {
    this.authService.authenticatedSubject.subscribe(value => {this.authenticated = value})
  }

  navOpen($event: any): void {
    this.isOpen = !this.isOpen;
    this.sidenav.toggle(this.isOpen);
  }

  logout(): void {
    this.authService.logout();
    this.sidenav.toggle(false);
    this.router.navigate(['/login']);
  }

  isAdmin():boolean {
    return this.adminAuthGuard.isAdmin();
  }


}
