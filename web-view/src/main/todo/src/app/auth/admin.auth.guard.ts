import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {AuthService} from "../service/auth.service";
import {DefaultService, User} from "../angular-client";

@Injectable({ providedIn: 'root' })
export class AdminAuthGuard implements CanActivate {
  constructor(
    private router: Router,
    private authService: AuthService
  ) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const token = this.authService.token;
    if (!token) {
      this.router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
      return false;
    } else {
      return this.isAdmin();
    }
  }

  isAdmin():boolean {
    if (this.authService.userValue == undefined) return false;
    let user: User = JSON.parse(this.authService.userValue);
    let roles = user.roles;
    if (roles == null) return false;
    for (let i = 0; i < roles.length; i++) {
      let role = roles[i];
      if (role.name === 'admin') return true;
    }
    return false;
  }


}
