import { IndicatorDTO } from './indicator.dto';

export interface DeviceDTO {
  id: string;
  name: string;
  trackingCode: string;
  coordinate: {
    latitude: string;
    longitude: string;
  };
  indicators: IndicatorDTO[];
}
