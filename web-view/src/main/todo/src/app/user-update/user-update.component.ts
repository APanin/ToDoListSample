import { Component, OnInit } from '@angular/core';
import {DefaultService, Role, User} from "../angular-client";
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-user-update',
  templateUrl: './user-update.component.html',
  styleUrls: ['./user-update.component.scss']
})
export class UserUpdateComponent implements OnInit {

  userUpdateForm = this.formBuilder.group({
    name: '',
    lastName: '',
    login: '',
    password: '',
    roles: ''
  });

  isCreate: boolean = false;
  user?:User;
  roles?:Role[];
  selectedRoles?:Role[];
  hide:boolean = true;


  constructor(
    private defaultService: DefaultService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private _snackBar: MatSnackBar
  ) {
    this.userUpdateForm.addControl('userUpdateForm', new FormGroup({
      name: new FormControl(),
      lastName: new FormControl(),
      login: new FormControl(),
      password: new FormControl(),
    }));
  }

  onSubmit(): void {
    const version = this.user?.version;
    let definedVer: number;
    if (version !== undefined) {
      definedVer = version;
    } else {
      definedVer = 0;
    }

    const user: User = {
      id: this.user?.id,
      name: this.userUpdateForm.get('name')?.value,
      lastName: this.userUpdateForm.get('lastName')?.value,
      login: this.userUpdateForm.get('login')?.value,
      password: this.userUpdateForm.get('password')?.value,
      roles: this.selectedRoles,
      version: definedVer
    }
    if (this.isCreate) {
      this.defaultService.userPost(user).subscribe(value => {
        this.userModifySuccess()
      })
    } else {
      this.defaultService.userPut(user).subscribe(value => {
        this.userModifySuccess()
      })
    }
  }

  userModifySuccess() {
    this._snackBar.open('User successfully created', 'Ok');
    this.router.navigate(['/user-list'])
  }

  ngOnInit(): void {
    this.route.paramMap
      .subscribe((params) => {
        if (params !== null) {
          const userId: string | null = params.get('userId');
          if (userId !== null) {
            const userIdNum: number = +userId;
            this.defaultService.userGet(userIdNum).subscribe(value => {
              this.userUpdateForm.get('name')?.setValue(value.name);
              this.userUpdateForm.get('lastName')?.setValue(value.lastName);
              this.userUpdateForm.get('login')?.setValue(value.login);
              this.user = value;
              this.selectedRoles = value.roles;
              console.log(this.selectedRoles)
              this.defaultService.rolesGet(0).subscribe(value1 => {
                this.roles = value1;
                console.log(this.roles)
              })

            })
          } else {
            this.isCreate = true;
            this.defaultService.rolesGet(0).subscribe(value1 => {
              this.roles = value1;
              console.log(this.roles)
            })
            return;
          }
        } else {
          this.isCreate = true;
          this.defaultService.rolesGet(0).subscribe(value1 => {
            this.roles = value1;
            console.log(this.roles)
          })
          return;
        }
      });
    this.defaultService.rolesGet(1).subscribe(value => {
      this.roles = value;
    })
  }

}
