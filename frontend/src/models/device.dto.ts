import { IndicatorDTO } from './indicator.dto';

export interface DeviceDTO {
  id: string;
  name: string;
  coordinate: {
    latitude: string;
    longitude: string;
  };
  indicators: IndicatorDTO[];
}
