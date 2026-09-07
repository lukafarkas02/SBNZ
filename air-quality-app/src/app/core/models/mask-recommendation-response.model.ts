export interface MaskRecommendationResponse {
  shouldWearMask: boolean;
  strongAdvice: boolean;
  message: string;
  riskMessage: string;      // <-- Исправљено (или ostavite rsikMessage ако vam tako odgovara)
  reasoningSteps: string[]; // <-- Додато за ланац закључивања
}
