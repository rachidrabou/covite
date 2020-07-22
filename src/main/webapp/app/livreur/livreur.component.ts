import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-livreur',
  templateUrl: './livreur.component.html',
  styleUrls: ['./livreur.component.scss']
})
export class LivreurComponent implements OnInit {
  chartOptionsmeryem3 = {
    responsive: true,
    legend: {
      display: false
    },
    title: {
      display: true,
      text: 'Livreur du mois '
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
  chartDatameryem3 = [{ data: [33, 60, 26, 70], label: 'nombre de commandes par mois' }];
  chartLabelsmeryem3 = ['livreur1', 'livreur2', 'livreur3', 'livreur4'];

  constructor() {}

  ngOnInit(): void {}
}
