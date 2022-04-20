import { Component, OnInit } from '@angular/core';
import {DefaultService, Task, User} from "../angular-client";
import {ActivatedRoute} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatListOption} from "@angular/material/list";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
  users : User[] = [];
  selectedUsers : User[] = [];

  constructor(private defaultService : DefaultService,
              private route: ActivatedRoute,
              private _snackBar: MatSnackBar) {

  }

  ngOnInit(): void {
    this.route.queryParamMap
      .subscribe((params) => {
          let page: number = +<string>params.get(`page`);
          this.defaultService.usersGet(page).subscribe(value => {
            this.users = value;
          })
        }
      );
  }

  onDelete(): void {
    for (let selectedUser of this.selectedUsers) {
      if (selectedUser != undefined && selectedUser.id != undefined)
        this.defaultService.userDelete(selectedUser.id).subscribe(value => {
          this._snackBar.open('User successfully deleted', 'Ok');
          let index = this.users.indexOf(selectedUser);
          this.users.splice(index);
        })
    }
  }

  onNgModelChange($event: User[]){
    console.log($event);
    this.selectedUsers=$event;
  }
}
