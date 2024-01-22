import { VirtualDeviceTypeEnum } from "../enums/VirtualDeviceType.enum"

export interface RentType {
    rentId: string,
    startLocalDateTime: string,
    endLocalDateTime: string,
    userId: string,
    renterUsername?: string,
    virtualDeviceId: string,
    virtualDeviceType?: VirtualDeviceTypeEnum | null
}

export interface CreateRentType {
    startLocalDateTime: string,
    endLocalDateTime: string,
    userId: string,
    virtualDeviceId: string
}