import { MeasurementRequest } from "./measurement-request.model";
import {WeatherConditions} from './weather-conditions';

export interface AirQualityRequestDTO {
  email: string | null;
  measurement: MeasurementRequest;
  weather: WeatherConditions;
}
