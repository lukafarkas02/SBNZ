import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavigationComponent } from '../../navigation/navigation/navigation.component';


@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, NavigationComponent],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit{
  aqiStatus: 'DOBAR' | 'UMEREN' | 'LOS' | 'OPASAN' = 'OPASAN';
  gaugeRotation: number = 0;
  gaugeColor: string = "";

  ngOnInit(): void {
    this.updateAqiStatus();
  }

  updateAqiStatus(){
    switch (this.aqiStatus) {
      case 'DOBAR':
        this.gaugeRotation = 20;   // ugao pokazivača
        this.gaugeColor = 'green';
        break;
      case 'UMEREN':
        this.gaugeRotation = 60;
        this.gaugeColor = 'yellow';
        break;
      case 'LOS':
        this.gaugeRotation = 120;
        this.gaugeColor = 'orange';
        break;
      case 'OPASAN':
        this.gaugeRotation = 160;
        this.gaugeColor = 'red';
        break;
    }
  }

  // Primer podataka za grafikon
  chartData = [
    { time: '00:00', pm25: 35, pm10: 50 },
    { time: '03:00', pm25: 40, pm10: 55 },
    { time: '06:00', pm25: 45, pm10: 60 },
    { time: '09:00', pm25: 50, pm10: 65 },
    { time: '12:00', pm25: 55, pm10: 70 },
    { time: '15:00', pm25: 60, pm10: 75 },
    { time: '18:00', pm25: 50, pm10: 60 },
    { time: '21:00', pm25: 45, pm10: 55 },
  ];

  // Trenutne vrednosti polutanata
  pollutants = [
    { name: 'PM2.5', value: 45 },
    { name: 'PM10', value: 60 },
    { name: 'NO2', value: 30 },
    { name: 'O3', value: 20 },
  ];

  // Upozorenja / notifikacije
  alerts = [
    'PM2.5 vrednost je povećana',
    'NO2 prelazi dozvoljenu granicu u centru grada'
  ];
}
