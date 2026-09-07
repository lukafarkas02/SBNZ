import { PollutantRequest } from "./pollutant-request.model";

export interface MeasurementRequest {
  pollutants: PollutantRequest[];
}