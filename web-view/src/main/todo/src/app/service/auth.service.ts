import {Injectable} from '@angular/core';
import {HttpHeaders, HttpResponse} from '@angular/common/http';
import {BehaviorSubject, Observable, Subject} from "rxjs";
import {AuthRequest, DefaultService, User} from "../angular-client";
import {Router} from "@angular/router";

@Injectable()
export class AuthService {

  authenticated = false;

  public authenticatedSubject: Subject<boolean> = new Subject<boolean>();

  private tokenSubject: BehaviorSubject<string>;
  public token: Observable<string>;
  private userSubject: BehaviorSubject<string>;
  public user: Observable<string>;

  constructor(
    private defaultService : DefaultService,
    private router: Router
  ) {
    this.tokenSubject = new BehaviorSubject<string>(<string>localStorage.getItem('token'));
    this.token = this.tokenSubject.asObservable();
    this.userSubject = new BehaviorSubject<string>(<string>localStorage.getItem(`currentUser`));
    this.user = this.userSubject.asObservable();
  }

  public get tokenValue(): string {
    return this.tokenSubject.value;
  }

  public get userValue(): string {
    return this.userSubject.value;
  }

  login(username: string, password: string) {
    let authRequest : AuthRequest = {login: username, password:
      password};
    this.defaultService.sessionCreate(authRequest,  "response").subscribe(
      value => {
        this.authenticate(value);
        this.router.navigate(['/task-list'], {
          queryParams: { page: '0' },
          queryParamsHandling: 'merge' });
      });
  }

  logout() {
    this.authenticated = false;
    this.authenticatedSubject.next(this.authenticated);
    localStorage.removeItem('token');
    this.tokenSubject.next('');
    this.router.navigate(['/logout']);
  }
  authenticate(response: HttpResponse<User>) {
    let token:string;
    let user:string;
    if (response.headers.get("Authorization") != null) {
      token = <string>response.headers.get("Authorization");
      user = JSON.stringify(response.body);
    } else{
      return;
    }
    localStorage.setItem('token', token);
    localStorage.setItem('currentUser', user);
    this.tokenSubject.next(token);
    this.userSubject.next(user);
    this.authenticated = true;
    this.authenticatedSubject.next(this.authenticated);
    return true;
  }



}
