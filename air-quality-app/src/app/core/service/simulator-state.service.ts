// import { Injectable, OnDestroy } from '@angular/core';
// import { BehaviorSubject } from 'rxjs';
//
// export interface SimulationData {
//   pm25: number;
//   pm10: number;
//   no2: number;
//   o3: number;
//   co2: number;        // <-- Додато
//   windSpeed: number;
//   humidity: number;
//   temperature: number;
//   pressure: number;   // <-- Додато
//   precipitation: boolean;
// }
//
// @Injectable({
//   providedIn: 'root'
// })
// export class SimulationStateService implements OnDestroy {
//   private currentSimulationSource = new BehaviorSubject<SimulationData>({
//     pm25: 40,
//     pm10: 60,
//     no2: 50,
//     o3: 40,
//     co2: 500,
//     windSpeed: 2.5,
//     humidity: 65.0,
//     temperature: 20.0,
//     pressure: 1013.0,
//     precipitation: false
//   });
//
//   currentSimulation$ = this.currentSimulationSource.asObservable();
//   private simulationInterval: any;
//
//   // Тренутна стања за генерисање дрфта унутар сервиса
//   private currentPollutants = {
//     PM2_5: 40,
//     PM10: 60,
//     NO2: 50,
//     O3: 40,
//     CO2: 500
//   };
//
//   private currentWeather = {
//     temperature: 20.0,
//     humidity: 65.0,
//     windSpeed: 3.5,
//     precipitation: 0.0,
//     pressure: 1013.0
//   };
//
//   constructor() {
//     this.startGlobalSimulation();
//   }
//
//   private startGlobalSimulation() {
//     this.simulationInterval = setInterval(() => {
//       this.generateAndEmitNewData();
//     }, 10_000);
//   }
//
//   private randomStep(maxStep: number): number {
//     return Math.round((Math.random() * 2 * maxStep - maxStep) * 10) / 10;
//   }
//
//   private clamp(value: number, min: number, max: number): number {
//     return Math.max(min, Math.min(max, value));
//   }
//
//   private generateAndEmitNewData() {
//     // Дрфт за загађење (укључујући CO2)
//     this.currentPollutants.PM2_5 = this.clamp(this.currentPollutants.PM2_5 + this.randomStep(3), 0, 100);
//     this.currentPollutants.PM10 = this.clamp(this.currentPollutants.PM10 + this.randomStep(3), 0, 100);
//     this.currentPollutants.NO2 = this.clamp(this.currentPollutants.NO2 + this.randomStep(5), 0, 100);
//     this.currentPollutants.O3 = this.clamp(this.currentPollutants.O3 + this.randomStep(5), 0, 100);
//     this.currentPollutants.CO2 = this.clamp(this.currentPollutants.CO2 + this.randomStep(50), 300, 2500); // <-- Симулација за CO2
//
//     // Дрфт за време (укључујући pressure)
//     this.currentWeather.temperature = this.clamp(this.currentWeather.temperature + this.randomStep(0.5), -10, 40);
//     this.currentWeather.humidity = this.clamp(this.currentWeather.humidity + this.randomStep(2), 10, 100);
//     this.currentWeather.windSpeed = this.clamp(this.currentWeather.windSpeed + this.randomStep(0.5), 0, 15);
//     this.currentWeather.pressure = this.clamp(this.currentWeather.pressure + this.randomStep(1), 980, 1040); // <-- Симулација за притисак
//
//     const round1 = (val: number) => Math.round(val * 10) / 10;
//
//     const newData: SimulationData = {
//       pm25: round1(this.currentPollutants.PM2_5),
//       pm10: round1(this.currentPollutants.PM10),
//       no2: round1(this.currentPollutants.NO2),
//       o3: round1(this.currentPollutants.O3),
//       co2: round1(this.currentPollutants.CO2),       // <-- Прослеђено
//       windSpeed: round1(this.currentWeather.windSpeed),
//       humidity: round1(this.currentWeather.humidity),
//       temperature: round1(this.currentWeather.temperature),
//       pressure: round1(this.currentWeather.pressure), // <-- Прослеђено
//       precipitation: false
//     };
//
//     this.currentSimulationSource.next(newData);
//   }
//
//   updateSimulation(data: SimulationData) {
//     this.currentSimulationSource.next(data);
//   }
//
//   getCurrentValues(): SimulationData {
//     return this.currentSimulationSource.value;
//   }
//
//   ngOnDestroy() {
//     if (this.simulationInterval) {
//       clearInterval(this.simulationInterval);
//     }
//   }
// }

import { Injectable, OnDestroy } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface SimulationData {
  pm25: number;
  pm10: number;
  no2: number;
  o3: number;
  co2: number;
  windSpeed: number;
  humidity: number;
  temperature: number;
  pressure: number;
  precipitation: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class SimulationStateService implements OnDestroy {
  private currentSimulationSource = new BehaviorSubject<SimulationData>({
    pm25: 40,
    pm10: 60,
    no2: 50,
    o3: 40,
    co2: 500,
    windSpeed: 2.5,
    humidity: 65.0,
    temperature: 20.0,
    pressure: 1013.0,
    precipitation: false
  });

  currentSimulation$ = this.currentSimulationSource.asObservable();
  private simulationInterval: any;

  private currentPollutants = {
    PM2_5: 40,
    PM10: 60,
    NO2: 50,
    O3: 40,
    CO2: 500
  };

  private currentWeather = {
    temperature: 20.0,
    humidity: 65.0,
    windSpeed: 3.5,
    precipitation: 0.0,
    pressure: 1013.0
  };

  constructor() {
    this.startGlobalSimulation();
  }

  private startGlobalSimulation() {
    this.simulationInterval = setInterval(() => {
      this.generateAndEmitNewData();
    }, 10_000);
  }

  private randomStep(maxStep: number): number {
    return Math.round((Math.random() * 2 * maxStep - maxStep) * 10) / 10;
  }

  private clamp(value: number, min: number, max: number): number {
    return Math.max(min, Math.min(max, value));
  }

  private generateAndEmitNewData() {
    // ISPRAVLJENO: veći koraci + prošireni opsezi da HAZARDOUS kategorije
    // (PM2.5>55, PM10>100, NO2>180, O3>180) i CEP obrasci budu stvarno dostižni.
    this.currentPollutants.PM2_5 = this.clamp(this.currentPollutants.PM2_5 + this.randomStep(4), 0, 100);
    this.currentPollutants.PM10  = this.clamp(this.currentPollutants.PM10  + this.randomStep(5), 0, 150);
    this.currentPollutants.NO2   = this.clamp(this.currentPollutants.NO2   + this.randomStep(8), 0, 220);
    this.currentPollutants.O3    = this.clamp(this.currentPollutants.O3    + this.randomStep(8), 0, 220);
    this.currentPollutants.CO2   = this.clamp(this.currentPollutants.CO2   + this.randomStep(60), 300, 2500);

    this.currentWeather.temperature = this.clamp(this.currentWeather.temperature + this.randomStep(0.5), -10, 40);
    this.currentWeather.humidity    = this.clamp(this.currentWeather.humidity + this.randomStep(2), 10, 100);
    this.currentWeather.windSpeed   = this.clamp(this.currentWeather.windSpeed + this.randomStep(1.2), 0, 15);
    this.currentWeather.pressure    = this.clamp(this.currentWeather.pressure + this.randomStep(1), 980, 1040);

    const round1 = (val: number) => Math.round(val * 10) / 10;

    const newData: SimulationData = {
      pm25: round1(this.currentPollutants.PM2_5),
      pm10: round1(this.currentPollutants.PM10),
      no2: round1(this.currentPollutants.NO2),
      o3: round1(this.currentPollutants.O3),
      co2: round1(this.currentPollutants.CO2),
      windSpeed: round1(this.currentWeather.windSpeed),
      humidity: round1(this.currentWeather.humidity),
      temperature: round1(this.currentWeather.temperature),
      pressure: round1(this.currentWeather.pressure),
      precipitation: false
    };

    this.currentSimulationSource.next(newData);
  }

  updateSimulation(data: SimulationData) {
    this.currentSimulationSource.next(data);
  }

  getCurrentValues(): SimulationData {
    return this.currentSimulationSource.value;
  }

  /**
   * NOVO: Ubacuje kratak niz vrednosti dizajniran da pouzdano okine
   * konkretan CEP obrazac, umesto da se čeka na organski random walk.
   * Svaki korak se šalje kroz isti currentSimulation$ stream (pa prolazi
   * kroz normalan sendToCep() flow), samo sa manjim razmakom (300ms)
   * da se demonstracija ne oteže.
   */
  triggerScenario(scenario: 'PM25_SPIKE' | 'CORRELATED_SURGE' | 'SUSTAINED_HAZARDOUS' | 'WIND_DROP' | 'RECOVERY' | 'STABLE'): void {
    const base = this.getCurrentValues();

    const sequences: Record<string, SimulationData[]> = {

      PM25_SPIKE: [
        { ...base, pm25: 30 },
        { ...base, pm25: 30 },
        { ...base, pm25: this.clamp(30 + 25, 0, 100) } // diff = 25 >= 6 prag
      ],

      CORRELATED_SURGE: [
        { ...base, pm25: 30, no2: 40 },
        { ...base, pm25: 30, no2: 40 },
        { ...base, pm25: this.clamp(30 + 18, 0, 100), no2: this.clamp(40 + 35, 0, 220) }
      ],

      SUSTAINED_HAZARDOUS: [
        { ...base, pm25: 60, no2: base.no2 },
        { ...base, pm25: 62, no2: base.no2 },
        { ...base, pm25: 65, no2: base.no2 }
      ],

      WIND_DROP: [
        { ...base, windSpeed: 6.0, pm25: 30 },
        { ...base, windSpeed: 6.0, pm25: 30 },
        { ...base, windSpeed: 0.8, pm25: 30 } // pad sa avg~6 na 0.8, pm25 ostaje moderate (20-45)
      ],

      RECOVERY: [
        { ...base, pm25: 60 },
        { ...base, pm25: 60 },
        { ...base, pm25: 60 },
        { ...base, pm25: 40 } // pad od 20 sa maxa 60, >=15 prag
      ],

      STABLE: [
        { ...base, pm25: 25 },
        { ...base, pm25: 26 },
        { ...base, pm25: 25 }
      ]
    };

    const steps = sequences[scenario];
    steps.forEach((data, i) => {
      setTimeout(() => this.currentSimulationSource.next(data), i * 300);
    });
  }

  ngOnDestroy() {
    if (this.simulationInterval) {
      clearInterval(this.simulationInterval);
    }
  }
}
