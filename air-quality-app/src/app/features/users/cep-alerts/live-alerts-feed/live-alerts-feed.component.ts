// live-alerts-feed.component.ts
import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { CommonModule } from '@angular/common';

export interface Warning {
  type: string;
  content: string;
  timestamp: string; // ISO string sa backend-a
}

interface DisplayAlert extends Warning {
  id: string;
  severity: 'critical' | 'warning' | 'early' | 'good' | 'info';
  icon: string;
  label: string;
}

@Component({
  selector: 'app-live-alerts-feed',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './live-alerts-feed.component.html',
  styleUrls: ['./live-alerts-feed.component.css']
})
export class LiveAlertsFeedComponent implements OnChanges {

  // Parent prosleđuje novoprimljene warning-e iz backend odgovora
  // (npr. rezultat POST /api/air/pm25Increase poziva)
  @Input() newAlerts: Warning[] | null = null;

  alerts: DisplayAlert[] = [];
  private maxAlerts = 25;

  private typeMeta: Record<string, { severity: DisplayAlert['severity']; icon: string; label: string }> = {
    PM25_SPIKE:                       { severity: 'warning',  icon: '📈', label: 'PM2.5 Spike' },
    PM25_SPIKE_RESOLVED:              { severity: 'good',     icon: '✅', label: 'Spike Resolved' },
    CORRELATED_SURGE:                 { severity: 'warning',  icon: '🚦', label: 'Correlated Surge' },
    CORRELATED_SURGE_RESOLVED:        { severity: 'good',     icon: '✅', label: 'Surge Resolved' },
    SUSTAINED_HAZARDOUS:              { severity: 'critical', icon: '☠️', label: 'Sustained Hazard' },
    SUSTAINED_HAZARDOUS_RESOLVED:     { severity: 'good',     icon: '✅', label: 'Hazard Resolved' },
    PROLONGED_SMOG:                   { severity: 'critical', icon: '🌫️', label: 'Prolonged Smog' },
    PROLONGED_SMOG_RESOLVED:          { severity: 'good',     icon: '💨', label: 'Smog Cleared' },
    WIND_DROP_EARLY_WARNING:          { severity: 'early',    icon: '⏳', label: 'Early Warning' },
    WIND_DROP_EARLY_WARNING_RESOLVED: { severity: 'good',     icon: '✅', label: 'Wind Normalized' },
    AIR_RECOVERING:                   { severity: 'good',     icon: '🌤️', label: 'Air Improving' },
    AIR_RECOVERING_RESOLVED:          { severity: 'info',     icon: 'ℹ️', label: 'Air Stabilized' },
    STABLE:                           { severity: 'info',     icon: 'ℹ️', label: 'Stabilized' },
  };

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['newAlerts'] && this.newAlerts && this.newAlerts.length > 0) {
      const mapped = this.newAlerts.map(a => this.toDisplayAlert(a));
      this.alerts = [...mapped, ...this.alerts].slice(0, this.maxAlerts);
    }
  }

  private toDisplayAlert(a: Warning): DisplayAlert {
    const meta = this.typeMeta[a.type] ?? { severity: 'info' as const, icon: '🔔', label: a.type };
    return {
      ...a,
      id: a.type + '_' + a.timestamp + '_' + Math.random().toString(36).slice(2, 7),
      severity: meta.severity,
      icon: meta.icon,
      label: meta.label
    };
  }

  timeAgo(timestamp: string): string {
    const diffMs = Date.now() - new Date(timestamp).getTime();
    const seconds = Math.floor(diffMs / 1000);
    if (seconds < 5) return 'just now';
    if (seconds < 60) return `${seconds}s ago`;
    const minutes = Math.floor(seconds / 60);
    if (minutes < 60) return `${minutes}m ago`;
    const hours = Math.floor(minutes / 60);
    return `${hours}h ago`;
  }

  trackByAlert(index: number, alert: DisplayAlert): string {
    return alert.id;
  }
}
