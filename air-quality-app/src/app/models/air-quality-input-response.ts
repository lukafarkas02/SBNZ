import { PollutantResponse } from "./pollutant-response.model";

export interface AirQualityInputResponse {
  id: number;
  pollutantMeasurment: {
    pollutants: PollutantResponse[];
  };
  user: {
    firstName: string;
    lastName: string;
    email: string;
    userType: string;
  };
}