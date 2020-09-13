import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-service',
  templateUrl: './service.component.html',
  styleUrls: ['./service.component.scss']
})
export class ServiceComponent implements OnInit {
  chartOptionsmeryem1 = {
    responsive: true,
    legend: {
      display: false
    },
    title: {
      display: true,
      text: 'suivi du service transport '
    },

    scales: {
      yAxes: [
        {
          display: true,
          ticks: {
            max: 100,
            beginAtZero: true
          }
        }
      ]
    }
  };
  chartDatameryem1 = [{ data: [3, 60, 26, 7, 15, 50, 80, 49], label: 'le plus commandé' }];
  chartLabelsmeryem1 = ['janvier', 'fevrier', 'mars', 'avril', 'mai', 'juin', 'juillet', 'aout', 'septembre'];
  // deuxieme graphe

  chartOptionsmeryem2 = {
    responsive: true,
    legend: {
      display: false
    },
    title: {
      display: true,
      text: 'suivi du service Livraison '
    },
    scales: {
      yAxes: [
        {
          display: true,
          ticks: {
            max: 100,
            beginAtZero: true
          }
        }
      ]
    }
  };
  chartDatameryem2 = [{ data: [33, 60, 26, 70, 60, 20, 50], label: 'le plus commandé' }];
  chartLabelsmeryem2 = ['janvier', 'fevrier', 'mars', 'avril', 'mai', 'juin', 'juillet', 'aout', 'septembre'];

  constructor() {}

  ngOnInit(): void {}
}
