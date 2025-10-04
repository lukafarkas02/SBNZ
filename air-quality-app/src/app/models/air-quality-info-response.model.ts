import { AirQualityInputResponse } from "./air-quality-input-response";
import { RecommendationResponse } from "./recommendation-response.model";

export interface AirQualityInfoResponse {
  airCategory: string;
  explanation: {
    influencingPollutants: string[];
  };
  recommendation: RecommendationResponse;
  warnings: string[] | null;
  input: AirQualityInputResponse;
}