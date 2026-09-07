export interface PollutantData {
  pm25: number;
  pm10: number;
  no2: number;
  o3: number;
  windSpeed: number;
  timestamp?: number;    // long u Javi (milisekunde)
  userEmail?: string | null;
}
