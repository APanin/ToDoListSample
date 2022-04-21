import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from "@angular/forms";
import {DefaultService, Task} from "../angular-client";
import {ActivatedRoute, Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-task-update',
  templateUrl: './task-update.component.html',
  styleUrls: ['./task-update.component.scss']
})
export class TaskUpdateComponent implements OnInit {

  taskUpdateForm = this.formBuilder.group({
    title: '',
    description: '',
    isDone: false
  });

  isDone: boolean = false;
  isCreate: boolean = false;
  task?:Task;


  constructor(
    private defaultService: DefaultService,
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private _snackBar: MatSnackBar
  ) {
    this.taskUpdateForm.addControl('taskUpdateForm', new FormGroup({
      title: new FormControl(),
      description: new FormControl(),
      isDone: new FormControl()
    }));
  }

  onSubmit(): void {
    const version = this.task?.version;
    let definedVer: number;
    if (version !== undefined) {
      definedVer = version;
    } else {
      definedVer = 0;
    }

    const task: Task = {
      id: this.task?.id,
      title: this.taskUpdateForm.get('title')?.value,
      description: this.taskUpdateForm.get('description')?.value,
      isDone: this.taskUpdateForm.get('isDone')?.value,
      version: definedVer
    }
    if (this.isCreate) {
      this.defaultService.taskPost(task).subscribe(value => {
        this.taskModifySuccess()
      })
    } else {
      this.defaultService.taskPut(task).subscribe(value => {
        this.taskModifySuccess()
      })
    }
  }

  taskModifySuccess() {
    this._snackBar.open('Task successfully created', 'Ok');
    this.router.navigate(['/task-list'])
  }

  taskStateChange(): void {
    this.isDone = !this.isDone;
    const control = this.taskUpdateForm.get('isDone');
    if (control !== null) {
      control.setValue(this.isDone)
    }
  }

  ngOnInit(): void {
    this.route.paramMap
      .subscribe((params) => {
        if (params !== null) {
          const taskId: string | null = params.get('taskId');
          if (taskId !== null) {
            const taskIdNum: number = +taskId;
            this.defaultService.taskGet(taskIdNum).subscribe(value => {
              this.taskUpdateForm.get('title')?.setValue(value.title);
              this.taskUpdateForm.get('description')?.setValue(value.description);
              this.taskUpdateForm.get('isDone')?.setValue(value.isDone);
              this.isDone = value.isDone;
              this.task = value;
            })
          } else {
            this.isCreate = true;
            return;
          }
        } else {
          this.isCreate = true;
          return;
        }
      });

  }

}
