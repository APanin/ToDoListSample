import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {AuthRequest, DefaultService} from "../angular-client";
import {AuthService} from "../service/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {


  loginForm = this.formBuilder.group({
    login: '',
    password: ''
  });

  hide = true;


  constructor(
    private authService : AuthService,
    private formBuilder: FormBuilder,
  ) {}

  onSubmit(): void {
    this.authService.login(this.loginForm.get('login')?.value, this.loginForm.get('password')?.value);
  }

  ngOnInit(): void {
    this.loginForm.addControl('address', new FormGroup({
      login: new FormControl(),
      password: new FormControl()
    }))
  }

}
