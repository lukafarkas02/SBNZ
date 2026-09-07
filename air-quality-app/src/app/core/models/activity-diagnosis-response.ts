export interface ActivityDiagnosisResponse {

  diagnosed: boolean;

  safe: boolean;

  shouldPostpone: boolean;

  strictRestriction: boolean;

  riskLevel: string;

  recommendation: string;

  reasoningSteps: string[];
}
