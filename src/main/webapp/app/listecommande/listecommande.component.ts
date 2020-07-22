import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-listecommande',
  templateUrl: './listecommande.component.html',
  styleUrls: ['./listecommande.component.scss']
})
export class ListecommandeComponent implements OnInit {
  commandes = [
    {
      date: '21 mars 2020',
      prix: '200',
      type: 'animal',
      typeService: 'transport'
    }
  ];
  constructor() {}

  ngOnInit(): void {}
}
