export interface WeatherConditions {
  temperature: number;
  humidity: number;
  windSpeed: number;
  windCategory?: string;
  precipitation: number;
  weatherImpact?: string;
  logMessage?: string;
  pressure: number;
}
