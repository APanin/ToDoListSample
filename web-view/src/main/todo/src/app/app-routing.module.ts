import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {TaskListComponent} from "./task-list/task-list.component";
import {LoginComponent} from "./login/login.component";
import {AuthGuard} from "./auth/auth.guard";
import {TaskUpdateComponent} from "./task-update/task-update.component";
import {UserListComponent} from "./user-list/user-list.component";
import {AdminAuthGuard} from "./auth/admin.auth.guard";
import {UserUpdateComponent} from "./user-update/user-update.component";

export const routes: Routes = [
  { path: 'task-list', component: TaskListComponent, canActivate: [AuthGuard]},
  { path: 'task-update/:taskId', component: TaskUpdateComponent, canActivate: [AuthGuard]},
  { path: 'task-update', component: TaskUpdateComponent, canActivate: [AuthGuard]},
  { path: 'user-list', component: UserListComponent, canActivate: [AdminAuthGuard]},
  { path: 'user-update/:taskId', component: UserUpdateComponent, canActivate: [AdminAuthGuard]},
  { path: 'user-update', component: UserUpdateComponent, canActivate: [AdminAuthGuard]},
  { path: 'login', component: LoginComponent},
  { path: '', redirectTo: 'task-list', pathMatch: 'full' }

];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: false })],
  exports: [RouterModule],
  providers: [AuthGuard]
})
export class AppRoutingModule { }
