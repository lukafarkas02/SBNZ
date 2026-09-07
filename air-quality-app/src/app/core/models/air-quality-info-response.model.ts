import { AirQualityInputResponse } from "./air-quality-input-response";

export interface AirQualityInfoResponse {
  id?: number;

  airCategory: string;

  explanation: {
    influencingPollutants: string[];
  };

  input: AirQualityInputResponse;
}
