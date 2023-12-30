import { DatabaseTypeEnum, OperatingSystemTypeEum, VirtualDeviceTypeEnum } from "../enums/VirtualDeviceType.enum";

export interface VirtualDeviceType {
    type: VirtualDeviceTypeEnum | undefined,
    id: string,
    storageSize: number,
    cpuCores: number,
    ram: number,
    phoneNumber?: number,
    databaseType?: DatabaseTypeEnum,
    operatingSystemType?: OperatingSystemTypeEum
}