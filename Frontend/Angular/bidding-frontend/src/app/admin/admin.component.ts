import { Component, OnInit } from '@angular/core';
import {AdminService} from '../admin.service';
declare var angular: any;

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  initial_container: Container = {id: 1, name: ['Emtpy'], status: 'No status'};
  containers = [this.initial_container];
  constructor(private adminService: AdminService) {}

  ngOnInit() {
    this.getStats();
  }

  getStats() {
    this.adminService.requestStats()
      .subscribe((data: Object) => {
          this.containers = [];
          console.log(data);
          angular.forEach(data, (entry) => {
            const container: Container = {
              id: entry['Id'],
              name: entry['Names'],
              status: entry['State']
            };
            this.containers.push(container);
          });
        }
      );
  }
}

export class Container {
  id: number;
  name: string[];
  status: string;
}
