import { AirQualityInfoResponse } from "./air-quality-info-response.model";
import {OutdoorActivityPlanResponse} from './OutdoorActivityPlanResponse';
import {HealthCheckReminderResponse} from './HealthCheckReminderResponse';
import {WeatherConditions} from './weather-conditions';

class SmartHomeCommandResponse {
}

export interface AirQualityEvaluationResponse {
  airQualityInfo: AirQualityInfoResponse | null;
  outdoorActivityPlan: OutdoorActivityPlanResponse | null;
  healthCheckReminder: HealthCheckReminderResponse | null;
  smartHomeCommand: SmartHomeCommandResponse | null;

  weatherConditions: WeatherConditions | null;

}
