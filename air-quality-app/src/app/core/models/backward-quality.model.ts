export interface BackwardQualityRequest {
  pm25: number;
  pm10: number;
  no2: number;
  o3: number;
  windSpeed: number;
  humidity: number;       // <-- Додајте ово
  temperature: number;    // <-- Додајте ово
  precipitation: boolean;
  userEmail: string;
}
