import { MeasurementRequest } from "./measurement-request.model";

export interface AirQualityRequestDTO {
  email: string | null;
  measurement: MeasurementRequest;
}