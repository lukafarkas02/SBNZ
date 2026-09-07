// template-page.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {PollutantClassificationResult, TemplateService, ThresholdRow} from '../../../../core/service/template.service';
import {NavigationComponent} from '../../../../layout/navigation/navigation/navigation.component';


@Component({
  selector: 'app-template-page',
  standalone: true,
  imports: [CommonModule, FormsModule, NavigationComponent],
  templateUrl: './template-page.component.html',
  styleUrls: ['./template-page.component.css']
})
export class TemplatePageComponent implements OnInit {

  pollutantTypes: string[] = [];
  selectedPollutant: string = '';

  currentRules: ThresholdRow[] = [];

  allThresholds: ThresholdRow[] = [];
  generatedRules: string = '';
  showGeneratedRules = false;

  saveMessage: string | null = null;

  categoryOptions = ['GOOD', 'MODERATE', 'POOR', 'HAZARDOUS'];

  // Test panel
  testValues: Record<string, number> = {
    PM2_5: 40, PM10: 60, NO2: 50, O3: 40, CO2: 500
  };
  results: PollutantClassificationResult[] | null = null;

  constructor(private templateService: TemplateService) {}

  ngOnInit(): void {
    this.templateService.getPollutantTypes().subscribe(types => {
      this.pollutantTypes = types;
      if (types.length > 0) {
        this.selectedPollutant = types[0];
        this.loadRulesForSelected();
      }
    });
    this.loadAllThresholds();
  }

  onPollutantChange(): void {
    this.saveMessage = null;
    this.loadRulesForSelected();
  }

  private loadRulesForSelected(): void {
    if (!this.selectedPollutant) return;
    this.templateService.getThresholdsForPollutant(this.selectedPollutant).subscribe(rules => {
      this.currentRules = rules;
    });
  }

  addRule(): void {
    this.currentRules.push({ category: 'GOOD', minValue: 0, maxValue: 0 });
  }

  removeRule(index: number): void {
    this.currentRules.splice(index, 1);
  }

  saveTemplate(): void {
    this.templateService.saveThresholds(this.selectedPollutant, this.currentRules).subscribe(res => {
      this.saveMessage = `Sačuvano — ${res.ruleCount} pravila za ${res.pollutantType} (ukupno ${res.totalGeneratedRules} generisanih pravila).`;
      this.loadAllThresholds();
    });
  }

  private loadAllThresholds(): void {
    this.templateService.getAllThresholds().subscribe(data => {
      this.allThresholds = data;
    });
  }

  toggleGeneratedRules(): void {
    this.showGeneratedRules = !this.showGeneratedRules;
    if (this.showGeneratedRules) {
      this.templateService.getGeneratedRules().subscribe(text => {
        this.generatedRules = text;
      });
    }
  }

  classify(): void {
    const pollutants = Object.keys(this.testValues).map(key => ({
      pollutantType: key,
      value: this.testValues[key]
    }));

    this.templateService.classify(pollutants).subscribe(res => {
      this.results = res;
    });
  }

  getStatusColor(status: string): string {
    switch (status) {
      case 'GOOD': return '#2e7d32';
      case 'MODERATE': return '#9ccc65';
      case 'POOR': return '#f9a825';
      case 'HAZARDOUS': return '#e53935';
      default: return '#9e9e9e';
    }
  }

  groupedThresholds(): Record<string, ThresholdRow[]> {
    const grouped: Record<string, ThresholdRow[]> = {};
    for (const row of this.allThresholds) {
      const key = row.pollutantType || '';
      if (!grouped[key]) grouped[key] = [];
      grouped[key].push(row);
    }
    return grouped;
  }

  pollutantKeys(): string[] {
    return Object.keys(this.groupedThresholds());
  }
}
