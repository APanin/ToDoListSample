import { Component, OnInit } from '@angular/core';
import {DefaultService, Task} from "../angular-client";
import {ActivatedRoute} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatListOption} from "@angular/material/list";

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.scss']
})
export class TaskListComponent implements OnInit {
  tasks : Task[] = [];
  selectedTasks : Task[] = [];



  constructor(private defaultService : DefaultService,
              private route: ActivatedRoute,
              private _snackBar: MatSnackBar) {

  }

  ngOnInit(): void {
    this.route.queryParamMap
      .subscribe((params) => {
          let page: number = +<string>params.get(`page`);
          this.defaultService.tasksGet(page).subscribe(value => {
            this.tasks = value;
          })
        }
      );
  }

  onDelete(): void {
    console.log(this.selectedTasks)
    for (let task of this.selectedTasks) {
      if (task != undefined && task.id != undefined)
        this.defaultService.taskDelete(task.id).subscribe(value => {
          this._snackBar.open('Task successfully deleted', 'Ok');
          let index = this.tasks.indexOf(task);
          this.tasks.splice(index);
        })
    }
  }

  onNgModelChange($event: Task[]){
    console.log($event);
    this.selectedTasks=$event;
  }

}
