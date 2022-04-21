import {Component, EventEmitter, OnInit, Output} from '@angular/core';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {


  @Output()
  open: EventEmitter<boolean> = new EventEmitter()
  constructor() {
  }

  ngOnInit() {

  }

  clickMenu() {
    this.open.emit(true);
  }

}
