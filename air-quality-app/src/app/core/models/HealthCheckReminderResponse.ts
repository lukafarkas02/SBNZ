export interface HealthCheckReminderResponse {
  userId: number;
  message: string;
  urgent: boolean;
  timestamp: string;
}
